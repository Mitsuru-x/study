<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>商品登録</h2>
	<form action="${pageContext.request.contextPath}/products"method="post">
		<label>商品名:<input type="text" name="name" ></label>
		<label>価格：<input type="number" name="price" ></label>
		<button type="submit">登録</button>
	</form>

	<c:if test="${not empty error }">
		<div style="color: red;">
			<c:out value="${error}" />
		</div>
	</c:if>

	<h2>商品一覧</h2>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>商品名</th>
			<th>価格</th>
			<th>登録日時</th>
			<th>変更</th>
			<th>削除</th>
		</tr>

		<c:forEach var="p" items="${products }">
			<tr>
				<!-- 変更 -->
				<form action="${pageContext.request.contextPath}/products" method="post">
				<input type="hidden" name="action" value="update">
				<td><input type="hidden" name="id" value="${p.id}">${p.id}</td>
				<td><input type="text" name="name"  value="${p.name }"></td>
				<td><input type="text" name="price" value="${p.price }"></td>
				<td><${p.createdAt }</td>
				<td><button type="submit">変更</button></td>
				</form>
				
				<!-- 削除 -->
				<td>
				<form action="${pageContext.request.contextPath}/products" method="post">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" value="${p.id }">
				<button type="submit">削除</button>
				</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>