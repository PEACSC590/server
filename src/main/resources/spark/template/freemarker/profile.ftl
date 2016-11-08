<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <meta name="description" content="">
  <meta name="author" content="">
  <!-- Latest compiled and minified CSS -->
  <!-- Latest compiled and minified CSS -->
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">


  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  <link rel="stylesheet" type="text/css" href="background.css">


  <!-- Latest compiled and minified JavaScript -->


  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
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
                      <a href="HomePage.ftl">Home</a>
                  </li>
                  <li>
                      <a href="items-page.ftl">For Sale</a>
                  </li>
                  <li>
                      <a href="about.ftl">About</a>
                  </li>
                  <li>
                      <a href="contact.ftl">Contact</a>
                  </li>
                  <li>
                      <a href="dashboard.ftl">Dashboard</a>
                  </li>
                  <li>
                      <a href="settings.ftl">Settings</a>
                  </li>
                  <li class="active">
                      <a href="profile.ftl">Profile</a>
                  </li>

                  <li><a href="login.ftl">Log Out</a></li>

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
          <a href="myitems.ftl" class="list-group-item">My Items</a>
          <a href="upload.ftl" class="list-group-item">Upload</a>
          <a href="cancelpendingsale.ftl" class="list-group-item">Pending Sales</a>
        </div>
      </div>
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


    </body>
    </html>
