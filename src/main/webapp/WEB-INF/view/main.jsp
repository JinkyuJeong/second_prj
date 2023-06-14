<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
<style>
  .carousel-item img {
    min-width: 500px;
    width: 100vw;
    height: 80vh; /* 이미지 높이 고정 */
    object-fit: cover; /* 이미지 비율 유지 */
  }
  .jumbo {
	  position: absolute;
	  top: 40%;
	  left: 30%;
	  transform: translate(-50%, -50%);
	  text-align: centel;
	  z-index: 1;
	}
	.jumbo h3, .jumbo h5 {
	  color: white;
	  text-shadow: 3px 3px 3px rgba(0, 0, 0, 0.5);
	  margin: 0;
    font-family: 'Do Hyeon', sans-serif;
	}
  .carousel-control-prev,
  .carousel-control-next {
    background-color: rgba(255, 255, 255, 0.5); /* 버튼의 배경색을 투명 검은색으로 설정합니다 */
    border: none; 
    opacity: 0.7; 
    width: 60px; 
    height: 60px; 
    position: absolute; 
    top: 50%; 
    transform: translateY(-50%); 
    z-index: 1;
  }
  .carousel-control-prev {left : 2.5%}
  .carousel-control-next {right : 2.5%}
  .carousel-control-prev:hover,
  .carousel-control-next:hover {opacity: 1;}
  .w3-quarter h2 {font-weight : bold;}
	.w3-quarter {
		height:330px;
		width : 22%;
		margin : 10px;
		box-shadow: 0px 4px 8.5px 0 rgba(0,0,0,0.4);
		display : flex;
		flex-direction: column;
		transition: transform 0.2s ease;
    text-align: left;
	}
  .w3-quarter:hover {
    transform: scale(1.1);
    z-index: 9;
  }
  a{text-decoration: none;}
  a:hover{color: red;}
</style>
</head>
<body>
	<!-- 사이트매시 바디 시작-->
    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="margin-top: -30px;">
      <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
      </div>
      <div class="carousel-inner">
      
        <div class="carousel-item active">
          <div class="jumbo">
            <h3 class="display-4 fw-bold mb-4">덤벨 가격 실화?</h3>
            <h5 class="mb-4">
              호미짐 오픈기념 덤벨 10% 할인!!
              <br>
              <br>
              <a class="btn btn-danger btn-lg" href="" role="button">바로가기</a>
            </h5>
          </div>
          <img src="images/item.jpg" class="d-block w-100" alt="...">
        </div>
        
        <div class="carousel-item">
          <div class="jumbo">
            <h3 class="display-4 fw-bold mb-4">오운완 챌린지</h3>
            <h5 class="mb-4">
              매일매일 📷인증샷을 남겨 포인트를 받아가세요! 
              <br>
              <br> 
              <a class="btn btn-danger btn-lg" href="" role="button">바로가기</a>
            </h5>
          </div>
          <img src="images/event.jpg" class="d-block w-100" alt="...">
        </div>
        
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>

    <div class="container">
      <h3 class="fw-bold mt-5">👍 호미짐 BEST4 상품</h3>
      <div class="w3-row-padding w3-padding-16 w3-center">
        <div class="w3-quarter">
          <h2>Best 1</h2>
          <a href=""><img src="images/product.jpg" style="width:100%">
          <h4 class="ms-3">상품명1</h4></a>
          <div class="ms-3"><span>30,000원</span></div>
        </div>
        <div class="w3-quarter">
          <h2>Best 2</h2>
          <a href=""><img src="images/product.jpg" style="width:100%">
          <h4 class="ms-3">상품명2</h4></a>
          <div class="ms-3"><span>30,000원</span></div>
        </div>
        <div class="w3-quarter">
          <h2>Best 3</h2>
          <a href=""><img src="images/product.jpg" style="width:100%">
          <h4 class="ms-3">상품명3</h4></a>
          <div class="ms-3"><span>30,000원</span></div>
        </div>
        <div class="w3-quarter">
          <h2>Best 4</h2>
          <a href=""><img src="images/product.jpg" style="width:100%">
          <h4 class="ms-3">상품명4</h4></a>
          <div class="ms-3"><span>30,000원</span></div>
        </div>
      </div>
    </div>
</body>
</html>