package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Usuario {
    private String sectorResidencia;
    private PerfilTarifario perfilTarifario;
    private List<RutaFavorita> rutasFavoritas;
    private List<Ruta> rutasRecorridas;
    private List<String> criteriosRecorridos;

    public Pasajero(int idUsuario, String nombreCompleto, String correo, String contrasena, String estadoCuenta, String sectorResidencia, PerfilTarifario perfilTarifario) {
        super(idUsuario, nombreCompleto, correo, contrasena, estadoCuenta);
        this.sectorResidencia = sectorResidencia;
        this.perfilTarifario = perfilTarifario;
        this.rutasFavoritas = new ArrayList<>();
        this.rutasRecorridas = new ArrayList<>();
        this.criteriosRecorridos = new ArrayList<>();
    }

    public boolean yaRecorrioConCriterio(Ruta ruta, String criterio) {
        if (ruta == null || criterio == null) {
            return false;
        }
        for (int i = 0; i < rutasRecorridas.size(); i++) {
            Ruta r = rutasRecorridas.get(i);
            String c = criteriosRecorridos.get(i);
            if (r.getCodigoLinea().equalsIgnoreCase(ruta.getCodigoLinea())
                    && c.equalsIgnoreCase(criterio)) {
                return true;
            }
        }
        return false;
    }

    public void registrarRutaRecorrida(Ruta ruta, String criterio) {
        if (ruta == null || criterio == null) {
            return;
        }
        if (!yaRecorrioConCriterio(ruta, criterio)) {
            rutasRecorridas.add(ruta);
            criteriosRecorridos.add(criterio);
        }
    }

    public String getCriterioRecorrido(int indice) {
        if (indice >= 0 && indice < criteriosRecorridos.size()) {
            return criteriosRecorridos.get(indice);
        }
        return "Más rápido";
    }

    public void registrarRutaRecorrida(Ruta ruta) {
        if (ruta != null) {
            rutasRecorridas.add(ruta);
        }
    }

    public List<Ruta> getRutasRecorridas() {
        return rutasRecorridas;
    }

    public void guardarRutaFavorita(RutaFavorita ruta) {
        if (ruta != null) {
            rutasFavoritas.add(ruta);
        }
    }

    public boolean eliminarRutaFavorita(int indice) {
        if (indice >= 0 && indice < rutasFavoritas.size()) {
            rutasFavoritas.remove(indice);
            return true;
        }
        return false;
    }

    public double consultarTarifa(double tarifaBase) {
        if (perfilTarifario != null) {
            return perfilTarifario.calcularTarifa(tarifaBase);
        }
        return tarifaBase;
    }

    public List<Itinerario> consultarItinerarios(RutaFavorita ruta) {
        if (ruta != null) {
            return ruta.consultarItinerarios();
        }
        return new ArrayList<>();
    }

    public int contarRutasFavoritas() {
        return rutasFavoritas.size();
    }

    public RutaFavorita consultarUltimaRutaFavorita() {
        if (rutasFavoritas.isEmpty()) {
            return null;
        }
        return rutasFavoritas.get(rutasFavoritas.size() - 1);
    }

    public boolean yaExisteRutaFavorita(String nombrePersonalizado, String origen, String destino, String criterio) {
        String nombreLimpio = (nombrePersonalizado != null) ? nombrePersonalizado.trim() : "";

        for (RutaFavorita rf : rutasFavoritas) {
            if (!nombreLimpio.isEmpty() && rf.getNombrePersonalizado().equalsIgnoreCase(nombreLimpio)) {
                return true;
            }

            for (Itinerario it : rf.consultarItinerarios()) {
                if (it.getOrigen().equalsIgnoreCase(origen)
                        && it.getDestino().equalsIgnoreCase(destino)
                        && it.getCriterioOptimizacion().equalsIgnoreCase(criterio)) {
                    return true;
                }
            }
        }
        return false;
    }

    public PerfilTarifario getPerfilTarifario() {
        return perfilTarifario;
    }

    public String getSectorResidencia() {
        return sectorResidencia;
    }

    public List<RutaFavorita> getRutasFavoritas() {
        return rutasFavoritas;
    }
}