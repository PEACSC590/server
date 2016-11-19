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

            <div class="col-md-9" style="float: none; margin: 0px auto">

              <div class="thumbnail">
                <#if item.imageURL??>
                  <img class="img-responsive" src="${item.imageURL}" alt="Product Image">
                <#else>
                  <img class="img-responsive" src="http://placehold.it/800x300" alt="No Product Image Provided">
                </#if>

                  <div class="caption-full">
                      <h3 class="pull-right">&#36;${item.price}</h3>
                      <h2>${item.name} <span class="badge">${item.status}</span></h2>
                      <div>
                      	<p>Seller is ${item.sellerID}</p>
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
						<#if !(item.isOwnItem?? && item.isOwnItem)>
						<div class="caption-full">
	                      	<button class="btn btn-default" data-toggle="modal" data-target="#buyModal" type="submit">Buy</button>
                        </div>
                        </#if>
                  </div>

									<!-- Modal -->
									<div id="buyModal" class="modal fade" role="dialog">
									  <div class="modal-dialog">

									    <!-- Modal content-->
									    <div class="modal-content">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal">&times;</button>
									        <h4 class="modal-title">Confirm Purchase</h4>
									      </div>
									      <div class="modal-body">
									        <p>Please confirm your purchase of ${item.name}</p>
													<button type="button" class="btn btn-primary" onclick="confirmPurchase('${item.itemID}')">Buy</button>
													<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									      </div>

									    </div>

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

<script>


function confirmPurchase(itemID) {
	console.log('buying item');
	var requestError = function requestError(err) {
		alert("Request error: " + err);
	};

	var requestSuccess = function requestSuccess(data) {
		if (data.status === 'pending')
	  		window.location.href = '/pendingitems';
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


  console.log({ userID: userID, userToken: userToken, itemID: itemID });

  $.ajax({
    type: 'POST',
    url: '/buy',
    data: {userID: userID, userToken: userToken, itemID: itemID},
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}


</script>
</body>

</html>
