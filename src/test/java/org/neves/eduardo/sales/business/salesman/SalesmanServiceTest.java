package org.neves.eduardo.sales.business.salesman;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.neves.eduardo.sales.business.product.ProductService;
import org.neves.eduardo.sales.business.sale.SaleService;
import org.neves.eduardo.sales.model.sale.Sale;
import org.neves.eduardo.sales.model.salesman.Salesman;
import org.wildfly.common.Assert;

import javax.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SalesmanServiceTest {

    @Inject
    SalesmanService salesmanService;

    @Test
    public void ShouldOrderDescendingByNumberOfSales() {
        PanacheMock.mock(Sale.class);
        PanacheMock.mock(Salesman.class);

        Mockito.when(Sale.count("salesmanId", new ObjectId("603c300ac38b7d38a7d99c34"))).thenReturn(20L);
        Mockito.when(Sale.count("salesmanId", new ObjectId("603c300ac38b7d38a7d99c35"))).thenReturn(27L);
        Mockito.when(Sale.count("salesmanId", new ObjectId("603c300ac38b7d38a7d99c36"))).thenReturn(23L);
        Mockito.when(Salesman.findAllList()).thenReturn(buildSalesmanList());

        Map<Salesman, Long> orderedSalesmen = salesmanService.listSalesmanBySales();

        assertIterableEquals(orderedSalesmen.values(), buildExpectedOrderedMap().values());
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

    private Map<Salesman, Long> buildExpectedOrderedMap() {
        Salesman salesman1 = new Salesman("salesmanA", "A1");
        salesman1.id = new ObjectId("603c300ac38b7d38a7d99c34");

        Salesman salesman2 = new Salesman("salesmanB", "A2");
        salesman2.id = new ObjectId("603c300ac38b7d38a7d99c35");

        Salesman salesman3 = new Salesman("salesmanC", "A3");
        salesman3.id = new ObjectId("603c300ac38b7d38a7d99c36");

        Map<Salesman, Long> expectedOrderedSalesman = new HashMap<>();

        expectedOrderedSalesman.put(salesman2, 27L);
        expectedOrderedSalesman.put(salesman3, 23L);
        expectedOrderedSalesman.put(salesman1, 20L);

        return expectedOrderedSalesman.entrySet()
                .stream()
                .sorted((Map.Entry.<Salesman, Long>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}