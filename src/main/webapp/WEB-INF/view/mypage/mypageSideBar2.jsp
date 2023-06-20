<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<nav style="width:200px;" id="mySidebar">
  <div class="w3-container w3-row">
    <div class="w3-col s8 w3-bar">
      <h3>마이페이지</h3>
    </div>	
  </div>
  <hr>
  <div class="w3-bar-block">
    <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>&nbsp; Close Menu</a>
    <a href="${path}/mypage/orderList" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="user" }'>w3-blue</c:if>">
    <i class="fa fa-users fa-fw"></i>&nbsp; 주문목록</a>
    <a href="${path}/item/list" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="item" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 취소/반품/교환 내역</a>
    <a href="${path}/chat/chat" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="chat" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 환불/입금 내역</a>
    <a href="${path}/cart/cartAdd" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="chat" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 장바구니</a>
    <hr>
    <a href="${path}/board/list?boardid=1" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="board" && boardid=="1" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 1:1문의</a>
    <hr>
    <a href="${path }/naver/search" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url ==  "naver"}'>w3-blue</c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 회원 정보 조회</a>
    <a href="${path }/naver/search" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url ==  "naver"}'>w3-blue</c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 회원정보 수정</a>
    <a href="${path }/naver/search" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url ==  "naver"}'>w3-blue</c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 배송지 관리</a>
    <a href="${path }/naver/search" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url ==  "naver"}'>w3-blue</c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 회원 탈퇴</a>
  </div>
  <br>    

</nav>