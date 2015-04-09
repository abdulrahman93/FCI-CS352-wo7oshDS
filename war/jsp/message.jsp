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
<p>choose from your friends to send to</p>

<%DatastoreService d = DatastoreServiceFactory.getDatastoreService(); 
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
			<td><form action="/social/home/message/isent" method="post">
				<input type="hidden" name="toUser"  value="<%=entity.getProperty("name")%>">
				<input type="hidden" name="fromUser" value= "${it.name}">
				<br><br>
				<input type="radio">
				
				<input type="text" name="content" size="50">
				<input type="submit" value="Send">
			    </form></td>
		
		<%}
	} %>
	</tr>
	</table>
</body>
</html>