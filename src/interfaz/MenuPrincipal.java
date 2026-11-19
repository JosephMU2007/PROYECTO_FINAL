
package interfaz;
import modelo.*;
import negocio.*;
import java.util.Scanner;

public class MenuPrincipal {
    private Scanner sc;
    private GestorUsuarios gestorUsuarios;
    private GestorRutas gestorRutas;
    private GestorPlanificacion gestorPlanificacion;

    public MenuPrincipal() {
        this.sc = new Scanner(System.in);
        this.gestorUsuarios = new GestorUsuarios();
        this.gestorRutas = new GestorRutas();
        this.gestorPlanificacion = new GestorPlanificacion();
    }

    public void iniciar() {
        int opc;
        do {
            System.out.println("\n BUSQ ");
            System.out.println("1. Registrarse (Módulo 1)");
            System.out.println("2. Iniciar Sesión (Módulo 1)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opc = Integer.parseInt(sc.nextLine());

            if (opc == 1) {
                registrarPasajeroUI();
            } else if (opc == 2) {
                procesarLogin();
            }
        } while (opc != 3);
        System.out.println("Gracias por utilizar BusQ.");
    }

    private void registrarPasajeroUI() {
        System.out.println("\n** REGISTRO DE PASAJERO **");
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();
        System.out.print("Sector de residencia: ");
        String sector = sc.nextLine();
        System.out.print("Perfil Tarifario (General / Estudiante / Adulto Mayor / Con Discapacidad): ");
        String tipoPerfil = sc.nextLine();

        double subsidio = tipoPerfil.equalsIgnoreCase("Estudiante") ? 0.50 : 0.0;
        PerfilTarifario perfil = new PerfilTarifario(tipoPerfil, subsidio, true);

        // Validamos e imprimimos el mensaje correspondiente
        if (gestorUsuarios.registrarPasajero(nombre, correo, pass, sector, perfil)) {
            System.out.println("Registro exitoso. ¡Bienvenido al Asistente de Movilidad del DMQ!");
        } else {
            System.out.println("Error: el correo electrónico ya se encuentra registrado.");
        }
    }

    private void procesarLogin() {
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Usuario usuario = gestorUsuarios.autenticar(correo, pass);
        if (usuario == null) {
            System.out.println("Error: usuario o contraseña incorrectos.");
            return;
        }

        System.out.println("Bienvenido, " + usuario.getNombreCompleto() + "!");
        if (usuario instanceof Pasajero) {
            menuPasajero((Pasajero) usuario);
        }
    }

    private void menuPasajero(Pasajero pasajero) {
        int opc;
        do {
            System.out.println("\n--- MENÚ PASAJERO (MÓDULOS 1, 2 Y 3) ---");
            System.out.println("1. Consultar catálogo de rutas y conexiones (Módulo 2)");
            System.out.println("2. Planificar itinerario óptimo y calcular ETA (Módulo 3)");
            System.out.println("3. Guardar ruta favorita (Módulo 3)");
            System.out.println("4. Cerrar sesión");
            System.out.print("Opción: ");
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1:
                    System.out.println("\n=== CATÁLOGO DE RUTAS DMQ ===");
                    System.out.println(gestorRutas.listarRutas());
                    break;
                case 2:
                    System.out.print("Ingrese código de línea de bus (ej. C1): ");
                    String cod = sc.nextLine();
                    Ruta ruta = gestorRutas.buscarRutaPorCodigo(cod);
                    if (ruta != null) {
                        Itinerario it = gestorPlanificacion.generarItinerarioOptimo(ruta, ruta.getSectorOrigen(), ruta.getSectorDestino(), "Más rápido", "08:00");
                        double costoFinal = it.calcularCostoTotal(pasajero.getPerfilTarifario());
                        double etaMin = gestorPlanificacion.calcularETA(ruta.calcularDistanciaTotal(), 30.0);

                        System.out.println("\n--- ITINERARIO GENERADO ---");
                        System.out.println("Origen: " + it.getOrigen() + " -> Destino: " + it.getDestino());
                        System.out.println("Duración estimada: " + it.getDuracionEstimadaMin() + " min");
                        System.out.println("Costo total con perfil (" + pasajero.getPerfilTarifario().getTipoPerfil() + "): $" + String.format("%.2f", costoFinal));
                        System.out.println("ETA estimado para llegada: " + String.format("%.2f", etaMin) + " min");
                    } else {
                        System.out.println("No se encontraron rutas directas ni combinaciones disponibles.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el nombre personalizado para su ruta favorita: ");
                    String nombreFav = sc.nextLine();
                    RutaFavorita rf = new RutaFavorita(nombreFav, "2026-11-18");
                    pasajero.guardarRutaFavorita(rf);
                    System.out.println("Ruta guardada en la cuenta del usuario exitosamente.");
                    break;
            }
        } while (opc != 4);
    }
}