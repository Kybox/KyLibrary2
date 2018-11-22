<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>KyLibrary Batch</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous">
    </script>
</head>
<body>
<div class="alert alert-info">
    <h2>KyLibrary Batch</h2>
</div>
<hr>
<c:if test="${result==null}">
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="panel panel-default" style="margin-left:10px; margin-right:10px">
            <div class="panel-body text-center">
                <form method="post" action="index">
                    <div class="form-group">
                        <label for="alertSender">Adresse e-mail :</label>
                        <input type="alertSender" class="form-control" id="alertSender" name="alertSender">
                    </div>
                    <div class="form-group">
                        <label for="pwd">Mot de passe :</label>
                        <input type="password" class="form-control" id="pwd" name="password">
                    </div>
                    <button type="submit" class="btn btn-default" data-toggle="modal" data-target="#processModal">
                        Connexion
                    </button>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3"></div>
</div>
<hr>
</c:if>
<c:if test="${result!=null}">
    <div class="col-md-12 text-center">
        [Code 400 = BAD REQUEST] - [Code 401 = UNAUTHORIZED]<br>
        [Code 500 = INTERNAL SERVER ERROR]<br>
        [Code 200 = OK]
    </div>
    <br>
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
<div class="modal fade" tabindex="-1" role="dialog" id="processModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Batch - Reminder</h4>
            </div>
            <div class="modal-body">
                <p class="text-info">Processus de récupération des ouvrages non rendus</p>
                <p>Connexion et traitement auprès du web service en cours.</p>
            </div>
            <div class="modal-footer">
                Merci de patienter quelques instants...
            </div>
        </div>
    </div>
</div><
</body>
</html>
