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
	
	<div class="container box wide noshadow">
		<blockquote>Programėlė padeda susirasti barą pagal jame pilstomo alaus rūšis. Skirta žmonėms, kurie mėgsta įvairaus skonio alų ir nėra prisirišę prie vienos
			girdyklos.</blockquote>

		<p>
			Šią programėlę sukūrė Gytis, Juozas, Laurynas, Vincentas, Saulius, Simonas per <a href="http://www.omnitel.lt/dirbtuves">Omnitel programėlių dirbtuvių</a> savaitgalį, kuriame
			buvo apdovanoti, kaip geriausi naujo turinio kategorijoje.
		</p>

		<img src="/img/htc-alaus-radaras-1.png" style="float:right;"/>
		
		<h3>Veikiančios funkcijos:</h3>
		<ul>
			<li><strong>Artimiausio baro paieška.</strong></li>
			<li><strong>Paieška pagal alaus rūšį / šalį / tipą.</strong></li>
			<li><strong>Režimas ištroškusiems nuotykių ieškotojams.</strong></li>
			<li><strong>Bokalų skaičiuoklė.</strong></li>
			<li><strong>Duomenų atnaujinimas iš interneto.</strong><br /> tikimės suvesti visos Lietuvos geriausius alubarius.</li>
		</ul>

		<h3>Padėkite mums:</h3>
		<ul>
			<li><strong><a href="https://spreadsheets.google.com/viewform?formkey=dGtSczg2Z0ptNmNPRU1IU0FXZ2N0SlE6MQ">Praneškite</a> apie savo mėgstamiausią barą!</strong></li>
			<li><strong><a href="https://spreadsheets.google.com/viewform?formkey=dFR4UVpibTBQU0tUVzlZTGtfRF9NZUE6MQ">Pasiūlykite</a> naujus perspėjimus bokalų skaičiuoklei.</strong></li>
			<li><strong>Praneškite apie klaidas ar neatitikimus: <a href="mailto:info@alausradaras.lt">info@alausradaras.lt</a></strong></li>
		</ul>

		<h3>Ateities planai:</h3>
		<ul>
			<li><strong>Leisti vartotojams atnaujinti vietas ir alaus pasiūlą.</strong> </li>
			<li><strong>Rėpliojimo po barus maršrutai.</strong>	</li>
			<li><strong>Integracija su soc. tinklais.</strong> </li>
		</ul>
	</div>
	
	<jsp:include page="/jsp/footer.jsp" />
</body>

</html>