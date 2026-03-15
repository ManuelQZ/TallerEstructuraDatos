package Escenario3;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        GestionDeSolicitudes sistema = new GestionDeSolicitudes();

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {

            Solicitud s = new Solicitud(
                    "SOL" + i,
                    "Usuario" + i,
                    "Origen" + i,
                    "Destino" + i,
                    LocalDateTime.now(),
                    EstadoSolicitud.PENDIENTE
            );

            sistema.registroSolicitud(s);
        }

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");

        System.out.println("Solicitudes pendientes:");
        sistema.mostrarSolicitudesPendientes();
    }
}