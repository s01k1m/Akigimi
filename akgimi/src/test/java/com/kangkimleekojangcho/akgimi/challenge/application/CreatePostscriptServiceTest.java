package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.application.request.CreatePostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.OidcProvider;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
class CreatePostscriptServiceTest extends ChallengeIntegrationTestSupport {

    @Autowired
    private CreatePostscriptService createPostscriptService;

    @DisplayName("[happy] 후기 생성 시 올바른 입력값을 주면 후기를 생성한다.")
    @Test
    public void givenValidData_whenCreatePostscript_thenCreatePostscript() throws Exception {
        //given
        User user = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE)
                .nickname("nickname")
                .oauthId("hellokakao")
                .kakaoProfileNickname(new KakaoNickname("kakaonick"))
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("h12314")
                .build());
        System.out.println("user : " + user.getId());

        Challenge challenge = prepareForCreatePostscript(user);
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));

        //when
        createPostscriptService.createPostscript(
                CreatePostscriptServiceRequest.builder()
                        .photo(file)
                        .content("content")
                        .challengeId(challenge.getId())
                        .build(), user.getId());

        //then
        Optional<Postscript> postscript = queryPostscriptDbPort.findPostscriptByChallenge(challenge);
        Assertions.assertThat(postscript.isPresent()).isTrue();
        Assertions.assertThat(postscript.get().getContent()).isEqualTo("content");
    }

    @DisplayName("[bad] 후기 생성 시 유저가 악의적으로 다른 유저의 챌린지로 댓글을 남기려고 하는 경우 오류를 던진다.")
    @Test
    public void givenRequestOfStranger_whenCreatePostscript_thenThrowError() throws Exception {
        //given
        User user = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE)
                .nickname("nickname2")
                .oauthId("hellokakao2")
                .kakaoProfileNickname(new KakaoNickname("kakaonick2"))
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("h12314")
                .build());


        System.out.println("user : " + user.getId());
        Challenge challenge = prepareForCreatePostscript(user);
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));
        User stranger = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE)
                .nickname("nick")
                .oauthId("hellokak")
                .kakaoProfileNickname(new KakaoNickname("kak"))
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("h12314")
                .build());

        //when //then
        Assertions.assertThatThrownBy(() -> createPostscriptService.createPostscript(
                CreatePostscriptServiceRequest.builder()
                        .photo(file)
                        .content("content")
                        .challengeId(challenge.getId())
                        .build(), stranger.getId())).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("[bad] 후기 생성 시 이미 리뷰가 있는 챌린지에 다시 생성을 요청 하는 경우 오류를 던진다.")
    @Test
    public void givenChallengeAlreadyContainsPostscriptData_whenCreatePostscript_thenThrowError() throws Exception {
        //given
        User user = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE)
                .nickname("nick")
                .oauthId("hello")
                .kakaoProfileNickname(new KakaoNickname("kakao"))
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("h12")
                .build());


        System.out.println("user : " + user.getId());

        Challenge challenge = prepareForCreatePostscript(user);
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));
        CreatePostscriptServiceRequest request = CreatePostscriptServiceRequest.builder()
                .photo(file)
                .content("content")
                .challengeId(challenge.getId())
                .build();

        createPostscriptService.createPostscript(request, user.getId());

        //when //then
        Assertions.assertThatThrownBy(() -> createPostscriptService.createPostscript(
                        request, user.getId())).isInstanceOf(BadRequestException.class)
                .hasMessage(BadRequestExceptionCode.ALREADY_PARTICIPATE_IN_POSTSCRIPT.getDescriptionMessage());
    }

    private Challenge prepareForCreatePostscript(User user) {
        Product product = Product.builder()
                .price(500000)
                .detail("테스트 프로덕트입니다.")
                .image("https://testUrl.com")
                .isDeleted(false)
                .thumbnail("https://thumbnail.com")
                .build();
        commandProductDbPort.save(product);

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
                .build());

        Account depositAccount = commandAccountDbPort.save(Account.builder()
                .user(user)
                .accountNumber("1234")
                .accountType(AccountType.DEPOSIT)
                .balance(300000L)
                .bank(Bank.MULTI)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .build());

        Account withdrawalAccount = commandAccountDbPort.save(Account.builder()
                .user(user)
                .accountNumber("1234")
                .accountType(AccountType.WITHDRAW)
                .balance(300000L)
                .bank(Bank.MULTI)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .build());

        return challenge;
    }
}