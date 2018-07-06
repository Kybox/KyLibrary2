<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>KyLibrary Batch</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
</head>
<body>
<div class="alert alert-info">
    <h2>KyLibrary Batch</h2>
</div>
<hr>
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="panel panel-default" style="margin-left:10px; margin-right:10px">
            <div class="panel-body text-center">
                <form method="post" action="index">
                    <div class="form-group">
                        <label for="email">Adresse e-mail :</label>
                        <input type="email" class="form-control" id="email" name="email">
                    </div>
                    <div class="form-group">
                        <label for="pwd">Mot de passe :</label>
                        <input type="password" class="form-control" id="pwd" name="password">
                    </div>
                    <button type="submit" class="btn btn-default">Connexion</button>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3"></div>
</div>
<hr>
<c:if test="${result!=null}">
    <div class="col-md-12 text-center">
        [Code 400 = BAD REQUEST] - [Code 401 = UNAUTHORIZED]<br>
        [Code 500 = INTERNAL SERVER ERROR]<br>
        [Code 200 = OK]
    </div>
    <br>
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <table class="table table-hover" style="width:100%">
            <tr>
                <th>Message</th>
                <th>Résultat</th>
            </tr>
            <c:forEach var="entry" items="${result}">
                <tr>
                    <td><c:out value="${entry.key}"/></td>
                    <td><c:out value="${entry.value}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-3"></div>
</div>
</c:if>
</body>
</html>
