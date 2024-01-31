package com.hanghae.knowledgesharing.handler;

import com.hanghae.knowledgesharing.entity.CustomOAuth2User;
import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.provider.JwtProvider;
import com.hanghae.knowledgesharing.repository.UserRepository;
import jakarta.servlet.ServletException;
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
        String refreshToken = jwtProvider.createRefreshToken(userId);

        User user = userRepository.findByUserId(userId);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        response.sendRedirect("http://localhost:3000/auth/oauth-response/" + accessToken + "/3600");

    }

}
