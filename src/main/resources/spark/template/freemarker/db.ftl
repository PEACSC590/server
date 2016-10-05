<!DOCTYPE html>
<html>
<head>
  <#include "head.ftl">
</head>

<body>

<div class="container">

  <h1>Database Output</h1>
    <ul>
    <#list results as x>
      <li> ${x} </li>
    </#list>
    </ul>

</div>

</body>
</html>
