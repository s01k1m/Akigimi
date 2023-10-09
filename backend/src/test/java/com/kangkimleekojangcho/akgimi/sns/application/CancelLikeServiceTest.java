package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CancelLikeServiceTest extends SnsServiceIntegrationTestSupport {

    @Autowired
    private CancelLikeService cancelLikeService;
    @Autowired
    private QueryLikeDbPort queryLikeDbPort;
    @Autowired
    private CommandLikeDbPort commandLikeDbPort;
    @Autowired
    private CommandCountLikeDbPort commandCountLikeDbPort;
    @Autowired
    private QueryCountLikeDbPort queryCountLikeDbPort;

    private static final long ADDED_COUNT_OF_LIKE = 1;

    @DisplayName("[happy] 유저가 자신의 게시글에 좋아요 취소 요청 시 정상적으로 좋아요를 완료한다.")
    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    public void givenValidFeedAndUser_whenCancelLike_thenExecute(boolean isPublic) throws Exception {
        //given
        User ownerOfFeed = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build());

        Feed feedToLike = prepareForCancelLikeFeed(ownerOfFeed, isPublic);
        commandLikeDbPort.save(Like.builder()
                .user(ownerOfFeed)
                .feed(feedToLike)
                .build());

        commandCountLikeDbPort.save(
                CountLike.builder()
                        .feed(feedToLike)
                        .likeCount(ADDED_COUNT_OF_LIKE)
                        .build()
        );

        //when
        cancelLikeService.execute(ownerOfFeed.getId(), feedToLike.getFeedId());


        //then
        Optional<Like> result = queryLikeDbPort.findByUserIdAndFeedId(ownerOfFeed.getId(), feedToLike.getFeedId());
        Optional<CountLike> countLike = queryCountLikeDbPort.findByFeed(feedToLike);
        assertThat(countLike).isPresent();
        assertThat(countLike.get().getLikeCount()).isEqualTo(ADDED_COUNT_OF_LIKE-1);
        assertThat(result).isEmpty();
    }

    @DisplayName("[happy] 유저가 자신의 글이 아닌 타인의 공개된 게시글에 좋아요 취소 요청 시 요청을 올바르게 수행한다..")
    @Test
    public void givenvalidFeed_whenCancelLikeOtherUsersFeed_thenExecute() throws Exception {
        //given
        User ownerOfFeed = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build());
        Feed feedToLike = prepareForCancelLikeFeed(ownerOfFeed, true);

        User userWantToCancelLike = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("Like하고 싶어요")
                .oauthId("abcde")
                .build());

        commandLikeDbPort.save(Like.builder()
                .user(userWantToCancelLike)
                .feed(feedToLike)
                .build());

        commandCountLikeDbPort.save(
                CountLike.builder()
                        .feed(feedToLike)
                        .likeCount(ADDED_COUNT_OF_LIKE)
                        .build()
        );

        //when
        cancelLikeService.execute(userWantToCancelLike.getId(), feedToLike.getFeedId());

        //then
        Optional<Like> result = queryLikeDbPort.findByUserIdAndFeedId(userWantToCancelLike.getId(), feedToLike.getFeedId());
        Optional<CountLike> countLike = queryCountLikeDbPort.findByFeed(feedToLike);
        assertThat(countLike).isPresent();
        assertThat(countLike.get().getLikeCount()).isEqualTo(ADDED_COUNT_OF_LIKE-1);
        assertThat(result).isEmpty();
    }

    @DisplayName("[bad] 유저가 자신의 글이 아닌 비공개된 게시글, 삭제된 게시글에 좋아요 취소 요청 시 에러를 던진다.")
    @CsvSource(value = {"true:false", "false:false"}, delimiter = ':')
    @ParameterizedTest
    public void givenInvalidFeed_whenUserCancelLike_thenExecute(boolean isDeleted, boolean isPublic) throws Exception {
        //given
        User ownerOfFeed = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("돌아다니는 카카오")
                .oauthId("abcde")
                .build());
        Feed feedToLike = prepareInvalidFeedForCancelLike(ownerOfFeed, isDeleted, isPublic);

        User userWantToCancelLike = commandUserDbPort.save(User.builder()
                .kakaoProfileNickname(new KakaoNickname("카카오프로필"))
                .userState(UserState.ACTIVE)
                .nickname("Like하고 싶어요")
                .oauthId("abcde")
                .build());

        commandLikeDbPort.save(Like.builder()
                .user(userWantToCancelLike)
                .feed(feedToLike)
                .build());

        //when then
        Assertions.assertThatThrownBy(() ->
                        cancelLikeService.execute(userWantToCancelLike.getId(), feedToLike.getFeedId()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(BadRequestExceptionCode.NOT_FEED.getDescriptionMessage());
    }

    private Feed prepareInvalidFeedForCancelLike(User user, boolean isDeleted, boolean isPublic) {
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

        return commandFeedDbPort.save(
                Feed.builder()
                        .user(user)
                        .price(30000L)
                        .accumulatedAmount(300000L)
                        .content("content")
                        .challenge(challenge)
                        .isPublic(isPublic)
                        .isDeleted(isDeleted)
                        .imageUrl("http://ee")
                        .place("여기서 아꼈다!")
                        .notPurchasedItem("그림")
                        .build()
        );
    }

    private Feed prepareForCancelLikeFeed(User user, boolean isPublic) {
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

        return commandFeedDbPort.save(
                Feed.builder()
                        .user(user)
                        .price(30000L)
                        .accumulatedAmount(300000L)
                        .content("content")
                        .challenge(challenge)
                        .isPublic(isPublic)
                        .isDeleted(false)
                        .imageUrl("http://ee")
                        .place("여기서 아꼈다!")
                        .notPurchasedItem("그림")
                        .build()
        );
    }
}