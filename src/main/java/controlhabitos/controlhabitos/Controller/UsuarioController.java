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
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
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

