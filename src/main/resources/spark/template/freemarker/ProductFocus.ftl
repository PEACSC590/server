<!DOCTYPE html>
<html>

<head>
	<title>View Item: ${item.name} | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

    <#include "/partials/nav.ftl">

    <!-- Page Content -->
    <div class="container-fluid">

        <div class="row">

            <!--<div class="col-md-2">
                <p class="lead">${item.name}</p>
                <div class="list-group">
                    <a href="#" class="list-group-item active">Category 1</a>
                    <a href="#" class="list-group-item">Category 2</a>
                    <a href="#" class="list-group-item">Category 3</a>
                </div>
            </div>-->

            <div class="col-md-9">

              <div class="thumbnail">
                <#if item.imageURL??>
                  <img class="img-responsive" src="${item.imageURL}" alt="Product Image">
                <#else>
                  <img class="img-responsive" src="http://placehold.it/800x300" alt="No Product Image Provided">
                </#if>

                  <div class="caption-full">
                      <h4 class="pull-right">&#36;${item.price}</h4>
                      <h4>${item.name} <span class="label label-info">${item.status}</span></h4>
                      <div>
                      <p>${item.description}</p>
                      </div>
                  </div>

                  <!-- tags -->
                  <div>
                  	<#if item.tags??>
	                    <#list item.tags as tag>
	                      <span class="label label-default">${tag}</span>
	                    </#list>
					</#if>
                  </div>
              </div>

            </div> <!-- here -->

        </div>

    </div>
    <!-- /.container -->

    <div class="container">

        <hr>
		<#include "/partials/footer.ftl">

    </div>
    <!-- /.container -->

  <#include "/partials/scripts.ftl">

</body>

</html>
