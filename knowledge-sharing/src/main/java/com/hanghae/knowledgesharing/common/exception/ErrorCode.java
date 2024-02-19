package com.hanghae.knowledgesharing.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

//  예시   DuplicatedCategory(HttpStatus.BAD_REQUEST, "이미 존재하는 카테고리 입니다.");

    InValidException(HttpStatus.BAD_REQUEST, "값이 잘못되었습니다."),
    ExistId(HttpStatus.CONFLICT,"유저가 존재합니다." ),

    MailSendFail(HttpStatus.SERVICE_UNAVAILABLE, "메일 전송 실패 입니다."),

    DatabaseError(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 베이스 에러입니다"),

    CertificationNotFound(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    CertificationMismatch(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다."),
    UserNotFound(HttpStatus.NOT_FOUND,"유저를 찾을수없습니다." ),
    PasswordFail(HttpStatus.UNAUTHORIZED,"비밀번호 실패입니다." ),
    RefreshTokenExpire(HttpStatus.UNAUTHORIZED,"리프래쉬 토큰 만료입니다 재로그인하세요." ),
    RefreshTokenFail(HttpStatus.UNAUTHORIZED, "리프래쉬 검증 실패."),
    EqualPassword(HttpStatus.UNAUTHORIZED, "이전 비밀번호와 동일합니다"),
    PermissionDenied(HttpStatus.UNAUTHORIZED, "당신의 권한이 아닙니다." ),
    ArticleNotFound(HttpStatus.FORBIDDEN, " 아티클을 찾을수없습니다 ." ),
    InvalidActionType(HttpStatus.BAD_REQUEST, "타입이 정확하지않습니다" ),
    CommentNotFound(HttpStatus.BAD_REQUEST,"댓글을 찾을수없습니다." );


    private final HttpStatus httpStatus;
    private final String message;
}