package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.CategoriaHabito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaHabito, Long> {
}
