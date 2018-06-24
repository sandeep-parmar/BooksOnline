<%@page import="com.dao.DBFacade"%>
<%@page import="com.bean.BookUser"%>
<%@page import="entities.BookAdBean"%>
<%@page import="com.dao.*"%>
<%@page import="com.bean.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	out.println(session.getAttribute("usermail"));
	BookAdBean bd = (BookAdBean)(session.getAttribute("bookAdObj"));
	out.println(bd.getBooktitle());
	String title = (String)bd.getBooktitle();
%>
<div id="container" class="width100per">  
	<form action = "sendBookAdOverEmail" method="post">
	<input type="hidden" value="<%=bd.getBookid() %>" name="bookid">
	<input type="hidden" value="<%=bd.getBooktitle() %>" name="booktitle">
	<input type="hidden" value="<%=bd.getBookauthor() %>" name="bookauthor">
	<input type="hidden" value="<%=bd.getBookdesc() %>" name="bookdesc">
	<input type="hidden" value="<%=bd.getThumbnail() %>" name="thumbnail">
	<input type="hidden" value="<%=bd.getName() %>" name="name">
	<input type="hidden" value="<%=bd.getPrice() %>" name="price">
	<input type="hidden" value="<%=bd.getSoldstatus() %>" name="soldstatus">
	<input type="hidden" value="<%=bd.getEmail() %>" name="email">	
	<input type="hidden" value="<%=bd.getCity() %>" name="city">
	<input type="hidden" value="<%=bd.getArea() %>" name="area">
		<div class="row">
			<div class="col-sm-2">
				<h4>
					<b>Email : </b>
				</h4>
			</div>
			<div class="col-sm-10">
				<h5>
					<input type="text" value="" id="buyerEmail" name ="buyerEmail">
				</h5>
			</div>		
		</div>
		<div class="row">
			<div class="col-sm-2">
				<h4></h4>
			</div>
			<div class="col-sm-10">
				<h5>
					<input type="submit" value="Get Data" id="sendBookAdData">
				</h5>
			</div>		
		</div>
	</form>
</div>
</body>
</html>