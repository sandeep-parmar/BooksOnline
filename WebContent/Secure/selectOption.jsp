<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Search For Book</title>
<style type="text/css">
input[type=text], select {
    padding: 6px 5px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 2px;
    box-sizing: border-box;
}

input[type=submit],button {
    padding: 6px 5px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    color: #fff;
    background-color: #337ab7;
    border-color: #2e6da4;
}

input[type=submit]:hover {
    background-color: #45a049;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	var selInd = 0;
	var bookSearchkey = '${bookSearchkey}';
	if(bookSearchkey == 'intitle'){
		selInd = 0;
	}else if(bookSearchkey == 'inauthor'){
		selInd = 2;
	}else{
		selInd = 1;
	}
	document.getElementById("bookSearchkey").selectedIndex = selInd;
});
</script>
</head>
<body class="body-grey">	

<div class="container">
<div class="row">
	<div class="searchContain">
		<form name="searchForBooks" method="post" action="/BooksOnline/GetBooksFromSearch">
			<div class="">
				<label for="sel1">Search By:</label>
				<select  id="bookSearchkey" name="bookSearchkey">
					<option value="intitle">TITLE</option>
    				<option value="isbn">ISBN</option>
    				<option value="inauthor">AUTHOR</option>    
 				</select>
  				<input type="text" id="bookSearchVal" name="bookSearchVal" value="${bookSearchVal}">
  				<button type="submit" id="searchForBooksBtn">Search</button>
			</div>
		</form>
	</div>
</div>
</div>
</body>
</html>