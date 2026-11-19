package modelo;
import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Usuario{
    private String sectorResidencia;
    private PerfilTarifario perfilTarifario;
    private List<RutaFavorita> rutasFavoritas;

    public Pasajero(int idUsuario, String nombreCompleto, String correo, String contrasena, String estadoCuenta, String sectorResidencia, PerfilTarifario perfilTarifario) {
        super(idUsuario, nombreCompleto, correo, contrasena, estadoCuenta);
        this.sectorResidencia = sectorResidencia;
        this.perfilTarifario = perfilTarifario;
        this.rutasFavoritas = new ArrayList<>();
    }

    public void guardarRutaFavorita(RutaFavorita ruta) {
        if (ruta != null) {
            rutasFavoritas.add(ruta);
        }
    }

    public double consultarTarifa(double tarifaBase) {
        if (perfilTarifario != null) {
            return perfilTarifario.calcularTarifa(tarifaBase);
        }
        return tarifaBase;
    }

    public PerfilTarifario getPerfilTarifario() { return perfilTarifario; }
    public String getSectorResidencia() { return sectorResidencia; }
    public List<RutaFavorita> getRutasFavoritas() { return rutasFavoritas; }
}
