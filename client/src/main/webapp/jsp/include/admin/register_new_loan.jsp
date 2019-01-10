<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
    <div class="alert alert-warning text-right">
        <b><i>Enregistrer un nouvel emprunt</i></b>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="alert alert-default text-left">
            <form class="form" id="formSearchBookByIsbn">
                <div class="input-group">
                    <input type="text" class="form-control" name="isbn" id="isbn"
                           placeholder="Saisir le numéro ISBN" required>
                    <span class="input-group-btn">
                <button class="btn btn-default" type="submit" id="btnSearchBook">
                    <i class="glyphicon glyphicon-search"></i>
                    Rechercher le livre
                </button>
            </span>
                </div>
            </form>
        </div>
    </div>
    <div class="col-md-6">
        <div class="alert alert-default text-right">
            <form class="form-group form-inline" id="formSearchUserByName">
                <input type="text" class="form-control" name="firstName" id="firstName"
                       placeholder="Saisir le nom du client" required>
                <input type="text" class="form-control" name="lastName" id="lastName"
                       placeholder="Saisir le prénom du client" required>
                <button class="btn btn-default" type="submit" id="btnSearchUser">
                    <i class="glyphicon glyphicon-search"></i>
                    Rechercher un client
                </button>
            </form>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="alert alert-default" id="resultBookSearch">
            <div class="col-md-2 text-left" id="bookCover"></div>
            <div id="bookInfo"></div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="alert alert-default" id="resultUserSearch"></div>
    </div>
</div>
<hr>
<div class="alert alert-default col-md-12 text-center">
    <button class="btn btn-primary" type="button" id="btn_register_new_loan" disabled>
        Enregistrer l'emprunt
    </button>
</div>
<div class="modal fade" id="registerLoanModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-user" style="margin-right:6px;"></span>
                    Information :
                </h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger" id="modalAlert" role="alert">
                    <p>
                        <span class="glyphicon glyphicon-alert" style="margin-right:6px;"></span>
                        <span id="modalMsgTitle"><b><i>Une erreur s'est produite !</i></b></span>
                    </p>
                    <br>
                    <span id="modalMessage"></span>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>

