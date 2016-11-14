<!DOCTYPE html>
<html>
<head>
	<title>Pending Items | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

  <#include "/partials/nav.ftl">

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-2">
        <div class="list-group" >
          <a href="myitems.ftl" class="list-group-item">My Items</a>
          <a href="upload.ftl" class="list-group-item">Upload</a>
          <a href="pendingitems.ftl" class="list-group-item active">Pending Sale</a>
        </div>
      </div>
    </div>
    <div class="container">

        <hr>
        <#include "/partials/footer.ftl">

    </div>


  <#include "/partials/scripts.ftl">

    </body>
    </html>