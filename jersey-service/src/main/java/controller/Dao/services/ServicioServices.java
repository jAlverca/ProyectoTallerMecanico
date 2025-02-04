package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Servicio;
import controller.Dao.ServicioDao;

public class ServicioServices {
    private ServicioDao obj;
    public ServicioServices(){
        obj = new ServicioDao();
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
    
    public LinkedList<Servicio> listAll(){
        return obj.getlistAll();

    }

    public Servicio getServicio(){
        return obj.getServicio();
    }

    public void setServicio( Servicio servicio){
        obj.setServicio(servicio);
    }

    public Servicio get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Servicio getServicioCodigo(String code){
        LinkedList<Servicio> list = obj.getlistAll();
        Servicio servicio = null;
        if(list.isEmpty()){
            return servicio;
        } else{
            Servicio[] servicios = list.toArray();
            for(int i = 0; i < servicios.length; i++){
                if(servicios[i].getCodigo().equals(code)){
                    servicio = servicios[i];
                    break;
                }
            }
        }
        return servicio;
    }
}
