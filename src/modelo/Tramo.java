package modelo;

public class Tramo {
    private int orden;
    private String tipo;
    private double distanciaKm;
    private double duracionMin;

    public Tramo(int orden, String tipo, double distanciaKm, double duracionMin) {
        this.orden = orden;
        this.tipo = tipo;
        this.distanciaKm = distanciaKm;
        this.duracionMin = duracionMin;
    }

    public double calcularDuracion() {
        return duracionMin;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }
}
