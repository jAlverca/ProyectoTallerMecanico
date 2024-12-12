package controller.Dao;

import models.Tecnico;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class TecnicoDao extends AdapterDao<Tecnico> {
    private Tecnico tecnico;
    private LinkedList<Tecnico> listAll;

    public TecnicoDao() {
        super(Tecnico.class);
        this.listAll = new LinkedList<>();
    }

    public Tecnico getTecnico() {
        if (tecnico == null) {
            tecnico = new Tecnico();
        }
        return this.tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LinkedList<Tecnico> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        tecnico.setId(id);
        this.persist(this.tecnico);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getTecnico(), getTecnico().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Tecnico> list = getlistAll();
        Tecnico tecnico = get(id);
        if (tecnico != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Tecnico con id " + id + " no encontrada.");
            return false;
        }
    }

}