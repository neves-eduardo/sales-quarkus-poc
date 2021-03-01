package org.neves.eduardo.sales.model.sale;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

@MongoEntity(collection = "sales")
public class Sale extends PanacheMongoEntity {

    ObjectId salesmanId;
    List<ObjectId> productIds;
    BigDecimal totalValue;

    public Sale() {
    }

    public Sale(ObjectId salesmanId, List<ObjectId> productIds, BigDecimal totalValue) {
        this.salesmanId = salesmanId;
        this.productIds = productIds;
        this.totalValue = totalValue;
    }

    public ObjectId getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(ObjectId salesmanId) {
        this.salesmanId = salesmanId;
    }

    public List<ObjectId> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<ObjectId> productIds) {
        this.productIds = productIds;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

}
