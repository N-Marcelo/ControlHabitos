package controlhabitos.controlhabitos.Service;

import controlhabitos.controlhabitos.Model.Usuario;
import controlhabitos.controlhabitos.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(long id){
        return usuarioRepository.findById(id);
    }
    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Usuario actualizarUsuario(Long id, Usuario usuarioDetails){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setCorreo(usuarioDetails.getCorreo());
        usuario.setContraseña(usuarioDetails.getContraseña());
        usuario.setRol(usuarioDetails.getRol());

        return usuarioRepository.save(usuario);
    }
    public Usuario recuperarContraseña(Long id, Usuario usuarioDetails){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setCorreo(usuarioDetails.getCorreo());
        usuario.setContraseña(usuarioDetails.getContraseña());

        return usuarioRepository.save(usuario);
    }
    public String eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return "Usuario eliminado correctamente";
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}