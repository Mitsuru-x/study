<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Health" %>
<%
  // 役割: セッションから履歴を取り出して表で表示
  @SuppressWarnings("unchecked")
  List<Health> history = (List<Health>) session.getAttribute("history");
%>
<!doctype html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>履歴</title>
  <style>
    body { font-family: system-ui, sans-serif; max-width: 820px; margin: 2rem auto; line-height: 1.7; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: .5rem .75rem; text-align: left; }
    th { background: #fafafa; }
    .actions { margin: 1rem 0; }
    a.btn { padding: .6rem 1rem; border-radius: 8px; background: #f2f2f2; color: #111; text-decoration: none; }
  </style>
</head>
<body>
  <h1>計算履歴</h1>
  <div class="actions">
    <a class="btn" href="health_form.jsp">フォームへ</a>
  </div>
  <% if (history == null || history.isEmpty()) { %>
    <p>履歴はまだありません。</p>
  <% } else { %>
    <table>
      <thead><tr><th>#</th><th>身長(cm)</th><th>体重(kg)</th><th>BMI</th><th>体型</th></tr></thead>
      <tbody>
      <%
        for (int i = 0; i < history.size(); i++) {
          Health h = history.get(i);
      %>
        <tr>
          <td><%= (i + 1) %></td>
          <td><%= h.getHeightCm() %></td>
          <td><%= h.getWeightKg() %></td>
          <td><%= String.format("%.2f", h.getBmi()) %></td>
          <td><%= h.getBodyType() %></td>
        </tr>
      <% } %>
      </tbody>
    </table>
  <% } %>
</body>
</html>
