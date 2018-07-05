<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm a book</title>
</head>
<body>
<%

%>
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
</body>
</html>