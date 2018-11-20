<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Mes réservations en cours</h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <tr>
                <th>Titre</th>
                <th>Auteur</th>
                <th class="text-center">Edition</th>
                <th class="text-center">Date de réservation</th>
                <th class="text-center">Position</th>
                <th class="text-center">Action</th>
            </tr>
            <s:iterator value="reservedBooks">
                <s:if test="pending">
                    <tr>
                        <td id="bookTitle" style="vertical-align: middle;">
                            <s:property value="book.title"/>
                        </td>
                        <td style="vertical-align: middle;">
                            <s:property value="book.author"/>
                        </td>
                        <td class="text-center" style="vertical-align: middle;">
                            <s:property value="book.publisher"/>
                        </td>
                        <td class="text-center" style="vertical-align: middle;">
                            <s:date name="reserveDate" format="dd/MM/yyyy"/>
                        </td>
                        <td class="text-center" style="vertical-align: middle;">
                            <s:property value="position"/> / <s:property value="total"/>
                        </td>
                        <td class="text-center" style="vertical-align: middle;">
                            <button class="btn btn-danger"
                                    id="btnCancel<s:property value="book.isbn"/>"
                                    onclick="cancelReserv('<s:property value="book.isbn"/>')">
                                <span class="glyphicon glyphicon-remove"></span>
                                Annuler
                            </button>
                        </td>
                    </tr>
                </s:if>
            </s:iterator>
        </table>
    </div>
</div>
<div class="modal fade" id="resultModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Mise à jour des livres réservés</h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-info" role="alert">
                    <p><b>Annulation de la réservation</b></p>
                    <p id="modalInfo"></p>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="modalBtn">Fermer</button>
            </div>
        </div>
    </div>
</div>
