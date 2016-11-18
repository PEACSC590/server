<!DOCTYPE html>
<html>
<head>
  	<title>For Sale | PEAbay</title>
    <#include "/partials/head.ftl">
  </head>

<body>

  <#include "/partials/nav.ftl">

	<!-- TODO: implement this idea using a query string -->
	<!-- @assignee: Jacob -->
 


<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p>List of Items on PEAbay</p>
    <p><a class="btn btn-primary btn-lg" href="about" role="button">Learn more &raquo;</a></p>
</div>

      <div class="container">
      	<div class="row">
        
	        <#list items as item>
	        <div class="col-md-4">
	          <div class="product">
	            <h2>${item.name}</h2>
	            <p>${item.name}--Learn more</p>
	            <p><a class="btn btn-default" href="list-item?itemID=${item.itemID}" role="button">View details &raquo;</a></p>
	    	</div>
	    	</div>
	    	</#list>
        
        
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
        </div></div>-->
    </div> 
  </div>
  </div>
  <div class="container-fluid">

	<hr>
	<#include "/partials/footer.ftl">

  </div>

  <#include "/partials/scripts.ftl">


    </body>
    </html>
