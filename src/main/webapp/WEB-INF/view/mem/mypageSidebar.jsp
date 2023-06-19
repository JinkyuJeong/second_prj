<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="flex-shrink-0 p-3 bg-white" style="width: 280px;">
        <a href="/" class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
          <span class="ms-3" style="font-size:30px;">마이페이지</span>
        </a>
        <ul class="list-unstyled ps-0">
          <li class="a mb-1 ms-1">
              쇼핑정보
            <div>
              <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small ms-3">
                <li><a href="#" class="link-dark rounded">-주문목록/배송조회</a></li>
                <li><a href="#" class="link-dark rounded">-취소/반품/교환 내역</a></li>
                <li><a href="#" class="link-dark rounded">-환불/입금 내역</a></li>
                <li><a href="#" class="link-dark rounded">-장바구니</a></li>
              </ul>
            </div>
          </li>
          <li class="mb-1 ms-1">
              고객센터
            <div>
              <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small ms-3">
                <li><a href="#" class="link-dark rounded">-1:1문의</a></li>
              </ul>
            </div>
          </li>
          <li class="mb-1 ms-1">            
              회원정보
            <div>
              <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small ms-3">
                <li><a href="#" class="link-dark rounded">-회원정보 수정 </a></li>
                <li><a href="#" class="link-dark rounded">-배송지관리</a></li>
                <li><a href="#" class="link-dark rounded">-회원탈퇴</a></li>
              </ul>
            </div>
          </li>
          <li class="border-top my-3"></li>
          <li class="mb-1">
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#account-collapse">
              Account
            </button>
            <div class="collapse" id="account-collapse">
              <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                <li><a href="#" class="link-dark rounded">New...</a></li>
                <li><a href="#" class="link-dark rounded">Profile</a></li>
                <li><a href="#" class="link-dark rounded">Settings</a></li>
                <li><a href="#" class="link-dark rounded">Sign out</a></li>
              </ul>
            </div>
          </li>
        </ul>
      </div> 
</body>
</html>