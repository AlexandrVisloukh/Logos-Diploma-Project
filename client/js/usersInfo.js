$(document).ready(function () {
    
    console.log(getUrlParameter('id'));
    let id = parseInt(getUrlParameter('id'), 10);
    console.log(id);
    showUserById(id);
    
    getUserDetails(id);
    
  
     $("#redactButton").on("click", function () {
         location.href="signup.html?id="+id;
     });

    $("#deleteButton").on("click", function () {
        deleteUser(id);
    });

    $("#imageInput").on('change', function () {
        uploadFile(id);
    });
    $("#redactDetailsBtn").on("click", function(){
     $("#formDetails2").show();
    })

    //  $("#submitUserDetails").on("click", function(){
    //      createOrRedactDetails(userId);
    //      return false;
    //  });

    
});

function submitFunc(){
    let id2 = parseInt(getUrlParameter('id'), 10);

    createOrRedactDetails(id2);
    
}

let userDetails;

function redactUser(userId){

};



function getUserDetails(id){
    
    $.ajax({
        url: SERVER_URL + "users/id=" + id+"/details",
        method: "GET",
      
        contentType: "application/json",
        
        complete: function (serverResponse) {
           // console.log(serverResponse.responseJSON);
         userDetails = serverResponse.responseJSON;
            console.log(userDetails);

            $("#dId").append(`${userDetails.id}`);
            $("#fName").append(`${userDetails.firstName}`);
            $("#lName").append(`${userDetails.lastName}`);
            $("#birthDay").append(`${new Date(userDetails.birthDay)}`);
            $("#aboutMe").append(`${userDetails.aboutMe}`);
            $("#adress").append(`${userDetails.location}`);
            $("#contactInfo").append(`${userDetails.contactInfo}`);
            $("#dStatus").append(`${userDetails.status}`);
            $("#dCreateDate").append(`${new Date(userDetails.createDate)}`);
            $("#dRedactDate").append(`${(userDetails.redactDate)?new Date(userDetails.redactDate):""}`);
            
            

        }
    });
    
  
}



function uploadFile(id) {
    let formData = new FormData();
    formData.append('imageFile', $('#imageInput')[0].files[0]);

    $.ajax({ // localhost:8080/users/10/image
        url: SERVER_URL + 'users/' + id + '/image',
        method: 'POST',
        contentType: false,
        processData: false,
        data: formData,
        complete: function (res) {
            console.log(res);
            if (res.status == 202) {
                location.reload()

              //  $("dd").empty();
              //showUserById(id);
            }
        }
    })
}

// @PostMapping("id={bookId}/{chapterId}/file")
//     public ResponseEntity<?> uploadChapter(
//             @PathVariable("bookId") int id,
//             @PathVariable("chapterId") int chapterId,
//             @RequestParam("chapterFile") MultipartFile file
function createOrRedactDetails(id) {

    let userDetailsId = parseInt($("#dId").text());
    let firstName = $("#firstNameInput").val();
    let lastName = $("#lastNameInput").val();
    let birthDay = $("#birthDayInput").val();
    let aboutMe = $("#aboutMeInput").val();
    let location = $("#adressImput").val();
    let contactInfo = $("#contactInfoInput").val();
    
    console.log(userDetailsId);
    if (userDetailsId){
        let userDetails = {
            id: userDetailsId,
            firstName: firstName,
            lastName: lastName,
            birthDay: birthDay,
            aboutMe: aboutMe,
            location: location,
            contactInfo: contactInfo
        };
        console.log("PUT PATH");
        $.ajax({
            url: SERVER_URL + "users/id=" + id+"/details",
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(userDetails),
            complete: function(serverResponse) {
              console.log(serverResponse);
              if (serverResponse.status == 201) {
                alert("UserDetails added to database");
                window.location.reload();
    
               // $("#detailsRow")[0].reset();
      
              }
            }
          });
    
        }
    else {
        let userDetails = {
            firstName: firstName,
            lastName: lastName,
            birthDay: birthDay,
            aboutMe: aboutMe,
            location: location,
            contactInfo: contactInfo
        };
       // console.log(userDetails);
        console.log("POST PATH");
        $.ajax({
            url: SERVER_URL + "users/id=" + id+"/details",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(userDetails),
            complete: function(serverResponse) {
              console.log(serverResponse);
              if (serverResponse.status == 201) {
                alert("UserDetails added to database");
                window.location.reload();

                //$("#detailsRow").reset();
      
              }
            }
          });
    }
  
    
    //console.log(JSON.stringify(userDetails));
  
    // Server URL : http://localhost:8080/
    // Endpoint: PostMapping("/users")
    
  };

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

function showUserById(id) {
    let IMAGE_URL = SERVER_URL + "users/image?imageName=";
    $.ajax({
        url: SERVER_URL + "users/id=" + id,
        method: "GET",
        contentType: "application/json",
        complete: function (serverResponse) {
            console.log(serverResponse.responseJSON);
            let user = serverResponse.responseJSON;
            $("#pId").append(`${user.id}`);
            $("#name").append(`${user.name}`);
            $("#email").append(`${user.email}`);
            $("#sexType").append(`${user.sexType}`);
            $("#status").append(`${user.status}`);
            $("#createDate").append(`${new Date(user.createDate)}`);
            $("#redactDate").append(`${(user.redactDate)?new Date(user.redactDate):""}`);
            for (let i = 0; i < user.roles.length; i++) {
                $("#listRoles").append(`
                <li>
                    <a href="role.html?id=${user.roles[i].id}">${user.roles[i].role}</a>
                </li>
                `);
            }
            if (user.image != null) {
                $("#image").attr("src", IMAGE_URL + user.image);
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

