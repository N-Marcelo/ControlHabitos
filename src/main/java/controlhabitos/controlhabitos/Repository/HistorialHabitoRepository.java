package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.HistorialHabito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialHabitoRepository extends JpaRepository <HistorialHabito, Long> {
    List<HistorialHabito> findByIdUsuario(Long idUsuario);
}
