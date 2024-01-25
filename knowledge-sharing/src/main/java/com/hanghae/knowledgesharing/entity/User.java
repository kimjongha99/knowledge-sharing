package com.hanghae.knowledgesharing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "password", columnDefinition = "VARCHAR(30)")
    private  String password;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    private String type;
    @Column(name = "role", columnDefinition = "VARCHAR(10)")
    private String role;



}
