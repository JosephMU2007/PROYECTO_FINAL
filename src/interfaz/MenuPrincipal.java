package interfaz;

import modelo.*;
import negocio.*;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner sc;
    private GestorUsuarios gestorUsuarios;
    private GestorRutas gestorRutas;
    private GestorPlanificacion gestorPlanificacion;
    private Itinerario ultimoItinerario;

    public MenuPrincipal() {
        this.sc = new Scanner(System.in);
        this.gestorUsuarios = new GestorUsuarios();
        this.gestorRutas = new GestorRutas();
        this.gestorPlanificacion = new GestorPlanificacion();
        this.ultimoItinerario = null;
    }

    public void iniciar() {
        int opc;
        do {
            System.out.println("\n BUSQ - ASISTENTE DE MOVILIDAD DMQ ");
            System.out.println("\n1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            opc = leerEntero("\nSeleccione una opción (1-3): ");

            if (opc == 1) {
                registrarPasajeroUI();
            } else if (opc == 2) {
                procesarLogin();
            } else if (opc != 3) {
                System.out.println("Opción fuera del menú. Ingrese un número válido (1-3).");
            }
        } while (opc != 3);
        System.out.println("\nGracias por utilizar BusQ!.");
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número válido del menú.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un valor decimal válido (ej. 0.35).");
            }
        }
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

        System.out.println("\nPerfil Tarifario:");
        System.out.println("1. General");
        System.out.println("2. Estudiante");
        System.out.println("3. Adulto Mayor");
        System.out.println("4. Con Discapacidad");

        int opcPerfil;
        do {
            opcPerfil = leerEntero("Ingrese un número del menú (1-4): ");
            if (opcPerfil < 1 || opcPerfil > 4) {
                System.out.println("Opción fuera del menú. Ingrese un número válido (1-4).");
            }
        } while (opcPerfil < 1 || opcPerfil > 4);

        String tipoPerfil = "General";
        double subsidio = 0.0;
        if (opcPerfil == 2) {
            tipoPerfil = "Estudiante";
            subsidio = 0.50;
        } else if (opcPerfil == 3) {
            tipoPerfil = "Adulto Mayor";
            subsidio = 0.50;
        } else if (opcPerfil == 4) {
            tipoPerfil = "Con Discapacidad";
            subsidio = 0.7143;
        }

        PerfilTarifario perfil = new PerfilTarifario(tipoPerfil, subsidio, true);

        String resultado = gestorUsuarios.registrarPasajero(nombre, correo, pass, sector, perfil);
        System.out.println(resultado);
    }

    private void procesarLogin() {
        System.out.print("\n**Inicio de Sesion**");
        System.out.print("\nCorreo: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Usuario usuario = gestorUsuarios.autenticar(correo, pass);
        if (usuario == null) {
            System.out.println("\nError: usuario o contraseña incorrectos.");
            return;
        }

        System.out.println("\nBienvenido, " + usuario.getNombreCompleto() + "!");
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
            System.out.println("1. Consultar catálogo de rutas disponibles");
            System.out.println("2. Consultar tarifa según mi perfil");
            System.out.println("3. Planificar itinerario óptimo y calcular ETA");
            System.out.println("4. Guardar ruta favorita");
            System.out.println("5. Consultar mis rutas favoritas");
            System.out.println("6. Cerrar sesión");
            opc = leerEntero("\nSeleccione una opción (1-6): ");

            switch (opc) {
                case 1:
                    System.out.println("\n** CATÁLOGO DE RUTAS DMQ **");
                    System.out.println(gestorRutas.listarRutasActivas());
                    break;
                case 2:
                    double tarifaBase = leerDecimal("\nIngrese la tarifa base del pasaje ($): ");
                    double tarifaFinal = pasajero.consultarTarifa(tarifaBase);
                    System.out.println("Perfil: " + pasajero.getPerfilTarifario().getTipoPerfil()
                            + " | Tarifa a pagar: $" + String.format("%.2f", tarifaFinal));
                    break;
                case 3:
                    System.out.print("\nIngrese código de línea de bus (ej. C1): ");
                    String cod = sc.nextLine();
                    Ruta ruta = gestorRutas.buscarRutaPorCodigo(cod);
                    if (ruta != null && ruta.getEstado().startsWith("OPERATIVA")) {
                        System.out.println("\nCriterio de optimización:");
                        System.out.println("1. Más rápido");
                        System.out.println("2. Menos transbordos");
                        System.out.println("3. Menos caminata");

                        int opcCriterio;
                        do {
                            opcCriterio = leerEntero("Ingrese un número del menú (1-3): ");
                            if (opcCriterio < 1 || opcCriterio > 3) {
                                System.out.println("Opción fuera del menú. Ingrese un número válido (1-3).");
                            }
                        } while (opcCriterio < 1 || opcCriterio > 3);

                        String criterio = "Más rápido";
                        if (opcCriterio == 2) {
                            criterio = "Menos transbordos";
                        } else if (opcCriterio == 3) {
                            criterio = "Menos caminata";
                        }

                        ultimoItinerario = gestorPlanificacion.generarItinerarioOptimo(ruta, criterio);
                        double costoFinal = ultimoItinerario.calcularCostoTotal(pasajero.getPerfilTarifario());
                        double etaMin = gestorPlanificacion.calcularETA(ruta, 30.0);

                        System.out.println("\n** ITINERARIO GENERADO **");
                        System.out.println("Origen: " + ultimoItinerario.getOrigen() + " -> Destino: " + ultimoItinerario.getDestino());
                        System.out.println("Criterio aplicado: " + criterio);
                        System.out.println("Duración estimada: " + ultimoItinerario.getDuracionEstimadaMin() + " min");
                        System.out.println("Costo total con perfil (" + pasajero.getPerfilTarifario().getTipoPerfil() + "): $" + String.format("%.2f", costoFinal));
                        System.out.println("ETA estimado para llegada: " + String.format("%.2f", etaMin) + " min");
                    } else {
                        System.out.println("No se encontraron rutas directas ni combinaciones disponibles.");
                    }
                    break;
                case 4:
                    if (ultimoItinerario == null) {
                        System.out.println("Primero planifique un itinerario en la opción 3 para poder guardarlo como favorito.");
                    } else {
                        System.out.print("\nIngrese el nombre personalizado para su ruta favorita: ");
                        String nombreFav = sc.nextLine();
                        RutaFavorita rf = new RutaFavorita(nombreFav, "2026-11-22");
                        rf.agregarItinerario(ultimoItinerario);
                        pasajero.guardarRutaFavorita(rf);
                        System.out.println("Ruta guardada en la cuenta del usuario exitosamente. Total favoritas: "
                                + pasajero.contarRutasFavoritas());
                    }
                    break;
                case 5:
                    consultarRutasFavoritasUI(pasajero);
                    break;
                case 6:
                    System.out.println("Cerrando sesión de pasajero...");
                    break;
                default:
                    System.out.println("Opción fuera del menú. Ingrese un número válido (1-6).");
                    break;
            }
        } while (opc != 6);
    }

    private void consultarRutasFavoritasUI(Pasajero pasajero) {
        System.out.println("\n** MIS RUTAS FAVORITAS (" + pasajero.contarRutasFavoritas() + ") **");
        if (pasajero.getRutasFavoritas().isEmpty()) {
            System.out.println("Aún no tienes rutas favoritas guardadas.");
            return;
        }

        for (int i = 0; i < pasajero.getRutasFavoritas().size(); i++) {
            RutaFavorita rf = pasajero.getRutasFavoritas().get(i);
            System.out.println((i + 1) + ". " + rf.getNombrePersonalizado() + " (Guardada: " + rf.getFechaGuardado() + ")");
            for (Itinerario it : pasajero.consultarItinerarios(rf)) {
                System.out.println("   - " + it.getOrigen() + " -> " + it.getDestino()
                        + " | Criterio: " + it.getCriterioOptimizacion()
                        + " | Duración: " + it.getDuracionEstimadaMin() + " min"
                        + " | Costo perfil: $" + String.format("%.2f", it.calcularCostoTotal(pasajero.getPerfilTarifario())));
            }
        }

        RutaFavorita ultima = pasajero.consultarUltimaRutaFavorita();
        if (ultima != null) {
            System.out.println("Última ruta favorita agregada: " + ultima.getNombrePersonalizado());
        }
    }

    private void menuAdministrador(Administrador admin) {
        int opc;
        do {
            System.out.println("\n** MENÚ ADMINISTRADOR **");
            System.out.println("1. Registrar nueva ruta en el catálogo");
            System.out.println("2. Actualizar ruta existente");
            System.out.println("3. Suspender ruta del catálogo");
            System.out.println("4. Listar todas las rutas");
            System.out.println("5. Cerrar sesión");
            opc = leerEntero("\nSeleccione una opción (1-5): ");

            switch (opc) {
                case 1:
                    System.out.print("\nCódigo de línea (ej. C2): ");
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
                    System.out.print("\nIngrese el código de la ruta a actualizar: ");
                    String codAct = sc.nextLine();
                    Ruta rutaAct = gestorRutas.buscarRutaPorCodigo(codAct);
                    if (rutaAct != null) {
                        System.out.print("Nuevo nombre de ruta (o Enter para mantener): ");
                        String nuevoNom = sc.nextLine().trim();
                        if (!nuevoNom.isEmpty() && !nuevoNom.equals(".")) {
                            rutaAct.setNombre(nuevoNom);
                        }

                        System.out.print("Nuevo sector de origen (o Enter para mantener): ");
                        String nuevoOrig = sc.nextLine().trim();
                        if (!nuevoOrig.isEmpty() && !nuevoOrig.equals(".")) {
                            rutaAct.setSectorOrigen(nuevoOrig);
                        }

                        System.out.print("Nuevo sector de destino (o Enter para mantener): ");
                        String nuevoDest = sc.nextLine().trim();
                        if (!nuevoDest.isEmpty() && !nuevoDest.equals(".")) {
                            rutaAct.setSectorDestino(nuevoDest);
                        }

                        System.out.println("\nNuevo estado de la ruta:");
                        System.out.println("0. Mantener estado actual (" + rutaAct.getEstado() + ")");
                        System.out.println("1. OPERATIVA");
                        System.out.println("2. DESVIADA");
                        System.out.println("3. SUSPENDIDA");

                        int opcEstado;
                        do {
                            opcEstado = leerEntero("Ingrese un número del menú (0-3): ");
                            if (opcEstado < 0 || opcEstado > 3) {
                                System.out.println("Opción fuera del menú. Ingrese un número válido (0-3).");
                            }
                        } while (opcEstado < 0 || opcEstado > 3);

                        if (opcEstado == 1) {
                            rutaAct.actualizarEstado("OPERATIVA");
                        } else if (opcEstado == 2) {
                            rutaAct.actualizarEstado("DESVIADA");
                        } else if (opcEstado == 3) {
                            rutaAct.actualizarEstado("SUSPENDIDA");
                        }

                        admin.actualizarRuta(rutaAct);
                        System.out.println("Trazado de ruta actualizado.");
                    } else {
                        System.out.println("Ruta no encontrada.");
                    }
                    break;
                case 3:
                    System.out.print("\nIngrese el código de la ruta a suspender: ");
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
                case 4:
                    System.out.println("\n** LISTADO GENERAL DE RUTAS **");
                    System.out.println(gestorRutas.listarTodasLasRutas());
                    break;
                case 5:
                    System.out.println("Cerrando sesión de administrador...");
                    break;
                default:
                    System.out.println("Opción fuera del menú. Ingrese un número válido (1-5).");
                    break;
            }
        } while (opc != 5);
    }
}