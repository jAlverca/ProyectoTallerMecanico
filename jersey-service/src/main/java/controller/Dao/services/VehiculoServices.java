package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Vehiculo;
import controller.Dao.VehiculoDao;

public class VehiculoServices {
    private VehiculoDao obj;
    public VehiculoServices(){
        obj = new VehiculoDao();
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
    
    public LinkedList<Vehiculo> listAll(){
        return obj.getlistAll();

    }

    public Vehiculo getVehiculo(){
        return obj.getVehiculo();
    }

    public void setVehiculo( Vehiculo vehiculo){
        obj.setVehiculo(vehiculo);
    }

    public Vehiculo get(Integer id) throws Exception {
        return obj.get(id);
    }

    public LinkedList<Vehiculo> listByPersonId(int id) {
        return obj.listByPersonId(id);
    }

    public LinkedList<Vehiculo> buscar_persona(Integer id) {
        return obj.buscar_persona(id);
    }
    public Object getVehiculoPlaca(String string) {
        return obj.getVehiculoPlaca(string);
    }

}
