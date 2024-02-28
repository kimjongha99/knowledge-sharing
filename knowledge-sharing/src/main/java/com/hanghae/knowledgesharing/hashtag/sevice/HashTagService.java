package com.hanghae.knowledgesharing.hashtag.sevice;

import com.hanghae.knowledgesharing.hashtag.dto.response.SearchByHashtagResponseDto;

public interface HashTagService {

    SearchByHashtagResponseDto searchByHashtag(String tagName);


}
