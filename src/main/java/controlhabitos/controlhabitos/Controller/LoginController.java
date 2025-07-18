package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Repository.UsuarioRepository;
import controlhabitos.controlhabitos.Service.EmailService;
import controlhabitos.controlhabitos.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

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
            @RequestParam("rol") String rol,
            Model model) {

        Optional<Usuario> existente = usuarioRepository.findByCorreo(correo);

        if (existente.isPresent()) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "registro";  // Vuelve al formulario con mensaje de error
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContraseña(contrasena);
        usuario.setRol(rol);
        usuario.setVerificado(false);

        // GENERAR TOKEN
        String token = java.util.UUID.randomUUID().toString();
        usuario.setTokenVerificacion(token);

        usuarioRepository.save(usuario);
        enviarCorreoVerificacion(usuario);

        model.addAttribute("mensaje", "Revisa tu correo para verificar la cuenta.");

        return "redirect:/index";
    }
    @Autowired
    private EmailService emailService;

    private void enviarCorreoVerificacion(Usuario usuario) {
        emailService.enviarVerificacion(
                usuario.getCorreo(),
                usuario.getNombre(),
                usuario.getTokenVerificacion()
        );
    }

    @PostMapping("/RecuperarContraseña")
    public String procesarRecuperacion(@RequestParam String correo, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreo(correo);

        if (optionalUsuario.isEmpty()) {
            model.addAttribute("error", "No se encontró ningún usuario con ese correo.");
            return "index"; // o redirigir con error
        }

        Usuario usuario = optionalUsuario.get();

        // Generar token si no existe (opcional, para mayor seguridad)
        if (usuario.getTokenVerificacion() == null || usuario.getTokenVerificacion().isEmpty()) {
            usuario.setTokenVerificacion(UUID.randomUUID().toString());
            usuarioRepository.save(usuario);
        }

        // Enviar el correo
        enviarCorreoRecuperarContraseña(usuario);

        model.addAttribute("mensaje", "Revisa tu correo para continuar con la recuperación.");
        return "index";
    }
    @PostMapping("/actualizarContraseña")
    public String actualizarContrasena(@RequestParam("token") String token,
                                       @RequestParam("contrasena") String nuevaContrasena,
                                       Model model) {
        System.out.println("🔧 Procesando recuperación con token: " + token);

        Usuario usuario = usuarioRepository.findByTokenVerificacion(token);

        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado con token: " + token);
            model.addAttribute("error", "Token inválido o expirado.");
            return "index";
        }

        try {
            Usuario usuarioActualizado = new Usuario();
            usuarioActualizado.setCorreo(usuario.getCorreo());
            usuarioActualizado.setContraseña(nuevaContrasena);

            usuarioService.recuperarContraseña(usuario.getIdUsuario(), usuarioActualizado);

            model.addAttribute("mensaje", "Contraseña actualizada correctamente.");
            usuario.setTokenVerificacion(null);
            usuarioRepository.save(usuario);
            System.out.println("✅ Contraseña actualizada para: " + usuario.getCorreo());
            return "index";

        } catch (Exception e) {
            System.out.println("⚠️ Error al actualizar contraseña: " + e.getMessage());
            model.addAttribute("error", "Hubo un problema al actualizar la contraseña.");
            return "RecuperarContraseña";
        }
    }


    private void enviarCorreoRecuperarContraseña(Usuario usuario) {
        emailService.enviarRecuperarContraseña(
                usuario.getCorreo(),
                usuario.getNombre(),
                usuario.getTokenVerificacion()
        );
    }

    @GetMapping("/recuperarContraseña")
    public String mostrarFormularioNuevaContrasena(@RequestParam("token") String token, Model model) {
        Usuario usuario = usuarioRepository.findByTokenVerificacion(token);
        if (usuario == null) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "index";
        }
        model.addAttribute("correo", usuario.getCorreo());
        model.addAttribute("token", token);
        return "RecuperarContraseña";
    }

    //Verificación de correo
    @GetMapping("/verificar")
    public String verificarCuenta(@RequestParam("token") String token, Model model) {
        Usuario usuario = usuarioRepository.findByTokenVerificacion(token);

        if (usuario != null) {
            usuario.setVerificado(true);
            usuario.setTokenVerificacion(null); // elimina el token
            usuarioRepository.save(usuario);
            model.addAttribute("mensaje", "Cuenta verificada correctamente.");
        } else {
            model.addAttribute("error", "Token inválido o expirado.");
        }

        return "index"; // o una página de confirmación
    }
    // Procesar login
    @PostMapping("/login")
    public String login(
            @RequestParam("usuario") String correo,
            @RequestParam("contrasena") String contrasena,
            Model model,
            HttpSession session) {

        Usuario usuario = usuarioRepository.findByCorreoAndContraseña(correo, contrasena);

        if (usuario != null && usuario.isVerificado()) {
            session.setAttribute("usuarioLogueado", usuario);

            if (usuario.getCorreo().equals("admin@gmail.com") && usuario.getContraseña().equals("admin")) {
                return "redirect:/AdminPage"; // Esto debe coincidir con un @GetMapping("/AdminPage")
            }
            return "redirect:/menu";

        } else {
            model.addAttribute("error", "Cuenta no verificada o credenciales incorrectas.");
            return "index";
        }
    }

    @GetMapping("/menu")
    public String menuPrincipal(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/index";
        }

        model.addAttribute("nombreUsuario", usuario.getNombre());
        return "MenuPrincipal";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String mostrarLogin() {
        return "index";
    }
}
