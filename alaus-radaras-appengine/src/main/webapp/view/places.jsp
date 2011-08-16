<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="/css/br.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAAj6N1wGgGpuuqxjU6PcoKRxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQCuZ2f9FcUf3Kd5Yh17KUSHtrfNA"></script>
<script type="text/javascript" src="/js/jsonrpc.js"></script>
<script type="text/javascript" src="/js/nb.js"></script>
<title>Places</title>
</head>
<body>
	<c:forEach var="city" items="${places}">
		<h2>${city.key}</h2>
		<ul>
			<c:forEach var="place" items="${city.value}">
				<li><a href="/place/${place.id}">${place.title }</a></li>
			</c:forEach>
		</ul>
	</c:forEach>
</body>

</html>