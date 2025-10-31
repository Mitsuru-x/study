<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>商品登録	</h2>
	<form action="${pageContext.request.contextPath}/products" method="post">
		<label>商品名：<input type="text" name="name" required></label>
		<label>価格：<input type="number" name="price" step="0.01" required></label>
		<button type="submit">登録</button>
	</form>
	
	<c:if test="${not empty error}">
	    <div style="color:red;"><c:out value="${error}" /></div>
	</c:if>
	
	<h2>商品一覧</h2>
	<table border="1">
	    <tr>
	        <th>ID</th>
            <th>商品名</th>
            <th>価格</th>
            <th>登録日時</th>
        </tr>
        <c:forEach var="p" items="${products }">
        <tr>
            <td><c:out value="${p.id }"/></td>
            <td><c:out value="${p.name }"/></td>
            <td><fmt:formatNumber value="${p.price }" type="currency"/></td
            <td><c:out value="${p.createdAt }"/></td>
        </tr>
        </c:forEach>
   </table>
</body>
</html>