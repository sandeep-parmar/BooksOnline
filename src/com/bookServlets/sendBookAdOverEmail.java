package com.bookServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.BookService;

import entities.BookAdBean;

/**
 * Servlet implementation class sendBookAdOverEmail
 */
@WebServlet("/sendBookAdOverEmail")
public class sendBookAdOverEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendBookAdOverEmail() {
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
		System.out.println("sendBookAdOverEmail called");
		BookAdBean bd = new BookAdBean(request.getParameter("bookid"),request.getParameter("booktitle"),request.getParameter("bookauthor"), request.getParameter("bookdesc"), request.getParameter("thumbnail"), request.getParameter("name"), Integer.parseInt(request.getParameter("price")), Integer.parseInt(request.getParameter("soldstatus")), request.getParameter("email"), Integer.parseInt(request.getParameter("pin")), request.getParameter("city"), request.getParameter("area"),request.getParameter("buyerEmail"));
		
		BookService.sendDetailsOfBookAd(bd);
		
	}
}
