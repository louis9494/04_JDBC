package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExemple4 {
	public static void main(String[] args) {
		
		// 부서명을 입력받아 
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		// hint : SQL 에서 문자열은 양쪽에 '' (홑따옴표) 필요
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
			System.out.println(" 부서명 입력 :  ");
			String input = sc.nextLine();
			
			
			/*String sql = "SELECT EMP_ID, EMP_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "WHERE SALARY BETWEEN " + min + "AND " + " 5000000 "
					+ "ORDER BY SALARY DESC";*/
			String sql = """
					SELECT 
					EMP_NO, 
					EMP_NAME,
					NVL(DEPT_TITLE, '없음) DEPT_TITLE,
					JOB_NAME
					FROM EMPLOYEE
					JOIN JOB USING(JOB_CODE)
					LEFT JOIN  DEPARTMENT ON(DEPT_ID  = DEPT_CODE)
					WHERE  DEPT_TITLE = 
					'""" +  input + "' ORDER BY JOB_CODE";
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);
			// 6. 조회 결과가 담겨있는 ResultSet 을
			// 커서 이용해 1행씩 접근해
			// 각 행에 작성된 컬럼값 얻어오기
			// -> while 안에서 꺼낸 데이터 출력

			
			boolean flag = true;
			// 조회 결과 있다면 false, 없으면 true
			
			while(rs.next()) {
				flag = false;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				

				
				System.out.printf( "%s / %s / %s / %s \n",
						empId, empName, deptTitle, jobName );
			}	
			
			if(flag) {
				System.out.println("일치하는 부서가 없습니다!");
			}

		
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
