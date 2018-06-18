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
<body class = "body-grey">

	<%@ include file="navbar.jsp"%>
	
	<div id='loadgif' style="display: none">
  		<!-- <img src='/BooksOnline/BootConfig/ajax-loader.gif'> -->
	</div>
	<div class="container loginsignup container_margin" id="logincontainer">
		<form method="post" id="signup" action="">
			<h4> Login </h4>
  			<div class="form-group">
    			<label for="enteremail">Enter Email/Mobile number:</label>
    			 <input type="text" class="form-control" id="loginid" name="loginid"> 
    			<!--<input type="text" class="form-control" id="username" name="username">-->
  			</div>  			
  			<div class="form-group">
    			<label for="pwd">Enter Password:</label>
    			<input type="password" class="form-control" id="loginpassword" name="loginpassword">
    			<!--<input type="password" class="form-control" id="password" name="password">-->
  			</div>
  			    			 
  			<button type="button" class="btn btn-primary btn-lg buttonclass" id="loginformsubmitbutton">Login</button>
  			<a href = "forgetpassword.jsp"> Forget Password </a>
  			<!--<button type="submit" class="btn btn-primary  btn-lg" >Login</button>-->
  			<div id="logincomment">
  			</div>
		</form>
		<div class="alert alert-success" id="alertbox" style="display: none">
  			<button type="button" class="close" data-hide="alert">&times;</button>
  			<strong id="alertdata">Success!</strong>		
  		</div>
	</div>
	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center">
  	<p>Online second hand bookstore </p> 
	</footer>
</body>
</html>