<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Books Online</title>
<%@ include file="../BootConfig/bootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="/BooksOnline/Css/style.css"></link>
<script src="/BooksOnline/Js/basicB.js"></script>
<script type="text/javascript">
var host = "http://localhost:";
var port="8080";
var webservice="/BooksOnline/rest";
var loginservice="/login";
var bookservice="/books";
var bookAdservice = "/bookAd";
var STATUS = "status";
var ERRMSG = "errmsg";
$(document).ready(function(){
	makeSubmenu();
});
function validateBookForm()
{
	var title = $("#title").val();
	var author = $("#author").val();										 
	var desc = $("#description").val();
	var isbn = $("#isbn_13").val();		
	var lname = $("#lname").val();
	var lcity = $("#lcity").val();
	var llocality = $("#llocality").val();
	var offprice = $("#offPrice").val();
	if(!lname || !lcity || !llocality || !offprice)
	{
		showWarning("Form fields can not be empty");
		return 0;
	}
	return 1;	
}

function saveSearchBookDetails(){
	console.log("called saveSearchBookDetails");
	//saving book
	if(validateBookForm()){
		editBookSearchFun();
		submitNormalForm();
		$("#saveSearchBook").addClass("hidden");
	}	
}
function showSuccess(msg)
{		  
	  $("#alertbox").removeClass("alert-danger");
	  $("#alertbox").removeClass("alert-warning");
	  $("#alertbox").addClass("alert-success");
	  $("#alertdata").html(msg);
	  $("#alertbox").show();
}
function showWarning(msg)
{
	  $("#alertbox").removeClass("alert-danger");
	  $("#alertbox").removeClass("alert-success");
	  $("#alertbox").addClass("alert-warning");
	  $("#alertdata").html(msg);
	  $("#alertbox").show();
}
function showDanger(msg)
{
	  $("#alertbox").removeClass("alert-warning");
	  $("#alertbox").removeClass("alert-success");
	  $("#alertbox").addClass("alert-danger");
	  $("#alertdata").html(msg);
	  $("#alertbox").show();
}
/*Normal form*/
function submitNormalForm()
{
	
	$.ajax({
        type: "POST",       
        contentType: "application/x-www-form-urlencoded",
        url: host + port + webservice + bookservice + "/savebook",
        data: $("#saveSearchBook").serialize(),
        dataType: 'json',
        success: function (data) {              	
        	//$("#saveSearchBook").addClass("hidden");
        	showSuccess(data[ERRMSG]);        
        	window.location.href = "/BooksOnline/home.jsp";
        },
        error: function (res,status,error) {
           showDanger(res.responseText);
        }
    });
}

function resetBookForm()
{
	$("#title").val("");
	$("#author").val("");										 
	$("#description").val("");
	$("#isbn").val("");
	$("#thumbnail").val("");
	$("#lname").val("");
	$("#lcity").val("");
	$("#llocality").val("");
	$("#offprice").val("");			
	$('#bookImg').attr('src',"");
}

function cancelSearchbookdet(){
	resetBookForm();
	$("#saveSearchBook").addClass('hidden');	
	window.location.href = "/BooksOnline/Secure/isbnquery.jsp";
}

function editBookSearchFun(){
	document.getElementById("title").removeAttribute('disabled');
	document.getElementById("author").removeAttribute('disabled');
	document.getElementById("description").removeAttribute('disabled');
	document.getElementById("isbn_13").removeAttribute('disabled');
}

var citiesByState = {
// 		Odisha: ["Bhubaneswar","Puri","Cuttack"],
		Maharashtra: ["Pune","Mumbai","Nagpur"]
	}
function makeSubmenu() {
	value = 'Maharashtra';
	if(value.length==0) 
		document.getElementById("lcity").innerHTML = "<option></option>";
	else {
		var citiesOptions = "";
		for(cityId in citiesByState[value]) {
			citiesOptions+="<option>"+citiesByState[value][cityId]+"</option>";
			}
		document.getElementById("lcity").innerHTML = citiesOptions;
	}
}
function displaySelected() { 
// 	var country = document.getElementById("countrySelect").value;
	var city = document.getElementById("lcity").value;
// 	alert(country+"\n"+city);
}
function resetSelection() {
// 	document.getElementById("countrySelect").selectedIndex = 0;
	document.getElementById("lcity").selectedIndex = 0;
}
</script>
</head>
<body class="body-grey">

<%@ include file="../navbar.jsp"%>

<div class="container">
	<img class="bookSearchRight" id="bookImg" src="${selThumb}" alt="No Preview Available"></img>
	<div class="col-sm-6">						
		<div class="alert alert-success" id="alertbox" style="display: none">
			<button type="button" class="close" data-hide="alert">&times;</button>
			<strong id="alertdata">Success!</strong>		
		</div>
	<form method="post" id=saveSearchBook action="/home.jsp">
		<h3 class="text-center">Confirm a book</h3>
		<div class="row pad_row">
			<label for="title" class="col-sm-4 col-sm-offset-1 control-label">Title:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="title" name="title" value="${seltitle}" disabled="disabled">
			</div>
		</div>
		<div class="row pad_row">
			<label for="author" class="col-sm-4 col-sm-offset-1 control-label">Authors:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="author" name="author" value="${selAuth }" disabled="disabled">
			</div>
		</div>
		<div class="row pad_row">
			<label for="description" class="col-sm-4 col-sm-offset-1 control-label">Description:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="description" name="description" value="${selDesc }" disabled="disabled">
			</div>
		</div>
		<div class="row pad_row">
			<label for="isbn_13" class="col-sm-4  col-sm-offset-1 control-label">Isbn:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="isbn_13" name="isbn_13" value="${selIsbn }" disabled="disabled">
			</div>
		</div>
		
		<input type="hidden" class="form-control" id="thumbnail" name="thumbnail" value="${selThumb}">
		<input type="hidden" class="form-control" id="isbn_10" name="isbn_10" value="${selisbn10 }">
		<input type="hidden" class="form-control" id="category" name="category" value="${selCat }">
		<input type="hidden" class="form-control" id="publisher" name="publisher" value="${selPubl }">
		<input type="hidden" class="form-control" id="publisheddate" name="publisheddate" value="${selPubd }">	
		
		
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
				<select class="form-control" id="lcity" name="lcity">
 				</select>
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
		<div class="row pad_row">				
			<div class="col-sm-4">
				<button type="button" class="btn btn-primary" name="cancelSearchbookdetails" id="cancelSearchbookdetails" onclick="cancelSearchbookdet();">Cancel</button>  			
			</div>
			<div class="col-sm-4">
				<button type="button" class="btn btn-primary" name="submitSearchbookdetails" id="submitSearchbookdetails" onclick="saveSearchBookDetails();">Submit</button>  			
			</div>
			<div class="col-sm-4">
				<button type="button" class="btn btn-primary" name="editBookSearch" id="editBookSearch" onclick="editBookSearchFun();">Edit Details</button>  			
			</div>
		</div>
	</form>
</div>	
</div>
		
<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  <p>Online second hand bookstore </p> 
</footer>
</body>
</html>