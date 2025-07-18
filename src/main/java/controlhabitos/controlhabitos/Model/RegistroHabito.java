package controlhabitos.controlhabitos.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Registro_Habito")
public class RegistroHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;

    @ManyToOne
    @JoinColumn(name = "id_Habito", nullable = false)
    private Habito habito;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Boolean completado;

    @Column(nullable = false)
    private String notaOpcional;
}