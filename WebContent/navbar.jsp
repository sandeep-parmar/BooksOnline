<%@page import="org.apache.shiro.SecurityUtils"%>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/BooksOnline/home"><b>BooksOnline</b></a>
    </div>
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
          		<li><a href="/BooksOnline/logout.jsp">Logout</a></li>          	 
        	</ul>
      	 </li>
      	 </shiro:authenticated>
      	         
        <li><a href="Secure/isbnquery.jsp"><button type="button" class="btn btn-primary btn-lg" id="postad"><b>Submit a Free Ad</b></button></a></li>                
      </ul>
    </div>
  </div>
</nav>

