<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Réservation</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<%@ include file="include/header.jsp" %>
<hr>
<s:if test="confirmed!='true'">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="alert alert-info text-center">
                <p>
                    <b>Information</b>
                    <br>
                    Après avoir validé votre réservation, vous serez averti par e-mail dès qu'un exemplaire du livre sera disponible.
                </p>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>
    <div class="row">
        <div class="cold-md-3 col-sm-4">
            <div class="thumbnail">
                <img src="<s:property value="book.cover"/>" alt="Couverture">
            </div>
        </div>
        <div class="col-md-8 col-sm-7">
            <h3><b><s:property value="book.title"/></b></h3>
            <h4><s:property value="book.author"/></h4>
            <b>ISBN : </b><s:property value="book.isbn"/>
            <hr>
            Edition : <s:property value="book.publisher"/>
            <br>
            Date de parution : <s:date name="book.publishDate" format="dd/MM/yyyy"/>
            <br>
            Genre littéraire : <s:property value="book.genre"/>
            <br>
            Exemplaire(s) disponibles(s) : <s:property value="book.available"/> / <s:property value="book.nbCopies"/>
            <br>
            Date de retour estimée : <s:date name="book.returnDate" format="dd/MM/yyyy"/>
            <hr>
            <b>Résumé :</b>
            <br>
            <s:property value="book.summary"/>
            <hr>
            <s:if test="!authorizedReservation">
                <button class="btn btn-primary" type="submit" disabled>
                    Vous avez déjà une réservation en cours de cet ouvrage !
                </button>
            </s:if>
            <s:elseif test="!book.bookable">
                <button class="btn btn-primary" type="submit" disabled>
                    Cet ouvrage n'est plus disponible à une réservation...
                </button>
            </s:elseif>
            <s:else>
                <s:url action="reserve" var="urlReserve">
                    <s:param name="isbn"><s:property value="book.isbn"/></s:param>
                    <s:param name="confirmed"><s:property value="true"/></s:param>
                </s:url>
                <s:a href="%{urlReserve}">
                    <button class="btn btn-primary" type="submit">Confirmer la réservation</button>
                </s:a>
            </s:else>
        </div>
    </div>
</s:if>
<s:else>
    <%@ include file="include/reserve/reserve_details.jsp" %>
</s:else>
</body>
