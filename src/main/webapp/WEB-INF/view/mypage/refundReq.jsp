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
	  var checkboxes = f.querySelectorAll('input[name="opt_numbers"]:checked');
	  var quantities = [];
	  var refundReasons = [];
	  var qChkElements = [];
	  
	  // 선택된 체크박스의 값을 배열로 추출하여 Hidden Input에 할당
	  var optNumbers = [];
	  checkboxes.forEach(function (checkbox) {
	    optNumbers.push(checkbox.value);
	  });
	  var optNumbersInput = document.createElement('input');
	  optNumbersInput.type = 'hidden';
	  optNumbersInput.name = 'opt_number';
	  optNumbersInput.value = optNumbers.join(',');
	  f.appendChild(optNumbersInput);
	  
	  // 선택된 체크박스에 포함된 요소들만 찾기
	  for (var i = 0; i < checkboxes.length; i++) {
	    var checkbox = checkboxes[i];
	    var checkboxParent = checkbox.closest('table'); // 체크박스의 부모 테이블 요소를 찾음
	    var quantityInput = checkboxParent.querySelector('input[name="opt_count"]');
	    var refundReason = checkboxParent.querySelector('select[name="refund_reasons"]');
	    var qChkElement = checkboxParent.querySelector('input[name="qChk"]');

	    if (quantityInput && refundReason && qChkElement) {
	      quantities.push(quantityInput);
	      refundReasons.push(refundReason);
	      qChkElements.push(qChkElement);
	    }
	  }

	  var checkedCount = checkboxes.length;
	  if (checkedCount === 0) {
	    alert("환불할 상품을 선택해주세요.");
	    return false;
	  }

	  for (var j = 0; j < quantities.length; j++) {
	    if (quantities[j].value.trim() === "") {
	      alert("수량을 입력해주세요.");
	      return false;
	    }
	  }

	  for (var k = 0; k < refundReasons.length; k++) {
	    if (refundReasons[k].value === "optionNotSelected") {
	      alert("환불사유를 선택해주세요.");
	      return false;
	    }
	  }

	  for (var l = 0; l < qChkElements.length; l++) {
	    var qChk = qChkElements[l];
	    if (qChk.value === "quantityNotChecked") {
	      alert("주문수량 이상 환불신청할 수 없습니다.");
	      return false;
	    }
	  }

	  return true; // 모든 조건을 만족하면 submit을 진행
	}
	
	function quantityChk(id) {
		var idParts = id.split('_');
		var opt_number = idParts[0];
		var order_id = idParts[1];
		$.ajax({
			url : "${path}/ajax/ov_quantityChk",
			method : "POST",
			data : {
				opt_number : opt_number,
				order_id : order_id
			},
			success : function(result) {
				if (result < $("#quantity_" + opt_number).val()) {
					alert("주문수량 이상 환불신청할 수 없습니다.");
					$(this).closest("tr").find("input[name='qChk']").val(
							"quantityNotChecked");
					$("#quantity_" + opt_number).val('');
					return;
				} else {
					$(this).closest("tr").find("input[name='qChk']").val(
							"quantityChecked");
				}
			},
			error : function(e) {
				alert("환불 수량 체크 오류: " + e.status);
			}
		});
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
      			<p class="mb-3 text-secondary">
      				· 구매 후<span class="text-danger"> 2주가 지난 상품</span> 및 <span class="text-danger">배송완료 되지 않은 상품</span>은 환불이 불가합니다. <br>
      			</p>
      			<div class="row">
      				<c:if test="${empty orderItems }">
      					<div class="text-secondary mt-5">
      						환불 가능한 상품이 없습니다. 
      					</div>
      				</c:if>
      				<c:if test="${!empty orderItems }">
      					<form class="form-control" action="refundReq?mem_id=${sessionScope.loginMem.mem_id }" method="post" name="f" onsubmit="return input_chk(this)">					
						<div class="mb-3">
							<label for="opt_numbers" class="form-label">환불 하실 상품을 선택해주세요.</label>
							<c:forEach items="${ orderItems}" var="i" varStatus="st">
							<table class="table">																					
								<c:if test="${i.order_state == '배송완료' }">	
								<input type="hidden" name="order_id" value="${i.order_id }" id="order_id">					
								<tr>
									<td>
										<div class="form-check">
  											<input class="form-check-input" type="checkbox" value="${i.opt_number }" id="checkbox_${st.index }" name="opt_numbers">
  											<label class="form-check-label" for="checkbox_${st.index }">
    											[주문번호 : ${i.order_id}] 상품명 : ${i.product_name} / 옵션명 :  ${i.opt_name } / 주문 개수 : ${i.opt_count } 개
  											</label>
										</div>
									</td>
									<td>
										<div class="form-check" id="quantityInput">
											<input type="text" class="form-control" name="opt_count" id="quantity_${i.opt_number }" onkeyup="quantityChk('${i.opt_number }_${i.order_id}')" placeholder="수량" value="${i.opt_count }">
										</div>
										<input type="hidden" name="qChk" id="qChk${st.index }" value="quantityChecked">
									</td>
								</tr>
								<tr>
									<td>
									<div class="mb-3">
										<select class="form-select" id="refund_reason${st.index }" name="refund_reason">
                      						<option value="optionNotSelected" disabled selected>환불사유를 선택하세요.</option>
                      						<option>고객 단순 변심</option>
                      						<option>실수로 구매함</option>
                      						<option>구매한 상품을 더 이상 원하지 않음</option>
                      						<option>상품 정보 상이</option>
                      						<option>제품 결함</option>
                    					</select>
									</div>
									</td>
								</tr>
								</c:if>						
							</table>
							</c:forEach>
						</div>
						<div class="mb-3">
							<label for="name" class="form-label">환불 신청자</label>
							<input type="text" class="form-control" id="name" value="${sessionScope.loginMem.mem_name }" readonly>
							<input type="hidden" name="refund_memId" value="${sessionScope.loginMem.mem_id }">
						</div>
						
						<div class="mb-3">
							<button type="submit" class="btn btn-danger">환불신청</button>
						</div>							
					</form>
      				</c:if>				
				</div>
			</div>
		</div>
	</div>
</body>
</html>