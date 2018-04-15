<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.DBFacade"%>
<%@page import="com.bean.*"%>
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
<%@ include file="../BootConfig/bootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="../Css/style.css"></link>

<script src="../Js/basicB.js"></script>

<script>

</script>


</head>
<body>

<%@ include file="../navbar.jsp"%>

<div id = "profilecontainer" style="padding-top: 100px ;padding-bottom: 100px; margin-left: 50px"> 
 <div class="row">
 <div class="col-sm-6">
	 <form>
 	  		<div class="form-group">
 	  			<div class="row"> 	  				 
    				<label for="profname" class = "col-sm-4">Name:</label>
    				<div class="col-sm-8">
    					<div class="input-group">
    						<span class="input-group-addon">
        						<input type="checkbox" class="checked" aria-label="..." id="namecheckbox" name="namecheckbox" value="profname">
      						</span>
    			 			<input type="text" class="form-control" disabled="true" id="profname" name="profname" value="<shiro:principal property="username"/>">
    			 		</div> 
    				</div>
    			</div>    			
  			</div>  	
  			<div class="form-group">
 	  			<div class="row">
    				<label for="profmobile" class = "col-sm-4">Mobile:</label>
    				<div class="col-sm-8">
    					<div class="input-group">
    						<span class="input-group-addon">
        						<input type="checkbox" class="checked" aria-label="..." id="mobilecheckbox" name="mobilecheckbox" value="profmobile">
      						</span>
    			 			<input type="text" class="form-control" disabled="true" id="profmobile" name="profmobile" value="<shiro:principal property="mobile"/>">
    			 		</div> 
    				</div>
    			</div>    			
  			</div>
  			<div class="form-group">
 	  			<div class="row">
    				<label for="profemail" class = "col-sm-4">Email: </span></label>
    				<div class="col-sm-8">
    				<div class="input-group">
    					<span class="input-group-addon">
        					<input type="checkbox" class="checked" aria-label="..." id="emailcheckbox" name="emailcheckbox" value="profemail">
      					</span>
    			 		<input type="text" class="form-control" disabled="true" id="profemail" name="profemail" value="<shiro:principal property="email"/>">
    			 		</div> 
    				</div>
    			</div>    			
  			</div>  	  					  	 	  
  			<div class="form-group">
 	  			<div class="row">    				
    				<div class="col-sm-4">
    			 		<a href = "/BooksOnline/resetpassword.jsp?username=<shiro:principal property="username"/>&uuid=<shiro:principal property="uuid"/>"><button type="button" class="btn btn-primary btn-md resetpassword"><b>Reset Password</b></button></a>
    			 	</div>
    			 	<div class="col-sm-3">
    			 		<button type="button" class="btn btn-primary btn-md resetpassword" id="profileupdateformsubmitbutton" name="profileupdateformsubmitbutton"><b>Update Profile</b></button> 
    				</div>
    			</div>    			
  			</div>
  			<div id="profilecomment"></div>
  			  	  					  	 	    			   
 	</form>	
 </div>
 </div>
  
</div>
	<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  <p>Online second hand bookstore </p> 
</footer>
	
</body>
</html>