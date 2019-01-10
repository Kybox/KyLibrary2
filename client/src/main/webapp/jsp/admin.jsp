<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="action" value="#request['struts.actionMapping'].name"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Administration</title>
    <%@ include file="include/head.jsp" %>
    <script src="<s:property value="rootPath"/>js/user.js" charset="UTF-8"></script>
    <s:if test="#action=='registerNewLoanDefault'">
        <script src="<s:property value="rootPath"/>js/register_new_loan.js" type="text/javascript" charset="UTF-8"></script>
    </s:if>
    <s:elseif test="#action=='registerTheReturnOfALoanDefault'">
        <script src="<s:property value="rootPath"/>js/register_the_return_of_a_loan.js" type="text/javascript" charset="UTF-8"></script>
    </s:elseif>
    <s:elseif test="#action=='listAllBookLoans'">
        <script src="<s:property value="rootPath"/>js/list_all_book_loans.js" type="text/javascript" charset="UTF-8"></script>
    </s:elseif>
</head>
<body>
<%@ include file="include/header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                    Sélectionner une action d'administration
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="dropdown-header">Gestion des utilisateurs</li>
                    <li class="disabled"><a href="#">Créer un nouveau compte client</a></li>
                    <li class="disabled"><a href="#">Modifier un compte client</a></li>
                    <li class="disabled"><a href="#">Supprimer un compte client</a></li>
                    <li class="divider"></li>
                    <li class="dropdown-header">Gestion des ouvrages</li>
                    <li class="disabled"><a href="#">Enregistrer un nouvel ouvrage</a></li>
                    <li class="disabled"><a href="#">Modifier un ouvrage</a></li>
                    <li class="disabled"><a href="#">Supprimer un ouvrage</a></li>
                    <li><a href="registerNewLoanDefault.action">Enregistrer un nouvel emprunt</a></li>
                    <li><a href="registerTheReturnOfALoanDefault.action">Enregistrer un retour d'emprunt</a></li>
                    <li><a href="listAllBookLoans.action">Lister tous les emprunts</a></li>
                </ul>
            </div>
            <hr>
        </div>
        <s:if test="#action=='registerNewLoanDefault'">
            <%@ include file="include/admin/register_new_loan.jsp"%>
        </s:if>
        <s:elseif test="#action=='registerTheReturnOfALoanDefault'">
            <%@ include file="include/admin/register_the_return_of_a_loan.jsp"%>
        </s:elseif>
        <s:elseif test="#action=='listAllBookLoans'">
            <%@ include file="include/admin/listAllBookLoans.jsp"%>
        </s:elseif>
    </div>
</body>
</html>
