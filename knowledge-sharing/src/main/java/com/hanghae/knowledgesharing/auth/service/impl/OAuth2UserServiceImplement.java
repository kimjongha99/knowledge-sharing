package com.hanghae.knowledgesharing.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.knowledgesharing.auth.handler.CustomOAuth2User;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        try{ //sout 나중에 지울것
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        User user = null;
        String userId = null;
        String email = null;
        String profileUrl = null;


        // 카카오 로그인시 id 값 가져오기 //
        if(oauthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            // Access nested attributes
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");

                // Profile is nested inside kakaoAccount
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    profileUrl = (String) profile.get("thumbnail_image_url");
                }
            }
            user = new User(userId, email, "kakao",profileUrl);
        }
        // 네이버 로그인시 id 값 가져올때 response로 넘어오면 그안에서 id랑 email 값 가져오기 //
        if(oauthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id").substring(0, 14);
            email = responseMap.get("email");
            profileUrl= responseMap.get("profile_image");


            user = new User(userId, email, "naver",profileUrl);
        }


        userRepository.save(user);
        // CustomOAuth2User Entity에서 getName으로 userId 꺼내오기 //
        return new CustomOAuth2User(userId);

    }
    }

