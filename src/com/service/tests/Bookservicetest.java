/*Enable assertions in run configurations*/

package com.service.tests;

import java.io.IOException;

import javax.ws.rs.FormParam;

import org.json.JSONObject;

import com.utility.JsonStrings;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Bookservicetest {

	String title = "abc";
	String author="abc";
	String description="abc";
	String isbn="12345";
	String thumbnail="www.bookscom/abc";
	String lname = "testuser";
	//String lphno = "1234567890";
	String lcity = "abc";
	String llocality = "abc";
	//String lpin = "444444";
	String offprice = "123";
	
	
	public Bookservicetest() {
		// TODO Auto-generated constructor stub
	}
	void getbooklist()
	{
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/books/getbooklist/robin%20sharma")
		  .get()		
		  .addHeader("postman-token", "5d8c32b0-5557-f5e2-eade-ec7bfe42130c")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert json.get("books") != null:"Got null response";
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
	}
	void getbookinfo(String key, String value)
	{
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/books/getbookinfo/"+key+"/"+value)
		  .get()		
		  .addHeader("postman-token", "5d8c32b0-5557-f5e2-eade-ec7bfe42130c")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert json.get("thumbnail") != null:"Got null response";
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	void savebook()
	{
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "title="+title
														+ "&author="+author
														+ "&description="+description
														+ "&isbn="+isbn
														+ "&thumbnail="+thumbnail
														+ "&lname="+lname														
														+ "&lcity="+lcity
														+ "&llocality="+llocality														
														+ "&offPrice="+offprice);
		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/books/savebook")
		  .post(body)
		  .addHeader("content-type", "application/x-www-form-urlencoded")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "d60ccbd7-97bf-62f6-0248-ddbec90b5b99")
		  .build();
		
		Response response = null;
		try {
			response = client.newCall(request).execute();
			String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"savebook failed "+json.get("errmsg");
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	void updatebookprice()
	{
		OkHttpClient client = new OkHttpClient();

		String newprice = "150";
		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/books/updatepriceofbook/"+isbn+"/"+newprice)
		  .get()		
		  .addHeader("postman-token", "5d8c32b0-5557-f5e2-eade-ec7bfe42130c")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"updatepriceofbook failed with an error"+json.get("errmsg");
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	void soldbook()
	{
		OkHttpClient client = new OkHttpClient();

		String newprice = "150";
		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/books/soldthisbook/"+isbn)
		  .get()		
		  .addHeader("postman-token", "5d8c32b0-5557-f5e2-eade-ec7bfe42130c")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"soldbook failed with an error"+json.get("errmsg");
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {		
		Bookservicetest test = new Bookservicetest();
		//test.invokeSaveBookHundredTimes();
		test.getbooklist();
		test.getbookinfo("title", "harry potter");
		test.getbookinfo("isbn", "9781137490957");
		test.savebook();
		test.updatebookprice();
		test.soldbook();
		
	}
}
