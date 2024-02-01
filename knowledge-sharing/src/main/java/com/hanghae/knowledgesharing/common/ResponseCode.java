package com.hanghae.knowledgesharing.common;

public interface ResponseCode {


    String SUCCESS = "SU";

    String VALIDATION_FAIL = "VF";
    String DUPLICATION_ID = "DI";

    String SIGN_IN_FAIL = "SF";
    String CERTIFICATION_FAIL = "CF";

    String MAIL_FAIL = "MF";
    String DATABASE_ERROR = "DBE";

    String REFRESH_TOKEN_INVALID = "RTI";
    String REFRESH_TOKEN_EXPIRED = "RTE";

    String NOT_EXISTED_USER = "NEU";

    String PREVIOUS_PASSWORD ="PRP";


    String HASHTAG_CREATION_FAIL = "HCF";
    String HASHTAG_NOT_FOUND = "HNF";
    String HASHTAG_VALIDATION_FAIL = "HVF";

    String ARTICLE_FAIL = "AF";
    String NOT_EXISTED_ARTICLE = "NA";
    String PERMISSION_FAIL ="PF" ;
}
