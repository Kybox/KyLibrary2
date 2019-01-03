// Global selectors
let resultSearch;
let resultTable;
let resultTableTbody;
let inputIsbn;
let btnListAllReturned;
let btnListAllUneturned;
let btnListAll;
let allBookLoanModal;
let modalMsgTitle;
let modalMessage;
let modalAlert;
let allSubmitBtn;
let bookCover;

$(document).ready(function() {

    resultSearch = $("#resultSearch");
    resultTable = $("#resultTable");
    resultTableTbody = $("#resultTable > tbody");
    inputIsbn = $("#isbn");
    btnListAllReturned = $("#btnListAllReturned");
    btnListAllUneturned = $("#btnListAllUneturned");
    btnListAll = $("#btnListAll");
    allBookLoanModal = $("#allBookLoanModal");
    modalMsgTitle = $("#modalMsgTitle");
    modalMessage = $("#modalMessage");
    modalAlert = $("#modalAlert");
    allSubmitBtn = $("#formSearchBookByIsbn :input[type=submit]");
    bookCover = $("#bookCover");

    /** Submit action **/
    $("#formSearchBookByIsbn").submit(function (e) {
        e.preventDefault();
        allSubmitBtn.prop("disabled", true);
    });

    btnListAllReturned.click(function () {
        searchBorrowersByIsbn(true);
    });
    btnListAllUneturned.click(function () {
        searchBorrowersByIsbn(false);
    });
    btnListAll.click(function () {
        searchBorrowersByIsbn(null);
    });
});

function searchBorrowersByIsbn(param) {

    let data;
    if(param === null) data = "isbn=" + inputIsbn.val();
    else data = "isbn=" + inputIsbn.val() + "&returned=" + param;

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
            allSubmitBtn.prop("disabled", false);
        }
    });
}

function displaySearchResult(data) {

    resultSearch.hide();
    resultTableTbody.empty();
    bookCover.empty();

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
                +returnFormatedDate+"</td></tr>";

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
        allBookLoanModal.modal();
    }
}

function openErrorModal(result) {

    console.log(result);
}