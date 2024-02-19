package com.hanghae.knowledgesharing.comment.controller;

import com.hanghae.knowledgesharing.comment.dto.request.PostCommentRequestDto;
import com.hanghae.knowledgesharing.comment.dto.response.CommentListResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.DeleteCommentResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.PatchCommentResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.PostCommentResponseDto;
import com.hanghae.knowledgesharing.comment.sevice.CommentService;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {


    private final CommentService commentService;


    @PostMapping("/{articleId}/comment")
    public ResponseDto<PostCommentResponseDto> postComment(
            @RequestBody @Valid PostCommentRequestDto requestDto,
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal String userId
    ) {

        PostCommentResponseDto response = commentService.postComment(requestDto, articleId, userId);
        return ResponseDto.success(response);
    }


    @GetMapping("/{articleId}/comment")
    public ResponseDto<Page<CommentListResponseDto>> getComments(
            @PathVariable Long articleId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable

    ) {
        Page<CommentListResponseDto> response = commentService.getCommentList(articleId, pageable);
        return ResponseDto.success(response);

    }

    @PatchMapping("/{commentId}/comment")
    public ResponseDto<PatchCommentResponseDto> patchComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String userId,
            @RequestBody Map<String, Object> body
            ) {
        String content = (String) body.get("content");

        PatchCommentResponseDto responseDto = commentService.patchComment(content, commentId, userId);
        return ResponseDto.success(responseDto);
    }
    @DeleteMapping("/{commentId}/comment")
    public ResponseDto<DeleteCommentResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String userId
    ) {
        DeleteCommentResponseDto response = commentService.deleteComment(commentId, userId);
        return ResponseDto.success(response);
    }

}
