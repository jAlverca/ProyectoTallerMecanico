package controller.Dao.services;
import controller.tda.list.LinkedList;
import models.Order;
import models.OrderDetalle;
import models.Servicio;
import models.Vehiculo;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import controller.Dao.OrderDao;

public class OrderServices {
    private OrderDao obj;
    public OrderServices(){
        obj = new OrderDao();
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
    
    public LinkedList<Order> listAll(){
        return obj.getlistAll();

    }

    public Order getOrder(){
        return obj.getOrder();
    }

    public void setOrder( Order lista){
        obj.setOrder(lista);
    }

    public Order get(Integer id) throws Exception {
        return obj.get(id);
    }




public Object[] listShowAll() throws Exception {
    if (obj == null || obj.getlistAll() == null || obj.getlistAll().isEmpty()) {
        return new Object[]{};
    }
    
    Order[] lista = obj.getlistAll().toArray();
    Object[] respuesta = new Object[lista.length];
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    for (int i = 0; i < lista.length; i++) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", lista[i].getId());
        map.put("fecha", lista[i].getFecha() != null ? format.format(lista[i].getFecha()) : "Fecha no disponible");
        map.put("NroOrder", lista[i].getNroOrder());
        map.put("subtotal", lista[i].getSubtotal());
        map.put("iva", lista[i].getIva());
        map.put("total", lista[i].getTotal());
        
        VehiculoServices vehiculoServices = new VehiculoServices();
        Object vehiculo = vehiculoServices.buscar_persona(lista[i].getIdVehiculo());
        if (vehiculo instanceof LinkedList<?> && !((LinkedList<?>) vehiculo).isEmpty()) {
            HashMap<String, Object> vehiculoData = new HashMap<>();
            Object lastVehiculo = ((LinkedList<?>) vehiculo).getLast();
            vehiculoData.put("vehiculo", lastVehiculo);
            
            PersonaServices personaServices = new PersonaServices();
            Object persona = personaServices.get(((Vehiculo) lastVehiculo).getIdPersona());
            vehiculoData.put("persona", persona);
            
            map.put("vehiculo", vehiculoData);
        } else {
            map.put("vehiculo", "Vehículo no encontrado");
        }
        
        OrderDetalleServices ods = new OrderDetalleServices();
        LinkedList<OrderDetalle> listaOrder = ods.searchOrder(lista[i].getId());
        if (listaOrder == null) {
            listaOrder = new LinkedList<>();
        }
        
        HashMap<String, Object>[] detalle = new HashMap[listaOrder.getSize()];
        for (int j = 0; j < listaOrder.getSize(); j++) {
            HashMap<String, Object> map_aux = new HashMap<>();
            map_aux.put("cant", listaOrder.get(j).getCant());
            map_aux.put("pu", listaOrder.get(j).getPu());
            map_aux.put("pt", listaOrder.get(j).getPt());
            
            ServicioServices servicioServices = new ServicioServices();
            Servicio servicio = servicioServices.get(listaOrder.get(j).getIdService());
            map_aux.put("servicio", servicio);
            
            detalle[j] = map_aux;
        }
        map.put("descripcion", detalle);
        respuesta[i] = map;
    }
    return respuesta;
}
                        
                       
    

public Object getOrderAll(Integer id) throws Exception {
    Order lista = get(id);
    if (lista != null && lista.getId() != null) {
        HashMap<String, Object> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        map.put("id", lista.getId());
        map.put("fecha", lista.getFecha() != null ? format.format(lista.getFecha()) : "Fecha no disponible");
        map.put("NroOrder", lista.getNroOrder());
        map.put("subtotal", lista.getSubtotal());
        map.put("iva", lista.getIva());
        map.put("total", lista.getTotal());

        VehiculoServices vehiculoServices = new VehiculoServices();
        Object vehiculo = vehiculoServices.buscar_persona(lista.getIdVehiculo());
        if (vehiculo instanceof LinkedList<?> && !((LinkedList<?>) vehiculo).isEmpty()) {
            HashMap<String, Object> vehiculoData = new HashMap<>();
            Object lastVehiculo = ((LinkedList<?>) vehiculo).getLast();
            vehiculoData.put("vehiculo", lastVehiculo);
            
            PersonaServices personaServices = new PersonaServices();
            Object persona = personaServices.get(((Vehiculo) lastVehiculo).getIdPersona());
            vehiculoData.put("persona", persona);
            
            map.put("vehiculo", vehiculoData);
        } else {
            map.put("vehiculo", "Vehículo no encontrado");
        }

        OrderDetalleServices ods = new OrderDetalleServices();
        LinkedList<OrderDetalle> listaOrder = ods.searchOrder(lista.getId());
        HashMap<String, Object>[] detalle = new HashMap[listaOrder.getSize()];
        for (int j = 0; j < listaOrder.getSize(); j++) {
            HashMap<String, Object> mapOrder = new HashMap<>();
            OrderDetalle listaDetalle = listaOrder.get(j);
            if (listaDetalle != null) {
                mapOrder.put("cant", listaDetalle.getCant());
                mapOrder.put("pu", listaDetalle.getPu());
                mapOrder.put("pt", listaDetalle.getPt());
                
                ServicioServices servicioServices = new ServicioServices();
                Servicio servicio = servicioServices.get(listaDetalle.getIdService());
                if (servicio != null) {
                    mapOrder.put("servicio", servicio);
                } else {
                    System.out.println("Error: Servicio no encontrado para el ID " + listaDetalle.getIdService());
                }
                detalle[j] = mapOrder;
            } else {
                System.out.println("Error: OrderDetalle es nulo en la posición " + j);
            }
        }
        map.put("descripcion", detalle);
        return map;
    }
    return new Object[]{};
} 
    
}
