package com.hanghae.knowledgesharing.common.entity;

import com.hanghae.knowledgesharing.auth.dto.request.auth.SignUpRequestDto;
import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name="user")
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR(30)")
    private String userId;
    @Column(name = "password") //next-do: 길이설정
    private String password;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "type", columnDefinition = "VARCHAR(50)")
    private String type;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(50)")
    private UserRoleEnum role;

    @Column(columnDefinition = "varchar(255)", nullable = true)
    private String profileImageUrl;

    @Column(name = "refresh_token")
    private String refreshToken;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true)//너
    private List<Follow> followings = new ArrayList<>();


    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true) //나
    private List<Follow> followers = new ArrayList<>();


    public User(SignUpRequestDto dto) {
        this.userId = dto.getId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.type = "app";
        this.role = UserRoleEnum.USER;
    }

    public User(String userId, String email, String type, String profileUrl) {
        this.userId = userId;
        this.password = "Passw0rd";
        this.email = email;
        this.type = type;
        this.profileImageUrl = profileUrl;
        this.role = UserRoleEnum.USER;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setPassword(String encode) {
        this.password = encode;
    }

    public void setRole(UserRoleEnum roleEnum) {
        this.role = roleEnum;
    }
}