package com.hanghae.knowledgesharing.comment.sevice.Impl;

import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.comment.dto.request.PostCommentRequestDto;
import com.hanghae.knowledgesharing.comment.dto.response.CommentListResponseDto;
import com.hanghae.knowledgesharing.comment.dto.response.PostCommentResponseDto;
import com.hanghae.knowledgesharing.comment.repository.CommentRepository;
import com.hanghae.knowledgesharing.comment.sevice.CommentService;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.Comment;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl  implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override


    public PostCommentResponseDto postComment(PostCommentRequestDto requestDto, Long articleId, String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ArticleNotFound));
        //생성자를 이용해서 만듬
        Comment comment = new Comment(requestDto, article, user);
        commentRepository.save(comment);


        // 성공 메시지 생성
        String successMessage = "댓글 작성 성공.";
        return new PostCommentResponseDto(successMessage);

    }

    @Override
    public Page<CommentListResponseDto> getCommentList(Long articleId, Pageable pageable) {
        // 페이지 매기기를 사용하여 기사에 대한 댓글을 가져옵니다.
        Page<Comment> commentsPage = commentRepository.findAllByArticleId(articleId, pageable);
        Page<CommentListResponseDto> resultPage = commentsPage.map(comment ->{
                CommentListResponseDto dto  = new CommentListResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getArticle().getId(),
                comment.getUser().getUserId() // User 엔티티에서 ID를 가져오는 메소드 가정
                );
            return dto;
        });

        return  resultPage;
    }
}
