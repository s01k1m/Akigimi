package com.kangkimleekojangcho.akgimi.product.adapter.out;

import com.kangkimleekojangcho.akgimi.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product,Long> {
    Product save(Product product);
}
