package com.hanghae.knowledgesharing.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchCommentRequestDto {

    @NotBlank
    private String content;



}
