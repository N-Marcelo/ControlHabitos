package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
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
            return "redirect:/menu";

        }
        model.addAttribute("error", "Usuario o contraseña incorrectos.");
        return "index";
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
