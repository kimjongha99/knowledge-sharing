package com.hanghae.knowledgesharing.hashtag.sevice;

import com.hanghae.knowledgesharing.hashtag.dto.response.SearchByHashtagResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface HashTagService {




    @Transactional(readOnly = true)
    SearchByHashtagResponseDto searchByHashtag(String tagName, Pageable pageable);
}
