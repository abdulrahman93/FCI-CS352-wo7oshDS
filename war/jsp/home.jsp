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
<form action="/social/login" method="لGET">
<input type="submit" value="Signout">
</form>
<body>
<%String uname= request.getParameter("${it.name}");%>
<p> Welcome b2a ya ${it.name} </p>
<p> This is should be user home page </p>
<p> Current implemented services "http://fci-swe-apps.appspot.com/rest/RegistrationService --- {requires: uname, email, password}" </p>
<p> and "http://fci-swe-apps.appspot.com/rest/LoginService --- {requires: uname,  password}" </p>
<p> you should implement sendFriendRequest service and addFriend service

<p>People you may know</p>

<% DatastoreService d = DatastoreServiceFactory.getDatastoreService(); 
	Query gaeQuery = new Query("users");
	PreparedQuery pq = d.prepare(gaeQuery);
	for (Entity entity : pq.asIterable()) {
		%>
		<table style="width:30%">
		<tr>
			<td><%=entity.getProperty("name") %></td>
			<td><form action="/social/home/pending" method="post">
				<input type="hidden" name="toUser"  value="<%=entity.getProperty("name")%>">
				<input type="hidden" name="fromUser" value= "${it.name}"/>
				<input type="submit" value="Add Friend">
				
			    </form></td>
		
		<%} %>
		</tr>
		
		<tr><td><b>People wants to add you</b></td></tr>
		<tr>
			
			<% DatastoreService d2 = DatastoreServiceFactory.getDatastoreService(); 
			Query gaeQuery2 = new Query("requests");
			PreparedQuery pq2 = d2.prepare(gaeQuery2);
			for (Entity entity : pq2.asIterable()) {
				//if(entity.getProperty("toUser").toString().equals("${it.name}"))
				{
				%>
				<tr>
					<td><%=entity.getProperty("fromUser") %> wants to add you</td>
					<td></td>
					<td><form action="" method="post">
						<input type="text" value="<%=uname%>"/>
						<input type="hidden" name="toUser"  value="<%=entity.getProperty("name")%>"/>
						<input type="hidden" name="fromUser" value= "${it.name}"/>
						<input type="submit" value="Accept Request">
						
					    </form></td>
				<%}
				} %>
		</tr>
		</table>
</body>
</html>