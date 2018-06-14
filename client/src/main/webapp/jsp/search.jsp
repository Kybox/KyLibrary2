<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>KyLibrary - Recherche</title>
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
<s:if test="bookList!=null">
    bookList != null / size = <s:property value="bookList.size"/>
    <s:iterator value="bookList">
        Titre : <s:property value="title"/>
    </s:iterator>
</s:if>
<s:else>bookList == null</s:else>
</body>
</html>
