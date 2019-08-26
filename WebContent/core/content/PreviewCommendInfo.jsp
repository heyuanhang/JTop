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
		 
 
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						推荐位效果预览
					</div>

					<div style="height:8px"></div>

					<cms:SysCommendContent flag="${param.commFlag}">
						<cms:SysCommendRow>
							<span class="STYLE1">&nbsp; <a href="${RowInfo.url}" target="_blank"><font style="text-decoration:underline">${RowInfo.title}</font> </a> </span>
						</cms:SysCommendRow>
						<br/>
					</cms:SysCommendContent>

				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>
		<button type="reset" onclick="javascript:window.close();">
			<img src="../style/icon/close.png" alt="" />
			关闭预览页
		</button>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  


   
  
</script>
