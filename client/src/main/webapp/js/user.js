/**
 * Sidebar menu switch active selected item
 */
$(function() {

    let urlParam = new URLSearchParams(window.location.search);

    if(urlParam.has("tab")){

        let param = urlParam.get("tab");
        $("#" + param).addClass("active");
    }
    else $("#info").addClass("active");

    if(urlParam.has("extented")){
        let isbn = urlParam.get("extended");
        showModal(isbn);
    }
});

function extend(isbn) {

    $.ajax({
        type: "POST",
        data: "isbn="+isbn,
        url: "extendBorrowing.action",
        success: function (data) {
            window.location.href = "user.action?tab=books&extended=book" + isbn;
        },
        error: function (data) {
            alert("Oops, une erreur est survenue...")
        }
    });
}

function showModal(isbn) {

    let info = $("#modalInfo");
    let bookTitle = $("#book" + isbn + " > #bookTitle");

    info.text("Livre : " + bookTitle);

    $("#resultModal").modal("show");
}

function updateContent(isbn) {

    let btnExtend = $("#btnExtend" + isbn);
    btnExtend.removeAttr("onclick");
    btnExtend.prop({disabled: true});
    btnExtend.html("Dur&eacutee prolong&eacutee");

    location.reload();
}