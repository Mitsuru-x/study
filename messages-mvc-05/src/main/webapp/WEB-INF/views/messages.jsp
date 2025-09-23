<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Message" %>
<%
	response.setContentType("text/html;charset=UTF-8");
	List<Message> messages = (List<Message>)request.getAttribute("messages");
	String userName = (String)session.getAttribute("userName");
	if(messages == null){messages = Collections.emptyList();}
	if(userName == null){userName = "";}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Messages</h1>
<p>現在のユーザー名:<strong><%= userName %></strong></p>
<form action="<%= request.getContextPath() %>/messages" method="post">
	<input type="text"  name="user" placeholder="ユーザー名">
	<input type="text" name="text" placeholder="本文を入力">
	<button type="submit">送信</button>
	<button type="submit" name="action"  value="clear">全削除</button>
</form>

<h2>一覧</h2>
<ul>
<%for(Message m : messages){%>
	<li><strong><%= m.getAuthor() %></strong>:<%= m.getText() %></li>
<% } %>
<% if(messages.isEmpty()){ %>
	<li>まだ投稿はありません</li>
<% } %>
</ul>
</body>
</html>