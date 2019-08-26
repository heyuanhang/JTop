<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>


		<script>
	    	//表格变色
			$(function()
			{ 
		   		$("#showlist tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});  
    
        </script>
	</head>
	<body>

		<!--main start-->


		<div style="height:5px;"></div>
		<center>

			<table  class="listdate-high" width="99%" border="0" cellpadding="0" cellspacing="0">

				<tr class="datahead">

					<td width="40%" align="left">
						<strong>&nbsp;参数项名称</strong>
					</td>

					<td align="left" colspan="3">
						<strong>&nbsp;项目值</strong>
					</td>

				</tr>
				
				<tr>

					<td align="left">
						&nbsp;当前系统版本

					</td>
					<td align="left">

						&nbsp;
						<cms:QueryData service="cn.com.mjsoft.cms.common.service.CommonSystemService" method="getCMSCoreVer" objName="Ver">
							${Ver}
						</cms:QueryData>
						 

					</td>
				</tr>

				<tr>

					<td align="left">
						&nbsp;服务器架构及系统

					</td>
					<td align="left">

						&nbsp;
						<%=System.getProperty("os.arch")%>
						-
						<%=System.getProperty("os.name")%>
						(
						<%=System.getProperty("os.version")%>
						)

					</td>
				</tr>
				
				<tr>
					<td align="left">
						&nbsp;Java软件提供商

					</td>
					<td align="left">

						&nbsp;
						<%=System.getProperty("java.vendor")%>


					</td>
				</tr>

				<tr>
					<td align="left">
						&nbsp;当前Java运行版本

					</td>
					<td align="left">

						&nbsp;

						<%=System.getProperty("java.runtime.name")%>
						-
						<%=System.getProperty("java.version")%>

					</td>
				</tr>

				<tr>
					<td align="left">
						&nbsp;Java_home目录

					</td>
					<td align="left">

						&nbsp;
						<%=System.getProperty("java.home")%>

					</td>
				</tr>
				
				<tr>
					<td align="left">
						&nbsp;JavaEE软件信息

					</td>
					<td align="left">

						&nbsp;
						<%= application.getServerInfo() %>

					</td>
				</tr>
				
				<tr>
					<td align="left">
						&nbsp;部署上下文目录

					</td>
					<td align="left">

						&nbsp;
						<%= application.getContextPath() %>

					</td>
				</tr>
				
				<tr>
					<td align="left">
						&nbsp;JVM剩余内存

					</td>
					<td align="left">

						&nbsp;
						<%=Runtime.getRuntime().freeMemory()/(1024*1024)%> M

					</td>
				</tr>
				
				<tr>
					<td align="left">
						&nbsp;JVM当前内存

					</td>
					<td align="left">

						&nbsp;
						<%=Runtime.getRuntime().totalMemory()/(1024*1024)%> M

					</td>
				</tr>
				
				<tr>
					<td align="left">
						&nbsp;JVM最大内存

					</td>
					<td align="left">

						&nbsp;
						<%=Runtime.getRuntime().maxMemory()/(1024*1024)%> M

					</td>
				</tr>
				
				
			</table>


		</center>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


</script>

