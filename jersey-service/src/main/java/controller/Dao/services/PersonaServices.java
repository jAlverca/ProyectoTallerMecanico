package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Persona;
import models.TipoIdentificacion;
import models.Vehiculo;

import java.util.HashMap;

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

    public Persona busquedaBinaria1(String atributo, String value) throws Exception {
        return obj.busquedaBinaria1(atributo, value);
    }

    public Persona buscarIdentificacion(String texto){
        return obj.buscarIdentificacion(texto);
    }

    public HashMap buscar_identificacion_vehiculo(String texto) throws Exception{
        Persona persona = obj.busquedaBinaria(texto);
        HashMap mapa = new HashMap<>();
        if(persona != null){
            try {
                mapa.put("id", persona.getId());
                mapa.put("nombre", persona.getNombres());
                mapa.put("apellido", persona.getApellidos());
                mapa.put("direccion", persona.getDireccion());
                mapa.put("telefono", persona.getTelefono());
                mapa.put("identificacion", persona.getIdentificacion());
                mapa.put("tipo", persona.getTipo().toString());
                VehiculoServices ve = new VehiculoServices();
                LinkedList<Vehiculo> lista = ve.buscar_persona(persona.getId());
                if(!lista.isEmpty()){
                    Vehiculo[] listaV = lista.toArray();
                    HashMap[] detalles = new HashMap[listaV.length];
                    for(int j = 0; j < listaV.length; j++){
                        HashMap map_aux = new HashMap<>();
                        map_aux.put("id", listaV[j].getId());
                        map_aux.put("placa", listaV[j].getPlaca());
                        map_aux.put("marca", listaV[j].getMarca());
                        map_aux.put("modelo", listaV[j].getModelo());
                        map_aux.put("color", listaV[j].getColor());
                        map_aux.put("descripcion", listaV[j].getDescripcion());
                        map_aux.put("estado", listaV[j].getEstado());
                        detalles[j] = map_aux;
                    }
                    mapa.put("vehiculos", detalles);
                }else{
                    mapa.put("vehiculos", "No tiene vehiculos registrados");
                }

            } catch (Exception e) {

                return new HashMap<>();
            }
          
        }
        return mapa;
    }
    
}
