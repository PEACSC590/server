<!DOCTYPE html>
<html lang="en">
  <head>
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
    <link rel="stylesheet" type="text/css" href="background.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- linking login stylesheet -->
    <link href="login.css" rel="stylesheet">

    <meta name="viewport" content="width=device-width, initial-scale=1">
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

    <div class = "container">
    	<div class="wrapper">
    		<form id="login" name="Login_Form" class="form-signin">
    		    <h3 class="form-signin-heading" style="text-align: center;">PEAbay</h3>
    			  <hr class="colorgraph"><br>

    			  <input type="text" class="form-control" id="username" name="Username" placeholder="Exeter Username" required="" autofocus="" />
    			  <input type="password" class="form-control" id="password" name="Password" placeholder="Exeter Password" required=""/>


          <!-- Hunter dont add this back yet we gon talk about this in class
						<div class="form-group" id="terms">
								<label class="col-xs-3 control-label">Terms of use</label>
								<div class="col-xs-9">
										<div style="border: 1px solid #e5e5e5; height: 200px; overflow: auto; padding: 10px; background:#fff;">
												<p>Lorem ipsum dolor sit amet, veniam numquam has te. No suas nonumes recusabo mea, est ut graeci definitiones. His ne melius vituperata scriptorem, cum paulo copiosae conclusionemque at. Facer inermis ius in, ad brute nominati referrentur vis. Dicat erant sit ex. Phaedrum imperdiet scribentur vix no, ad latine similique forensibus vel.</p>
												<p>Dolore populo vivendum vis eu, mei quaestio liberavisse ex. Electram necessitatibus ut vel, quo at probatus oportere, molestie conclusionemque pri cu. Brute augue tincidunt vim id, ne munere fierent rationibus mei. Ut pro volutpat praesent qualisque, an iisque scripta intellegebat eam.</p>
												<p>Mea ea nonumy labores lobortis, duo quaestio antiopam inimicus et. Ea natum solet iisque quo, prodesset mnesarchum ne vim. Sonet detraxit temporibus no has. Omnium blandit in vim, mea at omnium oblique.</p>
												<p>Eum ea quidam oportere imperdiet, facer oportere vituperatoribus eu vix, mea ei iisque legendos hendrerit. Blandit comprehensam eu his, ad eros veniam ridens eum. Id odio lobortis elaboraret pro. Vix te fabulas partiendo.</p>
												<p>Natum oportere et qui, vis graeco tincidunt instructior an, autem elitr noster per et. Mea eu mundi qualisque. Quo nemore nusquam vituperata et, mea ut abhorreant deseruisse, cu nostrud postulant dissentias qui. Postea tincidunt vel eu.</p>
												<p>Ad eos alia inermis nominavi, eum nibh docendi definitionem no. Ius eu stet mucius nonumes, no mea facilis philosophia necessitatibus. Te eam vidit iisque legendos, vero meliore deserunt ius ea. An qui inimicus inciderint.</p>
										</div>
										<div class="col-xs-6 col-xs-offset-3">
												<div class="checkbox">
														<input type="checkbox" id="agree-button" name="agree" value="agree">
														<label for="agree-button" style="color: #fff">Agree with the terms and conditions</label>
												</div>
										</div>
								</div>
						</div>
          -->


    			  <button class="btn btn-lg btn-primary btn-block" href="HomePage.ftl" name="Submit" value="Login" type="submit">Login</button>

				</form>

    <script>
    $(document).ready(function() {

        $('#login').submit(function() {

						if (!validateForm(this)) {
							alert("Invalid form");
							return;
						}

            $.ajax({
                type: "POST",
                url: '/login',
                data: {
                    username: $("#username").val(),
                    password: $("#password").val()
                },
                success: function(data)
                {
                    if (data === 'correct') { // or whatever response
                        window.location.replace('redirect url');
                    } else {
                        alert(data);
                    }
                }
            });

        });

    });

		function validateForm(form) {
			if (!$(form).find("#agree-button").val() === "agree") return false;

			return true;
		}
    </script>

		<script>

    // Update the value of "agree" input when clicking the Agree/Disagree button
    $('#agreeButton, #disagreeButton').on('click', function() {
        var whichButton = $(this).attr('id');

        $('#registrationForm')
            .find('[name="agree"]')
                .val(whichButton === 'agreeButton' ? 'yes' : 'no');
    });
});
		</script>

  </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
