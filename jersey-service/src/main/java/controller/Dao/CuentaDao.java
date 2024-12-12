package controller.Dao;

import models.Cuenta;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class CuentaDao extends AdapterDao<Cuenta> {
    private Cuenta cuenta;
    private LinkedList<Cuenta> listAll;

    public CuentaDao() {
        super(Cuenta.class);
        this.listAll = new LinkedList<>();
    }

    public Cuenta getCuenta() {
        if (cuenta == null) {
            cuenta = new Cuenta();
        }
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public LinkedList<Cuenta> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        cuenta.setId(id);
        this.persist(this.cuenta);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getCuenta(), getCuenta().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Cuenta> list = getlistAll();
        Cuenta cuenta = get(id);
        if (cuenta != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Cuenta con id " + id + " no encontrada.");
            return false;
        }
    }

}