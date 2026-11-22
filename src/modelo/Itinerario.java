package modelo;

import java.util.ArrayList;
import java.util.List;

public class Itinerario {
    private int idItinerario;
    private String origen;
    private String destino;
    private String criterioOptimizacion; // Más rápido, Menos transbordos, Menos caminata
    private String horaSalida;
    private double duracionEstimadaMin;
    private double costoTotal;
    private List<Tramo> tramos;

    public Itinerario(int idItinerario, String origen, String destino, String criterioOptimizacion,
                      String horaSalida, double costoTotal) {
        this.idItinerario = idItinerario;
        this.origen = origen;
        this.destino = destino;
        this.criterioOptimizacion = criterioOptimizacion;
        this.horaSalida = horaSalida;
        this.costoTotal = costoTotal;
        this.tramos = new ArrayList<>();
    }

    public void agregarTramo(Tramo tramo) {
        if (tramo != null) {
            tramos.add(tramo);
            this.duracionEstimadaMin = calcularTiempoTotal();
        }
    }

    public double calcularTiempoTotal() {
        double total = 0;
        for (Tramo t : tramos) {
            total += t.calcularDuracion();
        }
        this.duracionEstimadaMin = total;
        return total;
    }

    public double calcularCostoTotal(PerfilTarifario perfil) {
        if (perfil != null) {
            return perfil.calcularTarifa(this.costoTotal);
        }
        return this.costoTotal;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getCriterioOptimizacion() {
        return criterioOptimizacion;
    }

    public double getDuracionEstimadaMin() {
        return duracionEstimadaMin;
    }

    public double getCostoTotal() {
        return costoTotal;
    }
}