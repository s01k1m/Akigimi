package com.kangkimleekojangcho.akgimi.product.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductJpaAdapter implements QueryProductDbPort {
    private final ProductJpaRepository productJpaRepository;
    @Override
    public Optional<Product> findById(Long itemId) {
        return productJpaRepository.findById(itemId);
    }
}
