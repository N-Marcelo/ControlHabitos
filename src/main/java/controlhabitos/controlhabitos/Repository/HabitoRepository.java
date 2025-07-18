package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.Habito;
import controlhabitos.controlhabitos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HabitoRepository extends JpaRepository<Habito, Long> {

    // Solo listar los hábitos activos
    List<Habito> findByUsuario_IdUsuarioAndActivoTrue(Long idUsuario);
    List<Habito> findByActivoTrue();
}
