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
<body>

<%@ include file="../navbar.jsp"%>

<% 
	/*if(session.getAttribute("user") == null || session.getAttribute("user") == "")
	{
		response.sendRedirect("login.jsp");
	}*/
%>

<div class="searchContain">

<div class="row">
	<div class="col-sm-1 col-sm-offset-2">
		<label for="sel1">Search By:</label>
	</div>
	<div class="col-sm-2">
		<select class="form-control" id="searchkey">
    		<option>ISBN</option>
    		<option>TITLE</option>
    		<option>AUTHOR</option>    
 		</select>
	</div>
	<div class="col-sm-2">
		<div class="form-group">
  		<input type="text" class="form-control" id="inputfield">
		</div>
	</div>
	<div class="col-lg-2">
		<button type="button" class="btn btn-primary btn-md" id="searchIsbn">Search</button>
	</div>
</div>
	
</div>


<!--model-1-->
<div id="myModal1" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h5 class="modal-title">Confirm a book</h5>
      </div> 
      
      <div class="modal-body">
      
        <form method="post" id="bookform" action="/home.jsp">
        	<img class="img-responsive imgstyle center-block" alt="robin sharma" id="bookImg"></img>
        	
  			<div class="form-group">
    			<label for="title">Title:</label>
    			<input type="text" class="form-control" id="title" name="title">
  			</div>
  			<div class="form-group">
    			<label for="author">Author:</label>
    			<input type="text" class="form-control" id="author" name="author">
  			</div>
  			<div class="form-group">
    			<label for="description">Description:</label>
    			<input type="text" class="form-control" id="description" name="description">
  			</div>  		
  			<div class="form-group">
    			<label for="isbn">Isbn:</label>
    			<input type="text" class="form-control" id="isbn" name="isbn">
  			</div>  
  			<input type="hidden" class="form-control" id="thumbnail" name="thumbnail" >	
  					  		
  			<hr>
  			
  			<!-- validation of below entries pending -->
  			<div class="form-group">
    			<label >Name:</label>
    			<input type="text" class="form-control" id="lname" name="lname">
  			</div>  
  			<div class="form-group">
    			<label >Phone Number:</label>
    			<input type="text" class="form-control" id="lphno" name="lphno">
  			</div>  
  			<div class="form-group">
    			<label >City:</label>
    			<input type="text" class="form-control" id="lcity" name="lcity">
  			</div>  
  			<div class="form-group">
    			<label >Locality (nearby):</label>
    			<input type="text" class="form-control" id="llocality" name="llocality">
  			</div>
  			<div class="form-group">
    			<label >Pin:</label>
    			<input type="text" class="form-control" id="lpin" name="lpin">
  			</div>
  			<hr>  				
  			<div class="form-group">
    			<label >Offer Price:</label>
    			<input type="text" class="form-control" id="offPrice" name="offPrice">
  			</div>  
  			
  			<button type="button" class="btn btn-primary" id="submitbookdetails">Submit</button>  			
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


<!--model-2-->
<div id="myModal2" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Select your book from the list</h4>
      </div> 
      
      <div class="modal-body">        
        	<div class="form-group">
    			<label for="booklist">Book List</label>
    			<select class="form-control" id="booklist" name="booklist"></select>
  			</div>
  			
  			<button type="button" class="btn btn-primary" id="searchbytitle">Search</button>  					
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  <p>Online second hand boosstore </p> 
</footer>

</body>
</html>