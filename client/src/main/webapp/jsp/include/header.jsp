<%@ page contentType="text/html;charset=UTF-8" %>
<div style="padding-left: 10px;">
    <h2>
        <a href="index.action" style="text-decoration: none !important;">
            <b><i>KyLibrary Client</i></b>
        </a>
    </h2>
</div>
<div class="container-fluid text-right">
    <a href="search.action">
        <button class="btn btn-primary">
            <span class="glyphicon glyphicon-search"></span>
            Rechercher un livre
        </button>
    </a>
    <s:if test="#session.client">
        <span class="glyphicon glyphicon-minus"></span>
        <a href="userInfo.action">
            <button class="btn btn-info">
                <span class="glyphicon glyphicon-user"></span>
                Mon compte
            </button>
        </a>
        <span class="glyphicon glyphicon-minus"></span>
        <a href="logout.action">
            <button class="btn btn-danger">
                <span class="glyphicon glyphicon-remove"></span>
                Déconnexion
            </button>
        </a>
    </s:if>
    <s:elseif test="#session.manager">
        <span class="glyphicon glyphicon-minus"></span>
        <a href="manager.action">
            <button class="btn btn-info">
                <span class="glyphicon glyphicon-user"></span>
                Administrer
            </button>
        </a>
        <span class="glyphicon glyphicon-minus"></span>
        <a href="logout.action">
            <button class="btn btn-danger">
                <span class="glyphicon glyphicon-remove"></span>
                Déconnexion
            </button>
        </a>
    </s:elseif>
    <s:elseif test="#session.admin">
        <span class="glyphicon glyphicon-minus"></span>
        <a href="adminDefault.action">
            <button class="btn btn-info">
                <span class="glyphicon glyphicon-user"></span>
                Administrer
            </button>
        </a>
        <span class="glyphicon glyphicon-minus"></span>
        <a href="logout.action">
            <button class="btn btn-danger">
                <span class="glyphicon glyphicon-remove"></span>
                Déconnexion
            </button>
        </a>
    </s:elseif>
    <s:else>
        <span class="glyphicon glyphicon-minus"></span>
        <a href="login.action">
            <button class="btn btn-success">
                <span class="glyphicon glyphicon-user"></span>
                Connexion
            </button>
        </a>
    </s:else>
</div>
<br>
<br>