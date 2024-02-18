package com.hanghae;

public class DatabaseInsertExample {
    public static void main(String[] args) {
        // Database connection settings
        String url = "jdbc:mysql://127.0.0.1:3306/local-db?serverTimezone=UTC&characterEncoding=UTF-8";
        String user = "root";
        String password = "";

//        // SQL insert statement
//        String sql = "INSERT INTO follow (created_at, modified_at, from_user_id, to_user_id) VALUES (NOW(), NOW(), ?, 'test00')";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            // 'test01'부터 시작하여 'test03'까지 (또는 원하는 범위까지) 반복
//            for (int i = 124; i <= 135; i++) {
//                String fromUserId = "test" + String.format("%02d", i); // from_user_id 동적 생성
//                pstmt.setString(1, fromUserId); // SQL 쿼리에 from_user_id 설정
//                pstmt.executeUpdate(); // 삽입 문 실행
//            }
//
//            System.out.println("Insert completed.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


//        // SQL insert statement
//        String sql = "INSERT INTO follow (created_at, modified_at, from_user_id, to_user_id) VALUES (NOW(), NOW(), 'test00',? )";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            // 'test01'부터 시작하여 'test03'까지 (또는 원하는 범위까지) 반복
//            for (int i = 124; i <= 135; i++) {
//                String toUserId = "test" + String.format("%02d", i); // from_user_id 동적 생성
//                pstmt.setString(1, toUserId); // SQL 쿼리에 from_user_id 설정
//                pstmt.executeUpdate(); // 삽입 문 실행
//            }
//
//            System.out.println("Insert completed.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//



    }
}
