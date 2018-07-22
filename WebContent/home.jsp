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
$(document).ready(function()
{
    $(".backup_picture").on("error", function(){
        $(this).attr('src', './Images/no_thumb_avail.png');
    });
});
function getBookUser(userId,bookId){
	console.log("called:"+userId+"bookid"+bookId);
	var url = "http://localhost:8080";
	document.location.href = url + "/BooksOnline/getMoreBookDetails?userId=" + encodeURIComponent(userId) + "&bookId=" + encodeURIComponent(bookId); 
}

</script>

</head>
<body class = "body-grey">

	<%@ include file="navbar.jsp"%>	
	<%@ include file="carousel.jsp"%>
	
	<%
		List<BookUser> list = (List) request.getSession().getAttribute("list");
	%>
	<div align="center" class="displayNoofBookText">
		You can browse through ${totalItems} Books Posted.   Displaying 
		 <c:if test="${currentHomePage lt noOfPages}">
		 	${currentHomePage*10} of ${totalItems} .
		 </c:if>
		  <c:if test="${currentHomePage eq noOfPages}">
		 	${totalItems} of ${totalItems} .
		 </c:if>
		
	</div>
	<nav aria-label="Navigation for Books">
	    <ul class="pagination">
	        <c:if test="${currentHomePage != 1}">
	            <li class="page-item"><a class="page-link" 
	                href="HomeNavigation?recordsHomePerPage=${recordsHomePerPage}&currentHomePage=${currentHomePage-1}">Previous</a>
	            </li>
	        </c:if>
	
	        <c:forEach begin="1" end="${noOfPages}" var="i">
	            <c:choose>
	                <c:when test="${currentHomePage eq i}">
	                    <li class="page-item active"><a class="page-link">
	                            ${i} <span class="sr-only">(current)</span></a>
	                    </li>
	                </c:when>
	                <c:otherwise>
	                	<c:if test="${i <= 20 }">
		                   <li class="page-item"><a class="page-link" 
	                        href="HomeNavigation?recordsHomePerPage=${recordsHomePerPage}&currentHomePage=${i}">${i}</a>
							</li>
	                    </c:if>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
	
	        <c:if test="${currentHomePage lt noOfPages}">
	            <li class="page-item"><a class="page-link" 
	                href="HomeNavigation?recordsHomePerPage=${recordsHomePerPage}&currentHomePage=${currentHomePage+1}">Next</a>
	            </li>
	        </c:if>              
	    </ul>
	</nav>
	<input type="hidden" name="currentHomePage" value="${currentHomePage}">
	<input type="hidden" name="recordsHomePerPage" value="${recordsHomePerPage}">
	<div class="container container_margin">
		<div class="row cool-white">
			<c:forEach items="${list}" var="item">
				<a href="javascript:getBookUser('${item.uid}','${item.bookid}');">
					<div class="col-sm-3">
						<div class="panel panel-primary">
							<div class="panel-body img-hover">
								<img class="backup_picture img-responsive imgstyle center-block"
									src="${item.getBook().getThumbnail()}"
									alt="No Image Available" >
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
											<c:out value="${item.getBook().getBookauthor()}" />
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
											<c:out value="${item.getBook().getBooktitle()}" />
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
											<c:out value="${item.getCity()}" />
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
											<c:out value="${item.getArea()}" />
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