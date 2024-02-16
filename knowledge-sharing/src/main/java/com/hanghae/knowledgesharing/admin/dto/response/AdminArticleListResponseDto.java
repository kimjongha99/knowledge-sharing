package com.hanghae.knowledgesharing.admin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
@Getter
@NoArgsConstructor
public class AdminArticleListResponseDto {

    private Page<AdminArticleDetail> articles;


    public AdminArticleListResponseDto(Page<AdminArticleDetail> articles) {
        this.articles = articles;
    }

}
