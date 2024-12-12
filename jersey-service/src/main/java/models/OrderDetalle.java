package models;

public class OrderDetalle {
    private Integer id;
    private Integer cant;
    private Float pu;
    private Float pt;
    private Integer idOrder;
    private Integer IdService;
    private Integer idComponente;

    public OrderDetalle() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCant() {
        return this.cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
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

    public Integer getIdOrder() {
        return this.idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdService() {
        return this.IdService;
    }

    public void setIdService(Integer IdService) {
        this.IdService = IdService;
    }

    public Integer getIdComponente() {
        return this.idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

}
