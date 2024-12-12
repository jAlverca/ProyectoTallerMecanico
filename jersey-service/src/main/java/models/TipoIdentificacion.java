package models;

public enum TipoIdentificacion {
    CEDULA("CEDULA"),
    RUC("RUC"), 
    PASAPORTE("PASAPORTE");
    
    private String name;

    TipoIdentificacion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}