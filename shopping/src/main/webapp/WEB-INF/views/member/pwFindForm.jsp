<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="/js/infoFind.js" type="text/javascript" defer></script>
</head>
<body>
	<div class="container">

		<h1 class="col-sm-offset-2 col-sm-10">로그인</h1>
		<form class="form-horizontal">

			<div class="form-group">
				<label class="control-label col-sm-2" for="id">아이디</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="id"
						placeholder="Your id" name="id">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="mname">이름</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="mname"
						placeholder="Your name" name="mname" required="required">
					<p id="resultPw"></p>
				</div>
			</div>


			<div class="col-sm-offset-2 col-sm-8">
				<button type="button" class="btn btn-default" id="pwFindButton">패스워드
					찾기</button>
				<button type="button" class="btn btn-default"
					onclick="history.back()">뒤로가기</button>
			</div>
	</div>
	</form>

	</div>
</body>
</html>