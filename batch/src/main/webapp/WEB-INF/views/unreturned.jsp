<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Batch - Ouvrage non rendus</title>
    <%@include file="include/head.jsp"%>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<%@include file="include/header.jsp"%>
<c:if test="${empty batchResult}">
    <div class="alert alert-warning text-center">
        <b>Lancer le batch d'envoi d'e-mails aux utilisateurs dont les ouvrages n'ont pas été retournés à temps.</b>
    </div>
    <%@ include file="include/form.jsp"%>
    <c:if test="${not empty error}">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="alert alert-danger text-center">
                    <b><c:out value="${error}"></c:out></b>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    </c:if>
    <hr>
</c:if>
<c:if test="${not empty batchResult}">
    <div class="alert alert-warning text-center">
        <b>Résultats du batch d'envoi d'e-mails aux utilisateurs dont les ouvrages n'ont pas été retournés à temps.</b>
    </div>
    <div class="row">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <table class="table table-bordered table-hover">
                    <tbody>
                    <c:forEach items="${batchResult}" var="entry">
                        <tr>
                            <td>${entry.key}</td>
                            <td>${entry.value}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-md-3"></div>
        </div>
    </div>
</c:if>
</body>
</html>
