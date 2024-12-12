package controller.Dao;

import models.OrderDetalle;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class OrderDetalleDao extends AdapterDao<OrderDetalle> {
    private OrderDetalle orderDetalle;
    private LinkedList<OrderDetalle> listAll;

    public OrderDetalleDao() {
        super(OrderDetalle.class);
        this.listAll = new LinkedList<>();
    }

    public OrderDetalle getOrderDetalle() {
        if (orderDetalle == null) {
            orderDetalle = new OrderDetalle();
        }
        return this.orderDetalle;
    }

    public void setOrderDetalle(OrderDetalle orderDetalle) {
        this.orderDetalle = orderDetalle;
    }

    public LinkedList<OrderDetalle> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        orderDetalle.setId(id);
        this.persist(this.orderDetalle);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getOrderDetalle(), getOrderDetalle().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<OrderDetalle> list = getlistAll();
        OrderDetalle orderDetalle = get(id);
        if (orderDetalle != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("OrderDetalle con id " + id + " no encontrada.");
            return false;
        }
    }

}