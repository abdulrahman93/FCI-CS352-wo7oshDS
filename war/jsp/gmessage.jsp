<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
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
<body>
<p>Hello here in group message ${it.name} </p>
<% DatastoreService d = DatastoreServiceFactory.getDatastoreService(); 
	Query gaeQuery = new Query("users");
	Query gaeQuery1 = new Query("requests");
	
	PreparedQuery pq = d.prepare(gaeQuery);
	PreparedQuery pq1 = d.prepare(gaeQuery1);
	
		for (Entity e2: pq1.asIterable()){
			if(e2.getProperty("status").toString().equals("friends")
					&& e2.getProperty("fromUser").toString().equals("user")){%>
		<table>
		<tr>
			<td><%=e2.getProperty("toUser") %></td>
			<td><form action="/social/home/pending" method="post">
				<input type="hidden" name="toUser"  value="<%=e2.getProperty("toUser")%>">
				<input type="hidden" name="fromUser" value= "${it.name}"/>
				<input type="checkbox" value="Add Friend"/>
				
			    </form></td>
				</tr>
	<% } 
	}%>
	</table>
</body>
</html>