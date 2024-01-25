package com.hanghae.knowledgesharing.provider;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component  // 제어 역전 을 통해 의존성 주입할수있도록  @Component는 빈을 따로 등록하지않아도 자체 주입가능

public class JwtProvider {
  @Value("${secret-key}")
  private String secretKey;

  public String create(String userId) {  // userId를 받아와서 토큰 생성
    Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); //만료기간은 한시간으로 만듬
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // 시크릿키 만들기
    // json wab token 생성
    String jwt = Jwts.builder()

            //토큰 세부정보JWT는 제목(setSubject(userId))으로 userId를 포함하고 발급 시간(setIssuedAt(new Date())) 및 만료 시간(setExpiration(expiredDate)을 포함합니다. )).

            .signWith(key, SignatureAlgorithm.HS256)
            .setSubject(userId).setIssuedAt(new Date()).setExpiration(expiredDate)
            .compact();

    return jwt;
  }


  // jwt 검증
  public String validate(String jwt) {

    String subject = null;
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    try {
      subject = Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(jwt)
              .getBody()
              .getSubject();

    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
    return subject;
  }
}
