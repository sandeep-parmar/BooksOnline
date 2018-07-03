package com.bookServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Book;

/**
 * Servlet implementation class GetBooksFromSearch
 */
@WebServlet( name = "GetBooksFromSearch", urlPatterns = {"/GetBooksFromSearch"})
public class GetBooksFromSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBooksFromSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookSearchkey = request.getParameter("bookSearchkey");
		String bookSearchVal = request.getParameter("bookSearchVal");
		
		if(null != bookSearchkey && null != bookSearchVal ){
			List<Book> bookList = new ArrayList<>(0);
			
		}
	}

}
