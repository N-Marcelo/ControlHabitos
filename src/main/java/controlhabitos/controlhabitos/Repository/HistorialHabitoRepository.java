package controlhabitos.controlhabitos.Repository;

import controlhabitos.controlhabitos.Model.HistorialHabito;
import org.springframework.data.jpa.repository.JpaRepository;

// Estos imports SON imprescindibles:
import java.time.LocalDateTime;
import java.util.List;

public interface HistorialHabitoRepository extends JpaRepository<HistorialHabito, Long> {

    // Tu método existente
    List<HistorialHabito> findByIdUsuario(Long idUsuario);

    // El nuevo método, con LocalDateTime bien resuelto:
    List<HistorialHabito> findByIdUsuarioAndFechaCompletadoBetween(
        Long idUsuario,
        LocalDateTime fechaDesde,
        LocalDateTime fechaHasta
    );
}
