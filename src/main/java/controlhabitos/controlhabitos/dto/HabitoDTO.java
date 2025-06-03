package controlhabitos.controlhabitos.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitoDTO {
    private Long id;                  // id_habito en BD
    private String nombre;            // nombre_habito en BD
    private String descripcion;       // descripcion en BD
    private String frecuencia;        // diaria/semanal/personalizada
    private String prioridad;         // alta/media/baja
    private LocalTime recordatorio;   // recordatorio en BD
    private boolean activo;           // activo en BD
    private LocalDate fechaInicio;    // fecha_inicio en BD
    private Long idUsuario;
    private List<CategoriaDTO> categorias; // Lista de categorías asociadas
}