<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="bodyId" required="true"%>
<%@attribute name="satelliteId" required="true"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<head>
	<link rel="stylesheet" href="css/jquery-ui.css" />
	<link rel="stylesheet" href="css/bootstrap.css" />
	<link rel="stylesheet" href="css/bootstrap-theme.css" />
    <script src="js/jquery-1.9.1.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/bootstrap.js"></script>
    <link rel="stylesheet" href="css/style.css" />
    <style type="text/css">
      body {
      	width: 860px;
        margin: auto;
      }
    </style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="span10">
				<div class="header">
			    	<img src="images/funcube_logo.png" style="margin: auto"/>
					<h4>FUNcube Telemetry Processor</h4>
				</div>
			</div>
			<!--
			<div class="span10">
			    <nav class="navbar navbar-default" role="navigation">
			    	<div class="navbar-header">
			    		<a class="navbar-brand" href="2#">FUNcube(Flight)</a> 
			    	</div>
			    </nav>
			</div>
			-->
			<div class="span10">
				<jsp:doBody/>
			</div>
		</div>
	</div>
</body>
