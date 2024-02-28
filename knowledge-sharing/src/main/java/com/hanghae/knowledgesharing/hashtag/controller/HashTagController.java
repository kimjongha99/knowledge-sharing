package com.hanghae.knowledgesharing.hashtag.controller;

import com.hanghae.knowledgesharing.hashtag.dto.response.SearchByHashtagResponseDto;
import com.hanghae.knowledgesharing.hashtag.sevice.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hashtag")
@RequiredArgsConstructor
public class HashTagController {
    private final HashTagService hashtagService;


    @GetMapping("/{tagName}")
    public ResponseEntity<SearchByHashtagResponseDto> searchByHashtag(@PathVariable String tagName) {
        SearchByHashtagResponseDto result = hashtagService.searchByHashtag(tagName);
        return ResponseEntity.ok(result);
    }

}
