<!DOCTYPE html>
<html lang="en">
<head>
	<title>Dashboard | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

  <#include "/partials/nav.ftl">
  
<#macro itemsTable items>
	<table class="table textbgtable">
		<thead>
		  <tr>
		    <th>Item</th>
		    <th>Date</th>
		    <th>Price</th>
		  </tr>
		</thead>     
		<tbody>
		<#list items as item>
	        <tr>
				<td>${item.name}</td>
				<td><#if item.dateBought??>${item.dateBought}</#if></td>
				<td>$${item.price}</td>
			</tr>
		</#list>
		</tbody>
	</table>	
</#macro>
  
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-2">
        <div class="list-group">
          <a href="dashboard" class="list-group-item active">My Items</a>
          <a href="upload" class="list-group-item">Upload</a>
          <a href="pendingitems" class="list-group-item">Pending Sales</a>
        </div>
      </div>
      <h1 class="page-header textbgup">Upload</h1>
    </div>
  </div>
	
  <div class="container-fluid">
    <div class="row">


      <div class="jumbotron jumbotron-dashboard">

        <h1 class="display-3">Dashboard</h1>
        <h4 class="lead">- Your Transaction History on PEAbay -</h4>

      </div>

       <!-- <div class="row placeholders">
          <div class="col-xs-6 col-sm-3 placeholder">
            <img src="">
            <h4>Label</h4>
            <span class="text-muted">Product</span>
          </div>
          <div class="col-xs-6 col-sm-3 placeholder">
            <img src="">
            <h4>Label</h4>
            <span class="text-muted">Product</span>
          </div>
          <div class="col-xs-6 col-sm-3 placeholder">
            <img src="">
            <h4>Label</h4>
            <span class="text-muted">Product</span>
          </div>
          <div class="col-xs-6 col-sm-3 placeholder">
            <img src="">
            <h4>Label</h4>
            <span class="text-muted">Product</span>
          </div>
        </div> -->


        <div class="container-fluid">

            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Past Purchases</h2>
              <@itemsTable items=itemsBought/>
            </div>
            
            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Items for Sale</h2>
              <@itemsTable items=itemsListed/>
            </div>

            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Past Sales</h2>
              <@itemsTable items=itemsSold/>
            </div>

        </div>

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
