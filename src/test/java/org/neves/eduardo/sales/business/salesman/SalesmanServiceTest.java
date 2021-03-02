package org.neves.eduardo.sales.business.salesman;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.neves.eduardo.sales.model.sale.Sale;
import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SalesmanServiceTest {

    public static final long HIGHER_VALUE = 27L;
    public static final long MID_VALUE = 23L;
    public static final long LOW_VALUE = 20L;
    @Inject
    SalesmanService salesmanService;

    @Test
    public void ShouldOrderDescendingByNumberOfSales() {
        PanacheMock.mock(Sale.class);
        PanacheMock.mock(Salesman.class);

        Mockito.when(Sale.count("salesmanId", new ObjectId("603c300ac38b7d38a7d99c34"))).thenReturn(LOW_VALUE);
        Mockito.when(Sale.count("salesmanId", new ObjectId("603c300ac38b7d38a7d99c35"))).thenReturn(HIGHER_VALUE);
        Mockito.when(Sale.count("salesmanId", new ObjectId("603c300ac38b7d38a7d99c36"))).thenReturn(MID_VALUE);
        Mockito.when(Salesman.findAllList()).thenReturn(buildSalesmanList());

        Map<Salesman, Long> orderedSalesmen = salesmanService.listSalesmanBySales();

        assertIterableEquals(orderedSalesmen.values(), buildExpectedSalesOrderedMap().values());
    }

    @Test
    public void ShouldOrderDescendingBySalesValue() {
        PanacheMock.mock(Sale.class);
        PanacheMock.mock(Salesman.class);

        Mockito.when(Sale.findList("salesmanId", new ObjectId("603c300ac38b7d38a7d99c34"))).thenReturn(Arrays.asList(new Sale(null,null, BigDecimal.TEN)));
        Mockito.when(Sale.findList("salesmanId", new ObjectId("603c300ac38b7d38a7d99c35"))).thenReturn(
                Arrays.asList(new Sale(null,null, BigDecimal.valueOf(300.00)),new Sale(null,null, BigDecimal.valueOf(200.00))));
        Mockito.when(Sale.findList("salesmanId", new ObjectId("603c300ac38b7d38a7d99c36"))).thenReturn(Arrays.asList(new Sale(null,null, BigDecimal.valueOf(20.00))));
        Mockito.when(Salesman.findAllList()).thenReturn(buildSalesmanList());

        Map<Salesman, BigDecimal> orderedSalesmen = salesmanService.listSalesmanByValueSold();

        assertIterableEquals(orderedSalesmen.values(), buildExpectedValueOrderedMap().values());
    }

    private List<Salesman> buildSalesmanList() {
        Salesman salesman1 = new Salesman("salesmanA", "A1");
        salesman1.id = new ObjectId("603c300ac38b7d38a7d99c34");

        Salesman salesman2 = new Salesman("salesmanB", "A2");
        salesman2.id = new ObjectId("603c300ac38b7d38a7d99c35");

        Salesman salesman3 = new Salesman("salesmanC", "A3");
        salesman3.id = new ObjectId("603c300ac38b7d38a7d99c36");


        return Arrays.asList(salesman1, salesman2, salesman3);
    }

    private Map<Salesman, Long> buildExpectedSalesOrderedMap() {
        Salesman salesman1 = new Salesman("salesmanA", "A1");
        salesman1.id = new ObjectId("603c300ac38b7d38a7d99c34");

        Salesman salesman2 = new Salesman("salesmanB", "A2");
        salesman2.id = new ObjectId("603c300ac38b7d38a7d99c35");

        Salesman salesman3 = new Salesman("salesmanC", "A3");
        salesman3.id = new ObjectId("603c300ac38b7d38a7d99c36");

        Map<Salesman, Long> expectedOrderedSalesman = new HashMap<>();

        expectedOrderedSalesman.put(salesman2, HIGHER_VALUE);
        expectedOrderedSalesman.put(salesman3, MID_VALUE);
        expectedOrderedSalesman.put(salesman1, LOW_VALUE);

        return expectedOrderedSalesman.entrySet()
                .stream()
                .sorted((Map.Entry.<Salesman, Long>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<Salesman, BigDecimal> buildExpectedValueOrderedMap() {
        Salesman salesman1 = new Salesman("salesmanA", "A1");
        salesman1.id = new ObjectId("603c300ac38b7d38a7d99c34");

        Salesman salesman2 = new Salesman("salesmanB", "A2");
        salesman2.id = new ObjectId("603c300ac38b7d38a7d99c35");

        Salesman salesman3 = new Salesman("salesmanC", "A3");
        salesman3.id = new ObjectId("603c300ac38b7d38a7d99c36");

        Map<Salesman, BigDecimal> expectedOrderedSalesman = new HashMap<>();

        expectedOrderedSalesman.put(salesman2, BigDecimal.valueOf(500.00));
        expectedOrderedSalesman.put(salesman3, BigDecimal.valueOf(20.00));
        expectedOrderedSalesman.put(salesman1, BigDecimal.TEN);

        return expectedOrderedSalesman.entrySet()
                .stream()
                .sorted((Map.Entry.<Salesman, BigDecimal>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}