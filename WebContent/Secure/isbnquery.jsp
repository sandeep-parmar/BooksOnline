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

</head>
<body class="body-grey">

<%@ include file="../navbar.jsp"%>

<% 
	/*if(session.getAttribute("user") == null || session.getAttribute("user") == "")
	{
		response.sendRedirect("login.jsp");
	}*/
%>

<div id='loadgif' style="display: none">
  <!-- <img src='/BooksOnline/BootConfig/ajax-loader.gif'> -->
</div>
	

<div class="container">

<div class="row">
	<div class="col-sm-6 searchContain">
		
		<div class="row">
			<div class="col-sm-2 col-sm-offset-1">
				<label for="sel1">Search By:</label>
			</div>
			<div class="col-sm-3">
				<select class="form-control" id="searchkey">
    				<option>ISBN</option>
    				<option>TITLE</option>
    				<option>AUTHOR</option>    
 				</select>
			</div>
			<div class="col-sm-4">
				<div class="form-group">
  					<input type="text" class="form-control" id="inputfield">
				</div>
			</div>
			<div class="col-lg-2">
				<button type="button" class="btn btn-primary btn-md" id="searchIsbn">Search</button>
			</div>
		</div>
		<div class="row hidden" id="booklistrow">
			<div class="col-sm-2 col-sm-offset-1">
    			<label for="booklist">Book List</label>
    		</div>
    			<div class="col-sm-7">
    				<select class="form-control" id="booklist" name="booklist"></select>
    			</div>
    			<div class="col-sm-2">
    				<button type="button" class="btn btn-primary" id="searchbytitle">Search</button>
    			</div>  					
 		</div>
		<div class="row pad_row">
 			<button type="button" class="btn btn-primary btn-md col-sm-offset-9" id="manualfileloadbutton">Manually Post Ad</button>
 		</div>
	</div>
	
	<div class="col-sm-6">						

		
		<div class="alert alert-success" id="alertbox" style="display: none">
  			<button type="button" class="close" data-hide="alert">&times;</button>
  			<strong id="alertdata">Success!</strong>		
  		</div>
		
		<form method="post" class="hidden" id="bookform" action="/home.jsp">
			<h3 class="text-center">Confirm a book</h3>
			<div class="row pad_row hidden" id="bookImgRow">			
				<img class="img-responsive imgstyle center-block" alt="robin sharma" id="bookImg"></img>			
			</div>
			<div class="row pad_row">
				<label for="title" class="col-sm-4 col-sm-offset-1 control-label">Title:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="title" name="title">
				</div>
			</div>
			<div class="row pad_row">
				<label for="author" class="col-sm-4 col-sm-offset-1 control-label">Authors:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="author" name="author">
				</div>
			</div>
			<div class="row pad_row">
				<label for="description" class="col-sm-4 col-sm-offset-1 control-label">Description:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="description" name="description">
				</div>
			</div>
			<div class="row pad_row">
				<label for="isbn_13" class="col-sm-4  col-sm-offset-1 control-label">Isbn:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="isbn_13" name="isbn_13">
				</div>
			</div>
			
			<input type="hidden" class="form-control" id="thumbnail" name="thumbnail" >
			<input type="hidden" class="form-control" id="isbn_10" name="isbn_10" >
			<input type="hidden" class="form-control" id="category" name="category">
			<input type="hidden" class="form-control" id="publisher" name="publisher">
			<input type="hidden" class="form-control" id="publisheddate" name="publisheddate">	
			
			
			<hr>
			<div class="row pad_row">
				<label class="col-sm-4 col-sm-offset-1  control-label">Name:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="lname" name="lname">
				</div>
			</div>
			<div class="row pad_row">
				<label class="col-sm-4  col-sm-offset-1 control-label">City:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="lcity" name="lcity">
				</div>
			</div>
			<div class="row pad_row">
				<label class="col-sm-4  col-sm-offset-1 control-label">Locality (nearby):</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="llocality" name="llocality">
				</div>
			</div>

			<div class="row pad_row">
				<label class="col-sm-4  col-sm-offset-1 control-label">Offer Price:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="offPrice" name="offPrice">
				</div>
			</div>			
			<div class="row pad_row hidden" id="browseImgRow">
				<div class="col-sm-6 col-sm-offset-5">			
  					<input type="file" name="bootfileinput" id="bootfileinput"/><br/><br/>						
				</div>
			</div>
								
			<div class="row pad_row">				
				<div class="col-sm-6">
					<button type="button" class="btn btn-primary" id="cancelbookdetails">Cancel</button>  			
				</div>
				<div class="col-sm-6">
					<button type="button" class="btn btn-primary" id="submitbookdetails">Submit</button>  			
				</div>
			</div>
		</form>
	</div>   		
</div>	
</div>
		
<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  <p>Online second hand boosstore </p> 
</footer>
</body>
</html>