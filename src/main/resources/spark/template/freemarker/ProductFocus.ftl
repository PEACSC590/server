<!DOCTYPE html>
<html>

<head>
	<title>View Item: ${item.name} | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

    <#include "/partials/nav.ftl">
    <#include "/partials/details-menu.ftl">

    <!-- Page Content -->
    <div class="container-fluid">

        <div class="row">

            <div class="col-md-2">
                <p class="lead">${item.name}</p>
                <div class="list-group">
                    <a href="#" class="list-group-item active">Category 1</a>
                    <a href="#" class="list-group-item">Category 2</a>
                    <a href="#" class="list-group-item">Category 3</a>
                </div>
            </div>

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
                      <a>${item.description}</a>
                      </div>
                  </div>

                  <!-- tags -->
                  <div>
                    <#list item.tags as tag>
                      <span class="label label-default">${tag}</span>
                    </#list>

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

  /*
  *formData: { name: "name", imageURL: "sdf" }
  */

  // TODO: validate form inputs

  var item.name = localStorage.get('name');
  var item.imageURL = localStorage.get('imageURL');
  var item.description = localStorage.get('description');
  var item.status = localStorage.get('status');
  var item.price = localStorage.get('price');
  var item.tags = localStorage.get('tags');


  // TODO: validate that item are not null

  $.ajax({
    type: 'GET',
    url: '/list-item?itemID=[number]',
    data: { item: name, item: imageURL, item: description, item: status, item: price, item: tags},
    success: uploadSuccess,
    dataType: 'json'
  });

</script>

</body>

</html>
