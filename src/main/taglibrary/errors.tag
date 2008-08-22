<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ tag description="display all errors" pageEncoding="UTF-8" %>

<div id="errors">
<ul><c:forEach var="error" items="${errors.iterator}">
<li><fmt:message key="${error.key}"/></li>
</c:forEach></ul>
</div>
