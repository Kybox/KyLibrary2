$(document).ready(function() {

    /** Submit action **/
    $("#formSearchBookByIsbn").submit(function (e) {

        e.preventDefault();
        searchBookByIsbn();
    });
});

function searchBookByIsbn() {

    var isbn = $("#isbn").val();

    $.ajax({
        type: "POST",
        url: "adminSearchByIsbn.action",
        data: "isbn=" + isbn,
        dataType : "json",
        success : function (data) {
            displayResultBookSearch(data);
        },
        error : function (data) {
            openBookResultModal();
        }
    });
}

function displayResultBookSearch(data) {

    var resultBlock = $("#resultBookSearch");
    var bookCover = $("#bookCover");
    var bookInfo = $("#bookInfo");

    bookInfo.append("<b>ISBN : </b>" + data.book.isbn + "<br>"
        + "<b>TITRE : </b>" + data.book.title + "<br>"
        + "<b>AUTEUR : </b>" + data.book.author + "<br>"
        + "<i>Nombre d'exemplaires disponibles : </i>" + data.book.available + " / "
        + data.book.nbCopies);

    bookCover.append("<img src='" + data.book.cover + "' alt='Cover' height='100'>");
    resultBlock.show();
}

function openBookResultModal() {
    $("#registerLoanModal").modal();
}