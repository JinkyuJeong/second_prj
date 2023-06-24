<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
<script>
</script>
</head>
<body>
	<div class="container">
		<div style="display: flex; justify-content: space-between;">
			<div style="flex-basis: 20%;">
				<%@ include file="mypageSideBar2.jsp"%>
			</div>
			<div style="flex-basis: 80%;">
      <h1 class="mb-3">리뷰관리</h1>
      <div class="row">
        <div class="col-7">
          <h5>총 <span style="color: red;">${map.size() }</span>건</h5>
        </div>
        <div class="col-5 text-end">
        	<div class="btn-group mb-3">
	  </div>
        </div>
      </div>
      
      <div class="row" id="oinfo" class="info">     
      
        <table class="table table-hover">
          <tr style="text-align:center; background-color:#D1180B; color: white;">
            <th>등록일자</th>
            <th>별점</th>
            <th>리뷰내용</th>
            <th>주문상품</th>
            <th>수정</th>
            <th>삭제</th>
          </tr>
        <c:forEach items="${map }" var="m" varStatus="st">
          <tr style="text-align:center;">          	
            <td><fmt:formatDate value="${m.key.review_date}" pattern="yyyy-MM-dd" /></td>
            <td>
            	<c:if test="${m.key.review_value== '1' }">
            		★☆☆☆☆
            	</c:if>
            	<c:if test="${m.key.review_value== '2' }">
            		★★☆☆☆
            	</c:if>
            	<c:if test="${m.key.review_value== '3' }">
            		★★★☆☆
            	</c:if>
            	<c:if test="${m.key.review_value== '4' }">
            		★★★★☆
            	</c:if>
            	<c:if test="${m.key.review_value== '5' }">
            		★★★★★
            	</c:if>
            </td>
            <td>${m.key.review_content }</td>           
            <td>${m.value.product_name }</td>
            <td><button type="button" class="btn btn-outline-danger btn-sm" onclick="location.href='reviewUpdate?mem_id=${sessionScope.loginMem.mem_id}&review_number=${m.key.review_number }'">리뷰수정</button></td>
            <td><button type="button" class="btn btn-outline-danger btn-sm" onclick="location.href='reviewDelete?mem_id=${sessionScope.loginMem.mem_id}&order_itemId=${m.key.order_itemId }'">리뷰삭제</button></td>
          </tr>          
          </c:forEach>
        </table>
        
      </div>
      
    </div>
			</div>
		</div>
</body>
</html>