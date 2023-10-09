package com.kangkimleekojangcho.akgimi.product.application.request;

import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class CreateProductServiceRequest {
    private final String name;
    private final String detail;
    private final MultipartFile thumbnail;
    private final MultipartFile image;
    private final String url;
    private final Integer price;

    public Product toEntity(String image, String thumbnail){
        return Product.builder()
                .name(this.name)
                .detail(this.detail)
                .url(this.url)
                .price(this.price)
                .image(image)
                .thumbnail(thumbnail)
                .isDeleted(false)
                .build();
    }


}
