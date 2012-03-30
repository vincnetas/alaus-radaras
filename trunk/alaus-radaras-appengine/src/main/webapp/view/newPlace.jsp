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
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

    <script type="text/javascript">

      var map;
      var geocoder;
      
      function initialize() {
        var mapDiv = document.getElementById('map-canvas');
        
        geocoder = new google.maps.Geocoder();
        map = new google.maps.Map(mapDiv, {
          center: new google.maps.LatLng(0, 0),
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        });
      
        google.maps.event.addListenerOnce(map, 'tilesloaded', addMarkers);
      
      }
      
      function addMarkers() {
          var latLng = new google.maps.LatLng(0, 0);
          var marker = new google.maps.Marker({
            position: latLng,
            map: map
          });
      }
      

      google.maps.event.addDomListener(window, 'load', initialize);
    </script>

<title>Nauja vieta</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>

	<div class="group">
		<div class="container box newPlace">
			<span>Nauja vieta?</span>
			<input type="text" placeholder="pavadinimas" />
			<input type="text" placeholder="telefonas" />
			<input type="text" placeholder="web" />
			<input type="text" placeholder="tipas" />
		</div>
	
		<div class="container box newPlace newPlaceAddress">
			<span>Kur ji?</span>
			<input type="text" id="country" class="addressField" placeholder="šalis (matomai Lietuva)" />
			<input type="text" id="city" class="addressField" placeholder="miestas" />
			<input type="text" id="address" class="addressField" placeholder="adresas" />
			<div id="map-canvas" style="width: 99%; height: 400px; padding: 2px;"></div>
		</div>
	
		<div class="container box newPlace newPlaceBeer">
			<span>Kokio alaus turi?</span>
			<input type="text" id="beerSearchInput" placeholder="alaus pavadinimas" />
	
					<div id="${beer.objectId }" class="beer">
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_guinness.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
						<a href="/beer/${beer.objectId }"> 
							<img alt="${beer.title }" title="${beer.title }" src="/img/beer/brand_bravarai.png" /> 
						</a>
					</div>
		</div>
		
		<div class="container box newPlace newPlaceTags">
			<span>Kažkas ypatingo?</span>
			<input type="text" id="beerSearchInput" placeholder="alaus pavadinimas" />
		</div>
	</div>

	<jsp:include page="/jsp/footer.jsp"/>
	
		<script>
		google.load("jquery", "1.6.2");
		google.load("jqueryui", "1.8.14");

		google.setOnLoadCallback(function() {
			$(".addressField").change(function() {
				address = $("#address").val() + " " + $("#city").val() + " " + $("#country").val();
			    geocoder.geocode( { 'address': address}, function(results, status) {
			        if (status == google.maps.GeocoderStatus.OK) {
						map.setCenter(results[0].geometry.location);
						map.fitBounds(results[0].geometry.viewport);
			          	var marker = new google.maps.Marker({
			              	map: map,
			              	position: results[0].geometry.location
			          	});
			        } else {
			          alert("Geocode was not successful for the following reason: " + status);
			        }
			    });
			});
			
			// http://docs.jquery.com/UI/API/1.8/Autocomplete  
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