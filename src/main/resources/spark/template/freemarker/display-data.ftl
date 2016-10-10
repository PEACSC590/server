<!DOCTYPE html>
<html>
<head>
  <#include "head.ftl">
</head>

<body>

<!-- for demo purposes only -->
<#list data?keys as key>
  <p> <b>${key}</b> ${data[key]} </p>
</#list>

</body>
</html>
