package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreoAndContraseña(String correo, String contraseña);
    Usuario findByTokenVerificacion(String token);
    Optional<Usuario> findByCorreo(String correo);

}
