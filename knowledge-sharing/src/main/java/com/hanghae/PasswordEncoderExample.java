package com.hanghae;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderExample {
    public static void main(String[] args) {
        // Your raw password
        String rawPassword = "asdsadas2133";

        // Encoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Encode the raw password
        String encodedPassword = encoder.encode(rawPassword);

        // Print or use the encoded password
        System.out.println("Encoded password: " + encodedPassword);

    }
}


//
//SELECT *
//        FROM user
//        ORDER BY CAST(REGEXP_REPLACE(user_id, '\\D', '') AS UNSIGNED);
