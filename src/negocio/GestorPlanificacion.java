package negocio;

import modelo.*;
import java.util.List;

public class GestorPlanificacion {
    public Itinerario generarItinerarioOptimo(Ruta ruta, String criterio) {
        if (ruta == null) {
            return null;
        }

        List<Itinerario> itinerarios = ruta.getItinerarios();
        if (!itinerarios.isEmpty()) {
            return itinerarios.get(0);
        }

        Itinerario nuevo = new Itinerario(99, ruta.getSectorOrigen(), ruta.getSectorDestino(), criterio, "08:00", 0.35);
        nuevo.agregarTramo(new Tramo(1, "General", ruta.calcularDistanciaTotal(), ruta.calcularDistanciaTotal() * 2.5));
        ruta.agregarItinerario(nuevo);
        return nuevo;
    }

    public double calcularETA(Ruta ruta, double velocidadPromedioKmH) {
        if (ruta == null || velocidadPromedioKmH <= 0) {
            return 0.0;
        }

        Bus busCalculo;
        if (!ruta.getBuses().isEmpty()) {
            busCalculo = ruta.getBuses().get(0);
        } else {
            busCalculo = new Bus("BUS-TMP", "DMQ-000", 40, "OPERATIVO");
        }
        return busCalculo.calcularETA(ruta.calcularDistanciaTotal(), velocidadPromedioKmH);
    }
}
