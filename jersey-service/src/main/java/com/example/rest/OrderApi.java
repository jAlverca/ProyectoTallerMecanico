package com.example.rest;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import controller.Dao.services.OrderServices;

public class OrderApi {
    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOrders(@PathParam("id") int id) 
    {
        HashMap<String, Object> res = new HashMap<>();
        OrderServices ps = new OrderServices();
        try {
            res.put("msg", "Ok");
            res.put("data", ps.listByVehicleId(id).toArray());
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
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
        if (ps.getOrder().getId() == null) {
            map.put("data", "No existe el Vehiculo con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();

    @SuppressWarnings("rawtypes")
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);

        try {
            if (map.get("idVehiculo") != null) {
                OrderServices orderServices = new OrderServices();
                orderServices.setOrder(orderServices.get(Integer.parseInt(map.get("idVehiculo").toString())));
                if (orderServices.getOrder().getId() != null) {
                    OrderServices ps = new OrderServices();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formatter.parse(map.get("fecha").toString());
                    ps.getOrder().setFecha(fecha);
                    ps.getOrder().setSubtotal(Float.parseFloat(map.get("subtotal").toString()));
                    ps.getOrder().setIva(Float.parseFloat(map.get("iva").toString()));
                    ps.getOrder().setTotal(Float.parseFloat(map.get("total").toString()));
                    ps.getOrder().setNroOrder(map.get("nOrder").toString());
                    ps.getOrder().setIdVehiculo(orderServices.getOrder().getId());
                    ps.save(); 
                    res.put("msg", "OK");
                    res.put("data", "Orden registrada correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "La persona no existe");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                res.put("msg", "Error");
                res.put("data", "Faltan datos");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error en save data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    
}


