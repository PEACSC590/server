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
          <a href="dashboard.ftl" class="list-group-item">My Items</a>
          <a href="upload.ftl" class="list-group-item active">Upload</a>
          <a href="pendingitems.ftl" class="list-group-item">Pending Sales</a>
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
        <input type="text" class="form-control" name="imageURL" id="imageUrl">
      </div>

      <div class="form-group textbgform">
        <label for="title">Title</label>
        <input type="text" class="form-control" name="name" id="title">
      </div>
      <div class="form-group textbgform">
        <label for="price">Price ($)</label>
        <input type="text" class="form-control" name="price" id="price">
      </div>
      <div class="form-group textbgform">
        <label for="description">Description</label>
        <input type="text" class="form-control" name="description" id="description">
      </div>
      <div class="form-group textbgform">
        <label for="tags">Tags (separate tags by commas)</label>
        <input type="text" class="form-control" name="tags" id="tags">
      </div>
    </div>
    <button class="btn btn-default btn-file" id="upload" onclick="submitForm()">Upload</button>
  </div>
</form>

<!-- /.container -->

</div>
<div class="container">

  <hr>
  <#include "/partials/footer.ftl">

</div>

<#include "/partials/scripts.js">

<script>

function submitForm() {
  var formData = $('#uploadForm').serializeArray();

  /*
  *formData: { name: "name", imageURL: "sdf" }
  */

  // TODO: validate form inputs

  var userID = localStorage.get('userID');
  var userToken = localStorage.get('userToken');

  // TODO: validate that userID and userToken are not null

  var item = {
    name: formData.name,
    description: formData.description,
    price: parseInt(formData.price),
    tags: formData.tags.split(','),
    imageURL: formData.imageURL
  };

  console.log({ userID: userID, userToken: userToken, item: item });

  $.ajax({
    type: 'POST',
    url: '/upload',
    data: { userID: userID, userToken: userToken, item: item },
    success: uploadSuccess,
    dataType: 'json'
  });
}


function uploadSuccess() {

  alert("upload successful!");

}


</script>
</body>

</html>
