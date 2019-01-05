// Global selectors
let resultSearch;
let resultTable;
let inputIsbn;
let btnSearchBook;
let resultTableTbody;
let bookCover;
let modalBox;
let dataList;
let isbn;
let modalMsgTitle;
let modalMessage;
let modalAlert;

$(document).ready(function() {

   resultSearch = $("#resultSearch");
   resultTable = $("#resultTable");
   inputIsbn = $("#isbn");
   btnSearchBook = $("#btnSearchBook");
   resultTableTbody = $("#resultTable > tbody");
   bookCover = $("#bookCover");
   modalBox = $("#registerLoanReturnModal");
   modalMsgTitle = $("#modalMsgTitle");
   modalMessage = $("#modalMessage");
   modalAlert = $("#modalAlert");

    /** Submit action **/
    $("#formSearchBookByIsbn").submit(function (e) {
        e.preventDefault();
        btnSearchBook.prop("disabled", true);
        searchBorrowersByIsbn();
    });
    resultTable.on("click", ":input[type=button]", function () {
       let btnId = $(this).attr("id");
       loanReturn(btnId.substr(3));
    });
});

function searchBorrowersByIsbn() {

    let data = "isbn=" + inputIsbn.val() + "&returned=false";

    $.ajax({
        type: "POST",
        url: "searchBorrowersByIsbn.action",
        data: data,
        dataType : "json",
        success : function (data) {
            displaySearchResult(data);
        },
        error : function (result) {
            openErrorModal(result);
        },
        complete : function () {
            btnSearchBook.prop("disabled", false);
        }
    });
}

function displaySearchResult(data) {

    isbn = inputIsbn.val();

    console.log(data);

    dataList = data.borrowerList;

    if(data.borrowerList.length > 0){

        $.each(dataList, function (i) {

            // User
            let user = dataList[i].user;
            let lastName = user.lastName;
            let firstName = user.firstName;
            let email = user.email;

            // Book
            let book = dataList[i].bookBorrowed;
            let day = book.borrowingDate.dayOfMonth;
            let month = book.borrowingDate.monthValue;
            let year = book.borrowingDate.year;
            let borrowingDate = day + " / " + month + " / " + year;
            let returnDate = new Date(book.returnDate);
            let returnFormatedDate = returnDate.getDay() + " / " + (returnDate.getMonth()+1) + " / " + returnDate.getFullYear();

            let btn = "<button class='btn btn-default' type='button' id='btn"+ i +"'>Enregistrer le retour de l'ouvrage</button>";

            let row = "<tr><td>"+lastName+"</td><td>"
                +firstName+"</td><td>"
                +email+"</td><td class='text-center'>"
                +borrowingDate+"</td><td class='text-center'>"
                +returnFormatedDate+"</td><td class='text-center'>"
                +btn+"</td></tr>";

            resultTableTbody.append(row);
        });

        bookCover.append("<img src='" + dataList[0].bookBorrowed.book.cover + "' alt='Cover' class='img-thumbnail'>");
        resultSearch.fadeIn();
    }
    else {
        modalAlert.attr("class", "alert alert-warning");
        modalMsgTitle.empty();
        modalMsgTitle.append("<b><i>La liste des emprunts est vide</i></b>");

        modalMessage.empty();
        modalMessage.append("Il n'y a actuellement aucun emprunt en cours pour cet ouvrage...");
        modalBox.modal();
    }
}

function loanReturn(id) {

    let email = dataList[id].user.email;
    let data = "isbn=" + isbn + "&email=" + email;

    $.ajax({
        type: "POST",
        url: "registerTheReturnOfALoan.action",
        data: data,
        dataType : "json",
        success: function (data) {
            openSuccessModal(data);
        },
        error: function (data) {
            openErrorModal(data);
        },
        complete: function () {
            resetAll();
        }
    });
}

function openErrorModal(result) {

    console.log(result);
}

function openSuccessModal(data) {

    modalAlert.attr("class", "alert alert-success");
    modalMsgTitle.empty();
    modalMsgTitle.append("<b><i>R&eacute;sultat de l'action</i></b>");

    modalMessage.empty();
    modalMessage.append("Le retour de l'ouvrage a bien &eacute;t&eacute; enregistr&eacute;.");
    modalBox.modal();

    modalBox.on('hidden.bs.modal', function () {
        resetAll();
    });
}

function resetAll() {

    resultTableTbody.empty();
    resultSearch.hide();
    inputIsbn.val("");
}