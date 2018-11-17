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
                <th class="text-center">Action</th>
            </tr>
            <s:iterator value="reservedBooks">
                <s:if test="pending">
                    <tr>
                        <td style="vertical-align: middle;"><s:property value="book.title"/></td>
                        <td style="vertical-align: middle;"><s:property value="book.author"/></td>
                        <td class="text-center" style="vertical-align: middle;"><s:property value="book.publisher"/></td>
                        <td class="text-center" style="vertical-align: middle;"><s:date name="reserveDate" format="dd/MM/yyyy"/></td>
                        <td class="text-center" style="vertical-align: middle;">
                            <button class="btn btn-danger">
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
