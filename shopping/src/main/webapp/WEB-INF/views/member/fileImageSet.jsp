<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function formCheck() {
		let fnameMF = $('#fnameMF').val();
		if (fnameMF == null) {
			alert("수정할 사진을 선택하세요");
			return false;
		} else {
			return true;
		}
	}
</script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-sm-3">
				<h3>
					<span class="glyphicon glyphicon-user"></span> 사진 수정
				</h3>

				<img class="img-rounded" src="/member/storage/${dto.fname}"
					style="width: 280px">
				<!-- 사진 수정 -->
				<form class="form-horizontal" action="/member/fileImageSet"
					method="post" enctype="multipart/form-data"
					>
					<input type="hidden" name="id" value="${dto.id}" />
					<div class="form-group">
						<div>
							<input type="file" id="fnameMF" name="fnameMF"
								class="form-control"></input>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-5">
							<button class="btn btn-success">수정</button>
							<button type="reset" class="btn btn-danger"
								onclick="history.back()">취소</button>
						</div>
					</div>
				</form>
				<div class="caption">
					<p>ID : ${dto.id}</p>
					<p>성명 : ${dto.mname}</p>
					<p>배송지 : (${dto.zipcode })${dto.address1 } ${dto.address2}</p>
				</div>
			</div>

		</div>
	</div>
</body>
</html>

