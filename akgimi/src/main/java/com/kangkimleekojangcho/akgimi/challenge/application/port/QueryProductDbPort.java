package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.product.domain.Product;

import java.util.Optional;

public interface QueryProductDbPort {
    Optional<Product> findById(Long itemId);
}
