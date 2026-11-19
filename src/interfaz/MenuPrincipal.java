
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
            System.out.println("\n BUSQ - ASISTENTE DE MOVILIDAD DMQ ");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opc = Integer.parseInt(sc.nextLine());

            if (opc == 1) {
                registrarPasajeroUI();
            } else if (opc == 2) {
                procesarLogin();
            }
        } while (opc != 3);
        System.out.println("\nGracias por utilizar BusQ!.");
    }

    private void registrarPasajeroUI() {
        System.out.println("\n** REGISTRO DE PASAJERO **");
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña (mínimo 8 caracteres): ");
        String pass = sc.nextLine();
        System.out.print("Sector de residencia: ");
        String sector = sc.nextLine();
        System.out.print("Perfil Tarifario (General / Estudiante / Adulto Mayor / Con Discapacidad): ");
        String tipoPerfil = sc.nextLine();

        double subsidio = tipoPerfil.equalsIgnoreCase("Estudiante") ? 0.50 : 0.0;
        PerfilTarifario perfil = new PerfilTarifario(tipoPerfil, subsidio, true);

        String resultado = gestorUsuarios.registrarPasajero(nombre, correo, pass, sector, perfil);
        System.out.println(resultado);
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
        } else if (usuario instanceof Administrador) {
            menuAdministrador((Administrador) usuario);
        }
    }

    private void menuPasajero(Pasajero pasajero) {
        int opc;
        do {
            System.out.println("\n** MENÚ PASAJERO **");
            System.out.println("1. Consultar catálogo de rutas disponibles ");
            System.out.println("2. Planificar itinerario óptimo y calcular ETA ");
            System.out.println("3. Guardar ruta favorita ");
            System.out.println("4. Cerrar sesión");
            System.out.print("Opción: ");
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1:
                    System.out.println("\n** CATÁLOGO DE RUTAS DMQ **");
                    System.out.println(gestorRutas.listarRutasActivas());
                    break;
                case 2:
                    System.out.print("Ingrese código de línea de bus (ej. C1): ");
                    String cod = sc.nextLine();
                    Ruta ruta = gestorRutas.buscarRutaPorCodigo(cod);
                    if (ruta != null && ruta.getEstado().startsWith("OPERATIVA")) {
                        System.out.print("Criterio de optimización (Más rápido / Menos transbordos / Menos caminata): ");
                        String criterio = sc.nextLine();
                        Itinerario it = gestorPlanificacion.generarItinerarioOptimo(ruta, criterio);
                        double costoFinal = it.calcularCostoTotal(pasajero.getPerfilTarifario());
                        double etaMin = gestorPlanificacion.calcularETA(ruta.calcularDistanciaTotal(), 30.0);

                        System.out.println("\n** ITINERARIO GENERADO **");
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

    private void menuAdministrador(Administrador admin) {
        int opc;
        do {
            System.out.println("\n** MENÚ ADMINISTRADOR **");
            System.out.println("1. Registrar nueva ruta en el catálogo");
            System.out.println("2. Suspender ruta del catálogo");
            System.out.println("3. Listar todas las rutas");
            System.out.println("4. Cerrar sesión");
            System.out.print("Opción: ");
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1:
                    System.out.print("Código de línea (ej. C2): ");
                    String cod = sc.nextLine();
                    System.out.print("Nombre de ruta: ");
                    String nom = sc.nextLine();
                    System.out.print("Sector de origen: ");
                    String orig = sc.nextLine();
                    System.out.print("Sector de destino: ");
                    String dest = sc.nextLine();

                    Ruta nuevaRuta = new Ruta(cod, nom, orig, dest, "OPERATIVA");
                    if (gestorRutas.agregarRuta(nuevaRuta)) {
                        admin.registrarRuta(nuevaRuta);
                        System.out.println("Ruta registrada exitosamente en el catálogo del DMQ.");
                    } else {
                        System.out.println("Error: Ya existe una ruta con ese código.");
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el código de la ruta a suspender: ");
                    String codSusp = sc.nextLine();
                    Ruta r = gestorRutas.buscarRutaPorCodigo(codSusp);
                    if (r != null) {
                        System.out.print("Motivo de suspensión: ");
                        String motivo = sc.nextLine();
                        admin.suspenderRuta(r, motivo);
                        System.out.println("Ruta suspendida y deshabilitada del catálogo de opciones del usuario.");
                    } else {
                        System.out.println("Ruta no encontrada.");
                    }
                    break;
                case 3:
                    System.out.println("\n** LISTADO GENERAL DE RUTAS **");
                    System.out.println(gestorRutas.listarTodasLasRutas());
                    break;
            }
        } while (opc != 4);
    }
}