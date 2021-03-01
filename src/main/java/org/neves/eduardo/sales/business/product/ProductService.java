package org.neves.eduardo.sales.business.product;

import org.neves.eduardo.sales.model.product.Product;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService {

    public Product create(Product product) {
        Product.persist(product);
        return product;
    }

    public Product update(Product product) {
        Product.update(product);
        return product;
    }

    public void delete(String id) {
        Product.deleteById(new org.bson.types.ObjectId(id));
    }

}
