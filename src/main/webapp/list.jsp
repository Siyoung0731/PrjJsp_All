<%@page import="db.DBHandler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String username = request.getParameter("username");
	if(username == null) {
		username = "";
	}
	
	DBHandler db = new DBHandler();
	String resultTags =  db.list(username);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>List Jsp</h2>
	<p>검색할 이름: <%= username.equals("") ? "전체 검색" : username %></p>
	<ul><%= resultTags %></ul>
</body>
</html>