package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Post {
	private String owner;
	private String content;
	private String status;
	private String privacy;
	
	/**
	 * Constructor accepts user data
	 * 
	 * @param owner
	 *            who made post
	 * @param content
	 *            what in post
	 *
	 * @param status
	 *            what feeling do you have currently
	 * @param privacy
	 * 			  state privacy of post
	 */
	public Post(String owner, String content, String status, String privacy) {
		this.owner = owner;
		this.content = content;
		this.status = status;
		this.privacy = privacy;

	}

	public String getOwner() {
		return owner;
	}

	public String getContent() {
		return content;
	}

	public String getStat() {
		return status;
	}
	
	public String getPrivacy() {
		return privacy;
	}
	
	/**
	 * 
	 * This static method will form Post class using json format contains
	 * Post data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed Post form
	 */
	public static Post getPost(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new Post(object.get("owner").toString(), object.get(
					"content").toString(), object.get("status").toString(), 
					object.get("privacy").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 
	 * This static method will form Post class by getting owner's name contains
	 * Post data
	 * 
	 * @param owner
	 *            String in json format contains user data
	 * @return Constructed Post
	 */
	public static Post getPost2(String owner) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("privacy").toString());
			if (entity.getProperty("owner").toString().equals(owner)
					//&& (!(entity.getProperty("privacy").toString().equals("private"))) 
					) {
				System.out.println("Hello cond");
				Post returnedPost = new Post(entity.getProperty(
						"owner").toString(), entity.getProperty("content")
						.toString(), entity.getProperty("status").toString(), 
						entity.getProperty("privacy").toString());
				
				return returnedPost;
			}
		}
		return null;
	}
	
	/**
	 * This method will be used to save Post in datastore
	 * 
	 * @return boolean if Post is saved correctly or not
	 */
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity Post = new Entity("Post", list.size() + 1);
		
		if(!this.owner.isEmpty() && !this.content.isEmpty() && !this.status.isEmpty() &&
				!this.privacy.isEmpty()){
		Post.setProperty("owner", this.owner);
		Post.setProperty("content", this.content);
		Post.setProperty("status", this.status);
		Post.setProperty("privacy", this.privacy);
		datastore.put(Post);
		
		return true;
		}

		return false;

	}
	
}
