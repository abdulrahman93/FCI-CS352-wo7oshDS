<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Map;" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Timeline</title>
</head>
<body>
	<c:forEach items="${it}" var="m">
		<option value="${m.key}">
		<!-- ${m.value} -->
            
            <c:forEach items="${m.value}" var="arr">
            	${arr}
            </c:forEach>
            <br>
        </option>
	</c:forEach>
<p>${it.content}</p>
</body>
</html>