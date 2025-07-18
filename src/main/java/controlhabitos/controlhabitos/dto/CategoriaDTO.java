package controlhabitos.controlhabitos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Long id;          // id_categoria en BD
    private String nombre;    // nombre_categoria en BD
}
