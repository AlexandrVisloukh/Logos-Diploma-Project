$(document).ready(function() {
    let urlParameter = getUrlParameter('id');
    console.log(urlParameter);
    let idUrl = parseInt(getUrlParameter('id'), 10);
    console.log(urlParameter==undefined)
    $("#btnRegister").on("click", function() {
       let a= urlParameter==undefined;
       console.log(a);
        if (a){
             signup();
        } else{
             update(idUrl);
        }
      
    });


});
function update(id) {
    let userName = $("#userName").val();
    let userEmail = $("#userEmail").val();
    let userPassword = $("#userPassword").val();
    let userPasswordConfirm = $("#userPasswordConfirm").val();
    let userSexType = $("#userSexType").val();

    let user = {
        id: id,
        name: userName,
        email: userEmail,
        password: userPassword,
        confirmPassword: userPasswordConfirm,
        sexType: userSexType
      };
      $.ajax({
        url: SERVER_URL + "users/id="+id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(user),
        complete: function(serverResponse) {
          console.log(serverResponse);
          if (serverResponse.status == 201) {
            alert("User "+ userName+ " updated in database with id="+id);
            location.href="userInfo.html?id="+id;
        
            
          }
        }
      });
    };

function signup() {
    let userName = $("#userName").val();
    let userEmail = $("#userEmail").val();
    let userPassword = $("#userPassword").val();
    let userPasswordConfirm = $("#userPasswordConfirm").val();
    let userSexType = $("#userSexType").val();
  
   
    let user = {
      name: userName,
      email: userEmail,
      password: userPassword,
      confirmPassword: userPasswordConfirm,
      sexType: userSexType
    };
    console.log(JSON.stringify(user));
  
    // Server URL : http://localhost:8080/
    // Endpoint: PostMapping("/users")
    $.ajax({
      url: SERVER_URL + "users",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(user),
      complete: function(serverResponse) {
        console.log(serverResponse);
        if (serverResponse.status == 201) {
          alert("User added to database");
          $("#addUserForm")[0].reset();

        }
      }
    });
  };
  let getUrlParameter = function getUrlParameter(sParam) {
    let sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};