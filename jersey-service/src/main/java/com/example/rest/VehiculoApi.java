package com.example.rest;

import java.util.HashMap;

import controller.Dao.services.PersonaServices;
import controller.Dao.services.VehiculoServices;
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

@Path("vehicle")
public class VehiculoApi {
    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listVehicles(@PathParam("id") int id) 
    {
        HashMap<String, Object> res = new HashMap<>();
        VehiculoServices ps = new VehiculoServices();
        try {
            res.put("msg", "Ok");
            res.put("data", ps.listByPersonId(id).toArray());
            if (ps.listByPersonId(id).isEmpty()) {
                res.put("data", new Object[] {});
            }
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
        VehiculoServices ps = new VehiculoServices();
        try {
            ps.setVehiculo(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "OK");
        map.put("data", ps.getVehiculo());
        if (ps.getVehiculo().getId() == null) {
            map.put("data", "No existe el Vehiculo con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

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
            if (map.get("idPersona") != null) {
                PersonaServices personaServices = new PersonaServices();
                personaServices.setPersona(personaServices.get(Integer.parseInt(map.get("idPersona").toString())));
                if (personaServices.getPersona().getId() != null) {
                    VehiculoServices ps = new VehiculoServices();
                    ps.getVehiculo().setMarca(map.get("marca").toString());
                    ps.getVehiculo().setModelo(map.get("modelo").toString());
                    ps.getVehiculo().setPlaca(map.get("placa").toString());
                    ps.getVehiculo().setColor(map.get("color").toString());
                    ps.getVehiculo().setDescripcion(map.get("descripcion").toString());
                    ps.getVehiculo().setEstado(true);
                    ps.getVehiculo().setIdPersona(personaServices.getPersona().getId());
                    ps.save();
                    res.put("msg", "OK");
                    res.put("data", "Vehiculo registrado correctamente");
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


    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        // TODO
        // VALIDATION ---- BAD REQUEST
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);

        try {
            if (map.get("person") != null) {
                PersonaServices personaServices = new PersonaServices();
                personaServices.setPersona(personaServices.get(Integer.parseInt(map.get("person").toString())));
                if (personaServices.getPersona().getId() != null) {
                    VehiculoServices ps = new VehiculoServices();
                    ps.getVehiculo().setMarca(map.get("marca").toString());
                    ps.getVehiculo().setModelo(map.get("modelo").toString());
                    ps.getVehiculo().setPlaca(map.get("placa").toString());
                    ps.getVehiculo().setColor(map.get("color").toString());
                    ps.getVehiculo().setDescripcion(map.get("descripcion").toString());
                    ps.getVehiculo().setEstado(true);
                    ps.getVehiculo().setIdPersona(personaServices.getPersona().getId());
                    ps.update();
                    res.put("msg", "OK");
                    res.put("data", "Vehiculo registrada correctamente");
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
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            // TODO: handle exception
        }

    }
}
