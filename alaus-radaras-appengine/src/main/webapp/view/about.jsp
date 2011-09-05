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
	
	<div class="container box wide">
		<div class="logo">
			<a href="http://alausradaras.lt/">Alaus Radaras</a>
		</div>
		<!-- logo #end -->
		<p>
			Šią programėlę sukūrė Gytis, Juozas, Laurynas, Vincentas, Saulius, Simonas per <a href="http://www.omnitel.lt/dirbtuves">Omnitel programėlių dirbtuvių</a> savaitgalį, kuriame
			buvo apdovanoti, kaip geriausi naujo turinio kategorijoje.
		</p>

		<blockquote>Programėlė padeda susirasti barą pagal jame pilstomo alaus rūšis. Skirta žmonėms, kurie mėgsta įvairaus skonio alų ir nėra prisirišę prie vienos
			girdyklos.</blockquote>

		<h3>Atsisiųskite nemokamai!</h3>
		<br /> <a href="http://www.appbrain.com/app/alaus-radaras/alaus.radaras"> <img src="img/android-logo.png" ALIGN=MIDDLE /> <b> Android</b> </a> <br /> <a
			href="http://store.ovi.com/content/98606"> <img src="img/ovi-logo.png" ALIGN=MIDDLE /> <b> Nokia</b> </a> <br /> <a
			href="http://itunes.apple.com/lt/app/alaus-radaras/id418197023?mt=8&ls=1"> <img src="img/apple-logo.png" ALIGN=MIDDLE /> <b> iPhone</b> </a><br /> <br />

		<h3>Veikiančios funkcijos:</h3>
		<ul>
			<li><strong>Artimiausio baro paieška.</strong><br /> šiuo metu suvesti tik Vilniaus barai.</li>
			<li><strong>Paieška pagal alaus rūšį / šalį / tipą.</strong>
			</li>
			<li><strong>Režimas ištroškusiems nuotykių ieškotojams.</strong>
			</li>
			<li><strong>Bokalų skaičiuoklė.</strong>
			</li>
		</ul>

		<h3>Padėkite mums:</h3>
		<ul>
			<li><strong><a href="https://spreadsheets.google.com/viewform?formkey=dGtSczg2Z0ptNmNPRU1IU0FXZ2N0SlE6MQ">Praneškite</a> apie savo mėgstamiausią barą!</strong><br /> jeigu
				jame yra pilstomas koks nors įdomus alus.</li>
			<li><strong><a href="https://spreadsheets.google.com/viewform?formkey=dFR4UVpibTBQU0tUVzlZTGtfRF9NZUE6MQ">Pasiūlykite</a> naujus perspėjimus bokalų skaičiuoklei.</strong>
			</li>
			<li><strong>Praneškite apie klaidas ar neatitikimus: <a href="mailto:info@alausradaras.lt">info@alausradaras.lt</a>
			</strong>
			</li>

		</ul>

		<h3>Ateities planai:</h3>
		<ul>
			<li><strong>Kauno miesto barai.</strong>
			</li>
			<li><strong>Duomenų atnaujinimas iš interneto.</strong><br /> tikimės suvesti visos Lietuvos geriausius alubarius.</li>
			<li><strong>iPhone versija.</strong>
			</li>
			<li><strong>Leisti vartotojams atnaujinti vietas ir alaus pasiūlą.</strong>
			</li>
			<li><strong>Rėpliojimo po barus maršrutai.</strong>
			</li>
			<li><strong>Integracija su soc. tinklais.</strong>
			</li>
			<!--<li><strong>Alaus kainos.</strong></li>-->
		</ul>

		<p>&nbsp;</p>


	</div>
	<!-- content #end -->

	<div id="sidebar">
		<a href="http://www.appbrain.com/app/alaus-radaras/alaus.radaras"><img src="img/htc-alaus-radaras-1.png" alt="" />
		</a>

		<p class="copy">
			<a href="mailto:info@alausradaras.lt">info@alausradaras.lt</a>
		</p>

		<p class="designby">
			tema iš <a href="http://templatic.com/" title="templatic.com">Templatic</a>
		</p>
	</div>

	</div>
	<!-- wrapper #end -->
	
	<jsp:include page="/jsp/footer.jsp" />
</body>

</html>