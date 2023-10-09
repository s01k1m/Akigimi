package com.kangkimleekojangcho.akgimi.product.adapter.out;

import com.kangkimleekojangcho.akgimi.product.application.response.SearchProductResponse;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
}
