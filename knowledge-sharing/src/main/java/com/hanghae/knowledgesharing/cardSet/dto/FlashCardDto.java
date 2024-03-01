package com.hanghae.knowledgesharing.cardSet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlashCardDto {

    private Long flashCardId;
    private String realId;
    private String term;
    private String definition;

}