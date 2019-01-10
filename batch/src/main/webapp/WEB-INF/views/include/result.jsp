<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${batchResult}" var="entry">
    <tr>
        <td>${entry.key}</td>
        <td>${entry.value}</td>
    </tr>
</c:forEach>
