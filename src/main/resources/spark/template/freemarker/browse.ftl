<!DOCTYPE html>
<html>
<head>
	<#if pageName?? && pageName == "browse">
	<title>For Sale | PEAbay</title>
	<#elseif pageName?? && pageName == "search">
	<title>Searching items for sale | PEAbay</title>
	</#if>


    <#include "/partials/head.ftl">
  </head>

<body>

  <#include "/partials/nav.ftl">

<#if pageName?? && pageName == "browse">
<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p class="lead">- List of Items on PEAbay -</p>
    <a class="btn btn-primary btn-lg" href="about" role="button">Learn more &raquo;</a>
</div>
<#elseif pageName?? && pageName == "search">
<div class="jumbotron jumbotron-forsale">
    <h3>Searching items that match <pre>"${query}"</pre></h3>
</div>
</#if>

      <div class="container">
      	<div class="row">
	        <#list items as item>
	        <div class="col-md-4">
	          <div class="product">
							<#if item.imageURL??>
								<a href="list-item?itemID=${item.itemID}"><img class="img-responsive" src="${item.imageURL}" alt="Product Image"></a>
							<#else>
								<img class="img-responsive" src="http://placehold.it/800x300" alt="No Product Image Provided">
							</#if>
	            <a href="list-item?itemID=${item.itemID}"><h2>${item.name}</h2></a>
	            <p>${item.name}--Learn more</p>
	            <p><a class="btn btn-default" href="list-item?itemID=${item.itemID}" role="button">View details &raquo;</a></p>
    		  </div>
	    	</div>
	    	</#list>
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
