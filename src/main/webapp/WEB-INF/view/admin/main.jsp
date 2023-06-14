<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호미짐 관리자</title>
</head>
<body>
	<br><br>
    <div class="container">
      <h3><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 오늘의 주문 현황</h3>
      <p class="text-danger text-end">2023년 6월 11일</p>
      <div class="container w3-white p-3">
        <table class="table table-bordered text-center align-middle">
          <tr class="table-secondary">
            <th rowspan="2">결제</th>
            <th rowspan="2">상품준비중</th>
            <th rowspan="2">배송대기</th>
            <th rowspan="2">배송중</th>
            <th>취소</th>
            <th>반품</th>
            <th rowspan="2">상품문의</th>
          </tr>
          <tr class="table-secondary">
            <th>신청/처리완료</th>
            <th>신청/처리완료</th>
          </tr>
          <tr>
            <td>2</td>
            <td>2</td>
            <td>2</td>
            <td>2</td>
            <td>2</td>
            <td>2</td>
            <td>2</td>
          </tr>
        </table>
      </div>

      <h3 class="mt-5"><i class="fa fa-caret-square-o-right text-primary" aria-hidden="true"></i> 쇼핑몰 현황</h3>
      <div class="container w3-white p-3">

        <div>
          <button type="button" class="btn text-primary">일일매출현황</button>
          <button type="button" class="btn">주문현황</button>
          <button type="button" class="btn">상품문의현황</button>
        </div>

        <div class="row mt-3">
          <div class="col-6" id="todayChart">
            <br>
            <canvas id="canvas1" style="width:100%"></canvas>
            <br>
            <p class="ms-3">※ 최근 업데이트 일시 : 2023년 6월 11일 (자정 12시에 초기화됨)</p>
          </div>
          <div class="col-6" id="todayShop">
            <table class="table table-bordered align-middle">
              <tr class="table-secondary text-center">
                <th width="30%">날짜</th>
                <th width="22.5%">주문</th>
                <th width="22.5%">결제완료</th>
                <th width="25%">환불(취소/반품)</th>
              </tr>
              <tr class="text-danger text-end">
                <td class="text-center">2023년 6월 11일</td>
                <td>100,000원<br>(4건)</td>
                <td>90,000원<br>(4건)</td>
                <td>10,000원<br>(1건)</td>
              </tr>
              <tr class="text-end">
                <td class="text-center">2023년 6월 10일</td>
                <td>100,000원<br>(4건)</td>
                <td>90,000원<br>(4건)</td>
                <td>10,000원<br>(1건)</td>
              </tr>
              <tr class="text-end">
                <td class="text-center">최근 7일 합계</td>
                <td>100,000원<br>(4건)</td>
                <td>90,000원<br>(4건)</td>
                <td>10,000원<br>(1건)</td>
              </tr>
              <tr class="text-end">
                <td class="text-center">최근 7일 평균</td>
                <td>100,000원</td>
                <td>90,000원</td>
                <td>10,000원</td>
              </tr>
              <tr class="text-end">
                <td class="text-center">최근 30일 합계</td>
                <td>100,000원<br>(4건)</td>
                <td>90,000원<br>(4건)</td>
                <td>10,000원<br>(1건)</td>
              </tr>   
              <tr class="text-end">
                <td class="text-center">최근 30일 평균</td>
                <td>100,000원</td>
                <td>90,000원</td>
                <td>10,000원</td>
              </tr>             
            </table>
          </div>
        </div>
      </div>
    </div>
    
    <script>
  $(()=> {
    barGraphPrint();
  });

  let rsf = function(){
    return Math.round(Math.random()*255);
  }
  let randomColor= function(opacity){
    return "rgba("+ rsf() + "," + rsf() + "," + rsf() + "," + (opacity || '0.3') +")";
  }

  function barGraphPrint(){
		let regdates = ['2023-06-11','2023-06-10','2023-06-09'];	
		let datas = ['7','6','5'];
		let colors = [randomColor(0.7),randomColor(0.5),randomColor(0.9)];
		
		let chartData = {
			labels : regdates,
			datasets : [{
				type : "line",
				borderWidth : 2,
				boraderColor : colors,
				label : "매출금액",
				fill : false,
				data : datas
			},{
				type : "bar",
				label : "매출금액",
				backgroundColor : colors,
				data : datas
			}]
		}

		let config = {
			type : 'bar',   //그래프 종류
			data : {        //데이터 정보
				datasets : [
				  { type : "line",	borderWidth : 2,   borderColor : colors,
					label :'매출금액',	fill : false,  	   data : datas },
                  {	type : "bar",  backgroundColor : colors,  label :'매출 금액',	data : datas }
                 ],
			     labels : regdates,
			},
			options : {
				responsive : true,
				legend : {display:false},
			    title : {
			    	display : true, 	text : "최근 7일 매출 현황",
			    	position : 'bottom'
			    },
			    scales : {
			    	xAxes : [{ display : true,
			    		       scaleLabel : {display : true, labelString : "날짜"}
			    	         }],
			    	yAxes : [{
			    		scaleLabel : { display : true, labelString : "매출 금액(만원)"  },
			    		ticks : {beginAtZero : true}
			    	  }]
			    }
			}
	}
		
		let ctx = document.getElementById("canvas1").getContext('2d');
		new Chart(ctx, config);
	}	// barGraphPrint
</script>
</body>
</html>