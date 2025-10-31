<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.CartItem, model.User" %>
<%
    // セッションから取得
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if (cart == null) cart = java.util.Collections.emptyList();

    // 合計金額を計算
    int total = 0;
    for (CartItem it : cart) total += it.getPrice() * it.getQty();

    // メッセージ/ログイン情報
    String msg = (String) request.getAttribute("msg");
    User login = (User) session.getAttribute("login");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>カート</title>
</head>
<body>
    <h1>カート</h1>

    <% if (msg != null) { %>
        <p style="color:red;"><%= msg %></p>
    <% } %>

    <ul>
        <% if (!cart.isEmpty()) { %>
            <% for (CartItem it : cart) { %>
                <li>
                    <%= it.getName() %> × <%= it.getQty() %> = <%= it.getPrice() * it.getQty() %>円
                    <form method="post" action="<%= request.getContextPath() %>/cart" style="display:inline">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="pid" value="<%= it.getProductId() %>">
                        <button type="submit">削除</button>
                    </form>
                </li>
            <% } %>
        <% } else { %>
            <li>カートは空です。</li>
        <% } %>
    </ul>

    <p>合計: <strong><%= total %></strong> 円</p>

    <% if (login != null) { %>
        <!-- ログイン済みなら購入ボタン -->
        <form method="post" action="<%= request.getContextPath() %>/checkout">
            <button type="submit" <%= cart.isEmpty() ? "disabled" : "" %>>購入する</button>
        </form>
    <% } else { %>
        <!-- 未ログインなら案内 -->
        <p>
            <a href="<%= request.getContextPath() %>/auth">ログイン/会員登録</a>後に購入できます。
        </p>
    <% } %>

    <p><a href="<%= request.getContextPath() %>/products">商品一覧へ戻る</a></p>
</body>
</html>
