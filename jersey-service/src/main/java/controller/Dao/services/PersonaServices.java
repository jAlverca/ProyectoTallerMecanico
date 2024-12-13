package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Persona;
import models.TipoIdentificacion;
import controller.Dao.PersonaDao;

public class PersonaServices {
    private PersonaDao obj;
    public PersonaServices(){
        obj = new PersonaDao();
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
    
    public void merge(Persona persona, Integer id) throws Exception {
        obj.merge(persona, id);
    }

    public LinkedList<Persona> listAll(){
        return obj.getlistAll();

    }

    public Persona getPersona(){
        return obj.getPersona();
    }

    public void setPersona( Persona persona){
        obj.setPersona(persona);
    }

    public Persona get(Integer id) throws Exception {
        return obj.get(id);
    }

    public TipoIdentificacion getTipoIdentificacion(String tipo) {
        return obj.getTipoIdentificacion(tipo);
    }

    public TipoIdentificacion[] getTipos() {
        return obj.getTipos();
    }

    public LinkedList<Persona> busquedaLinealBinaria(String atributo, String value) throws Exception {
        return obj.busquedaLinealBinaria(atributo, value);
    }

    public LinkedList<Persona> mergeSort(Integer type_order, String atributo) throws Exception {
        LinkedList<Persona> lista_ordenada = obj.mergeSort(type_order, atributo);
        return lista_ordenada;
    }
}
