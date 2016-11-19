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
                  <li <#if pageName?? && pageName == "dashboard">class="active"</#if>>
                      <a href="dashboard">Dashboard</a>
                  </li>
                  <li <#if pageName?? && pageName == "browse">class="active"</#if>>
                      <a href="browse">For Sale</a>
                  </li>
                  <li <#if pageName?? && pageName == "about">class="active"</#if>>
                      <a href="about">About</a>
                  </li>

                  <li><a href="#" onclick="return logout()">Log Out</a></li>
                  
                  <li>
                   <form class="navbar-form navbar-right" action="search" method="get">
				    <div class="input-group">
				      <input type="text" name="q" class="form-control" placeholder="Search for Products">
				      <span class="input-group-btn">
				        <button class="btn btn-default" type="submit">Search</button>
				      </span>
				    </div><!-- /input-group -->
				   </form>
				  </li>
              </ul>
          </div>
          <!-- /.navbar-collapse -->
      </div>
      <!-- /.container -->
  </nav>
  <script>
  function logout() {
	$.ajax({
	    type: 'POST',
	    url: '/logout',
	    data: { userID: userID, userToken: userToken },
	    dataType: 'json',
	 }).always(function() {
	 	// clear creds + redirect regardless of the status of the request or the API method
	 	window.localStorage.clear();
  		window.location.href = '/login';
	 });
	  
	 return false;
  }
  </script>