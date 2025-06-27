package controlhabitos.controlhabitos.Service;

import controlhabitos.controlhabitos.Model.*;
import controlhabitos.controlhabitos.Repository.*;
import controlhabitos.controlhabitos.dto.HabitoDTO;
import controlhabitos.controlhabitos.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<HabitoDTO> listarHabitos(Long idUsuario){
        return habitoRepository.findByUsuario_IdUsuarioAndActivoTrue(idUsuario).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public HabitoDTO buscarHabitoPorId(long id){
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
    public HabitoDTO actualizarHabito(Long id, HabitoDTO habitoDTO){
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
        Usuario usuario = usuarioRepository.findById(habito.getUsuario().getIdUsuario()
)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        habito.setUsuario(usuario); // asegurar que esté completamente cargado

        // Deshabilitar el hábito en vez de eliminarlo
        habito.setActivo(false);
        habitoRepository.save(habito);

        // Crear registro de auditoría
        AuditoriaHabito auditoria = new AuditoriaHabito();
        auditoria.setHabito(habito);
        auditoria.setUsuario(usuario);
        auditoria.setFechaAccion(java.time.LocalDateTime.now());
        auditoria.setTipoAccion("deshabilitado");

        auditoriaHabitoRepository.save(auditoria);

        return "Hábito deshabilitado correctamente.";
    }
    @Autowired
    private HistorialHabitoRepository historialHabitoRepository;

    public void completarHabito(Long idHabito, Long idUsuario) {
        Habito habito = habitoRepository.findById(idHabito)
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        HistorialHabito historial = new HistorialHabito();
        historial.setIdHabito(idHabito);
        historial.setIdUsuario(idUsuario);
        historial.setFechaCompletado(LocalDateTime.now());

        historialHabitoRepository.save(historial);
    }
    public List<HistorialHabito> obtenerHistorialPorUsuario(Long idUsuario) {
        return historialHabitoRepository.findByIdUsuario(idUsuario);
    }

}
