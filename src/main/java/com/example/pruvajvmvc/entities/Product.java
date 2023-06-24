package com.example.pruvajvmvc.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@ToString @EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String title;
    @Getter @Setter
    private Double price;
    @Getter @Setter
    private Integer quantity;

    public Product(){}


    public Product(Long id, String title, Double price, Integer quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
