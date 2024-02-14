package com.hanghae.knowledgesharing.comment.serivce.impl;

import com.hanghae.knowledgesharing.comment.dto.request.comment.PatchCommentRequestDto;
import com.hanghae.knowledgesharing.comment.dto.request.comment.PostCommentRequestDto;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.CommentListResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.DeleteCommentResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.PatchCommentResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.comment.PostCommentResponseDto;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.Comment;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.comment.repository.CommentRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import com.hanghae.knowledgesharing.comment.serivce.CommentService;
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

    @Override
    public ResponseEntity<PatchCommentResponseDto> patchComment(PatchCommentRequestDto requestDto, Long commentId, String userId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + commentId));
            User user = userRepository.findByUserId(userId);
            if (user == null) return PatchCommentResponseDto.notExistUser();
            // 2. Authorization Check
            if (!comment.getUser().equals(user)) {
                return PatchCommentResponseDto.notAuthorized(); // Implement this method similarly to notExistUser
            }
            // 3. Update Comment
            comment.setContent(requestDto.getContent());
            commentRepository.save(comment);

            // 4. Success Response
            return PatchCommentResponseDto.success();

        }catch (Exception e) {
            // 5. General Error Handling
            return PatchCommentResponseDto.databaseError();
        }

    }

    @Override
    public ResponseEntity<DeleteCommentResponseDto> deleteComment(Long commentId, String userId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + commentId));
            User user = userRepository.findByUserId(userId);
            if (user == null) return DeleteCommentResponseDto.notExistUser();


            commentRepository.delete(comment);
            return DeleteCommentResponseDto.success();

        } catch (Exception e) {
            e.printStackTrace();
            return DeleteCommentResponseDto.databaseError();
        }
    }
}
