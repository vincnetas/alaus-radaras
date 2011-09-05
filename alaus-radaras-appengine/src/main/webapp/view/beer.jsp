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
<title></title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp" />
	
	<div id="content">
		<div id="pubTitle">${beer.title }</div>

		<div id="infoSection" class="section">
			<div class="sectionHeader">
				<div class="sectionTitle">Informacija</div>
			</div>
			<div id="map">
				<img alt="map" src="" width="300px" height="300px" />
			</div>
			<div>
				<div id="country">${company.title }</div>
			</div>
		</div>

		<div id="beerSection" class="section">
			<div class="sectionHeader">
				<div class="sectionTitle">Vietos</div>
			</div>
			<div class="sectionContent">
				<c:forEach var="place" items="${places }">
					<div id="${place.objectId }">
						<img alt="" src="" /> <span><a href="/place/${place.objectId }">${place.title }</a></span>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

</body>

</html>