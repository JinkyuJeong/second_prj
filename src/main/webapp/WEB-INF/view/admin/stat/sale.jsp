<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐관리자</title>
<style type="text/css">
	#tr, .green, .btn-outline-custom-green:hover {
		background-color: green;
		color : white;
	}
	.btn-outline-custom-green {
	  color: green;
	  border-color: green;
	}
	
	.active-green{
		color: white;
	  background-color: green;
	}
</style>
</head>
<body>
	<br><br>
  <div class="row">
    <div class="col-2 w3-white pt-1">
      <div class="w3-padding-64 w3-large w3-text-grey text-center fw-bold">
        <span style="font-size: 2em; color: black;">통계</span> <br><br>
        <a href="${path }/admin/stat/sale" class="w3-bar-item w3-button">· 매출</a> <br>
        <a href="${path }/admin/stat/item" class="w3-bar-item w3-button">· 제품</a>
      </div>
    </div>
    <div class="col-1"></div>
    <div class="container w3-white pt-1 col-9">
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 통계</h3>
      <p class="mb-3">쇼핑몰 통계를 보여주는 페이지 입니다.</p>
      
      <div class="container">
      
      	<form action="searchStat">
	        <div style="border-bottom: 2px solid black;margin-bottom: 10px;">
	          <div class="input-group p-3">
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
      	
     	 	<c:if test="${empty statSaleList }">
       		<h4 class="text-center">해당 일자에는 데이터가 존재하지 않습니다.</h4>
        </c:if>
        
        <c:if test="${empty statSaleList }">
	        <table class="table table-hover table-bordered text-center align-middle">
	          <tr id="tr">
	            <th width="25%">날짜</th>
	            <th width="10%">주문 건 수</th>
	            <th width="15%">주문 금액</th>
	            <th width="10%">취소 건 수</th>
	            <th width="15%">취소 금액</th>
	            <th width="10%">환불 건 수</th>
	            <th width="15%">환불 금액</th>
	          </tr>
	          <c:forEach var="sale" items="${statSaleList}" varStatus="st">
		          <tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
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
							<a class="w3-bar-item w3-button w3-hover-black" href="sale?pageNum=${pageNum-1}&sd=${sd}&ed=${ed}">&laquo;</a>
						</c:if>
						
						<c:forEach var="a" begin="${startPage}" end="${endPage}">
							<c:if test="${a <= maxPage}">
								<a class="w3-bar-item w3-button w3-hover-black ${a == pageNum ? 'w3-black' : '' }" href="refundList?pageNum=${pageNum}&sd=${sd}&ed=${ed}">${a}</a>
							</c:if>
						</c:forEach>
							
						<c:if test="${startPage+4 >= maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('다음 페이지가 없습니다.');">&raquo;</a>
						</c:if>
						<c:if test="${startPage+4 < maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black" href="refundList?pageNum=${startPage+5}&sd=${sd}&ed=${ed}">&raquo;</a>
						</c:if>
			    </div>
			  </div>
      </div>
    </div>
  </div>
</body>
</html>