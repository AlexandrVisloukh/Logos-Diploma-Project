$(document).ready(function () {
 showAllUsers();
 
 
  $(document).on("click", "#usersTable tbody button", function (e) {
    console.log(e.target.id);
    let btnId = e.target.id;
    let userId = btnId.split("-")[1];
    deleteUser(userId);
  });

  $(document).on('change', '#container1 input', function (e) {
    console.log(e.target.id);
    let inputId = e.target.id;
    let userId = inputId.split("-")[1];
    console.log(userId);
    uploadFile(userId);
  });
  $(document).on("click", ".indexed", function (e) {
    let inputId = e.target.id;
    let userId = inputId.split("-")[1];
    pageNumber = userId-1;
   
    showAllUsers(pageNumber, pageSize)
  });

  let pageNumber = 0;
  let pageSize = 10;
  
  $(document ).on("click","#pagePrev", function () {
       if (pageNumber > 0) {
      pageNumber--;
    }
    showAllUsers(pageNumber, pageSize);
  });

  $(document ).on("click","#pageNext", function () {
    
    pageNumber++;
    showAllUsers(pageNumber, pageSize);

  });

  $("#pageSize").on("change", function () {
    pageSize = parseInt($("#pageSize :selected").val(), 10);

    console.log(typeof (pageSize));
    showAllUsers(pageNumber, pageSize);
  })
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
    complete: function (res) {
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
    url: SERVER_URL + "users/id=" + userId,
    method: "DELETE",
    contentType: "application/json",
    complete: function (serverResponse) {
      if (serverResponse.status == 200) {
        alert("user deleted");
      }
    }
  });
}



function showAllUsers(pageNumber = 0, pageSize = 10) {

  let IMAGE_URL = SERVER_URL + "users/image?imageName=";
  // let IMAGE_URL = "D:\\Visloukh\\\client\\image\\";
  $.ajax({
    url: SERVER_URL + "users/page?page=" + pageNumber + "&size=" + pageSize,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      console.log(serverResponse.responseJSON);
      let users = serverResponse.responseJSON;

      $("#container1").empty();
      $("#pagitation1").empty();
      //console.log(users.totalPages);
      
      if (users.totalPages > 1) {
        
        $("#pagitation1").append(`
          <li class="page-item"><a class="page-link" id="pagePrev" >Previous</a></li>
          `);
        for (let i = 1; i <= users.totalPages; i++) {
          $("#pagitation1").append(`
          <li class="page-item indexed"><a class="page-link" id=pageItem-${i}>${i}</a></li>
          `);
        };
        $("#pagitation1").append(
          `<li class="page-item"><a class="page-link" id="pageNext" >Next</a></li>`
        )
      }
      if (users.totalPages <= pageNumber) {
        $('#pageNext').prop('disabled', true);
      } else {
        $('#pageNext').prop('disabled', false);
      }
      if (pageNumber <= 1) {
        $('#pagePrev').prop('disabled', true);
      } else {
        $('#pagePrev').prop('disabled', false);
      }
      $.each(users.content, function (key, value) {
        $("#container1").append(`
            <div class="row">
            <div class="col-sm-3" id="col1-id${value.id}">
            <img src="${value.image != null ? (IMAGE_URL + value.image) : ''}"width="250px">
            <input type="file"  accept="image/*" class="form-control" id="image-${value.id}">
            </div>
            <div class="col-sm-8" id="col2-id${value.id}">
                <p id="pId"> id: <a href="userInfo.html?id=${value.id}">${value.id}</a>  </p>
                <p id="pName"> name: <a href="userInfo.html?id=${value.id}">   ${value.name}</a> </p>
                <p id="pEmail"> email: ${value.email} </p>
                <p id="pSexType"> sexType: ${value.sexType} </p>
                <p id="pStatus"> status:  ${value.status}</p>
                <p id="pCreateDate"> createDate: ${new Date(value.createDate)} </p>
                <p id="pRedactDate"> redactDate: ${new Date(value.redactDate)}</p>

            </div>
            </div>
            `)
      });
    }
  });
}

