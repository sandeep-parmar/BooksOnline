<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	
	<div class="container loginsignup" id="signupcontainer">
		<form method="post" id="signupform">
			<h4> Create Account </h4>
  			<div class="form-group">
    			<label for="Name">Your Name:</label>
    			<input type="text" class="form-control" id="name">
  			</div>
  			<div class="form-group">
    			<label for="mobile">Mobile number:</label>
    			<input type="text" class="form-control" id="mobile">
  			</div>
  			<div class="form-group">
    			<label for="email">Email address:</label>
    			<input type="text" class="form-control" id="email">
  			</div>
  			<div class="form-group">
    			<label for="pwd">password:</label>
    			<input type="password" class="form-control" id="password">
  			</div>
  			<div class="form-group" id="signupcomment">
  				
  			</div>
  			  			
  			<button type="button" class="btn btn-primary" id="signupformsubmitbutton" name="signupformsubmitbutton">Submit</button>
		</form>
	
	</div>
	
	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center">
  	<p>Online second hand bookstore </p> 
	</footer>
	
</body>
</html>