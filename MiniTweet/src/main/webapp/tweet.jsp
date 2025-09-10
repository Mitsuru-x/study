<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MintTweet - つぶやく</title>
</head>
<body>
<h1>MiniTweet</h1>
<form action="${pageContext.request.contextPath }/TweetServlet" method="post">
	<label>
	つぶやき
	<input type="text" name="text" maxlength="140" required>
	</label>
	<button type="submit">送信</button>
</form>
<p style="color: #666;">※　まだ送信先(/teet)は未実装。まずは画面が出ればOK。</p>
</body>
</html>