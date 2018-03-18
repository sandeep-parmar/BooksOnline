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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Books Online</title>
<%@ include file="BootConfig/bootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="Css/style.css"></link>

<script src="Js/basicB.js"></script>

</head>
<body>
<%@ include file="navbar.jsp"%>
<%
	BookAdBean bd = (BookAdBean) request.getAttribute("bookad");
	request.setAttribute("bd", bd);
	session.setAttribute("usermail", bd.getEmail());
%>
<br><br><br> <!-- To be removed  -->

<div id="container" style="width:100%;">                                   
  <div id="left" style="float:left; width:30%;"> 
  	<img class="" height="300" width="300"
		src=<c:out value = "${bd.getThumbnail()}"/>
		alt=<c:out value = "${bd.getThumbnail()}"/>>
  </div>                     
  <div id="right" style="float:right; width:70%;"> 
  	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Title : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getBooktitle()}" />
			</h5>
		</div>		
	</div>
  	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Author : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getBookauthor()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>ISBN : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getBookid()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Offered Price : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getPrice()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Book Description : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getBookdesc()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Poster's PINCODE : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getPin()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Poster's City : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getCity()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Poster's Area : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:out value="${bd.getArea()}" />
			</h5>
		</div>		
	</div>
	<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Current Staus : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<c:if test ="${bd.getSoldstatus() eq 0}">
					<c:out value="Available" />
				</c:if>
				<c:if test ="${bd.getSoldstatus() ne 0}">
					<c:out value="Sold" />
				</c:if>
			</h5>
		</div>		
	</div>
		<div class="row">
		<div class="col-sm-2">
			<h4>
				<b>Get This Deal : </b>
			</h4>
		</div>
		<div class="col-sm-10">
			<h5>
				<a href='#' onclick='javascript:window.open("getdeal.jsp", "_blank", "scrollbars=1,resizable=1,height=300,width=450");' title='Pop Up'>Enter Mail</a>
			</h5>
		</div>		
	</div>
   </div>                   
</div>
<!-- Footer Not added as we are changing page -->
</body>
</html>