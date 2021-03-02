package org.neves.eduardo.sales.api.sale;

import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.neves.eduardo.sales.business.sale.SaleService;
import org.neves.eduardo.sales.model.sale.Sale;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sales")
public class SaleRest {

    @Inject
    SaleService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Sale sale) {
        return Response.ok(service.create(sale)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response find(@PathParam String id) {
        return Response.ok(service.findById(new ObjectId(id))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

}
