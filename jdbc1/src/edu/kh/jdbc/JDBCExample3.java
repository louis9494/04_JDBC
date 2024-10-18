package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
	
		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는
		// 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		// -> 이클립스 콘솔 출력
		
		// [실행화면]
		// 최소 급여 : 1000000
		// 최대 급여 : 3000000
		
		// (사번) / (이름) / (급여)
		// (사번) / (이름) / (급여)
		// (사번) / (이름) / (급여)
		// ...
		
		
		// 1. JDBC 객체 참조 변수 선언
		Connection conn = null;
		
		Statement stmt = null;

		ResultSet rs = null;
	
		try {	/* 2. DriverManager 객체를 이용해서 Connection  객체 생성하기 */
			
	
			// 2-1 ) Oracle JDBC Driver 객체를 메모리에 로드(적재) 하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2-2 ) DB 연결 정보 작성
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소
			
			String port = ":1521"; // 프로그램 연결을 위한 포트 번호
			
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			
			String userName = "kh_osh"; // 사용자 계정명
			
			String password = "kh1234"; // 계정 비밀번호
			
			conn = DriverManager.getConnection(type+host+port+dbName, 
					userName, 
					password);
			
			Scanner sc = new Scanner(System.in);
			System.out.println("최소 급여 : ");
			int min = sc.nextInt();
			
			System.out.println("최대 급여 : ");
			int max = sc.nextInt();
			
			/*String sql = "SELECT EMP_ID, EMP_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "WHERE SALARY BETWEEN " + min + "AND " + " 5000000 "
					+ "ORDER BY SALARY DESC";*/
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN
					""" + min + " AND " + max
					+ " ORDER BY SALARY DESC";
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);
			// 6. 조회 결과가 담겨있는 ResultSet 을
			// 커서 이용해 1행씩 접근해
			// 각 행에 작성된 컬럼값 얻어오기
			// -> while 안에서 꺼낸 데이터 출력
			int count = 0;
			
			while(rs.next()) {
				count++;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");

				
				System.out.printf( "%s / %s / %d \n",
						empId, empName, salary);
			}	
			
			System.out.println("총원 : " + count + "명");
			
			
		
		} catch (Exception e) {
			e.printStackTrace(); // 예외 추적
		} 		// 7. 사용 완료된 JDBC 객체 자원 반환(close)
		// -> 생성된 역순으로 close!
			finally {
			
			/* 7. 사용 완료된 JDBC 객체 자원 반환(close) */
			// -> 수행하지 않으면 DB와 연결된 Connetion(개수가 한정되어 있음) 이 그대로 남아있어서
			//	  다른 클라이언트가 추가적으로 연결되지 못하는 문제가 발생할 수 있다!!
			
			try {
				
				// 만들어진 역순으로 close 수행하는 것을 권장
				if( rs != null ) rs.close();
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
				
				// if문은 NullPointerException 방지용 구문
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
		}
	}
}