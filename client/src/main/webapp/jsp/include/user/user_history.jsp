<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
    <div class="panel-group" id="accordion">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                        Historique des livres empruntés
                    </a>
                </h4>
            </div>
            <div id="collapse1" class="panel-collapse collapse">
                <div class="panel-body">
                    <table class="table table-hover">
                        <tr>
                            <th>Titre</th>
                            <th>Auteur</th>
                            <th class="text-center">Edition</th>
                            <th class="text-center">Date de retour</th>
                        </tr>
                        <s:iterator value="borrowedBooks">
                            <s:if test="returned!=false">
                                <tr id="book<s:property value="book.isbn"/>">
                                    <td><s:property value="book.title"/></td>
                                    <td><s:property value="book.author"/></td>
                                    <td class="text-center"><s:property value="book.publisher"/></td>
                                    <td class="text-center">
                                        <s:date name="returndate" format="dd/MM/yyyy"/>
                                    </td>
                                </tr>
                            </s:if>
                        </s:iterator>
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                        Historique des livres réservés
                    </a>
                </h4>
            </div>
            <div id="collapse2" class="panel-collapse collapse">
                <div class="panel-body">
                    <table class="table table-hover">
                        <tr>
                            <th>Titre</th>
                            <th>Auteur</th>
                            <th class="text-center">Edition</th>
                            <th class="text-center">Date de réservation</th>
                            <th class="text-center">Statut</th>
                        </tr>
                        <s:iterator value="reservedBooks">
                            <tr id="book<s:property value="book.isbn"/>">
                                <td><s:property value="book.title"/></td>
                                <td><s:property value="book.author"/></td>
                                <td class="text-center"><s:property value="book.publisher"/></td>
                                <td class="text-center"><s:date name="reserveDate" format="dd/MM/yyyy"/></td>
                                <td class="text-center">
                                    <s:if test="pending">En cours</s:if>
                                    <s:else>Terminé</s:else>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
        </div>
    </div>