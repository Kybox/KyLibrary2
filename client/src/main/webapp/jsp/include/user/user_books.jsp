<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Liste des livres actuellement empruntés</h3>
    </div>
    <div class="panel-body">
        <s:date name="dateToday" format="dd/MM/yyyy"/>
        <table class="table table-hover">
            <tr>
                <th>Titre</th>
                <th>Auteur</th>
                <th>Edition</th>
                <th>Date de restitution</th>
                <th>Prolonger l'emprunt</th>
            </tr>
            <s:iterator value="borrowedBooks">
                <s:if test="returned!=true">
                    <tr>
                        <td><s:property value="book.title"/></td>
                        <td><s:property value="book.author"/></td>
                        <td><s:property value="book.publisher"/></td>
                        <td><s:date name="returndate" format="dd/MM/yyyy"/></td>
                        <td>
                            <s:if test="extended!=true">
                                <button class="btn btn-primary">
                                    Prolonger
                                </button>
                            </s:if>
                            <s:else>
                                <button class="btn btn-default" disabled>
                                    Prolonger
                                </button>
                            </s:else>
                        </td>
                    </tr>
                </s:if>
            </s:iterator>
        </table>
    </div>
</div>