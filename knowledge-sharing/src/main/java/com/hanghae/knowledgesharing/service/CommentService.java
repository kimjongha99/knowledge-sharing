package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.comment.PostCommentRequestDto;
import com.hanghae.knowledgesharing.dto.response.comment.CommentListResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.PostCommentResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto requestDto, Long articleId, String userId);

    ResponseEntity<? super CommentListResponseDto> getCommentList(Long articleId, Pageable pageable);

//    ResponseEntity<ResponseDto> getCommentList(Long articleId, CommentListRequestDto requestDto);
}
