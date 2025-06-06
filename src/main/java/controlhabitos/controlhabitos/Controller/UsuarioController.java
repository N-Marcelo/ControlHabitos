package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Repository.UsuarioRepository;
import controlhabitos.controlhabitos.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    // Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam("contrasena") String contrasena,
            Model model) {

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContraseña(contrasena);

        usuarioRepository.save(usuario);

        return "redirect:/index";
    }

    // Procesar login
    @PostMapping("/login")
    public String login(
            @RequestParam("usuario") String correo,
            @RequestParam("contrasena") String contrasena,
            Model model,
            HttpSession session) {

        Usuario usuario = usuarioRepository.findByCorreoAndContraseña(correo, contrasena);

        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);

            if (usuario.getCorreo().equals("admin@gmail.com") && usuario.getContraseña().equals("admin")) {
                return "redirect:/AdminPage"; // Esto debe coincidir con un @GetMapping("/AdminPage")
            }
            return "redirect:/habitos";

        }
        model.addAttribute("error", "Usuario o contraseña incorrectos.");
        return "index";
    }

    @RestController
    @RequestMapping("/usuario")
    public static class UsuarioApiController {

        @Autowired
        private UsuarioService usuarioService;

        @GetMapping
        public List<Usuario> listarUsuarios() {
            return usuarioService.listarUsuarios();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable long id) {
            return usuarioService.buscarUsuarioPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public Usuario guardarUsuario(@RequestBody Usuario usuario) {
            return usuarioService.guardarUsuario(usuario);
        }

        @PutMapping("/{id}")
        public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
            return usuarioService.actualizarUsuario(id, usuarioDetails);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarUsuario(@PathVariable long id) {
            try {
                String mensaje = usuarioService.eliminarUsuario(id);
                return ResponseEntity.ok(mensaje);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }
}

