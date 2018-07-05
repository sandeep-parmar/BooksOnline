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
import com.service.BookService;

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
		String currentPageStr = request.getParameter("currentPage");
		String recordsPerPageStr = request.getParameter("recordsPerPage");
		
		if(null != bookSearchkey && null != bookSearchVal ){
			int currentPage = 1;
			int recordsPerPage = 10;
			if(null != currentPageStr && null != recordsPerPageStr){
				currentPage = Integer.parseInt(currentPageStr);
				recordsPerPage = Integer.parseInt(recordsPerPageStr);
			}
			List<Book> bookList = new ArrayList<>(0);
			List<Book> booksTotal = new ArrayList<>(0);
			BookService bookService = new BookService();
			int totalItems = 0;
			int noOfPages = 1;
			currentPage = currentPage - 1;
			booksTotal = bookService.getBookDetails(bookSearchkey, bookSearchVal, 0, recordsPerPage);
			if(null != booksTotal && booksTotal.size() > 0){
				totalItems = booksTotal.get(0).getTotalItems();
				if(totalItems % 10 == 0){
					noOfPages = totalItems;
				}else{
					noOfPages = (totalItems / 10) + 1;
				}
				bookList = bookService.getBookDetails(bookSearchkey, bookSearchVal, currentPage, recordsPerPage);
			}
			
			currentPage = currentPage + 1;
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("totalItems", totalItems);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
	        request.setAttribute("books", bookList);
	        request.setAttribute("bookSearchkey", bookSearchkey);
	        request.setAttribute("bookSearchVal", bookSearchVal);
	        
//	        response.sendRedirect("/BooksOnline/Secure/bookListFromSearch.jsp");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/Secure/bookListFromSearch");
	        dispatcher.forward(request, response);
	        
		}
	}

}