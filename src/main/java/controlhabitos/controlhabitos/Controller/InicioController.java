package controlhabitos.controlhabitos.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping({"/", "/index"})
    public String mostrarIndex() {
        return "index";
    }
    @GetMapping("/AdminPage")
    public String mostrarAdminPage() {
        return "AdminPage";
    }
}
