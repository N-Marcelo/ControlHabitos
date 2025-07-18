package controlhabitos.controlhabitos.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Historial_Habito")
public class HistorialHabito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_habito", nullable = false)
    private Long idHabito;

    @Column(name = "fecha_completado", nullable = false)
    private LocalDateTime fechaCompletado;

    @Column(name = "nota")
    private String nota;

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(Long idHabito) {
        this.idHabito = idHabito;
    }

    public LocalDateTime getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(LocalDateTime fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
