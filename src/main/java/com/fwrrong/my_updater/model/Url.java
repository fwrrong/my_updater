package com.fwrrong.my_updater.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_urls")
public class Url {
    @Id
    private String url;
    private String brand;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Url() {
    }

    public Url(String url, String brand) {
        this.url = url;
        this.brand = brand;
    }
}
