$(document).ready(function () {
  showAllBooks();
  
  
   $(document).on("click", "#usersTable tbody button", function (e) {
     console.log(e.target.id);
     let btnId = e.target.id;
     let booksId = btnId.split("-")[1];
     deleteUser(booksId);
   });
 
   $(document).on('change', '#container1 input', function (e) {
     console.log(e.target.id);
     let inputId = e.target.id;
     let booksId = inputId.split("-")[1];
     console.log(booksId);
     uploadFile(booksId);
   });
   $(document).on("click", ".indexed", function (e) {
     let inputId = e.target.id;
     let booksId = inputId.split("-")[1];
     pageNumber = booksId-1;
    
     showAllBooks(pageNumber, pageSize)
   });
 
   let pageNumber = 0;
   let pageSize = 10;
   
   $(document ).on("click","#pagePrev", function () {
        if (pageNumber > 0) {
       pageNumber--;
     }
     showAllBooks(pageNumber, pageSize);
   });
 
   $(document ).on("click","#pageNext", function () {
     
     pageNumber++;
     showAllBooks(pageNumber, pageSize);
 
   });
 
   $("#pageSize").on("change", function () {
     pageSize = parseInt($("#pageSize :selected").val(), 10);
 
     console.log(typeof (pageSize));
     showAllBooks(pageNumber, pageSize);
   })
 });
 
 function uploadFile(booksId) {
   let formData = new FormData();
   formData.append('imageFile', $('#logoInput-' + booksId)[0].files[0]);
 
   $.ajax({ // localhost:8080/books/10/image
     url: SERVER_URL + 'books/' + booksId + '/image',
     method: 'POST',
     contentType: false,
     processData: false,
     data: formData,
     complete: function (res) {
       console.log(res);
       if (res.status == 202) {
 //        $("#usersTable tbody").empty();
         showAllBooks();
       }
     }
   })
 }
 
 function deleteBook(booksId) {
   let deleteBook = confirm("Ви впевнені що хочете видалити цю книгу?");
   if (deleteBook) {
     deleteBookRequest(booksId);
   }
 }
 
 function deleteBookRequest(booksId) {
   $.ajax({
     url: SERVER_URL + "books/" + booksId,
     method: "DELETE",
     contentType: "application/json",
     complete: function (serverResponse) {
       if (serverResponse.status == 200) {
         alert("book deleted");
       }
     }
   });
 }
 
 
 
 function showAllBooks(pageNumber = 0, pageSize = 10) {
 
   let IMAGE_URL = SERVER_URL + "books/image?imageName=";
   // let IMAGE_URL = "D:\\Visloukh\\\client\\image\\";
   $.ajax({
     url: SERVER_URL + "books/page?page=" + pageNumber + "&size=" + pageSize,
     method: "GET",
     contentType: "application/json",
     complete: function (serverResponse) {
       console.log(serverResponse.responseJSON);
       let books = serverResponse.responseJSON;
 
       $("#container1").empty();
       $("#pagitation1").empty();
       //console.log(users.totalPages);
       
       if (books.totalPages > 1) {
         
         $("#pagitation1").append(`
           <li class="page-item"><a class="page-link" id="pagePrev" >Previous</a></li>
           `);
         for (let i = 1; i <= books.totalPages; i++) {
           $("#pagitation1").append(`
           <li class="page-item indexed"><a class="page-link" id=pageItem-${i}>${i}</a></li>
           `);
         };
         $("#pagitation1").append(
           `<li class="page-item"><a class="page-link" id="pageNext" >Next</a></li>`
         )
       }
       if (books.totalPages <= pageNumber) {
         $('#pageNext').prop('disabled', true);
       } else {
         $('#pageNext').prop('disabled', false);
       }
       if (pageNumber <= 1) {
         $('#pagePrev').prop('disabled', true);
       } else {
         $('#pagePrev').prop('disabled', false);
       }
       $.each(books.content, function (key, value) {
         $("#container1").append(`
         <div class="row" style="margin-top:30px">
         <div class="col-sm-4">
         <img src="${value.fileLogo != null ? (IMAGE_URL + value.fileLogo) : ''}" id="userImg-${value.id}" width="100%">
         <input type="file" class="form-control" accept="image/*" id="logoInput-${value.id}">
         </div>
         <div class="col-sm-8">
             <p id="pId-${value.id}"> id: ${value.id}</p>
             <p id="title-${value.id}"> title: <a href="book.html?id=${value.id}"> ${value.title}</a></p>
             <p id="description-${value.id}">description: ${value.description}</p>
             <p id="fileName-${value.id}"> FileName: ${value.fileName}</p>
             <p id="category-${value.id}">Category: ${value.category.name}</p>
             <p id="author-${value.id}">Author: ${value.author}</p>
             <p id="status-${value.id}"> status: ${value.status}</p>
             <p id="createDate-${value.id}"> createDate: ${new Date(value.createDate)}</p>
             <p id="redactDate-${value.id}"> redactDate: ${(value.redactDate)?new Date(value.redactDate):""}</p>
        
         </div>
        
        </div>
             `)
       });
     }
   });
 }


//  <div class="row" style="margin-top:30px">
//  <div class="col-sm-4">
//      <img src="" alt="" id="userImg">
//      <input type="file" class="form-control" id="logoInput">
//  </div>
//  <div class="col-sm-8">
//      <p id="pId"> id</p>
//      <p id="title"> title</p>
//      <p id="description"> description</p>
//      <p id="fileName"> FileName</p>
//      <p id="category">Category </p>
//      <p id="author">Author </p>
//      <p id="status"> status</p>
//      <p id="createDate"> createDate</p>
//      <p id="redactDate"> redactDate</p>

//  </div>

// </div>



// <div class="row">
//              <div class="col-sm-3" id="col1-id${value.id}">
//              <img src="${value.image != null ? (IMAGE_URL + value.image) : ''}"width="250px">
//              <input type="file" class="form-control" id="image-${value.id}">
//              </div>
//              <div class="col-sm-8" id="col2-id${value.id}">
//                  <p id="pId-"> id: <a href="userInfo.html?id=${value.id}">${value.id}</a>  </p>
//                  <p id="pName"> name: <a href="userInfo.html?id=${value.id}">   ${value.name}</a> </p>
//                  <p id="pEmail"> email: ${value.email} </p>
//                  <p id="pSexType"> sexType: ${value.sexType} </p>
//                  <p id="pStatus"> status:  ${value.status}</p>
//                  <p id="pCreateDate"> createDate: ${new Date(value.createDate)} </p>
//                  <p id="pRedactDate"> redactDate: ${new Date(value.redactDate)}</p>
              
//              </div>
//              </div>