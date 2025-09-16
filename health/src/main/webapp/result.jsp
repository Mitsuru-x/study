<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Health" %>
<%
  // 役割: サーブレットが置いた "health" 属性を受け取り、結果を整形して表示
  Health health = (Health) request.getAttribute("health");
%>
<!doctype html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>BMI 結果</title>
  <style>
    body { font-family: system-ui, sans-serif; max-width: 720px; margin: 2rem auto; line-height: 1.7; }
    .card { padding: 1rem 1.25rem; border: 1px solid #ddd; border-radius: 12px; }
    .grid { display: grid; grid-template-columns: 140px 1fr; gap: .5rem 1rem; }
    .muted { color: #666; }
    .actions { margin-top: 1rem; display: flex; gap: .5rem; }
    a.btn { padding: .6rem 1rem; border-radius: 8px; background: #f2f2f2; color: #111; text-decoration: none; }
  </style>
</head>
<body>
  <h1>BMI 結果</h1>

  <% if (health == null) { %>
    <p class="muted">結果がありません。フォームから計算してください。</p>
    <a class="btn" href="health_form.jsp">入力フォームへ</a>
  <% } else { %>
    <div class="card">
      <div class="grid">
        <div>身長</div><div><%= health.getHeightCm() %> cm</div>
        <div>体重</div><div><%= health.getWeightKg() %> kg</div>
        <div>BMI</div><div><%= String.format("%.2f", health.getBmi()) %></div>
        <div>体型</div><div><%= health.getBodyType() %></div>
      </div>
    </div>
    <div class="actions">
      <a class="btn" href="health_form.jsp">もう一度</a>
      <a class="btn" href="history.jsp">履歴を見る</a>
    </div>
  <% } %>
</body>
</html>
