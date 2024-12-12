package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.OrderDetalle;
import controller.Dao.OrderDetalleDao;

public class OrderDetalleServices {
    private OrderDetalleDao obj;
    public OrderDetalleServices(){
        obj = new OrderDetalleDao();
    }
    public Boolean save() throws Exception{
        return obj.save();
    }
    public Boolean update() throws Exception{
        return obj.update();
    }
    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id); 
    }
    
    public LinkedList<OrderDetalle> listAll(){
        return obj.getlistAll();

    }

    public OrderDetalle getOrderDetalle(){
        return obj.getOrderDetalle();
    }

    public void setPersona( OrderDetalle orderDetalle){
        obj.setOrderDetalle(orderDetalle);
    }

    public OrderDetalle get(Integer id) throws Exception {
        return obj.get(id);
    }
}
