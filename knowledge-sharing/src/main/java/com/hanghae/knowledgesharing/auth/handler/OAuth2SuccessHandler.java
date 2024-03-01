package com.hanghae.knowledgesharing.auth.handler;

import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.jwt.JwtProvider;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        String accessToken = jwtProvider.create(userId);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setSecure(false); // Note: Set to false if you are testing over HTTP in development environment, true for production
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3600); // Expiration time should match the JWT expiration
        response.addCookie(accessTokenCookie);


        String refreshToken = jwtProvider.createRefreshToken(userId);
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(false);
        refreshTokenCookie.setSecure(false); // Note: Set to false if you are testing over HTTP in development environment, true for production
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days for refresh token
        response.addCookie(refreshTokenCookie);



        User user = userRepository.findByUserId(userId);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);



        response.sendRedirect("https://knowledge-sharing-98a68ftp0-kimjongha99s-projects.vercel.app/");

    }

}
