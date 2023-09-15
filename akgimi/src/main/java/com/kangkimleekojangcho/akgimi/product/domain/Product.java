package com.kangkimleekojangcho.akgimi.product.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
}
