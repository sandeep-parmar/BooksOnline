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

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Books Online</title>
<%@ include file="../BootConfig/bootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="../Css/style.css"></link>

<script src="../Js/basicB.js"></script>

<script>

</script>


</head>
<body>

<%@ include file="../navbar.jsp"%>

<%
	List<BookUser> list = DBFacade.getMyBooks();
	request.setAttribute("list", list);
%>

<div class="container"> 

  <c:set var="count" value="0" scope="page" />
  <c:forEach items="${list}" var = "item">
  
  <c:set var="count" value="${count + 1}" scope="page"/>
  
  	<div class="row">
  	<div class="col-sm-4">
  		<img 
    		class = "img-responsive myimgstyle center-block" 
    		src = <c:out value = "${item.getBook().getThumbnail()}"/> 
    		alt = <c:out value = "${item.getBook().getTitle()}"/>
    	> 
  	</div>	
    <div class = "col-sm-6">
    	<div class="row">
    				<div class="col-sm-4"><h5><b>Author</b></h5></div>
					<div class="col-sm-8"><h5><c:out value = "${item.getBook().getAuthors()}"/></h5></div>
		</div>
		<div class="row">
					<div class="col-sm-4"><h5><b>Title</b></h5></div>
					<div class="col-sm-8"><h5><c:out value = "${item.getBook().getTitle()}"/></h5></div>
		</div>
		<div class="row">
					<div class="col-sm-4"><h5><b>Offer Price</b></h5></div>
					<div class="col-sm-8">
						<c:set var="updatedprice" value="updatedprice_${count}"/>
						<input type="text" class="form-control" id="<c:out value ="${updatedprice}"/>" name="<c:out value ="${updatedprice}"/>" value="<c:out value ="${item.getPrice()}"/>">
					</div>
		</div>
		<div class="row">
					<div class="col-sm-4"><h5><b>bookid</b></h5></div>
					<div class="col-sm-8"><h5><c:out value = "${item.getBookid()}"/></h5></div>
		</div>
		
		<c:set var="soldthisbook" value="soldthisbook_${count}"/>
		<input type="text" class="form-control hidden" id="<c:out value ="${soldthisbook}"/>" name="<c:out value ="${soldthisbook}"/>" value="<c:out value ="${item.getBookid()}"/>">
	
		 <c:choose>
  			<c:when test="${item.getSoldstatus() == 1}">
    			<button type="button" class="btn btn-primary btn-md soldbutton" disabled="true"><b>SOLD</b></button>
    			<button type="button" class="btn btn-primary btn-md updatepricebutton" disabled="true"><b>UPDATE</b></button>
  			</c:when>
  			<c:otherwise>
    			<button type="button" class="btn btn-primary btn-md soldbutton"  value = "<c:out value="${count}"/>"><b>SOLD</b></button>
    			<button type="button" class="btn btn-primary btn-md updatepricebutton" value = "<c:out value="${count}"/>"><b>UPDATE</b></button>
  			</c:otherwise>
		</c:choose>
    </div>
    </div>
    <hr>
  	</c:forEach> 	
	</div>

	<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  <p>Online second hand bookstore </p> 
</footer>
	
</body>
</html>