<!DOCTYPE html>
<html>
<head>
  	<title>For Sale | PEAbay</title>
    <#include "/partials/head.ftl">
  </head>

<body>

  <#include "/partials/nav.ftl">
  <#include "/partials/details-menu.ftl">

	<!-- TODO: implement this using a query string -->
	<!-- @assignee: Jacob -->
  <!--<div class="container-fluid">
    <div class="row">
      <div class="col-md-2">
        <div class="list-group">

          <a href="items-page.ftl" class="list-group-item active">All Categories</a>
          <a href="books.ftl" class="list-group-item">Books</a>
          <a href="clothes.ftl" class="list-group-item">Clothes</a>
          <a href="furniture.ftl" class="list-group-item">Furniture</a>
          <a href="other.ftl" class="list-group-item">Other</a>
        </div>
      </div>-->


<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p>List of Items on PEAbay</p>
    <p><a class="btn btn-primary btn-lg" href="about.ftl" role="button">Learn more &raquo;</a></p>
</div>

      <!-- Example row of columns -->
<<<<<<< HEAD

=======
      <#list items as document>
>>>>>>> 195e75810009154f7ab7b568d33c4f26d4275e0f
      <div class="container">
        <div class="col-md-4">
          <div class="product">
            <h2>${document.get("itemName")}</h2>
            <p>${document.get("itemName")}--Learn more</p>
            <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </#list>
        </div>
        <div class="col-md-4">
          <div class="product">
            <h2>Heading</h2>
            <p>Product 2</p>
            <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
       </div>
        <div class="col-md-4">
          <div class="product">
            <h2>Heading</h2>
            <p>Product 3</p>
            <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div>
        <div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 4</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 5</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 6</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 7</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 8</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 9</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div>




      </div>
    </div> <!-- /container -->
  </div>
  <div class="container">

	<hr>
	<#include "/partials/footer.ftl">

  </div>

  <#include "/partials/scripts.ftl">


    </body>
    </html>