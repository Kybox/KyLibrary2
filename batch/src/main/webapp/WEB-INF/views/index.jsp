<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>KyLibrary Batch</title>
    <%@include file="include/head.jsp"%>
</head>
<body>
<%@include file="include/header.jsp"%>
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="panel panel-default" style="margin-left:10px; margin-right:10px">
            <div class="panel-body text-center">
                <div class="dropdown btn-group">
                    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                        Sélectionner le batch à lancer (authentification requise) :
                        <span class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/Unreturned">Batch des ouvrages non rendus à temps</a></li>
                        <li><a href="${pageContext.request.contextPath}/Reservation">Batch des réservations disponibles</a></li>
                        <li><a href="#">Batch des prêts arrivant à expiration</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3"></div>
</div>
</body>
</html>
