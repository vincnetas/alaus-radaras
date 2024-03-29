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
	<jsp:include page="/jsp/header.jsp"/>

	<div class="container box homeInputBeer">
		<span>Noriu alaus</span> <input type="text" id="beerSearchInput" placeholder="įvesk pavadinimą" /><sub>(<a href="/beers">rodyk visus</a>)</sub>
	</div>

	<div class="container box homeInputPlace">
		<span>Ieškau baro</span> <input type="text" id="placeSearchInput" placeholder="įvesk pavadinimą" /><sub>(<a href="/places">rodyk visus</a>)</sub>
	</div>

	<jsp:include page="/jsp/footer.jsp"/>
	
	<script>
		google.load("jquery", "1.6.2");
		google.load("jqueryui", "1.8.14");

		google.setOnLoadCallback(function() {

			// http://docs.jquery.com/UI/API/1.8/Autocomplete  
			$("#placeSearchInput").autocomplete({
				source : function(request, result) {
					jsonService.nb.acPlace({
						params : [ request.term, 5 ],
						onSuccess : function(su) {
							var suggestions = [];
							$.each(su, function(i, val) {
								suggestions.push({
									label : val.title,
									place : val
								});
							});

							result(suggestions);
						},
						onException : function(e) {
							alert("Unable to compute because: " + e);
							return true;
						}
					});
				},
				select : function(event, ui) {
					window.open("/place/" + ui.item.place.id, "_self");
				},
				delay : 0
			});

			$("#beerSearchInput").autocomplete({
				source : function(request, result) {
					jsonService.nb.acBeer({
						params : [ request.term, 5 ],
						onSuccess : function(su) {
							var suggestions = [];
							$.each(su, function(i, val) {
								suggestions.push({
									label : val.title,
									place : val
								});
							});

							result(suggestions);
						},
						onException : function(e) {
							alert("Unable to compute because: " + e);
							return true;
						}
					});
				},
				select : function(event, ui) {
					window.open("/beer/" + ui.item.place.id, "_self");
				},
				delay : 0
			});

		});
	</script>
</body>
</html>