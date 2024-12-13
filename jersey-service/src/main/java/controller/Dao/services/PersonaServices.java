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

    @SuppressWarnings("rawtypes")
    public LinkedList order(Integer type_order, String atributo) {
        return obj.order(type_order, atributo);
    }

    public LinkedList<Persona> buscar_apellido(String texto) {
        return obj.buscar_apellidos(texto);
    }

    public LinkedList<Persona> buscar_apellidos(String texto) {
        return obj.buscar_apellidos(texto);
    }
    public Persona buscar_identificacion(String texto) {
        return obj.buscar_identificacion(texto);
    }
    public TipoIdentificacion[] getTipos() {
        return obj.getTipos();
    }
}
