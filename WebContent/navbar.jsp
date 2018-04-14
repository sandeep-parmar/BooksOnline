<%@page import="org.apache.shiro.SecurityUtils"%>

<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/BooksOnline/home"><b>BooksOnline</b></a>
    </div>
    
     <form class="navbar-form navbar-left" method="get" action="custombooks">
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Enter City" id="entercity" name="entercity" style="border-radius:1rem">
      </div>
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Enter Locality" id="enterlocality" name="enterlocality" style="border-radius:1rem">
      </div>
      <div class="form-group">
        <input type="text" class="form-control form-rounded" placeholder="Title / Author / Isbn" id="searchbookbytai" name="searchbookbytai" style="border-radius:1rem">
      </div>
      <button type="submit" class="btn btn-primary btn-md">Search</button>
    </form>
    
    <div class="collapse navbar-collapse" id="myNavbar">
       
      <ul class="nav navbar-nav navbar-right">
       
      	<shiro:notAuthenticated>    
      	<li><a href="/BooksOnline/signup"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      	<li><a href="/BooksOnline/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      	</shiro:notAuthenticated>
      	
      	<shiro:authenticated>      	       	   
      	 <li class="dropdown">
      	 	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span>
      	 		<shiro:principal property="username"/><span class="caret"></span>
      	 	</a>
      	 	<ul class="dropdown-menu">          		
          		<li><a href="/BooksOnline/Secure/myads">MyAds</a></li>
          		<li><a href="/BooksOnline/Secure/myprofile">Profile</a></li>  
          		<li><a href="/BooksOnline/logout.jsp">Logout</a></li>          		       	
        	</ul>
      	 </li>
      	 </shiro:authenticated>
      	         
         <li><a href="/BooksOnline/Secure/isbnquery.jsp"><button type="button" class="btn btn-primary btn-lg" id="postad"><b>Submit Free Ad</b></button></a></li>                
      </ul>
    </div>
  </div>
</nav>
