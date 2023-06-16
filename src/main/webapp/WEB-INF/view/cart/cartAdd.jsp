<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"	 %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
</head>
<body>
	<div class="container">
      <h1 class="mt-5">장바구니</h1>
      <div class="row mt-4">
        <div class="col-lg-8">
          <hr>
          <c:forEach items="${map}" var="map">          
          <table class="table">
            <tr>
              <td rowspan="4"><img src="${path }/img/thumb/${map.value.product_thumb }" style="width:100px;height:100px;"></td>
              <th>상품명</th>
              <td>${map.value.product_name }</td>
            </tr>
            <tr>
              <th>선택 옵션 명</th>
              <td>${map.value.opt_name }</td>
              <td rowspan="4"><button type="button" class="btn-close" aria-label="Close"></button></td>
            </tr>
            <tr>
              <th>구입수량</th>
              <td>
                <div class="btn-group">
                  <button type="button" class="btn btn-secondary" disabled>-</button>
                  <input type="number" name="quantity" style="width:50px;" value="${map.key.opt_count }">
                  <button type="button" class="btn btn-secondary">+</button>
                </div>
              </td>
            </tr>
            <tr>
              <th>가격</th>
              <td><fmt:formatNumber value="${(map.value.product_price * (100-map.value.product_discountRate)/100) * map.key.opt_count }" pattern=",###"/>원</td>
              
            </tr>
          </table>
          </c:forEach>
        </div>

        <div class="col-lg-4 sticky-card">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">결제정보</h5>
              <hr>
              <p class="card-text">주문금액: <fmt:formatNumber value="${total }" pattern=",###"/>원</p>
              <p class="card-text">할인: <fmt:formatNumber value="${discounted }" pattern=",###"/>원</p>
              <p class="card-text">결제예정금액: <fmt:formatNumber value="${discountedTotal }" pattern=",###"/>원</p>
            </div>
          </div>
          <div class="mt-3">
            <button type="button" class="btn-danger btn-lg"  style="width:100%">구매하기</button>
          </div>
        </div>
      </div>
    </div>
</body>
</html>