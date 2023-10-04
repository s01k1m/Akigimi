package com.kangkimleekojangcho.akgimi.product.application.port;

import com.kangkimleekojangcho.akgimi.product.application.response.SearchProductResponse;
import com.kangkimleekojangcho.akgimi.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface QueryProductDbPort {
    Optional<Product> findById(Long id);

    List<Product> findByNameAndStartMoneyAndendMoney(String name, Integer startMoney, Integer endMoney);
}
