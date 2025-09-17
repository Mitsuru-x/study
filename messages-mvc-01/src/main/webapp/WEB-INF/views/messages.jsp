<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <!-- JSP基本ディレクティブ -->
<%@ page import="java.util.*" %>                                        <!-- Listを使うため -->
<%@ page import="model.Message" %>                                  <!-- Messageモデルを使うため -->
<%
    response.setContentType("text/html; charset=UTF-8");                // 文字化け防止（念のため）
    List<Message> messages = (List<Message>) request.getAttribute("messages"); // Controllerからの一覧
    String userName = (String) session.getAttribute("userName");        // セッション上のユーザー名
    if (messages == null) { messages = Collections.emptyList(); }       // 保険：nullなら空リスト
    if (userName == null) { userName = ""; }                            // 未設定なら空でフォームに反映
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">                                                  <!-- HTML側の文字コード -->
<title>Messages</title>                                                 <!-- タイトルだけ（装飾なし） -->
</head>
<body>
<h1>Messages</h1>                                                       <!-- 見出しだけ -->

<p>現在のユーザー名: <strong><%= (userName.isBlank() ? "未設定" : userName) %></strong></p> <!-- セッション確認 -->

<form method="post" action="<%= request.getContextPath() %>/messages">  <!-- 投稿フォーム（POST送信） -->
  <input type="text" name="user" placeholder="ユーザー名（初回/変更）」 value="<%= userName %>"><!-- user入力欄 -->
  <input type="text" name="text" placeholder="本文を入力">             <!-- 本文入力欄 -->
  <button type="submit">送信</button>                                   <!-- 送信ボタン -->
  <button type="submit" name="action" value="clear">全削除</button>     <!-- 学習用クリア -->
</form>

<h2>一覧</h2>                                                           <!-- 一覧見出し -->
<ul>                                                                     <!-- シンプルなリスト -->
<% for (Message m : messages) { %>                                       <!-- JSTLなしのforループ -->
  <li><strong><%= m.getAuthor() %></strong>: <%= m.getText() %></li>     <!-- 投稿者: 本文 -->
<% } %>                                                                  <!-- ループ終了 -->
<% if (messages.isEmpty()) { %>                                          <!-- 空のときの表示 -->
  <li>まだ投稿はありません。</li>                                           <!-- 空メッセージ -->
<% } %>                                                                  <!-- if終了 -->
</ul>

<p>表示は<strong>forward</strong>、送信後は<strong>redirect(PRG)</strong>で二重送信を防止。</p> <!-- 学習ポイント -->
</body>
</html>
