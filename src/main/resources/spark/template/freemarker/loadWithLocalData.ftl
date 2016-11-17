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
				
				
			window.location.href = dest + "?" + $.obj2Qs(data);
		</script>
	</body>
</html>