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
	function list_disp(order_id) {
		$("#saleLine"+order_id).toggle();
		console.log(order_id)
		$.ajax({
			url : "${path}/ajax/orderDetail",
			method : "POST",
			data : {order_id : order_id}, 
			success : function(result) {
				var html = ""
				html+="<tr style='background-color:pink; color:white;'><th>상품명</th><th>옵션명</th><th>상품가격</th><th>주문수량</th><th>상품총액</th></tr>"              	
				$.each(result, function(index, o) {
					html += "<tr>";
					html += "<td>" + o.product_name + "</td>";
					html += "<td>" + o.opt_name + "</td>";
					html += "<td>" + o.product_price + "원 </td>";
					html += "<td>" + o.opt_count + "개 </td>";
					html += "<td>" + o.product_price * o.opt_count + "원 </td>";
					html += "</tr>";
				})
				$("#orderDetail"+order_id).html(html);
			}, error : function(e) {
				alert("주문 상세정보 불러오기 오류 : " + e.status)
			}
		})
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
      <h1 class="mb-3">취소/환불 내역</h1>
      <div class="row">
        <div class="col-7">
          <h5>총 <span style="color: red;">${refundList.size() }</span>건</h5>
        </div>
        <div class="col-5 text-end">
        	<div class="btn-group mb-3">
			<button type="button" onclick="location.href='cancelList?mem_id=${sessionScope.loginMem.mem_id}'" class="btn btn-outline-danger ${empty param.refund_type ? 'active' : '' }">전체</button>
			<button type="button" onclick="location.href='cancelList?mem_id=${sessionScope.loginMem.mem_id}&refund_type=취소'" class="btn btn-outline-danger ${param.refund_type == '취소' ? 'active' : '' }">취소</button>
			<button type="button" onclick="location.href='cancelList?mem_id=${sessionScope.loginMem.mem_id}&refund_type=환불대기'" class="btn btn-outline-danger  ${param.refund_type == '환불대기' ? 'active' : '' }">환불대기</button>
			<button type="button" onclick="location.href='cancelList?mem_id=${sessionScope.loginMem.mem_id}&refund_type=환불완료'" class="btn btn-outline-danger  ${param.refund_type == '환불완료' ? 'active' : '' }">환불완료</button>
	  </div>
        </div>
      </div>
      
      <div class="row" id="oinfo" class="info">
      
      
        <table class="table table-hover">
          <tr style="text-align:center; background-color:#D1180B; color: white;">
            <th>취소일자</th>
            <th>주문번호</th>
            <th>취소사유</th>
            <th>취소금액</th>
            <th>처리현황</th>
          </tr>
        <c:forEach items="${refundList }" var="r" varStatus="st">
          <tr style="text-align:center;">
            <td><fmt:formatDate value="${r.refund_date }" pattern="yyyy년 MM월 dd일" /></td>
            <td><a href="javascript:list_disp('${r.refund_orderId }')">${r.refund_orderId }</a></td>
            <td>${r.refund_reason}</td>           
            <td><fmt:formatNumber value="${r.refund_price }" pattern="###,###"/>원</td>
            <td>${r.refund_type }</td>
          </tr>          
          <!-- 주문 상세정보 -->
          <tr style="text-align:center;" class="saleLine" id="saleLine${r.refund_orderId }">
            <td colspan="7">
            <table id="orderDetail${r.refund_orderId }" class="table table-borderless">       
                   
            </table>
            </td>
          </tr>
          </c:forEach>
        </table>
      </div>
      
    </div>
			</div>
		</div>
</body>
</html>