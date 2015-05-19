package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>Request Friend class</h1>
 * <p>
 * This class will save friend requests, it will holds request data
 * </p>
 *
 * @author Wo7oshDS
 * @version 1.8
 * @since 2015-03-9
 */
public class ReqForm {
	private String toUser;
	private String fromUser;
	private String status;
	
	/**
	 * Constructor accepts user data
	 * 
	 * @param toUser
	 *            user receives friend request
	 * @param fromUser
	 *            user sent friend request
	 * @param status
	 *            pending or became friends
	 */
	public ReqForm(String toUser, String fromUser, String status) {
		this.toUser = toUser;
		this.fromUser = fromUser;
		this.status = status;

	}

	public String getToUser() {
		return toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getStat() {
		return status;
	}
	
	/**
	 * 
	 * This static method will form ReqForm class using json format contains
	 * request data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed Request form
	 */
	public static ReqForm getReq(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new ReqForm(object.get("toUser").toString(), object.get(
					"fromUser").toString(), object.get("status").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 
	 * This static method will get Request class using only email
	 * This method will serach for request in datastore
	 * 
	 * @param email
	 *            user email
	 * @return Constructed request form
	 */
	
	public static ReqForm getReq(String toUser, String fromUser) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		//make connection
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("toUser").toString());
			//check for specific users
			if (entity.getProperty("toUser").toString().equals(toUser)
					&& entity.getProperty("fromUser").toString().equals(fromUser)) {
				ReqForm returnedReq = new ReqForm(entity.getProperty(
						"toUser").toString(), entity.getProperty("fromUser")
						.toString(), entity.getProperty("status").toString());
				return returnedReq;
			}
		}

		return null;
	}
	
	/**
	 * This method will be used to save request in datastore
	 * 
	 * @return boolean if request is saved correctly or not
	 */
	public Boolean saveReq() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		addDatastore(list, datastore, this.status);
		/*
		Entity req = new Entity("requests", list.size() + 1);

		req.setProperty("toUser", this.toUser);
		req.setProperty("fromUser", this.fromUser);
		req.setProperty("status", this.status);
		datastore.put(req);
		*/

		return true;

	}
	
	/**
	 * 
	 * This static method will change status of request to freinds
	 * This method will serach for request in datastore
	 * 
	 * 
	 * @return boolean in case change is correct
	 */
	
	public Boolean update() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("toUser").toString());
			if (entity.getProperty("toUser").toString().equals(toUser)
					&& entity.getProperty("fromUser").toString().equals(fromUser)) {
				datastore.delete(entity.getKey());
				break;
			}
		}
		
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		addDatastore(list, datastore, "Friends");
		/*
		Entity req = new Entity("requests", list.size() + 1);

		req.setProperty("toUser", this.toUser);
		req.setProperty("fromUser", this.fromUser);
		req.setProperty("status", "Friends");
		datastore.put(req);
		*/

		return true;
	}
	
	/**
	 * 
	 * This static method will get Request class using only email
	 * This method will serach for request in datastore
	 * 
	 * @param email
	 *            user email
	 * @return Array List of strings
	 */
	
	public static ArrayList<String> getFriends(UserEntity user) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		ArrayList<String> rf = new ArrayList<String>();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			//System.out.println(entity.getProperty("toUser").toString());
			if ((entity.getProperty("toUser").toString().equals(user.getName())
					&& entity.getProperty("status").toString().equals("Friends"))) {
				rf.add(entity.getProperty("fromUser").toString());
			}
			else if(entity.getProperty("fromUser").toString().equals(user.getName())){
				rf.add(entity.getProperty("toUser").toString());
			}
		}
		
		if(rf.size() > 0)
			return rf;
		
		return null;
	}
	
	public void addDatastore(List<Entity> list, DatastoreService ds, String status){
		Entity req = new Entity("requests", list.size() + 1);

		req.setProperty("toUser", this.toUser);
		req.setProperty("fromUser", this.fromUser);
		req.setProperty("status", status);
		ds.put(req);
	}
	
}
