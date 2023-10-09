package com.kangkimleekojangcho.akgimi.product.application;

import com.kangkimleekojangcho.akgimi.product.application.port.CommandProductDbPort;
import com.kangkimleekojangcho.akgimi.product.application.request.CreateProductServiceRequest;
import com.kangkimleekojangcho.akgimi.product.application.response.CreateProductServiceResponse;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandImagePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveProductService {
    private final CommandProductDbPort commandProductDbPort;
    private final CommandImagePort commandImagePort;

    @Transactional
    public CreateProductServiceResponse save(
            Long userId,
            CreateProductServiceRequest createProductServiceRequest) {
        // 1. 물건 썸네일 저장
        String thumbnailUrl = commandImagePort.save(createProductServiceRequest.getThumbnail(), userId);
        // 2. 물건 이미지 저장
        String imageUrl = commandImagePort.save(createProductServiceRequest.getImage(), userId);

        // 3. Product 저장
        Product product = createProductServiceRequest.toEntity(imageUrl,thumbnailUrl);
        commandProductDbPort.save(product);

        return CreateProductServiceResponse.builder()
                .isProductRegistered(true)
                .build();
    }
}
