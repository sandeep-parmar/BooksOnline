$(document).ready(function(){
	
	var host = "http://localhost:";
	var port="8080";
	var webservice="/BooksOnline/rest";
	var loginservice="/login";
	var bookservice="/books";
					
	
	function getSetBookTitles(author)
	{
		
		$.get(host + port + webservice + bookservice + "/getbooklist/" + author,
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
		//alert(host + port + webservice + bookservice + "/getbookinfo/" + searchKey + "/" + searchVal);
		$.get(host + port + webservice + bookservice + "/getbookinfo/" + searchKey + "/" + searchVal,
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
		//alert(searchVal);
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
		
		$("#bookform").attr("action",host + port + webservice + bookservice + "/savebook").submit();
		
	    $('#myModal1').modal('toggle');
	    $("#comment").html("book saved successfully")
	    return false;
	});
	
	$("#loginformsubmitbutton").click(function() {
		//alert(host + port + webservice + loginservice + "/saveuser");
		
		var mobile = $("#loginid").val();
		var password = $("#loginpassword").val();
		
		jQuery.ajax({
		    url: host + port + webservice + loginservice + "/validate",
		    type: 'post',
		    data: JSON.stringify({ 
		    	"mobile": $("#loginid").val(),
				"password": $("#loginpassword").val(),				
		    }),	
		    dataType: 'json',
		    contentType: "application/json; charset=utf-8",
		    success: function (data){
		    	var status= data["valid"];
		    	if(status)
		    	{				    
		    		$(location).attr('href', '/BooksOnline/Secure/isbnquery.jsp?user=' + mobile);
		    	}
		    	else
		    	{
		    		$("#logincomment").html("userid or password is incorrect");
		    	}
		        
		    },
		    error: function (data){
		        alert("user login failed");        
		    }

		});
	});
	
	$("#signupformsubmitbutton").click(function() {
				
		
		alert(host + port + webservice + loginservice + "/saveuser");
			
		jQuery.ajax({
		    url: host + port + webservice + loginservice + "/saveuser",
		    type: 'post',
		    data: JSON.stringify({ 
		    	"username": $("#name").val(),
				"mobile": $("#mobile").val(),
				"email": $("#email").val(),
				"password": $("#password").val()
		    }),	
		    dataType: 'json',
		    contentType: "application/json; charset=utf-8",
		    success: function (data){
		    	var status= data["status"];
		    	if(!status)
		    	{
		    		$("#signupcomment").html("User registered successfully");
		    		$(location).attr('href', 'login.jsp');
		    	}
		    	else
		    	{
		    		$("#signupcomment").html("Their is an error in registration");
		    	}
		        
		    },
		    error: function (data){
		        alert("user registration failed");        
		    }

		    });
		/*$.post(host + port + webservice + loginservice + "/saveuser",
				{
					contentType: "application/json; charset=utf-8",	
				},
				{
					username: $("#name").val(),
					mobile: $("#mobile").val(),
					email: $("#email").val(),
					password: $("#password").val()
				},
				function(data, status){					
				console.log(data);
			
		
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);
				});*/
		
		/*$("#signupform").attr("action",host + port + webservice + loginservice + "/saveuser").submit();
		$("#signupcomment").html("User registered successfully");
		$(location).attr('href', 'login.jsp');
	    return false;*/
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