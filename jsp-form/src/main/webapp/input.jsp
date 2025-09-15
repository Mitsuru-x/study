<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ja">
<head><meta charset="UTF-8"><title>入力フォーム</title></head>
<body>
  <h1>名前を入力してください</h1>
  <form action="${pageContext.request.contextPath}/FormServlet" method="post">
    <input type="text" name="name" required>
    <button type="submit">送信</button>
  </form>
</body>
</html>
