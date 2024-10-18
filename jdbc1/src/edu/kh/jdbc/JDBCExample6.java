
package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {
	public static void main(String[] args) {
		
		// 아이디, 비밀번호, 이름을 입력받아
		// 아이디, 비밀번호가 일치하는 사용자(TB_USER)의
		// 이름을 수정(UPDATE)
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		
		try {
			
		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_osh";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			/*
			 * AutoCommit 끄기!
			 *  왜? ->개발자가 원할 때 트랜잭션을 마음대로 제어하기 위해
			 *  
			 * */
			conn.setAutoCommit(false);//커밋 끄기
			
			// 3. SQL 작성
			Scanner sc = new Scanner(System.in);
			System.out.println("아이디 입력 : ");
			String id = sc.nextLine();
	
			System.out.println("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.println("수정할 이름 입력 : ");
			String name = sc.nextLine();
			
			String sql = """
					UPDATE TB_USER SET
					USER_NAME = ?
					WHERE USER_ID = ?
					AND USER_PW = ?
				""";
			

			// 4. PreparedStatement 객체 생성
			// -> 객체 생성과 동시에 SQL 이 담겨지게됨
			// -> 왜냐하면 미리 ? (placeholder)에 값을 받을 준비를 해야되기 떄문에..
			pstmt = conn.prepareStatement(sql);
			
			
			// 5. ? 위치홀더에 알맞은 값 대입
			
			// pstmt.set 자료형(?순서, 대입할값 )
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			// -> 여기까지 실행하면 SQL이 작성 완료된 상태!
			
			// 6. SQL(INSERT) 수행 후 결과(int) 반환 받기
			// executeQuery()  : SELECT 수행, ResultSet 반환 
			// executeUpdate() : DML 수행, 결과 행 개수(int) 반환
			// 	-> 보통 DML 실패 시 0, 성공 시 0 초과된 값이 반환된다
			
			// PreparedStatement를 이용하여 sql 실행 시
			// executeQuery() / executeUpdate() 매개변수 자리에 아무것도 없어야 한다!!
			int result = pstmt.executeUpdate();
			// 성공한 행의 개수
			// 성공 시 "수정 성공" + commit
			
			// 실패 시 "아이디 또는 비밀번호 불일치" + ROLLBACK 
			
			// 7. result 값에 따른 결과 처리 + 트랜잭션 제어처리 
			/// 실패하면 롤백 성공하면 커밋, 오토커밋 꺼야함
			if(result > 0) { // INSERT 성공 시
				System.out.println("수정 성공!");
				conn.commit(); // commit 수행 -> DB에 INSERT 영구 반영
				
			} else {
				System.out.println("아이디 또는 비밀번호 불일치");
				conn.rollback(); // 실패 시 ROLLBACK
			}
	
		
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 8. 사용한 JDBC 객체 자원 반환
				if( pstmt != null ) pstmt.close();
				if( conn != null ) conn.close();
			}
				
			 catch (Exception e) {
				e.printStackTrace();
					
			}
	
		}
	}
}




