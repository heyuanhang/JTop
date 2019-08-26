<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>
		<script>  

        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						外链词信息
					</div>

					<form id="serverConfigForm" name="serverConfigForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="25%" class="input-title">
									<strong>外链词汇</strong>
								</td>
								<td class="td-input">
									<input id="serverName" name="serverName" type="text" class="form-input" size="40" />
									<span class="red">*</span><br/><span class="ps">若同时添加多个外链词，用逗号隔离区分</span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>链接URL</strong>
								</td>
								<td class="td-input">
									<input id="serverName" name="serverName" type="text" class="form-input" size="40" />
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>页面打开</strong>
								</td>
								<td class="td-input">
									<input id="" name="openMode" type="radio" class="form-radio" checked/>弹出新的页面
									&nbsp;
									<input id="" name="openMode" type="radio" class="form-radio" />当前页打开
								</td>
							</tr>

							
						</table>
						<!-- hidden -->
						<input type="hidden" id="censorInfo" name="censorInfo" />
						
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitServerConfig();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>


				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

var api = frameElement.api, W = api.opener;

function submitServerConfig()
{
	var form = document.getElementById("serverConfigForm");
	
	form.action = '../../site/addServerConfig.do';
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
