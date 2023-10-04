package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfFeedWrittenByFollowerRequestServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import com.kangkimleekojangcho.akgimi.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class GetBunchOfFeedWrittenByFollowerRequestServiceTest extends SnsServiceIntegrationTestSupport {

    @Autowired
    private GetBunchOfFeedWrittenByFollowerRequestService getBunchOfFeedWrittenByFollowerRequestService;

    @Autowired
    private CommandCountLikeDbPort commandCountLikeDbPort;

    private static final int NUMBER_OF_PUBLIC = 10;
    private static final Long DEFAULT_LIKE_COUNT = 0L;

    @DisplayName("[happy]팔로우 팔로잉이 없는 경우 자신의 글만을 반환한다.")
    @CsvSource(value = {"100:20", "10:10", "0:0"}, delimiter = ':')
    @ParameterizedTest
    void givenUserWithNoRelation_whenRequestGetBunchOfFeed_thenReturnData(int size, int answer) throws Exception {
        /**
         * 팔로우 된 사람의 게시글
         * 본인의 게시글
         */
        //given
        GetBunchOfFeedWrittenByFollowerRequestServiceRequest getBunchOfFeedServiceRequest =
                GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                        .lastFeedId(Long.MAX_VALUE)
                        .numberOfFeed(size)
                        .build();


        User user = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("stranger")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthStranger")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(user);

        //when
        GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                user.getId(), getBunchOfFeedServiceRequest);

        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo().size()).isEqualTo(answer);
    }

    @DisplayName("[happy]피드를 요청했을 때 정확한 데이터가 주어지면 요청에 따라 유저와 팔로워의 피드를 최신순으로 반환한다.")
    @MethodSource("givenCountAndAnswerWhenFollowOnly")
    @ParameterizedTest
    void givenValidData_whenRequestGetBunchOfFeed_thenReturnData(
            int size,
            int answer
    ) throws Exception {
        /**
         * 팔로우 된 사람의 게시글
         * 본인의 게시글
         */
        //given
        GetBunchOfFeedWrittenByFollowerRequestServiceRequest getBunchOfFeedServiceRequest =
                GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                        .lastFeedId(Long.MAX_VALUE)
                        .numberOfFeed(size)
                        .build();

        User followee = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("followee"))
                .nickname("followee")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthFollowee")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());

        followee = commandUserDbPort.save(followee);
        prepareForSelectFeed(followee);
        prepareForSelectFeed(followee);


        User follower = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("hello")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthFollower")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(follower);
        prepareForSelectFeed(follower);

        User stranger = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("stranger")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthStranger")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(stranger);
        prepareForSelectFeed(stranger);

        Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .followTime(LocalDateTime.now()).build();
        commandFollowDbPort.save(follow);

        //when
        GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                follower.getId(), getBunchOfFeedServiceRequest);

        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo().size()).isEqualTo(answer);
    }

    private static Stream<Arguments> givenCountAndAnswerWhenFollowOnly() {
        return Stream.of(
                Arguments.of(
                        100, 60
                ),
                Arguments.of(
                        0, 0
                ),
                Arguments.of(
                        10, 10
                )
        );
    }


    @DisplayName("[happy]서로 팔로우 중인 유저들에 대해 정확한 데이터가 주어지면 요청에 따라 유저와 팔로워의 피드를 최신순으로 반환한다.")
    @MethodSource("givenCountAndAnswerWhenFollowEachOther")
    @ParameterizedTest
    void givenUserFollowEachOther_whenRequestGetBunchOfFeed_thenReturnData(
            int size,
            int answer
    ) throws Exception {
        //given
        GetBunchOfFeedWrittenByFollowerRequestServiceRequest getBunchOfFeedServiceRequest =
                GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                        .lastFeedId(Long.MAX_VALUE)
                        .numberOfFeed(size)
                        .build();

        User followee = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("followee"))
                .nickname("followee")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthFollowee")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());

        followee = commandUserDbPort.save(followee);
        prepareForSelectFeed(followee);


        User follower = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("hello")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthFollower")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(follower);

        User stranger = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("stranger")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthStranger")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(stranger);

        Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .followTime(LocalDateTime.now()).build();
        commandFollowDbPort.save(follow);
        follow = Follow.builder()
                .follower(followee)
                .followee(follower)
                .followTime(LocalDateTime.now()).build();
        commandFollowDbPort.save(follow);


        //when
        GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                follower.getId(), getBunchOfFeedServiceRequest);

        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo().size()).isEqualTo(answer);
    }

    private static Stream<Arguments> givenCountAndAnswerWhenFollowEachOther() {
        return Stream.of(
                Arguments.of(
                        100, 30
                ),
                Arguments.of(
                        0, 0
                ),
                Arguments.of(
                        10, 10
                )
        );
    }


    @DisplayName("[happy]좋아요가 있는 피드의 경우 좋아요 표기를 true값으로 반환한다.")
    @MethodSource("givenLikeSizeWithAnswer")
    @ParameterizedTest
    void givenLikedFeed_whenRequestGetBunchOfFeed_thenReturnFeedWithLike(
            int size,
            int answer
    ) throws Exception {
        //given
        GetBunchOfFeedWrittenByFollowerRequestServiceRequest getBunchOfFeedServiceRequest =
                GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                        .lastFeedId(Long.MAX_VALUE)
                        .numberOfFeed(size)
                        .build();

        User followee = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("followee"))
                .nickname("followee")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthFollowee")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());

        followee = commandUserDbPort.save(followee);


        User follower = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("hello")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthFollower")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(follower);
        prepareForSelectFeedWithLike(followee, follower);

        User stranger = commandUserDbPort.save(User.builder()
                .userState(UserState.ACTIVE) //TODO: active에 따라서도 나눠야 함.
                .kakaoProfileNickname(new KakaoNickname("ddd"))
                .nickname("stranger")
                .profileImageUrl("http:aa.com")
                .oauthId("oauthStranger")
                .oidcProvider(OidcProvider.KAKAO)
                .simplePassword("1234")
                .build());
        prepareForSelectFeed(stranger);

        Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .followTime(LocalDateTime.now()).build();
        commandFollowDbPort.save(follow);
        follow = Follow.builder()
                .follower(followee)
                .followee(follower)
                .followTime(LocalDateTime.now()).build();
        commandFollowDbPort.save(follow);


        //when
        GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfReceiptServiceResponse
                = getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                follower.getId(), getBunchOfFeedServiceRequest);

        for (BriefFeedInfo info : getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo()) {
            System.out.println(info.isLikedFeed());
        }
        //then
        assertThat(getBunchOfReceiptServiceResponse.bunchOfBriefFeedInfo()).isNotNull();
        assertThat(getBunchOfReceiptServiceResponse
                .bunchOfBriefFeedInfo().stream()
                .filter(element -> element.isLikedFeed().equals(true)).count()).isEqualTo(answer);

    }


    private static Stream<Arguments> givenLikeSizeWithAnswer() {
        return Stream.of(
                Arguments.of(
                        100, 10
                ),
                Arguments.of(
                        0, 0
                ),
                Arguments.of(
                        10, 10
                )
        );
    }

    private void prepareForSelectFeedWithLike(User user, User likeUser) {
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


        createPublicFeedWithLike(challenge, user, likeUser);
        createNotPublicFeed(challenge, user);
    }


    private void createPublicFeedWithLike(Challenge challenge, User user, User likeUser) {
        for (long i = 0; i < NUMBER_OF_PUBLIC; i++) {
            Feed feed = commandFeedDbPort.save(
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

            commandCountLikeDbPort.save(CountLike.builder()
                    .count(DEFAULT_LIKE_COUNT + 1)
                    .feed(feed)
                    .build());

            commandLikeDbPort.save(
                    Like.builder()
                            .feed(feed)
                            .user(likeUser)
                            .build()
            );
        }
    }


    private void prepareForSelectFeed(User user) {
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
            Feed feed = commandFeedDbPort.save(
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
            commandCountLikeDbPort.save(CountLike.builder()
                    .count(DEFAULT_LIKE_COUNT)
                    .feed(feed)
                    .build());
        }
    }

    private void createNotPublicFeed(Challenge challenge, User user) {
        for (long i = 0; i < NUMBER_OF_PUBLIC; i++) {
            Feed feed = commandFeedDbPort.save(
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
            commandCountLikeDbPort.save(CountLike.builder()
                    .count(DEFAULT_LIKE_COUNT)
                    .feed(feed)
                    .build());
        }
    }
}