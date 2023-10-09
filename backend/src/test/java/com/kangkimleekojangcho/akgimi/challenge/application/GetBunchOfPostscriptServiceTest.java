package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.adapter.out.PostscriptJpaRepository;
import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetBunchOfPostscriptServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.OidcProvider;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class GetBunchOfPostscriptServiceTest extends ChallengeIntegrationTestSupport {

    private static final int NUMBER_OF_POSTSCRIPT = 10;

    @Autowired
    private GetBunchOfPostscriptService getBunchOfPostscriptService;

    @Autowired
    private PostscriptJpaRepository postscriptJpaRepository;

    @DisplayName("[happy] 후기글 목록을 요청 시 정확한 데이터를 입력하면 후기글 목록을 반환한다.")
    @CsvSource(value = {"100:10", "5:5", "10:10", "11:10"}, delimiter = ':')
    @ParameterizedTest
    public void givenValidData_whenRequestBunchOfPostscript_thenReturnBunchOfPostscript(
            Integer numberOfPostscript,
            Integer answer
    ) throws Exception {
        //given
        Product productToGetPostscript = commandProductDbPort.save(Product.builder()
                .name("닌텐도 DS")
                .price(500000)
                .detail("테스트 프로덕트입니다.")
                .image("https://testUrl.com")
                .isDeleted(false)
                .thumbnail("https://thumbnail.com")
                .build());
        prepareBunchOfPostscript(productToGetPostscript);


        Product notRelatedProduct = commandProductDbPort.save(Product.builder()
                .name("닌텐도 switch")
                .price(500000)
                .detail("테스트 프로덕트입니다.")
                .image("https://testUrl.com")
                .isDeleted(false)
                .thumbnail("https://thumbnail.com")
                .build());
        prepareBunchOfPostscript(notRelatedProduct);


        GetBunchOfPostscriptServiceRequest request = GetBunchOfPostscriptServiceRequest.builder()
                .lastPostscriptId(Long.MAX_VALUE)
                .productId(productToGetPostscript.getId())
                .numberOfPostscript(numberOfPostscript)
                .build();

        //when
        GetBunchOfPostscriptServiceResponse result
                = getBunchOfPostscriptService.execute(request);

        //then
        assertThat(result.bunchOfPostscriptInfo().size()).isEqualTo(answer);
        assertThat(result.bunchOfPostscriptInfo().get(0).period().intValue()).isEqualTo(30);
    }


    private void prepareBunchOfPostscript(Product product) {
        for (int i = 0; i < NUMBER_OF_POSTSCRIPT; i++) {
            User user = commandUserDbPort.save(User.builder()
                    .userState(UserState.ACTIVE)
                    .nickname(product.getName() + i)
                    .oauthId(product.getName() + i)
                    .kakaoProfileNickname(new KakaoNickname(product.getName() + i))
                    .oidcProvider(OidcProvider.KAKAO)
                    .simplePassword("h12314").build());

            Challenge challenge = commandChallengeDbPort.save(Challenge.builder()
                    .challengeStartDate(LocalDate.now())
                    .challengeEndDate(LocalDate.now().plusDays(30))
                    .accumulatedAmount(300000L)
                    .achievementDate(null)
                    .achievementState(true)
                    .isInProgress(false)
                    .product(product)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .challengeStartDate(LocalDate.now().minusDays(30))
                    .achievementDate(LocalDate.now())
                    .challengeEndDate(LocalDate.now().plusDays(10))
                    .build());
            Account depositAccount = commandAccountDbPort.save(Account.builder()
                    .user(user)
                    .accountNumber(product.getName() + i)
                    .accountType(AccountType.DEPOSIT)
                    .balance(300000L)
                    .bank(Bank.MULTI)
                    .isDeleted(false)
                    .isPasswordRegistered(true)
                    .password("1234")
                    .build());

            Account withdrawalAccount = commandAccountDbPort.save(Account.builder()
                    .user(user)
                    .accountNumber(product.getName() + i)
                    .accountType(AccountType.WITHDRAW)
                    .balance(300000L)
                    .bank(Bank.MULTI)
                    .isDeleted(false)
                    .isPasswordRegistered(true)
                    .password("1234")
                    .build());

            commandPostscriptDbPort.save(Postscript.builder()
                    .isDeleted(false)
                    .challenge(challenge)
                    .image("http://imageimage.com")
                    .content("hello")
                    .build());
        }
    }
}