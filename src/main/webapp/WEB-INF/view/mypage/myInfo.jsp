<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
</head>
<body>
	<div class="container">
		<div style="display: flex; justify-content: space-between;">
			<div style="flex-basis: 30%;">
				<%@ include file="mypageSideBar2.jsp"%>
			</div>
			<div style="flex-basis: 70%; margin-left: 150px;">
					<h1 style="width:70%; padding: 5px; margin-bottom: 15px;">회원 정보</h1>
					<div>
						<!-- 이메일 -->
						<div class="form-group mb-3">
							<label class="mb-1" for="email">이메일</label>
							<div class="input-group mb-3">
								<c:set var="email" value="${mem.mem_id }" />
								<c:set var="split" value="@" />
								<input type="text" class="form-control" id="email1" name="email1" readonly value="${fn:substringBefore(email,split) }"> 
								<span class="input-group-text">@</span>
								<input type="text" class="form-control" id="email2" name="email2" readonly value="${fn:substringAfter(email,split) }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="mb-1" for="nickname">이름</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="nickname" id="nickname" readonly value="${mem.mem_name }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="mb-1" for="nickname">전화번호</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="nickname" id="nickname" readonly 
								value="${mem.mem_phoneno }">
							</div>
						</div>

						<div class="form-group">
							<label class="mb-1" for="nickname">보유 포인트</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="nickname" id="nickname" readonly value="${mem.mem_point }">
							</div>
						</div>

					</div>
					<br>
			</div>
		</div>
	</div>
</body>
</html>