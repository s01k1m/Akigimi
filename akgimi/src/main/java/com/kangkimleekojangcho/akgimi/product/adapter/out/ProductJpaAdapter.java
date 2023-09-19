package com.kangkimleekojangcho.akgimi.product.adapter.out;


import com.kangkimleekojangcho.akgimi.product.application.port.CommandProductDbPort;
import com.kangkimleekojangcho.akgimi.product.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductJpaAdapter implements CommandProductDbPort, QueryProductDbPort {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id){
        return productJpaRepository.findById(id);
    }
}
