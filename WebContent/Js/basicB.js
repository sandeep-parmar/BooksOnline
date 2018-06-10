$(document).ready(function(){
	
	var host = "http://localhost:";
	var port="8080";
	var webservice="/BooksOnline/rest";
	var loginservice="/login";
	var bookservice="/books";
	var bookAdservice = "/bookAd";
	var STATUS = "status";
	var ERRMSG = "errmsg";
				
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
					$("#booklistrow").toggleClass("hidden");
					//$('#myModal2').modal('toggle');
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
				$("#bookform").removeClass('hidden');
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
									 
				//$('#myModal1').modal('toggle');
				
									 
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);
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
	
	$("#searchIsbn").click(function(){
		
		/*This is because of using same form for submitting form data and browse image data*/
		//$("#bookform").attr("enctype","application/x-www-form-urlencoded");
		$("#bookform").addClass('type1');
		$("#bookImgRow").removeClass('hidden');
		$("#browseImgRow").addClass('hidden');
				
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
			//alert("sss-1");
			getBookInfoAndSetModal1(searchKey, searchVal);
		}
	});
	
	$("#searchbytitle").click(function(){
		
		//$('#myModal2').modal('toggle');
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
		$("#booklistrow").toggleClass("hidden");
	});
	
	
	$("#cancelbookdetails").click(function() {
		resetBookForm();
		$("#bookform").addClass('hidden');
		
	});
	
	$("#submitbookdetails").click(function() {
		
		//saving book
		if($("#bookform").hasClass("type1"))
		{
			alert("bookform has type 1");
			$("#bookform").attr("action",host + port + webservice + bookservice + "/savebook").submit();
				
			//$("#comment").html("book saved successfully");
			alert("book type-1 saved successfully");	
			$("#bookform").addClass("hidden");
			//sleep(1000);
			//location.replace("/BooksOnline/home.jsp");
		}	
		else
		{
			alert("bookform do not have type 1");
			submitcustomform();
		}
		resetBookForm();
	    return false;
	});
	
	$("#loginformsubmitbutton").click(function() {
		
		var email = $("#loginid").val();
		var password = $("#loginpassword").val();
		
		jQuery.ajax({
		    url: host + port + webservice + loginservice + "/validate",
		    type: 'post',
		    data: JSON.stringify({ 
		    	"email": $("#loginid").val(),
				"password": $("#loginpassword").val(),				
		    }),	
		    dataType: 'json',
		    contentType: "application/json; charset=utf-8",
		    success: function (data){
		    	var status= data[STATUS];
		    	var errmsg = data[ERRMSG];
		    	var active = data["accountStatus"];
		    	var userEmail = data["userEmail"];
		    	//var userMobile = data["userMobile"];
		    	
		    	if(status == 0)
		    	{				    
		    		$(location).attr('href', '/BooksOnline/Secure/isbnquery');
		    	}
		    	else
		    	{
		    		if(active === 0)
		    		{
		    			$("#logincomment").html("Your Account is disabled,Please activate your acount by Clicking on verification link" +
		    					"in your registered email ");
		    		}
		    		else
		    		{
		    			$("#logincomment").html(errmsg);
		    		}
		    	}		        
		    },
		    error: function (data){
		        alert("user validation failed");        
		    }
		});
	});
	
	$(".soldbutton").click(function() {
		  var count = $(this).attr("value");
		  var bookid = $("#soldthisbook_" + count).val(); 
		  alert(bookid);
		  $.get(host + port + webservice + bookservice + "/soldthisbook/" + bookid,
				function(data, status){					
					$(location).attr('href', '/BooksOnline/Secure/myads.jsp');
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					//alert( res.responseText+" "+status+" "+error);
				});
	});

	$(".updatepricebutton").click(function() {
		var count = $(this).attr("value");
		//alert("updatedprice_" + count);
		var offerprice = $("#updatedprice_" + count).val();
		var bookid = $("#soldthisbook_" + count).val(); 			
		
		  $.get(host + port + webservice + bookservice + "/updatepriceofbook/" + bookid + "/" + offerprice,
				function(data, status){					
					$(location).attr('href', '/BooksOnline/Secure/myads.jsp');
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					//alert( res.responseText+" "+status+" "+error);
				});
	});
	
	function sendSoldStatus(bookid) {
		alert(bookid);
	}
	
	$("#signupformsubmitbutton").click(function() {
						
			//alert(host + port + webservice + loginservice + "/saveuser");
				
			jQuery.ajax({
			    url: host + port + webservice + loginservice + "/saveuser",
			    type: 'post',
			    data: JSON.stringify({ 
			    	"username": $("#name").val(),
					//"mobile": $("#mobile").val(),
					"email": $("#email").val(),
					"password": $("#password").val()
			    }),	
			    dataType: 'json',
			    contentType: "application/json; charset=utf-8",
			    success: function (data){
			    	var status= data[STATUS];
			    	var errmsg = data[ERRMSG];
			    	if(!status){
			    		$("#signupcomment").html("User registered successfully. Please verify your account by clicking on verification " +
			    				"link sent to your registered email address");
			    		//$(location).attr('href', 'login.jsp');
			    	}else{
			    		$("#signupcomment").html(ermsg);
			    	}		  
			    },
			    error: function (data){
			        alert("user registration failed");        
			    }
		    });
	});
	$("#profileupdateformsubmitbutton").click(function() {
		
		var profname;
		//var profmobile;
		var profemail;
		var somethingtoupdate = false;
		if($('#namecheckbox').is(":checked"))
		{			
			profname = $("#profname").val();
			somethingtoupdate = true;
		}
		/*if($('#mobilecheckbox').is(":checked"))
		{			
			profmobile = $("#profmobile").val();
			somethingtoupdate = true;
		}*/
		if($('#emailcheckbox').is(":checked"))
		{			
			profemail = $("#profemail").val();
			somethingtoupdate = true;
		}
				
		if(somethingtoupdate)
		{	
		jQuery.ajax({
		    url: host + port + webservice + loginservice + "/profileupdate",
		    type: 'post',
		    data: JSON.stringify({ 
		    	"username": profname,
				//"mobile": profmobile,
				"email": profemail
		    }),	
		    dataType: 'json',
		    contentType: "application/json; charset=utf-8",
		    success: function (data){
		    	var status= data["status"];
		    	var errmsg = data[ERRMSG];
		    	
		    	if(!status){
		    		if(!profemail)
		    		{
		    			$("#profilecomment").html("user profile is updated successfully");
		    		}
		    		else
		    		{
		    			$("#profilecomment").html("user email is updated successfully. verification link has been sent to your new email");
		    		}
		    		
		    		//$(location).attr('href', 'login.jsp');
		    	}else{
		    		$("#profilecomment").html(errmsg);
		    	}		  
		    },
		    error: function (data){
		        alert("user profile update failed");        
		    }
	    });
		}
		else
		{
	  		$("#profilecomment").html("Please modify proper field to update");
		}
});
	
	/*This is to update profile data like name,mobile,email*/
	$(".checked").change(function(e) {
		var editfield = $(this).attr("value");
		if($(e.target).is(':checked'))
		{			
			$("#" + editfield).prop('disabled',false);
		}
		else
		{				
			$("#" + editfield).prop('disabled',true);
		}
		
	});
	
	$("#forgetpasswordsubmitbutton").click(function() {
		
		//alert(host + port + webservice + loginservice + "/saveuser");		
		var fpemail = $("#fpemail").val();
		$.get(host + port + webservice + loginservice + "/forgetpassword/" + fpemail,
				function(data, status){					
						var ret= data["status"];						
						if(status === "success")
						{
							if(ret == 0){
								$("#fpcomment").html("Password reset link has been sent to your email");							
							}else{
								$("#fpcomment").html("Invalid email");
							}	
						}
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);
				});		    	
	});
	
	$("#resetpasswordsubmitbutton").click(function() {
		
		//alert(host + port + webservice + loginservice + "/saveuser");
		alert("reset password");
		var uuid = $("#resuuid").val();
		var respasswd = $("#respassword").val();
		$.get(host + port + webservice + loginservice + "/resetpassword/" + uuid + "/" + respasswd,
				function(data, status){					
						var ret= data["status"];						
						if(status === "success")
						{
							if(ret == 0){
								$("#rescomment").html("Password has been reset successfully");							
							}else{
								$("#rescomment").html("Password reset successful");
							}	
						}
				})
				.fail(function(res,status,error) {
					console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);
				});		    	
	});
		
	
	function submitcustomform()
	{
		 //	alert("sandeep");
	 	console.log("sandeep");
        //stop submit the form, we will post it manually.

        // Get form
        var form = $('#bookform')[0];

		// Create an FormData object 
        var data = new FormData(form);

		// If you want to add an extra field for the FormData
        //data.append("CustomField", "This is some extra data, testing");

		// disabled the submit button
       // $("#submitcustomdata").prop("disabled", true);
        
        	
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: host + port + webservice + bookservice + "/brbook",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

                //$("#brresult").text(data);
            	$("#bookform").addClass("hidden");
                console.log("SUCCESS : ", data);
                //$("#submitcustomdata").prop("disabled", false);

            },
            error: function (e) {

                //$("#brresult").text(e.responseText);
            	alert("book saved successfully");
                console.log("ERROR : ", e);
                //$("#submitcustomdata").prop("disabled", false);

            }
        });
	}
	/*Upload custom images*/
	 $("#submitcustomdata").click(function (event) {

		 //	alert("sandeep");
		 	console.log("sandeep");
	        //stop submit the form, we will post it manually.
	        event.preventDefault();

	        // Get form
	        var form = $('#customedataform')[0];

			// Create an FormData object 
	        var data = new FormData(form);

			// If you want to add an extra field for the FormData
	        //data.append("CustomField", "This is some extra data, testing");

			// disabled the submit button
	        $("#submitcustomdata").prop("disabled", true);
	        
	        	
	        $.ajax({
	            type: "POST",
	            enctype: 'multipart/form-data',
	            url: host + port + webservice + bookservice + "/brbook",
	            data: data,
	            processData: false,
	            contentType: false,
	            cache: false,
	            timeout: 600000,
	            success: function (data) {

	                $("#brresult").text(data);
	                console.log("SUCCESS : ", data);
	                $("#submitcustomdata").prop("disabled", false);

	            },
	            error: function (e) {

	                $("#brresult").text(e.responseText);
	                console.log("ERROR : ", e);
	                $("#submitcustomdata").prop("disabled", false);

	            }
	        });

	    });

	 $("#manualfileloadbutton").click(function (event) {
		// alert("parmar");
		//$("#customedataform").toggleClass('hidden');
		$("#bookImgRow").addClass('hidden');
		$("#browseImgRow").removeClass('hidden');
		
		//$("#bookform").attr("enctype","multipart/form-data");
		$("#bookform").removeClass('type1');
		$("#bookform").removeClass('hidden');
		$("#manualfileloadbutton").hide();
	 });
		 
	 
	 // manually file input plugin
	 $("#bootfileinput").fileinput({
		 	'showUpload':false,
		 	'previewFileType':'any',		 	
		    hideThumbnailContent: false,
		    allowedFileTypes:['image'],
		    previewSettings : {
    			image: {width: "auto", height: "auto", 'max-width': "100%",'max-height': "100%"}
		    	}
		});

	 
	 
	 // easy auto complete
	 			 		
	 		
	 		var options1 = {
				url: function(phrase) {
					return host + port + webservice + bookservice + "/getcitysuggestion?phrase=" + phrase + "&format=json";
				},

				getValue: "name"
			};	 	

			$("#entercity").easyAutocomplete(options1);
			
						 
			var options2 = {
				url: function(phrase) {
					return host + port + webservice + bookservice + "/getlocalitysuggestion?phrase=" + phrase + "&format=json";
				},

				getValue: "name"
			};

			$("#enterlocality").easyAutocomplete(options2);

			var options3 = {					
					url: function(phrase) {
						return host + port + webservice + bookservice + "/getbooksuggestion?phrase=" + phrase + "&format=json";
					},

					getValue: "name"
				};

				$("#searchbookbytai").easyAutocomplete(options3);
		 
				
			$(document).ajaxStart(function(){
					 // Show image container
					$("#loadgif").show();
			});
			$(document).ajaxComplete(function(){
					 // Hide image container
					$("#loadgif").hide();
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