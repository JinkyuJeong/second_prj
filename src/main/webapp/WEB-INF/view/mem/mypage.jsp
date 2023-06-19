<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-3">
				<%@ include file="mypageSidebar.jsp"%>
			</div>
			<div>
				test
				<form action="update" method="post" name="f"
					onsubmit="return input_check(this)">
					<h1 id="title">마이페이지</h1>
					<hr>
					<div>
						<!-- 이메일 -->
						<div class="form-group mb-3">
							<label class="mb-1" for="email">이메일</label>
							<div class="input-group mb-3">
								<c:set var="email" value="" />
								<c:set var="split" value="@" />
								<input type="text" class="form-control" id="email1"
									name="email1"> <span class="input-group-text">@</span>
								<input type="text" class="form-control" id="email2"
									name="email2">
							</div>
						</div>

						<!-- 닉네임 -->
						<div class="form-group">
							<label class="mb-1" for="nickname">닉네임</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="nickname"
									id="nickname">
							</div>
						</div>

						<h4 class="mt-4">가입일자 : 2023년 6월 12일</h4>

						<div class="mt-3">
							<a class="btn" href="myBoardList?email=&nickname="><font
								size="5">게시글 : 1개</font></a> <br> <a class="btn"
								href="myCommList?email=&nickname="><font size="5">댓글
									: 1개</font></a>
						</div>
					</div>
					<br>
					<!-- 회원가입 / 초기화 -->
					<div class="container mt-3" align="center">
						<a class="btn" href="updateForm?email=">수정</a> <a class="btn ms-2"
							href="deleteForm?email=">회원탈퇴</a>
					</div>
					<!-- 정보수정 / 비번변경 -->
					<div class="container mt-3" align="center">
						<button type="submit" class="btn btn-dark">정보수정</button>
						<button type="reset" class="btn btn-dark ms-2"
							onclick="win_passChg()">비밀번호변경</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
</html>