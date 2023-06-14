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
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${path }/css/style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
  $(()=> {
    $('#prodMenu .nav-link').click(function(event) {
      event.preventDefault();
      // Remove active class from all nav links
      $('#prodMenu .nav-link').removeClass('active');
      // Add active class to the clicked nav link
      $(this).addClass('active');
    });
  });
</script>
<sitemesh:write property="head"/>
</head>
<body>
  
  <!-- 상단 Nav -->
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
      <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
        <ul class="navbar-nav ml-auto mr-4">
          <li class="nav-item">
            <a class="nav-link" href="#"><i class="fa fa-user-plus" aria-hidden="true"></i> 회원가입 / <i class="fa fa-user" aria-hidden="true"></i> 마이페이지</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#"><i class="fa fa-sign-in" aria-hidden="true"></i> 로그인 / <i class="fa fa-sign-out" aria-hidden="true"></i> 로그아웃</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#"><i class="fa fa-shopping-cart" aria-hidden="true"></i> 장바구니</a>
          </li>  
          <li class="nav-item dropdown" style="margin-right: 100px;">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">고객센터</a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">공지사항</a></li>
              <li><a class="dropdown-item" href="#">자주하는 질문</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container mt-1" id="header">
    <div id="logoSearch">
      <div>
        <img src="${path }/images/mainLogo.JPG" id="logo">
      </div>
      <div>
        <div id="prodMenu"class="ms-5 mt-5">
          <nav class="navbar navbar-expand-sm navbar-light fw-bold fs-5">
            <div class="container-fluid">
              <ul class="navbar-nav">
                <li class="nav-item">
                  <a class="nav-link" href="#">상품1</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">상품2</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">상품3</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">상품4</a>
                </li>
                <li class="nav-item ms-5">
                  <a class="nav-link" href="#">💪 오운완 챌린지</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </div>
  </div>

  <hr class="my-4 custom-divider">

  <div style="min-height: 100vh;">
    <!-- 사이트매시 바디 시작-->
    <sitemesh:write property="body"/>
    <!-- 사이트매시 바디 끝 -->
  </div>

  <hr>

  <!-- 푸터 -->
  <footer class="w3-container w3-padding-16 w3-center">  
    <div class="w3-xlarge w3-padding-32 fw-bold">
      <h5><i class="fa fa-map-marker"></i> 구디아카데미 GDJ62기 CNT2조 정진규, 강수빈</h5>
      <h5><i class="fa fa-envelope"></i> rritjy@naver.com | zxc2289@naver.com</h5>
      <h5><i class="fa fa-instagram"></i> @jeongjingyu63 | @sub__b.in</h5>
    </div>
  </footer>


</body>
</html>