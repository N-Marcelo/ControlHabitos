package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
