<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Authentification</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<%@ include file="include/header.jsp" %>
<div class="row">
    <div class="col-md-2"></div>
<div class="panel panel-default col-md-8">
    <div class="panel-heading" style="background: transparent;">
        <h3 class="panel-title">
            <span class="glyphicon glyphicon-log-in" style="margin-right:6px;"></span>
            <b>Connexion à un compte utilisateur</b>
        </h3>
    </div>
    <div class="panel-body">
        <br>
        <form method="post" action="login.action">
            <span class="glyphicon glyphicon-envelope" style="margin-right:6px;"></span>
            <label for="alertSender">Adresse e-mail :</label>
            <input type="alertSender" class="form-control" name="login" id="alertSender" required>
            <br>
            <span class="glyphicon glyphicon-lock" style="margin-right:6px;"></span>
            <label for="password">Mot de passe :</label>
            <input type="password" name="password" class="form-control" id="password" required>
            <hr/>
            <button type="submit" class="btn btn-primary">Connexion !</button>
        </form>
    </div>
</div>
    <div class="col-md-2"></div>
</div>
<s:if test="hasActionErrors()">
    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Connexion à un compte utilisateur</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        <p><b>Une erreur est survenue :</b></p>
                        <p><s:actionerror/></p>
                    </div>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
                </div>
            </div>
        </div>
    </div>
    <script type="application/javascript">
        $("#loginModal").modal("show");
    </script>
</s:if>
</body>
</html>
