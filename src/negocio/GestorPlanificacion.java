package negocio;
import modelo.*;
import java.util.List;

public class GestorPlanificacion {
    public Itinerario generarItinerarioOptimo(Ruta ruta, String origen, String destino, String criterio, String horaSalida) {
        if (ruta == null) return null;

        List<Itinerario> itinerarios = ruta.getItinerarios();
        if (!itinerarios.isEmpty()) {
            return itinerarios.get(0);
        }
        return null;
    }

    public double calcularETA(double distanciaKm, double velocidadPromedioKmH) {
        if (velocidadPromedioKmH <= 0) return 0.0;
        return (distanciaKm / velocidadPromedioKmH) * 60.0; // Minutos estimados
    }
}
