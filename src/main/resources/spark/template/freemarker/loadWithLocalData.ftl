<!DOCTYPE html>
<html>
	<head>
		<title>PEAbay</title>
	</head>
	<body>
		<#include "/partials/scripts.ftl">

		<script>
		var dest = "${redirect}",
			get = ${get};
		var data = {};

		for (var i = 0; i < get.length; i++) {
			data[get[i]] = window.localStorage.getItem(get[i]);
		}
		
		if (data.userToken && data.userToken === null && data.userID && data.userID === null)
			window.location.href = "/login";
		
		var dataQs = $.obj2Qs(data);
		window.location.href = dest + (window.location.search ? window.location.search + "&" + dataQs : "?" + dataQs);
		</script>
	</body>
</html>