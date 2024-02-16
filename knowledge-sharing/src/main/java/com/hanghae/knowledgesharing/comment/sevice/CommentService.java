package com.hanghae.knowledgesharing.comment.sevice;

import com.hanghae.knowledgesharing.comment.dto.request.PostCommentRequestDto;
import com.hanghae.knowledgesharing.comment.dto.response.CommentListResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.PostCommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {



    PostCommentResponseDto postComment(PostCommentRequestDto requestDto, Long articleId, String userId);

    Page<CommentListResponseDto> getCommentList(Long articleId, Pageable pageable);
}
