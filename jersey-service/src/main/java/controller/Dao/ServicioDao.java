package controller.Dao;

import models.Servicio;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class ServicioDao extends AdapterDao<Servicio> {
    private Servicio servicio;
    private LinkedList<Servicio> listAll;

    public ServicioDao() {
        super(Servicio.class);
        this.listAll = new LinkedList<>();
    }

    public Servicio getServicio() {
        if (servicio == null) {
            servicio = new Servicio();
        }
        return this.servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public LinkedList<Servicio> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        servicio.setId(id);
        this.persist(this.servicio);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getServicio(), getServicio().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Servicio> list = getlistAll();
        Servicio servicio = get(id);
        if (servicio != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Servicio con id " + id + " no encontrada.");
            return false;
        }
    }

}