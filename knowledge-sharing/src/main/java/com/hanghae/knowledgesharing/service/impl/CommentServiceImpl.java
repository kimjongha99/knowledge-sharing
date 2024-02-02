package com.hanghae.knowledgesharing.service.impl;

import com.hanghae.knowledgesharing.dto.request.comment.PostCommentRequestDto;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.CommentListResponseDto;
import com.hanghae.knowledgesharing.dto.response.comment.PostCommentResponseDto;
import com.hanghae.knowledgesharing.entity.Article;
import com.hanghae.knowledgesharing.entity.Comment;
import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.repository.ArticleRepository;
import com.hanghae.knowledgesharing.repository.CommentRepository;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto requestDto, Long articleId, String userId) {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) return PostCommentResponseDto.notExistUser();


            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));

            //생성자를 이용해서 만듬
            Comment comment = new Comment(requestDto, article, user);
            commentRepository.save(comment);
            return PostCommentResponseDto.success();

        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity< ? super CommentListResponseDto> getCommentList(Long articleId, Pageable pageable) {
        try {
            // 페이지 매기기를 사용하여 기사에 대한 댓글을 가져옵니다.
            Page<Comment> commentsPage = commentRepository.findAllByArticleId(articleId, pageable);
            List<CommentListResponseDto.CommentDetailDto> commentDetails = commentsPage
                    .getContent()
                    .stream()
                    .map(comment -> new CommentListResponseDto.CommentDetailDto(
                            comment.getId(),
                            comment.getContent(),
                            comment.getArticle().getId(),
                            comment.getUser().getUserId()
                    ))
                    .collect(Collectors.toList());

            Page<CommentListResponseDto.CommentDetailDto> commentDetailPage = new PageImpl<>(commentDetails, pageable, commentsPage.getTotalElements());
            return CommentListResponseDto.success(commentDetailPage);
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response
            return ResponseDto.databaseError();
        }
    }
}
