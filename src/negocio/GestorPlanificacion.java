package negocio;
import modelo.*;
import java.util.List;

public class GestorPlanificacion {
    public Itinerario generarItinerarioOptimo(Ruta ruta, String criterio) {
        if (ruta == null) return null;

        List<Itinerario> itinerarios = ruta.getItinerarios();
        if (!itinerarios.isEmpty()) {
            return itinerarios.get(0);
        }
        // Si no tiene itinerario predefinido, se genera uno básico basado en la ruta
        Itinerario nuevo = new Itinerario(99, ruta.getSectorOrigen(), ruta.getSectorDestino(), criterio, "08:00", 0.35);
        nuevo.agregarTramo(new Tramo(1, "General", ruta.calcularDistanciaTotal(), ruta.calcularDistanciaTotal() * 2.5));
        return nuevo;
    }

    public double calcularETA(double distanciaKm, double velocidadPromedioKmH) {
        if (velocidadPromedioKmH <= 0) return 0.0;
        return (distanciaKm / velocidadPromedioKmH) * 60.0; // Minutos estimados
    }
}
