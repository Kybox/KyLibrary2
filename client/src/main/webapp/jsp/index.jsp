<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Client</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<%@ include file="include/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-6 col-md-3"></div>
        <div class="col-md-6 text-center">
            <h2>Rechercher un livre :</h2>
            <form>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-primary">Rechercher</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
