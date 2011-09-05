<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="/css/home.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAAj6N1wGgGpuuqxjU6PcoKRxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQCuZ2f9FcUf3Kd5Yh17KUSHtrfNA"></script>
<script type="text/javascript" src="/js/jsonrpc.js"></script>
<script type="text/javascript" src="/js/nb.js"></script>
<title></title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp" />

	<div class="container box wide place">
		<c:choose>
			<c:when test="${not empty place.homepage }">
				<a href="${place.homepage }" title="${place.homepage }"><span class="homepage">${place.title }</span></a>
			</c:when>
			<c:otherwise>
				<span>${place.title }</span>
			</c:otherwise>
		</c:choose>
		<span class="phone">${place.phone }</span>
	</div>
	
	<div class="group">
		
		<div class="container box map">
			<img alt="${place.country } ${place.city } ${place.streetAddress }" title="${place.country } ${place.city } ${place.streetAddress }"
				src="http://maps.googleapis.com/maps/api/staticmap?center=${place.latitude },${place.longitude }&size=400x400&markers=color:yellow|${place.latitude },${place.longitude }&sensor=false"
				width="400px" height="400px" />
		</div>

		<div class="container box contacts">
			<span class="address"> ${place.country } ${place.city } ${place.streetAddress } </span>  
		</div>

		<div class="container box beers">
			<c:forEach var="beer" items="${beers }">
				<div id="${beer.objectId }" class="beer">
					<a href="/beer/${beer.objectId }"> 
						<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_${beer.icon }.png" /> 
					</a>
				</div>
			</c:forEach>
		</div>
	
	</div>

	<div id="content">

		<div id="tagsSection" class="section">
			<div class="sectionContent">
				<c:forEach var="tag" items="${place.tags }">
					<div id="tag_${tag }">
						<img alt="" src="" /> <span>${tag }</span>
					</div>
				</c:forEach>
			</div>
		</div>
		
	</div>

	<jsp:include page="/jsp/footer.jsp" />
</body>

</html>