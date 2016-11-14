<html>
<head>
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

  <!-- Optional theme -->


  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>Upload | PEAbay</title>

  <!-- Bootstrap -->
  <link href="css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- linking login stylesheet -->


  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" type="text/css" href="background.css">



</head>

<body>

  <!-- Navigation -->
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
      <!-- Brand and toggle get grouped for better mobile display -->
      <div class="navbar-header">

        <img class="navbar-brand" src="logo.png">

      </div>
      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">

            <li>
                <a href="dashboard.ftl">Dashboard</a>
            </li>

            <li class="active">
                <a href="items-page.ftl">Browse</a>
            </li>
            
            <li>
                <a href="about.ftl">About</a>
            </li>

            <li>
            	<a href="login.ftl">Log Out</a>
            </li>

            <form class="navbar-form navbar-right" action="items-page.ftl">
              <input type="text" href="items-page.ftl" class="form-control" placeholder="Search">
            </form>
        </ul>
      </div>
      <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
  </nav>

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

  <!-- Footer -->
  <footer>
    <div class="row">
      <div class="col-lg-12">
        <p>Copyright &copy; PEAbay 2016</p>
      </div>
    </div>
  </footer>

</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>

<!--<script src="main.js"></script>-->
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
