package com.kangkimleekojangcho.akgimi.product.adapter.out;


import com.kangkimleekojangcho.akgimi.product.application.port.CommandProductDbPort;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandProductJpaAdapter implements CommandProductDbPort {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
}
