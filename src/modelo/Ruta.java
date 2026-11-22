package modelo;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
    private String codigoLinea;
    private String nombre;
    private String sectorOrigen;
    private String sectorDestino;
    private String estado;
    private double distanciaAcumuladaKm;
    private List<Parada> paradas;
    private List<Itinerario> itinerarios;
    private List<Bus> buses;

    public Ruta(String codigoLinea, String nombre, String sectorOrigen, String sectorDestino, String estado) {
        this.codigoLinea = codigoLinea;
        this.nombre = nombre;
        this.sectorOrigen = sectorOrigen;
        this.sectorDestino = sectorDestino;
        this.estado = estado;
        this.paradas = new ArrayList<>();
        this.itinerarios = new ArrayList<>();
        this.buses = new ArrayList<>();
    }

    public void agregarParada(Parada parada) {
        if (parada != null) {
            paradas.add(parada);
            this.distanciaAcumuladaKm = calcularDistanciaTotal();
        }
    }

    public void actualizarEstado(String estado) {
        if (estado != null && !estado.isBlank()) {
            this.estado = estado.trim().toUpperCase();
        }
    }

    public double calcularDistanciaTotal() {
        double total = 0;
        for (int i = 0; i < paradas.size() - 1; i++) {
            total += paradas.get(i).distanciaA(paradas.get(i + 1));
        }
        this.distanciaAcumuladaKm = total;
        return total;
    }

    public void agregarItinerario(Itinerario it) {
        if (it != null) {
            itinerarios.add(it);
        }
    }

    public void agregarBus(Bus bus) {
        if (bus != null) {
            buses.add(bus);
        }
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
    }

    public void setSectorOrigen(String sectorOrigen) {
        if (sectorOrigen != null && !sectorOrigen.isBlank()) {
            this.sectorOrigen = sectorOrigen;
        }
    }

    public void setSectorDestino(String sectorDestino) {
        if (sectorDestino != null && !sectorDestino.isBlank()) {
            this.sectorDestino = sectorDestino;
        }
    }

    public String getCodigoLinea() {
        return codigoLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSectorOrigen() {
        return sectorOrigen;
    }

    public String getSectorDestino() {
        return sectorDestino;
    }

    public String getEstado() {
        return estado;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public List<Itinerario> getItinerarios() {
        return itinerarios;
    }

    public List<Bus> getBuses() {
        return buses;
    }
}