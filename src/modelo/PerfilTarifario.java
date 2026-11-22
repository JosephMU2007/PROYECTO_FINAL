package modelo;

public class PerfilTarifario {
    private String tipoPerfil;
    private double porcentajeSubsidio;
    private boolean documentacionValidada;

    public PerfilTarifario(String tipoPerfil, double porcentajeSubsidio, boolean documentacionValidada) {
        this.tipoPerfil = tipoPerfil;
        this.porcentajeSubsidio = porcentajeSubsidio;
        this.documentacionValidada = documentacionValidada;
    }

    public boolean validarRequisitos() {
        return documentacionValidada && porcentajeSubsidio >= 0 && porcentajeSubsidio <= 1.0;
    }

    public double calcularTarifa(double tarifaBase) {
        if (validarRequisitos()) {
            return tarifaBase * (1.0 - porcentajeSubsidio);
        }
        return tarifaBase;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public double getPorcentajeSubsidio() {
        return porcentajeSubsidio;
    }

    public boolean isDocumentacionValidada() {
        return documentacionValidada;
    }
}