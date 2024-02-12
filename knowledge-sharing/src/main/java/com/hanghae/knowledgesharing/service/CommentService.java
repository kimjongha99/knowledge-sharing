package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.comment.PatchCommentRequestDto;
import com.hanghae.knowledgesharing.dto.request.comment.PostCommentRequestDto;
import com.hanghae.knowledgesharing.dto.response.comment.CommentListResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.DeleteCommentResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.PatchCommentResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.PostCommentResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto requestDto, Long articleId, String userId);

    ResponseEntity<? super CommentListResponseDto> getCommentList(Long articleId, Pageable pageable);


    ResponseEntity<PatchCommentResponseDto> patchComment(PatchCommentRequestDto requestDto, Long commentId, String userId);


    ResponseEntity<DeleteCommentResponseDto>  deleteComment(Long commentId, String userId);
}
