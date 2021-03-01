package org.neves.eduardo.sales.api.salesman;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.neves.eduardo.sales.business.salesman.SalesmanService;
import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/salesmen")
public class SalesmanRest {

    @Inject
    SalesmanService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Salesman input) {
        service.create(input);
        return Response.ok(input).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{registration}")
    public Response find(@PathParam String registration) {
        Optional<Salesman> salesman = service.find(registration);

        if (!salesman.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(salesman.get()).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Salesman salesman) {
        return Response.ok(service.update(salesman)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{registration}")
    public void delete(@PathParam String registration) {
        service.delete(registration);
    }

}
