<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Search For Book</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
</head>
<body class="m-3">
	<label for="records">Found ${totalItems} Results. Showing ${currentPage * 10} results of ${totalItems}</label>
	<nav aria-label="Navigation for Searched Books">
	    <ul class="pagination">
	        <c:if test="${currentPage != 1}">
	            <li class="page-item"><a class="page-link" 
	                href="GetBooksFromSearch?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&bookSearchkey=${bookSearchkey}&bookSearchVal=${bookSearchVal}">Previous</a>
	            </li>
	        </c:if>
	
	        <c:forEach begin="1" end="${noOfPages}" var="i">
	            <c:choose>
	                <c:when test="${currentPage eq i}">
	                    <li class="page-item active"><a class="page-link">
	                            ${i} <span class="sr-only">(current)</span></a>
	                    </li>
	                </c:when>
	                <c:otherwise>
	                	<c:if test="${i <= 20 }">
		                   <li class="page-item"><a class="page-link" 
	                        href="GetBooksFromSearch?recordsPerPage=${recordsPerPage}&currentPage=${i}&bookSearchkey=${bookSearchkey}&bookSearchVal=${bookSearchVal}">${i}</a>
							</li>
	                    </c:if>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
	
	        <c:if test="${currentPage lt noOfPages}">
	            <li class="page-item"><a class="page-link" 
	                href="GetBooksFromSearch?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&bookSearchkey=${bookSearchkey}&bookSearchVal=${bookSearchVal}">Next</a>
	            </li>
	        </c:if>              
	    </ul>
	</nav>
	<div class="row col-md-10">
	    <table class="table table-striped table-bordered table-sm">
	        <tr>
	        	<th>Select</th>
	            <th>Title</th>
	            <th>Author</th>
	            <th>Publishers</th>
	            <th>Book Description</th>
	        </tr>
	
	        <c:forEach items="${books}" var="book">
	            <tr>
	            	<td><input type="radio" name="selectedBookFromSearch" id="selectedBookFromSearch"></td>
	                <td>${book.getBooktitle()}</td>
	                <td>${book.getBookauthor()}</td>  
	                <td>${book.getPublisher()}</td>  
	                <td title="${book.getBookdesc()}">${book.getBookShortCustdesc()}</td>  
	            </tr>
	        </c:forEach>
	    </table>
	    <a class="page-link" href="/BooksOnline/Secure/isbnquery.jsp" >Next Step</a>
	</div>
</body>
</html>