$(document).ready(function () {

    let id = parseInt(getUrlParameter('id'), 10);
    console.log(id);
    showBookById(id);

    // getUserDetails(id);


    $("#redactButton").on("click", function () {
        $("#redactBookForm").show();
        $("#redactChapterForm").hide();
        $("#titleInput").val($("#title").text());
        $("#descriptionInput").val($("#description").text());
        $("#fileNameInput").val($("#fileName").text());
        $("#authorImput").val($("#author").text());

        getCategories();
    });
    $("#chapterSelectInput").on("change", function(){
        if ($("#chapterSelectInput").val() !="new"){
            $("#deleteChapterButton").show();
        }else {$("#deleteChapterButton").hide();};

    });
    $("#deleteButton").on("click", function () {
        deleteBook(id);
    });
    $("#deleteChapterButton").on("click", function () {
        deleteChapter();
    });
    $("#createChapterButton").on("click", function () {

        
        $("#redactChapterForm").show();
        $("#redactBookForm").hide();
        $("#deleteChapterButton").hide();
        let chapters = getChaptersInfo();
        $.each(chapters, function (key, chapter) {
            $("#chapterSelectInput").append(` <option value="${chapter.id}">${chapter.name}</option>`)
        });

  


    });
    $("#imageInput").on('change', function () {
        uploadFile(id);
    });
    $("#redactDetailsBtn").on("click", function () {
        $("#formDetails2").show();
    })
    $(document).on("click", "#chaptersTable tbody button", function(e) {
        console.log(e.target.id);
        let btnId = e.target.id;
        let chapterId = btnId.split("-")[1];
    //    downloadChapter(chapterId);
      });
    //  $("#submitUserDetails").on("click", function(){
    //      createOrRedactDetails(userId);
    //      return false;
    //  });

    //     $.each(selectValues, function(key, value) {
    //         $('#mySelect')
    //              .append($('<option>', { value : key })
    //              .text(value));
    //    });

});

function getChaptersInfo() {
    let chapters =[]
    let listChapters = $("#listChapters tbody tr")
    listChapters.each(function() {
        var listElem={ 
        id:    parseInt($(this).find("td").eq(0).html(),10),
        name:   $(this).find("td").eq(1).html(),
    }    
    //console.log(listElem);
    chapters.push(listElem);
    });
    console.log(chapters);
    return chapters;
}
    
function getCategories() {
    $.ajax({
        url: SERVER_URL + "category",
        method: "GET",

        contentType: "application/json",

        complete: function (serverResponse) {
            // console.log(serverResponse.responseJSON);
            categoryes = serverResponse.responseJSON;
            console.log(categoryes);
            for (let i = 1; i <= categoryes.length; i++) {
                $("#categoryInput").append(`
        <option  value="${categoryes[i - 1].id}">${categoryes[i - 1].name}</option>
        `);
            }
        }
    });
}

function submitRedactFunc() {
    let bookId = parseInt(getUrlParameter('id'), 10);
    let title = $("#titleInput").val();
    let description = $("#descriptionInput").val();
    let fileName = $("#fileNameInput").val();
    let category = $("#categoryInput").val();
    let author = $("#authorImput").val();

    let book = {
        id: bookId,
        title: title,
        description: description,
        fileName: fileName,
        category: {
            id: category
        },
        author: author
    }



    console.log(book);


    $.ajax({
        url: SERVER_URL + "books/id=" + bookId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(book),
        complete: function (serverResponse) {
            console.log(serverResponse);
            if (serverResponse.status == 201) {
                alert("Book updated in database");
                $("#redactBookForm").hide();
                window.location.reload();

                // $("#detailsRow")[0].reset();

            }
        }
    });
}
function submitRedactChapterFunc() {
    let bookId = parseInt(getUrlParameter('id'), 10);
    let selectedChapt = $("#chapterSelectInput").val();
    console.log(selectedChapt);
    let chapterId = $("#chapterSelectInput").val();
    console.log(chapterId);
    let chapterName = $("#chapterNameInput").val();
    let description = $("#chapterDescriptionInput").val();
    //  let path= $("#chapterFileNameInput").val();
    if (selectedChapt == "new") {
        let chapter = {
            "chapterName": chapterName,
            "description": description,
            //         "path": path
        }
        console.log("post path");
        console.log(chapter);
        postNewChapter(chapter, bookId);
    } else {
        let chapter = {
            "id": chapterId,
            "chapterName": chapterName,
            "description": description,
            //           "path": path
        }
        uploadChapterFile(bookId, chapterId);
        console.log("put path");
        console.log(chapter);
        updateChapter(chapter, bookId);
    }

}

function deleteChapter() {
  //  let chapters = getChaptersInfo;
    
    let selected = $("#chapterSelectInput").val();
    console.log(selected);
 //   if ($.inArray(selected, chapters.id)) {
        deleteChapterRequest(selected);
 //   }

}
function deleteChapterRequest(chapterId) {
    let bookId = parseInt(getUrlParameter('id'), 10);
    $.ajax({
        url: SERVER_URL + "books/id=" + bookId + "/" + chapterId,
        method: "DELETE",
        contentType: "application/json",
        complete: function (serverResponse) {
            if (serverResponse.status == 200) {
                alert("chapter deleted from db");

                $("#redactChapterForm")[0].reset();
                window.location.reload;
            }
        }
    });
   
}
function postNewChapter(chapter, bookId) {
    console.log(JSON.stringify(chapter));


    $.ajax({
        url: SERVER_URL + "books/id=" + bookId,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(chapter),
        complete: function (serverResponse) {
            console.log(serverResponse);
            answer = serverResponse.responseJSON;
            console.log(answer.id);
            if (serverResponse.status == 201) {
                uploadChapterFile(bookId, answer.id);
                alert("chapter added to database");

                //   $("#redactChapterForm")[0].reset();

                location.reload();
            }
        }
    });
}
// function downloadChapter(chapterId){
//    // let IMAGE_URL = SERVER_URL + "books/image?imageName=";
//    ${SERVER_URL}books/chapter?chapterName=${book.chapters[i].path}
//     $.ajax({
//         url: SERVER_URL + books/chapter?chapterName=" + id,
//         method: "GET",
//         contentType: "application/json",
//         complete: function (serverResponse) {
//             console.log(serverResponse.responseJSON);
//             let book = serverResponse.responseJSON;
// }

//     });
// }

function updateChapter(chapter, bookId) {

    console.log(JSON.stringify(chapter));
    console.log(chapter.id);

    console.log(SERVER_URL + "books/id=" + bookId + "/" + chapter.id);
    $.ajax({
        url: SERVER_URL + "books/id=" + bookId + "/" + chapter.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(chapter),
        complete: function (serverResponse) {
            console.log(serverResponse);
            if (serverResponse.status == 201) {
                alert("chapter updated in database");
                $("#redactChapterForm")[0].reset();
                window.location.reload;
            }
        }
    });



};
function uploadFile(id) {
    let formData = new FormData();
    formData.append('imageFile', $('#imageInput')[0].files[0]);

    $.ajax({ // localhost:8080/books/10/image
        url: SERVER_URL + 'books/' + id + '/image',
        method: 'POST',
        contentType: false,
        processData: false,
        data: formData,
        complete: function (res) {
            console.log(res);
            if (res.status == 202) {
                window.location.reload()

                //  $("dd").empty();
                //showUserById(id);
            }
        }
    })
}

function uploadChapterFile(bookId, chapterId) {
    let formData = new FormData();
    formData.append('chapterFile', $('#chaperFileInput')[0].files[0]);

    $.ajax({ // localhost:8080/users/10/image
        url: SERVER_URL + 'books/id=' + bookId + '/' + chapterId + '/file',
        method: 'POST',
        contentType: false,
        processData: false,
        data: formData,
        complete: function (res) {
            console.log(res);
            if (res.status == 202) {
                //  location.reload()

                //  $("dd").empty();
                //showUserById(id);
            }
        }
    })
}
function deleteBook(bookId) {
    let deleteBook = confirm("Ви впевнені що хочете видалити цю книгу?");
    if (deleteBook) {
        deleteBookRequest(bookId);
    }
}

function deleteBookRequest(bookId) {
    $.ajax({
        url: SERVER_URL + "books/" + bookId,
        method: "DELETE",
        contentType: "application/json",
        complete: function (serverResponse) {
            if (serverResponse.status == 200) {
                alert("book deleted");
            }
        }
    });
}

function showBookById(id) {
    let IMAGE_URL = SERVER_URL + "books/image?imageName=";
    $.ajax({
        url: SERVER_URL + "books/id=" + id,
        method: "GET",
        contentType: "application/json",
        complete: function (serverResponse) {
            console.log(serverResponse.responseJSON);
            let book = serverResponse.responseJSON;
            $("#dId").append(`${book.id}`);
            $("#title").append(`${book.title}`);
            $("#description").append(`${book.description}`);
            $("#fileName").append(`${book.fileName}`);
            $("#category").append(`${book.category.name}`);
            $("#author").append(`${book.author}`);
            $("#status").append(`${book.status}`);
            $("#createDate").append(`${new Date(book.createDate)}`);
            $("#redactDate").append(`${(book.redactDate) ? new Date(book.redactDate) : ""}`);
            $("#listChapters").append(`
            <table class="table" id="chaptersTable">
        <thead>
            <tr>
    
                <th>id</th>
                <th>chapterName</th>
                <th>description</th>
                <th>path</th>
                <th>status</th>
                <th>createDate</th>
                <th>redactDate</th>
                <th>Download</th>
                
                
            </tr>
        </thead>
        <tbody></tbody>
      </table>`);
      
            for (let i = 0; i < book.chapters.length; i++) {
                $("#listChapters tbody").append(`
                <tr>
                <td>${book.chapters[i].id}</td>
                <td>${book.chapters[i].chapterName}</td>
                <td>${book.chapters[i].description}</td>
                <td>${book.chapters[i].path}</td>
                <td>${book.chapters[i].status}</td>
                <td>${new Date(book.chapters[i].createDate)}</td>
                <td>${(book.chapters[i].redactDate) ? new Date(book.chapters[i].redactDate) : ""}</td>
                
               
                <td style=${book.chapters[i].path != null ? "" : "display:none"}>
                <form method"get" action=${SERVER_URL}books/id=${book.id}/${book.chapters[i].id}/download>
                <button type="submit"  class="btn btn-sm" id="chapterBtn-${book.chapters[i].id}">link</button>
                </form>
                </td>
                </tr>
                `);
            }
            if (book.fileLogo != null) {
                $("#image").attr("src", IMAGE_URL + book.fileLogo);
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

