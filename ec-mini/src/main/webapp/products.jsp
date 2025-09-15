<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!doctype html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>商品一覧</title>
</head>
<body>
  <h1>商品一覧</h1>
  <a href="${pageContext.request.contextPath}/Cart">🛒 カートを見る</a>
  <ul>
    <c:forEach var="p" items="${catalog}">
      <li>
        <c:out value="${p.name}"/> - <c:out value="${p.price}"/> 円
        <form action="${pageContext.request.contextPath}/Cart" method="post" style="display:inline">
          <input type="hidden" name="action" value="add">
          <input type="hidden" name="id" value="${p.id}">
          <button type="submit">カートに追加</button>
        </form>
      </li>
    </c:forEach>
  </ul>
</body>
</html>
