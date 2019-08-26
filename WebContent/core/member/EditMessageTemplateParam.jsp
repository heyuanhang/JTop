<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		
		<script>  
			var api = frameElement.api, W = api.opener;
			
			 if("true"==="${param.fromFlow}")
	         {     	 	
	            api.close(); 
	            
	       		W.window.location.reload();
	         }
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						参数信息
					</div>
					<cms:SystemList querySign="query_single_msg_template_param" objName="MGTP" var="${param.id}">
					<form id="templateParamForm" name="templateParamForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="25%" class="input-title">
									<strong>参数名称</strong>
								</td>
								<td class="td-input">
									<input id="paramName" name="paramName" type="text" class="form-input" style="width:220px"value="${MGTP.paramName}"/>
									
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>替换标志</strong>
								</td>
								<td class="td-input">
									<input id="paramFlag" name="paramFlag" type="text" class="form-input" style="width:220px" value="${MGTP.paramFlag}"/>
								</td>
							</tr>
														
						</table>
						
						
						<!-- hidden -->
						<input type="hidden" id="tpId" name="tpId" value="${MGTP.tpId}"/>
						
						<cms:Token mode="html"/>
						
						
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitTemplateParam();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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



function submitTemplateParam()
{
	encodeFormInput('templateParamForm', false);
	
	var form = document.getElementById("templateParamForm");
	
	form.action = '<cms:BasePath/>member/editMTP.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
</cms:SystemList>
