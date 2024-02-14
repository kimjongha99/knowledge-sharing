package com.hanghae.knowledgesharing.article.dto.request.article;


import com.hanghae.knowledgesharing.common.enums.FavoriteActionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateFavoriteCountRequestDto {

    private FavoriteActionType actionType;


}
