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
        Pasajero p1 = new Pasajero(1, "Carlos Pérez", "carlos@udla.edu.ec", "12345678", "ACTIVA", "Norte", perfilEstudiante);
        Administrador admin = new Administrador(2, "Admin DMQ", "admin@busq.ec", "admin123", "ACTIVA", "Supervisor");
        usuarios.add(p1);
        usuarios.add(admin);
    }

    public Usuario autenticar(String correo, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.iniciarSesion(correo, contrasena)) {
                return u;
            }
        }
        return null;
    }

    public String registrarPasajero(String nombre, String correo, String contrasena, String sector, PerfilTarifario perfil) {
        if (correo == null || !correo.contains("@") || !correo.contains(".")) {
            return "Error: El correo electrónico debe contener '@' y '.'.";
        }

        int posArroba = correo.indexOf('@');
        int posPunto = correo.lastIndexOf('.');
        if (posArroba <= 0 || posPunto <= posArroba + 1 || posPunto >= correo.length() - 1) {
            return "Error: El formato del correo electrónico no es válido.";
        }

        if (contrasena == null || contrasena.length() < 8) {
            return "Error: La contraseña debe tener al menos 8 caracteres.";
        }

        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo)) {
                return "Error: El correo electrónico ya se encuentra registrado.";
            }
        }

        int nuevoId = usuarios.size() + 1;
        Pasajero nuevo = new Pasajero(nuevoId, nombre, correo, contrasena, "ACTIVA", sector, perfil);
        usuarios.add(nuevo);
        return "Registro exitoso. Bienvenido a BusQ, " + nombre + ".";
    }
}
