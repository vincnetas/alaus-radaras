<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="/css/home.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAAj6N1wGgGpuuqxjU6PcoKRxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQCuZ2f9FcUf3Kd5Yh17KUSHtrfNA"></script>
<script type="text/javascript" src="/js/jsonrpc.js"></script>
<script type="text/javascript" src="/js/nb.js"></script>
<title></title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp" />

	<div class="container box wide ">
		<c:forEach var="beer" items="${beers }">
			<div id="${beer.objectId }" class="beer">
				<a href="/beer/${beer.objectId }"> 
					<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_${beer.icon }.png" /> 
				</a>
			</div>
		</c:forEach>
	</div>
	
	<jsp:include page="/jsp/footer.jsp" />
	
</body>

</html>