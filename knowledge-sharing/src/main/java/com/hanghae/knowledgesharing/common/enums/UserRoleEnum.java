package com.hanghae.knowledgesharing.common.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
        USER(Authority.USER, "유저"),  // 사용자 권한
    ADMIN(Authority.ADMIN, "마스터");    // 마스터 권한

    private final String authority;
    private final String people;

    UserRoleEnum(String authority, String people) {
        this.authority = authority;
        this.people = people;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";  // 유저
        public static final String ADMIN = "ROLE_ADMIN";      // 마스터 계정
    }
}