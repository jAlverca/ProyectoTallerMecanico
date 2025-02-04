package com.example.rest;

import java.util.HashMap;

import controller.Dao.services.PersonaServices;
import models.Persona;

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
        // Validar que no se repita la identificacion
        PersonaServices ps = new PersonaServices();

        try {
            Persona p = ps.buscarIdentificacion(map.get("identificacion").toString());
            if (p != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una persona con esa identificación");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

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

    @Path("/update/{idPersona}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("idPersona") Integer idPersona, HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        PersonaServices ps = new PersonaServices();
        try {
            Persona p = ps.get(idPersona);
            if (p != null) {
                p.setApellidos(map.get("apellidos").toString());
                p.setNombres(map.get("nombres").toString());
                p.setDireccion(map.get("direccion").toString());
                p.setTelefono(map.get("telefono").toString());
                p.setIdentificacion(map.get("identificacion").toString());
                ps.setPersona(p);
                ps.merge(p, idPersona);
                res.put("msg", "OK");
                res.put("data", "Persona actualizada correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "ERROR");
                res.put("data", "Persona no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{idPersona}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("idPersona") Integer idPersona) {
        HashMap<String, Object> res = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            Persona p = ps.get(idPersona);
            if (p == null) {
                res.put("msg", "ERROR");
                res.put("data", "Persona no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            ps.delete(idPersona);
            res.put("msg", "OK");
            res.put("data", "Persona eliminada correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/list/search/ident/vehicle/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar_persona(@PathParam("texto") String texto) throws Exception {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        map.put("msg", "Ok");
        map.put("data", ps.buscar_identificacion_vehiculo(texto));
        if (map.isEmpty()) {
            map.put("msg", "No se encontró persona con ese identificador");
            return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(map).build();
        }
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Path("/search/{atributo}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPerson(@PathParam("atributo") String atributo, @PathParam("valor") String valor) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {

            if (atributo.equals("cedula")) {
                Persona p = ps.busquedaBinaria1(atributo, valor);
                map.put("data", p.toHashMap());
            } else {
                

            }
            map.put("msg", "OK");
            map.put("data", map.get("data"));

            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al buscar la persona: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

    }

}
