package modelo;

import java.util.ArrayList;
import java.util.List;

public class RutaFavorita {
    private String nombrePersonalizado;
    private String fechaGuardado;
    private List<Itinerario> itinerarios;

    public RutaFavorita(String nombrePersonalizado, String fechaGuardado) {
        this.nombrePersonalizado = nombrePersonalizado;
        this.fechaGuardado = fechaGuardado;
        this.itinerarios = new ArrayList<>();
    }

    public void renombrar(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombrePersonalizado = nombre;
        }
    }

    public void agregarItinerario(Itinerario itinerario) {
        if (itinerario != null) {
            itinerarios.add(itinerario);
        }
    }

    public List<Itinerario> consultarItinerarios() {
        return itinerarios;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public String getFechaGuardado() {
        return fechaGuardado;
    }
}