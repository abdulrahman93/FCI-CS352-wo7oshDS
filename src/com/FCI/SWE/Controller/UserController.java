package com.FCI.SWE.Controller;

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
import com.FCI.SWE.Models.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Wo7oshDS
 * @version 1.8
 * @since 2015-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController {
	//String link = "http://1-dot-swe2-social.appspot.com/rest/";
	String link = "http://localhost:8888/rest/";
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return entry point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	
	/**
	 * Action function to response to link to send individual message
	 * controller part, it will calls send message service
	 *
	 
	@GET
	@Path("/home/message")
	public Response imessage() {
		return Response.ok(new Viewable("/jsp/message")).build();
	}
	*/
	
	 /**
	 * Action function to response to link to send individual message
	 * controller part, it will calls send message service
	 */
	 
	@GET
	@Path("/home/notifications")
	public Response notifications() {
		return Response.ok(new Viewable("/jsp/notifications")).build();
	}
	
	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/RegistrationService";
		String serviceUrl = link+"RegistrationService";
		//System.out.println(" "+serviceUrl);
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		try{
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "Registered Successfully";
		}  catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/LoginService";
		String serviceUrl = link+"LoginService";
		
		System.out.println(" "+serviceUrl);
		
		String urlParameters = "uname=" + uname + "&password=" + pass;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}
	
	/**
	 * Action function to response to add freind request, This function will act as
	 * a controller part and it will calls FreindReqService to make
	 * request
	 * 
	 * @param toUser
	 *            provided requested user
	 * @param fromUser
	 *            provided requesting user
	 * @return Status string
	 * 			  Assuring that request has been sent
	 */
	@POST
	@Path("/home/pending/")
	@Produces(MediaType.TEXT_PLAIN)
	public String pending(@FormParam("toUser") String toUser,
			@FormParam("fromUser") String fromUser) {
		String serviceUrl = link+"FreindReqService";
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/FreindReqService";
		//URL url = new URL(serviceUrl);
		String urlParameters = "toUser=" + toUser + "&fromUser=" + fromUser;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Friend Request sent";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}
	
	/**
	 * Action function to response to accept friendship request, This function will act as
	 * a controller part and it will calls FreindsService to make
	 * acceptance
	 * 
	 * @param toUser
	 *            provided requested user
	 * @param fromUser
	 *            provided requesting user
	 * @return Status string
	 * 			  Assuring that status of friendship between them is exist
	 */
	@POST
	@Path("/home/friends")
	@Produces(MediaType.TEXT_PLAIN)
	public String friends(@FormParam("toUser") String toUser,
			@FormParam("fromUser") String fromUser) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/FreindsService";
		
		String serviceUrl = link+"FreindsService";
		//URL url = new URL(serviceUrl);
		String urlParameters = "toUser=" + toUser + "&fromUser=" + fromUser;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "You and  "+fromUser+"  are now friends";
		}  catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}
	
	/**
	 * Action function to response to send individual message. This function will act as a
	 * controller part, it will calls sender service to send message
	 *
	 * @param email
	 *            provided user email
	 * @return Individual message page view
	 */
	@POST
	@Path("/home/message/")
	@Produces("text/html")
	public Response message(@FormParam("email") String email) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/senderService";
		String serviceUrl = link+"senderService";
		String urlParameters = "email=" + email;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			System.out.println("hello message post fun "+email);
			//URL url = new URL(serviceUrl);
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			if (object.get("Status").equals("Failed"))
				return null;
			
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/message", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Action function to assure sending individual message, This function will act as
	 * a controller part and it will calls SendIMessage to send
	 * message
	 * 
	 * @param toUser
	 *            provided requested user
	 * @param fromUser
	 *            provided requesting user
	 * @return Status string
	 * 			  Ensure sending the message
	 */
	@POST
	@Path("/home/message/isent")
	@Produces(MediaType.TEXT_PLAIN)
	public String isent(@FormParam("toUser") String toUser,
			@FormParam("fromUser") String fromUser, @FormParam("content") String content) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/SendIMessage";
		String serviceUrl = link+"SendIMessage";
		String urlParameters = "toUser=" + toUser + "&fromUser=" + fromUser
				+ "&content=" + content;
		System.out.println("here imessage: "+fromUser);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Your message has been sent";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}
	
	/**
	 * Action function to create group message. This function will act as a
	 * controller part, it will calls group message service to send content
	 *
	 * @param email
	 *            provided user email
	 * @return Group message page view
	 */
	@POST
	@Path("/home/gmessage/")
	@Produces("text/html")
	public Response gmessage(@FormParam("email") String email) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/senderService";
		String serviceUrl = link+"senderService";
		String urlParameters = "email=" + email;
		System.out.println("hello message post fun "+email);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			if (object.get("Status").equals("Failed"))
				return null;
			
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/gmessage", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Action function to create post. This function will act as a
	 * controller part, it will calls sender service to check user data and get
	 * user from datastore
	 *
	 * @param email
	 *            provided user email
	 * @return created post page view
	 */
	@POST
	@Path("/home/makePost/")
	@Produces("text/html")
	public Response createPost(@FormParam("email") String email) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/senderService";
		String serviceUrl = link+"senderService";
		String urlParameters = "email=" + email;
		System.out.println("hello message post fun "+email);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			if (object.get("Status").equals("Failed"))
				return null;
			
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/makePost", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Action function to assure post creation. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 *
	 * @param owner
	 *            post creator
	 * @param content
	 *            post content
	 * @param status
	 *            feeling what
	 * @param privacy
	 *            post privacy
	 * @return String status
	 */
	@POST
	@Path("/home/makePost/postCreated")
	@Produces(MediaType.TEXT_PLAIN)
	public String PostCreated(@FormParam("owner") String owner, 
			@FormParam("content") String content, @FormParam("status") String status,
			@FormParam("privacy") String privacy) {
		//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/postCreated";
		String serviceUrl = "http://localhost:8888/rest/postCreated";
		String urlParameters = "owner=" + owner + "&content=" + content
				+ "&status=" + status + "&privacy=" + privacy;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			System.out.println("hello post fun "+owner);
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			if (object.get("Status").equals("OK"))
				return "Your Post has been created";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}
	
	/**
	 * Action function to show timeline. This function will act as a
	 * controller part, it will calls sender service to check user data and get
	 * user from datastore
	 *
	 * @param email
	 *            provided user email
	 * @return timeline page view
	 */
	@POST
	@Path("/home/timeline/")
	@Produces("text/html")
	public Response timeline(@FormParam("email") String email) {
				//String serviceUrl = "http://1-dot-swe2-social.appspot.com/rest/senderService";
				String serviceUrl = link+"senderService";
				String urlParameters = "email=" + email;
				String retJson = Connection.connect(serviceUrl, urlParameters, "POST", 
						"application/x-www-form-urlencoded;charset=UTF-8");
				try {
					System.out.println("hello message timeline fun "+email);
					
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(retJson);
					JSONObject object = (JSONObject) obj;
					
					if (object.get("Status").equals("Failed"))
						return null;
					
					Map<String, String[]> map = new HashMap<String, String[]>();
					UserEntity user = UserEntity.getUser(object.toJSONString());
					
					System.out.println("here user "+ user.getName());
					
					ArrayList<String> friends = ReqForm.getFriends(user);
					friends.add(user.getName());
					
					System.out.println("sz "+friends.size());
					for(String f :friends){
						Post p = Post.getPost2(f);
						
						if(p != null){
							System.out.println("p "+p.getOwner());
							map.put(p.getOwner(), new String []{p.getOwner()+" wrote: \"", 
								p.getContent()+"\" ", ", feeling: "+p.getStat(),
								"  , privacy: "+p.getPrivacy()} );
							/*
							map.put("content", p.getContent());
							map.put("status", p.getStat());
							map.put("privacy", p.getPrivacy());
							*/
						}
					}
					System.out.println("here final "+map.size());
					return Response.ok(new Viewable("/jsp/timeline", map)).build();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
	}


}