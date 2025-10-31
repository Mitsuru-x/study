<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Product, model.User" %>

<%
    // サーブレット（例：ProductsServlet）で request.setAttribute("products", ...) 済み想定
    List<Product> products = (List<Product>) request.getAttribute("products");
    User login = (User) session.getAttribute("login");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>商品一覧</title>
</head>
<body>
    <!-- ログイン状態 -->
    <h1>商品一覧</h1>
    <p>
        <% if (login == null) { %>
            <a href="<%= request.getContextPath() %>/auth">ログイン/会員登録</a>
        <% } else { %>
            ようこそ <strong><%= login.getEmail() %></strong> さん｜
            <a href="<%= request.getContextPath() %>/cart">カートを見る</a>
        <% } %>
    </p>

    <ul>
        <% if (products != null) { %>
            <% for (Product p : products) { %>
                <li>
                    <strong><%= p.getName() %></strong>
                    - <%= p.getPrice() %>円（在庫: <%= p.getStock() %>）
                    <% if (login != null) { %>
                        <form method="post" action="<%= request.getContextPath() %>/cart" style="display:inline">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="pid" value="<%= p.getId() %>">
                            <button type="submit">カートに入れる</button>
                        </form>
                    <% } %>
                </li>
            <% } %>
        <% } else { %>
            <li>商品データがありません。</li>
        <% } %>
    </ul>
</body>
</html>
