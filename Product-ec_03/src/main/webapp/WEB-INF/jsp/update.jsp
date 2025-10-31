<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>商品を変更</h2>
    <form action="${pageContext.request.contextPath}/products" method="post">
    		<input type="hidden" name="action" value="update">
    		<input type="hidden" name="id" value="${p.id}">
    		商品名：<input type="text" name="name" value="${p.name}">
    		価格：<input type="namber" name="price" value="${p.price}">
    		<button type="submit">保存</button>
    		<a href="${pageContext.request.contextPath}/products">戻る</a>
    	</form>
</body> 
</html>