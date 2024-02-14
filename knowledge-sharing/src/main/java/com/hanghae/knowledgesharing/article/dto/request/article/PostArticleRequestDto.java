package com.hanghae.knowledgesharing.article.dto.request.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostArticleRequestDto {

    @NotBlank
    @Size(max = 500)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String content;


    private List<String> hashtags;


    private List<String> imageUrls;
}
