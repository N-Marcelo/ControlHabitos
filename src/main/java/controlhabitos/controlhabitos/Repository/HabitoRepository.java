package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitoRepository extends JpaRepository<Habito, Long> {
}
