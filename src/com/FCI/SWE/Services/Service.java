package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.ReqForm;
import com.FCI.SWE.Models.SingleMessage;
import com.FCI.SWE.Models.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Wo7oshDS
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	/**
	 * Login Rest Service, this service will be called to make freind
	 * request process, store request in data store.
	 * @param toUser provided requested user
	 * @param fromUser provided requesting user
	 * @return request in json format
	 */
	@POST
	@Path("/FreindReqService")
	public String FreindReqService(@FormParam("toUser") String toUser,
		@FormParam("fromUser") String fromUser) {
		ReqForm req = new ReqForm(toUser, fromUser, "pending");
		
		req.saveReq();
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		/*
		object.put("toUser", req.getToUser());
		object.put("fromUser", req.getFromUser());
		object.put("stat", req.getStat());
		*/
		return object.toString();

	}
	
	/**
	 * Login Rest Service, this service will be called to make freind
	 * request process, store request in data store.
	 * @param toUser provided requested user
	 * @param fromUser provided requesting user
	 * @return request in json format
	 */
	@POST
	@Path("/FreindsService")
	public String FreindsService(@FormParam("toUser") String toUser,
		@FormParam("fromUser") String fromUser) {
		ReqForm req = ReqForm.getReq(toUser, fromUser);
		
		req.update();
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		return object.toString();

	}
	
	/**
	 * Login Rest Service, this service will be called to make freind
	 * request process, store request in data store.
	 * @param toUser provided requested user
	 * @param fromUser provided requesting user
	 * @return request in json format
	 */
	@POST
	@Path("/SendIMessage")
	public String SendIMessage(@FormParam("toUser") String toUser,
		@FormParam("fromUser") String fromUser, @FormParam("content") String content) {
		System.out.println("hlooo: "+fromUser);
		SingleMessage sm = new SingleMessage(toUser, fromUser, content);
		
		sm.saveSingleMessage();
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		return object.toString();

	}
	
	/**
	 * Login Rest Service, this service will be called to make freind
	 * request process, store request in data store.
	 * @param toUser provided requested user
	 * @param fromUser provided requesting user
	 * @return request in json format
	 */
	@POST
	@Path("/senderService")
	public String senderService(@FormParam("email") String email) {
		System.out.println("hereSender: "+email);
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser2(email);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	/**
	 * Login Rest Service, this service will be called to make freind
	 * request process, store request in data store.
	 * @param toUser provided requested user
	 * @param fromUser provided requesting user
	 * @return request in json format
	 */
	@POST
	@Path("/postCreated")
	public String postCreated(@FormParam("owner") String owner,
		@FormParam("content") String content, @FormParam("status") String status,
		@FormParam("privacy") String privacy) {
		Post p = new Post(owner, content, status, privacy);
		
		p.savePost();
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		/*
		object.put("toUser", req.getToUser());
		object.put("fromUser", req.getFromUser());
		object.put("stat", req.getStat());
		*/
		return object.toString();

	}

}