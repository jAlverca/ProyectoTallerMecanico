package controller.Dao;

import models.Vehiculo;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class VehiculoDao extends AdapterDao<Vehiculo> {
    private Vehiculo vehiculo;
    private LinkedList<Vehiculo> listAll;

    public VehiculoDao() {
        super(Vehiculo.class);
        this.listAll = new LinkedList<>();
        }

    public Vehiculo getVehiculo() {
        if (vehiculo == null) {
            vehiculo = new Vehiculo();
        }
        return this.vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LinkedList<Vehiculo> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        vehiculo.setId(id);
        this.persist(this.vehiculo);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getVehiculo(), getVehiculo().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Vehiculo> list = getlistAll();
        Vehiculo vehiculo = get(id);
        if (vehiculo != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Vehiculo con id " + id + " no encontrada.");
            return false;
        }
    }

    public LinkedList<Vehiculo> getIdPersonaVehiculo(int personId) {
        LinkedList<Vehiculo> result = new LinkedList<>();
        for (Vehiculo v : getlistAll().toArray()) {
            if (v.getIdPersona() == personId) {
                result.add(v);
            }
        }
        return result;
    }

    public LinkedList<Vehiculo> listByPersonId(int personId) {
        VehiculoDao dao = new VehiculoDao();
        return dao.getIdPersonaVehiculo(personId);
    }

    public LinkedList<Vehiculo> buscar_persona(Integer id){
        LinkedList<Vehiculo> lista = new LinkedList<>();
        LinkedList<Vehiculo> listaVehiculos = listAll();
        if(!listaVehiculos.isEmpty()){
            Vehiculo[] aux = listaVehiculos.toArray();
            for (int i = 0; i < aux.length; i++) {
                if(aux[i].getIdPersona().intValue() == id.intValue()){
                    lista.add(aux[i]);
                }
            }
        }
        return lista;
    }

    public Object getVehiculoPlaca(String string) {
        LinkedList<Vehiculo> list = getlistAll();
        if (!list.isEmpty()) {
            Vehiculo[] aux = list.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getPlaca().equals(string)) {
                    return aux[i];
                }
            }
        }
        return null;
    }
}