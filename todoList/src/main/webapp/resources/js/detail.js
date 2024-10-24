// 할일 상세 조회 페이지에서 쿼리스트링 값 얻어오기
// url 에서 얻어오면 된다 (쿼리스트링 부분 ?todoNo=4)


// location.search : 쿼리스트링만 얻어오기
// URLSearchParams : 쿼리스트링을 객체 형태로 다룰 수 있는 객체

const todoNo = new URLSearchParams(location.search).get("todoNo"); // 할 일 번호

// ?todoNo=4
// -> todoNo = 4


//console.log(todoNo);


// 목록으로 버튼 클릭 시
const goToList = document.querySelector("#goToList");
// 목록으로 버튼 요소

// 목록으로 버튼이 클릭된 경우
goToList.addEventListener("click", () => {
	// "/" 메인페이지 요청 (GET 방식) 자바스크립트에서는 무조건 get (복잡함)
	location.href = "/";
	
	
})

// 완료 여부 변경 버튼 클릭 시
const completeBtn = document.querySelector("#completeBtn");
completeBtn.addEventListener("click", () => {
	// 현재 보고있는 todo의 완료 여부
	// O(true) <-> X(false) 변경 요청하기(get 요청)
	location.href = "/todo/complete?todoNo=" + todoNo;
	// /todo/complete?todoNo= -> 요청주소
	
});
// 삭제 버튼 클릭 시

// 수정 버튼 클릭시