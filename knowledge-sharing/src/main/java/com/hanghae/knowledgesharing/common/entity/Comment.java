package com.hanghae.knowledgesharing.common.entity;

import com.hanghae.knowledgesharing.comment.dto.request.PostCommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class Comment extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    // 댓글과 사용자(User) 간의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Comment(PostCommentRequestDto dto, Article article, User user) {
        this.content = dto.getContent();
        this.article = article;
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
