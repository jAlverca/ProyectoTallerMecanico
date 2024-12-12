package com.example.rest;

import java.util.HashMap;

import controller.Dao.services.PersonaServices;
import controller.tda.list.LinkedList;

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

@Path("person")
public class PersonaApi {
    @SuppressWarnings("rawtypes")
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
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
        PersonaServices ps = new PersonaServices();
        try {
            ps.setPersona(ps.get(id));
        } catch (Exception e) {

        }

        map.put("msg", "Ok");
        map.put("data", ps.getPersona());

        if (ps.getPersona() == null || ps.getPersona().getId() == 0) {
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
        try {
            PersonaServices ps = new PersonaServices();
            ps.getPersona().setApellidos(map.get(("apellidos")).toString());
            ps.getPersona().setNombres(map.get(("nombres")).toString());
            ps.getPersona().setDireccion(map.get(("direccion")).toString());
            ps.getPersona().setTelefono(map.get(("telefono")).toString());
            ps.getPersona().setTipo(ps.getTipoIdentificacion(map.get("tipo").toString()));
            ps.getPersona().setIdentificacion(map.get("identificacion").toString());
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Persona registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            PersonaServices ps = new PersonaServices();
            ps.getPersona().setApellidos(map.get(("apellidos")).toString());
            ps.getPersona().setNombres(map.get(("nombres")).toString());
            ps.getPersona().setDireccion(map.get(("direccion")).toString());
            ps.getPersona().setTelefono(map.get(("telefono")).toString());
            ps.getPersona().setTipo(ps.getTipoIdentificacion(map.get("tipo").toString()));
            ps.getPersona().setIdentificacion(map.get("identificacion").toString());
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Persona editada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            PersonaServices ps = new PersonaServices();
            Integer id = Integer.parseInt(map.get("id").toString());

            Boolean success = ps.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Persona no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error en delete data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

 /*   @SuppressWarnings("unchecked")
    @Path("/list/search/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsLastName(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        map.put("msg", "Ok");
        LinkedList lista = ps.buscar_apellido(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @SuppressWarnings("unchecked")
    @Path("/list/search/ident/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsIdent(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        map.put("msg", "Ok");
        ps.setPersona(ps.buscar_identificacion(texto));
        map.put("data", ps.getPersona());
        if (ps.getPersona().getTipo() == null) {
            map.put("data", "No existe la persona");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @SuppressWarnings("unchecked")
    @Path("/list/order/{attribute}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsLastName(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        map.put("msg", "Ok");
        try {
            // revisar el order
            LinkedList lista = ps.order(type, attribute);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
            // TODO handle exception
        }

        return Response.ok(map).build();
    }*/

}
