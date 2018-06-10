package com.service.tests;

import java.io.IOException;

import org.json.JSONObject;

import com.bean.User;
import com.dao.UserDao;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Loginservicetest {

	String testusername = "testuser";
	//String testmobile = "9011916677";
	String testemail = "testuser@gmailabc.com";
	String testpassword = "test@123";
	String testpasswordnew = "test.123";
	
	public Loginservicetest() {
		// TODO Auto-generated constructor stub
	}

	void testsignup()
	{
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		String jsondata = "{\"username\":\""+ testusername + "\",\"email\":\"" + testemail + "\",\"password\":\""+ testpassword + "\"}";
		System.out.println(jsondata);
		RequestBody body = RequestBody.create(mediaType, jsondata);
		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/login/saveuser")
		  .post(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "166e1496-82b7-a0e5-5ff0-dd1a1ac07278")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			//System.out.println(response.body().string());
			String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"user signup failed "+json.get("errmsg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void testverify()
	{
		User user = new UserDao().getUserAccount(User.emailStr, testemail);
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/login/verify/" + testusername + "/" + user.getUuid())
		  .get()		  
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "7ca97016-7bad-5127-a13e-2cf89fe22960")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			System.out.println(response.body().string());
			/*String jsonstr = response.body().string();
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"user verification failed "+json.get("errmsg");*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void testlogin()
	{
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"email\":\"" + testemail + "\",\"password\":\""+testpassword+"\"}");
		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/login/validate")
		  .post(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "e4402c9e-1cf7-1db2-ba3d-20ab23b1590e")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();			
			String jsonstr = response.body().string();
			System.out.println(jsonstr);
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"user login failed "+json.get("errmsg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void profileupdate()
	{
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"username\":\"" + testusername + "\",\"email\":null}");
		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/login/profileupdate")
		  .post(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "7cb12336-509e-2711-4276-6a75d3d56b42")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();			
			String jsonstr = response.body().string();
			System.out.println(jsonstr);
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"user profile update failed "+json.get("errmsg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void resetpassword()
	{
		User user = new UserDao().getUserAccount(User.emailStr, testemail);
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://localhost:8080/BooksOnline/rest/login/resetpassword/" + user.getUuid() + "/" +testpasswordnew)
		  .get()
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "385e8eac-a974-8318-0256-d5f20ccde84c")
		  .build();

		Response response = null;
		try {
			response = client.newCall(request).execute();			
			String jsonstr = response.body().string();
			System.out.println(jsonstr);
			JSONObject json = new JSONObject(jsonstr);
			assert (Integer)json.get("status") == 0:"user password reset failed "+json.get("errmsg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Loginservicetest test = new Loginservicetest();
		test.testsignup();
		test.testverify();
		test.testlogin();
		test.profileupdate();
		test.resetpassword();
	}
}
