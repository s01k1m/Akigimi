package com.kangkimleekojangcho.akgimi.challenge.application;
import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.product.application.port.CommandProductDbPort;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.OidcProvider;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class CreateChallengeServiceTest{
    private User user;
    private Product product;
    private Account depositAccount;

    @Autowired
    private CommandUserDbPort commandUserDbPort;
    @Autowired
    private CommandProductDbPort commandProductDbPort;
    @Autowired
    private CommandAccountDbPort commandAccountDbPort;
    @Autowired
    private CommandChallengeDbPort commandChallengeDbPort;

    @BeforeEach
    public void beforeEach() {
        user = commandUserDbPort.save(
                User.builder()
                        .id(1L)
                        .oauthId("test-oauth-id")
                        .nickname("test-nickname")
                        .oidcProvider(OidcProvider.KAKAO)
                        .userState(UserState.ACTIVE)
                        .kakaoProfileNickname(new KakaoNickname("test-kakao-nickname"))
                        .simplePassword("test-simple-password")
                        .profileImageUrl("test-profile-img")
                        .build());

        product = commandProductDbPort.save(
                Product.builder()
                        .id(1L)
                        .name("항공권")
                        .price(1000000)
                        .build());

        depositAccount = commandAccountDbPort.save(
                Account.builder()
                        .id(1L)
                        .user(user)
                        .accountType(AccountType.DEPOSIT)
                        .bank(Bank.SSAFY)
                        .accountNumber("1234")
                        .balance(100000L)
                        .password("password")
                        .isDeleted(false)
                        .isPasswordRegistered(true)
                        .build());
    }

    @AfterEach
    public void afterEach(){

    }

    @DisplayName("[happy] 새로운 챌린지를 생성할 수 있어요.")
    @Test
    void create(){
        //given

        //when
        Challenge challenge = Challenge.builder()
                .user(user)
                .product(product)
                .accumulatedAmount(depositAccount.getBalance())
                .achievementState(false)
                .challengePeriod(30)
                .challengeStartDate(LocalDate.now())
                .isInProgress(true)
                .build();

        Challenge save = commandChallengeDbPort.save(challenge);

        //then
        Assertions.assertEquals(1, save.getProduct().getId());
        Assertions.assertEquals(30, save.getChallengePeriod());
    }
}