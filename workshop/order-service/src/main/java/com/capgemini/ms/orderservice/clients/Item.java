package com.capgemini.ms.orderservice.clients;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private int version;

    private String name;

    private Float price;

    private String description;

    public Item() {
    }

    public Item(Long id, int version, String name, Float price, String description) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}