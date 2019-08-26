<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>

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
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												
															
												<td width="3%">
													<strong>版本号</strong>
												</td>
												
												<td width="8%">
													<strong>操作人</strong>
												</td>
												
												<td width="16%">
													<strong>版本记录</strong>
												</td>
												
												<td width="8%">
													<strong>修改时间</strong>
												</td>

												<td width="5%">
													<strong>操作</strong>
												</td>
											</tr>
 
											<cms:QueryData service="cn.com.mjsoft.cms.templet.service.TempletService" method="getTemplateEditorTag" objName="TE" var="${param.tName},">

												<tr>
												
													
													<td>
														${TE.editionId}
													</td>
																										
													<td>
														<cms:SystemUser id="${TE.editor}">${SysUser.userName}</cms:SystemUser>
													</td>
													
													<td>
														<div style="height:3px"></div>
														<textarea readonly  style="height:50px;width:320px" class="form-textarea">${TE.editDesc}</textarea>
														<div style="height:3px"></div>
													</td>
													
													<td>
														&nbsp;&nbsp;<cms:FormatDate date="${TE.editDT}" />
													</td>
													
													<td>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openViewCodeAndTextDialog('${TE.filePath}','','${TE.editionId}')">查看</a>&nbsp;&nbsp;</span>
															
															</center>														
													</td>
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="TE">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前模板没有版本记录!
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

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">





function openViewCodeAndTextDialog(entry,type,eId)
{
	if('css' == type)
	{
		type = 'text/css';
	}
	else if('js' == type)
	{
		type = 'text/javascript';
	}
	else
	{
		type = 'text/html';
	}
	 
	var url = '<cms:Domain/>resources/directViewPage.do?mode=edition&entry='+encodeData(encodeURI(encodeURIComponent(entry)))+'&type='+type+'&etId='+eId+'&fileEntry='+encodeData(encodeURIComponent(encodeURIComponent('${param.fileEntry}')));
	
	window.open(url,'','width=1300,height=875，top=20,left=40,toolbar=no,menubar=no,scrollbars=yes,titlebar=no, resizable=no,location=no, status=no');
	
}

</script>
