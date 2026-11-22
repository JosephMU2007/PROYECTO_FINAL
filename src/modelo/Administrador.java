package modelo;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario {
    private String rol;
    private List<Ruta> rutasAdministradas;

    public Administrador(int idUsuario, String nombreCompleto, String correo, String contrasena, String estadoCuenta, String rol) {
        super(idUsuario, nombreCompleto, correo, contrasena, estadoCuenta);
        this.rol = rol;
        this.rutasAdministradas = new ArrayList<>();
    }

    public boolean registrarRuta(Ruta ruta) {
        if (ruta != null && !rutasAdministradas.contains(ruta)) {
            rutasAdministradas.add(ruta);
            return true;
        }
        return false;
    }

    public boolean actualizarRuta(Ruta rutaActualizada) {
        if (rutaActualizada == null) {
            return false;
        }
        for (int i = 0; i < rutasAdministradas.size(); i++) {
            if (rutasAdministradas.get(i).getCodigoLinea().equalsIgnoreCase(rutaActualizada.getCodigoLinea())) {
                rutasAdministradas.set(i, rutaActualizada);
                return true;
            }
        }
        rutasAdministradas.add(rutaActualizada);
        return true;
    }

    public void suspenderRuta(Ruta ruta, String motivo) {
        if (ruta != null) {
            ruta.actualizarEstado("SUSPENDIDA (" + motivo + ")");
        }
    }

    public String getRol() {
        return rol;
    }

    public List<Ruta> getRutasAdministradas() {
        return rutasAdministradas;
    }
}