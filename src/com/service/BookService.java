package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dao.BookAdDAO;
import com.dao.BookDao;
@Path("/books")
public class BookService {

	public BookService() {
		// TODO Auto-generated constructor stub
	}
	
	
	private String sendBookRequest(String key, String value)
	{
		String address = "https://www.googleapis.com/books/v1/volumes?q=";
		 String query = key+"="+value;
		 String charset = "UTF-8";
	 
		URL url;
		String str;
		String jsonres="";		
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
		System.out.println("aaaaa author:"+author);
		String jsonres= sendBookRequest("author", author);
		
		JSONObject returnJson = null;
		
		JSONObject json = new JSONObject(jsonres);
		JSONArray itemsArr = null;
		if(json !=null && json.has("items"))
		{
			itemsArr = json.getJSONArray("items");
		}
		
		JSONArray titles = new JSONArray();
		JSONObject item=null;
		for(int i=0;i<itemsArr.length();i++)
		{
			item= itemsArr.getJSONObject(i);
			JSONObject jsonvolume = null;
			if(item != null && item.has("volumeInfo"))
			{
				jsonvolume = item.getJSONObject("volumeInfo");
				if(jsonvolume != null && jsonvolume.has("title"))
				{
					titles.put(jsonvolume.getString("title"));
				}
			}
		}
		
		//System.out.println("booklist:"+titles);
		
		returnJson = new JSONObject();
		returnJson.put("books", titles);
		
		return returnJson.toString();
	}
	
	@GET
	@Path("/getbookinfo/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookDetails(@PathParam("param1") String key, @PathParam("param2") String value)
	{			
		//System.out.println(value);
		String jsonres= sendBookRequest(key, value);
		
		JSONObject returnJson = null;
		
		JSONObject json = new JSONObject(jsonres);
		
		JSONArray jsonArr = null;
		if(json !=null && json.has("items"))
		{
			jsonArr = json.getJSONArray("items");
		}
		
		JSONObject arr1=null;
		if(jsonArr !=null && jsonArr.length()>0)
		{
		 arr1= jsonArr.getJSONObject(0);
		}
		
		JSONObject jsonvolume = null;
		if(arr1.has("volumeInfo"))
		{
			jsonvolume = arr1.getJSONObject("volumeInfo");
		}
		
		JSONArray authors = null;
		if(jsonvolume.has("authors"))
		{
			authors = jsonvolume.getJSONArray("authors");
		}
		String isbn=null;
		JSONArray isbns=null;
		if(jsonvolume.has("industryIdentifiers"))
		{
			isbns = jsonvolume.getJSONArray("industryIdentifiers");
			for(int i=0;i<isbns.length();i++)
			{
				JSONObject isbnData = isbns.getJSONObject(i);
				String type = isbnData.getString("type");
				if(type.equals("ISBN_13"))
				{
					isbn = isbnData.getString("identifier"); 
				}
			}
			//System.out.println("isbn:"+isbn);
		}
		
		JSONObject imagelinks=null;
		if((jsonvolume != null) && jsonvolume.has("imageLinks"))
		{
			//System.out.println("aaaaa1");
			imagelinks = jsonvolume.getJSONObject("imageLinks");
			//System.out.println("aaaaa2");
		}
		
		String authorlist="";
		for(int i=0;i<authors.length();i++)
		{
			authorlist += authors.getString(i)+" ";
		}					
		
		
		String thumbnail = null;
		if(imagelinks != null && imagelinks.has("thumbnail"))
		{
			//System.out.println("aaaaaaaa3");
			thumbnail = imagelinks.getString("thumbnail");
			//System.out.println("aaaaaaaa4");
		}
		
		String title = null;
		if(jsonvolume.has("title"))
		{
			title = jsonvolume.getString("title");
		}
		
		
		String description = null;
		
		if(jsonvolume.has("description"))
		{
			description = jsonvolume.getString("description");
		}
		
		returnJson = new JSONObject();
		returnJson.put("title", title);
		returnJson.put("author", authorlist);
		returnJson.put("description", description);
		returnJson.put("thumbnail", thumbnail);
		returnJson.put("isbn", isbn);
		//System.out.println("authorlist:"+authorlist+"title:"+title+",description:"+description);
		return returnJson.toString();
	}
	
	@POST
	@Path("/savebook")
	public void saveBook(@FormParam("title") String booktitle,
			@FormParam("author") String bookauthor,
			@FormParam("description") String bookdesc,
			@FormParam("isbn") String bookid,
			@FormParam("thumbnail") String thumbnail,
			@FormParam("userMobile") String userMobile,
			@FormParam("userEmail") String userEmail,
			@FormParam("offPrice") String offerPrice,
			@FormParam("prefLoc") String preferredLoc
			)			
	{
		
		//System.out.println(title+", "+author+","+description+","+isbn+","+thumbnail);
		String isbn = BookDao.saveBook(bookid, booktitle, bookauthor, bookdesc, thumbnail);
		isbn = BookAdDAO.saveBookAd(isbn, userMobile, userEmail, offerPrice, preferredLoc);
		
	}
}
