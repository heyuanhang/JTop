<%@ page contentType="text/html; charset=utf-8" session="false"%>

<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<base target="_self">
		<%
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		%>
		<title>添加模板</title>

		<script type="text/javascript" src="../javascript/commonUtil.js"></script>

		<!--加载 js -->

	</head>



	<body>

	</body>
</html>
正在渲染......
<script type="text/javascript">

var entry = "${param.entry}".replaceAll("\\"+splitChar,"/");
//alert(entry);
	//alert("<cms:Domain/>"+"site/default/"+entry);
	gotoCurrentPage("<cms:Domain/>"+"site/default/"+entry+"?random="+Math.random());
//document.location.href="<cms:Domain/>"+"site/default/"+entry+"?random="+Math.random();


</script>



