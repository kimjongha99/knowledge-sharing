package com.hanghae.knowledgesharing.entity;

import com.hanghae.knowledgesharing.dto.request.auth.SignUpRequestDto;
import com.hanghae.knowledgesharing.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name="user")
public class User {

    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR(30)")
    private  String userId;
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

    public  User(SignUpRequestDto dto) {
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
        this.profileImageUrl=profileUrl;
        this.role= UserRoleEnum.USER;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setPassword(String encode) {
        this.password=encode;
    }
}
