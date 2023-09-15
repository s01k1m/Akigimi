package com.kangkimleekojangcho.akgimi.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    //TODO: private Category category;

    @Builder
    public Product(Long id, String thumbnail, String image,
                   String detail, String url, Integer price, boolean isDeleted) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.image = image;
        this.detail = detail;
        this.url = url;
        this.price = price;
        this.isDeleted = isDeleted;
    }
}
