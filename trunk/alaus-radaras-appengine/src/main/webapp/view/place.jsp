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
	<div id="content">
		<div id="pubTitle">${place.title }</div>

		<div id="infoSection" class="section">
			<div class="sectionHeader">
				<div class="sectionTitle">Informacija</div>
			</div>
			<div id="map">
				<img alt="map" src="http://maps.googleapis.com/maps/api/staticmap?center=${place.latitude },${place.longitude }&size=300x300&markers=color:yellow|${place.latitude },${place.longitude }&sensor=false" width="300px" height="300px" />
			</div>
			<div>
				<div id="country">${place.country }</div>
				<div id="city">${place.city }</div>
				<div id="address">${place.streetAddress }</div>
				<div id="phone">${palce.phone }</div>
				<div id="homepage">${place.homepage }</div>
			</div>
		</div>

		<div id="beerSection" class="section">
			<div class="sectionHeader">
				<div class="sectionTitle">Alus</div>
				<div id="addBeer">
					<input value="pridėti alų" />
				</div>
			</div>
			<div class="sectionContent">
				<c:forEach var="beer" items="${beers }">
					<div id="${beer.objectId }">
						<img alt="" src="" /> <span><a href="/beer/${beer.objectId }">${beer.title }</a></span>
					</div>
				</c:forEach>
			</div>
		</div>

		<div id="tagsSection" class="section">
			<div class="sectionHeader">
				<div class="sectionTitle">Žymos</div>
				<div id="addTag">
					<input value="pridėti žymą" />
				</div>
			</div>			
			<div class="sectionContent">
				<c:forEach var="tag" items="${place.tags }">
					<div id="tag_${tag }">
						<img alt="" src="" /> <span>${tag }</span>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

</body>

</html>