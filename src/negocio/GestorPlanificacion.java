package negocio;

import modelo.*;

public class GestorPlanificacion {

    public Itinerario generarItinerarioOptimo(Ruta ruta, String criterio) {
        if (ruta == null) {
            return null;
        }

        double distancia = ruta.calcularDistanciaTotal();
        if (distancia <= 0) {
            distancia = 15.0;
        }

        double duracionBase = (distancia / 25.0) * 60.0;
        double duracionFinal = duracionBase;
        String tipoTramo = "Troncal directo";

        if ("Menos transbordos".equalsIgnoreCase(criterio)) {
            duracionFinal = duracionBase * 1.10;
            tipoTramo = "Troncal sin transbordos";
        } else if ("Menos caminata".equalsIgnoreCase(criterio)) {
            duracionFinal = duracionBase * 1.05;
            tipoTramo = "Alimentador / Parada cercana";
        }

        Itinerario itinerario = new Itinerario(
                1,
                ruta.getSectorOrigen(),
                ruta.getSectorDestino(),
                criterio,
                "08:00",
                0.35
        );
        itinerario.agregarTramo(new Tramo(1, tipoTramo, distancia, duracionFinal));
        return itinerario;
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
