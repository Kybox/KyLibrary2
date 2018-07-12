<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Historique des livres empruntés</h3>
    </div>
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
                        <td id="bookTitle"><s:property value="book.title"/></td>
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