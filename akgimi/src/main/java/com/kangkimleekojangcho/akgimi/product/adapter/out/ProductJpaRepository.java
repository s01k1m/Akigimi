package com.kangkimleekojangcho.akgimi.product.adapter.out;

import com.kangkimleekojangcho.akgimi.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
