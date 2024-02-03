package com.hanghae.knowledgesharing.controller;


import com.hanghae.knowledgesharing.dto.request.comment.PostCommentRequestDto;
import com.hanghae.knowledgesharing.dto.response.comment.CommentListResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.PostCommentResponseDto;
import com.hanghae.knowledgesharing.repository.CommentRepository;
import com.hanghae.knowledgesharing.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @PostMapping("/{articleId}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(
            @RequestBody @Valid PostCommentRequestDto requestBody,
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostCommentResponseDto> response = commentService.postComment(requestBody, articleId, userId);
        return response;
    }


    @GetMapping("/{articleId}/comments")
    public ResponseEntity<? super CommentListResponseDto> getComments(
            @PathVariable Long articleId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return commentService.getCommentList(articleId, pageable);
    }
}