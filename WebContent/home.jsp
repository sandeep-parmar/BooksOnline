<%@page import="com.dao.DBFacade"%>
<%@page import="com.bean.BookUser"%>
<%@page import="com.dao.*"%>
<%@page import="com.bean.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Books Online</title>
<%@ include file="BootConfig/bootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="Css/style.css"></link>

<script src="Js/basicB.js"></script>
<script type="text/javascript">
function getBookUser(userId,bookId){
	console.log("called:"+userId+"bookid"+bookId);
	var url = "http://localhost:8080";
	document.location.href = url + "/BooksOnline/getMoreBookDetails?userId=" + encodeURIComponent(userId) + "&bookId=" + encodeURIComponent(bookId); 
}
</script>
<script type="text/javascript">
function getBookUser(userId,bookId){
	console.log("called:"+userId+"bookid"+bookId);
	var url = "http://localhost:8080";
	document.location.href = url + "/BooksOnline/getMoreBookDetails?userId=" + encodeURIComponent(userId) + "&bookId=" + encodeURIComponent(bookId); 
}
</script>

</head>
<body class = "body-grey">

	<%@ include file="navbar.jsp"%>

	<%
		List<BookUser> list = DBFacade.getBookAdList();
		request.setAttribute("list", list);
	%>
	<div class="container">
		<div class="row cool-white">
			<c:forEach items="${list}" var="item">
				<a href="javascript:getBookUser('${item.uid}','${item.bookid}');">
					<div class="col-sm-3">
						<div class="panel panel-primary">
							<div class="panel-body img-hover">
								<img class="img-responsive imgstyle center-block"
									src=<c:out value = "${item.getBook().getThumbnail()}"/>
									alt=<c:out value = "${item.getBook().getThumbnail()}"/>>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-4">
										<h5 class="h5-spec">
											<b>Author</b>
										</h5>
									</div>
									<div class="col-sm-8">
										<h5 class="h5-spec">
											<c:out value="${item.getBook().getAuthors()}" />
										</h5>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<h5 class="h5-spec">
											<b>Title</b>
										</h5>
									</div>
									<div class="col-sm-8">
										<h5 class="h5-spec">
											<c:out value="${item.getBook().getTitle()}" />
										</h5>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<h5 class="h5-spec">
											<b>Offer Price</b>
										</h5>
									</div>
									<div class="col-sm-8">
										<h5 class="h5-spec">
											<c:out value="${item.getPrice()}" />
										</h5>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<h5 class="h5-spec">
											<b>City</b>
										</h5>
									</div>
									<div class="col-sm-8">
										<h5 class="h5-spec">
											<c:out value="${item.getLocality().getCity()}" />
										</h5>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<h5 class="h5-spec">
											<b>Locality</b>
										</h5>
									</div>
									<div class="col-sm-8">
										<h5 class="h5-spec">
											<c:out value="${item.getLocality().getArea()}" />
										</h5>
									</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</c:forEach>
		</div>
	</div>



	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center">
	<p>Online second hand bookstore</p>
	</footer>

</body>
</html>