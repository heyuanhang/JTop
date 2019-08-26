<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		%>

		<title>合肥明靖信息科技JTopCMS</title>
		<link rel="stylesheet" type="text/css" href="../../style/default/console.css" />

	</head>
	<script>



</script>
	<frameset rows="80,*" border="0" frameborder="yes" framespacing="0" name="mainframe" id="mainframe">
		<frame src="top_2.jsp" name="topFrame" framespacing="0" frameborder="0" scrolling="no" noresize="noresize" id="topFrame" />
		<frameset row="*" cols="152,9,*" framespacing="0" frameborder="0" scrolling="no" noresize="noresize" name="centerFrame" id="centerFrame">
			<frame src="<cms:BasePath/>core/console/ListLeft.jsp" name="leftFrame" frameborder="0" scrolling="no" id="leftFrame" />
			<frame src="mid.jsp" name="midFrame" frameborder="0" scrolling="no" id="midFrame" />
			<frame src="welcome.jsp" name="contentFrame" id="contentFrame" />
		</frameset>
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>
