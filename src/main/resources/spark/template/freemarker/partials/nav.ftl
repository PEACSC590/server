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
                      <a href="dashboard">Dashboard</a>
                  </li>
                  <li>
                      <a href="items-page">For Sale</a>
                  </li>
                  <li class="active">
                      <a href="about">About</a>
                  </li>

                  <li><a href="logout">Log Out</a></li>
	
				<!-- TODO: complete -->
                  <form class="navbar-form navbar-right" action="items-page" method="post">
                    <input type="text" href="items-page.ftl" class="form-control" placeholder="Search for Products">
                  </form>
              </ul>
          </div>
          <!-- /.navbar-collapse -->
      </div>
      <!-- /.container -->
  </nav>