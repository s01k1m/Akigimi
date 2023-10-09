package com.kangkimleekojangcho.akgimi.product.application.port;

import com.kangkimleekojangcho.akgimi.product.domain.Product;

public interface CommandProductDbPort {
    Product save(Product product);
}
