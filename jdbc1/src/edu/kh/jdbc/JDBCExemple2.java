package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExemple2 {
	public static void main(String[] args) {
		
		// 입력 받은 급여보다 초과해서 받는 사원의 
		// 사번, 이름, 급여 조회
		
		// 1. JDBC 객체 참조용 변수 선언
		Connection conn = null; // DB연결정보 저장 객체
		

		Statement stmt = null; // SQL 수행, 결과 반환용 객체
		

		ResultSet rs = null; // SELECT 수행 결과 저장 객체
		//2. DricerManager 객체를 이용해서 Connetion 객체 생성
		
		try {
			// 2-1) Oracle JDBC Driver 객체 메모리 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2-2) DB 연결 정보 작성
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소
			String port = ":1521"; // 프로그램 연결을 위한 포트 번호
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			// jdbc:oracle:thin:@localhost:1521:XE
			
			String userName = "kh_osh"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			
			// 2-3 )DB 연결 정보와 DriverManager 를 이용해서 Connection 객체 생성
			conn = DriverManager.getConnection(type+host+port+dbName, 
										userName, 
										password);

			System.out.println(conn);
			// 3. SQL 작성
			// 입력받은 급여 -> Scanner 필요
			// int input 여기에 급여 담기	
			
			// 입력 받은 급여보다 초과해서 받는 사원의 
			// 사번, 이름, 급여 조회
			Scanner sc = new Scanner(System.in);
			
			System.out.println("급여 입력 : ");
			int input = sc.nextInt();
			String sql = "SELECT EMP_NO, EMP_NAME, SALARY FROM EMPLOYEE "
					+ "WHERE SALARY >  "+ input;
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			// 5. Statement 객체를 이용하여 SQL 수행 후 결과 반환 받기
			// executeQuery() : select 실행, ResultSet 반환
			// execoteUpdate() : DML 실행, 결과 행의 개수 반환(int)
			rs = stmt.executeQuery(sql);
			// 6. 조회 결과가 담겨있는 ResultSet 을
			// 커서 이용해 1행씩 접근해
			// 각 행에 작성된 컬럼값 얻어오기
			// -> while 안에서 꺼낸 데이터 출력
			while(rs.next()) {
			
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");

				
				System.out.printf( "%s / %s / %d \n",
						empId,empName,salary);
			}				
			
		} catch (ClassNotFoundException e) {
			System.out.println("해당 클래스를 찾을 수 없습니다");
			e.printStackTrace();
		} catch (SQLException e) {
		e.printStackTrace();
			
		}
		// 7. 사용 완료된 JDBC 객체 자원 반환(close)
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
