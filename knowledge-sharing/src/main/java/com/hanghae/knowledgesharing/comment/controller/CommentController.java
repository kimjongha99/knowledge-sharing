package com.hanghae.knowledgesharing.comment.controller;


import com.hanghae.knowledgesharing.comment.dto.request.comment.PatchCommentRequestDto;
import com.hanghae.knowledgesharing.comment.dto.request.comment.PostCommentRequestDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.CommentListResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.DeleteCommentResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.PatchCommentResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.PostCommentResponseDto;
import com.hanghae.knowledgesharing.comment.repository.CommentRepository;
import com.hanghae.knowledgesharing.comment.serivce.CommentService;
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


    @GetMapping("/{articleId}/comment")
    public ResponseEntity<? super CommentListResponseDto> getComments(
            @PathVariable Long articleId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return commentService.getCommentList(articleId, pageable);
    }

    @PatchMapping("/{commentId}/comment")
    public ResponseEntity<PatchCommentResponseDto> patchComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String userId,
            @RequestBody PatchCommentRequestDto requestDto

    ) {
        ResponseEntity<PatchCommentResponseDto> response =commentService.patchComment(requestDto,commentId,userId);
        return response;
    }

    @DeleteMapping("/{commentId}/comment")
    public ResponseEntity<DeleteCommentResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<DeleteCommentResponseDto> response = commentService.deleteComment(commentId, userId);
        return  response;
    }
}
