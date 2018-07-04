<%@ page contentType="text/html;charset=UTF-8" %>
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
</body>
</html>
