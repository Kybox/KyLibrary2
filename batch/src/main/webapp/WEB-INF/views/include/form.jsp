<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="panel panel-default" style="margin-left:10px; margin-right:10px">
            <div class="panel-body text-center">
                <%--@elvariable id="loginForm" type="fr.kybox.entities.LoginForm"--%>
                <form:form method="post" modelAttribute="loginForm">
                    <div class="form-group">
                        <label for="email">Adresse e-mail :</label>
                        <form:input path="email" cssClass="form-control" id="email" required="required"/>
                    </div>
                    <div class="form-group">
                        <label for="password">Mot de passe :</label>
                        <form:password path="password" cssClass="form-control" id="password" required="required"/>
                    </div>
                    <input type="submit" class="btn btn-default" value="Connexion" data-toggle="modal" data-target="#processModal">
                </form:form>
            </div>
        </div>
    </div>
    <div class="col-md-3"></div>
</div>