package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.connection.ConnectionHandler;
import com.dao.FacadeDB;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

@Path("/books")
public class BookService {

	public BookService() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("/getbookinfo/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookDetails(@PathParam("param1") String isbn)
	{		
		 String address = "https://www.googleapis.com/books/v1/volumes?q=";
		 String query = "title="+isbn;
		 String charset = "UTF-8";
	 
		URL url;
		String str;
		String jsonres="";
		JSONObject returnJson = null;
		try {
			url = new URL(address + URLEncoder.encode(query, charset));
		
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
			while ((str = in.readLine()) != null) {			
				jsonres+=str;
			}
		
		JSONObject json = new JSONObject(jsonres);
		
		JSONArray jsonArr = null;
		if(json.has("items"))
		{
			jsonArr = json.getJSONArray("items");
		}
		
		JSONObject arr1=null;
		if(jsonArr.length()>0)
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
		
		JSONObject imagelinks=null;
		if(jsonvolume.has("imageLinks"))
		{
			imagelinks = jsonvolume.getJSONObject("imageLinks");
		}
		
		String authorlist="";
		for(int i=0;i<authors.length();i++)
		{
			authorlist += authors.getString(i)+" ";
		}					
		
		String thumbnail = null;
		if(imagelinks.has("thumbnail"))
		{
			thumbnail = imagelinks.getString("thumbnail");
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
		//System.out.println("authorlist:"+authorlist+"title:"+title+",description:"+description);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return returnJson.toString();
	}
	
	@POST
	@Path("/savebook")
	public void saveBook(@FormParam("title") String booktitle,
			@FormParam("author") String bookauthor,
			@FormParam("description") String bookdesc,
			@FormParam("isbn") String bookid,
			@FormParam("thumbnail") String thumbnail
			)			
	{
		
		//System.out.println(title+", "+author+","+description+","+isbn+","+thumbnail);
		FacadeDB.saveBook(bookid, booktitle, bookauthor, bookdesc, thumbnail);
	}
}
