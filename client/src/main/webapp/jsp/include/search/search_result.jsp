<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="container">
    <s:iterator value="bookList">
        <div class="row">
            <div class="col-md-3 col-sm-4">
                <div class="thumbnail">
                    <img src="<s:property value="cover"/>" alt="Couverture"/>
                </div>
            </div>
            <div class="col-md-9 col-sm-8">
                <h3><b><s:property value="title"/></b></h3>
                <h4><s:property value="author"/></h4>
                ISBN : <s:property value="isbn"/>
                <hr>
                Edition : <s:property value="publisher"/>
                <br>
                Date de parution : <s:date name="publishDate" format="dd/MM/yyyy"/>
                <br>
                Genre littéraire : <s:property value="genre"/>
                <br>
                Exemplaire(s) disponible(s) : <s:property value="available"/> / <s:property value="nbCopies"/>
                <s:if test="bookable">
                    <br>
                    <hr>
                    <s:if test="#session.user">
                        <s:url action="reserveSummary" var="urlReserve">
                            <s:param name="isbn"><s:property value="isbn"/></s:param>
                        </s:url>
                        <s:a href="%{urlReserve}">
                            <button class="btn btn-primary" type="submit">Réserver un emprunt</button>
                        </s:a>
                    </s:if>
                    <s:else>
                        <button class="btn btn-primary" type="submit" disabled>
                            Connectez-vous pour réserver un emprunt
                        </button>
                    </s:else>
                </s:if>
                <hr>
                Résumé : <br>
                <s:property value="summary"/>
            </div>
        </div>
        <hr>
    </s:iterator>
</div>
