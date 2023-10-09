package com.kangkimleekojangcho.akgimi.product.adapter.in.request;

import com.kangkimleekojangcho.akgimi.product.application.request.CreateProductServiceRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class CreateProductRequest {
    private final String name;
    private final String detail;
    private final String url;
    private final Integer price;
    private final List<MultipartFile> thumbnail;
    private final List<MultipartFile> image;

    public CreateProductServiceRequest toServiceRequest() {
        return CreateProductServiceRequest.builder()
                .name(this.name)
                .detail(this.detail)
                .thumbnail(this.thumbnail.get(0))
                .image(this.image.get(0))
                .url(this.url)
                .price(this.price)
                .build();
    }
}
