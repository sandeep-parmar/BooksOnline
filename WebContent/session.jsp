<%
	String user = request.getParameter("user");
	//System.out.println("yeun gelo");
	if(user != null)
	{
		session.setAttribute("user", user);
		response.sendRedirect("isbnquery.jsp");
	}
%>