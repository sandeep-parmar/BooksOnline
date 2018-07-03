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
					showDanger("Something went wrong, we are looking into it");
					//console.log(res.responseText);
					//alert( res.responseText+" "+status+" "+error);
				});
	}
	
	function setReadOnlyTrue()
	{
		$("#title").prop("readonly",true);
		$("#author").prop("readonly",true);
		$("#description").prop("readonly",true);
		$("#isbn").prop("readonly",true);
	}
	
	function setReadOnlyFalse()
	{
		$("#title").prop("readonly",false);
		$("#author").prop("readonly",false);
		$("#description").prop("readonly",false);
		$("#isbn").prop("readonly",false);
	}
	
	function getBookInfoAndSetModal1(searchKey, searchVal)
	{				
		$.get(host + port + webservice + bookservice + "/getbookinfo/" + searchKey + "/" + searchVal,
				function(data, status){				
				
				var status= data[STATUS];
	    		var errmsg = data[ERRMSG];
	    		if(status == 0)
	    		{
	    			
	    			var totalItems = data["totalItems"];
	    			var searchBookKey = searchKey;
	    			var searchBookVal = searchVal;
	    			$("#totalItems").val(totalItems);
	    			$("#searchBookKey").val(searchBookKey);
	    			$("#searchBookVal").val(searchBookVal);
	    			
//	    			var title = data["title"];									
//	    			var author = data["author"];
//	    			var description = data["description"];
//	    			var thumbnail = data["thumbnail"];
//	    			var isbn_13 = data["ISBN_13"];
//	    			var isbn_10 = data["ISBN_10"];
//	    			var publisher = data["publisher"];
//	    			var publisheddate = data["publishedDate"];
//	    			//alert(publisheddate);
//	    			var category = data["categories"];														
//
//	    			if(status == 0)
//	    			{										
//	    				$("#bookform").removeClass('hidden');				
//	    			}
//	    			else if(status == 13)
//	    			{								
//	    				$("#bookform").addClass('hidden');
//	    				resetBookForm();
//	    			}							
//
//	    			$("#title").val(title);
//	    			$("#author").val(author);										 
//	    			$("#description").val(description);
//	    			$("#isbn_13").val(isbn_13);	
//
//	    			$('#bookImg').attr('src',thumbnail);
//
//	    			/*hidden fields*/
//	    			$("#thumbnail").val(thumbnail);
//	    			$("#isbn_10").val(isbn_10);
//	    			$("#publisher").val(publisher);
//	    			$("#publisheddate").val(publisheddate);
//	    			$("#category").val(category);
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
		setReadOnlyFalse();	
	}
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
		if(!title || !author || !desc || !isbn || !lname || !lcity || !llocality || !offprice)
		{
			showWarning("Form fields can not be empty");
			return 0;
		}
		return 1;	
	}
	
	$("#searchIsbn").click(function(){
		
		/*This is because of using same form for submitting form data and browse image data*/
		
		$("#bookform").addClass('type1');
		$("#bookImgRow").removeClass('hidden');
		$("#browseImgRow").addClass('hidden');
				
		var searchKey = $("#searchkey").val();
		var searchVal = $("#inputfield").val();
		//alert(searchKey);
		if(!searchVal)
		{			
			alert("input field can not be empty");			
		}else{		
			//getBookInfoAndSetModal1(searchKey, searchVal);
			var boolistjsp = ("../Secure/bookListView.jsp?page=1&bookSearchKey="+searchKey+"&bookSearchVal="+searchVal);
			window.location.href = boolistjsp;
		}
		return false;
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
		$("#manualfileloadbutton").show();
		
	});
	
	$("#submitbookdetails").click(function() {
		
		//saving book
		if($("#bookform").hasClass("type1"))
		{
			if(validateBookForm())
			{
				//$("#bookform").attr("action",host + port + webservice + bookservice + "/savebook").submit();
				
				submitNormalForm();
				
				$("#bookform").addClass("hidden");
			}				
		}	
		else
		{
			alert("bookform do not have type 1");
			
			if(validateBookForm())
			{
				submitcustomform();
			}
		}
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
		    			/*$("#logincomment").html("Your Account is disabled,Please activate your acount by Clicking on verification link" +
		    					"in your registered email ");*/
		    			showWarning("Your Account is disabled,Please activate your acount by Clicking on verification link" +
		    					"in your registered email");
		    		}
		    		else
		    		{
		    			//$("#logincomment").html(errmsg);
		    			showDanger(errmsg);
		    		}
		    	}		        
		    },
		    error: function (res,status,error){
		        //alert("user validation failed");
		    	//showDanger(res.responseText);
		    	showDanger("Something went wrong, we are looking into it");
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
					showDanger("Something went wrong, we are looking into it");
					//console.log(res.responseText);
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
					showDanger("Something went wrong, we are looking into it");
					//console.log(res.responseText);
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
			    		/*$("#signupcomment").html("User registered successfully. Please verify your account by clicking on verification " +
			    				"link sent to your registered email address");*/
			    		showSuccess("User registered successfully. Please verify your account by clicking on verification " +
			    				"link sent to your registered email address");

			    	}else{
			    		//$("#signupcomment").html(ermsg);
			    		showWarning(errmsg);
			    	}		  
			    },
			    error: function (res,status,error){
			    	showDanger("Something went wrong, we are looking into it");
			        //alert("user registration failed");
			    	//showDanger(res.responseText);
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
		    	var status= data[STATUS];
		    	var errmsg = data[ERRMSG];
		    	
		    	if(!status){
		    		if(!profemail)
		    		{
		    			showSuccess("user profile is updated successfully");
		    			//$("#profilecomment").html("user profile is updated successfully");
		    		}
		    		else
		    		{
		    			//$("#profilecomment").html("user email is updated successfully. verification link has been sent to your new email");
		    			showSuccess("user email is updated successfully. verification link has been sent to your new email");
		    		}
		    		
		    		//$(location).attr('href', 'login.jsp');
		    	}else{
		    		//$("#profilecomment").html(errmsg);
		    		showDanger(errmsg);
		    	}		  
		    },
		    error: function (res,status,error){
		    	showDanger("Something went wrong, we are looking into it");
		        //alert("user profile update failed");
		    	//showDanger(res.responseText);
		    }
	    });
		}
		else
		{
	  		//$("#profilecomment").html("Please modify proper field to update");
			showWarning("Please modify proper field to update");
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
						var status = data[STATUS];
						var error = data[ERROMSG];
						if(status === "success")
						{
							if(ret == 0){
								showSuccess("Password reset link has been sent to your email");							
							}else{
								showDanger(error);
							}	
						}
				})
				.fail(function(res,status,error) {
					showDanger("Something went wrong, we are looking into it");
					//showDanger(res.responseText);
					/*console.log(res.responseText);
					alert( res.responseText+" "+status+" "+error);*/
				});		    	
	});
	
	$("#resetpasswordsubmitbutton").click(function() {
		
		//alert(host + port + webservice + loginservice + "/saveuser");
		alert("reset password");
		var uuid = $("#resuuid").val();
		var respasswd = $("#respassword").val();
		$.get(host + port + webservice + loginservice + "/resetpassword/" + uuid + "/" + respasswd,
				function(data, status){					
						var status = data[STATUS];
						var error  = data[ERRMSG];
						if(status === "success")
						{
							if(status == 0){
								showSuccess("Password has been reset successfully");							
							}else{
								showWarning(error);
							}	
						}
				})
				.fail(function(res,status,error) {
					showDanger("Something went wrong, we are looking into it");
					//console.log(res.responseText);
					//alert( res.responseText+" "+status+" "+error);
				});		    	
	});
		
	/*Normal form*/
	function submitNormalForm()
	{
		
		$.ajax({
            type: "POST",       
            contentType: "application/x-www-form-urlencoded",
            url: host + port + webservice + bookservice + "/savebook",
            data: $("#bookform").serialize(),
            dataType: 'json',
            success: function (data) {              	
            	$("#bookform").addClass("hidden");
            	showSuccess(data[ERRMSG]);            	
                //console.log("SUCCESS : ", data[ERRMSG]);
            },
            error: function (res,status,error) {
            
            	showDanger(res.responseText);
            	//alert("some error:"+e);
                //console.log("ERROR : ", e);

            }
        });
	}
	
	/*Upload custom images*/
	function submitcustomform()
	{		
	 	
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
                         	
            	alert(data[ERRMSG]);
            	showSuccess(data[ERRMSG]);
            	$("#bookform").addClass("hidden");
                //console.log("SUCCESS : ", data);
                //$("#submitcustomdata").prop("disabled", false);

            },
            error: function (res,status,error) {
            	showDanger(res.responseText);
            	//alert("book saved successfully");
                //console.log("ERROR : ", e);
            }
        });
	}

	 $("#manualfileloadbutton").click(function (event) {


		$("#bookImgRow").addClass('hidden');
		$("#browseImgRow").removeClass('hidden');
				
		$("#bookform").removeClass('type1');
		$("#bookform").removeClass('hidden');
		resetBookForm();
		
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
		
	  /*for hiding and showing alert info*/
	  $(function(){
		    $("[data-hide]").on("click", function(){
		        $(this).closest("." + $(this).attr("data-hide")).hide();
		    });
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
	  
	  function openBookPopUp(url, title, w, h) {
		    
		    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
		    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

		    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
		    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

		    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
		    var top = ((height / 2) - (h / 2)) + dualScreenTop;
		    var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
		    if (window.focus) {
		        newWindow.focus();
		    }
		}
});