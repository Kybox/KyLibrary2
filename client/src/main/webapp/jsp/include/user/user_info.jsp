<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title">Information relatives à votre compte utilisateur</h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th>E-mail</th>
                <th>Date de naissance</th>
                <th>Adresse postale</th>
                <th>Tel</th>
            </tr>
            <tr>
                <td><s:property value="#session['client'].lastName"/></td>
                <td><s:property value="#session['client'].firstName"/></td>
                <td><s:property value="#session['client'].email"/></td>
                <td><s:date name="#session['client'].birthday" format="dd/MM/yyyy"/></td>
                <td><s:property value="#session['client'].postalAddress"/></td>
                <td><s:property value="#session['client'].tel"/></td>
            </tr>
        </table>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-body">
        <form class="form-inline">
            <input type="checkbox" id="cBoxAlertSender" class="css-checkbox" value=""
                   checked="<s:property value="#session['client'].alertSender"/>">
            <label for="cBoxAlertSender" class="css-label">
                Me notifier par e-mail des prêts arrivant à expiration
            </label>
        </form>
    </div>
</div>
<div class="modal fade" id="alertSenderModal" tabindex="-1" role="dialog">
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
                <div class="alert alert-info" id="modalAlertSenderBody" role="alert">
                    <p>
                        <span class="glyphicon glyphicon-alert" style="margin-right:6px;"></span>
                        <span id="modalAlertSenderMsgTitle"></span>
                    </p>
                    <br>
                    <span id="modalAlertSenderMessage"></span>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>
