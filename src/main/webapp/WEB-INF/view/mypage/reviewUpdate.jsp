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
					<h1 style="width:70%; padding: 5px; margin-bottom: 15px;">리뷰수정</h1>
					<form action="reviewUpdate" method="POST" name="f">
					<input type="hidden" value="${sessionScope.loginMem.mem_id }" name="mem_id">
					<input type="hidden" value="${param.review_number }" name="review_number">
					<div>	
						<div class="form-group mb-3">
							<label class="mb-1" for="order_itemId">수정할 상품</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="order_itemId" id="order_itemId" readonly value="${ov.product_name }" >
							</div>
						</div>
						
						<div class="form-group">
							<label class="mb-1" for="review_value">별점</label>
							<div class="input-group mb-3">
								<select class="form-select" id="review_value" name="review_value">
                      				<option value="optionNotSelected" disabled selected>별점을 선택하세요.</option>
                      				<option value=1>★</option>
          							<option value=2>★★</option>
          							<option value=3>★★★</option>
          							<option value=4>★★★★</option>
          							<option value=5>★★★★★</option>
                    			</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="mb-1" for="review_content">리뷰내용</label>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="review_content" id="review_content">
							</div>
						</div>
						<button class="btn btn-danger" type="submit">리뷰수정</button>		
					</div>
					</form>
			</div>
		</div>
	</div>
</body>
</html>