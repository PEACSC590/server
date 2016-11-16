<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>Login | PEAbay</title>
    <#include "/partials/head.ftl">
  </head>

  <body>
	<div class="logintitle">
  		<img class="logologin" src="logo.png">
  	</div>

  <div class="container">

    <div class="load">
      <form id="loginForm">
	      <div class="form-group textbgform">
	        <label for="imageUrl">Exeter Username</label>
	        <input type="text" class="form-control" name="userID" id="userID" required>
	      </div>
	
	      <div class="form-group textbgform">
	        <label for="title">Exeter Password</label>
	        <input type="password" class="form-control" name="password" id="password" required>
	      </div>
	      
	      <div class="form-group" id="terms">
              <div class="conditions">
								<div class="col-xs-12">
                  <label style="color: #fff">Terms and conditions</label>
										<div style="border: 1px solid #e5e5e5; height: 200px; overflow: auto; padding: 10px; background:#fff;">
												<p>These are the terms and conditions</p>
										</div>
										<div class="col-xs-6 col-xs-offset-3">
												<div class="checkbox">
														<input type="checkbox" id="agree-button" name="agree" value="agree">
														<label for="agree-button" style="color: #fff">Agree with the terms and conditions</label>
												</div>
										</div>
                    <div>
            			       <button class="btn btn-default btn-file" type="submit" id="upload">Login</button>
                       </div>
								</div>
              </div>
						</div>
      </form>
    </div>
  </div>





  </div>

	<#include "/partials/scripts.ftl">

    <script>
    
    $("#loginForm").on('submit', function(e) {
    	submitForm();
    	e.preventDefault();
    	return false;
    });

    function submitForm() {
    	var requestError = function requestError(err) {
	    	alert("Request error: " + err);
	    };
	
	    var requestSuccess = function requestSuccess(data) {
	      if (data.success === "true") console.log("login successful!");
	      else if (data.success === "false") console.log("login failed.");
	
	      if (data.success === "true" && data.userToken) {
	      	localStorage.setItem("userID", userID);
	      	localStorage.setItem("userToken", userToken);
	      } else {
	      	alert("login failed.");
	      }
	    };
    
      //console.log("in submitForm");

      var formData = $('#loginForm').serializeObject();
      
      if (formData.agree !== 'agree') {
      	alert('You must accept the terms and conditions.');
      	return;
      }

      var userID = formData.userID,
      	password = formData.password;

      var userData = {
        userID: userID,
        password: password,
      };

      $.ajax({
        type: 'POST',
        url: '/login',
        data: { userID: document.getElementById("userID").value, password: document.getElementById("password").value },
        success: requestSuccess,
        error: requestError,
        dataType: 'json',
      });
    }


    </script>
  </body>
</html>
