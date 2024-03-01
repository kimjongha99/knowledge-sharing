package com.hanghae.knowledgesharing.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeListResponseDto {

    private List<UserInfo> users;
    private int page;
    private int size;
    private long totalElements;


}
