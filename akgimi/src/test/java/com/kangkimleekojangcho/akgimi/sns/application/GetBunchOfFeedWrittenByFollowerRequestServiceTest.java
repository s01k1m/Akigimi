package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfFeedWrittenByFollowerRequestServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GetBunchOfFeedWrittenByFollowerRequestServiceTest extends SnsServiceIntegrationTestSupport {

    @Autowired
    private GetBunchOfFeedWrittenByFollowerRequestService getBunchOfFeedWrittenByFollowerRequestService;

    private static final int NUMBER_OF_PUBLIC = 10;

    @DisplayName("[happy]본인의 영수증을 요청했을 때 정확한 데이터가 주어지면 요청에 따라 영수증 리스트를 반환한다.")
    @MethodSource("givenSameUserAnsAnswer")
    @ParameterizedTest
    void givenValidData_whenSameUserRequestGetBunchOfReceipt_thenReturnData(
            User requestUser,
            int size,
            int answer
    ) throws Exception {
        //given
        GetBunchOfFeedWrittenByFollowerRequestServiceRequest getBunchOfFeedServiceRequest =
                GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                        .lastFeedId(Long.MAX_VALUE)
                        .numberOfFeed(size)
                        .build();
        requestUser = commandUserDbPort.save(requestUser);
        prepareForSelectReceipt(requestUser);

        //when
        GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                requestUser.getId(), getBunchOfFeedServiceRequest);

        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo().size()).isEqualTo(answer);
    }

    private static Stream<Arguments> givenSameUserAnsAnswer() {
        User user = User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .profileImageUrl("httt")
                .nickname("helllloooo")
                .userState(UserState.ACTIVE)
                .oauthId("abcde")
                .build();
        return Stream.of(
                Arguments.of(
                        user, 100, 20
                ),
                Arguments.of(
                        user, 0, 0
                ),
                Arguments.of(
                        user, 10, 10
                )
        );
    }

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