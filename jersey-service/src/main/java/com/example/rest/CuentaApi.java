package com.example.rest;

import java.util.HashMap;

import controller.Dao.services.CuentaServices;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

@Path("account")
public class CuentaApi {
    @SuppressWarnings("rawtypes")
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
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
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
        try {
            ps.setCuenta(ps.get(id));
        } catch (Exception e) {

        }

        map.put("msg", "Ok");
        map.put("data", ps.getCuenta());

        if (ps.getCuenta() == null || ps.getCuenta().getId() == 0) {
            map.put("msg", "No se encontró persona con ese identificador");
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
        //Validar que no se repita la identificacion
        CuentaServices ps = new CuentaServices();

        try {
            ps.getCuenta().setCorreo(map.get("correo").toString());
            ps.getCuenta().setClave(map.get("clave").toString());
            ps.getCuenta().setEstado(true);
            ps.getCuenta().setId_persona(Integer.parseInt(map.get("id_persona").toString()));
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Cuenta registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }


        
}
