package com.hanghae.knowledgesharing.auth.handler;

import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.jwt.JwtProvider;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        String refreshToken = jwtProvider.createRefreshToken(userId);


        User user = userRepository.findByUserId(userId);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

//        long refreshTokenExpiryDuration = 7 * 24 * 60 * 60;
//
//        response.sendRedirect("https://knowledge-sharing-two.vercel.app/ "+ accessToken + "/3600");
        // 7일의 시간을 초단위로 환산
        long refreshExpirySeconds = 7 * 24 * 60 * 60; // 7 days in seconds

        String redirectURL = String.format("https://knowledge-sharing-two.vercel.app/%s/3600/%s/%d",
                URLEncoder.encode(accessToken, StandardCharsets.UTF_8),
                URLEncoder.encode(refreshToken, StandardCharsets.UTF_8),
                refreshExpirySeconds);

        response.sendRedirect(redirectURL);
    }

}
