<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
<script>
  function inputChk(f) {
    if($.trim(f.stock_quantity.value) == "") {
	    alert("입고 수량을 입력하세요.");
	    f.opt_quantity.focus();
	    return false;
	  }

	  const isQuantityValid = $("#isQuantityValid").val();
	  if(!(isQuantityValid == '1')){
		  alert("수량이 유효하지 않습니다.");
			return false;
	  }

	  return true;
	}

  function validQuantity() {
    var input = $("#quantity").val();
    var pattern = /^[0-9]+$/; // 숫자만 허용하는 정규식 패턴
    var codeMsg = $("#quantityMsg");
    
    if (pattern.test(input)) {
      $("#quantity").removeClass("is-invalid").addClass("is-valid");
      codeMsg.css("color", "green").text("올바른 입력입니다.");
      $("#isQuantityValid").val("1");
    } else {
      $("#quantity").removeClass("is-valid").addClass("is-invalid");
      codeMsg.css("color", "red").text("숫자만 입력해주세요.");
      $("#isQuantityValid").val("0");
    }
  }
</script>
</head>
<body>
	<br><br>
    <div class="container w3-white pt-1">
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 제품 재고 등록</h3>
      <p class="mb-3">제품 재고를 등록하는 페이지 입니다.</p>
      <form action="stockReg" method="post" name="f" onsubmit="return inputChk(this)">
      	<input type="hidden" value="${prodOpt.opt_number }" name="opt_number">
        <table class="table align-middle">
          <tr class="text-center">
            <td width="15%" class="table-dark text-center">제품명</td>
            <td width="35%">${prodOpt.product_name }</td>
            <td width="15%" class="table-dark text-center">제품 이미지</td>
            <td width="35%">
            	<img src="${path }/img/thumb/${prodOpt.product_thumb }" width="200" height="130">
            	<input type="hidden" name="stock_prodThumb" value="${prodOpt.product_thumb }">
            </td>
          </tr>
          <tr>
            <td class="table-dark text-center">옵션명</td>
            <td>${prodOpt.opt_name }<input type="hidden" name="stock_prodName" value="${prodOpt.product_name }(${prodOpt.opt_name })"></td>
            <td class="table-dark text-center">입고 수량</td>
            <td>
              <input type="number" name="stock_quantity" id="quantity" class="form-control" onkeyup="validQuantity()" placeholder="입고 수량을 입력하세요.">
              <span class="mt-1" id="quantityMsg">&nbsp;</span>
              <input type="hidden" value="0" id="isQuantityValid">
            </td>
          </tr>
        </table>

        <div class="text-center">
          <button type="submit" class="btn btn-dark">재고 등록</button>
          <a href="stockList" class="btn btn-dark">목록</a>
        </div>
      </form>
      <br>
    </div>
</body>
</html>