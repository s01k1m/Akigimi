package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.CreateFeedServiceRequest;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@Transactional
class CreateFeedServiceTest extends SnsServiceIntegrationTestSupport {


    @Autowired
    private QueryFeedDbPort queryFeedDbPort;

    @Autowired
    private CreateFeedService createFeedService;

    @MockBean
    private MultipartFile mockFile;

    @BeforeEach
    void beforeEach() {
    }

    @DisplayName("[happy] 올바른 데이터를 입력하면 피드가 올라간다.")
    @Test
    void givenValidDataWhenSaveThenIsWorking() throws Exception {
        //given
        User user = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build());
        prepareForCreateFeed(user);

        CreateFeedServiceRequest request = CreateFeedServiceRequest.builder()
                .photo(mockFile)
                .saving(3000L)
                .content("컨텐츠")
                .isPublic(true)
                .akgimiPlace("아낀 장소")
                .notPurchasedItem("아낀 물품")
                .build();
        given(commandFeedImagePort.save(mockFile, user.getId())).willReturn("hello");

        //when
        Long feedId = createFeedService.createFeed(request, user.getId());

        //then
        assertThat(queryFeedDbPort.findById(feedId)).isPresent();
    }

    @MethodSource("generateWrongData")
    @DisplayName("[bad] 잘못된 데이터를 입력하면 피드 생성 도중 오류를 반환한다.")
    @ParameterizedTest
    void givenInvalidDataWhenSaveThenThrowError(CreateFeedServiceRequest request) throws Exception {
        //given
        User user = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build());
        prepareForCreateFeed(user);

        //when //then
        assertThatThrownBy(() -> createFeedService.createFeed(request, 1L))
                .isInstanceOf(BadRequestException.class);
    }

    private void prepareForCreateFeed(User user) {

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
    }


    private static Stream<Arguments> generateWrongData() throws Exception {
        MultipartFile mockFile = mock(MultipartFile.class);

        return Stream.of(
                Arguments.of(
                        CreateFeedServiceRequest.builder()
                                .photo(mockFile)
                                .saving(3000L)
                                .content("컨텐츠")
                                .isPublic(true)
                                .akgimiPlace("아낀 장소")
                                .notPurchasedItem("아낀 물품")
                                .build()
                ),
                Arguments.of(
                        CreateFeedServiceRequest.builder()
                                .photo(mockFile)
                                .saving(null)
                                .content("컨텐츠")
                                .isPublic(true)
                                .akgimiPlace(null)
                                .notPurchasedItem("아낀 물품")
                                .build()
                ),
                Arguments.of(CreateFeedServiceRequest.builder()
                        .photo(mockFile)
                        .saving(3000L)
                        .content("컨텐츠")
                        .isPublic(null)
                        .akgimiPlace("D")
                        .notPurchasedItem("아낀 물품")
                        .build()),
                Arguments.of(CreateFeedServiceRequest.builder()
                        .photo(mockFile)
                        .saving(3000L)
                        .content("컨텐츠")
                        .isPublic(true)
                        .akgimiPlace(null)
                        .notPurchasedItem("아낀 물품")
                        .build()),
                Arguments.of(CreateFeedServiceRequest.builder()
                        .photo(mockFile)
                        .saving(3000L)
                        .content("컨텐츠")
                        .isPublic(true)
                        .akgimiPlace("아낀 장소")
                        .notPurchasedItem(null)
                        .build())
        );
    }
}