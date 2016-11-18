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
				
				<!-- TODO: complete using AJAX instead -->
                  <li><a href="#" onclick="return logout()">Log Out</a></li>
	
				<!-- TODO: complete -->
                  <form class="navbar-form navbar-right" action="browse" method="post">
                    <input type="text" href="items-page.ftl" class="form-control" placeholder="Search for Products">
                  </form>
              </ul>
          </div>
          <!-- /.navbar-collapse -->
      </div>
      <!-- /.container -->
  </nav>
  <script>
  function logout() {
  	var requestError = function requestError(err) {
		alert("Request error: " + err);
	};
	
	var requestSuccess = function requestSuccess(data) {
		if (data.success === 'true')
	  		window.location.href = '/login';
	  	else alert("Error: " + data.error);
	};
  
	$.ajax({
	    type: 'POST',
	    url: '/logout',
	    data: { userID: userID, userToken: userToken },
	    success: requestSuccess,
	    error: requestError,
	    dataType: 'json',
	 });
	  
	 return false;
  }
  </script>