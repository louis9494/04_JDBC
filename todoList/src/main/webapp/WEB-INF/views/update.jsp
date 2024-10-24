<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title> 수정 페이지</title>
</head>
<body>
  
  <h4>할 일 수정</h4>

  <%-- 
    /todo/update - POST 방식 요청
                -> Servlet의 doPost() 오버라이딩
  --%>
  <form action="/todo/update" method="post" id="updateForm">
    <div>
      제목 : <input type="text" name="title" value="">
    </div>
    <div>
      <textarea name="detail" 
        rows="3" cols="50" placeholder="상세 내용"></textarea>
    </div>

    <%-- todoNo 도 수정 요청 시 파라미터로 보내기 --%>
    
    <button>수정</button>
  </form>

	<%-- session 스코프에 message 있으면 alert 출력하기 --%>

</body>
</html>