package controlhabitos.controlhabitos.Service;

import controlhabitos.controlhabitos.Model.*;
import controlhabitos.controlhabitos.Repository.*;
import controlhabitos.controlhabitos.dto.HabitoDTO;
import controlhabitos.controlhabitos.dto.CategoriaDTO;
import controlhabitos.controlhabitos.dto.ResumenSemanalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitoService {
    @Autowired
    private HabitoRepository habitoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AuditoriaHabitoRepository auditoriaHabitoRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HistorialHabitoRepository historialHabitoRepository;

    private HabitoDTO convertToDto(Habito habito) {
        return new HabitoDTO(
            habito.getIdHabito(),
            habito.getNombreHabito(),
            habito.getDescripcion(),
            habito.getFrecuencia().name(), // Convertir Enum a String
            habito.getPrioridad().name(),
            habito.getRecordatorio(),
            habito.getActivo(),
            habito.getFechaInicio(),
            habito.getUsuario().getIdUsuario(),
            // Convertir categorías a DTOs
            habito.getCategorias().stream()
                .map(categoria -> new CategoriaDTO(
                    categoria.getIdCategoria(),
                    categoria.getNombreCategoria()
                ))
                .collect(Collectors.toList())
        );
    }

    private Habito convertToEntity(HabitoDTO habitoDTO) {
        Habito habito = new Habito();
        habito.setNombreHabito(habitoDTO.getNombre());
        habito.setDescripcion(habitoDTO.getDescripcion());
        habito.setFrecuencia(Frecuencia.valueOf(habitoDTO.getFrecuencia())); // String a Enum
        habito.setPrioridad(Prioridad.valueOf(habitoDTO.getPrioridad()));
        habito.setRecordatorio(habitoDTO.getRecordatorio());
        habito.setActivo(habitoDTO.isActivo());
        habito.setFechaInicio(habitoDTO.getFechaInicio());

        // Recuperar el usuario
        Usuario usuario = usuarioRepository.findById(habitoDTO.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        habito.setUsuario(usuario);

        if (habitoDTO.getCategorias() != null && !habitoDTO.getCategorias().isEmpty()) {
            List<CategoriaHabito> categorias = habitoDTO.getCategorias().stream()
                .map(categoriaDTO -> categoriaRepository.findById(categoriaDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Categoría con ID " + categoriaDTO.getId() + " no encontrada"))
                )
                .collect(Collectors.toList());
            habito.setCategorias(categorias);
        } else {
            habito.setCategorias(List.of()); // Para evitar null
        }

        return habito;
    }

    public List<HabitoDTO> listarHabitos(Long idUsuario) {
        return habitoRepository.findByUsuario_IdUsuarioAndActivoTrue(idUsuario).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 * * * * *", zone = "America/Lima") // Ejecutar cada minuto
    public void enviarRecordatorios() {
        LocalTime horaActual = LocalTime.now(ZoneId.of("America/Lima")).withSecond(0).withNano(0);

        List<Habito> habitos = habitoRepository.findByActivoTrue();

        for (Habito habito : habitos) {
            LocalTime horaRecordatorio = habito.getRecordatorio();
            if (horaRecordatorio != null && horaActual.equals(horaRecordatorio)) {
                Usuario usuario = habito.getUsuario();
                emailService.enviarRecordatorio(
                    usuario.getCorreo(),
                    usuario.getNombre(),
                    habito.getNombreHabito(),
                    habito.getDescripcion()
                );
            }
        }
    }

    public HabitoDTO buscarHabitoPorId(long id) {
        return habitoRepository.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));
    }

    public HabitoDTO guardarHabito(HabitoDTO habitoDTO) {
        Habito habito = convertToEntity(habitoDTO);
        Usuario usuario = usuarioRepository.findById(habitoDTO.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        habito.setUsuario(usuario);
        Habito habitoGuardado = habitoRepository.save(habito);
        return convertToDto(habitoGuardado);
    }

    public HabitoDTO actualizarHabito(Long id, HabitoDTO habitoDTO) {
        Habito habitoExistente = habitoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Habito no encontrado"));

        habitoExistente.setNombreHabito(habitoDTO.getNombre());
        habitoExistente.setDescripcion(habitoDTO.getDescripcion());
        habitoExistente.setFrecuencia(Frecuencia.valueOf(habitoDTO.getFrecuencia()));
        habitoExistente.setPrioridad(Prioridad.valueOf(habitoDTO.getPrioridad()));
        habitoExistente.setRecordatorio(habitoDTO.getRecordatorio());
        habitoExistente.setActivo(habitoDTO.isActivo());
        habitoExistente.setFechaInicio(habitoDTO.getFechaInicio());

        Habito habitoActualizado = habitoRepository.save(habitoExistente);
        return convertToDto(habitoActualizado);
    }

    public String eliminarHabito(Long id) {
        // Buscar el hábito por ID
        Habito habito = habitoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        // Asegurar que el usuario esté bien cargado (evita errores de null)
        Usuario usuario = usuarioRepository.findById(habito.getUsuario().getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        habito.setUsuario(usuario); // asegurar que esté completamente cargado

        // Deshabilitar el hábito en vez de eliminarlo
        habito.setActivo(false);
        habitoRepository.save(habito);

        // Crear registro de auditoría
        AuditoriaHabito auditoria = new AuditoriaHabito();
        auditoria.setHabito(habito);
        auditoria.setUsuario(usuario);
        auditoria.setFechaAccion(LocalDateTime.now());
        auditoria.setTipoAccion("deshabilitado");

        auditoriaHabitoRepository.save(auditoria);

        return "Hábito deshabilitado correctamente.";
    }

    public void completarHabito(Long idHabito, Long idUsuario,LocalDateTime fechaCompletado, String nota) {
        Habito habito = habitoRepository.findById(idHabito)
            .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        HistorialHabito historial = new HistorialHabito();
        historial.setIdHabito(idHabito);
        historial.setIdUsuario(idUsuario);
        historial.setFechaCompletado(fechaCompletado);
        historial.setNota(nota);

        historialHabitoRepository.save(historial);
    }

    public List<HistorialHabito> obtenerHistorialPorUsuario(Long idUsuario) {
        return historialHabitoRepository.findByIdUsuario(idUsuario);
    }

    // === MÉTODO NUEVO: Resumen Semanal de Hábitos Completados ===

    public List<ResumenSemanalDTO> obtenerResumenSemanal(Long idUsuario) {
        LocalDate hoy = LocalDate.now(ZoneId.systemDefault());
        LocalDate haceUnaSemana = hoy.minusDays(6);
        LocalDateTime inicio = haceUnaSemana.atStartOfDay();
        LocalDateTime fin    = hoy.atTime(LocalTime.MAX);

        // Explicitamos el tipo para Java 8:
        List<HistorialHabito> historiales =
            historialHabitoRepository
                .findByIdUsuarioAndFechaCompletadoBetween(idUsuario, inicio, fin);

        // Mapa de fecha → conteo
        Map<LocalDate, Long> cuentasPorDia = historiales.stream()
            .collect(Collectors.groupingBy(
                h -> h.getFechaCompletado().toLocalDate(),
                Collectors.counting()
            ));

        // Construimos la lista garantizando cada día
        List<ResumenSemanalDTO> resumen = new ArrayList<>();
        for (LocalDate dia = haceUnaSemana; !dia.isAfter(hoy); dia = dia.plusDays(1)) {
            Long total = cuentasPorDia.getOrDefault(dia, 0L);
            resumen.add(new ResumenSemanalDTO(dia, total));
        }
        return resumen;
    }
}
