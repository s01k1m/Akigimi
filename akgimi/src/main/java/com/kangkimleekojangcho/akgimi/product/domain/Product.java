package com.kangkimleekojangcho.akgimi.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String thumbnail;
    private String image;
    private String detail;
    private String url;
    private Integer price;
    private boolean isDeleted;
}
