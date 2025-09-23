<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,model.Message" %>
<%
	@SuppressWarnings("unchecked")
	List<Message> messages = (List<Message>)request.getAttribute("messages");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>掲示板</h1>
	
	<form method="post" action="<%= request.getContextPath() %>/bbs">
	<input type="text" name="text">
	<button type="submit">投稿</button>
	</form>
	
	<hr>
	<h2>一覧</h2>
	<ul>
	<% if(messages != null){
		for(Message m : messages){
	%>
		<li>[<%= m.getCreatedAt() %>]<%= m.getText() %></li>
	<%
		}
	} 
	%>
	</ul>
</body>
</html>