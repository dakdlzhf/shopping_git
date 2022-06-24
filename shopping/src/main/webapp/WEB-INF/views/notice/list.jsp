<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="util" uri="/ELFunctions"%>

<!DOCTYPE html>
<html>
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script type="text/javascript">
	function read(noticeno) { //	페이징/검색을 유지하는 read
		var url = "/notice/read";
		url += "?noticeno=" + noticeno;
		url += "&col=${col}";
		url += "&word=${word}";
		url += "&nowPage=${nowPage}";
		location.href = url;
	}
	let grade = '${grade}';
</script>
</head>
<body>
	<div class="container">

		<h2>공지 사항</h2>
		<form class="form-inline" action="/notice/list">
			<div class="form-group">
				<select class="form-control" name="col">
					<option value="wname"
						<c:if test= "${col=='wname'}"> selected </c:if>>성명</option>
					<option value="title"
						<c:if test= "${col=='title'}"> selected </c:if>>제목</option>
					<option value="content"
						<c:if test= "${col=='content'}"> selected </c:if>>내용</option>
					<option value="title_content"
						<c:if test= "${col=='title_content'}"> selected</c:if>>제목+내용</option>
					<option value="total"
						<c:if test= "${col=='total'}"> selected </c:if>>전체출력</option>
				</select>
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Enter 검색어"
					name="word" value="${word}">
			</div>
			<button type="submit" class="btn btn-default">검색</button>
			<button type="button" class="btn btn-default" id="noticeCreateBtn"
				>등록</button>
		</form>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>등록날짜</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>

				<c:choose>
					<c:when test="${empty list}">
						<tr>
							<td colspan="6">등록된 글이 없습니다.</td>
					</c:when>
					<c:otherwise>

						<c:forEach var="dto" items="${list}">

							<tr>
								<td>${dto.noticeno}</td>
								<td><a href="javascript:read('${dto.noticeno}')">${dto.title}</a>

									<c:if test="${util:newImg(fn:substring(dto.rdate,0,10)) }">
										<!-- 연월일만 뽑아내는 substring -->
										<img src="/images/new.gif">
									</c:if></td>
								<td>${dto.wname}</td>
								<td>${dto.rdate}</td>
								<td>${dto.cnt}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>

			</tbody>

		</table>
		
		<div>${paging}</div>
	
		
		<!-- notice.js 연결 -->
		<script src="${pageContext.request.contextPath}/js/notice.js"></script>
	</div>
</body>
</html>
