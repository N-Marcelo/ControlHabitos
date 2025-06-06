package controlhabitos.controlhabitos.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaHabitoController {

    @GetMapping("/habitos")
    public String vistaHabitos() {
        return "habitos"; // Esto busca el archivo `habitos.html` dentro de /templates
    }
}
