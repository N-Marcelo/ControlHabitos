package controlhabitos.controlhabitos.dto;

import java.time.LocalDate;

public class ResumenSemanalDTO {
    private LocalDate fecha;
    private Long totalCompletados;

    public ResumenSemanalDTO(LocalDate fecha, Long totalCompletados) {
        this.fecha = fecha;
        this.totalCompletados = totalCompletados;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Long getTotalCompletados() {
        return totalCompletados;
    }
    public void setTotalCompletados(Long totalCompletados) {
        this.totalCompletados = totalCompletados;
    }
}
