<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" tabindex="-1" role="dialog" id="processModal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Batch - KyLibrary</h4>
            </div>
            <div class="modal-body">
                <p class="text-info">
                <c:out value="${jspModalDesc}"/></p>
                <p>Connexion et traitement auprès du web service en cours.</p>
            </div>
            <div class="modal-footer">
                Merci de patienter quelques instants...
            </div>
        </div>
    </div>
</div>