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
	<%
		String name = request.getParameter("username");
		String uuid = request.getParameter("uuid");
		
		System.out.println("username:"+name+", uuid:"+uuid);
		
		request.setAttribute("name", name);
		request.setAttribute("uuid", uuid);
	%>
	
	<div class="container loginsignup" id="resetpasswordcontainer">
		<form method="post" id="signupform">
			<h4> Reset Password </h4>
			<h3>Hello <c:out value = "${name}"/></h3>
			 <h4>Please enter new password</h4>
			<div class="form-group">    			
    			<input type="text" class="form-control hidden" id="resuuid" name ="resuuid" value=<c:out value = "${uuid}"/>>
  			</div>  		
  			<div class="form-group">
    			<label for="pwd">New password:</label>
    			<input type="password" class="form-control" id="respassword">
  			</div>
  			<div class="form-group">
    			<label for="pwd">Reenter password:</label>
    			<input type="password" class="form-control" id="respassword2">
  			</div>
  			<div class="form-group" id="rescomment">  			
  			</div>
  			  			
  			<button type="button" class="btn btn-primary buttonclass" id="resetpasswordsubmitbutton" name="resetpasswordsubmitbutton">Submit</button>
		</form>
	
	</div>
</body>
</html>