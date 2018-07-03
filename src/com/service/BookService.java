package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import com.bean.Book;
import com.bean.User;
import com.dao.BookAdDAO;
import com.dao.BookDao;
import com.dao.DBFacade;
import com.dao.UserRealm;
import com.utility.*;

import entities.BookAdBean;

@Path("/books")
public class BookService {
	
	@Context ServletContext context;
	
	public static String statusStr = "status";
	public static String errmsgStr = "errmsg";

	public BookService() {
		
	}
	public String sendResponse(int status)
	{
		String response = Errorcode.errmsgstr[status];
		
		JSONObject json = new JSONObject();
		json.put(statusStr, status);
		json.put(errmsgStr, response);
		
		return json.toString();
	}
	
	/*
	 * Performing a search You can perform a volumes search by sending an HTTP GET request to the following URI:
		https://www.googleapis.com/books/v1/volumes?q=search+terms
		This request has a single required parameter:

		q - Search for volumes that contain this text string. There are special keywords you can specify in the search terms to search in particular fields, such as:
		intitle: Returns results where the text following this keyword is found in the title.
		inauthor: Returns results where the text following this keyword is found in the author.
		inpublisher: Returns results where the text following this keyword is found in the publisher.
		subject: Returns results where the text following this keyword is listed in the category list of the volume.
		isbn: Returns results where the text following this keyword is the ISBN number.
		lccn: Returns results where the text following this keyword is the Library of Congress Control Number.
		oclc: Returns results where the text following this keyword is the Online Computer Library Center number.
		Request
		Here is an example of searching for Daniel Keyes' "Flowers for Algernon":

		GET https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes&key=yourAPIKey
	 */
	private String sendBookRequest(String key, String value)
	{
		String address = "https://www.googleapis.com/books/v1/volumes?q=";
		String query = key + ":" + value;
		String charset = "UTF-8";
	 
		int status = Errorcode.EC_SUCCESS.getValue();
		System.out.println(query);
		URL url;
		String str;
		String jsonres = "";		
		try {
			url = new URL(address + URLEncoder.encode(query, charset));
			System.out.println("url called"+url);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
			while ((str = in.readLine()) != null) {			
				jsonres+=str;
			}		
		}catch (IOException e) {
			System.out.println(e.getMessage());			
			//status = Errorcode.EC_FILE_READ_FAILED.getValue();			
		}
		return jsonres;
	}
	
	private String sendBookRequest(String key, String value, int startInd, int endInd)
	{
		String address = "https://www.googleapis.com/books/v1/volumes?q=";
		String query = key + ":" + value + "&startIndex=" + startInd + "&maxResults=" + endInd;
		String charset = "UTF-8";
	 
		int status = Errorcode.EC_SUCCESS.getValue();
		System.out.println(query);
		URL url;
		String str;
		String jsonres = "";		
		try {
//			url = new URL(address + URLEncoder.encode(query, charset));
			url = new URL(address + query);
			System.out.println("url called"+url);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
			while ((str = in.readLine()) != null) {			
				jsonres+=str;
			}		
		}catch (IOException e) {
			System.out.println(e.getMessage());			
			//status = Errorcode.EC_FILE_READ_FAILED.getValue();			
		}
		return jsonres;
	}

	@GET
	@Path("/getbooklist/{author}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookList(@PathParam("author") String author)
	{
//		int status = Errorcode.EC_SUCCESS.getValue();
		String jsonres= sendBookRequest(JsonStrings.INAUTHOR, author);
		
		JsonStrings jsonSTR = new JsonStrings(jsonres);

		JSONArray itemsArr = jsonSTR.getItemsArray();
		JSONArray titles = jsonSTR.getTitlesArrayFromItems(itemsArr);

		JSONObject returnJson = jsonSTR.getNewJsonObject();
		returnJson.put(JsonStrings.BOOKS, titles);
		//System.out.println("returning:"+returnJson.toString());
		return returnJson.toString();
	}
	

	@GET
	@Path("/soldthisbook/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	public String setSoldStatusTrue(@PathParam("param1") String bookId)
	{
		System.out.println(bookId);
		int status = Errorcode.EC_SUCCESS.getValue();
		User user = UserRealm.getLoggedInUser();
		status = DBFacade.setSoldStatusTrue(user.getEmail(), bookId);
		return sendResponse(status);
	}
	
	public String setNoDataFound(String str){
//		return null != str ? str.trim() : "<span style=\"background-color: #FFFF00\">Information not found</span>";
		return null != str ? str.trim() : "<span style=\"font-style: italic;\">Information not found</span>";
	}
	
	@GET
	@Path("/getbookinfo/{param1}/{param2}/{param3}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookDetails(@PathParam("param1") String key, @PathParam("param2") String value, @PathParam("param3") String page)
	{				
		System.out.println("getBookDetails called : key:" + key + "  Value:" +value);		
		int status = Errorcode.EC_SUCCESS.getValue();
		int iter = 0;		
		key = key.equalsIgnoreCase("title") ? JsonStrings.INTITLE : key.equalsIgnoreCase("isbn") ? JsonStrings.INISBN : JsonStrings.INAUTHOR;
		
		int startInd = Integer.parseInt(page);
		int endInd = 0;
		if(startInd == 1){
			startInd = 0;
			endInd = 7;
		}else{
			endInd = 7;
			startInd = startInd - 1;
			startInd = (startInd*endInd)+1;
		}
		
		do{
			//to get total count
			String jsonres = sendBookRequest(key, value, 0, endInd);
			JsonStrings jsonSTR = null;
			JSONObject returnJson = null;
			JSONArray jsonArr = null;
			JSONObject obj0 = null;
			Integer totalItems = null;
			if(null != jsonres && !jsonres.isEmpty()){
				jsonSTR = new JsonStrings(jsonres);
				totalItems = jsonSTR.getTotalItems(jsonres);
				returnJson = jsonSTR.getNewJsonObject();
				returnJson.put(JsonStrings.TOTALITEMS, totalItems);
			}
			
			List<Book> bookList = new ArrayList<>(0);
			int cntItem = 0;
			jsonres = "";
			jsonres = sendBookRequest(key, value, startInd, endInd);
			if(null != jsonres && !jsonres.isEmpty()){
				jsonSTR = new JsonStrings(jsonres);
				//got one or more items
				if(totalItems > 1){
					jsonArr = jsonSTR.getItemsArray();
					if(null != jsonArr){
						for(cntItem = 0; cntItem < endInd; cntItem++){
							obj0 = jsonSTR.getIthObjectFromArray(jsonArr, cntItem);
							if(null != obj0){
								JSONObject jsonvolume = jsonSTR.getVolumesInfoFromObject(obj0);
								if(null != jsonvolume){
									JSONObject imagelinks = jsonSTR.getImageLinksFromVolumeObject(jsonvolume);
									String isbn = jsonSTR.getIsbnFromVolumeObject(jsonvolume);
									String isbn10 = jsonSTR.getIsbn10FromVolumeObject(jsonvolume);
									String publisher = jsonSTR.getPublisherFromVolumeObject(jsonvolume);
									String publishedDate = jsonSTR.getPublishedDateFromVolumeObject(jsonvolume);
									String category = jsonSTR.getCategoryFromVolumeObject(jsonvolume);
									String title = jsonSTR.getTitleFromVolumeObject(jsonvolume);		
									String description = jsonSTR.getDescriptionFromVolumeObject(jsonvolume);
									JSONArray authors =  jsonSTR.getAuthorsArrayFromVolumeObject(jsonvolume);
									String authorlist = "";
									String thumbnail = null;
									if(authors != null){			
										for(int i = 0; i < authors.length(); i++)
										{
											authorlist += authors.getString(i) + " ";
										}
									}
									if(null != imagelinks){
										thumbnail = jsonSTR.getThumbnailFromImageLinks(imagelinks);
									}
									
									Book book = new Book();
									book.setBookid(setNoDataFound(isbn));
									book.setIsbn_10(setNoDataFound(isbn10));
									book.setPublisher(setNoDataFound(publisher));
									book.setPublished_date(setNoDataFound(publishedDate));
									book.setCategory(setNoDataFound(category));
//									if(key.equalsIgnoreCase(JsonStrings.INTITLE)){
//										if(title.contains(value)){
//											title = title.replace(value, "<span style=\"background-color: #FFFF00\">"+value+"</span>");
//										}
//									}
									book.setBooktitle(setNoDataFound(title));
									book.setBookdesc(setNoDataFound(description));
									book.setBookauthor(setNoDataFound(authorlist));
									book.setThumbnail(setNoDataFound(thumbnail));
									bookList.add(book);
								}else{
									status = Errorcode.EC_DATA_NOT_FOUND.getValue();
									break;
								}
							}
						}
					}
				}else{
					returnJson.put(JsonStrings.TOTALITEMS, totalItems);
					jsonArr = jsonSTR.getItemsArray();
					if(null != jsonArr){
						obj0 = jsonSTR.getIthObjectFromArray(jsonArr, 0);
						if(null != obj0){
							JSONObject jsonvolume = jsonSTR.getVolumesInfoFromObject(obj0);
							if(null != jsonvolume){
								JSONObject imagelinks = jsonSTR.getImageLinksFromVolumeObject(jsonvolume);
								String isbn = jsonSTR.getIsbnFromVolumeObject(jsonvolume);
								String isbn10 = jsonSTR.getIsbn10FromVolumeObject(jsonvolume);
								String publisher = jsonSTR.getPublisherFromVolumeObject(jsonvolume);
								String publishedDate = jsonSTR.getPublishedDateFromVolumeObject(jsonvolume);
								String category = jsonSTR.getCategoryFromVolumeObject(jsonvolume);
								String title = jsonSTR.getTitleFromVolumeObject(jsonvolume);		
								String description = jsonSTR.getDescriptionFromVolumeObject(jsonvolume);
								JSONArray authors =  jsonSTR.getAuthorsArrayFromVolumeObject(jsonvolume);
								String authorlist = "";
								String thumbnail = null;
								if(authors != null){			
									for(int i = 0; i < authors.length(); i++)
									{
										authorlist += authors.getString(i) + " ";
									}
								}
								if(null != imagelinks){
									thumbnail = jsonSTR.getThumbnailFromImageLinks(imagelinks);
								}
								Book book = new Book();
								book.setBookid(isbn);
								book.setIsbn_10(isbn10);
								book.setPublisher(publisher);
								book.setPublished_date(publishedDate);
								book.setCategory(category);
								book.setBooktitle(title);
								book.setBookdesc(description);
								book.setBookauthor(authorlist);
								book.setThumbnail(thumbnail);
								returnJson.put(JsonStrings.BOOK,book);
							}else{
								status = Errorcode.EC_DATA_NOT_FOUND.getValue();
								break;
							}
						}
					}
				}
			}
			
			if(jsonArr == null){
				status = Errorcode.EC_DATA_NOT_FOUND.getValue();
				break;
			}
			
			String response = Errorcode.errmsgstr[status];

			returnJson.put(statusStr, status);
			returnJson.put(errmsgStr, response);
			if(null != bookList && !bookList.isEmpty()){
				returnJson.put(JsonStrings.BOOKCOUNT, bookList.size());
				for(int bookCnt = 0; bookCnt <bookList.size();bookCnt++){
					returnJson.put(JsonStrings.BOOK+bookCnt, bookList.get(bookCnt));
				}
			}
			System.out.println("JSON returnned From BookService : getBookDetails with param1 and Param2 :"+returnJson.toString());
			return returnJson.toString();
			
		}while(iter != 0);
		System.out.println("status:"+status);
		return sendResponse(status);
	}
	
	@POST
	@Path("/savebook")
	public String saveBook(@FormParam("title") String title,
			@FormParam("author") String author,
			@FormParam("description") String desc,
			@FormParam("isbn_13") String id,
			@FormParam("isbn_10") String isbn_10,
			@FormParam("publisher") String publisher,
			@FormParam("publisheddate") String publisheddate,
			@FormParam("category") String category,
			@FormParam("thumbnail") String thumbnail,			
			@FormParam("lname") String lname,
			@FormParam("lcity") String lcity,
			@FormParam("llocality") String llocality,
			//@FormParam("lpin") String lpin,
			@FormParam("offPrice") String offPrice
			)			
	{		
		int status = Errorcode.EC_SUCCESS.getValue();
		System.out.println("thumbnail:"+thumbnail+",publisher:"+publisher+",publisheddate:"+publisheddate+",category:"+category);
		
		/*Get current logged in user object from shiro*/
		User user = UserRealm.getLoggedInUser();
		//System.out.println("aaaa");
		
		JsonStrings jsonSTR = new JsonStrings();
		JSONObject json = jsonSTR.getNewJsonObject();
		
		json.put(JsonStrings.TITLE, title);
		json.put(JsonStrings.AUTHOR, author);
		json.put(JsonStrings.DESCRIPTION, desc);
		json.put(JsonStrings.ISBN_13, id);
		
		/*hidden fields*/
		json.put(JsonStrings.ISBN_10, isbn_10);
		json.put(JsonStrings.PUBLISHER, publisher);
		json.put(JsonStrings.PUBLISHEDDATE, publisheddate);
		json.put(JsonStrings.CATEGORIES, category);
		json.put(JsonStrings.THUMBNAIL, thumbnail);
		
		/*local user details*/
		json.put(JsonStrings.LNAME, lname);
		json.put(JsonStrings.LCITY, lcity);
		json.put(JsonStrings.LLOCALITY, llocality);
		json.put(JsonStrings.OFFPRICE, offPrice);
		
		status = DBFacade.saveBookUser(user, json);
		return sendResponse(status);
	}
	
	@GET
	@Path("/updatepriceofbook/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateThisBooksprice(@PathParam("param1") String bookId, @PathParam("param2") String newPrice)
	{		
		//System.out.println("aaaaa");
		User user = UserRealm.getLoggedInUser();		
		int status = DBFacade.updateNewOfferPrice(user.getEmail(), bookId, newPrice);
		return sendResponse(status);
	}
	
	@GET
	@Path("/getcitysuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCitySuggestion(@QueryParam("phrase") String phrase)
	{			
		List<String> clist = DBFacade.getCityList(phrase);
		JSONArray jsonArr = new JSONArray();
		for(String city:clist)
		{
			JSONObject obj = new JSONObject();
			obj.put("name", city);
			jsonArr.put(obj);
		}		
		System.out.println(jsonArr.toString());
		return jsonArr.toString();
	}
	
	@GET
	@Path("/getbooksuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookSuggestion(@QueryParam("phrase") String phrase)
	{			
		List<String> glist = new ArrayList<String>();
		List<String> tlist = DBFacade.getBookList(phrase, Book.booktitleStr);
		List<String> alist = DBFacade.getBookList(phrase, Book.bookauthorStr);
		List<String> ilist = DBFacade.getBookList(phrase, Book.bookidStr);
		glist.addAll(tlist);
		glist.addAll(alist);
		glist.addAll(ilist);
		
		//System.out.println(glist);
		
		JSONArray jsonArr = new JSONArray();
		for(String str:glist)
		{
			JSONObject obj = new JSONObject();
			obj.put("name", str);
			jsonArr.put(obj);
		}				
		return jsonArr.toString();
	}
	
	@GET
	@Path("/getlocalitysuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLocalitySuggestion(@QueryParam("phrase") String phrase)
	{	
		List<String> clist = DBFacade.getLocalityList(phrase);
		JSONArray jsonArr = new JSONArray();
		for(String city:clist)
		{
			JSONObject obj = new JSONObject();
			obj.put("name", city);
			jsonArr.put(obj);
		}				
		System.out.println(jsonArr.toString());
		return jsonArr.toString();
	}
	
	@POST
	@Path("/brbook")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String saveBrowseBook(
			@FormDataParam("bootfileinput") InputStream uploadedInputStream,
			@FormDataParam("bootfileinput") FormDataContentDisposition fileDetail,
			@FormDataParam("title") String title,
			@FormDataParam("author") String author,
			@FormDataParam("description") String desc,
			@FormDataParam("isbn_13") String id,
			@FormDataParam("isbn_10") String isbn_10,
			@FormDataParam("publisher") String publisher,
			@FormDataParam("publishedDate") String publisheddate,
			@FormDataParam("category") String category,
			@FormDataParam("lname") String lname,
			@FormDataParam("lcity") String lcity,
			@FormDataParam("llocality") String llocality,			
			@FormDataParam("offPrice") String offPrice
			)			
	{		
				
		int status = Errorcode.EC_SUCCESS.getValue();
		int iter = 0;
		do
		{
			try {
				String UPLOAD_FOLDER = this.context.getInitParameter("uploadfolder");

				// check if all form parameters are provided

				if (uploadedInputStream == null || fileDetail == null)
				{	
					status = Errorcode.EC_DATA_NOT_FOUND.getValue();
					break;
					//return Response.status(400).entity("Invalid form data").build();
				}

				// create our destination folder, if it not exists

				createFolderIfNotExists(UPLOAD_FOLDER);								

				String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();

				saveToFile(uploadedInputStream, uploadedFileLocation);				

				/*Actual operation*/
				System.out.println("Image location:" + uploadedFileLocation);
				User user = UserRealm.getLoggedInUser();

				JsonStrings jsonSTR = new JsonStrings();
				JSONObject json = jsonSTR.getNewJsonObject();

				json.put(JsonStrings.TITLE, title);
				json.put(JsonStrings.AUTHOR, author);
				json.put(JsonStrings.DESCRIPTION, desc);
				json.put(JsonStrings.ISBN_13, id);

				/*hidden fields*/
				json.put(JsonStrings.THUMBNAIL, uploadedFileLocation);

				/*local user details*/
				json.put(JsonStrings.LNAME, lname);
				json.put(JsonStrings.LCITY, lcity);
				json.put(JsonStrings.LLOCALITY, llocality);
				json.put(JsonStrings.OFFPRICE, offPrice);


				status = DBFacade.saveBookUser(user, json);
		
			} catch (SecurityException | IOException e) {
				
				/*return Response.status(500)
						.entity("Can not create destination folder on server")
						.build();*/
				status = Errorcode.EC_DATA_NOT_FOUND.getValue();
			}
		}while(iter!=0);
				
		return sendResponse(status);
				
				/*
				return Response.status(200)
						.entity("File saved to" + uploadedFileLocation).build();*/
	}
		
	/**
	 * Utility method to save InputStream data to target location/file
	 * 
	 * @param inStream
	 *            - InputStream to be saved
	 * @param target
	 *            - full path to destination file
	 */
	private void saveToFile(InputStream inStream, String target)
			throws IOException {
		System.out.println("writing to a file");
		
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	
	/**
	 * Creates a folder to desired location if it not already exists
	 * 
	 * @param dirName
	 *            - full path to the folder
	 * @throws SecurityException
	 *             - in case you don't have permission to create the folder
	 */
	private void createFolderIfNotExists(String dirName)
			throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

	public static void sendDetailsOfBookAd(BookAdBean bd){
		System.out.println("called sendDetailsOfBookAd:"+bd.getBooktitle());
		
		int b = UserRealm.sendSelectedBookAdOverEmail("hi", bd.getBuyerEmail(), true);
		int r = UserRealm.sendSelectedBookAdOverEmail("hi", bd.getEmail(), false);
	}
}
