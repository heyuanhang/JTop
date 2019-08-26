<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../style/blue/js/jquery-1.7.2.min.js"></script>

		<script>
			var api = frameElement.api, W = api.opener; 
		 
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

		<div style="height:1px"></div>
		<form  method="post">
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							
							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlists" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												
											 
												
												<td width="8%">
													<strong>字段名</strong>
												</td>
											 
												
									 			
												<td width="56%">
													<strong>敏感内容</strong>
												</td>
												
											 

												
												
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.content.service.ContentService" method="getHighlightSW" objName="HL" var="${param.code}" >

												<tr>
													
													
													<td>
														&nbsp;
														${HL.sn}
													</td>
													
													<td>
														${HL.swh}
													</td>
													
													
													
													 
													
												
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="HL">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前内容无操作记录!
																</center>
															</td>
														</tr>
											</cms:Empty>
											 


										</table>
									</div>

								</td>
							</tr>


						</table>

						</form>

					</td>
				</tr>

				<tr>
					<td height="10">
						&nbsp;
					</td>
				</tr>
				
				
			</table>
				<div style="height:5px;"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="btnbg100">
									<div style="float:right">
										<a id="buttonHref" href="javascript:add();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>自动替换&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>返回修改&nbsp;</b> </a>
									</div>
								</tr>
							</table>
						</div>
			

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

 
function add()
{
	var actionName = '<cms:DecodeParam enc="utf-8" str="${param.actionName}" />';

	var actionId = '${param.actionId}';
	
	var fromStepId = '${param.fromStepId}';
	
	var toStepId = '${param.toStepId}';
	
	var draft = '${param.draft}';
	
	 	
    api.get('main_content').addContentInfo(actionName, actionId, fromStepId, toStepId, draft);
    
    close();

}

function close()
{
	api.close();
}
		


</script>
