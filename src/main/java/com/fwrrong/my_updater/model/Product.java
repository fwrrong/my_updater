package com.fwrrong.my_updater.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.UUID;

@Document(collection = "products")
public class Product {
    @Id
    private UUID id;

    private String name;

    @Field("in_stock")
    private Boolean inStock = false;

    private String image;

    private String url;

    private String size;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Product() {
    }

    public Product(String name, String image, String url, String size) {
        this.name = name;
        this.image = image;
        this.url = url;
        this.size = size;
    }

    public Product(UUID id, String name, Boolean inStock, String image, String url, String size) {
        this.id = id;
        this.name = name;
        this.inStock = inStock;
        this.image = image;
        this.url = url;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inStock=" + inStock +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
