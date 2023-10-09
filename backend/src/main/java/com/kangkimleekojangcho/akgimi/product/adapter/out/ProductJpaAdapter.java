package com.kangkimleekojangcho.akgimi.product.adapter.out;


import com.kangkimleekojangcho.akgimi.product.application.port.CommandProductDbPort;
import com.kangkimleekojangcho.akgimi.product.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.product.application.response.SearchProductResponse;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductJpaAdapter implements CommandProductDbPort, QueryProductDbPort {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQuerydslRepository productQuerydslRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id){
        return productJpaRepository.findById(id);
    }

    @Override
    public List<Product> findByNameAndStartMoneyAndendMoney(String name, Integer startMoney, Integer endMoney) {
        return productQuerydslRepository.findByNameAndStartMoneyAndendMoney(name, startMoney, endMoney);
    }
}
