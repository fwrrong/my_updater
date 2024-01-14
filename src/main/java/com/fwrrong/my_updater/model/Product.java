package com.fwrrong.my_updater.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String image;

    private String link;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Product(String name, String image, String link) {
        this.name = name;
        this.image = image;
        this.link = link;
    }

    public Product(UUID id, String name, String image, String link) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.link = link;
    }

    public Product() {
    }
}
