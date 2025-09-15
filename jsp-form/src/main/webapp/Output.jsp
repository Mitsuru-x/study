<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!doctype html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>結果表示</title>
</head>
<body>
  <h1>結果</h1>
  <p>こんにちは、<c:out value="${userName}"/> さん！</p>

  <h2>これまでの入力履歴</h2>
  <c:choose>
    <c:when test="${empty names}">
      <p>まだ履歴はありません。</p>
    </c:when>
    <c:otherwise>
      <ul>
        <c:forEach var="n" items="${names}" varStatus="s">
          <li>#<c:out value="${s.index + 1}"/>：<c:out value="${n}"/></li>
        </c:forEach>
      </ul>
    </c:otherwise>
  </c:choose>

  <a href="${pageContext.request.contextPath}/input.jsp">← 戻る</a>
</body>
</html>
