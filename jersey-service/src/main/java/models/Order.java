package models;

import java.util.Date;

public class Order {
    private Integer id;
    private Date fecha;
    private Float subtotal;
    private Float iva;
    private Float total;
    private String nroOrder;
    private Integer idTecnico;
    private Integer idVehiculo;

    public Order() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getIva() {
        return this.iva;
    }

    public void setIva(Float iva) {
        this.iva = iva;
    }

    public Float getTotal() {
        return this.total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getNroOrder() {
        return this.nroOrder;
    }

    public void setNroOrder(String nroOrder) {
        this.nroOrder = nroOrder;
    }

    public Integer getIdTecnico() {
        return this.idTecnico;
    }

    public void setIdTecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    public Integer getIdVehiculo() {
        return this.idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

}