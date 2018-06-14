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
                Edition <s:property value="publisher"/>
                <br>
                Date de parution : <s:date name="publishDate.toGregorianCalendar()" format="dd/MM/yyyy"/>
                <br>
                Genre littéraire : <s:property value="genre"/>
                <hr>
                Résumé : <br>
                <s:property value="summary"/>
            </div>
        </div>
        <hr>
    </s:iterator>
</div>
