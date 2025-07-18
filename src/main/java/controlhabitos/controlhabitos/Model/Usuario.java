package controlhabitos.controlhabitos.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private boolean verificado = false;

    @Column(name = "token_verificacion", unique = true)
    private String tokenVerificacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Habito> habitos;
}
