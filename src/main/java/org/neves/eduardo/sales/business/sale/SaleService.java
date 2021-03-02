package org.neves.eduardo.sales.business.sale;

import org.bson.types.ObjectId;
import org.neves.eduardo.sales.business.product.ProductService;
import org.neves.eduardo.sales.business.salesman.SalesmanService;
import org.neves.eduardo.sales.model.product.Product;
import org.neves.eduardo.sales.model.sale.Sale;
import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.quarkus.mongodb.panache.PanacheMongoEntityBase.count;

@ApplicationScoped
public class SaleService {

    @Inject
    SalesmanService salesmanService;

    @Inject
    ProductService productService;

    public Sale create(Sale sale) {
        checkForSalesmanExistence(sale.getSalesmanId());
        List<Product> existentProducts = getExistentProducts(sale.getProductIds());
        BigDecimal totalPrice = calculateProductsTotalPrice(existentProducts);

        sale.setTotalValue(totalPrice);
        Sale.persist(sale);
        return sale;
    }

    private BigDecimal calculateProductsTotalPrice(List<Product> existentProducts) {
        return existentProducts.stream()
                    .map(product -> product.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void checkForSalesmanExistence(ObjectId sale) {
        salesmanService.find(sale)
                .orElseThrow(() -> new NoSuchElementException(String.format("Salesman not found with Id [%s]", sale)));
    }

    private List<Product> getExistentProducts(List<ObjectId> productIds) {
        return productIds
                .stream()
                .map(id -> (Product) Product.findByIdOptional(id)
                        .orElseThrow(() -> new NoSuchElementException(String.format("Product not found with Id [%s]", productIds))))
                .collect(Collectors.toList());
    }

}
