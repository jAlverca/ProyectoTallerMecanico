package models;

public class Servicio {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Float pu;
    private Float pt;
    private String codigo;

    public Servicio() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPu() {
        return this.pu;
    }

    public void setPu(Float pu) {
        this.pu = pu;
    }

    public Float getPt() {
        return this.pt;
    }

    public void setPt(Float pt) {
        this.pt = pt;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
