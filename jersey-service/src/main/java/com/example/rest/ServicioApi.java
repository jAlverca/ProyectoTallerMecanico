package com.example.rest;

import java.util.HashMap;

import controller.Dao.services.ServicioServices;
import models.Servicio;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

@Path("services")
public class ServicioApi {
    @SuppressWarnings("rawtypes")
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServices() {
        HashMap map = new HashMap<>();
        ServicioServices ps = new ServicioServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServices(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        ServicioServices ps = new ServicioServices();
        try {
            ps.setServicio(ps.get(id));
        } catch (Exception e) {

        }

        map.put("msg", "Ok");
        map.put("data", ps.getServicio());

        if (ps.getServicio() == null || ps.getServicio().getId() == 0) {
            map.put("msg", "No se encontró Servicio con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }

        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);

        try {
            ServicioServices ps = new ServicioServices();
            if(ps.getServicioCodigo(map.get(("codigo")).toString()) != null){
                res.put("msg", "Error");
                res.put("data", "El código ya existe");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
            ps.getServicio().setNombre(map.get(("nombre")).toString());
            ps.getServicio().setDescripcion(map.get(("descripcion")).toString());
            ps.getServicio().setPu(Float.parseFloat(map.get(("pu")).toString()));
            ps.getServicio().setPt(Float.parseFloat(map.get(("pt")).toString()));
            ps.getServicio().setCodigo(map.get(("codigo")).toString());
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Servicio registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }

    @Path("/update/{idServicio}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateServices(@PathParam("idServicio") Integer idServicio, HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        ServicioServices ps = new ServicioServices();
        try {
            Servicio p = ps.get(idServicio);
            ps.getServicio().setNombre(map.get(("nombre")).toString());
            ps.getServicio().setDescripcion(map.get(("descripcion")).toString());
            ps.getServicio().setPu(Float.parseFloat(map.get(("pu")).toString()));
            ps.getServicio().setPt(Float.parseFloat(map.get(("pt")).toString()));
            ps.getServicio().setCodigo(map.get(("codigo")).toString());
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Servicio actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar la Servicio: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


    @Path("/code/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServicesCode(@PathParam("code") String code) {
        HashMap<String, Object> map = new HashMap<>();
        ServicioServices ps = new ServicioServices();
        try {
            ps.setServicio(ps.getServicioCodigo(code));
        } catch (Exception e) {
            // Manejo de excepciones
        }
        map.put("msg", "Ok");
        map.put("data", ps.getServicio());
    
        if (ps.getServicio().getId() == null) {
            map.put("msg", "No se encontró Servicio con ese identificador");
            return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(map).build();
        }
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }

    
    @SuppressWarnings("unchecked")
    @Path("/delete/{idServicio}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteServices(@PathParam("idServicio") Integer idServicio) {
        HashMap res = new HashMap<>();
        ServicioServices ps = new ServicioServices();
        try {
            ps.delete(idServicio);
            res.put("msg", "OK");
            res.put("data", "Persona eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
