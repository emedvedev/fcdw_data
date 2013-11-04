<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%
   // Set refresh, autoload time as 5 seconds
   response.setIntHeader("Refresh", 5);
%>
<t:publicpage bodyId="homePage" satellliteId="${satelliteId}">
	<jsp:body>	
		<p>You are seeing the dashboard of the data processor.
		<br>This URL is used to receive packets from FUNcube Dashboards running on PCs</br>
		<p>Please click <a href = "https://warehouse.funcube.org.uk">&nbsp;here&nbsp;</a>
			to visit the FUNcube Data Warehouse.</p>
		<br><strong>If you get a 404 (not found) error, we are doing an upgrade.<strong></strong></br>
		</p>
	</jsp:body>
</t:publicpage>