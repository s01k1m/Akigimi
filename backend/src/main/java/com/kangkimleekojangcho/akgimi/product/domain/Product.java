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
    private String name; // 물건이름
    private String thumbnail; // 썸네일
    private String image; // 이미지
    private String detail; // 물건 상세정보
    private String url; // 물건 구매 링크
    private Integer price; // 물건 가격
    private boolean isDeleted; // 삭제되었는지 여부
    //TODO: private Category category;
}
