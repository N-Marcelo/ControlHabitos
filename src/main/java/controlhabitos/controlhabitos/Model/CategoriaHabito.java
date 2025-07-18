package controlhabitos.controlhabitos.Model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Categoria_Habito")
public class CategoriaHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(nullable = false)
    private String nombreCategoria;

    @ManyToMany(mappedBy = "categorias")
    private List<Habito> habitos;
}
