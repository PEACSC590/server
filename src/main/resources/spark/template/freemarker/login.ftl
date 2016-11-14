<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>Login | PEAbay</title>
    <#include "/partials/head.ftl">
  </head>

  <body>

  <!--
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" id="title">PEABay</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">


          </ul>
        </div>
      </div>
    </nav>
  -->

  <div class="container">

    <div class="load">
      <form id="loginForm">
        <!--<span class="btn btn-default btn-file">
        Browse Files <input type="file" class="file_bag" id="input">
      </span>-->

      <div class="form-group textbgform">
        <label for="imageUrl">Exeter Username</label>
        <input type="text" class="form-control" name="userID" id="userID">
      </div>

      <div class="form-group textbgform">
        <label for="title">Exeter Password</label>
        <input type="text" class="form-control" name="password" id="password">
      </div>
    </div>
    <button class="btn btn-default btn-file" id="upload" onclick="submitForm()">Upload</button>
  </div>
</form>

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
            			       <button class="btn login" href="HomePage.ftl" name="Submit" value="Login" type="submit">Login</button>
                       </div>
								</div>
              </div>
						</div>





  </div>

	<#include "/partials/scripts.ftl">

    <script>

    function submitForm() {
      console.log("in submitForm");

      var formData = $('#loginForm').serializeArray();

      console.log("formData: " + formData);
      console.log("userID:" + formData.userID)
      console.log("userID value: " + document.getElementById("userID").value);
      console.log("password value: " + document.getElementById("password").value);
      var userData = {
        userID: document.getElementById("userID").value,
        password: document.getElementById("password").value,
      };

      //console.log({ userID: userID, password: password });

      $.ajax({
        type: 'POST',
        url: '/login',
        data: { userID: document.getElementById("userID").value, password: document.getElementById("password").value},
        success: uploadSuccess,
        dataType: 'json'
      });
    }


    function uploadSuccess() {

      alert("login successful!");

      // TODO: redirect to homepage
    }


    </script>
  </body>
</html>
