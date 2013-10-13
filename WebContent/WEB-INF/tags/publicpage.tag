<%@tag description="Public Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="bodyId" required="true"%>
<%@attribute name="satellliteId" required="true"%>

<t:genericpage bodyId="${bodyId}" satelliteId="${satelliteId}">
    <jsp:body>
        
          	<jsp:doBody/>
    
    </jsp:body>
</t:genericpage>