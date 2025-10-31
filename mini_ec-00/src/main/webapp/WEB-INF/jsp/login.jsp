<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String msg = (String) request.getAttribute("msg"); // 失敗などのメッセージ
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン / 会員登録</title>
</head>
<body>
    <h1>ログイン / 会員登録</h1>

    <% if (msg != null) { %>
        <p style="color:red;"><%= msg %></p>
    <% } %>

    <!-- ログインフォーム -->
    <form method="post" action="<%= request.getContextPath() %>/auth">
        <input type="hidden" name="action" value="login">
        <div>
            メール:
            <input type="email" name="email" required>
        </div>
        <div>
            パス:
            <input type="password" name="pass" required>
        </div>
        <button type="submit">ログイン</button>
    </form>

    <hr>

    <!-- 新規登録フォーム -->
    <form method="post" action="<%= request.getContextPath() %>/auth">
        <input type="hidden" name="action" value="register">
        <div>
            メール:
            <input type="email" name="email" required>
        </div>
        <div>
            パス:
            <input type="password" name="pass" required>
        </div>
        <button type="submit">新規登録</button>
    </form>

    <p>
        <a href="<%= request.getContextPath() %>/products">商品一覧へ戻る</a>
    </p>
</body>
</html>
