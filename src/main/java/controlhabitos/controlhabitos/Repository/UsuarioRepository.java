package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreoAndContraseña(String correo, String contraseña);
}
