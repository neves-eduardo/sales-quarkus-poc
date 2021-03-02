package org.neves.eduardo.sales.model.salesman;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.util.List;

@MongoEntity(collection = "salesmen")
public class Salesman extends PanacheMongoEntity {

    String name;
    String registration;

    public Salesman(String name, String registration) {
        this.name = name;
        this.registration = registration;
    }

    public Salesman() {
    }

    public static List<Salesman> findAllList() {
        return findAll().list();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

}
