<%--
  Created by IntelliJ IDEA.
  User: Kybox
  Date: 14/05/2018
  Time: 16:25
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>KyLibrary WebService</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
</head>
<body>
<div class="alert alert-info">
    <h2>KyLibrary WebService</h2>
</div>
<hr>
<div class="panel panel-default" style="margin-left: 10px">
    <div class="panel-body text-center">
        <a id="services" href="#">
            <button type="button" class="btn btn-info">Available SOAP services</button>
        </a>
        <br>
        <br>
        <a id="wsdl" href="#">
            <button type="button" class="btn btn-info">Display the WSDL file</button>
        </a>
        <br>
        <br>
        <a href="https://github.com/Kybox/KyLibrary/tree/master/webservice">
            <button type="button" class="btn btn-info">Git-hub repository</button>
        </a>
    </div>
</div>
<hr>
<script>
    document.getElementById("services").href = window.location.href + "SOAP/";
    document.getElementById("wsdl").href = window.location.href + "SOAP/LibraryWebService?wsdl";
</script>
</body>
</html>
