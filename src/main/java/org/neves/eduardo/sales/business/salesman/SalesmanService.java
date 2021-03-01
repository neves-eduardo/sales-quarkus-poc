package org.neves.eduardo.sales.business.salesman;

import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class SalesmanService {

    public Salesman create(Salesman salesman) {
        Optional<Salesman> possibleSalesman = find(salesman.getRegistration());

        if(possibleSalesman.isPresent()) {
            throw new RuntimeException(String.format("salesman already exists with registration number [%s]", salesman.getRegistration()));
        }

        Salesman.persist(salesman);
        return salesman;
    }

    public Optional<Salesman> find(String registration) {
        return Salesman.find("registration", registration).firstResultOptional();
    }

    public Salesman update(Salesman salesman) {
        Salesman.update(salesman);
        return salesman;
    }

    public void delete(String registration) {
        Salesman.delete("registration", registration);
    }

}
