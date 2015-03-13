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
<title>Insert title here</title>
</head>

<form action="/social/login" method="GET">
<input type="submit" value="Signout">
</form>

<body>
<%String userName= UserEntity.getCurrentActiveUser().getName();%>

<p> Welcome b2a ya ${it.name} </p>
<p> This is should be user home page </p>
<p> Current implemented services "http://fci-swe-apps.appspot.com/rest/RegistrationService --- {requires: uname, email, password}" </p>
<p> and "http://fci-swe-apps.appspot.com/rest/LoginService --- {requires: uname,  password}" </p>
<p> you should implement sendFriendRequest service and addFriend service

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
		<table style="width:30%">
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
				%>
				<tr>
					<td><%=entity.getProperty("fromUser")%> wants to add you</td>
					<td><form action="/social/home/friends" method="post">
						<input type="hidden" name="toUser"  value="<%=entity.getProperty("toUser")%>"/>
						<input type="hidden" name="fromUser" value= "<%=entity.getProperty("fromUser")%>"/>
						<input type="submit" value="Accept Request">
						
					    </form></td>
			<%}%>
		</tr>
		</table>
</body>
</html>