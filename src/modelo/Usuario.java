package modelo;

public class Usuario {
    private int idUsuario;
    private String nombreCompleto;
    private String correo;
    private String contrasena;
    private String estadoCuenta;

    public Usuario(int idUsuario, String nombreCompleto, String correo, String contrasena, String estadoCuenta) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.contrasena = contrasena;
        this.estadoCuenta = estadoCuenta;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        return this.correo.equalsIgnoreCase(correo)&&
                this.contrasena.equals(contrasena)&&
                "ACTIVA".equalsIgnoreCase(this.estadoCuenta);
    }

    public void actualizarDatos(String nombreCompleto, String correo, String contrasena) {
        if (nombreCompleto != null && !nombreCompleto.isBlank())
            this.nombreCompleto = nombreCompleto;

        if (correo != null && !correo.isBlank())
            this.correo = correo;

        if (contrasena != null && !contrasena.isBlank())
            this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public String getCorreo() {
        return correo;
    }
    public String getContrasena() {
        return contrasena;
    }
    public String getEstadoCuenta() {
        return estadoCuenta;
    }
}
