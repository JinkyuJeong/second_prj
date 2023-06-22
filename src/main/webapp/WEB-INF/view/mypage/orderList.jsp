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
	function cancel(order_id) {
		$.ajax({
			url : "${path}/ajax/cancelOrder",
			method : "POST",
			data : { order_id: order_id },
			success : function(result) {
				alert(result);
			}, 
			error : function(e) {
				alert("[ajax] 주문취소 오류 : " + e.status);
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
      <h1 class="mb-3">주문조회</h1>
      <div class="row">
        <div class="col-2">
          <h5>총 <span style="color: red;">${orderCnt }</span>건</h5>
        </div>
      </div>
      <div class="row" id="oinfo" class="info">
      
      
        <table class="table table-hover">
          <tr style="text-align:center; background-color:#D1180B; color: white;">
            <th>주문일자</th>
            <th>주문번호</th>
            <th>제품명</th>
            <th>결제금액</th>
            <th>처리현황</th>
            <th>결제취소</th>
            <th>환불요청</th>
          </tr>
        <c:forEach items="${map }" var="map" varStatus="st">
          <tr style="text-align:center;">
            <td><fmt:formatDate value="${map.value.get(0).order_date }" pattern="yyyy년 MM월 dd일" /></td>
            <td><a href="javascript:list_disp('${map.key }')">${map.key }</a></td>
            <c:if test="${map.value.size() ==1 }">
            	<td>${map.value.get(0).product_name }</td>
            </c:if>
            <c:if test="${map.value.size() !=1 }">
            	<td>${map.value.get(0).product_name } 외 ${map.value.size() -1 } 개</td>
            </c:if>            
            <td><fmt:formatNumber value="${map.value.get(0).order_totalPay }" pattern="###,###"/>원</td>
            <td>${map.value.get(0).order_state }</td>
            <td>
            	<c:if test="${map.value.get(0).order_state=='결제완료' }">
            		<button type="button" class="btn btn-outline-danger btn-sm" onclick="cancel('${map.key}')">주문취소</button>
            	</c:if>             	           
            </td>
            <td>
            	<c:if test="${map.value.get(0).order_state=='배송완료' }">
            		<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">환불신청</button>
            	</c:if>
            	<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">환불신청</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        주문번호 '${map.key }' 에 대해 환불신청하시겠습니까?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니요</button>
        <button type="button" class="btn btn-danger" onclick="location.href='refundReq?mem_id=${sessionScope.loginMem.mem_id}&order_id=${map.key }'">네</button>
      </div>
    </div>
  </div>
</div>
            </td>
          </tr>
                    
          <!-- 주문 상세정보 -->
          <tr style="text-align:center;" class="saleLine" id="saleLine${map.key }">
            <td colspan="7">
            <table id="orderDetail${map.key }" class="table table-borderless">                    
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