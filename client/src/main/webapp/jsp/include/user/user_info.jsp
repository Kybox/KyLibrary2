<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="panel panel-primary">
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
                <td><s:property value="#session.user.lastName"/></td>
                <td><s:property value="#session.user.firstName"/></td>
                <td><s:property value="#session.user.alertSender"/></td>
                <td><s:date name="#session.user.birthday" format="dd/MM/yyyy"/></td>
                <td><s:property value="#session.user.postalAddress"/></td>
                <td><s:property value="#session.user.tel"/></td>
            </tr>
        </table>
    </div>
</div>