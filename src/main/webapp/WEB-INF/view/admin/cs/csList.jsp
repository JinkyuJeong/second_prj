<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐 관리자</title>
<style type="text/css">
	#tr{
		background-color: purple;
		color : white;
	}
</style>
</head>
<body>
	<br><br>
    <div class="container w3-white pt-1">
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 1:1 문의 내역</h3>
      <p class="mb-3">1:1 문의 내역을 보여주는 페이지 입니다.</p>
      
      <div class="container">
      	<form action="csList">
	        <div style="display: flex;justify-content: space-between;border-bottom: 2px solid black;margin-bottom: 10px;">
	          <h4 style="margin-top: 25px;">총 <span class="text-danger">${csCnt }</span>개</h4>
	          <div class="input-group p-3" style="width: 50%;">
						  <input type="date" name="sd" value="${sd}" class="form-control mr-3">
						  <div class="input-group-prepend">
						    <span class="input-group-text">부터</span>
						  </div>
						  <input type="date" name="ed" value="${ed}" class="form-control">
						  <div class="input-group-prepend">
						    <span class="input-group-text">까지</span>
						  </div>
						  <div class="input-group-append">
						    <button class="btn btn-outline-secondary" type="submit" id="button-addon2">
						      <i class="fa fa-search"></i>
						    </button>
						  </div>
						</div>
	        </div>
        </form>
        
        <c:if test="${empty csList }">
        	<h4 class="text-center">등록된 문의 등록 내역이 없습니다.</h4>
        </c:if>
        
        <c:if test="${!empty csList }">
        	<form action="csDel" method="post">
		        <table class="table table-hover table-bordered text-center align-middle">
		          <tr id="tr">
		            <th width="10%">문의 번호</th>
		            <th width="20%">회원 아이디</th>
		            <th width="30%">문의 내용</th>
		            <th width="20%">처리 담당자 </th>
		            <th width="20%">문의 상태</th>
		          </tr>
		          <c:forEach var="cs" items="${csList }">
			          <tr>
			            <td>${cs.cs_number }</td>
			            <td>${cs.mem_id }</td>
			            <td>${cs.cs_qContent }</td>
			            <td>${empty cs.manager_name? '미정' : 'cs.manager_name'}</td>
			            <td>
			            	${cs.cs_state}
			            	<c:if test="${cs.cs_state eq '답변대기' }">
			            		<a class="btn btn-sm btn-secondary" href="csRe?cs_number=${cs.cs_number}">답변하기</a>
			            	</c:if>
			            	<c:if test="${cs.cs_state eq '답변완료' }">
			            		<a class="btn btn-sm btn-success" href="csDetail?cs_number=${cs.cs_number}">처리내역</a>
			            	</c:if>
			            </td>
			          </tr>
		          </c:forEach>
		        </table>
	        </form>
        </c:if>

         <div class="w3-center w3-padding-32">
		    <div class="w3-bar">
			    <c:if test="${pageNum<= 1}">
						<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('이전 페이지가 없습니다.');">&laquo;</a>
					</c:if>
					<c:if test="${pageNum > 1}">
						<a class="w3-bar-item w3-button w3-hover-black" href="stockList?pageNum=${pageNum-1}&sd=${sd}&ed=${ed}">&laquo;</a>
					</c:if>
					
					<c:forEach var="a" begin="${startPage}" end="${endPage}">
						<c:if test="${a <= maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black ${a == pageNum ? 'w3-black' : '' }" href="stockList?pageNum=${pageNum-1}&sd=${sd}&ed=${ed}">${a}</a>
						</c:if>
					</c:forEach>
						
					<c:if test="${startPage+4 >= maxPage}">
						<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('다음 페이지가 없습니다.');">&raquo;</a>
					</c:if>
					<c:if test="${startPage+4 < maxPage}">
						<a class="w3-bar-item w3-button w3-hover-black" href="stockList?pageNum=${pageNum-1}&sd=${sd}&ed=${ed}">&raquo;</a>
					</c:if>
		    </div>
		  </div>

      </div>
    </div>
</body>
</html>