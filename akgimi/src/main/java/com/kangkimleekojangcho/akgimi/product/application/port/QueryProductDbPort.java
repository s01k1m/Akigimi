package com.kangkimleekojangcho.akgimi.product.application.port;

import com.kangkimleekojangcho.akgimi.product.domain.Product;

import java.util.Optional;

public interface QueryProductDbPort {
    Optional<Product> findById(Long id);
}
