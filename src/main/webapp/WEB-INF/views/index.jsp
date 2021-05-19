<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="layout/header.jsp"%>
<div class="container">

<c:forEach var="board" items="${boards.content}">
	<div class="card m-2">
		<div class="card-body">
			<h4 class="card-title"><c:out value="${board.title}" /></h4>
			<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
		</div>
	</div>
</c:forEach>

<ul class="pagination justify-content-center">
<c:choose>
	<c:when test="${boards.first}">
  <li class="page-item disabled"><a class="page-link" href="">Previous</a></li>
	</c:when>
	<c:otherwise>
  <li class="page-item"><a class="page-link" href="?page=${boards.number-1}&keyword=${keyword}">Previous</a></li>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${boards.last}">
  <li class="page-item disabled"><a class="page-link" href="">Next</a></li>
	</c:when>
	<c:otherwise>
  <li class="page-item"><a class="page-link" href="?page=${boards.number+1}&keyword=${keyword}">Next</a></li>
	</c:otherwise>
</c:choose>
</ul>
<div class="container">
	<div class="m-2">
  <div class="form-inline d-flex justify-content-end">
    <input id="keyword" type="text" name = "keyword" class="form-control mr-sm-2" placeholder="search" value="${keyword}">
    <button id="search__btn" class="btn btn-success m-1" type="submit">검색</button>
  </div>
	</div>
</div>
 </div>
  <script>
	$('#search__btn').on('click', function(){
		var keyword = $('#keyword').val();
		location.href='/?keyword='+keyword;
		
	});
</script>
<!--  
 <script>
	$('#search__btn').on('click', function(){
		var keyword = $('#keyword').val();
		
		
		$.ajax({
			type: "POST",
			url: "/api/board/search",
			data: JSON.stringify({"keyword":keyword}),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
		
		
		
	});
</script>
 -->
 
<%@ include file="layout/footer.jsp"%>
