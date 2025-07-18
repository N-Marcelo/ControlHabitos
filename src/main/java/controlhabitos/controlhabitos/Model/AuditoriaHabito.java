package controlhabitos.controlhabitos.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Auditoria_Habito")
public class AuditoriaHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_habito", nullable = false)
    private Habito habito;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private LocalDateTime fechaAccion;

    private String tipoAccion;
}
