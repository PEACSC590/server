<!DOCTYPE html>
<html>
<head>
  	<title>For Sale | PEAbay</title>
    <#include "/partials/head.ftl">
  </head>

<body>

  <#include "/partials/nav.ftl">

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-2">
        <div class="list-group">

          <a href="items-page.ftl" class="list-group-item active">All Categories</a>
          <a href="books.ftl" class="list-group-item">Books</a>
          <a href="clothes.ftl" class="list-group-item">Clothes</a>
          <a href="furniture.ftl" class="list-group-item">Furniture</a>
          <a href="other.ftl" class="list-group-item">Other</a>
        </div>
      </div>


<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p>List of Items on PEAbay</p>
    <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more &raquo;</a></p>
</div>

      <!-- Example row of columns -->
      <#list items as document>
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
            <p>Product 2--Learn more</p>
            <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
       </div>
        <div class="col-md-4">
          <div class="product">
            <h2>Heading</h2>
            <p>Product 3--Learn more</p>
            <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div>
        <div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 4--Learn more</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 5--Learn more</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 6--Learn more</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 7--Learn more</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 8--Learn more</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div><div class="col-md-4">
          <div class="product">
          <h2>Heading</h2>
          <p>Product 9--Learn more</p>
          <p><a class="btn btn-default" href="ProductFocus.ftl" role="button">View details &raquo;</a></p>
        </div>
        </div>




      </div>
    </div> <!-- /container -->
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
