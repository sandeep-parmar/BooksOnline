$(document).ready(function(){
	
	var host = "http://localhost:";
	var port="8080";
	var webservice="/BooksOnline/rest/books";
				
		
	$("#searchIsbn").click(function(){
				
		var isbn = $("#isbnField").val();		
		if(!isbn)
		{			
			alert("Isbn Can not be empty");			
		}
		else
		{		
		$.get(host + port + webservice + "/getbookinfo/" + isbn,
				 function(data, status){					
					 //console.log(data);
					 var title = data["title"];
					
					 var author = data["author"];
					 var description = data["description"];
					 var thumbnail = data["thumbnail"];
					
					 
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
	});
	
	$("#submitbookdetails").click(function() {
		
		$("#bookform").attr("action",host + port + webservice + "/savebook").submit();
		
	    $('#myModal1').modal('toggle');
	    $("#comment").html("policy created succe")
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