<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!doctype html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>カート</title>
<style>
body{font-family:system-ui, sans-serif; line-height:1.7; margin:24px}
table{border-collapse:collapse; width:100%}
th,td{border-bottom:1px solid #ddd; padding:.5rem .6rem; text-align:left}
tfoot td{font-weight:bold}
a.btn, button{padding:.4rem .8rem; border-radius:8px; border:1px solid #999; background:#fff; cursor:pointer}
.actions{display:flex; gap:.5rem; align-items:center}
.header{display:flex; justify-content:space-between; align-items:center; margin-bottom:12px}
</style>
</head>
<body>
  <div class="header">
    <h1>🛒 カート</h1>
    <a class="btn" href="${pageContext.request.contextPath}/products.jsp">← 商品一覧へ</a>
  </div>

  <c:choose>
    <c:when test="${empty items}">
      <p>カートは空です。</p>
    </c:when>
    <c:otherwise>
      <form class="actions" action="${pageContext.request.contextPath}/Cart" method="post">
        <input type="hidden" name="action" value="clear">
        <button type="submit">カートを空にする</button>
      </form>
      <table>
        <thead>
          <tr><th>商品</th><th>単価</th><th>数量</th><th>小計</th><th></th></tr>
        </thead>
        <tbody>
          <c:forEach var="it" items="${items}">
            <tr>
              <td><c:out value="${it.product.name}"/></td>
              <td><c:out value="${it.product.price}"/> 円</td>
              <td><c:out value="${it.quantity}"/></td>
              <td><c:out value="${it.subtotal}"/> 円</td>
              <td>
                <form action="${pageContext.request.contextPath}/Cart" method="post" style="display:inline">
                  <input type="hidden" name="action" value="remove">
                  <input type="hidden" name="id" value="${it.product.id}">
                  <button type="submit">削除</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
        <tfoot>
          <tr><td colspan="3">合計</td><td colspan="2"><strong><c:out value="${total}"/> 円</strong></td></tr>
        </tfoot>
      </table>
    </c:otherwise>
  </c:choose>
</body>
</html>
