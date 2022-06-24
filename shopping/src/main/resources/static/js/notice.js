//등록버튼 누를시 이벤트
$('#noticeCreateBtn').on("click",function(){
	if(grade=='A'){
		location.href='/notice/create';
	}else{
		alert("관리자 만 등록이 가능합니다.")
	}
});
$('#noticeReadCreateBtn').on("click",function(){
	if(grade=='A'){
		location.href='/notice/create';
	}else{
		alert("관리자 만 등록이 가능합니다.")
	}
});

//제목->수정 버튼 누를시



//제목->삭제 버튼 누를시