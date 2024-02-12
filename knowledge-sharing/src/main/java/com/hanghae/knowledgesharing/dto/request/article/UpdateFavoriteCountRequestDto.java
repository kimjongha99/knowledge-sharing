package com.hanghae.knowledgesharing.dto.request.article;


import com.hanghae.knowledgesharing.enums.FavoriteActionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateFavoriteCountRequestDto {

    private FavoriteActionType actionType;


}
