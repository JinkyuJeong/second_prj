<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html>
<html lang="en">
<head>
<title><sitemesh:write property="title"/></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" >
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR:wght@600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${path }/css/style_admin.css">
<script type="text/javascript" src="http://cdn.ckeditor.com/4.5.7/full/ckeditor.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<style>
  .nav-item.dropdown-item a{
    text-decoration: none;
  }
</style>
<sitemesh:write property="head"/>
</head>
<body class="w3-light-grey">
  
  <!-- 상단 Nav -->
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top" id="topNav">
    <div class="container-fluid">
      <a class="navbar-brand ms-5" href="#"><span class="text-danger">호미짐</span> 관리자</a>
      <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
        <ul class="navbar-nav ml-auto mr-4">
          <li class="nav-item">
            <a class="nav-link" href="#"><i class="fa fa-users" aria-hidden="true"></i> 회원관리</a></a>
          </li>
          <li class="nav-item dropdown" style="margin-right: 100px;">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"><i class="fa fa-list-ul" aria-hidden="true"></i> 게시판관리</a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">공지사항</a></li>
              <li><a class="dropdown-item" href="#">자주하는 질문</a></li>
            </ul>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#"><i class="fa fa-sign-out" aria-hidden="true"></i> 로그아웃</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- 왼쪽 사이드 바 -->
  <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:250px;" id="mySidebar"><br>
    <div class="container">
      <h3 class="text-center">쇼핑몰 관리</h3>
    </div>
    <hr>
    <div class="w3-bar-block">
      <ul class="navbar-nav ml-auto mr-4">
        <li class="nav-item dropdown-item w3-blue">
          <h4><a href=""><i class="fa fa-shopping-bag" aria-hidden="true"></i> 상품관리</a></h4>
        </li>
        <li class="nav-item dropdown-item">
          <h4><a href=""><i class="fa fa-cube" aria-hidden="true"></i> 재고관리</a></h4>
        </li>
        <li class="nav-item dropdown-item">
          <h4><a href="" ><i class="fa fa-paper-plane-o" aria-hidden="true"></i> 주문관리</a></h4>
        </li>
        <li class="nav-item dropdown-item">
          <h4><a href="" ><i class="fa fa-line-chart" aria-hidden="true"></i> 통계분석</a></h4>
        </li>
        <li class="nav-item dropdown-item">
          <h4><a href="" ><i class="fa fa-question-circle" aria-hidden="true"></i> 상품문의</a></h4>
        </li>
        <li class="nav-item dropdown-item">
          <h4><a href=""><i class="fa fa-ticket" aria-hidden="true"></i> 쿠폰관리</a></h4>
        </li>
      </ul>
    </div>
  </nav>
  
  <div style="margin-left:270px;margin-top:43px;margin-right: 50px ;min-height: 100vh;">
    <!-- 사이트매시 바디 시작-->
    <sitemesh:write property="body"/>
    <!-- 사이트매시 바디 끝 -->
  </div>

  <hr>

  <!-- 푸터 -->
  <footer class="w3-container w3-padding-16 w3-center">  
    <div class="w3-xlarge w3-padding-32 fw-bold">
      <h5><i class="fa fa-map-marker"></i> 구디아카데미 GDJ62기 두리두리차두리 (정진규, 강수빈)</h5>
      <h5><i class="fa fa-envelope"></i> rritjy@naver.com | zxc2289@naver.com</h5>
      <h5><i class="fa fa-instagram"></i> @jeongjingyu63 | @sub__b.in</h5>
    </div>
  </footer>

</body>
</html>
