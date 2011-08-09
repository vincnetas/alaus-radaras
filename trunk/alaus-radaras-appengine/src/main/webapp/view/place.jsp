<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAAj6N1wGgGpuuqxjU6PcoKRxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQCuZ2f9FcUf3Kd5Yh17KUSHtrfNA"></script>
<script type="text/javascript" src="/js/jsonrpc.js"></script>
<script type="text/javascript" src="/js/nb.js"></script>
<title></title>
</head>
<body>
	<h1>${place.title}</h1>
	<input type="text" id="field"/>
	<script>
	google.load("jquery", "1.6.2");
	google.load("jqueryui", "1.8.14");
	
	google.setOnLoadCallback(function() {
	    	
		jsonService.nb.getPlaces({
			params : [
			],
			onSuccess : function(result) {
				var suggestions = [];
				$.each(result, function(i, val) {
					suggestions.push(val.title);
				});

				// http://docs.jquery.com/UI/API/1.8/Autocomplete  
		        $("#field").autocomplete({  
		            source: suggestions,
		            delay : 0
		        });				
			},
			onException : function(e) {
				alert("Unable to compute because: " + e);
				return true;
			}
		});
	});

	</script>
</body>
</html>