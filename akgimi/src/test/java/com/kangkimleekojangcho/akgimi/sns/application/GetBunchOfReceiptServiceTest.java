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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class GetBunchOfReceiptServiceTest extends SnsServiceIntegrationTestSupport {

    @Autowired
    private GetBunchOfReceiptService getBunchOfReceiptService;

    private static final int NUMBER_OF_PUBLIC = 10;
    @BeforeEach
    void before() {
        prepareForSelectReceipt();
    }

    @DisplayName("[happy] 정확한 데이터가 주어지면 요청에 따라 영수증 리스트를 반환한다.")
    @Test
    void givenValidData_whenGetBunchOfReceipt_thenReturnData() throws Exception {
        //given
        GetBunchOfReceiptServiceRequest getBunchOfReceiptServiceRequest =
                GetBunchOfReceiptServiceRequest.builder()
                        .lastReceiptId(Long.MAX_VALUE)
                        .numberOfReceipt(100)
                        .build();
        //when
        GetBunchOfReceiptServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfReceiptService.getBunchOfReceipt(1L, getBunchOfReceiptServiceRequest);


        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefReceiptInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefReceiptInfo().size()).isEqualTo(NUMBER_OF_PUBLIC);
    }

    private void prepareForSelectReceipt() {
        User user = User.builder()
                .id(1L)
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build();

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
        for (long i = 1; i <= NUMBER_OF_PUBLIC; i++) {
            commandFeedDbPort.save(
                    Feed.builder()
                            .user(user)
                            .feedId(i)
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
        for (long i = NUMBER_OF_PUBLIC+1; i <= NUMBER_OF_PUBLIC+10; i++) {
            commandFeedDbPort.save(
                    Feed.builder()
                            .user(user)
                            .feedId(i)
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


}