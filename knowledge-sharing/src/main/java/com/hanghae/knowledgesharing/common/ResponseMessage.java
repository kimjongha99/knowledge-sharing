package com.hanghae.knowledgesharing.common;

public interface ResponseMessage {
    String SUCCESS = "Success";

    String VALIDATION_FAIL = "Validation failed";
    String DUPLICATION_ID = "Duplicate Id";

    String SIGN_IN_FAIL = "Login information mismatch";
    String CERTIFICATION_FAIL = "Certification failed";

    String MAIL_FAIL = "Mail send failed";
    String DATABASE_ERROR = "Database error";

    String REFRESH_TOKEN_INVALID = "새로고침 토큰이 잘못되었습니다.";
    String REFRESH_TOKEN_EXPIRED = "새로고침 토큰이 만료되었습니다. 다시 로그인하세요";
}