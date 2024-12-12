package controller.Dao;

import models.Order;
import models.Order;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class OrderDao extends AdapterDao<Order> {
    private Order order;
    private LinkedList<Order> listAll;

    public OrderDao() {
        super(Order.class);
        this.listAll = new LinkedList<>();
    }

    public Order getOrder() {
        if (order == null) {
            order = new Order();
        }
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LinkedList<Order> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        order.setId(id);
        this.persist(this.order);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getOrder(), getOrder().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Order> list = getlistAll();
        Order order = get(id);
        if (order != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Order con id " + id + " no encontrada.");
            return false;
        }
    }

        public LinkedList<Order> getIdVehicleOrder(int vehicleId) {
        LinkedList<Order> result = new LinkedList<>();
        for (Order v : getlistAll().toArray()) {
            if (v.getIdVehiculo() == vehicleId) {
                result.add(v);
            }
        }
        return result;
    }

    public LinkedList<Order> listByVehicleId(int vehicleId) {
        OrderDao dao = new OrderDao();
        return dao.getIdVehicleOrder(vehicleId);
    }

}