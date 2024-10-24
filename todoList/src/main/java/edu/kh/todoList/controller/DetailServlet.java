package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.dto.Todo;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 할 일 상세 조회 요청 처리 Servlet
@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet{

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			try {
				// 요청 시 전달 받은 파라미터 얻어오기
				int todoNo = Integer.parseInt(req.getParameter("todoNo"));
				
				// 서비스 객체 생성
				TodoListServiceImpl service = new TodoListServiceImpl();
				
				Todo todo = service.todoDetailView(todoNo);
				// TODO_NO 컬럼값이 todoNo와 같은 todo가 없으면 null 반환 / 있으면 todo 객체 반환
				
				// todo가 존재하지 않을 경우
				// -> 메인페이지(/) redirect 후 
				// 할 일이 존재하지 않습니다
				// alert 출력
				if(todo == null) {
					// session 객체 생성 후 message 세팅하기
					HttpSession session = req.getSession();
					session.setAttribute("message", "할 일이 존재하지 않습니다.");
					
					resp.sendRedirect("/");
					return;
				}
				
				// todo가 존재하는 경우 
				// detail.jsp 로 forward해서 응답
				// req -> 위임받은 것까지 생성
				req.setAttribute("todo", todo);
				
				// JSP 파일 경로 (webapp 폴더 기준으로 작성)
				String path = "/WEB-INF/views/detail.jsp";
				
				// 요청 발송자를 이용해서 요청 위임
				req.getRequestDispatcher(path).forward(req, resp);
				
				
			} catch (Exception e) {
			e.printStackTrace();
			}
		}
}
