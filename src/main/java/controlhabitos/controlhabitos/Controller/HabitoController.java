package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.Model.Habito;
import controlhabitos.controlhabitos.Model.HistorialHabito;
import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Repository.HabitoRepository;
import controlhabitos.controlhabitos.Repository.HistorialHabitoRepository;
import controlhabitos.controlhabitos.Repository.UsuarioRepository;
import controlhabitos.controlhabitos.Service.EmailService;
import controlhabitos.controlhabitos.Service.HabitoService;
import controlhabitos.controlhabitos.dto.HabitoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habito")
public class HabitoController {

    @Autowired
    private HabitoService habitoService;

    @GetMapping
    public List<HabitoDTO> listarHabitos(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return List.of();
        }
        return habitoService.listarHabitos(usuario.getIdUsuario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> buscarHabitoPorId(@PathVariable long id){
        try {
            HabitoDTO habito = habitoService.buscarHabitoPorId(id);
            return ResponseEntity.ok(habito);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    private EmailService emailService;

    private void EnviarRecortadorio(Usuario usuario, HabitoDTO habito) {
        emailService.enviarRecordatorio(
                usuario.getCorreo(),
                usuario.getNombre(),
                habito.getNombre(),
                habito.getDescripcion()
        );
    }

    @PostMapping
    public ResponseEntity<HabitoDTO> guardarHabito(@RequestBody HabitoDTO habitoDTO, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        habitoDTO.setIdUsuario(usuario.getIdUsuario()); // Asegúrate de que esto esté
        HabitoDTO creado = habitoService.guardarHabito(habitoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoDTO> actualizarHabito(@PathVariable Long id, @RequestBody HabitoDTO habitoDTO) {
        HabitoDTO actualizado = habitoService.actualizarHabito(id, habitoDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  eliminarHabito(@PathVariable long id){
        try {
            //Obtención de OK y un mensaje adicional
            String mensaje = habitoService.eliminarHabito(id);
            return ResponseEntity.ok(mensaje);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/{id}/completar")
    public ResponseEntity<?> completarHabito(
            @PathVariable Long id,
            @RequestParam(required = false) String fechaCompletado,
            @RequestParam(required = false) String nota,
            HttpSession session) {
        // Obtener usuario logueado desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }

        try {
            LocalDateTime fechaFinal = (fechaCompletado != null && !fechaCompletado.isEmpty())
                    ? LocalDate.parse(fechaCompletado).atStartOfDay()
                    : LocalDateTime.now();

            habitoService.completarHabito(id, usuario.getIdUsuario(), fechaFinal, nota); // Este método lo defines en tu servicio
            return ResponseEntity.ok("Hábito completado y registrado en historial");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
