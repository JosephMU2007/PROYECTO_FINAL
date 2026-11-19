package negocio;
import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class GestorRutas {
    private List<Ruta> rutas;

    public GestorRutas() {
        this.rutas = new ArrayList<>();
        cargarRutasIniciales();
    }

    private void cargarRutasIniciales() {
        Ruta r1 = new Ruta("C1", "Corredor Central Norte", "La Ofelia", "Quitumbe", "OPERATIVA");
        Parada p1 = new Parada(101, "Terminal La Ofelia", 0.0, 15.0);
        Parada p2 = new Parada(102, "Estación La Carolina", 0.0, 8.5);
        Parada p3 = new Parada(103, "Terminal Quitumbe", 0.0, 0.0);
        r1.agregarParada(p1);
        r1.agregarParada(p2);
        r1.agregarParada(p3);

        Itinerario it1 = new Itinerario(1, "La Ofelia", "Quitumbe", "Más rápido", "08:00", 0.35);
        it1.agregarTramo(new Tramo(1, "Troncal", 15.0, 42.0));
        r1.agregarItinerario(it1);

        rutas.add(r1);
    }

    public boolean agregarRuta(Ruta ruta) {
        if (ruta != null && buscarRutaPorCodigo(ruta.getCodigoLinea()) == null) {
            rutas.add(ruta);
            return true;
        }
        return false;
    }

    public Ruta buscarRutaPorCodigo(String codigo) {
        for (Ruta r : rutas) {
            if (r.getCodigoLinea().equalsIgnoreCase(codigo)) {
                return r;
            }
        }
        return null;
    }

    public String listarRutas() {
        if (rutas.isEmpty()) return "No existen rutas registradas.";
        StringBuilder sb = new StringBuilder();
        for (Ruta r : rutas) {
            sb.append("[").append(r.getCodigoLinea()).append("] ")
                    .append(r.getNombre()).append(" (").append(r.getSectorOrigen()).append(" -> ").append(r.getSectorDestino()).append(")")
                    .append(" | Estado: ").append(r.getEstado())
                    .append(" | Distancia: ").append(String.format("%.2f", r.calcularDistanciaTotal())).append(" km\n");
        }
        return sb.toString();
    }
}
