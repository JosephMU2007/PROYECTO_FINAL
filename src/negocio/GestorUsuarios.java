package negocio;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private List<Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        PerfilTarifario perfilEstudiante = new PerfilTarifario("Estudiante", 0.50, true);
        Pasajero p1 = new Pasajero(1, "Carlos Pérez", "carlos@udla.edu.ec", "1234", "ACTIVA", "Norte", perfilEstudiante);
        usuarios.add(p1);
    }

    public Usuario autenticar(String correo, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.iniciarSesion(correo, contrasena)) {
                return u;
            }
        }
        return null;
    }

    public boolean registrarPasajero(String nombre, String correo, String pass, String sector, PerfilTarifario perfil) {
        // Valida si ya existe un usuario registrado con ese correo
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return false; // Retorna falso indicando que ya existe
            }
        }
        Pasajero nuevo = new Pasajero(usuarios.size() + 1, nombre, correo, pass, "ACTIVA", sector, perfil);
        usuarios.add(nuevo);
        return true;
    }
}
