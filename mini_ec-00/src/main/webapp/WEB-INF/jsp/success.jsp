<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Integer orderId = (Integer) request.getAttribute("orderId"); // 注文ID
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>購入完了</title>
</head>
<body>
    <h1>ご購入ありがとうございます</h1>

    <% if (orderId != null) { %>
        <p>注文番号：<strong><%= orderId %></strong></p>
    <% } %>

    <p>
        <a href="<%= request.getContextPath() %>/products">商品一覧へ</a>
    </p>
</body>
</html>
