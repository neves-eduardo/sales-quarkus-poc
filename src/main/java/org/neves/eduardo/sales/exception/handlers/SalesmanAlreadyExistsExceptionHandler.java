package org.neves.eduardo.sales.exception.handlers;

import org.neves.eduardo.sales.exception.SalesmanAltreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SalesmanAlreadyExistsExceptionHandler implements ExceptionMapper<SalesmanAltreadyExistsException> {

    @Override
    public Response toResponse(SalesmanAltreadyExistsException exception)
    {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }

}