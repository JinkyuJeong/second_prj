<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐 관리자</title>
<script>
  function inputChk(f) {
	  if($.trim(f.opt_name.value) == "") {
	    alert("옵션명을 입력하세요.");
	    f.opt_name.focus();
	    return false;
	  }
	  return true;
	}
</script>
</head>
<body>
	<br><br>
    <div class="container w3-white pt-1">
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 제품 옵션 변경</h3>
      <p class="mb-3">제품 옵션을 변경하는 페이지 입니다.</p>
      <form action="optChg" method="post" name="f" onsubmit="return inputChk(this)">
      	<input type="hidden" value="${opt.opt_number }" name="opt_number">
        <table class="table align-middle">
          <tr class="text-center">
            <td width="15%" class="table-dark text-center">제품명</td>
            <td width="35%">${opt.product_name }</td>
            <td width="15%" class="table-dark text-center">제품 이미지</td>
            <td width="35%"><img src="${path }/img/thumb/${opt.product_thumb }" width="200" height="130"></td>
          </tr>
          <tr>
            <td class="table-dark text-center">옵션명</td>
            <td><input type="text" name="opt_name" class="form-control" value="${opt.opt_name }"></td>
            <td class="table-dark text-center">수량</td>
            <td><input type="number" name="opt_quantity" value="${opt.opt_quantity }" class="form-control" readonly>  </td>
          </tr>
        </table>

        <div class="text-center">
          <button type="submit" class="btn btn-dark">옵션 변경</button>
          <a href="optList" class="btn btn-dark">목록</a>
        </div>
      </form>
      <br>
    </div>
</body>
</html>