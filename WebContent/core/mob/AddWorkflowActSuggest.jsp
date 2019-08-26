<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
			<link rel="stylesheet" href="css/mui.min.css">
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>
			
			 
			
			
		</script>
	</head>
	<body>
	
	<form id="renameForm" name="renameForm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
				

					
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							
							<tr>
								<td width="15%" class="input-title">
									<strong>建议</strong>
								</td>
								<td class="td-input">
									<textarea id="flowSug" style="width:85%; height:265px;" class="form-textarea"></textarea>
									<br/><span class="ps">流程建议将附送给下一步骤审核者,一般为当前执行者的本次执行理由和建议</span>
								</td>
							</tr>
						

						</table>
						<!-- hidden -->
						<input type="hidden" id="entry" name="entry" value="${param.entry}" />
							
							 
					</form>
					<div style="height:15px"></div>
					 
					<nav class="mui-bar mui-bar-tab mui-text-center">
											<button type="button" onclick="javascript:addSuggest();" class="mui-btn mui-btn-success">
												确定
											</button>
											&nbsp;
											<button type="button" onclick="javascript:closeD();" class="mui-btn">
												取消
											</button>
						 
				 </nav>
				</td>
			</tr>
	
	
	
	</body>
</html>

<script type="text/javascript">

var api = frameElement.api, W = api.opener; 

 


$('#flowSug').css('height','${param.wsh-120}px');




$('#flowSug').val(W.$('#jtopcms_sys_flow_suguest').val());


function addSuggest()
{
	var flowSug = $('#flowSug').val();
	
	 
	W.$('#jtopcms_sys_flow_suguest').val(flowSug);
	 
 	 api.close();      
}


function closeD()
{
	   
    api.close();      
}
				
									
</script>

