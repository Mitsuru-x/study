<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,model.Message" %>
<%
	response.setContentType("text/html;charset=utf-8");
	List<Message> messages = (List<Message>)request.getAttribute("messages");
	String userName = (String)session.getAttribute("userName");
	if(messages == null){ messages = Collections.emptyList();}
	if(userName == null){ userName = "";}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Messages</h1>
<p>現在のユーザー名:<strong><%= (userName.isBlank() ? "未設定" : userName) %></strong></p>

<form method="post" action="<%= request.getContextPath() %>/messages">
	<input type="text" name="user" placeholder="ユーザー名(初回/変更)" value="<%= userName %>">
	<input type="text" name="text" placeholder="本文を入力">
	<button type="submit">送信</button>
	<button type="submit" name="action" value="clear">全消去</button>
</form>
<h2>一覧</h2>
<ul>
<% for(Message m : messages){ %>
	<li><strong><%= m.getAuthor() %></strong>:<%= m.getText() %></li>
	<% } %>
	<% if(messages.isEmpty()){ %>
		<li>まだ投稿はありません。</li>
	<% } %>
</ul>
<p>表示は<strong>forward</strong>、送信後は<strong>redirect(RPG)</strong>で二重送信を防止。</p>
</body>
</html>