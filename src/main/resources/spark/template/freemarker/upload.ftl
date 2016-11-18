<html>
<head>
	<title>Upload | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

  <#include "/partials/nav.ftl">
  
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-2">
        <div class="list-group">
          <a href="dashboard" class="list-group-item">My Items</a>
          <a href="upload" class="list-group-item active">Upload</a>
          <a href="pendingitems" class="list-group-item">Pending Items</a>
        </div>
      </div>
      <h1 class="page-header textbgup">Upload</h1>
    </div>
  </div>

  <!--       <div class="col-lg-12"> -->


  <div class="container">

  	<#if error??>
	  	<div>
	  		<b style="color: #e00;">${error}</b>
	  	</div>
  	</#if>

    <div class="load">
      <form id="uploadForm">
        <!--<span class="btn btn-default btn-file">
        Browse Files <input type="file" class="file_bag" id="input">
      </span>-->

      <div class="form-group textbgform">
        <label for="imageUrl">Image URL</label>
        <input type="text" class="form-control" name="imageURL" id="imageURL">
      </div>

      <div class="form-group textbgform">
        <label for="title">Item Name</label>
        <input type="text" class="form-control" name="name" id="name">
      </div>
      <div class="form-group textbgform">
        <label for="price">Item Price (dollars and cents)</label>
        <input type="text" class="form-control" name="price" id="price">
      </div>
      <div class="form-group textbgform">
        <label for="description">Item Description</label>
        <input type="text" class="form-control" name="description" id="description">
      </div>
      <div class="form-group textbgform">
        <label for="tags">Item Tags (separate tags by commas)</label>
        <input type="text" class="form-control" name="tags" id="tags">
      </div>
    </div>
    <button class="btn btn-default" type="submit" id="upload">Upload</button>
  </div>
</form>

<!-- /.container -->

</div>
<div class="container">

  <hr>
  <#include "/partials/footer.ftl">

</div>

<#include "/partials/scripts.ftl">

<script>

$("#uploadForm").on('submit', function(e) {
	submitForm();
	e.preventDefault();
	return false;
});

function submitForm() {
	var requestError = function requestError(err) {
		alert("Request error: " + err);
	};
	
	var requestSuccess = function requestSuccess(data) {
		if (data.status === 'listed')
	  		window.location.href = '/dashboard';
	  	else alert("Error: " + data.error);
	};


  var formData = $('#uploadForm').serializeObject();

  // TODO: validate form inputs

  var item = {
    name: formData.name,
    description: formData.description,
    price: parseInt(formData.price),
    tags: formData.tags.split(','),
    imageURL: formData.imageURL
  };

  console.log({ userID: userID, userToken: userToken, item: JSON.stringify(item)});

  $.ajax({
    type: 'POST',
    url: '/upload',
    data: { userID: userID, userToken: userToken, item: JSON.stringify(item) },
    success: requestSuccess,
    error: requestError,
    dataType: 'json'
  });
}


</script>
</body>

</html>
