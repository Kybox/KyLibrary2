<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Liste des livres actuellement empruntés</h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <tr>
                <th>Titre</th>
                <th>Auteur</th>
                <th class="text-center">Edition</th>
                <th class="text-center">Date de retour</th>
                <th class="text-center">Prolonger l'emprunt</th>
            </tr>
            <s:iterator value="borrowedBooks">
                <s:if test="returned!=true">
                    <tr id="book<s:property value="book.isbn"/>">
                        <td id="bookTitle"><s:property value="book.title"/></td>
                        <td><s:property value="book.author"/></td>
                        <td class="text-center"><s:property value="book.publisher"/></td>
                        <td class="text-center">
                            <s:date name="returndate.toGregorianCalendar()" format="dd/MM/yyyy"/>
                        </td>
                        <td class="text-center">
                            <s:if test="extended!=true">
                                <button class="btn btn-primary"
                                        id="btnExtend<s:property value="book.isbn"/>"
                                        onclick="extend('<s:property value="book.isbn"/>')">
                                    + 4 semaines
                                </button>
                            </s:if>
                            <s:else>
                                <button class="btn btn-default" disabled>
                                    Durée déjà prolongée
                                </button>
                            </s:else>
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
                <h4 class="modal-title">Mise à jour des livres empruntés</h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-info" role="alert">
                    <p><b>Prolongation de l'emprunt</b></p>
                    <p id="modalInfo"></p>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="modalBtn">Fermer</button>
            </div>
        </div>
    </div>
</div>