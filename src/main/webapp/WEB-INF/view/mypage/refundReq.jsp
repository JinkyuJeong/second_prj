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
	function input_chk(f) {		
		var checkboxes = $("input[type='checkbox']:checked");
		if (checkboxes.length === 0) {
		  alert("환불하실 상품을 선택해주세요.");
		  return false;
		}
		if(f.refund_reason.value.trim() === '') {
			alert("수량을 입력해주세요.");
			f.refund_reason.focus();
			return false;
		}
		if(f.refund_reason.value.trim() === '') {
			alert("환불사유를 입력해주세요.");
			f.refund_reason.focus();
			return false;
		}
		return true;
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
      			<h1 class="mb-3">환불 신청</h1>
      			<div class="row">
					<form class="form-control" action="refundReg" method="post" name="f" onsubmit="input_chk(this)">
						<div class="mb-3">
							<label for="exampleFormControlInput1" class="form-label">환불 하실 상품을 선택해주세요.</label>
							<table class="table">
								<c:forEach items="${ orderItems}" var="i">
								<tr>
									<td>
										<div class="form-check">
  											<input class="form-check-input" type="checkbox" value="${i.opt_number }" id="flexCheckDefault">
  											<label class="form-check-label" for="flexCheckDefault">
    											상품명 : ${i.product_name} / 옵션명 :  ${i.opt_name } / 주문 개수 : ${i.opt_count } 개
  											</label>
										</div>
									</td>
									<td>
										<div class="form-check" id="quantityInput">
											<input type="text" class="form-control" name="opt_count" id="quantity" placeholder="수량">
										</div>
									</td>
								</tr>
								</c:forEach>
							</table>
						</div>
						<div class="mb-3" id="quantityInput" style="display:none;">
							<label for="exampleFormControlInput1" class="form-label">수량</label>
							<input type="text" class="form-control" id="quantity">
						</div>
						<div class="mb-3">
							<label for="exampleFormControlInput1" class="form-label">환불 신청자</label>
							<input type="text" class="form-control" id="name" value="${sessionScope.loginMem.mem_name }" readonly>
							<input type="hidden" name="refund_memId" value="${sessionScope.loginMem.mem_id }">
						</div>
						<div class="mb-3">
							<label for="exampleFormControlTextarea1" class="form-label">환불사유</label>
							<textarea class="form-control" id="refund_reason" name ="refund_reason" rows="3"></textarea>
						</div>
						<div class="mb-3">
							<button type="submit" class="btn btn-danger">환불신청</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>