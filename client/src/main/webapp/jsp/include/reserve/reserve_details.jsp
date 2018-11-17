<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <div class="alert alert-success text-center">
            <p>
                <b>Réservation confirmée</b>
                <br>
                L'ouvrage intitulé <s:property value="book.title"/> de <s:property value="book.author"/>
                a bien été réservé.
                <br>
                Vous recevrez prochainement un e-mail lorsqu'il sera disponible.
            </p>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>
