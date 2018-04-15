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
	
	<div class="container loginsignup" id="forgetpassword">
		<form method="post" id="signup" action="">
			<h4> Forget Password </h4>
  			<div class="form-group">
    			<label for="enteremail">Enter Email:</label>
    			 <input type="text" class="form-control" id="fpemail" name="fpemail"> 
    			<!--<input type="text" class="form-control" id="username" name="username">-->
  			</div>  			

  			<button type="button" class="btn btn-primary btn-lg buttonclass" id="forgetpasswordsubmitbutton">Submit</button>
  			<!--<button type="submit" class="btn btn-primary  btn-lg" >Login</button>-->
  			<div id="fpcomment">
  			</div>
		</form>
	</div>
	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center">
  	<p>Online second hand bookstore </p> 
	</footer>
</body>
</html>