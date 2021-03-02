package org.neves.eduardo.sales.business.salesman;

import org.bson.types.ObjectId;
import org.neves.eduardo.sales.exception.SalesmanAltreadyExistsException;
import org.neves.eduardo.sales.model.sale.Sale;
import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class SalesmanService {

    public Salesman create(Salesman salesman) {
        Optional<Salesman> possibleSalesman = find(salesman.getRegistration());

        if (possibleSalesman.isPresent()) {
            throw new SalesmanAltreadyExistsException(String.format("salesman already exists with registration number [%s]", salesman.getRegistration()));
        }

        Salesman.persist(salesman);
        return salesman;
    }

    public Optional<Salesman> find(String registration) {
        return Salesman.find("registration", registration).firstResultOptional();
    }

    public Optional<Salesman> find(ObjectId id) {
        return Salesman.findByIdOptional(id);
    }

    public List<Salesman> findAll() {
        return Salesman.findAll().list();
    }

    public Salesman update(Salesman salesman) {
        Salesman.update(salesman);
        return salesman;
    }

    public void delete(String registration) {
        Salesman.delete("registration", registration);
    }

    public Map<Salesman, Long> listSalesmanBySales() {
        List<Salesman> salesmen = Salesman.findAllList();

        Map<Salesman, Long> salesmanAndSales = new HashMap<>();
        salesmen
                .forEach(salesman -> salesmanAndSales.put(salesman, Sale.count("salesmanId", salesman.id)));

        return sortSalesmenByNumberOfSales(salesmanAndSales);
    }

    public Map<Salesman, BigDecimal> listSalesmanByValueSold() {
        List<Salesman> salesmen = Salesman.findAllList();

        Map<Salesman, BigDecimal> salesmanAndSales = new HashMap<>();
        salesmen
                .forEach(salesman -> salesmanAndSales.put(salesman, getTotalSoldBySalesman(salesman)));

        return sortBySaleValue(salesmanAndSales);
    }

    private BigDecimal getTotalSoldBySalesman(Salesman salesman) {
        List<Sale> sales = Sale.findList("salesmanId",salesman.id);
        return sales.stream().map(Sale::getTotalValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private HashMap<Salesman, Long> sortSalesmenByNumberOfSales(Map<Salesman, Long> salesmanAndSales) {
        return salesmanAndSales.entrySet()
                .stream()
                .sorted((Map.Entry.<Salesman, Long>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private HashMap<Salesman, BigDecimal> sortBySaleValue(Map<Salesman, BigDecimal> salesmanAndSales) {
        return salesmanAndSales.entrySet()
                .stream()
                .sorted((Map.Entry.<Salesman, BigDecimal>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
