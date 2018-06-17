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
            <form id="formSearch" method="post" action="search.action">
                <input type="text" name="keyword" class="form-control" placeholder="Saisir ici votre recherche..." required>
                <br>
                <button class="btn btn-primary" type="submit">Rechercher</button>
            </form>
        </div>
    </div>
</div>
<hr>
<s:if test="bookList != null">
    <s:if test="bookList.size > 0">
        <%@ include file="include/search/search_result.jsp" %>
    </s:if>
    <s:else>
        <div class="text-center">
            <h3>Aucun résultat pour votre recherche...</h3>
        </div>
    </s:else>
</s:if>
</body>
</html>
