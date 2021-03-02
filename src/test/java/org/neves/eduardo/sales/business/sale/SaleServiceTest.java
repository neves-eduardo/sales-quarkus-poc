package org.neves.eduardo.sales.business.sale;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.neves.eduardo.sales.business.product.ProductService;
import org.neves.eduardo.sales.business.salesman.SalesmanService;
import org.neves.eduardo.sales.model.sale.Sale;
import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class SaleServiceTest {

    @InjectMock
    SalesmanService salesmanService;

    @InjectMock
    ProductService productService;

    @Inject
    SaleService saleService = new SaleService();



//    private Map<Salesman, Long> buildUnorderedMap() {
//        Map<Salesman, Long> unorderedSalesmen = new HashMap<>();
//
//        unorderedSalesmen.put(new Salesman("salesmanA", "A1"), 9L);
//        unorderedSalesmen.put(new Salesman("salesmanB", "A2"), 10L);
//        unorderedSalesmen.put(new Salesman("salesmanC", "A3"), 6L);
//    }

}