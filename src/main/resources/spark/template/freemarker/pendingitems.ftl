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

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; PEAbay 2016</p>
                </div>
            </div>
        </footer>

    </div>


  <#include "/partials/scripts.js">

    </body>
    </html>
