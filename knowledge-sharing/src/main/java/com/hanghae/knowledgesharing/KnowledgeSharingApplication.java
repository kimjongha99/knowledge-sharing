package com.hanghae.knowledgesharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class KnowledgeSharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeSharingApplication.class, args);
    }

}
