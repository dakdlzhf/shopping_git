<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="util" uri="/ELFunctions"%>


<!DOCTYPE html>
<html>
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<script type="text/javascript">
	//page 가 로딩되는순간 처리하겠다 $(function(){})
	/*$(function(){
		if('${empty list}'){
			$("tfoot").hide();
			
		}else{
			$("tfoot").show();
		}
	})*/

	function read(contentsno) {
		var url = "/contents/detail/"+contentsno+"/?nowPage=1";
		location.href = url;

	}

	function change(check) {
		if (check.checked) {
			<!-- 여기서 aa 는 배열이된다 (모두찾는거니까 ALL)-->
			let aa = document.querySelectorAll("#ch");
			for (let i = 0; i < aa.length; i++) {
				aa[i].checked=true;
			}
		} else {
			let aa = document.querySelectorAll("#ch")
			for (let i = 0; i < aa.length; i++) {
				aa[i].checked=false;
			}
		}
	}
	function del(cartno){
		if(confirm('상품을 삭제하시겠습니까?')){
			let url = '/cart/delete/'+cartno;
			location.href= url;
		}
	}
	$(function(){
		$('#ch').on("change",function(){
			
		})
	})
	function order(){
		//태그들을 찾는작업
		let cno = document.querySelectorAll("#ch"); //아이디가 ch 인 것들을 다찾아서 cno 배열에 담는다.check 된것들
		let qty = document.querySelectorAll("#qty"); //아이디가 qty 인 것들을 다찾아서 qty 배열에 담는다.	
		let size = document.querySelectorAll("#size"); //아이디가 size 인 것들을 다찾아서 size 배열에 담는다.	
		
		let cnt = 0; //check 값을 검사하고 check 될때마다 ++ 된다.
		let param_cno = ''; // 숫자여도 결국 문자로 된다 check 박스안에있는 value 값 상품번호 여러개가 연결한다
		let param_qty = ''; // 숫자여도 결국 문자로 된다, 수량을 여러개 연결한다
		let param_size = ''; // 숫자여도 결국 문자로 된다사이즈를 여러개 연결한다.
		
		for(let i = 0; i <cno.length; i++){
			if(cno[i].checked == true) { //체크가 되어져있다면!
				cnt++;
				param_cno += cno[i].value +','; //연결하기위해 , 를 사용
				param_qty += qty[i].value +',';
				param_size += size[i].innerText +',';
			}
		}
		//아무것도 체크가안되있으면 주문이 안되도록하는 작업
		if(cnt == 0){
			alert("상품을 선택하세요");
			return;
		}else{
			let url = "/order/create/cart/"+param_cno+"/"+param_qty+"/"+param_size;
			location.href=url;
		}
	}
</script>

</head>
<body>
	<div class="container">

		<h2>
			<img src="/svg/cart4.svg" style='width: 30px'> 장바구니
		</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<!-- 체크박스  -->
					<th><input type='checkbox' onchange="javascript:change(this)"></th>
					<th>상품이미지</th>
					<th>상품명</th>
					<th>수량</th>
					<th>가격</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>

				<c:choose>
					<c:when test="${empty list}">
						<tr>
							<td colspan="6">등록된 상품이 없습니다.</td>
					</c:when>
					<c:otherwise>

						<c:set var='tot' value='${0 }' />
						<c:forEach var="dto" items="${list}">
							<c:set var='tot' value='${tot+(dto.cdto.price * dto.count)}' />
							<tr>
								<td><input type='checkbox' id='ch'
									value="${dto.cdto.contentsno }"></td>
								<td><img src="/contents/storage/${dto.cdto.filename}"
									class="img-rounded" width="100px" height="100px"></td>
								<td><a href="javascript:read('${dto.cdto.contentsno}')">${dto.cdto.pname}(size
										: <span id='size'>${dto.size}</span>)
								</a></td>
								<td><input type='number' value="${dto.count}" min="1"
									max="10" id="qty"></td>
								<td>${dto.cdto.price}</td>
								<td><a href="javascript:del('${dto.cartno}')"> <span
										class="glyphicon glyphicon-trash"></span>
								</a></td>
							</tr>
						</c:forEach>

					</c:otherwise>
				</c:choose>
			<tfoot>
				<tr style="background-color: beige; font-size: large">
					<th colspan="6" style="padding: 30px;">주문금액 ${tot}원 + 배송비
						3,000원 = 합계 ${tot + 3000}원 <a href="javascript:order()"> <img
							src="/svg/bag-heart-fill.svg" title="주문하기"
							style='width: 30px; margin-left: 30px'>
					</a> <a href="/contents/mainlist/1"> <img src="/svg/box2-heart.svg"
							title="쇼핑계속" style='width: 30px; margin-left: 30px'>
					</a>
					</th>
				</tr>

			</tfoot>
			</tbody>

		</table>

	</div>
</body>
</html>
