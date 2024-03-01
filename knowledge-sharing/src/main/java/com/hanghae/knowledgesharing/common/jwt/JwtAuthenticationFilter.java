package com.hanghae.knowledgesharing.common.jwt;


import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;
    // 인증을 건너뛰어야 하는 경로 목록을 정의합니다.
    private static final Set<String> SKIP_PATHS = Set.of(
            "GET /api/v1/articles/{articleId}",
            "GET /api/v1/comments/{articleId}/comment"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String requestUri = request.getRequestURI();
            String requestMethod = request.getMethod();

            // 요청 URI가 인증을 건너뛰어야 하는 경로 목록에 있는지 확인합니다.
            // Check if the combined method and URI should be skipped
            boolean skip = SKIP_PATHS.stream().anyMatch(path -> {
                String[] parts = path.split(" ", 2); // Split into method and path
                if (parts.length < 2) return false; // Safety check
                String method = parts[0];
                String uriTemplate = parts[1];
                return method.equalsIgnoreCase(requestMethod) && new UriTemplate(uriTemplate).matches(requestUri);
            });


            String token = parseBearerToken(request);
            if(token == null) { // token이 null이면 밑에 작업 진행하지말고 doFilter로 다음 필터로 넘기고 return //
                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtProvider.validate(token); // token을 jwtProvider로 validate로 검증 -> 검증 통해서 userId 꺼내옴
            if(userId == null) { // userId 가 null이면 doFilter로 검증
                filterChain.doFilter(request, response);
                return;
            }

            User user = userRepository.findByUserId(userId); // user 정보 꺼내오기
            String role = user.getRole().getAuthority(); // role : ROLE_USER, ROLE_ADMIN // 권한 지정

            // ROLE_DEVELOPER, ROLE_BOSS ... etc 권한의 배열형태
            List<GrantedAuthority> authorities = new ArrayList<>(); // 권한 리스트
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 빈 context 만들어주기
            AbstractAuthenticationToken authenticationToken =  // context 안에 담을 token 만들기
                    new UsernamePasswordAuthenticationToken(userId, null, authorities); // object를 받을수있음 // 유저정보, 비밀번호, 권한 리스트
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // request detail에 넣어줌

            securityContext.setAuthentication(authenticationToken); // context에 token 넣어줌
            SecurityContextHolder.setContext(securityContext); // context를 등록시켜줌

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // 다음 filter로 넘어가도록 해줌
        filterChain.doFilter(request, response);
    }
    // request 객체로부터 token 꺼내오는 작업 //
    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization"); // request의 header로 부터 Authorization 가져옴//
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization) return null;
        boolean isBearer = authorization.startsWith("Bearer "); // 'Bearer '로 시작하는지 확인
        if(!isBearer) return null;
        String token = authorization.substring(7);
        return token;

    }


}
