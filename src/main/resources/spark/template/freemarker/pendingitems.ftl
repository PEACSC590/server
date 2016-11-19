<!DOCTYPE html>
<html>
<head>
	<title>Pending items | PEAbay</title>

    <#include "/partials/head.ftl">
  </head>

<body>

  <#include "/partials/nav.ftl">
  <div class="container-fluid">
  <div class="row">
      <div class="col-md-2">
        <div class="list-group">
          <a href="dashboard" class="list-group-item">My Items</a>
          <a href="upload" class="list-group-item">Upload</a>
          <a href="pendingitems" class="list-group-item active">Pending Items</a>
        </div>
      </div>
      
    </div>
  </div>

<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p class="lead">- Your Pending Sales -</p>
</div>

      <div class="container">
      	<div class="row">
	        <#list pendingSales as item>
	        <div class="col-md-4">
	          <div class="product">
	          <#if item.imageURL??>
								<a href="list-item?itemID=${item.itemID}"><img class="img-responsive" src="${item.imageURL}" alt="Product Image"></a>
							<#else>
								<img class="img-responsive" src="http://placehold.it/800x300" alt="No Product Image Provided">
							</#if>
							
	            <div class="caption-full">
                      <h3 class="pull-right">&#36;${item.price}</h3>
                      <a href="list-item?itemID=${item.itemID}"><h2>${item.name}<span class="badge">${item.status}</span></h2></a>
                      <div>
                      	<p>Pending sale to ${item.buyerID}</p>
                      </div>
                  </div>
	            <p>
	            	<button type="button" class="btn btn-primary" onclick="refuseSale('${item.itemID}')">Cancel sale</button>
	            	<button type="button" class="btn btn-primary" onclick="confirmSale('${item.itemID}', '${item.buyerID}')">Confirm sale</button>
	            </p>
    		  </div>
	    	</div>
	    	</#list>
    	</div> 
	</div>
	
	<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p class="lead">- Your Pending Purchases-</p>
</div>

	<div class="container">
      	<div class="row">
	        <#list pendingPurchases as item>
	        <div class="col-md-4">
	          <div class="product">
	          <#if item.imageURL??>
								<a href="list-item?itemID=${item.itemID}"><img class="img-responsive" src="${item.imageURL}" alt="Product Image"></a>
							<#else>
								<img class="img-responsive" src="http://placehold.it/800x300" alt="No Product Image Provided">
							</#if>
	            <div class="caption-full">
                      <h3 class="pull-right">&#36;${item.price}</h3>
                      <h2>${item.name} <span class="badge">${item.status}</span></h2>
                      <div>
                      	<p>${item.description}</p>
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
	            <p>
	            	<button type="button" class="btn btn-primary" onclick="cancelPendingSale('${item.itemID}')">Cancel purchase</button>
	            </p>
    		  </div>
	    	</div>
	    	</#list>
    	</div> 
	</div>
	
	
  </div>
  

	<hr>
	<#include "/partials/footer.ftl">

  </div>

  <#include "/partials/scripts.ftl">

<script>

function confirmSale(itemID, buyerID) {
	console.log('confirm selling item');
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
    data: {userID: userID, userToken: userToken, itemID: itemID, buyerID: buyerID},
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}

function refuseSale(itemID) {
	var requestError = function requestError(err) {
		alert("Request error: " + err);
	};

	var requestSuccess = function requestSuccess(data) {
		if (data.status === 'listed')
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


  console.log({ userID: userID, userToken: userToken, itemID: itemID});

  $.ajax({
    type: 'POST',
    url: '/cancelPendingSale',
    data: {userID: userID, userToken: userToken, itemID: itemID},
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}

function cancelPendingSale(itemID) {
	var requestError = function requestError(err) {
		alert("Request error: " + err);
	};

	var requestSuccess = function requestSuccess(data) {
		if (data.status === 'listed')
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


  console.log({ userID: userID, userToken: userToken, itemID: itemID});

  $.ajax({
    type: 'POST',
    url: '/cancelPendingSale',
    data: {userID: userID, userToken: userToken, itemID: itemID},
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}



</script>

    </body>
    </html>
