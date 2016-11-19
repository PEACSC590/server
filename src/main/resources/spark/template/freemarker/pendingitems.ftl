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
           <@itemsTable items=pendingPurchases date=true/>
            </div>
            
            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Pending Sales</h2>
             <@itemsTable items=pendingSales date=true/>
			</div>

        </div>

        </div>
      </div>
      
      
  <div class="caption-full">
                      <button class="btn btn-default" data-toggle="modal" data-target="#sellModal" type="submit">Sell</button>
                      </div>
                  </div>

									<!-- Modal -->
									<div id="sellModal" class="modal fade" role="dialog">
									  <div class="modal-dialog">

									    <!-- Modal content-->
									    <div class="modal-content">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal">&times;</button>
									        <h4 class="modal-title">Confirm Sale</h4>
									      </div>a
									      <div class="modal-body">
									        <p>Some text in the modal.</p>
													<button type="button" class="btn btn-primary" onclick="confirmSale('${item.itemID}', '${item.buyerID}')">Sell</button>
													<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									      </div>

									    </div>

									  </div>
              </div>

  
  
  

    <div class="container">

        <hr>
        <#include "/partials/footer.ftl">

    </div>
	

  <#include "/partials/scripts.ftl">
  
  function confirmSale(itemID, buyerID) {
	console.log('selling item');
	var requestError = function requestError(err) {
		alert("Request error: " + err);
	};

	var requestSuccess = function requestSuccess(data) {
		if (data.status === 'sold')
	  		window.location.href = '/dashboard';
	  	else alert("Error: " + data.error);
	};

  // TODO: validate form inputs

  var userID = localStorage.getItem('userID');
  var userToken = localStorage.getItem('userToken');

  if (!userID || !userToken) {
  	alert('not logged in.');
  	window.location.href = '/login';
  	return;
  }


  console.log({ userID: userID, userToken: userToken, itemID: itemID, buyerID: buyerID });

  $.ajax({
    type: 'POST',
    url: '/sell',
    data: {userToken: userToken, itemID: itemID, buyerID: buyerID},
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}
	</script>
    </body>
    </html>
