<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" href="../../favicon.ico">

  <title>PEAbay Dashboard</title>

  <!-- Bootstrap core CSS -->
  <link href="../../dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
  <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="dashboard.css" rel="stylesheet">

  <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
  <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
  <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>PEAbay</title>

  <!-- Bootstrap -->
  <link href="css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- linking login stylesheet -->
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
  <link rel="stylesheet" type="text/css" href="sidebar.css">

  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>

  <div id="sidebar-wrapper">
      <ul class="sidebar-nav">
          <li class="sidebar-brand">
              <a>
                  Categories
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
         <a class="navbar-brand" href="HomePage.ftl">PEABay</a>
       </div>
       <div id="navbar" class="collapse navbar-collapse">
         <ul class="nav navbar-nav">
           <li><a href="HomePage.ftl">Home</a></li>
           <li><a href="items-page.ftl">For Sale</a></li>
           <li><a href="about.ftl">About</a></li>
           <li><a href="contact.ftl">Contact</a></li>

           <li class="active"><a href="dashboard.ftl">Dashboard</a></li>
           <li><a href="settings.ftl">Settings</a></li>
           <li><a href="profile.ftl">Profile</a></li>

           <form class="navbar-form navbar-left" action="items-page.ftl">
             <input type="text" class="form-control" placeholder="Search">
           </form>

           <li><a href="login.ftl">Log Out</a></li>
           </ul>




       </div><!--/.nav-collapse -->
     </div>
   </nav>
    
        <div class="container" style="width:100%">
          <h1 class="page-header">Dashboard</h1>

          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="">
              <h4>Label</h4>
              <span class="text-muted">Product</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="">
              <h4>Label</h4>
              <span class="text-muted">Product</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="">
              <h4>Label</h4>
              <span class="text-muted">Product</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="">
              <h4>Label</h4>
              <span class="text-muted">Product</span>
            </div>
          </div>


          <div class="container">
        
              <div class="table-responsive col-md-5">
                <h2 class="sub-header">Past purchases</h2>
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Item</th>
                      <th>Date</th>
                      <th>Price</th>
                      <th>Seller</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>

                  </tbody>
                </table>
              </div>

              <div class="table-responsive col-md-5">
                <h2 class="sub-header">Past sales</h2>
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Item</th>
                      <th>Date</th>
                      <th>Price</th>
                      <th>Seller</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>
                    <tr>
                      <td>insert item</td>
                      <td>10/14/15</td>
                      <td>$5.00</td>
                      <td>me</td>

                    </tr>

                  </tbody>
                </table>
              </div>




       
          </div>
        </div>



    </body>
    </html>
