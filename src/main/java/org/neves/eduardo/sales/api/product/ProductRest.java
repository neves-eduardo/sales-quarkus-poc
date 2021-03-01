package org.neves.eduardo.sales.api.product;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.neves.eduardo.sales.business.product.ProductService;
import org.neves.eduardo.sales.model.product.Product;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductRest {

    @Inject
    ProductService productService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product create(Product product) {
        return productService.create(product);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product update(Product product) {
        return productService.update(product);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void delete(@PathParam String id) {
        productService.delete(id);
    }

}
