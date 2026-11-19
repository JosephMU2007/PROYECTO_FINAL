package modelo;

public class Parada {
    private int idParada;
    private String nombre;
    private double coordenadaX;
    private double coordenadaY;

    public Parada(int idParada, String nombre, double coordenadaX, double coordenadaY) {
        this.idParada = idParada;
        this.nombre = nombre;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public double distanciaA(Parada otra) {
        if (otra == null) return 0.0;
        double dx = this.coordenadaX - otra.coordenadaX;
        double dy = this.coordenadaY - otra.coordenadaY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean esCaminableA(Parada otra) {
        return distanciaA(otra) <= 0.2; // Umbral de 200m
    }

    public int getIdParada() {
        return idParada;
    }
    public String getNombre() {
        return nombre;
    }
    public double getCoordenadaX() {
        return coordenadaX;
    }
    public double getCoordenadaY() {
        return coordenadaY;
    }
}
