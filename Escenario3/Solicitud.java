package Escenario3;

import java.time.LocalDateTime;

public class Solicitud {

    private String codigo;
    private String usuario;
    private String origen;
    private String destino;
    private LocalDateTime fechaSolicitud;
    private EstadoSolicitud estado;

    public Solicitud(String codigo, String usuario, String origen, String destino,
                     LocalDateTime fechaSolicitud, EstadoSolicitud estado) {

        this.codigo = codigo;
        this.usuario = usuario;
        this.origen = origen;
        this.destino = destino;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "codigo='" + codigo + '\'' +
                ", usuario='" + usuario + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", estado=" + estado +
                '}';
    }
}