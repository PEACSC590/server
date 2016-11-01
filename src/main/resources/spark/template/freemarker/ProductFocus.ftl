<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${item.name} | PEAbay</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Custom CSS -->
    <link href="ProductFocus.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">

                <img id="brandimage" src="https://upload.wikimedia.org/wikipedia/en/d/d7/Phillips_Exeter_Academy_Seal.png">
                <a class="navbar-brand">
                  PEAbay
                </a>
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
                    <li>
                        <a href="profile.ftl">Profile</a>
                    </li>

                    <li><a href="login.ftl">Log Out</a></li>

                    <form class="navbar-form navbar-left" action="items-page.ftl">
                      <input type="text" href="items-page.ftl" class="form-control" placeholder="Search">
                    </form>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container-fluid">

        <div class="row">

            <div class="col-md-3">
                <p class="lead">Shop Name</p>
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

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; PEAbay 2016</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
