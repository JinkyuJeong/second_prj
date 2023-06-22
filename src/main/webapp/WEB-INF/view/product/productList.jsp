<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐</title>
<script type="text/javascript">
	function listpage(page) {
		document.searchForm.pageNum.value = page;
		document.searchForm.submit();
	}
	function noPage() {
		alert("페이지가 존재하지 않습니다.");
	}
</script>
<style>
	.noline {text-decoration:none;}
	a.active {color : red;}
</style>
</head>
<body>
	<div class="container">
      <div class="row">
        <div class="col-3" style="font-size:40px;">${product_type_name }</div> 
        <div class="col-5"></div>
        <div class="col">
          <form action="productList" method="post" name="searchForm">
          	<input type="hidden" name="pageNum" value="1">
          	<input type="hidden" name="product_type" value="${param.product_type }">
            <div class="input-group">
              <input type="text" class="form-control" id="searchContent" name="searchContent" placeholder="상품을 검색하세요." value="${searchContent }">
              <button class="btn btn-danger" type="submit" id="button-addon2"><i class="fa fa-search"></i></button>
            </div>
          </form>
        </div>        
      </div>
      <hr>
      <div class="row">
        <div class="col" style="margin-bottom:20px; font-size:20px;">
        	<a href="productList?sort=1" class="noline ${param.sort == 1 ? 'active' : '' }">주문많은순</a>
        	<a href="productList?sort=2" class="noline ${param.sort == 2 ? 'active' : '' }">낮은가격순</a>
        	<a href="productList?sort=3" class="noline ${param.sort == 3 ? 'active' : '' }">높은가격순</a>
        	<a href="productList?sort=4" class="noline ${param.sort == 4 ? 'active' : '' }">최신등록순</a>
        	<a href="productList?sort=5" class="noline ${param.sort == 5 ? 'active' : '' }">리뷰많은순</a>
        </div>     
      </div>
	
	  <c:forEach items="${productList }" var="p" varStatus="st">
	  		<c:if test="${st.index % 3 == 0}">
    			<div class="row">
  			</c:if>
  			<c:if test='${p.product_discountRate != 0 }'>
  			<div class="col-4">
	  		<table class="table">
	  			<tr>
	  				<td>	  					
          				<a href="productDetail?product_number=${p.product_number }"><img src="${path }/img/thumb/${p.product_thumb}" style="width:100%"></a>
          				<div class="row"> 
            				<div class="col-7">            					
              					<div class="text-primary" style="font-size: 20px;">${p.product_discountRate}%</div>              					
            				</div>
            				<div class="col-2 ms-2">
            					<c:set var="discounted" value="${p.product_price*(100-p.product_discountRate)/100 }" />
              					<div style="font-size: 20px;"><fmt:formatNumber value="${discounted+(1-(discounted%1))%1}" /></div>
            				</div>
            				<div class="col-1">
              					<div class="text-secondary" style="font-size: 20px;text-decoration:line-through;"><fmt:formatNumber value="${p.product_price }" pattern=",###" /></div>
            				</div>
          				</div>        
          				<div style="font-size:20px;"><a href="productDetail?product_number=${p.product_number }" style="text-decoration:none;">${p.product_name }</a></div>
         				<div class="text-secondary"><span>평점 4.8</span><span>  리뷰 34</span></div>        				
	  				</td>
	  			</tr>
	  		</table>
	  		</div>
	  		</c:if>
	  		<c:if test='${p.product_discountRate == 0 }'>
  			<div class="col-4">
	  		<table class="table">
	  			<tr>
	  				<td>	  					
          				<a href="productDetail?product_number=${p.product_number }"><img src="${path }/img/thumb/${p.product_thumb}" style="width:100%"></a>
          				<div class="row"> 
            				<div class="col-7">              					
            				</div>
            				<div class="col-2 ms-2">
            				</div>
            				<div class="col-1">
              					<div style="font-size: 20px; text-align:right;"><fmt:formatNumber value="${p.product_price }" pattern=",###" /></div>
            				</div>
          				</div>        
          				<div style="font-size:20px;"><a href="productDetail?product_number=${p.product_number }" style="text-decoration:none;">${p.product_name }</a></div>
         				<div class="text-secondary"><span>평점 4.8</span><span>  리뷰 34</span></div>        				
	  				</td>
	  			</tr>
	  		</table>
	  		</div>
	  		</c:if>
	  		<c:if test="${st.index % 3 == 2 or st.last}">
    			</div>
  			</c:if>
	  </c:forEach>
      	
    <br>
    <div class="w3-center w3-padding-32">
      <div class="w3-bar">
      	<c:if test="${pageNum >1 }">
      		<a href="javascript:listpage('${pageNum -1 }')" class="w3-bar-item w3-button" style="font-size:20px">&laquo;</a>
      	</c:if>
      	<c:if test="${pageNum <= 1 }">
			<a href="javascript:noPage()" class="w3-bar-item w3-button" style="font-size:20px">&laquo;</a>
		</c:if>
		<c:forEach var="a" begin="${startpage }" end="${endpage }">
			<c:if test="${a==pageNum }"><a href="javascript:listpage('${a }')" class="w3-bar-item w3-button w3-hover-black w3-black	" style="font-size:20px">${a }</a></c:if>
			<c:if test="${a != pageNum }">
				<a href="javascript:listpage('${a }')" class="w3-bar-item w3-button w3-hover-black" style="font-size:20px">${a }</a>
			</c:if>
		</c:forEach>
      	<c:if test="${pageNum < maxpage }">
			<a href="javascript:listpage('${pageNum + 1 }')" class="w3-bar-item w3-button w3-hover-black" style="font-size:20px">&raquo;</a>
		</c:if>
		<c:if test="${pageNum >= maxpage }">
			<a href="javascript:noPage()" class="w3-bar-item w3-button" style="font-size:20px">&raquo;</a>
		</c:if>
      </div>
    </div>
</body>
</html>