<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="action" value="#request['struts.actionMapping'].name"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Administration</title>
    <%@ include file="include/head.jsp" %>
    <script src="<s:property value="rootPath"/>js/user.js" charset="UTF-8"></script>
    <s:if test="#action=='adminNewLoan'">
        <script src="<s:property value="rootPath"/>js/register_new_loan.js"></script>
    </s:if>
</head>
<body>
<%@ include file="include/header.jsp" %>
<body>
    <div class="container">
        <div class="row">
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                    Sélectionner une action d'administration
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="dropdown-header">Gestion des utilisateurs</li>
                    <li><a href="#">Créer un nouveau compte client</a></li>
                    <li><a href="#">Modifier un compte client</a></li>
                    <li><a href="#">Supprimer un compte client</a></li>
                    <li class="divider"></li>
                    <li class="dropdown-header">Gestion des ouvrages</li>
                    <li><a href="#">Enregistrer un nouvel ouvrage</a></li>
                    <li><a href="#">Modifier un ouvrage</a></li>
                    <li><a href="#">Supprimer un ouvrage</a></li>
                    <li><a href="adminNewLoan.action">Enregistrer un nouvel emprunt</a></li>
                    <li><a href="#">Enregistrer un retour d'emprunt</a></li>
                    <li><a href="#">Lister tout les emprunts</a></li>
                </ul>
            </div>
            <hr>
        </div>
        <s:if test="#action=='adminNewLoan'">
            <%@ include file="include/admin/register_new_loan.jsp"%>
        </s:if>
    </div>
</body>
</html>
