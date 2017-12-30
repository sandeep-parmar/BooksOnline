$(document).ready(function(){
	
	var host = "http://localhost:";
	var port="8080";
	var webservice="/BooksOnline/rest/books";
					
	
	function getSetBookTitles(author)
	{
		//alert(author);
		$.get(host + port + webservice + "/getbooklist/" + author,
				function(data, status){					
				//console.log(data);
			
					//alert(status);
					var options = null;
					var booklist = data["books"];
					
					$('select').children().remove();
					
					for(var i = 0; i<booklist.length ;i++)
					{
						options+="<option value='" + booklist[i] + "'>" + booklist[i] + "</option>";
					}
						
					$('select[name="booklist"]').append(options);
					$('#myModal2').modal('toggle');
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);
				});
	}
	
	function getBookInfoAndSetModal1(searchKey, searchVal)
	{
		$.get(host + port + webservice + "/getbookinfo/" + searchKey + "/" + searchVal,
				function(data, status){					
				//console.log(data);
				var title = data["title"];
									
				var author = data["author"];
				var description = data["description"];
				var thumbnail = data["thumbnail"];
				var isbn = data["isbn"];
									
									 
				$("#title").val(title);
				$("#author").val(author);										 
				$("#description").val(description);
				$("#isbn").val(isbn);
				$("#thumbnail").val(thumbnail);
									 
				$('#bookImg').attr('src',thumbnail);
									 
				$("#title").prop("readonly",true);
				$("#author").prop("readonly",true);
				$("#description").prop("readonly",true);
				$("#isbn").prop("readonly",true);
									 
				$('#myModal1').modal('toggle');
									 
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);
				});
	}
	
	$("#searchIsbn").click(function(){
				
		var searchKey = $("#searchkey").val();
		var searchVal = $("#inputfield").val();
		//alert(searchKey);
		if(!searchVal)
		{			
			alert("input field can not be empty");			
		}
		else if(searchKey === "AUTHOR" || searchKey === "author")
		{
			getSetBookTitles(searchVal);
		}
		else
		{		
			//alert(searchKey+" "+searchVal);
			getBookInfoAndSetModal1(searchKey, searchVal);
		}
	});
	
	$("#searchbytitle").click(function(){
		
		$('#myModal2').modal('toggle');
		var searchVal = $("#booklist").val();
		alert(searchVal);
		if(!searchVal)
		{			
			alert("title can not be empty");			
		}
		else
		{		
			//alert(searchKey+" "+searchVal);
			getBookInfoAndSetModal1("title", searchVal);
		}
	});
	
	$("#submitbookdetails").click(function() {
		
		$("#bookform").attr("action",host + port + webservice + "/savebook").submit();
		
	    $('#myModal1').modal('toggle');
	    $("#comment").html("book saved successfully")
	    return false;
	});
	
	$("#submit").click(function(){
		  var name = $(this).attr("name");
		  var table = $(this).attr("alt");
		  
		  $('body').append($('<form/>')
				  .attr({
					  'action':'prodFeature' , 
					  'method': 'get', 
					  'id': 'replacer'})
				  .append($('<input/>')
				    .attr({
				    	'type': 'hidden', 
				    	'name': 'name', 
				    	'value': name})
				  )
				  .append($('<input/>')
				    .attr({
				    	'type': 'hidden', 
				    	'name': 'table', 
				    	'value': table})
				  )
				).find('#replacer').submit();
	  });
	  
	  $(".side").click(function(){
		  var src = $(this).attr("src");	 
		  $('#super').attr("src",src);
	  });
	  
	  
	  function validateEmail(sEmail) {
	      var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	      if (filter.test(sEmail)) {
	          return true;
	      }
	      else {
	          return false;
	      }
	  }
});