<html>
  <head>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">

      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


      <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
      <title>${item.name} | PEAbay</title>

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

      <link rel="stylesheet" type="text/css" href="stylesheet.css">
      <link rel="stylesheet" type="text/css" href="sidebar.css">


  </head>

  <body>

    <!-- Sidebar -->
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
                <a href="#">
                    PEAbay
                </a>
            </li>
            <li>
                <a href="#">Books</a>
            </li>
            <li>
                <a href="#">Clothes</a>
            </li>
            <li>
                <a href="#">Furniture</a>
            </li>
            <li>
                <a href="#">Food</a>
            </li>
            <li>
                <a href="#">Other</a>
            </li>
        </ul>
    </div>
    <!-- /#sidebar-wrapper -->

    <div id="page-content-wrapper">
      <nav class="navbar navbar-inverse">
       <div class="container-fluid">
         <div class="navbar-header">
           <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
             <span class="sr-only">Toggle navigation</span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
           </button>
           <a class="navbar-brand" href="#">PEABay</a>
         </div>
         <div id="navbar" class="collapse navbar-collapse">
           <ul class="nav navbar-nav">
             <li><a href="#">Home</a></li>
             <li><a href="#about">About</a></li>
             <li><a href="#contact">Contact</a></li>
           </ul>
           <ul class="nav navbar-nav navbar-right">
             <li><a href="dashboard.ftl">Dashboard</a></li>
             <li><a href="#">Settings</a></li>
             <li><a href="#">Profile</a></li>

             <form class="navbar-form navbar-right">
               <input type="text" class="form-control" placeholder="Search">
             </form>

             <li><a href="#">Advanced Search</a></li>
             </ul>




         </div><!--/.nav-collapse -->
       </div>
     </nav>

     <div class="container">
       <div class="header">
         <div class="col-lg-12">
           <h1 class="page-header">Upload

           </h1>
         </div>
       </div>

       <span class="btn btn-default btn-file">
         Browse Files <input type="file" class="file_bag" id="input">
       </span>

       <form class="titleInput">
         <label for="title">Title:</label>
         <input type="text" class="form-control" id="title">
       </form>
       <form class="priceInput">
         <label for="price">Price ($):</label>
         <input type="text" class="form-control" id="price">
       </form>
       <form class="descriptionInput">
         <label for="description">Description:</label>
         <input type="text" class="form-control" id="description">
       </form>
       <button class="btn btn-default btn-file" id="upload">Upload</button>

       <hr>
     </div>

    <!-- /.container -->

    </div>

 <!-- Bootstrap core JavaScript
 ================================================== -->
 <!-- Placed at the end of the document so the pages load faster -->
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
 <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
 <script src="../../dist/js/bootstrap.min.js"></script>
 <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
 <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>

</html>
