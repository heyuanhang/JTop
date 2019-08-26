


<%@ page contentType="text/html; charset=utf-8" isErrorPage="true" import="java.io.*"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<body>
		<h2>
			问题如下：
		</h2>
		<hr>

		<br>
		<%
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			out.println(exception.getMessage());
		%>
	</body>






</html>
