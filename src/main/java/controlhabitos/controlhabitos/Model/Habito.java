package controlhabitos.controlhabitos.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Habito")
public class Habito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabito;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties("habitos")
    private Usuario usuario;

    @Column(nullable = false)
    private String nombreHabito;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Column(nullable = false)
    private LocalTime recordatorio;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @OneToMany(mappedBy = "habito")
    @JsonIgnore
    private List<RegistroHabito> registros;

    @ManyToMany
    @JoinTable(
            name = "Habito_Categoria",
            joinColumns = @JoinColumn(name = "idHabito"),
            inverseJoinColumns = @JoinColumn(name = "idCategoria")
    )
    private List<CategoriaHabito> categorias;
}