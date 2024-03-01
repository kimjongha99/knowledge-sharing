package com.hanghae.knowledgesharing.common.util.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

@Slf4j // Lombok의 로거, 클래스 레벨에서 로그를 사용할 수 있게 해주는 어노테이션
@Aspect // 이 클래스를 Aspect로 정의
@Component // 스프링의 빈으로 등록
@RequiredArgsConstructor // Lombok, 초기화되지 않은 final 필드나, @NonNull 어노테이션이 붙은 필드에 대해 생성자를 생성
public class UseTimeAop {



    // 특정 패키지의 모든 public 메소드 실행 전후로 Advice를 적용
    @Around("execution(public * com.hanghae.knowledgesharing.user.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.article.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.auth.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.comment.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.follow.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.hashtag.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.image.controller.*.*(..))" +
            "|| execution(public * com.hanghae.knowledgesharing.user.controller.*.*(..))")
    public synchronized Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis(); // 메소드 실행 시작 시간 측정


        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed();  // 핵심 로직 실행
            return output;
        } finally {
            // 측정 종료 시간
            long endTime = System.currentTimeMillis();
            // 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;

            Class clazz = joinPoint.getTarget().getClass(); // 실행 대상 객체의 클래스 정보
            log.info("[API Use Time] API URI: " + getRequestUrl(joinPoint, clazz) + ", run Time: " + runTime + " ms");
        }
    }
    // API의 URI를 추출하는 메소드
    private String getRequestUrl(JoinPoint joinPoint, Class clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // 실행 대상 메소드 정보
        Method method = methodSignature.getMethod(); // 메소드 객체
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class); // 클래스 레벨의 RequestMapping
        String baseUrl = requestMapping.value()[0]; // 기본 URL


        // HTTP 메소드와 매핑된 URI를 결합하여 최종 URL 생성
        String url = Stream.of(GetMapping.class, PutMapping.class, PostMapping.class,
                        PatchMapping.class, DeleteMapping.class, RequestMapping.class)
                .filter(mappingClass -> method.isAnnotationPresent(mappingClass)) // 해당 HTTP 메소드 어노테이션을 가지고 있는지 확인
                .map(mappingClass -> getUrl(method, mappingClass, baseUrl)) // 최종 URL 생성
                .findFirst().orElse(null);
        return url;
    }

    // HTTP 메소드와 requestURI를 조합하여 문자열로 반환
    private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl){
        Annotation annotation = method.getAnnotation(annotationClass); // 메소드에서 사용된 HTTP 메소드 어노테이션
        String[] value;
        String httpMethod;
        try {
            value = (String[])annotationClass.getMethod("value").invoke(annotation); // 어노테이션의 value 속성 값(URI)
            httpMethod = (annotationClass.getSimpleName().replace("Mapping", "")).toUpperCase(); // HTTP 메소드
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
        return String.format("%s %s%s", httpMethod, baseUrl, value.length > 0 ? value[0] : "") ; // 최종 URL 포맷
    }
}
