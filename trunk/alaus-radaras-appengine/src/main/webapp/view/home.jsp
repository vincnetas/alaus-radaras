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
	<div class="header box">
		<div class="logo">
			<a href="http://www.alausradaras.lt/home"> <img src="/img/bokalas_vol_3.png" width="80px"> Alaus radaras </a>
		</div>
		<div class="market">
			<ul>
				<li><a href="https://market.android.com/details?id=alaus.radaras"> <img alt="android" title="Androido turgelis" src="http://www.alausradaras.lt/img/android.png" /> </a>
				</li>
				<li><a href="http://itunes.apple.com/lt/app/alaus-radaras/id418197023?mt=8"> <img alt="apple" title="Obuolių krautuvė" src="http://www.alausradaras.lt/img/apple.png" />
				</a>
				</li>
				<li><a href="http://store.ovi.com/content/98606"> <img alt="nokia" title="Nokia durys (Ovi, suomiškai Durys)" src="http://www.alausradaras.lt/img/ovi.png" /> </a>
				</li>
			</ul>
		</div>
	</div>

	<div class="container box">
		<span>Noriu alaus</span> <input type="text" id="beerSearchInput" placeholder="įvesk pavadinimą" /><sub>(<a href="/beers">rodyk visus</a>)</sub>
	</div>

	<div class="container box">
		<span>Ieškau baro</span> <input type="text" id="placeSearchInput" placeholder="įvesk pavadinimą" /><sub>(<a href="/places">rodyk visus</a>)</sub>
	</div>

	<div class="footer box">
		<a href="http://alausradaras.lt" title="Daugiau informacijos">Apie radarą</a>
	</div>

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