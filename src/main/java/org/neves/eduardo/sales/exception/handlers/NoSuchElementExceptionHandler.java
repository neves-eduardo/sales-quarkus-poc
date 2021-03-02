package org.neves.eduardo.sales.exception.handlers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class NoSuchElementExceptionHandler implements ExceptionMapper<NoSuchElementException> {

    @Override
    public Response toResponse(NoSuchElementException exception)
    {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }

}
