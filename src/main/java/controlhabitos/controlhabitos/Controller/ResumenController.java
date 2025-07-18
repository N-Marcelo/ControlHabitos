package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.dto.ResumenSemanalDTO;
import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Service.HabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class ResumenController {

    @Autowired
    private HabitoService habitoService;

    @GetMapping("/resumen-semanal")
    public String mostrarResumenSemanal(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        List<ResumenSemanalDTO> resumen =
            habitoService.obtenerResumenSemanal(usuario.getIdUsuario());
        model.addAttribute("resumen", resumen);
        return "resumenSemanal";
    }
}
