package com.renanwillian.qtc.resources;


import com.renanwillian.qtc.dto.CarRequest;
import com.renanwillian.qtc.services.CarService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResources {

    @Inject
    CarService carService;

    @GET
    public Response list() {
        return Response.ok(carService.list()).build();
    }

    @POST
    public Response list(CarRequest carRequest) {
        return Response.status(Response.Status.CREATED.getStatusCode())
                       .entity(carService.create(carRequest))
                       .build();
    }

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") Long id) {
        return Response.ok(carService.find(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CarRequest carRequest) {
        return Response.ok(carService.update(id, carRequest)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        carService.delete(id);
        return Response.noContent().build();
    }
}
