package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Order;
import controller.Dao.OrderDao;

public class OrderServices {
    private OrderDao obj;
    public OrderServices(){
        obj = new OrderDao();
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
    
    public LinkedList<Order> listAll(){
        return obj.getlistAll();

    }

    public Order getOrder(){
        return obj.getOrder();
    }

    public void setOrder( Order order){
        obj.setOrder(order);
    }

    public Order get(Integer id) throws Exception {
        return obj.get(id);
    }

        public LinkedList<Order> listByVehicleId(int id) {
        return obj.listByVehicleId(id);
    }
}
