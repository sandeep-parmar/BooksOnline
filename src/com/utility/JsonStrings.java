package com.utility;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonStrings {
	
		public String jsonDetails;

		public static String INAUTHOR			 = "inauthor";
		public static String INTITLE			 = "intitle";
		public static String INISBN		     	 = "isbn";
		
		public static String AUTHOR              = "author";
		public static String AUTHORS             = "authors";
		public static String ITEMS               = "items";
		public static String VOLUMESINFO         = "volumeInfo";
		public static String TITLE               = "title";
		public static String INDUSTRYIDENTIFIERS = "industryIdentifiers";
		public static String TYPE                = "type";
		public static String IDENTIFIER          = "identifier";
		public static String IMAGELINKS          = "imageLinks";
		public static String THUMBNAIL           = "thumbnail";
		public static String DESCRIPTION         = "description";		
		public static String ISBN_10             = "ISBN_10";
		public static String ISBN_13             = "ISBN_13";
		public static String BOOKS               = "books";
		public static String PUBLISHER           = "publisher";
		public static String PUBLISHEDDATE       = "publishedDate";
		public static String CATEGORIES          = "categories";
		
		/*This is required for dbfacade*/
		public static String LNAME          	 = "lname";
		public static String LCITY          	 = "lcity";
		public static String LLOCALITY         	 = "llocaltiy";
		public static String OFFPRICE          	 = "offprice";
		
		public JsonStrings()
		{			
		}
		public JsonStrings(String jsondetails)
		{
			jsonDetails = jsondetails;
		}
		
		public JSONObject getJsonObject()
		{
			return new JSONObject(jsonDetails);
		}
		
		public JSONArray getItemsArray()
		{
			JSONObject json = getJsonObject();
			
			JSONArray itemsArr = null;
			if(json !=null && json.has(JsonStrings.ITEMS))
			{
				itemsArr = json.getJSONArray(JsonStrings.ITEMS);
			}
			return itemsArr;
		}

		public JSONArray getTitlesArrayFromItems(JSONArray itemsArr) {
			JSONArray titles = new JSONArray();
			JSONObject item=null;
			for(int i = 0; i < itemsArr.length(); i++)
			{
				item= itemsArr.getJSONObject(i);
				JSONObject jsonvolume = null;
				if(item != null && item.has(JsonStrings.VOLUMESINFO))
				{
					jsonvolume = item.getJSONObject(JsonStrings.VOLUMESINFO);
					if(jsonvolume != null && jsonvolume.has(JsonStrings.TITLE))
					{
						titles.put(jsonvolume.getString(JsonStrings.TITLE));
					}
				}
			}
			return titles;
		}

		public JSONObject getNewJsonObject() {
			return new JSONObject();
		}

		public JSONObject getIthObjectFromArray(JSONArray jsonArr,int i) {
			JSONObject arr1=null;
			if(jsonArr !=null && jsonArr.length()>0)
			{
			 arr1= jsonArr.getJSONObject(i);
			}
			return arr1;
		}

		public JSONObject getVolumesInfoFromObject(JSONObject arr1) {
			JSONObject jsonvolume = null;
			if(arr1 !=null && arr1.has(JsonStrings.VOLUMESINFO))
			{
				jsonvolume = arr1.getJSONObject(JsonStrings.VOLUMESINFO);
			}
			return jsonvolume;
		}

		public JSONArray getAuthorsArrayFromVolumeObject(JSONObject jsonvolume) {
			JSONArray authors = null;
			if(jsonvolume !=null && jsonvolume.has(JsonStrings.AUTHORS))
			{
				authors = jsonvolume.getJSONArray(JsonStrings.AUTHORS);
			}
			return authors;
		}

		public String getIsbnFromVolumeObject(JSONObject jsonvolume) {
			String isbn=null;
			JSONArray isbns=null;
			if(jsonvolume !=null && jsonvolume.has(JsonStrings.INDUSTRYIDENTIFIERS))
			{
				isbns = jsonvolume.getJSONArray(JsonStrings.INDUSTRYIDENTIFIERS);
				for(int i=0;i<isbns.length();i++)
				{
					JSONObject isbnData = isbns.getJSONObject(i);
					String type = isbnData.getString(JsonStrings.TYPE);
					if(type.equals(JsonStrings.ISBN_13))
					{
						isbn = isbnData.getString(JsonStrings.IDENTIFIER); 
						break;
					}
				}
			}
			return isbn;
		}

		public String getIsbn10FromVolumeObject(JSONObject jsonvolume) {
			String isbn=null;
			JSONArray isbns=null;
			if(jsonvolume !=null && jsonvolume.has(JsonStrings.INDUSTRYIDENTIFIERS))
			{
				isbns = jsonvolume.getJSONArray(JsonStrings.INDUSTRYIDENTIFIERS);
				for(int i=0;i<isbns.length();i++)
				{
					JSONObject isbnData = isbns.getJSONObject(i);
					String type = isbnData.getString(JsonStrings.TYPE);
					if(type.equals(JsonStrings.ISBN_10))
					{
						isbn = isbnData.getString(JsonStrings.IDENTIFIER); 
						break;
					}
				}
			}
			return isbn;
		}
		
		public JSONObject getImageLinksFromVolumeObject(JSONObject jsonvolume) {
			JSONObject imagelinks=null;
			if((jsonvolume != null) && jsonvolume.has(JsonStrings.IMAGELINKS))
			{
				imagelinks = jsonvolume.getJSONObject(JsonStrings.IMAGELINKS);
			}
			return imagelinks;
		}

		public String getThumbnailFromImageLinks(JSONObject imagelinks) {
			String thumbnail = null;
			if(imagelinks != null && imagelinks.has(JsonStrings.THUMBNAIL))
			{
				thumbnail = imagelinks.getString(JsonStrings.THUMBNAIL);
			}
			return thumbnail;
		}

		public String getTitleFromVolumeObject(JSONObject jsonvolume) {
			String title = null;
			if(jsonvolume != null && jsonvolume.has(JsonStrings.TITLE))
			{
				title = jsonvolume.getString(JsonStrings.TITLE);
			}
			return title;
		}

		public String getDescriptionFromVolumeObject(JSONObject jsonvolume) {
			String description = null;
			
			if(jsonvolume != null && jsonvolume.has(JsonStrings.DESCRIPTION))
			{
				description = jsonvolume.getString(JsonStrings.DESCRIPTION);
			}
			return description;
		}
		
		public String getPublisherFromVolumeObject(JSONObject jsonvolume) {
			String publisher = null;
			if(jsonvolume != null && jsonvolume.has(JsonStrings.PUBLISHER))
			{
				publisher = jsonvolume.getString(JsonStrings.PUBLISHER);
			}
			return publisher;
		}
		
		public String getPublishedDateFromVolumeObject(JSONObject jsonvolume) {
			String publishedDate = null;
			if(jsonvolume != null && jsonvolume.has(JsonStrings.PUBLISHEDDATE))
			{
				publishedDate = jsonvolume.getString(JsonStrings.PUBLISHEDDATE);
			}
			return publishedDate;
		}
		
		public String getCategoryFromVolumeObject(JSONObject jsonvolume) {
			String category=null;
			JSONArray categories=null;
			if(jsonvolume !=null && jsonvolume.has(JsonStrings.CATEGORIES))
			{
				categories = jsonvolume.getJSONArray(JsonStrings.CATEGORIES);				
				category = categories.getString(0);				
			}
			return category;
		}
}
