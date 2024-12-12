package controller.Dao;

import models.Componente;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class ComponenteDao extends AdapterDao<Componente> {
    private Componente componente;
    private LinkedList<Componente> listAll;

    public ComponenteDao() {
        super(Componente.class);
        this.listAll = new LinkedList<>();
    }

    public Componente getComponente() {
        if (componente == null) {
            componente = new Componente();
        }
        return this.componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public LinkedList<Componente> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        componente.setId(id);
        this.persist(this.componente);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getComponente(), getComponente().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Componente> list = getlistAll();
        Componente componente = get(id);
        if (componente != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Componente con id " + id + " no encontrada.");
            return false;
        }
    }

}