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
      <div class="jumbotron jumbotron-login">
	      <div class="form-group textbgform">
	        <label for="imageUrl">Exeter Username</label>
	        <input type="text" class="form-control" name="userID" id="userID" required>
	      </div>

	      <div class="form-group textbgform">
	        <label for="title">Exeter Password</label>
	        <input type="password" class="form-control" name="password" id="password" required>
	      </div>
	      </div>

	      <div class="form-group" id="terms">
              <div class="conditions">
								<div class="col-xs-12">
                  <label style="color: #fff">Terms and conditions</label>
										<div style="border: 1px solid #e5e5e5; height: 200px; overflow: auto; padding: 10px; background:#fff;">
												<p>Terms and Conditions ("Terms") 

<br><br>Last updated: 11/18/16 

<br><br>Please read these Terms and Conditions ("Terms", "Terms and Conditions") carefully before using the PEAbay website operated by PEAbay. 

<br>Your access to and use of the Service is conditioned on your acceptance of and compliance with these Terms. These Terms apply to all visitors, users and others who access or use the Service. 

<br><br>By accessing or using the Service you agree to be bound by these Terms. If you disagree with any part of the terms then you may not access the Service. Failing to adhere by these terms may result in your account being banned from the server.

<br><br>Content 
<br>PEAbay allows you to post, buy, sell, and view items within the Exeter community. You are responsible for providing accurate information with regards to product details, pricing, and appearance. 

<br><br>Sales and Purchases 
<br>If you wish to purchase any product or service made available through PEAbay, you may be asked to supply certain information relevant to your Purchase including and arrange your own time for monetary exchanges. PEAbay is not responsible for fraudulent purchases or lost or stolen property. 

<br><br>Ebook 
<br>All members of the Exeter community are expected to adhere to Ebook rules and will be held accountable for any actions that break Ebook policies. Any inappropriate behavior or use of PEAbay will be immediately recorded by website administrators and reported to the Dean’s office for further action.

<br><br>Changes 
<br>We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a revision is material we will try to provide at least 1 day's notice prior to any new terms taking effect. What constitutes a material change will be determined at our sole discretion. 

<br><br>If you have any questions about these Terms, please contact peabay.bot@gmail.com
</p>
										</div>
										<div class="col-xs-6 col-xs-offset-3">
												<div class="checkbox">
														<input type="checkbox" id="agree-button" name="agree" value="agree">
														<label for="agree-button" style="color: #fff">Agree with the terms and conditions</label>
												</div>
										</div>
                    <div>
            			       <button class="btn btn-file" type="submit" id="upload">Login</button>
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
	      //if (data.success === "true") console.log("login successful!");
	     // else if (data.success === "false") console.log("login failed.");
		
		//console.log(data);
		//console.log(data.userToken);
	      if (data.success === "true" && data.userToken) {
	      	localStorage.setItem("userID", userID);
	      	localStorage.setItem("userToken", data.userToken);
          window.location.replace("/dashboard");
	      } else {
	      	alert("Bad username or password.");
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
