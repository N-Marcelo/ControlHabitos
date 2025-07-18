package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.Model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping({"/", "/index"})
    public String mostrarIndex() {
        return "index";
    }
    @GetMapping("/AdminPage")
    public String mostrarAdminPage(HttpSession session, Model model){
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
        return "redirect:/login";
    }
    model.addAttribute("idUsuario", usuario.getIdUsuario());
    return "AdminPage";
}
    @GetMapping("/RecuperarContraseña")
    public  String mostrarRecuperarContraseña() {return "RecuperarContraseña"; }
}
