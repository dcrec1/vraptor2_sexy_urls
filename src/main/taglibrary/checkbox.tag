<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.vraptor.org/jsp" prefix="vraptor" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="selected" %>
<c:choose><c:when test="${selected}">
<input type="checkbox" value="true" name="${name}" checked/>
</c:when><c:otherwise>
<input type="checkbox" value="true" name="${name}"/>
</c:otherwise></c:choose>
</select>