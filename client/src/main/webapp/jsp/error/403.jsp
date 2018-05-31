<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Authentification requise</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<br>
<div class="alert alert-warning text-center" role="alert">
    L'accès à ces données nécessite une authentification utilisateur !
    <br>
    <br>
    <a href="login.action">
        <button class="btn btn-info">
            Se connecter
        </button>
    </a>
</div>
</body>
</html>