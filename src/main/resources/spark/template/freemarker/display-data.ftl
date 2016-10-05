<!DOCTYPE html>
<html>
<head>
  <#include "head.ftl">
</head>

<body>

<!-- for demo purposes only -->
<#list data?keys as key>
  <li> <b>${key}</b> ${data[key]} </li>
</#list>

</body>
</html>
