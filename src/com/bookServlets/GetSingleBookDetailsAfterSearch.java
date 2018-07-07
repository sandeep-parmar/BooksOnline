package com.bookServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Book;

/**
 * Servlet implementation class GetSingleBookDetailsAfterSearch
 */
@WebServlet( name = "GetSingleBookDetailsAfterSearch", urlPatterns = {"/GetSingleBookDetailsAfterSearch"})
public class GetSingleBookDetailsAfterSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSingleBookDetailsAfterSearch() {
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
		List<Book> bookList = (ArrayList)request.getSession().getAttribute("books");
		String selIsbn = request.getParameter("selectedBookFromSearch");
		Book book = new Book();
		if(null != bookList && bookList.size()> 0 && null != selIsbn){
			for(int i=0 ;i < bookList.size(); i++){
				if(selIsbn.equals(bookList.get(i).getBookid())){
					book = bookList.get(i);
				}
			}
			request.setAttribute("seltitle", book.getBooktitle());
			request.setAttribute("selIsbn", book.getBookid());
			request.setAttribute("selDesc", book.getBookdesc());
			request.setAttribute("selAuth", book.getBookauthor());
			request.setAttribute("selThumb", book.getThumbnail());
			request.setAttribute("selisbn10", book.getIsbn_10());
			request.setAttribute("selCat", book.getCategory());
			request.setAttribute("selPubl", book.getPublisher());
			request.setAttribute("selPubd", book.getPublished_date());
	        
			RequestDispatcher dispatcher = request.getRequestDispatcher("/saveSearchBook");
		    dispatcher.forward(request, response);
		}
		
	}

}
