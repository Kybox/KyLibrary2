<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Compte utilisateur</title>
    <%@ include file="include/head.jsp" %>
    <script src="<s:property value="rootPath"/>js/user.js" charset="UTF-8"></script>
</head>
<body>
<%@ include file="include/header.jsp" %>
<div class="container">
    <div class="row profile">
        <div class="col-md-2 col-xs-3">
            <nav class="profile-sidebar">
                <div class="profile-userpic">
                    <img src="https://image.flaticon.com/icons/svg/149/149071.svg" class="img-responsive" alt="">
                </div>
                <div class="profile-usertitle text-center">
                    <div class="profile-usertitle-name">
                        <h3>
                            <s:property value="user.firstName"/> <s:property value="user.lastName"/>
                        </h3>
                    </div>
                </div>
                <div class="profile-usermenu">
                    <div class="list-group">

                        <a href="user.action?tab=info" id="info" class="list-group-item">
                            <span class="glyphicon glyphicon-user"></span>
                            Mes informations
                        </a>
                        <a href="user.action?tab=books" id="books" class="list-group-item">
                            <span class="glyphicon glyphicon-transfer"></span>
                            Livres à rendre
                        </a>
                        <a href="userReservations.action?tab=reservations" id="reservations" class="list-group-item">
                            <span class="glyphicon glyphicon-calendar"></span>
                            Réservations
                        </a>
                        <a href="user.action?tab=history" id="history" class="list-group-item">
                            <span class="glyphicon glyphicon-list-alt"></span>
                            Historique
                        </a>

                    </div>
                </div>
            </nav>
        </div>
        <div class="col-md-10">
            <div class="profile-content">

                <s:if test="#request['struts.actionMapping'].name=='userReservations'">
                    <%@ include file="include/user/user_reservation.jsp"%>
                </s:if>

                <s:else>

                <!-- URL = user.action?tab=... -->
                <s:set var="content" value="%{#parameters.tab[0]}"/>

                <!-- Borrowed books -->
                <s:if test="%{#content=='books'}">
                    <%@ include file="include/user/user_books.jsp"%>
                </s:if>

                <!-- History -->
                <s:elseif test="%{#content=='history'}">
                    <%@ include file="include/user/user_history.jsp"%>
                </s:elseif>

                <!-- Reservation -->
                <s:elseif test="%{#content=='reservations'}">
                    <%@ include file="include/user/user_reservation.jsp"%>
                </s:elseif>

                <!-- User info -->
                <s:else>
                    <%@ include file="include/user/user_info.jsp"%>
                </s:else>
                </s:else>
            </div>
        </div>
    </div>
</div>
</body>
</html>
