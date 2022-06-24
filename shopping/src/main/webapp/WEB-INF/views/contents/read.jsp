<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/adminRead.css">
</head>
<body>

	<div class="containerBox">
		<div class="flex-box">
			<img src="/contents/storage/${dto.filename}"
				style="width: 300px; heigth: 300px;">
			<div class="contentBox">
				<div class="item">상품명 : ${dto.pname}</div>
				<div class="item">상품가격 : ${dto.price}</div>
				<div class="item">상품설명 : ${dto.detail}</div>
				<div class="item">상품등록날짜 :${dto.rdate}</div>
			</div>
			<div class="btnBox">
				<button class="btn btn-success" onclick="history.back()">뒤로가기</button>
			</div>
		</div>
	
</body>
</html>