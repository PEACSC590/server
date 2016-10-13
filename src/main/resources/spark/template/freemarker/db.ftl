<!DOCTYPE html>
<html>
<head>
  <#include "head.ftl">
</head>

<body>

<div class="container">

  <h1>Database Output</h1>
  	<#if error??>
		<p style="color: red">${error}</p>
	</#if>
	<#if results??>
	    <ul>
			<#list results as x>
		    	<li> ${x} </li>
		    </#list>
	    </ul>
    </#if>

</div>

</body>
</html>
