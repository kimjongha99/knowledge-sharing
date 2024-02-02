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
    String NOT_EXISTED_USER = "유저가 존재하지않습니다";
    String PREVIOUS_PASSWORD ="이전 비밀번호와 동일합니다.";

    String HASHTAG_CREATION_FAIL = "해시태그 생성 실패.";
    String HASHTAG_NOT_FOUND = "해시태그를 찾을 수 없습니다";
    String HASHTAG_VALIDATION_FAIL = "해시태그 확인 실패";

    String ARTICLE_FAIL = " 게시글 조회실패했습니다." ;
    String NOT_EXISTED_ARTICLE = "보드가 존재하지않습니다";
    String PERMISSION_FAIL = "허가 실패입니다.";
    String COMMENTS_NOT_FOUND = "댓글 찾을 수없음 404";
    String NO_PERMISSION_TO_VIEW_COMMENTS =  "코멘트 허가 실패";
}