package org.neves.eduardo.sales.exception;

public class SalesmanAltreadyExistsException extends RuntimeException {

    public SalesmanAltreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

}
