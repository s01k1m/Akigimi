package com.kangkimleekojangcho.akgimi.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BadRequestExceptionCode {
    INVALID_INPUT("001", "잘못된 입력 값입니다."),
    // attractionId와 attractionType 이 매치가 안된 경우
    NOT_MATCH_ATTRACTION("002", "잘못된 id 입니다."),
    NOT_USER("003", "회원이 아닙니다."),
    NO_REFRESH_TOKEN("004", "리프레쉬 토큰이 없습니다."),
    REFRESH_TOKEN_NOT_EQUAL("005", "DB에 있는 리프레쉬 토큰과 다릅니다."),
    NOT_AVAILIABLE_PROVIDER("006", "유효하지 않은 Oauth Provider입니다."),
    ALREADY_USER("007", "이미 회원 가입된 유저입니다."),
    DUPLICATE_NICKNAME("008", "닉네임이 중복됩니다."),
    NO_ONE_PICTURE("009", "클라이언트로부터 입력 받은 사진이 한 장이 아닙니다."),
    NOT_IMAGE_FORMAT("010", "이미지 파일 포맷이 아닙니다."),
    NO_RESOURCE("011","자원이 존재하지 않습니다"),
    NOT_PARTICIPATE_IN_CHALLENGE("012", "유저가 현재 챌린지에 참여중이지 않습니다."),
    NO_BANK_ACCOUNT("013", "존재하지 않는 계좌입니다."),
    LACK_OF_ACCOUNT_BALANCE("014","계좌의 잔고가 부족합니다."),
    EXCEED_MAXIMUM_BALANCE("015", "계좌에 입금 가능한 최대 금액을 초과하였습니다."),
    NO_PRODUCT("016", "존재하지 않는 물건입니다."),
    ALREADY_PARTICIPATE_IN_CHALLENGE("017", "이미 챌린지 진행중입니다."),
    NO_TRANSFER("018","거래 내역이 존재하지 않습니다."),
    NO_AVAILABLE_ACCOUNT_TYPE("019", "유효하지 않은 계좌 타입입니다."),
    NO_SALT("020", "솔트가 존재하지 않습니다"),
    NOT_AVAILABLE_PASSWORD("021", "패스워드가 맞지 않습니다"),
    NO_AVAILABLE_FRIEND_TYPE("022","친구의 타입이 유효하지 않습니다."),
    PASSWORD_NOT_SETTED("023", "패스워드가 설정되지 않았습니다." ),
    CANNOT_ACTIVATE("024", "회원을 활성화할 수 없습니다."),
    ALREADY_PARTICIPATE_IN_POSTSCRIPT("025", "이미 후기를 작성했습니다");
    private final String code;
    private final String descriptionMessage;
}
