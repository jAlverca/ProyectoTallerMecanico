package models;

import java.util.HashMap;

public class Persona{
    
    private Integer id;    
    private String apellidos;
    private String nombres;
    private String direccion;
    private String telefono;
    private String identificacion;
    private TipoIdentificacion tipo;

    public Persona() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public TipoIdentificacion getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoIdentificacion tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        return apellidos+" "+nombres;
    };

        public HashMap toHashMap() {
        HashMap map = new HashMap();
        map.put("id", id);
        map.put("nombres", nombres);
        map.put("apellidos", apellidos);
        map.put("identificacion", identificacion);
        map.put("telefono", telefono);
        map.put("direccion", direccion);
        map.put("tipo", tipo.toString());
        return map;
    }

}
