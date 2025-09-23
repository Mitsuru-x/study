<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	@SuppressWarnings("unchecked")
	List<String> mutters = (List<String>)request.getAttribute("mutters");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>最小掲示板</h1>
	
	<form action="<%= request.getContextPath() %>/bbs" method="post">
	<input type="text" name="text" placeholder="ひとこと" required="required">
	<button type="submit">投稿</button>
	</form>
	
	<hr>
	<h2>投稿一覧</h2>
	<ul>
		<% if(mutters != null){
			for(String m : mutters){ 
		%>
			<li><%= m %></li>
		<% }
			} %>
	</ul>
</body>
</html>