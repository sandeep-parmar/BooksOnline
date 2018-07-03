<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Books Search Results</title>
<title>Books Online</title>
<%@ include file="../BootConfig/bootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="../Css/style.css"></link>

<script src="../Js/basicB.js"></script>
<%@ include file="../BootConfig/bootstrap.jsp"%>
<style type="text/css">
table, th, td {
    border: 3px solid black;
    border-collapse: collapse;
}
td {
    text-align: left;
}
</style>
<script type="text/javascript">
var host = "http://localhost:";
var port="8080";
var webservice="/BooksOnline/rest";
var loginservice="/login";
var bookservice="/books";
var bookAdservice = "/bookAd";
var STATUS = "status";
var ERRMSG = "errmsg";
//generalise fun to acess url params
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
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

function setReadOnlyTrue()
{
	$("#title").prop("readonly",true);
	$("#author").prop("readonly",true);
	$("#description").prop("readonly",true);
	$("#isbn").prop("readonly",true);
}

function replaceStrData(str){
	str = str.trim();
	var eqInd = str.indexOf("=");
	return str.substring(eqInd+1, str.length-1);
}

function getBookInfoAndSetModal1(searchKey, searchVal,page)
{				
	$.get(host + port + webservice + bookservice + "/getbookinfo/" + searchKey + "/" + searchVal+ "/" + page,
			function(data, status){				
			
			var status= data[STATUS];
    		var errmsg = data[ERRMSG];
    		var books = [];
    		if(status == 0)
    		{
    			var totalItems = data["totalItems"];
    			var totalBooksReturned = data["bookcount"];
    			var searchBookKey = searchKey;
    			var searchBookVal = searchVal;
    			$("#totalItems").val(totalItems);
    			$("#searchBookKey").val(searchBookKey);
    			$("#searchBookVal").val(searchBookVal);
    			
    			
    			for(var i=1; i <= totalBooksReturned;i++){
    				addAnchor(i);
    			}
				for(var i=0; i<totalBooksReturned;i++){
    				var book0 = data["book"+i];
    				
// 	    			console.log("book0"+book0);
	    			if(book0!=null){
	    				var bookTit_ind = book0.indexOf("booktitle=");
	    				var bookauthor_ind = book0.indexOf("bookauthor=");
	    				var bookdesc_ind = book0.indexOf("bookdesc=");
	    				var bookid_ind = book0.indexOf("bookid=");
	    				var thumbnail_ind = book0.indexOf("thumbnail=");
	    				var category_ind = book0.indexOf("category=");
	    				var isbn_10_ind = book0.indexOf("isbn_10=");
	    				var publishe_ind = book0.indexOf("publisher=");
	    				var published_date_ind = book0.indexOf("published_date=");
	    				
	    				var title = replaceStrData(book0.substring(bookTit_ind, bookauthor_ind));									
	    	   			var author = replaceStrData(book0.substring(bookauthor_ind, bookdesc_ind));
	    	   			var description = replaceStrData(book0.substring(bookdesc_ind, bookid_ind));
	    	   			var thumbnail = replaceStrData(book0.substring(thumbnail_ind, category_ind));
	    	   			var isbn_13 = replaceStrData(book0.substring(bookid_ind, thumbnail_ind));
	    	   			var isbn_10 = replaceStrData(book0.substring(isbn_10_ind, publishe_ind));
	    	   			var publisher = replaceStrData(book0.substring(publishe_ind, published_date_ind));
	    	   			var publisheddate = replaceStrData(book0.substring(published_date_ind, book0.length));
	    	   			var category = replaceStrData(book0.substring(category_ind, isbn_10_ind))
	    				
	    	   			var Row = document.getElementById('tr'+i);
	        			var Cells = Row.getElementsByTagName('td');
	        			Cells[1].innerHTML = title;
	        			Cells[2].innerHTML = description.substring(0,70);
	        			Cells[3].innerHTML = author;
	        			Cells[4].innerHTML = publisher;
    				
    				}
				}
    			
    			
    			
    			
    			
	   			
//    			//alert(publisheddate);
//    			var category = data["categories"];														
//
//    			if(status == 0)
//    			{										
//    				$("#bookform").removeClass('hidden');				
//    			}
//    			else if(status == 13)
//    			{								
//    				$("#bookform").addClass('hidden');
//    				resetBookForm();
//    			}							
//
//    			$("#title").val(title);
//    			$("#author").val(author);										 
//    			$("#description").val(description);
//    			$("#isbn_13").val(isbn_13);	
//
//    			$('#bookImg').attr('src',thumbnail);
//
//    			/*hidden fields*/
//    			$("#thumbnail").val(thumbnail);
//    			$("#isbn_10").val(isbn_10);
//    			$("#publisher").val(publisher);
//    			$("#publisheddate").val(publisheddate);
//    			$("#category").val(category);

    			document.getElementById('totalItemsLbl').innerHTML = totalItems;
    			document.getElementById('totalBooksReturnedLbl').innerHTML = totalBooksReturned;
    		}
    		else
    		{
    			showWarning(errmsg);
    		}
			
			setReadOnlyTrue();				 
								 
			})
			.fail(function(res,status,error) {
				//console.log(res.responseText);
				//alert( res.responseText+" "+status+" "+error);
				showDanger("Something went wrong, we are looking into it");
			});		
}

$(document).ready(function(){
var bookSearchKey = getUrlVars()["bookSearchKey"];
var bookSearchVal = getUrlVars()["bookSearchVal"];
var page = getUrlVars()["page"];
document.getElementById('bookSearchKeyLbl').innerHTML = bookSearchKey;
document.getElementById('bookSearchValLbl').innerHTML = bookSearchVal;
$("#bookSearchKeyLbl").val(bookSearchKey);
$("#bookSearchValLbl").val(bookSearchVal);
// console.log("bookSearchKey"+bookSearchKey);
getBookInfoAndSetModal1(bookSearchKey, bookSearchVal,page);
});

function hideRows(trId) {
    var trid = document.getElementById(trId);
    if (trid != null) {
        trid.style.display = 'none';
    }
}
function showRows(trId) {
    var trid = document.getElementById(trId);
    if (trid != null) {
        trid.style.display = '';
    }
}
function appendData(e){
    e.href = e.href + "&bookSearchKey=" + document.getElementById('bookSearchKeyLbl').value;
    e.href = e.href + "&bookSearchVal=" + document.getElementById('bookSearchValLbl').value;
}

function addAnchor(i){
	var mydiv = document.getElementById("pageLinks");
	var aTag = document.createElement('a');
	aTag.setAttribute('href',"bookListView.jsp?page="+i);
	aTag.setAttribute('onClick',"appendData(this)");
	aTag.appendChild(document.createTextNode(i));
	mydiv.appendChild(aTag);
}

</script>
</head>
<body class="body-grey">
<%@ include file="../navbar.jsp"%>
<div id='loadgif' style="display: none">
  <!-- <img src='/BooksOnline/BootConfig/ajax-loader.gif'> -->
</div>
<div class="container">
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<label>Your Search for Books Using : </label>
			<label id="bookSearchKeyLbl"></label>
			<label>For Value : </label>
			<label style="color: blue" id="bookSearchValLbl"></label>
			<label>Returned  </label>
			<label id="totalItemsLbl"></label>
			<label> Results.</label>
			<label id="totalBooksReturnedLbl"></label>
			<label> Books.</label>
		</div>
	</div>
	<table style="width:100%" id="bookDislpay"> 
		<tr>
			<th>Select</th>
			<th>Title</th>
			<th>Description</th>
			<th>Authors</th>
			<th>Publishers</th>
		</tr>
		<tr id="tr0">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr id="tr1">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr id="tr2">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr id="tr3">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr id="tr4">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr id="tr5">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr id="tr6">
			<td><input type="radio" id="selisbn"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	
	<div id="pageLinks"></div>
</div>	

<% 

%>


<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  <p>Online second hand Book Store </p> 
</footer>

</body>
</html>