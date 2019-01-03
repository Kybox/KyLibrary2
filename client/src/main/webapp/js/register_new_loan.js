// Global selectors
let bookInfo;
let bookCover;
let resultBookBlock;
let resultUserBlock;
let btnRegisterNewLoan;
let btnSearchUser;
let btnSearchBook;
let inputIsbn;
let inputFirstName;
let inputLastName;
let userEmail;
let bookIsbn;
let modalMsg;
let modalMsgTitle;
let registerLoanModal;

$(document).ready(function() {

    bookInfo = $("#bookInfo");
    bookCover = $("#bookCover");
    resultBookBlock = $("#resultBookSearch");
    resultUserBlock = $("#resultUserSearch");
    btnRegisterNewLoan = $("#btn_register_new_loan");
    btnSearchUser = $("#btnSearchUser");
    btnSearchBook = $("#btnSearchBook");
    inputIsbn = $("#isbn");
    inputFirstName = $("#firstName");
    inputLastName = $("#lastName");
    modalMsg = $("#modalMessage");
    modalMsgTitle = $("#modalMsgTitle");
    registerLoanModal = $("#registerLoanModal");

    /** Submit action **/
    $("#formSearchBookByIsbn").submit(function (e) {
        e.preventDefault();
        btnSearchBook.prop("disabled", true);
        searchBookByIsbn();
    });
    $("#formSearchUserByName").submit(function (e) {
        e.preventDefault();
        btnSearchUser.prop("disabled", true);
        searchUserByName();
    });
    btnRegisterNewLoan.click(function (e) {
        $(this).prop("disabled", true);
        btnSearchUser.prop("disabled", true);
        btnSearchBook.prop("disabled", true);
        registerNewLoan();
    });
});

function searchBookByIsbn() {

    let data = "isbn=" + inputIsbn.val();

    $.ajax({
        type: "POST",
        url: "searchBookByIsbn.action",
        data: data,
        dataType : "json",
        success : function (data) {
            displayResultBookSearch(data);
        },
        error : function () {
            openErrorModal("<b>Ouvrage inconnu...</b>");
        },
        complete : function () {
            $("#btnSearchBook").prop("disabled", false);
        }
    });
}

function searchUserByName() {

    let data = "firstName=" + inputFirstName.val() + "&lastName=" + inputLastName.val();

    $.ajax({
        type: "POST",
        url: "searchUserByName.action",
        data: data,
        dataType: "json",
        success : function (data) {
            displayResultUserSearch(data);
        },
        error : function () {
            openErrorModal("<b>Client inconnu...</b>");
        },
        complete : function () {
            btnSearchUser.prop("disabled", false);
        }
    });
}

function displayResultBookSearch(data) {

    bookIsbn = data.book.isbn;

    // Objects
    let bookAvailable = data.book.available;

    bookInfo.empty();
    bookCover.empty();

    bookInfo.append("<b>ISBN : </b>" + bookIsbn + "<br>"
        + "<b>TITRE : </b>" + data.book.title + "<br>"
        + "<b>AUTEUR : </b>" + data.book.author + "<br>"
        + "<b>EXEMPLAIRES : </b>" + bookAvailable + " / " + data.book.nbCopies);

    bookCover.append("<img src='" + data.book.cover + "' alt='Cover' class='img-thumbnail'>");

    if(bookAvailable === 0) resultBookBlock.attr("class", "alert alert-danger");
    else resultBookBlock.attr("class", "alert alert-success");

    resultBookBlock.show();
    checkNeededData();
}

function displayResultUserSearch(data) {

    console.log(data);
    console.log("SIZE = " + data.userList.length);

    // Objects
    let userData = data.userList[0];
    let date = new Date(userData.birthday);
    let birthday = date.getDate() + " / " + (date.getMonth() + 1) + " / " + date.getFullYear();
    userEmail = userData.email;

    resultUserBlock.empty();

    resultUserBlock.append("<b>NOM : </b>" + userData.lastName + "<br>"
        + "<b>PRENOM : </b>" + userData.firstName + "<br>"
        + "<b>E-MAIL : </b>" + userEmail + "<br>"
        + "<b>DATE DE NAISSANCE : </b>" + birthday);

    resultUserBlock.attr("class", "alert alert-success");
    resultUserBlock.show();
    checkNeededData();
}

function openErrorModal(data) {

    modalMsg.empty();
    modalMsg.append(data);
    registerLoanModal.modal();
}

function checkNeededData() {

    if(resultUserBlock.attr("class") === "alert alert-success"
    && resultBookBlock.attr("class") === "alert alert-success"){

        btnRegisterNewLoan.prop("disabled", false);
    }
    else btnRegisterNewLoan.prop("disabled", true);
}

function registerNewLoan() {

    const data = "email=" + userEmail + "&isbn=" + bookIsbn;
    $.ajax({
        type: "POST",
        url: "registerNewLoan.action",
        data: data,
        dataType: "json",
        success : function () {
            openModalSucess();
        },
        error : function () {
            alert("Erreur !");
        },
        complete : function () {
            btnRegisterNewLoan.prop("disabled", false);
        }
    });
}

function openModalSucess() {

    $("#modalAlert").attr("class", "alert alert-success");
    modalMsg.empty();
    modalMsg.append("L'emprunt a bien &eacute;t&eacute; enregistr&eacute;.");

    modalMsgTitle.empty();
    modalMsgTitle.append("<b><i>Confirmation :</i></b>");
    registerLoanModal.modal();

    registerLoanModal.on('hidden.bs.modal', function () {
        resetAll();
    });
}

function resetAll() {

    bookInfo.empty();
    bookCover.empty();
    resultUserBlock.empty();

    resultBookBlock.attr("class", "alert alert-default");
    resultUserBlock.attr("class", "alert alert-default");

    resultBookBlock.hide();
    resultUserBlock.hide();

    btnRegisterNewLoan.prop("disabled", true);
    btnSearchUser.prop("disabled", false);
    btnSearchBook.prop("disabled", false);

    modalMsgTitle.empty();
    modalMsgTitle.append("<b><i>Une erreur s'est produite !</i></b>");

    inputFirstName.val("");
    inputLastName.val("");
    inputIsbn.val("");
}