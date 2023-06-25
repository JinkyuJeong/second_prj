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
			<div style="flex-basis: 70%; margin-left: 150px;">
					<h1 style="width:70%; padding: 5px; margin-bottom: 15px;">리뷰쓰기</h1>
					<form action="reviewReg?mem_id=${sessionScope.loginMem.mem_id }" method="POST" name="f">
					<input type="hidden" name="order_itemId" id="order_itemId" value="${ov.order_itemId }">
					<div>
						<div class="form-group mb-3">
							<label class="mb-1" for="order_itemId">상품정보</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="product_name" id="product_name" readonly value="${ov.product_name }" >
							</div>
						</div>
						
						<div class="form-group">
							<label class="mb-1" for="nickname">별점</label>
							<div class="input-group mb-3">
								<select class="form-select" id="review_value" name="review_value">
                      				<option value="optionNotSelected" disabled selected>별점을 선택하세요.</option>
                      				<option value="1">★</option>
          							<option value="2">★★</option>
          							<option value="3">★★★</option>
          							<option value="4">★★★★</option>
          							<option value="5">★★★★★</option>
                    			</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="mb-1" for="nickname">리뷰내용</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="review_content" id="review_content">
							</div>
						</div>
						<button class="btn btn-danger" type="submit" id="reviewReg">리뷰등록</button>		
					</div>
					</form>
			</div>
		</div>
	</div>
</body>
</html>