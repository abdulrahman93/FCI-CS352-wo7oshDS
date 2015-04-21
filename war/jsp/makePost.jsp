<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Create Post</title>
</head>
<body>
	<form action="/social/home/makePost/postCreated" method="post">
	<input type="hidden" name="owner" value= "${it.name}">
	<input type="text" name="content" onFocus="value=''" size="50">
	<br><br>
	<p><input type="radio" name="status" value="angry"/>Feeling Angry</p>
	<p><input type="radio" name="status" value="happy"/>Feeling Happy</p>
	<p>===============================================</p>
	<p><input type="radio" name="privacy" value="public"/>Public</p>
	<p><input type="radio" name="privacy" value="private"/>Private</p>
	<p><input type="radio" name="privacy" value="friends"/>Only Friends</p>
	<input type="submit" value="Send">
    </form>
</body>
</html>