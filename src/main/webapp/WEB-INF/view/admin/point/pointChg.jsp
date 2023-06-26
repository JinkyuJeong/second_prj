<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐 관리자</title>
<style type="text/css">
	.navy, .btn-outline-custom-navy:hover {
		background-color: navy !important;
		color : white;
	}
/*	.btn-outline-custom-navy {
	  color: navy;
	  background-color: navy !important;
	}
	
	.active-navy{
		color: white;
	  background-color: navy !important;
	}
*/
</style>
<script>
  function inputChk(f) {
    if($.trim(f.point_value.value) == "") {
	    alert("변경할 포인트를 입력하세요.");
	    f.point_value.focus();
	    return false;
	  }

	  const isPointValid = $("#isPointValid").val();
	  if(!(isPointValid == '1')){
		  alert("포인트값이 유효하지 않습니다.");
			return false;
	  }

	  return true;
	}

  function validPoint() {
    var input = $("#point").val();
    var pattern = /^[0-9]+$/; // 숫자만 허용하는 정규식 패턴
    var codeMsg = $("#pointMsg");
    
    if (pattern.test(input)) {
      $("#point").removeClass("is-invalid").addClass("is-valid");
      codeMsg.css("color", "green").text("올바른 입력입니다.");
      $("#isPointValid").val("1");
    } else {
      $("#point").removeClass("is-valid").addClass("is-invalid");
      codeMsg.css("color", "red").text("숫자만 입력해주세요.");
      $("#isPointValid").val("0");
    }
  }
  
  function ablePoint() {
	  $("#point").prop("readonly", false);
	  $("#pMsg").show();
	}
</script>
</head>
<body>
	<br><br>
    <div class="container w3-white pt-1" style="width:50%;">
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 포인트 내역 수정</h3>
      <p class="mb-3">포인트 내역을 수정하는 페이지 입니다.</p>
      <form action="pointChg" method="post" name="f" onsubmit="return inputChk(this)">
      	<input type="hidden" value="${point.point_number }" name="point_number">
        <table class="table table-bordered align-middle">
          <tr class="text-center">
            <td class="navy text-center" width="30%">회원 아이디</td>
            <td><input class="form-control" name="mem_id" value="${point.mem_id }" readonly></td>
          </tr>
          <tr>
          	<td class="navy text-center">변경 포인트(P)<span class="text-danger">*</span></td>
            <td>
              <input type="number" value="${point.point_value }" name="point_value" id="point" class="form-control" onkeyup="validPoint()" readonly placeholder="지급 포인트를 입력하세요.">
              <span class="mt-1" id="pointMsg">&nbsp;</span> <br><br>
              <button type="button" class="btn btn-sm btn-outline-dark mb-1 text-end" onclick="ablePoint()">포인트 변경</button>
              <div class="mt-1" id="pMsg" style="display:none; font-size:13px;"><span class="text-danger">*</span>변경할 포인트를 입력하고 아래 수정 버튼을 눌러주세요.</div>
              <input type="hidden" value="1" id="isPointValid">
              <input type="hidden" value="${point.point_value }" name="cur_point">
            </td>
          </tr>
          <tr>
          	<td class="navy text-center">비고<span class="text-danger">*</span></td>
          	<td><input type="text" name="point_type" class="form-control" required value="${point.point_type }"></td>
          </tr>
        </table>

        <div class="text-center">
          <button type="submit" class="btn btn-dark">수정</button>
          <a href="pointList" class="btn btn-dark">목록</a>
        </div>
      </form>
      <br>
    </div>
</body>
</html>