package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.common.application.port.BeanBusinessValidationService;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfReceiptServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfReceiptServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.ToStringExclude;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class GetBunchOfReceiptServiceTest extends SnsServiceIntegrationTestSupport {

    @Autowired
    private GetBunchOfReceiptService getBunchOfReceiptService;

    private static final int NUMBER_OF_PUBLIC = 10;

    @DisplayName("[happy] 다른 유저가 요청했을 때 정확한 데이터가 주어지면 요청에 따라 영수증 리스트를 반환한다.")
    @Test
    void givenValidData_whenGetBunchOfReceipt_thenReturnData() throws Exception {
        //given
        GetBunchOfReceiptServiceRequest getBunchOfReceiptServiceRequest =
                GetBunchOfReceiptServiceRequest.builder()
                        .lastReceiptId(Long.MAX_VALUE)
                        .numberOfReceipt(100)
                        .build();
        User user = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build());
        prepareForSelectReceipt(user);

        User user2 = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오2")
                .oauthId("abcde")
                .build());

        //when
        GetBunchOfReceiptServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfReceiptService.getBunchOfReceipt(user.getId(),user2.getId(), getBunchOfReceiptServiceRequest);


        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefReceiptInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefReceiptInfo().size()).isEqualTo(10);
    }

//    @DisplayName("[bad] 잘못된 데이터가 주어지면 영수증 리스트가 아닌 오류를 반환한다.")
//    @Test
//    public void givenInvalidData_whenGetBunchOfReceipt_thenThrowError() throws Exception {
//        //given
//        //테스트 진행 방향. -> S3 오류 잡기. 나머지는 어차피 최적화하면서 변경해야함.
//
//        //when
//
//        //then
//    }

    private void prepareForSelectReceipt(User user) {
        commandUserDbPort.save(user);

        Product product = Product.builder()
                .price(500000)
                .detail("테스트 프로덕트입니다.")
                .image("https://testUrl.com")
                .isDeleted(false)
                .thumbnail("https://thumbnail.com")
                .build();
        commandProductDbPort.save(product);

        Challenge challenge = Challenge.builder()
                .challengeStartDate(LocalDate.now())
                .challengeEndDate(LocalDate.now().plusDays(30))
                .accumulatedAmount(300000L)
                .achievementDate(null)
                .achievementState(false)
                .isInProgress(true)
                .product(product)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        commandChallengeDbPort.save(challenge);

        Account depositAccount = Account.builder()
                .user(user)
                .accountNumber("1234")
                .accountType(AccountType.DEPOSIT)
                .balance(300000L)
                .bank(Bank.MULTI)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .build();
        commandAccountDbPort.save(depositAccount);

        Account withdrawalAccount = Account.builder()
                .user(user)
                .accountNumber("1234")
                .accountType(AccountType.WITHDRAW)
                .balance(300000L)
                .bank(Bank.MULTI)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .build();
        commandAccountDbPort.save(withdrawalAccount);

        createPublicFeed(challenge, user);
        createNotPublicFeed(challenge, user);
    }

    private void createPublicFeed(Challenge challenge, User user) {
        for (long i = 0; i < NUMBER_OF_PUBLIC; i++) {
            commandFeedDbPort.save(
                    Feed.builder()
                            .user(user)
                            .price(100000L)
                            .content("content")
                            .place("my place")
                            .imageUrl("http://imageimage.com")
                            .accumulatedAmount(1000000L)
                            .challenge(challenge)
                            .isDeleted(false)
                            .isPublic(true)
                            .notPurchasedItem("안샀어요")
                            .build());
        }
    }

    private void createNotPublicFeed(Challenge challenge, User user) {
        for (long i = 0; i < NUMBER_OF_PUBLIC; i++) {
            commandFeedDbPort.save(
                    Feed.builder()
                            .user(user)
                            .price(100000L)
                            .content("content")
                            .place("my place")
                            .imageUrl("http://imageimage.com")
                            .accumulatedAmount(1000000L)
                            .challenge(challenge)
                            .isDeleted(false)
                            .isPublic(false)
                            .notPurchasedItem("안샀어요")
                            .build());
        }
    }


}