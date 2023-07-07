<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
<script>

	function deleteChall(chall_number) {
		$.ajax({
			url : "${path}/ajax/deleteChall",
			data : {
				chall_number : chall_number
			},
			success : function(result) {
				location.reload();
			},
			error : function(e) {
				alert("챌린지 삭제 중 오류 발생 " + e.status)
			}
		})
	}
</script>
</head>
<body>
	<div class="container">
		<div style="display: flex; justify-content: space-between;">
			<div style="flex-basis: 20%;">
				<%@ include file="mypageSideBar2.jsp"%>
			</div>
			<div style="flex-basis: 80%;">
				<h1 class="mb-3">이벤트 관리</h1>
				<p class="mb-3 text-secondary">
      				· 포인트가<span class="text-danger"> 지급완료</span> 된 챌린지는 <span class="text-danger">삭제가 불가</span>합니다. <br>
      			</p>
				<c:if test="${empty myChallList }">
					<h2 class="text-secondary text-center" style="margin-top: 50px;">
						이벤트 참여 내역이 없습니다.</h2>
				</c:if>
				<c:if test="${!empty myChallList }">
				<div class="row">
					<div class="col-7">
						<h5>
							총 <span style="color: red;">${myChallList.size() }</span>건
						</h5>
					</div>
					<div class="col-5 text-end">
						<div class="btn-group mb-3">
							<button type="button"
								onclick="location.href='challList?mem_id=${sessionScope.loginMem.mem_id}'"
								class="btn btn-outline-danger ${empty param.chall_state ? 'active' : '' }">전체</button>
							<button type="button"
								onclick="location.href='challList?mem_id=${sessionScope.loginMem.mem_id}&chall_state=지급대기'"
								class="btn btn-outline-danger  ${param.chall_state == '지급대기' ? 'active' : '' }">지급대기</button>
							<button type="button"
								onclick="location.href='challList?mem_id=${sessionScope.loginMem.mem_id}&chall_state=지급완료'"
								class="btn btn-outline-danger  ${param.chall_state == '지급완료' ? 'active' : '' }">지급완료</button>
						</div>
					</div>
				</div>
			<div class="row" id="oinfo" class="info">
						<table class="table table-hover">
							<tr	style="text-align: center; background-color: #D1180B; color: white;">
								<th>등록일자</th>
								<th>이미지</th>
								<th>포인트 지급유무</th>
								<th>횟수</th>	
							</tr>
							<c:forEach items="${myChallList }" var="m" varStatus="st">
								<tr style="text-align: center;">
									<td><fmt:formatDate value="${m.chall_regdate}" pattern="yyyy-MM-dd" /></td>
									<td><img src="${path }/img/chall/${m.chall_pic }" style="width:100px; height:100px;"></td>
									<td>
										${m.chall_state }<br>
										<c:if test="${m.chall_state == '지급대기' }">
											<button class="btn btn-danger" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal${m.chall_number }">삭제</button>
										</c:if>										
									</td>
									<td>${m.chall_cnt }일차</td>
								</tr>
								          <!-- Modal -->
					<div class="modal fade" id="exampleModal${m.chall_number }" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">챌린지 삭제</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">챌린지를 삭제하시겠습니까?</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
									<button type="button" class="btn btn-danger" onclick="javascript:deleteChall('${m.chall_number }')">삭제</button>
								</div>
							</div>
						</div>
					</div>
							</c:forEach>
						</table>
					</div>
					       <div class="w3-center w3-padding-32">
		    <div class="w3-bar">
			    <c:if test="${pageNum<= 1}">
						<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('이전 페이지가 없습니다.');">&laquo;</a>
					</c:if>
					<c:if test="${pageNum > 1}">
						<a class="w3-bar-item w3-button w3-hover-black" href="challList?mem_id=${sessionScope.loginMem.mem_id }&pageNum=${pageNum-1}&chall_state=${param.chall_state }">&laquo;</a>
					</c:if>
					
					<c:forEach var="a" begin="${startPage}" end="${endPage}">
						<c:if test="${a <= maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black ${a == pageNum ? 'w3-black' : '' }" href="challList?mem_id=${sessionScope.loginMem.mem_id }&pageNum=${a}&chall_state=${param.chall_state }">${a}</a>
						</c:if>
					</c:forEach>
						
					<c:if test="${startPage+4 >= maxPage}">
						<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('다음 페이지가 없습니다.');">&raquo;</a>
					</c:if>
					<c:if test="${startPage+4 < maxPage}">
						<a class="w3-bar-item w3-button w3-hover-black" href="challList?mem_id=${sessionScope.loginMem.mem_id }&pageNum=${startPage+5}&chall_state=${param.chall_state }">&raquo;</a>
					</c:if>
		    </div>
		  </div>
				</c:if>

			</div>
		</div>
	</div>
</body>
</html>