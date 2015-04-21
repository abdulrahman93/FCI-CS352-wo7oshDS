<%@page import="com.FCI.SWE.Models.UserEntity"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@page import="com.google.appengine.api.datastore.Query" %>
<%@page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@page import="com.google.appengine.api.datastore.Entity" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>
<style>

body {
    background-color: #d0e4fe;
}

p {
    font-family: "sans-serif";
    font-size: larger;
}
</style>

<!-- 
<link rel="stylesheet" type="text/css" href="/SocialNetwork/styles.css" />
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<link rel="stylesheet" type="text/css" href="/war/styles.css" />
<link rel="stylesheet" type="text/css" href="/war/jsp/styles.css" />
-->

</head>


<form action="/social/login" method="GET" style="text-align: right;">
<input type="submit" value="Signout">
</form>

<body bgcolor="#9966FF">

<%//String userName= UserEntity.getCurrentActiveUser().getName().toString();%>
<!--  <h1>The Social Network</h1> -->
<p> Welcome b2a ya ${it.name} </p>
<p> This is should be user home page </p>
<p> Current implemented services "http://fci-swe-apps.appspot.com/rest/RegistrationService --- {requires: uname, email, password}" </p>
<p> and "http://fci-swe-apps.appspot.com/rest/LoginService --- {requires: uname,  password}" </p>

<p>People you may know</p>

<% DatastoreService d = DatastoreServiceFactory.getDatastoreService(); 
	Query gaeQuery = new Query("users");
	Query gaeQuery1 = new Query("requests");
	
	PreparedQuery pq = d.prepare(gaeQuery);
	PreparedQuery pq1 = d.prepare(gaeQuery1);
	
	for (Entity entity : pq.asIterable()) {
		int f=0;
		for (Entity e2: pq1.asIterable()){
			if(entity.getProperty("name").toString().equals(e2.getProperty("toUser").toString()))
				f=1;
			}
		if(f == 0){%>
		<table>
		<tr>
			<td><%=entity.getProperty("name") %></td>
			<td><form action="/social/home/pending" method="post">
				<input type="hidden" name="toUser"  value="<%=entity.getProperty("name")%>">
				<input type="hidden" name="fromUser" value= "${it.name}"/>
				<input type="submit" value="Add Friend">
				
			    </form></td>
		
		<%}
	} %>
		</tr>
		
		<tr><td><b>People wants to add you</b></td></tr>
			
			<% DatastoreService d2 = DatastoreServiceFactory.getDatastoreService(); 
			Query gaeQuery2 = new Query("requests");
			PreparedQuery pq2 = d2.prepare(gaeQuery2);
			for (Entity entity : pq2.asIterable()) {
				if(entity.getProperty("status").toString().equals("pending")){
				%>
				<tr>
					<td><p><%=entity.getProperty("fromUser")%></p> wants to add you</td>
					<td><form action="/social/home/friends" method="post">
						<input type="hidden" name="toUser"  value="<%=entity.getProperty("toUser")%>"/>
						<input type="hidden" name="fromUser" value= "<%=entity.getProperty("fromUser")%>"/>
						<input type="submit" value="Accept Request">
						
					    </form></td>
			<%}
			}%>
		</tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<!--  -->
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>
		<form action="/social/home/message/" method="post">
		<input type="hidden" name="email" value= "${it.email}"/>
		<input type="submit" value="Send single message">
		</form>
		</td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>
		<form action="/social/home/gmessage/" method="post">
		<input type="hidden" name="email" value= "${it.email}"/>
		<input type="submit" value="Send Group message">
		</form>
		</td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>
		<a href="/social/home/notifications">Notifications</a>
		</td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td><p>Phase-2 b</p></td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>
		<form action="/social/home/makePost/" method="post">
		<input type="hidden" name="email" value= "${it.email}"/>
		<input type="submit" value="Create post">
		</form>
		</td></tr>
		<tr><td>--------------------------------------------------------------------</td></tr>
		<tr><td>
		<form action="/social/home/timeline/" method="post">
		<input type="hidden" name="email" value= "${it.email}"/>
		<input type="submit" value="Timeline">
		</form>
		</td></tr>
		</table>
</body>
</html>