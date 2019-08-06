$(document).ready(function() {
  showAllUsers();

  $("#btnRegister").on("click", function() {
    signup();
  });

  $(document).on("click", "#usersTable tbody button", function(e) {
    console.log(e.target.id);
    let btnId = e.target.id;
    let userId = btnId.split("-")[1];
    deleteUser(userId);
  });

  $(document).on('change', '#usersTable tbody input', function(e) {
    // console.log(e.target.id);
    let inputId = e.target.id;
    let userId = inputId.split("-")[1];
    // console.log(userId);
    uploadFile(userId);
  });

  let pageNumber = 0;
  $("#btnPrev").on("click", function() {
    if (pageNumber > 0) {
      pageNumber--;
    }
    showAllUsers(pageNumber);
  });

  $("#btnNext").on("click", function() {
    pageNumber++;
    showAllUsers(pageNumber);
  });
});

function uploadFile(userId) {
  let formData = new FormData();
  formData.append('imageFile', $('#image-' + userId)[0].files[0]);

  $.ajax({ // localhost:8080/users/10/image
    url: SERVER_URL + 'users/' + userId + '/image',
    method: 'POST',
    contentType: false,
    processData: false,
    data: formData,
    complete: function(res) {
      console.log(res);
      if (res.status == 202) {
        $("#usersTable tbody").empty();
        showAllUsers();
      }
    }
  })
}

function deleteUser(userId) {
  let deleteUser = confirm("Ви впевнені що хочете видалити цього user-а?");
  if (deleteUser) {
    deleteUserRequest(userId);
  }
}

function deleteUserRequest(userId) {
  $.ajax({
    url: SERVER_URL + "users/" + userId,
    method: "DELETE",
    contentType: "application/json",
    complete: function(serverResponse) {
      if (serverResponse.status == 200) {
        alert("user deleted");
      }
    }
  });
}

function signup() {
  let userName = $("#userName").val();
  let userEmail = $("#userEmail").val();
  let userPassword = $("#userPassword").val();
  let userPasswordConfirm = $("#userPasswordConfirm").val();
  let userSexType = $("#userSexType").val();

  console.log(
    userName +
      " " +
      userEmail +
      " " +
      userPassword +
      " " +
      userPasswordConfirm +
      userSexType
  );

  // name
  // email
  // password
  // passwordConfirm
  // sexType

  let user = {
    name: userName,
    email: userEmail,
    password: userPassword,
    passwordConfirm: userPasswordConfirm,
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
        $("#usersTable tbody").empty();
        showAllUsers();
      }
    }
  });
}

function showAllUsers(pageNumber) {

  let IMAGE_URL = SERVER_URL + "users/image?imageName=";

  $.ajax({
    url: SERVER_URL + "users/page?page=" + pageNumber,
    method: "GET",
    contentType: "application/json",
    complete: function(serverResponse) {
      console.log(serverResponse.responseJSON);
      let users = serverResponse.responseJSON;

      $("#usersTable tbody").empty();

      if (users.totalPages <= pageNumber) {
        $('#btnNext').prop('disabled', true);
      } else {
        $('#btnNext').prop('disabled', false);
      }

      $.each(users.content, function(key, value) {
        $("#usersTable tbody").append(
          `
                        <tr>
                            <td>${value.id}</td>
                            <td>${value.name}</td>
                            <td>${value.email}</td>
                            <td>${value.password}</td>
                            <td>${value.sexType}</td>
                            <td>
                              <img src="${value.image != null? (IMAGE_URL + value.image) : ''}" width="150px">
                            </td>
                            <td>
                                <button class="btn btn-danger btn-sm" id="user-${
                                  value.id
                                }">Delete</button>
                            </td>
                            <td>
                                <input type="file" class="form-control" id="image-${value.id}">
                            </td>
                        </tr>
                        `
        );
      });
    }
  });
}



// $(document).ready(function () {
//   showAllUsers();

//   $("#btnRegister").on("click", function () {
//     signup();
//   });

//   $(document).on("click", "#usersTable tbody button", function (e) {
//     console.log(e.target.id);
//     let btnId = e.target.id;
//     let userId = btnId.split("-")[1];
//     deleteUser(userId);
//   });

//   $(document).on('change', '#usersTable tbody input', function (e) {
//     // console.log(e.target.id);
//     let inputId = e.target.id;
//     let userId = inputId.split("-")[1];
//     // console.log(userId);
//     uploadFile(userId);
//   });

//     let pageNumber = 0;
//     $("#btnPrev").on("click", function() {
//       if (pageNumber > 0) {
//         pageNumber--;
//       }
//       showAllUsers(pageNumber);
//     });

//     $("#btnNext").on("click", function() {
//       pageNumber++;
//       showAllUsers(pageNumber);
//     });
//   });

//   function uploadFile(userId) {
//     let formData = new FormData();
//     formData.append('imageFile', $('#image-' + userId)[0].files[0]);

//     $.ajax({ // localhost:8080/users/10/image
//       url: SERVER_URL + 'users/' + userId + '/image',
//       method: 'POST',
//       contentType: false,
//       processData: false,
//       data: formData,
//       complete: function(res) {
//         console.log(res);
//         if (res.status == 202) {
//           $("#usersTable tbody").empty();
//           showAllUsers();
//         }
//       }
//     })
//   }

//   function deleteUser(userId) {
//     let deleteUser = confirm("Ви впевнені що хочете видалити цього user-а?");
//     if (deleteUser) {
//       deleteUserRequest(userId);
//     }
//   }

//   function deleteUserRequest(userId) {
//     $.ajax({
//       url: SERVER_URL + "users/id=" + userId,
//       method: "DELETE",
//       contentType: "application/json",
//       complete: function (serverResponse) {
//         if (serverResponse.status == 200) {
//           alert("user deleted");
//           showAllUsers();
//         }
//       }
//     });
//   }

//   function signup() {
//     let userId = $("#userId").val();
//     let userName = $("#userName").val();
//     let userEmail = $("#userEmail").val();
//     let userPassword = $("#userPassword").val();
//     let userPasswordConfirm = $("#userPasswordConfirm").val();
//     let userSexType = $("#userSexType").val();

//     console.log(
//       userName +
//       " " +
//       userEmail +
//       " " +
//       userPassword +
//       " " +
//       userPasswordConfirm +
//       userSexType
//     );

//     // name
//     // email
//     // password
//     // passwordConfirm
//     // sexType
    
//     if (userId==""){
//     let user = {
//       name: userName,
//       email: userEmail,
//       password: userPassword,
//       confirmPassword: userPasswordConfirm,
//       sexType: userSexType,
//     };
    
//     console.log(JSON.stringify(user));
//     // Server URL : http://localhost:8080/
//     // Endpoint: PostMapping("/users")
//     $.ajax({

//       //url: "http://localhost:8080/users",
//       url: SERVER_URL + "users",
//       method: "POST",
//       contentType: "application/json",
//       data: JSON.stringify(user),
//       complete: function (serverResponse) {
//         console.log(serverResponse);
//         if (serverResponse.status == 201) {
//           alert("User added to database");
//           $("#addUserForm")[0].reset();
//           $("#usersTable tbody").empty();
//           showAllUsers();
//         }
//       }
//     });
//   }else{
//     let user = {
//       id: userId,
//       name: userName,
//       email: userEmail,
//       password: userPassword,
//       confirmPassword: userPasswordConfirm,
//       sexType: userSexType,
//     };
    
//     console.log(JSON.stringify(user));
//     // Server URL : http://localhost:8080/
//     // Endpoint: PostMapping("/users")
//     $.ajax({

//       //url: "http://localhost:8080/users",
//       url: SERVER_URL + "users/id="+userId,
//       method: "PUT",
//       contentType: "application/json",
//       data: JSON.stringify(user),
//       complete: function (serverResponse) {
//         console.log(serverResponse);
//         if (serverResponse.status == 201) {
//           alert("User redacted in database");
//           $("#addUserForm")[0].reset();
//           $("#usersTable tbody").empty();
//           showAllUsers();
//         }
//       }
//     });


//   }
//   };

//   function showAllUsers() {

//     //let IMAGE_URL = SERVER_URL + "users/image?imageName=";
//     let IMAGE_URL = "D:\\Visloukh\\libra\\client\\image\\";
//     $.ajax({
//       //  url: SERVER_URL + "users/page?page=" + pageNumber,
//       url: SERVER_URL + "users",
//       method: "GET",
//       contentType: "application/json",
//       complete: function (serverResponse) {
//         console.log(serverResponse.responseJSON);
//         let users = serverResponse.responseJSON;
//         console.log(users);

//         $("#usersTable tbody").empty();



//         if (users.totalPages <= pageNumber) {
//           $('#btnNext').prop('disabled', true);
//         } else {
//           $('#btnNext').prop('disabled', false);
//         }

//         //$.each(users.content, function(key, value) {
//         let i = 0;
//         while (i < users.length) {
//           $("#usersTable tbody").append(
//             `
//                         <tr>
//                             <td>${users[i].id}</td>
//                             <td>${users[i].name}</td>
//                             <td>${users[i].email}</td>
//                             <td>${users[i].password}</td>
//                             <td>${users[i].sexType}</td>
//                             <td>${users[i].status}</td>
                            
//                             <td>
//                               <img src="${users[i].image != null ? (IMAGE_URL + users[i].image) : ''}" width="150px">
//                             </td>
//                             <td>
//                                 <button class="btn btn-danger btn-sm" id="user-${users[i].id}">Delete</button>
//                             </td>
                            
//                         </tr>
//                         `
//           );
//           i++;
//         }
//       }
//     });
//   }

// });

//                             // <td>
//                             //     <input type="file" class="form-control" id="image-${users[i].id}">
//                             // </td>