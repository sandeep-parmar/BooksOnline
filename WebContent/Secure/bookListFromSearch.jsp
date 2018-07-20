<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.bean.Book" %>
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

<script type="text/javascript">
var selIsbn = "";
$(document).ready(function(){
	document.getElementById('selectBookErrorMsg').style.visibility = "hidden";
});
function saveSelLinkClick(){
	for (i = 0; i < document.getElementById('bookListSize').value; i++) {
	   if($('input:radio[name=selectedBookFromSearch]')[i].checked){
		   selIsbn = $('input:radio[name=selectedBookFromSearch]:checked').val();
		   if(null != selIsbn || selIsbn != '' || selIsbn != ""){
			  document.forms["selectSearchBookData"].submit();
		   }else{
			   document.getElementById('selectBookErrorMsg').style.visibility = "";
		   }
		   break;
	   }
	}
	if(null == selIsbn || selIsbn == '' || selIsbn == ""){
		document.getElementById('selectBookErrorMsg').style.visibility = "";
	}
}

function settingBookData(){
	document.getElementById('selectBookErrorMsg').style.visibility = "hidden";

}

</script>
</head>
<body class="m-3">
	<jsp:include page="selectOption.jsp"></jsp:include>
	<label for="selectBookErrorMsg" id="selectBookErrorMsg" name="selectBook" style="color: red">Please Select Book to Proceed</label>
	<c:choose>
		<c:when test="${books.size() > 0}">
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
		<a class="page-link" href="#" onclick="saveSelLinkClick();" id="saveSelLink">Proceed Next</a>
		<form name="selectSearchBookData" id="selectSearchBookData" method="post" action="/BooksOnline/GetSingleBookDetailsAfterSearch">
		<input type="hidden" name="bookListSize" id="bookListSize" value="${books.size()}">
		
		<%
			session.setAttribute("books", request.getAttribute("books"));
			List<Book> bookList = (ArrayList)request.getAttribute("books");
		%>
		
			    <table class="table table-striped table-bordered table-sm" id="myTable">
			        <tr>
			        	<th>Select</th>
			            <th>Title</th>
			            <th>Author</th>
			            <th>Publishers</th>
			            <th>Category</th>
			            <th>Book Description</th>
			        </tr>
			
			        <c:forEach items="${books}" var="book" varStatus="loop">
			            <tr>
			            	<td><input type="radio" name="selectedBookFromSearch" id="selectedBookFromSearch" value='<c:out value="${book.getBookid()}" />' onclick="settingBookData();"></td>
			                <td>
				                <c:choose>
				                	<c:when test="${book.getBooktitle() != ''}">
				                		${book.getBooktitle()}
				                	</c:when>
				                	<c:otherwise>
				                		<span style="font-style: italic;font-weight: bold;">Information not found</span>
				                	</c:otherwise>
				                </c:choose>
			                </td>
			                <td>
				                 <c:choose>
				                	<c:when test="${book.getBookauthor() != ''}">
				                		${book.getBookauthor()}
				                	</c:when>
				                	<c:otherwise>
				                		<span style="font-style: italic;font-weight: bold;">Information not found</span>
				                	</c:otherwise>
				                </c:choose>
			                </td>  
			                <td>
			                	<c:choose>
			                		<c:when test="${book.getPublisher() != ''}">
			                			${book.getPublisher()}
			                		</c:when>
			                		<c:otherwise>
			                			<span style="font-style: italic;font-weight: bold;">Information not found</span>
			                		</c:otherwise>
			                	</c:choose>
			                </td>
			                <td>
			                	<c:choose>
			                		<c:when test="${book.getCategory() != ''}">
			                			${book.getCategory()}
			                		</c:when>
			                		<c:otherwise>
			                			<span style="font-style: italic;font-weight: bold;">Information not found</span>
			                		</c:otherwise>
			                	</c:choose>
			                </td>
			                <td title="${book.getBookdesc()}">
			                	<c:choose>
			                		<c:when test="${book.getBookShortCustdesc() != ''}">
			                			${book.getBookShortCustdesc()}
			                		</c:when>
			                		<c:otherwise>
			                			<span style="font-style: italic;font-weight: bold;">Information not found</span>
			                		</c:otherwise>
			                	</c:choose>
			                </td>  
			            </tr>
			        </c:forEach>
			    </table>
		    </form>
		    <a class="page-link" href="#" onclick="saveSelLinkClick();" id="saveSelLink">Proceed Next</a>
		</div>
		</c:when>
		<c:otherwise>
			<div align="center" style="font-size: 25px; font-style: oblique;">
				<label for="selectBookNoResults" id="selectBookNoResults" name="selectBookNoResults" style="color: blue">No Results Found. Please Search Again !</label>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>