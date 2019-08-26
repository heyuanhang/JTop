<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script>
			

		</script>
	</head>
	<body>
	<cms:QueryData service="cn.com.mjsoft.cms.content.service.ContentService" method="getContentOperInfoInfoForWK" objName="SI" var="${param.contentId},${param.infoType},,1" >
	
	<form id="renameForm" name="renameForm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
				

					
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td  class="input-title">
								 
								</td>
								<td class="td-input">
									审核人： 
									  <cms:SystemUser name="${SI.puserName}">
									  
									  	<i>${SysUser.userTrueName}</i>
									  	
									  </cms:SystemUser>
									 
									
									
									
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 执行步骤：<i>${SI.actionId}</i> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 时间： <i><cms:FormatDate date="${SI.eventDT}" /></i>
								</td>
							</tr>
							<tr>
								<td width="15%" class="input-title">
									<strong>审核意见</strong>
								</td>
								<td class="td-input">
									<textarea id="flowSug" style="width:545px; height:445px;" class="form-textarea">${SI.msgContent}</textarea>
									<br/><span class="ps"></span>
								</td>
							</tr>
							
							 
						

						</table>
						<!-- hidden -->
						<input type="hidden" id="entry" name="entry" value="${param.entry}" />
							
							 
					</form>
					<div style="height:15px"></div>
					<%--<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
								 	<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>
				--%></td>
			</tr>
				</cms:QueryData>
	
	
	</body>
</html>

<script type="text/javascript">

var api = frameElement.api, W = api.opener; 

 

 

function close()
{
	     
    api.close();      
}
				
									
</script>

