package com.kangkimleekojangcho.akgimi.challenge.adapter.out;


import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.PostscriptInfo;
import com.kangkimleekojangcho.akgimi.challenge.application.response.QPostscriptInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kangkimleekojangcho.akgimi.challenge.domain.QChallenge.challenge;
import static com.kangkimleekojangcho.akgimi.challenge.domain.QPostscript.postscript;
import static com.kangkimleekojangcho.akgimi.product.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class PostscriptQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<PostscriptInfo> findBunchOfPostscriptForProduct(
            GetBunchOfPostscriptServiceRequest request
    ) {
        return jpaQueryFactory.select(
                        new QPostscriptInfo(
                                postscript.challenge.user.id,
                                postscript.challenge.user.profileImageUrl,
                                postscript.challenge.product.id,
                                postscript.challenge.product.name,
                                postscript.challenge.user.nickname,
                                postscript.challenge.product.price,
                                postscript.content,
                                postscript.image,
                                postscript.challenge.challengeEndDate.year()
                                        .subtract(postscript.challenge.challengeStartDate.year())
                                        .multiply(365)
                                        .add(postscript.challenge.achievementDate.dayOfYear())
                                        .subtract(postscript.challenge.challengeStartDate.dayOfYear())
                        )
                ).from(
                       challenge
                ).innerJoin(product)
                .on(
                        challenge.achievementState.eq(true),
                        product.id.eq(request.productId()),
                        challenge.product.eq(product)
                )
                .innerJoin(postscript)
                .on(challenge.eq(postscript.challenge))
                .where(
                        ltScriptId(request.lastPostscriptId())
                )
                .limit(request.numberOfPostscript())
                .fetch();
    }

    private BooleanExpression ltScriptId(Long postscriptId) {
        if (postscriptId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return postscript.postscriptId.lt(postscriptId);
    }
}
