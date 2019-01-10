<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
    <div class="alert alert-warning text-right">
        <b><i>Lister tous les emprunts</i></b>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-default text-left">
            <form class="form" id="formSearchBookByIsbn">
                <div class="input-group">
                    <input type="text" class="form-control" name="isbn" id="isbn"
                           placeholder="Saisir le numéro ISBN" required>
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="submit" id="btnListAllReturned">
                            <i class="glyphicon glyphicon-search"></i>
                            Lister tous les emprunts retournés
                        </button>
                        <button class="btn btn-default" type="submit" id="btnListAllUneturned">
                            <i class="glyphicon glyphicon-search"></i>
                            Lister tous les emprunts en cours
                        </button>
                        <button class="btn btn-default" type="submit" id="btnListAll">
                            <i class="glyphicon glyphicon-search"></i>
                            Lister tous les emprunts
                        </button>
                    </span>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="row" id="resultSearch" hidden>
    <div class="col-md-12">
        <div class="col-md-2" id="bookCover"></div>
        <div class="col-md-10">
            <table class="table table-hover" id="resultTable">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th class="text-center">Date d'emprunt</th>
                    <th class="text-center">Date de retour</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" id="allBookLoanModal" tabindex="-1" role="dialog">
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
                        <span id="modalMsgTitle"></span>
                    </p>
                    <br>
                    <span id="modalMessage"></span>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>
