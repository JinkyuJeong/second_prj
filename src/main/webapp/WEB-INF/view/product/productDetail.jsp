<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
<!-- 상품 사진 슬라이드 -->
<script>
  let slideIndex = 0;

  window.onload = function() {
    showSlides(slideIndex);
  };

  function plusSlides(n) {
    showSlides(slideIndex += n);
  }

  function showSlides(n) {
    const slides = document.getElementsByClassName("slide");
    if (n >= slides.length) {
      slideIndex = 0;
    }
    if (n < 0) {
      slideIndex = slides.length - 1;
    }
    for (let i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";
    }
    slides[slideIndex].style.display = "block";
  }
</script>
<script type="text/javascript">	
	var pageNum;
	$(function() {
		//리뷰 페이징
		pageNum = 1;
		listpage(pageNum);
		
		$("#optionSelect").change(function () {
			addSelectedOption();			
		})
		$(document).on("click", ".removeBtn", function() {
		    var containerElement = $(this).closest(".selected-option");
		    containerElement.remove();
		    addSum();
		});
		//수량 변경(-)
		$(document).on("click", ".minus", function() {
	        var quantityInput = $(this).siblings("input[name='quantity']");
	        var currentQuantity = parseInt(quantityInput.val());
	        if (currentQuantity > 1) {
	            quantityInput.val(currentQuantity - 1);
	        }
	        addSum();
	    });
		//수량 변경(+)
	    $(document).on("click", ".plus", function() {
	        var quantityInput = $(this).siblings("input[name='quantity']");
	        var opt_number = $(this).siblings("input[name='opt_number']");
	        var currentQuantity = parseInt(quantityInput.val());
	        quantityInput.val(currentQuantity + 1);
	        $.ajax({
	        	  url: "${path}/ajax/opt_quantityCheck",
	        	  type: "POST",
	        	  data: { opt_number: opt_number.val() },
	        	  success: function(result) {
	        	    if (quantityInput.val() > parseInt(result)) {
	        	      alert("상품 재고보다 많이 주문할 수 없습니다.");
	        	      quantityInput.val(result);
	        	      return;
	        	    }
	        	  },
	        	  error: function(e) {
	        	    alert("재고 수량 체크 에러: " + e.status);
	        	  }
	        });
	        addSum();
	    });
	    $("#cartBtn").click(function(e) {
	        e.preventDefault();
	        var selectedOption = $("#optionSelect").val();
	        if (selectedOption === "optionNotSelected") {
	            $("#staticBackdrop .modal-body").text("옵션이 선택되지 않았습니다. 장바구니로 이동하시겠습니까?");
	        } else {
	        	// FormData 객체 생성
	            var formData = new FormData(document.getElementById("cart"));
	            
	            // 선택한 옵션의 수량과 옵션 번호를 배열로 추가
	            var quantities = [];
	            var optNumbers = [];
	            
	            $(".quantityInput").each(function() {
	                quantities.push($(this).val());
	            });

	            $(".hiddenOpt_number").each(function() {
	                optNumbers.push($(this).val());
	            });

	            // FormData에 배열로 추가
	            for (var i = 0; i < quantities.length; i++) {
	                formData.append("quantities[]", quantities[i]);
	            }

	            for (var i = 0; i < optNumbers.length; i++) {
	                formData.append("optNumbers[]", optNumbers[i]);
	            }
	            //장바구니 db에 정보 insert, update
	        	 $.ajax({    	
	        			url : "${path}/ajax/cartAdd",
	        			type : "POST",
	        			data: formData,
	        			processData: false,
	        		    contentType: false,
	        			success : function(result) {
	        				if(result == 'shouldLogin') {
	        					alert("로그인이 필요한 서비스 입니다.");
	        					location.href="/second_prj/mem/login"
	        					return;
	        				}
	        				console.log(result)
	        			},
	        			error : function(e) {
	        				alert("장바구니 담기 에러 : " + e.status)
	        			}
	        	})
	            $("#staticBackdrop").modal("show");
	        }
	    });
	    //장바구니로 이동 버튼 클릭
	    $("#moveToCart").click(function() {
	        window.location.href = "../cart/cartAdd?mem_id=${sessionScope.loginMem.mem_id} ";
	    });
	    $("#moveToList").click(function() {
	    	 $("#staticBackdrop").modal("hide");
	    })
	    $("#buyBtn").click(function(e) {
	    	$("#cart").submit();
	    });
	})
	//옵션 선택 시 동작하는 함수
  function addSelectedOption() {
    var selectBox = document.getElementById("optionSelect");
    var selectedOption = selectBox.options[selectBox.selectedIndex];
    var selectedValue = selectedOption.value;
    var selectedText = selectedOption.text;

    // 이미 선택된 옵션인지 확인
    var selectedOptions = document.getElementsByClassName("selected-option");
    for (var i = 0; i < selectedOptions.length; i++) {
      var option = selectedOptions[i];
      if (option.dataset.value === selectedValue) {
        alert("이미 선택된 옵션입니다.");
        return;
      }
    }
    // soldOut인지 확인
    if (selectedValue === "soldOut") {
      alert("품절된 상품입니다.");
      return;
    }   
    let param = {opt_number: parseInt($("#opt_number").val())};
    console.log(selectedValue)
    $.ajax({    	
		url : "${path}/ajax/optionSelect",
		type : "POST",
		data: { opt_number: parseInt(selectedValue) },
		success : function(result) {
			// 내용 추가
		    var selectedOptionsDiv = document.getElementById("selectedOptions");
		    var newOption = document.createElement("div");
		    newOption.className = "selected-option";
		    newOption.dataset.value = selectedValue;
		    newOption.innerHTML = result;
		    selectedOptionsDiv.appendChild(newOption);
		    addSum();
		},
		error : function(e) {
			alert("옵션 추가 에러 : " + e.status)
		}
	})
  }	
	function addSum() {
		  var totalQuantity = 0;
		  var totalAmount = 0;

		  // 선택된 옵션들의 수량과 금액을 계산하여 업데이트
		  var selectedOptions = document.getElementsByClassName("selected-option");
		  for (var i = 0; i < selectedOptions.length; i++) {
		    var option = selectedOptions[i];
		    var quantityInput = $(option).find("input[name='quantity']");
		    var quantity = parseInt(quantityInput.val());
		    var amount = quantity * ${product.product_price * (100-product.product_discountRate)/100};
		    console.log($("#price").text())
		    totalQuantity += quantity;
		    totalAmount += amount;
		  }

		  // 총 수량 업데이트
		  document.getElementById("totalQuantity").textContent = "총 수량 " + totalQuantity + "개";

		  // 총 상품 금액 업데이트
		  document.getElementById("totalAmount").textContent = totalAmount.toLocaleString() + "원";
	}	
	
	//리뷰 페이징
	var pageNum = ${pageNum};
	function listpage(newPageNum) {		
		$.ajax({
	        type: "POST",
	        url: "${path}/ajax/reviewAjax",
	        data: { pageNum: newPageNum, product_number : ${param.product_number}},
	        success: function (result) {
	            $("#reviewDiv").html("");
	            var html = "";
	            if(result == null || result.length == 0) {
	            	html += "<p class='text-secondary' style='margin-left:100px;'>아직 등록된 리뷰가 없습니다.</p>"
	            } else {
	            	result.forEach(function (r) {
		                var reviewValue = "";
		                if (r.review_value == '1') {
		                    reviewValue = "★☆☆☆☆";
		                } else if (r.review_value == '2') {
		                    reviewValue = "★★☆☆☆";
		                } else if (r.review_value == '3') {
		                    reviewValue = "★★★☆☆";
		                } else if (r.review_value == '4') {
		                    reviewValue = "★★★★☆";
		                } else if (r.review_value == '5') {
		                    reviewValue = "★★★★★";
		                }	                
		                html += '<div class="row mx-5 mt-1 mb-1">';
		                html += '	<div class="col">' + reviewValue + '</div>' 
						if(r.mem_id == null || r.mem_id == '') {
							html += ' <div class="col">탈퇴한 회원</div>';
						} else {
							html += ' <div class="col">' + r.mem_id.substring(0, 3) + '***</div>';
						}
		                html += ' <div class="col-7">' + r.review_content + '</div>';
		                html += ' <div class="col">' + new Date(r.review_date).toLocaleDateString("ko-KR", { year: 'numeric', month: '2-digit', day: '2-digit' }) + '</div>';
		                html += '</div>'; 	                    
		            })
	            }	            
	            $("#reviewDiv").html(html);
	            pageNum = newPageNum;
	        },
	        error: function (e) {
	            console.log(e.status);
	        }
		});
	}
	function noPage() {
		alert("페이지가 존재하지 않습니다.");
	}
	// 이전 페이지로 이동하는 함수
	function goToPreviousPage() {
	    var previousPage = pageNum - 1;
	    if (previousPage >= 1) {
	        listpage(previousPage);
	    }
	}

	// 다음 페이지로 이동하는 함수
	function goToNextPage() {
	    var nextPage = pageNum + 1;
	    listpage(nextPage);
	}
	//리뷰 페이징 끝
	
	
</script>
</head>
<body>
	<div class="container">
      <!-- 상품 소개 -->
      <div class="row border">
        <div class="col-6 d-flex flex-column align-items-center">
          <div class="slideshow">
          	  <c:forEach items="${product_pircturesList }" var="p">
          	  	<img class="slide zoomImage" src="${path }/img/product/${p}" style="width:100%">
              </c:forEach>          
          </div>
          <div class="d-flex justify-content-center mt-3">
            <button class="btn" onclick="plusSlides(-1)">이전 <i class="fa fa-arrow-circle-left"></i></button>
            <button class="btn" onclick="plusSlides(1)"><i class="fa fa-arrow-circle-right"></i> 다음</button>  
          </div>
        </div>
        <div class="col-6">
          <div class="row">
              <div>
                <h1>${product.product_name }</h1> 
                <div class="row mt-1">
                  <c:if test='${product.product_isDiscount==0 }'>
                  	<div class="col-9"></div>
                  	<div class="col-3" style="font-size:30px;" id="price"><fmt:formatNumber value="${product.product_price}" />원</div>
                  </c:if>
                  <c:if test='${product.product_isDiscount==1 }'>
                  	<div class="col-7 text-primary" style="font-size:30px;">${product.product_discountRate }%</div>
                  	<div class="col-2 text-secondary" style="font-size:30px; text-decoration:line-through"><fmt:formatNumber value="${product.product_price }" pattern=",###" /></div>
                  	<c:set var="discounted" value="${product.product_price*(100-product.product_discountRate)/100 }" />
                  	<c:set var="dc" value="${discounted+(1-(discounted%1))%1}"/>
                  	<div class="col-3" style="font-size:30px;" id="price"><fmt:formatNumber value="${dc}" />원</div>
                  </c:if>                  
                </div>
                <hr>  
                <div class="row mb-2">
                  <div class="col-2 text-secondary"><i class="fa fa-bus" aria-hidden="true"></i> &nbsp; 택배배송</div>
                  <div class="col text-secondary">30,000원 이상 구매 시 무료배송, CJ대한통운</div>
                </div>
<!--               <div class="row">
                  <div class="col text-secondary">제주 추가 4,000원, 제주 외 도서지역 추가 4,000원</div>
                </div> -->  
                <hr>
           <form action="../cart/checkout" method="post" name="cart" id="cart">
           	  <input type="hidden" value="${param.product_number }" name="product_number">
              <div>
                <div class="row">
                  <div class="col-12">
                    <select class="form-select" id="optionSelect">
                      <option value="optionNotSelected" disabled selected>옵션 선택</option>
                      <c:forEach items="${optList }" var="o">
                      	<c:if test="${o.opt_quantity==0 }">
                      		<option class="text-secondary" value="soldOut" id="opt_number">${o.opt_name }[품절]</option>
                      	</c:if>
                      	<c:if test="${o.opt_quantity>0 && o.opt_quantity <= 5}">
                      		<option value="${o.opt_number }" id="opt_number">${o.opt_name } &nbsp; [${ o.opt_quantity} 개 남음]</option>
                      	</c:if> 
                      	<c:if test="${o.opt_quantity > 5}">
                      		<option value="${o.opt_number }" id="opt_number">${o.opt_name }</option>
                      	</c:if>                     	
                      </c:forEach>
                    </select>
                  </div>                  
                </div>
                <hr>
                <div class="option_box_wrap">
  					<div class="option_box" id="selectedOptions">
  						<!-- 선택된 옵션 보여지는 부분 -->
  					</div>
				</div>
                <hr>
                <div class="row mt-3">
                  <div class="col-4" style="font-size:30px;">총 상품 금액</div>
                  <div class="col-4 text-secondary" style="font-size:30px;" id="totalQuantity"></div>
                  <div class="col-4 text-primary" style="font-size:30px;" id="totalAmount"></div>
                </div>
                <div class="row mt-5 mb-5">
                  <div class="col-6">
                    <button type="button" id="cartBtn" class="btn-light btn-lg" style="width:100%" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                      <i class="fa fa-shopping-cart" aria-hidden="true"></i>&nbsp;&nbsp; 장바구니
                    </button>
                    
                    <!-- 모달창 -->
               <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                 <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                   <div class="modal-content">
                     <div class="modal-header">
                       <h5 class="modal-title" id="staticBackdropLabel">호미짐</h5>
                       <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <div class="modal-body">
                       장바구니에 상품이 추가되었습니다.
                     </div>
                     <div class="modal-footer">
                       <button type="button" id="moveToCart" class="btn btn-secondary" data-bs-dismiss="modal">장바구니로 이동</button>
                       <button type="button" id="moveToList" class="btn btn-primary">계속쇼핑하기</button>
                     </div>
                   </div>
                 </div>
               </div>
                                   
                  </div>
                  <div class="col-6">
                    <button type="submit" class="btn-danger btn-lg"  id="buyBtn" style="width:100%">
                      <i class="fa fa-credit-card" aria-hidden="true"></i>&nbsp;&nbsp; 구매하기
                    </button>
                  </div>                  
                </div>
              </div>
            </form>
          </div>
        </div>          
      </div>
      
      <!-- 리뷰 -->
      <div class="row">
      <hr>
        <div class="row">
            <div class="col-9 ms-3" style="font-size:20px;">&nbsp;&nbsp;리뷰(${reviewList.size() })</div>
            <input type="hidden" name="pageNum" id="pageNum" value="1">
            <div class="col-2 text-end" style="font-size:25px;">
      			<a href="javascript:goToPreviousPage()" class="w3-bar-item w3-button w3-hover-black" style="font-size:20px">&laquo;</a>
				<a href="javascript:goToNextPage()" class="w3-bar-item w3-button w3-hover-black" style="font-size:20px">&raquo;</a>
            </div>
        </div>
        <div id="reviewDiv">    

        </div> 
      <!--상품 상세 설명-->
      <div class="row mt-5 justify-content-center">
      	<div class="col-12 text-center">
        	${product.product_desc }
        </div>
      </div>
      
    </div>
</body>
</html>