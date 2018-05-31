<%@ page contentType="text/html;charset=UTF-8" %>
<div style="padding-left: 10px;">
    <h2>
        <a href="index.action" style="text-decoration: none !important;">
            <b><i>KyLibrary Client</i></b>
        </a>
    </h2>
</div>
<div class="container-fluid text-right">
    <s:if test="#session.user">
        <a href="login.action">
            <button class="btn btn-danger">
                <span class="glyphicon glyphicon-user"></span>
                Déconnexion
            </button>
        </a>
    </s:if>
    <s:else>
        <a href="login.action">
            <button class="btn btn-success">
                <span class="glyphicon glyphicon-user"></span>
                Connexion utilisateur
            </button>
        </a>
    </s:else>
</div>
<br>
<br>