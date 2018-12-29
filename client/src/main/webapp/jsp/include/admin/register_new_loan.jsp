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
                           placeholder="Saisir le numéro ISBN">
                    <span class="input-group-btn">
                <button class="btn btn-default" type="submit">
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
            <form class="form" id="formSearchUserByName">
                <div class="input-group">
                    <input type="text" class="form-control" name="username" id="username"
                           placeholder="Saisir le nom et le prénom du client">
                    <span class="input-group-btn">
                <button class="btn btn-default" type="button">
                    <i class="glyphicon glyphicon-search"></i>
                    Rechercher un client
                </button>
            </span>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6 container">
        <div class="alert alert-success" id="resultBookSearch">
            <div class="col-md-2 text-left" id="bookCover"></div>
            <div id="bookInfo"></div>
        </div>
    </div>
    <div class="col-md-6 container">
        <div class="alert alert-success" id="resultUserSearch"></div>
    </div>
</div>
<hr>
<div class="alert alert-default col-md-12">
    En attente des informations requises...
</div>
<div class="modal fade" id="registerLoanModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-user" style="margin-right:6px;"></span>
                    Profil utilisateur
                </h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger" role="alert">
                    <p>
                        <span class="glyphicon glyphicon-alert" style="margin-right:6px;"></span>
                        <b>Une erreur s'est produite !</b>
                    </p>
                    <br>
                    <span id="modalErrorMsg"></span>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>

