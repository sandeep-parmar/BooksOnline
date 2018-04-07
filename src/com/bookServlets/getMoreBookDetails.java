package com.bookServlets;

import java.awt.SecondaryLoop;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BooksFullAdDao;

import entities.BookAdBean;

/**
 * Servlet implementation class getMoreBookDetails
 */
@WebServlet("/getMoreBookDetails")
public class getMoreBookDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getMoreBookDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("called getMoreBookDetails");
		String url = "/bookdetails.jsp";

		BooksFullAdDao bfd = new BooksFullAdDao();
		RequestDispatcher rd ;
		BookAdBean bd = bfd.getBookFullDetails(request.getParameter("userId"), request.getParameter("bookId"));
		System.out.println(bd);
		request.setAttribute("bookad", bd);
		//request.getSession().setAttribute("sharedId", shared);
		request.setAttribute("bookname", bd.getBooktitle());
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
