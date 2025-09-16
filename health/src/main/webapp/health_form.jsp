<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>BMI 入力フォーム</title>
  <style>
    body { font-family: system-ui, sans-serif; max-width: 720px; margin: 2rem auto; line-height: 1.7; }
    form { display: grid; gap: .75rem; padding: 1rem; border: 1px solid #ddd; border-radius: 12px; }
    label { font-weight: 600; }
    input[type="number"] { padding: .5rem; border: 1px solid #ccc; border-radius: 8px; }
    .error { color: #b00020; font-weight: 700; }
    .row { display: grid; grid-template-columns: 140px 1fr; gap: .75rem; align-items: center; }
    .actions { display: flex; gap: .5rem; }
    button, a.btn { padding: .6rem 1rem; border-radius: 8px; text-decoration: none; border: none; }
    button { background: #111; color: #fff; cursor: pointer; }
    a.btn { background: #f2f2f2; color: #111; }
  </style>
</head>
<body>
  <h1>BMI 計算</h1>

  <%-- エラー表示（あれば） --%>
  <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
  %>
    <p class="error"><%= error %></p>
  <% } %>

  <form method="post" action="<%= request.getContextPath() %>/health">
    <div class="row">
      <label for="height">身長(cm)</label>
      <input id="height" name="height" type="number" step="0.1" min="1"
             value="<%= request.getAttribute("prevHeight") != null ? request.getAttribute("prevHeight") : "" %>"
             required>
    </div>
    <div class="row">
      <label for="weight">体重(kg)</label>
      <input id="weight" name="weight" type="number" step="0.1" min="1"
             value="<%= request.getAttribute("prevWeight") != null ? request.getAttribute("prevWeight") : "" %>"
             required>
    </div>
    <div class="actions">
      <button type="submit">計算する</button>
      <a class="btn" href="<%= request.getContextPath() %>/health">リセット</a>
      <a class="btn" href="history.jsp">履歴を見る</a>
    </div>
  </form>
</body>
</html>
