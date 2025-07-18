package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.dto.ResumenSemanalDTO;
import controlhabitos.controlhabitos.Model.HistorialHabito;
import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Repository.HabitoRepository;
import controlhabitos.controlhabitos.Service.HabitoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HistorialController {
    @Autowired
    private HabitoService habitoService;
    @Autowired
    private HabitoRepository habitoRepository;
    @GetMapping("/historial")
    public String verHistorial(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        List<HistorialHabito> historial = habitoService.obtenerHistorialPorUsuario(usuario.getIdUsuario());

        // Creamos una lista de mapas para incluir el nombre del hábito junto a los demás datos
        List<Map<String, Object>> historialConNombre = historial.stream().map(item -> {
            Map<String, Object> datos = new HashMap<>();
            datos.put("idHistorial", item.getIdHistorial());
            datos.put("idHabito", item.getIdHabito());
            datos.put("idUsuario", item.getIdUsuario());
            datos.put("fechaCompletado", item.getFechaCompletado());
            datos.put("nota", item.getNota());

            // Buscar el nombre del hábito
            habitoRepository.findById(item.getIdHabito())
                    .ifPresent(h -> datos.put("nombreHabito", h.getNombreHabito()));

            return datos;
        }).toList();

        model.addAttribute("historial", historialConNombre);
        return "historial";
    }
    
    


    @GetMapping("/historial/usuario/{id}")
    public String verHistorialPorId(@PathVariable("id") Long idUsuario, Model model) {
        List<HistorialHabito> historial = habitoService.obtenerHistorialPorUsuario(idUsuario);

        List<Map<String, Object>> historialConNombre = historial.stream().map(item -> {
            Map<String, Object> datos = new HashMap<>();
            datos.put("idHistorial", item.getIdHistorial());
            datos.put("idHabito", item.getIdHabito());
            datos.put("idUsuario", item.getIdUsuario());
            datos.put("fechaCompletado", item.getFechaCompletado());
            datos.put("nota", item.getNota());

            habitoRepository.findById(item.getIdHabito())
                    .ifPresent(h -> datos.put("nombreHabito", h.getNombreHabito()));

            return datos;
        }).toList();

        model.addAttribute("historial", historialConNombre);
        return "historial"; // usa la misma vista
    }

}
