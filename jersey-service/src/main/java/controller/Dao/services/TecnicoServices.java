package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Tecnico;
import controller.Dao.TecnicoDao;

public class TecnicoServices {
    private TecnicoDao obj;
    public TecnicoServices(){
        obj = new TecnicoDao();
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
    
    public LinkedList<Tecnico> listAll(){
        return obj.getlistAll();

    }

    public Tecnico getTecnico(){
        return obj.getTecnico();
    }

    public void setPersona( Tecnico tecnico){
        obj.setTecnico(tecnico);
    }

    public Tecnico get(Integer id) throws Exception {
        return obj.get(id);
    }
}
