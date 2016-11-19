<!DOCTYPE html>
<html lang="en">
<head>
	<title>Dashboard | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

  <#include "/partials/nav.ftl">
  
<#macro itemsTable items date>
	<table class="table textbgtable">
		<thead>
		  <tr>
		    <th>Item</th>
		    <#if date><th>Date Bought</th></#if>
		    <th>Price</th>
		  </tr>
		</thead>     
		<tbody>
		<#list items as item>
	        <tr>
				<td>${item.name}</td>
				<#if date>
					<td>
					<#if item.dateBought??>${item.dateBought?number_to_datetime}
					<#else>-
					</#if>
					</td>
				</#if>
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
          <a href="pendingitems" class="list-group-item">Pending Items</a>
        </div>
      </div>
      <div class="jumbotron jumbotron-dashboard">
		
        <h1 class="display-3">Dashboard</h1>
        <h4 class="lead">- Your Transaction History on PEAbay -</h4>
        

      </div>
      
    </div>
  </div>
	
  <div class="container-fluid">
    <div class="row">

        <div class="container-fluid">

            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Past Purchases</h2>
              <@itemsTable items=itemsBought date=true/>
            </div>
            
            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Items for Sale</h2>
              <@itemsTable items=itemsListed date=false/>
            </div>

            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Past Sales</h2>
              <@itemsTable items=itemsSold date=true/>
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
