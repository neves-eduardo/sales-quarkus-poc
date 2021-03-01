package org.neves.eduardo.sales.business.sale;

import org.bson.types.ObjectId;
import org.neves.eduardo.sales.model.product.Product;
import org.neves.eduardo.sales.model.sale.Sale;
import org.neves.eduardo.sales.model.salesman.Salesman;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SaleService {

    public Sale create(Sale sale) {
        checkForSalesmanExistence(sale.getSalesmanId());
        List<Product> existentProducts = getExistentProducts(sale.getProductIds());
        BigDecimal totalPrice = findProductsTotalPrice(existentProducts);

        sale.setTotalValue(totalPrice);
        Sale.persist(sale);
        return sale;
    }

    private BigDecimal findProductsTotalPrice(List<Product> existentProducts) {
        return existentProducts.stream()
                    .map(product -> product.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void checkForSalesmanExistence(ObjectId sale) {
        Salesman.findByIdOptional(sale)
                .orElseThrow(() -> new RuntimeException(String.format("Salesman not found with Id [%s]", sale)));
    }

    private List<Product> getExistentProducts(List<ObjectId> productIds) {
        return productIds
                .stream()
                .map(id -> (Product) Product.findByIdOptional(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Product not found with Id [%s]", productIds))))
                .collect(Collectors.toList());
    }

}
