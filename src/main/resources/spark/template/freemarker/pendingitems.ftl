<!DOCTYPE html>
<html>
<head>
	<title>Pending Items | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

  <#include "/partials/nav.ftl">
  
<#macro itemsTable items date>
	<table class="table textbgtable">
		<thead>
		  <tr>
		    <th>Item</th>
		    <th>Price</th>
		  </tr>
		</thead>     
		<tbody>
		<#list items as item>
	        <tr>
				<td>${item.name}</td>
				
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
          <a href="dashboard" class="list-group-item">My Items</a>
          <a href="upload" class="list-group-item">Upload</a>
          <a href="pendingitems" class="list-group-item active">Pending Items</a>
        </div>
      </div>
      <div class="jumbotron jumbotron-dashboard">
		
        <h1 class="display-3">Pending Items</h1>
        <h4 class="lead">- View your items pending approval-</h4>
        

      </div>
      
    </div>
  </div>
  
    <div class="container-fluid">
    <div class="row">

        <div class="container-fluid">

            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Pending Purchases</h2>
           <@itemsTable items=/>
            </div>
            
            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Pending Sales</h2>
             <@itemsTable items=/>
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
