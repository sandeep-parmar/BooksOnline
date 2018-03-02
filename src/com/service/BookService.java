package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.security.auth.Subject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bean.Book;
import com.bean.User;
import com.dao.BookAdDAO;
import com.dao.BookDao;
import com.dao.DBFacade;
import com.dao.UserRealm;
import com.utility.*;

@Path("/books")
public class BookService {

	public BookService() {
		
	}
	
	private String sendBookRequest(String key, String value)
	{
		String address = "https://www.googleapis.com/books/v1/volumes?q=";
		 String query = key + "=" + value;
		 String charset = "UTF-8";
	 
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
			e.printStackTrace();
		}
		return jsonres;
	}

	@GET
	@Path("/getbooklist/{author}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookList(@PathParam("author") String author)
	{
		
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
	public void setSoldStatusTrue(@PathParam("param1") String bookId)
	{
		System.out.println(bookId);
		User user = UserRealm.getLoggedInUser();
		DBFacade.setSoldStatusTrue(user.getMobile(), bookId);
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
		
		DBFacade.saveBookUser(user, title, author, desc, id, thumbnail, lcity, llocality, lpin, lname, lphno , offPrice);
	}
}
