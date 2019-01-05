<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${jspTtile}"/></title>
    <%@include file="include/head.jsp"%>
</head>
<body>
<%@include file="include/header.jsp"%>
<c:if test="${empty batchResult}">
    <div class="alert alert-warning text-center">
        <b>Lancer le batch d'envoi d'e-mails aux utilisateurs dont la réservation est disponible.</b>
    </div>
    <%@ include file="include/form.jsp"%>
    <c:if test="${not empty Error}">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="alert alert-danger text-center">
                    <b><c:out value="${Error}"></c:out></b>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    </c:if>
    <hr>
</c:if>
<c:if test="${not empty batchResult}">
    <div class="alert alert-warning text-center">
        <b>Résultats du batch d'envoi d'e-mails aux utilisateurs dont la réservation est disponible.</b>
    </div>
    <div class="row">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <%@include file="include/result.jsp"%>
                    </tbody>
                </table>
            </div>
            <div class="col-md-3"></div>
        </div>
    </div>
</c:if>
<%@ include file="include/modal.jsp"%>
</body>
</html>
