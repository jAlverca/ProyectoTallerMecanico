package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Componente;
import controller.Dao.ComponenteDao;

public class ComponenteServices {
    private ComponenteDao obj;
    public ComponenteServices(){
        obj = new ComponenteDao();
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
    
    public LinkedList<Componente> listAll(){
        return obj.getlistAll();

    }

    public Componente getComponente(){
        return obj.getComponente();
    }

    public void setPersona( Componente componente){
        obj.setComponente(componente);
    }

    public Componente get(Integer id) throws Exception {
        return obj.get(id);
    }
}
