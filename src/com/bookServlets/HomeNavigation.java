package com.bookServlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.BookUser;
import com.dao.DBFacade;
import com.mysql.jdbc.StringUtils;

/**
 * Servlet implementation class HomeNavigation
 */
@WebServlet(name = "HomeNavigation",urlPatterns = {"/HomeNavigation"})
public class HomeNavigation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeNavigation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		System.out.println(request.getQueryString());
		String currentHomePageStr = request.getParameter("currentHomePage");
		String recordsHomePerPageStr = request.getParameter("recordsHomePerPage");
		System.out.println("called HomeNavigation :currentHomePageStr::"+currentHomePageStr+" recordsHomePerPageStr:: "+recordsHomePerPageStr);
		currentHomePageStr = null == currentHomePageStr ? "0" : currentHomePageStr;
		recordsHomePerPageStr = null == recordsHomePerPageStr ? "10" : recordsHomePerPageStr;
		int currentPage = Integer.parseInt(currentHomePageStr);
		int recordsPerPage = Integer.parseInt(recordsHomePerPageStr);
		int totalItems =  DBFacade.getBookAdListCount();
		int noOfPages = 0;
		noOfPages = totalItems / 10;
		if(totalItems % 10 != 0){
			noOfPages++;
		}
		System.out.println("totalItems returned::"+totalItems);
		List<BookUser> list = DBFacade.getBookAdList(currentPage, recordsPerPage);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("noOfPages", noOfPages);
		request.getSession().setAttribute("totalItems", totalItems);
		request.getSession().setAttribute("currentHomePage", currentPage == 0 ? 1 : currentPage);
		request.getSession().setAttribute("recordsHomePerPage", recordsPerPage);
		response.sendRedirect("/BooksOnline/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
