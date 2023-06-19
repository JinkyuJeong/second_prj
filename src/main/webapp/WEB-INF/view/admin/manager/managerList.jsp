<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐 관리자</title>
</head>
<body>
	<br><br>
    <div class="container w3-white pt-1" >
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 매니저 목록</h3>
      <p class="mb-3">매니저 목록을 보여주는 페이지 입니다.</p>
      
      <div class="container">
      	<form action="managerList">
	        <div style="display: flex;justify-content: space-between;border-bottom: 2px solid black;margin-bottom: 10px;">
	          <h4 style="margin-top: 25px;">총 <span class="text-danger">${managerCnt }</span>명</h4>
	          <div class="input-group p-3" style="width: 30%;">
	            <input type="text" class="form-control" name="query" placeholder="매니저명 검색" value="${param.query}">
	            <button class="btn btn-outline-secondary" type="submit" id="button-addon2"><i class="fa fa-search"></i></button>
	          </div>
	        </div>
        </form>
        
        <c:if test="${empty managerCnt }">
        	<h4 class="text-center">등록된 관리자가 없습니다.</h4>
        </c:if>
        
        <c:if test="${!empty managerCnt }">
	        <table class="table table-hover table-bordered text-center align-middle">
	          <tr class="table-success">
	            <th width="10%">매니저 번호</th>
	            <th width="20%">아이디</th>
	            <th width="10%">이름</th>
	            <th width="20%">생년월일</th>
	            <th width="15%">권한</th>
	            <th width="15%">&nbsp;</th>
	          </tr>
	          <c:forEach var="manager" items="${managerList }">
		          <tr>
		            <td>${manager.manager_number }</td>
		            <td>${manager.manager_id }</td>
		            <td>${manager.manager_name}</td>
		            <td>${manager.manager_birth}</td>
		            <td>${manager.manager_grant}</td>
		            <td>
		            	<a class="btn btn-sm btn-dark" href="managerChg?manager_id=${manager.manager_id}">수정</a>
		            	<c:if test="${manager.manager_grant ne '총괄'}">
		            		<a class="btn btn-sm btn-dark" href="managerDel?manager_id=${manager.manager_id}">탈퇴</a>
		            	</c:if>
		            </td>
		          </tr>
	          </c:forEach>
	        </table>
        </c:if>

        <div class="w3-center w3-padding-32">
			    <div class="w3-bar">
				    <c:if test="${pageNum<= 1}">
							<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('이전 페이지가 없습니다.');">&laquo;</a>
						</c:if>
						<c:if test="${pageNum > 1}">
							<a class="w3-bar-item w3-button w3-hover-black" href="prodList?pageNum=${pageNum-1}&query=${param.query}">&laquo;</a>
						</c:if>
						
						<c:forEach var="a" begin="${startPage}" end="${endPage}">
							<c:if test="${a <= maxPage}">
								<a class="w3-bar-item w3-button w3-hover-black ${a == pageNum ? 'w3-black' : '' }" href="prodList?pageNum=${a}&query=${param.query}">${a}</a>
							</c:if>
						</c:forEach>
							
						<c:if test="${startPage+4 >= maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('다음 페이지가 없습니다.');">&raquo;</a>
						</c:if>
						<c:if test="${startPage+4 < maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black" href="prodList?pageNum=${startPage+5}&query=${param.query}">&raquo;</a>
						</c:if>
			    </div>
			  </div>

      </div>
    </div>
</body>
</html>