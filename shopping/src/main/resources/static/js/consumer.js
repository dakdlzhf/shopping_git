$(function() {//페이지가 로딩될때
	showList();
	showPage();
});//page loading function end  (showList,showPage 호~출)

let replyUL = $(".chat");
let replyPageFooter = $(".panel-footer");

function showList() {
	getList({ contentsno: contentsno, sno: sno, eno: eno })
		.then(list => {
			let str = ""

			for (var i = 0; i < list.length; i++) {
				str += "<li class='list-group-item' data-rnum='" + list[i].rnum + "'>";
				str += "<div><div class='header'><strong class='primary-font'>" + list[i].id + "</strong>";
				str += "<small class='pull-right text-muted'>" + list[i].regdate + "</small></div>";
				str += replaceAll(list[i].content, '\n', '<br>') + "</div></li>";
			}

			replyUL.html(str);
		});

}//showList() end

function replaceAll(str, searchStr, replaceStr) {
	return str.split(searchStr).join(replaceStr);
}
let param = '';
param += "nPage=" + nPage;
param += "&nowPage=" + nowPage;
param += "&contentsno=" + contentsno;
param += "&col=" + colx;
param += "&word=" + wordx;

function showPage() {//댓글 풋터
	getPage(param)
		.then(paging => {
			console.log(paging);
			let str = "<div><small class='text-muted'>" + paging + "</small></div>";

			replyPageFooter.html(str);
		});
}
let modal = $(".modal");
let modalInputContent = modal.find("textarea[name='content']");

let modalModBtn = $("#modalModBtn");
let modalRemoveBtn = $("#modalRemoveBtn");
let modalRegisterBtn = $("#modalRegisterBtn");

//모달 숨기기
$("#modalCloseBtn").on("click", function(e) {
	modal.modal('hide');
});

//모달 보이기
$("#addReplyBtn").on("click", function(e) {
	console.log("지금접속한 회원 sessionID : " + sessionId);
	if (sessionId == '') {
		alert("비회원 사용불가 , 로그인 부터 하셔야합니다.");
		location.href = "/member/login";

		return;
	}
	modalInputContent.val("");
	modal.find("button[id !='modalCloseBtn']").hide();

	modalRegisterBtn.show();

	$(".modal").modal("show");

});

//댓글생성누르면 처리
modalRegisterBtn.on("click", function(e) {

	if (modalInputContent.val() == '') {
		alert("댓글을 입력하세요")
		return;
	}

	let reply = { // json 객체..
		content: modalInputContent.val(),//리뷰내용 값 을 content 에저장
		id: sessionId,
		contentsno: contentsno
	};
	add(reply)
		.then(result => {
			modal.find("input").val("");
			modal.modal("hide");

			showList();
			showPage();

		}); //end add

}); //end modalRegisterBtn.on
$(".chat").on("click", "li", function(e) {

	let rnum = $(this).data("rnum");

	get(rnum)
		.then(reply => {

			modalInputContent.val(reply.content);
			modal.data("rnum", reply.rnum);

			modal.find("button[id !='modalCloseBtn']").hide();

			modalModBtn.show();
			modalRemoveBtn.show();

			$(".modal").modal("show");

		});
});
//댓글 수정 
modalModBtn.on("click", function(e) {

	let rnum = modal.data("rnum");
	let data = { // json 객체..
		id: sessionId,
		rnum: rnum
	};
	//유저 확인
	sessionCheck(data)
		.then(result => { // 현재 session 로그인한 유저id 와 댓글을 남긴 유저의 id 를 비교후 true 가날라오면 update 진행
			if (result) {
				console.log(result);
				let reply = { rnum: modal.data("rnum"), content: modalInputContent.val() };
				update(reply)
					.then(result => {
						modal.modal("hide");
						showList();
						showPage();
					});
				return;
			} else { //false 라면 alert 
				alert("작성자 본인만 댓글수정이 가능합니다.");
				$('#myModal').fadeOut(1500, 'swing');
				$('#myModal').modal('hide');
				return;
			}
		})


});//modify

//댓글 삭제
modalRemoveBtn.on("click", function(e) {
	let rnum = modal.data("rnum");
	let data = { // json 객체..
		id: sessionId,
		rnum: rnum
	};

	sessionCheck(data)
		.then(result => {
			if (result) {
				console.log(result);
				let rnum = modal.data("rnum");
				remove(rnum)
					.then(result => {
						modal.modal("hide");
						showList();
						showPage();
					});
				return;
			} else {
				alert("작성자 본인만 댓글삭제가 가능합니다.");
				$('#myModal').fadeOut(1000, 'swing');
				$('#myModal').modal('hide');
				return;
			}
		})





});//remove