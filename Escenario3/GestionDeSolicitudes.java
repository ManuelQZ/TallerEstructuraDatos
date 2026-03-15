package Escenario3;

import java.util.Queue;
import java.util.ArrayDeque;
import java.util.HashMap;

public class GestionDeSolicitudes {

    private Queue<Solicitud> colaSolicitudes;
    private HashMap<String, Solicitud> mapaSolicitudes;

    public GestionDeSolicitudes() {
        colaSolicitudes = new ArrayDeque<>();
        mapaSolicitudes = new HashMap<>();
    }

    // Registrar solicitud
    public void registroSolicitud(Solicitud solicitud) {

        colaSolicitudes.add(solicitud);
        mapaSolicitudes.put(solicitud.getCodigo(), solicitud);

    }

    // Atender solicitud más antigua
    public Solicitud atenderSolicitudMasAntigua() {

        Solicitud solicitud = colaSolicitudes.poll();

        if (solicitud != null) {
            mapaSolicitudes.remove(solicitud.getCodigo());
        }

        return solicitud;
    }

    // Cancelar solicitud
    public void cancelarSolicitud(String codigo) {

        Solicitud solicitud = mapaSolicitudes.get(codigo);

        if (solicitud != null) {
            mapaSolicitudes.remove(codigo);
            colaSolicitudes.remove(solicitud);
        }

    }

    // Mostrar solicitudes pendientes
    public void mostrarSolicitudesPendientes() {

        for (Solicitud s : colaSolicitudes) {
            System.out.println(s);
        }

    }
}