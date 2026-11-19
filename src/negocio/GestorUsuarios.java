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

    public String registrarPasajero(String nombre, String correo, String pass, String sector, PerfilTarifario perfil) {

        if (pass == null || pass.length() < 8) {
            return "Error: la contraseña debe contener mínimo 8 caracteres." ;
        }

        // Valida si ya existe un usuario registrado con ese correo
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return "Error: el correo electrónico ya se encuentra registrado.";
            }
        }
        Pasajero nuevo = new Pasajero(usuarios.size() + 1, nombre, correo, pass, "ACTIVA", sector, perfil);
        usuarios.add(nuevo);
        return "Registro exitoso. ¡Bienvenido al Asistente de Movilidad del DMQ!"   ;
    }
}
