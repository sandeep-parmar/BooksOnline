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
<script type="text/javascript">
function openPopup(url, title, w, h) {
    
    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    if (window.focus) {
        newWindow.focus();
    }
}
</script>
</head>
<body>
<%@ include file="navbar.jsp"%>
<%
	BookAdBean bd = (BookAdBean) request.getAttribute("bookad");
	request.setAttribute("bd", bd);
	session.setAttribute("usermail", bd.getEmail());
%>
<br><br><br> <!-- To be removed  -->

<div id="container" class="width100per">                                   
  <div id="left" class="floatLeft width70per"> 
  	<img class="" height="300" width="300"
		src=<c:out value = "${bd.getThumbnail()}"/>
		alt=<c:out value = "${bd.getThumbnail()}"/>>
  </div>                     
  <div id="right" class="floatRight width70per"> 
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
				<a href='#' onclick='javascript:openPopup("getdeal.jsp","Enter Your Communication Detais","600","400");' title='Pop Up'>Enter Mail</a>
			</h5>
		</div>		
	</div>
   </div>                   
</div>
<!-- Footer Not added as we are changing page -->
</body>
</html>