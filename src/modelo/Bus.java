package modelo;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    private String codigoBus;
    private String placa;
    private int capacidadMaxima;
    private String estado;
    private List<Itinerario> itinerarios;

    public Bus(String codigoBus, String placa, int capacidadMaxima, String estado) {
        this.codigoBus = codigoBus;
        this.placa = placa;
        this.capacidadMaxima = capacidadMaxima;
        this.estado = estado;
        this.itinerarios = new ArrayList<>();
    }

    public double calcularETA(double distanciaKm, double velocidadPromedioKmH) {
        if (distanciaKm <= 0 || velocidadPromedioKmH <= 0) {
            return 0.0;
        }
        return (distanciaKm / velocidadPromedioKmH) * 60.0;
    }

    public void agregarItinerario(Itinerario itinerario) {
        if (itinerario != null) {
            itinerarios.add(itinerario);
        }
    }

    public String getCodigoBus() {
        return codigoBus;
    }

    public String getPlaca() {
        return placa;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public String getEstado() {
        return estado;
    }

    public List<Itinerario> getItinerarios() {
        return itinerarios;
    }
}