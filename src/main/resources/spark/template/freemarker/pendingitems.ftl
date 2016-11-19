<!DOCTYPE html>
<html>
<head>
	<title>Pending items | PEAbay</title>

    <#include "/partials/head.ftl">
  </head>

<body>

  <#include "/partials/nav.ftl">

<div class="jumbotron jumbotron-forsale">
    <h1>PEAbay</h1>
    <p class="lead">- List of Items on PEAbay -</p>
    <a class="btn btn-primary btn-lg" href="about" role="button">Learn more &raquo;</a>
</div>

      <div class="container">
      	<div class="row">
	        <#list pendingSales as item>
	        <div class="col-md-4">
	          <div class="product">
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
	            	<button type="button" class="btn btn-primary" onclick="cancelPendingSale('${item.itemID}', '${item.buyerID}')">Cancel sale</button>
	            	<button type="button" class="btn btn-primary" onclick="confirmSale('${item.itemID}', '${item.buyerID}')">Confirm sale</button>
	            </p>
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

function cancelPendingSale(itemID, buyerID) {
	console.log('confirm selling item');
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


  console.log({ userID: userID, userToken: userToken, itemID: itemID, buyerID: buyerID });

  $.ajax({
    type: 'POST',
    url: '/cancelPendingSale',
    data: {userID: userID, userToken: userToken, itemID: itemID, buyerID: buyerID},
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}


</script>

    </body>
    </html>
