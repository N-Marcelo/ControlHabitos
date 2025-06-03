package controlhabitos.controlhabitos.Controller;

import controlhabitos.controlhabitos.Model.Habito;
import controlhabitos.controlhabitos.Service.HabitoService;
import controlhabitos.controlhabitos.dto.HabitoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/habito")
public class HabitoController {
    @Autowired
    private HabitoService habitoService;

    @GetMapping
    public List<HabitoDTO> listarHabitos(){
        return habitoService.listarHabitos();
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

    @PostMapping
    public ResponseEntity<HabitoDTO> guardarHabito(@RequestBody HabitoDTO habitoDTO) {
        HabitoDTO creado = habitoService.guardarHabito(habitoDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
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
}
