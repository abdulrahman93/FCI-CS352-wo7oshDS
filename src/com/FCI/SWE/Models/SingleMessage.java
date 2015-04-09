package com.FCI.SWE.Models;

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

public class SingleMessage {
	private String toUser;
	private String fromUser;
	private String content;
	
	/**
	 * Constructor accepts user data
	 * 
	 * @param toUser
	 *            user receives friend request
	 * @param fromUser
	 *            user sent friend request
	 * @param content
	 *            content of message
	 */
	public SingleMessage(String toUser, String fromUser, String content) {
		this.toUser = toUser;
		this.fromUser = fromUser;
		this.content = content;

	}

	public String getToUser() {
		return toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getContent() {
		return content;
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
	public static SingleMessage getSingleMes(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new SingleMessage(object.get("toUser").toString(), object.get(
					"fromUser").toString(), object.get("content").toString());
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
	 * @return Constructed user entity
	 */
	
	public static SingleMessage getSingleMes(String toUser, String fromUser) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("SingleM");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("toUser").toString());
			if (entity.getProperty("toUser").toString().equals(toUser)
					&& entity.getProperty("fromUser").toString().equals(fromUser)) {
				SingleMessage returnedReq = new SingleMessage(entity.getProperty(
						"toUser").toString(), entity.getProperty("fromUser")
						.toString(), entity.getProperty("content").toString());
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
	public Boolean saveSingleMessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("SingleM");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity SMessage = new Entity("SingleM", list.size() + 1);

		SMessage.setProperty("toUser", this.toUser);
		SMessage.setProperty("fromUser", this.fromUser);
		SMessage.setProperty("content", this.content);
		datastore.put(SMessage);

		return true;

	}
	
}
