<!DOCTYPE html>
<html lang="en">
<head>
	<title>Dashboard | PEAbay</title>
	<#include "/partials/head.ftl">
</head>

<body>

  <#include "/partials/nav.ftl">

  <div class="container-fluid">
    <div class="row">


      <div class="jumbotron jumbotron-dashboard">

        <h1 class="display-3">Dashboard</h1>
        <h4 class="lead">- Your Transaction History on PEAbay -</h4>

      </div>

       <!-- <div class="row placeholders">
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
        </div> -->


        <div class="container-fluid">


            <div class="table-responsive col-md-4">


              <h2 class="sub-header textbgdash2">Past Purchases</h2>
              <table  class="table textbgtable">
                 <thead >
                  <tr>
                    <th>Item</th>
                    <th>Date</th>
                    <th>Price</th>

                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <#list itemsBought as document, itemName>
                    <td>${itemName}</td>
                   </#list>
                    <#list itemsBought as document, dateBought>
                    <td>${dateBought}</td>
                   </#list>
                    <#list itemsBought as document, itemPrice>
                    <td>"$" + ${"itemPrice"}</td>
                   </#list>

                  </tr>


                </tbody>
              </table>
            </div>


            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Items for Sale</h2>

              <table  class="table textbgtable">
                <thead>
                  <tr>
                    <th>Item</th>
                    <th>Date</th>
                    <th>Price</th>

                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <#list itemsUploaded as document, itemName>
                    <td>${itemName}</td>
                   </#list>
                    <#list itemsUploaded as document, dateBought>
                    <td>${dateBought}</td>
                   </#list>
                    <#list itemsUploaded as itemsUploaded, itemPrice>
                    <td>"$" + ${itemPrice}</td>
                   </#list>

                  </tr>

                </tbody>
              </table>
            </div>

            <div class="table-responsive col-md-4">
              <h2 class="sub-header textbgdash2">Past Sales</h2>
              <table  class="table textbgtable">
                <thead>
                  <tr>
                    <th>Item</th>
                    <th>Date</th>
                    <th>Price</th>

                  </tr>
                </thead>
                <tbody>
                   <tr>
                    <#list itemsSold as document, itemName>

                    <td>${itemName}</td>

                   </#list>
                    <#list itemsSold as document, dateBought>

                    <td>${dateBought}</td>

                   </#list>
                    <#list itemsSold as document, itemPrice>

                    <td>"$" + ${itemPrice}</td>

                    </#list>
    </tr>
                </tbody>
              </table>
            </div>

        </div>


        </div>
      </div>



    </div>
    <div class="container">

        <hr>
		<#include "/partials/footer.ftl">

    </div>
    
    <#include "/partials/scripts.js">

    <script>

  /*
  *formData: { name: "name", imageURL: "sdf" }
  */

  // TODO: validate form inputs

  var userID = localStorage.get('userID');
  var userToken = localStorage.get('userToken');

  // TODO: validate that userID and userToken are not null

  $.ajax({
    type: 'GET',
    url: '/dashboard',
    data: { userID: userID, userToken: userToken},
    success: uploadSuccess,
    dataType: 'json'
  });

</script>

    </body>
    </html>
