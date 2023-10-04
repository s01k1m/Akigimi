package com.kangkimleekojangcho.akgimi.product.adapter.out;

import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    QProduct product = QProduct.product;
    public List<Product> findByNameAndStartMoneyAndendMoney(
            String name,
            Integer startMoney,
            Integer endMoney
    ) {
        return jpaQueryFactory
                .selectFrom(product)
                .where(
                        product.name.like("%" + name + "%"),
                        product.price.goe(startMoney*10000),
                        product.price.loe(endMoney*10000)
                )
                .fetch();
    }
}
