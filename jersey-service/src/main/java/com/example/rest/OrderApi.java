package com.example.rest;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import controller.Dao.services.OrderServices;
import controller.Dao.services.ServicioServices;
import controller.Dao.services.OrderDetalleServices;
import controller.Dao.services.VehiculoServices;
import controller.tda.list.LinkedList;

@Path("order")
public class OrderApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrder() {
        HashMap map = new HashMap<>();
        OrderServices ps = new OrderServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/listAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListAll() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            OrderServices ps = new OrderServices();
            Object[] resultado = ps.listShowAll();
            if (resultado == null) {
                resultado = new Object[]{};
            }
            map.put("msg", "Ok");
            map.put("data", resultado);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", "Error al obtener las órdenes: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        OrderServices ps = new OrderServices();
        try {
            ps.setOrder(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "OK");
        map.put("data", ps.getOrder());
        return Response.ok(map).build();
    }

    @Path("/getAll/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("id") Integer id){
        HashMap map = new HashMap<>();
        try {
            OrderServices ps = new OrderServices();
            map.put("msg", "OK");
            map.put("data", ps.getOrderAll(id));
            if (ps.listAll().isEmpty()) {
                map.put("data", new HashMap<>());
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", "Error al obtener las órdenes: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map){
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        System.out.println("Datos recibidos: " + a); // Agrega este log para verificar los datos recibidos
        try {
            if(map.get("vehiculo") != null){
                VehiculoServices personaServices = new VehiculoServices();
                personaServices.setVehiculo(personaServices.get(Integer.parseInt(map.get("vehiculo").toString())));
                if(personaServices.getVehiculo().getId() != null){
                    OrderServices ps = new OrderServices();
                    ps.getOrder().setFecha(new Date());
                    ps.getOrder().setIdVehiculo(personaServices.getVehiculo().getId());
                    ps.getOrder().setNroOrder(UUID.randomUUID().toString());
                    if(ps.getOrder().getNroOrder() == null){
                        res.put("msg", "Error");
                        res.put("data", "Error al registrar la orden: NroOrder es nulo");
                        return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                    }
                    ps.getOrder().setIva(map.get("iva") != null ? Float.parseFloat(map.get("iva").toString()) : 0.0f);
                    ps.getOrder().setSubtotal(map.get("subtotal") != null ? Float.parseFloat(map.get("subtotal").toString()) : 0.0f);
                    ps.getOrder().setTotal(map.get("total") != null ? Float.parseFloat(map.get("total").toString()) : 0.0f);
                    
                    List<HashMap> aux = (List<HashMap>) map.get("descripcion");
                    LinkedList<OrderDetalleServices> lista_osd = new LinkedList<>();
                    if (aux != null) {
                        for(HashMap mapa : aux){
                            ServicioServices ss = new ServicioServices();
                            OrderDetalleServices ods = new OrderDetalleServices();
                            ods.getOrderDetalle().setCant(mapa.get("cant") != null ? Integer.parseInt(mapa.get("cant").toString()) : 0);
                            ods.getOrderDetalle().setPu(mapa.get("pu") != null ? Float.parseFloat(mapa.get("pu").toString()) : 0.0f);
                            ods.getOrderDetalle().setPt(mapa.get("pt") != null ? Float.parseFloat(mapa.get("pt").toString()) : 0.0f);
                            try {
                                ss.setServicio(ss.getServicioCodigo(mapa.get("service").toString())); // Usa el código del servicio
                                if(ss.getServicio().getId() != null){
                                    ods.getOrderDetalle().setIdService(ss.getServicio().getId());
                                    lista_osd.add(ods);
                                } else {
                                    System.out.println("Error: Servicio no encontrado para el código " + mapa.get("service"));
                                }
                            } catch (Exception e) {
                                System.out.println("Error al obtener el servicio: " + e.getMessage());
                            }
                        }
                    }
                    
                    OrderDetalleServices[] osd = lista_osd.toArray();
                    ps.save();
                    for(OrderDetalleServices aux_osd : osd){
                        aux_osd.getOrderDetalle().setIdOrder(ps.getOrder().getId());
                        aux_osd.save();
                    }
                    res.put("msg", "OK");
                    res.put("data", "Orden registrada correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "Vehículo no encontrado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                res.put("msg", "Error");
                res.put("data", "Vehículo no especificado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        } catch(Exception e){
            System.out.println("Error en save data " + e.toString());
            res.put("msg", "Error");
            res.put("data", "Error al registrar la orden: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }
}


