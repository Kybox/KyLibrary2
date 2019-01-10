$(document).ready(function() {

    $("#cBoxAlertSender").on("click", function () {
        if($(this).is(":checked")){
            $(this).attr("checked", "checked").change();
            updateAlertSendStatus(true);
        }
        else {
            $(this).removeAttr("checked").change();
            updateAlertSendStatus(false);
        }
        $(this).prop("disabled", true);
    });
});

/**
 * Update alertSenderStatus
 */
function updateAlertSendStatus(status){

    let data = "status=" + status;
    $.ajax({
        type: "POST",
        url: "updateAlertSenderStatus.action",
        data: data,
        dataType: "json",
        success : function () {
            openAlertSenderModal("success", status);
        },
        error : function () {
            openAlertSenderModal("error");
        }
    });
}

/**
 * AlertSenderModal
 */
function openAlertSenderModal(state, status){

    let alertSenderModal = $("#alertSenderModal");
    let alert = $("#modalAlertSenderBody");
    let modalTitle = $("#modalAlertSenderMsgTitle");
    let modalMsg = $("#modalAlertSenderMessage");

    modalTitle.empty();
    modalMsg.empty();

    if(state === "success") {
        alert.attr("class", "alert alert-success");
        modalTitle.append("<b><i>Confirmation de l'action</i></b>");
        if(status === true) modalMsg.append("Les notifications par e-mail sont maintenant actives.");
        else modalMsg.append("Les notifications par e-mail sont d&eacute;sormais inactives.");
    }
    else {
        alert.attr("class", "alert alert-danger");
        modalTitle.append("<b><i>Une erreur s'est produite</i></b>");
        modalMsg.append("La modification des notifications par e-mail n'a pas &eacute;t&eacute; enregistrée.")
    }

    alertSenderModal.modal();

    alertSenderModal.on('hidden.bs.modal', function () {
        $("#cBoxAlertSender").prop("disabled", false);
    });
}

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

    if(urlParam.has("cancel")){
        let isbn = urlParam.get("cancel");
        showModalCancelReservation(isbn);
    }
});

function extend(isbn) {

    $.ajax({
        type: "POST",
        data: "isbn="+isbn,
        url: "extendBorrowing.action",
        success: function (data) {
            window.location.href = "userBorrowing.action?tab=books&extended=book" + isbn;
        },
        error: function (data) {
            alert("Oops, une erreur est survenue...")
        }
    });
}

function cancelReserv(isbn) {

    $.ajax({
        type: "POST",
        data: "isbn="+isbn,
        url: "cancelReservation.action",
        success: function (data) {
            window.location.href = "userReservations.action?tab=reservations&cancel=book" + isbn;
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

function showModalCancelReservation(isbn) {

    //let info = $("#modalInfo");
    //let bookTitle = $("#book" + isbn + " > #bookTitle");

    //info.text("Livre : " + bookTitle);

    $("#resultModal").modal("show");
}

function updateContent(isbn) {

    let btnExtend = $("#btnExtend" + isbn);
    btnExtend.removeAttr("onclick");
    btnExtend.prop({disabled: true});
    btnExtend.html("Dur&eacutee prolong&eacutee");

    location.reload();
}