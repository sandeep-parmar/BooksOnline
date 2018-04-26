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

	public BookService() {
		
	}
	
	private String sendBookRequest(String key, String value)
	{
		String address = "https://www.googleapis.com/books/v1/volumes?q=";
		String query = key + "=" + value;
		String charset = "UTF-8";
	 
		int status = Errorcode.EC_SUCCESS.getValue();
		
		URL url;
		String str;
		String jsonres = "";		
		try {
			url = new URL(address + URLEncoder.encode(query, charset));
		
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
			while ((str = in.readLine()) != null) {			
				jsonres+=str;
			}		
		}catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			status = Errorcode.EC_FILE_READ_FAILED.getValue();			
		}
		return jsonres;
	}

	@GET
	@Path("/getbooklist/{author}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookList(@PathParam("author") String author)
	{
		int status = Errorcode.EC_SUCCESS.getValue();
		String jsonres= sendBookRequest(JsonStrings.AUTHOR, author);
		
		JsonStrings jsonSTR = new JsonStrings(jsonres);

		JSONArray itemsArr = jsonSTR.getItemsArray();
		JSONArray titles = jsonSTR.getTitlesArrayFromItems(itemsArr);

		JSONObject returnJson = jsonSTR.getNewJsonObject();
		returnJson.put(JsonStrings.BOOKS, titles);
		return returnJson.toString();
	}
	

	@GET
	@Path("/soldthisbook/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	public String setSoldStatusTrue(@PathParam("param1") String bookId)
	{
		System.out.println(bookId);
		User user = UserRealm.getLoggedInUser();
		DBFacade.setSoldStatusTrue(user.getMobile(), bookId);
		return "Success";
	}
	
	
	@GET
	@Path("/getbookinfo/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookDetails(@PathParam("param1") String key, @PathParam("param2") String value)
	{			
		String jsonres= sendBookRequest(key, value);
		
		JsonStrings jsonSTR = new JsonStrings(jsonres);
		
		JSONObject returnJson = null;
		
		JSONArray jsonArr = jsonSTR.getItemsArray();
		
		JSONObject obj0 = jsonSTR.getIthObjectFromArray(jsonArr, 0);
		
		JSONObject jsonvolume = jsonSTR.getVolumesInfoFromObject(obj0);
		
		JSONArray authors = jsonSTR.getAuthorsArrayFromVolumeObject(jsonvolume);
		JSONObject imagelinks = jsonSTR.getImageLinksFromVolumeObject(jsonvolume);
		
		String authorlist = "";
		for(int i = 0; i < authors.length(); i++)
		{
			authorlist += authors.getString(i) + " ";
		}
		
		String isbn = jsonSTR.getIsbnFromVolumeObject(jsonvolume);
		
		String title = jsonSTR.getTitleFromVolumeObject(jsonvolume);
		
		String thumbnail = jsonSTR.getThumbnailFromImageLinks(imagelinks);
		
		String description = jsonSTR.getDescriptionFromVolumeObject(jsonvolume);
		
		returnJson = jsonSTR.getNewJsonObject();
		
		returnJson.put(JsonStrings.TITLE, title);
		returnJson.put(JsonStrings.AUTHOR, authorlist);
		returnJson.put(JsonStrings.DESCRIPTION, description);
		returnJson.put(JsonStrings.THUMBNAIL, thumbnail);
		returnJson.put(JsonStrings.ISBN, isbn);
		return returnJson.toString();
	}
	
	@POST
	@Path("/savebook")
	public void saveBook(@FormParam("title") String title,
			@FormParam("author") String author,
			@FormParam("description") String desc,
			@FormParam("isbn") String id,
			@FormParam("thumbnail") String thumbnail,
			@FormParam("lname") String lname,
			@FormParam("lphno") String lphno,
			@FormParam("lcity") String lcity,
			@FormParam("llocality") String llocality,
			@FormParam("lpin") String lpin,
			@FormParam("offPrice") String offPrice
			)			
	{
		//System.out.println(title+" "+author+" "+desc+" "+id+" "+thumbnail+" "+lname+" "+lphno+" "+lcity+" "+llocality+" "+lpin+" "+offPrice);
		
		/*Get current logged in user object from shiro*/
		User user = UserRealm.getLoggedInUser();
		
		DBFacade.saveBookUser(user, title, author, desc, id, thumbnail, lpin, lcity, llocality, lname, lphno , offPrice);
	}
	
	@GET
	@Path("/updatepriceofbook/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateThisBooksprice(@PathParam("param1") String bookId, @PathParam("param2") String newPrice)
	{
		System.out.println(bookId+","+newPrice);
		User user = UserRealm.getLoggedInUser();
		//DBFacade.setSoldStatusTrue(user.getMobile(), bookId);
		DBFacade.updateNewOfferPrice(user.getMobile(), bookId, newPrice);
		return "success";
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
		return jsonArr.toString();
	}
	
	@GET
	@Path("/getbooksuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookSuggestion(@QueryParam("phrase") String phrase)
	{			
		List<String> glist = new ArrayList<String>();
		List<String> tlist = DBFacade.getBookList(phrase, "booktitle");
		List<String> alist = DBFacade.getBookList(phrase, "bookauthor");
		List<String> ilist = DBFacade.getBookList(phrase, "bookid");
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
		return jsonArr.toString();
	}
	
	@POST
	@Path("/brbook")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response saveBrowseBook(
			@FormDataParam("bootfileinput") InputStream uploadedInputStream,
			@FormDataParam("bootfileinput") FormDataContentDisposition fileDetail,
			@FormDataParam("brtitle") String title,
			@FormDataParam("brauthor") String author,
			@FormDataParam("brdesc") String desc,
			@FormDataParam("brisbn") String id,		
			@FormDataParam("brlname") String lname,
			@FormDataParam("brlphno") String lphno,
			@FormDataParam("brlcity") String lcity,
			@FormDataParam("brllocality") String llocality,
			@FormDataParam("brlpin") String lpin,
			@FormDataParam("broffPrice") String offPrice
			)			
	{		
		System.out.println("title:"+title+" , author:"+author+ ", desc"+desc);
		System.out.println("isbn"+ id);
		System.out.println("lname"+ lname);
		System.out.println("lphno"+ lphno);
		System.out.println("lcity"+ lcity);
		System.out.println("locality"+ llocality);
		System.out.println("lpin"+ lpin);
		System.out.println("offer price"+ offPrice);
		System.out.println("file name"+ fileDetail.getFileName());
//		String uploadDirectory = (String) context.getInitParameter("dataStoragePath");
		String UPLOAD_FOLDER = this.context.getInitParameter("uploadfolder");
		
		// check if all form parameters are provided
				
				if (uploadedInputStream == null || fileDetail == null)
					return Response.status(400).entity("Invalid form data").build();
				
				// create our destination folder, if it not exists
				try {
					
					createFolderIfNotExists(UPLOAD_FOLDER);
					
				} catch (SecurityException se) {
					
					return Response.status(500)
							.entity("Can not create destination folder on server")
							.build();
				}
				
				String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
				
				try {
				
					saveToFile(uploadedInputStream, uploadedFileLocation);
				} catch (IOException e) {
					
					return Response.status(500).entity("Can not save file").build();
				}
				
				/*Actual operation*/
				System.out.println("Image location:" + uploadedFileLocation);
				User user = UserRealm.getLoggedInUser();
				DBFacade.saveBookUser(user, title, author, desc, id, uploadedFileLocation, lpin, lcity, llocality, lname, lphno , offPrice);
				
				return Response.status(200)
						.entity("File saved to " + uploadedFileLocation).build();
			
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
