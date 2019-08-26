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
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												
											 
												
												<td width="10%">
													<strong>操作人</strong>
												</td>
												
												<td width="8%">
													<strong>执行动作</strong>
												</td>
												
											
												
												
												<td width="7%">
													<strong>执行时间</strong>
												</td>
												
												<td width="5%">
													<strong>设备</strong>
												</td>
												
												
												<td width="10%">
													<strong>备注</strong>
												</td>
												
												 

												
												
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.content.service.ContentService" method="getContentOperInfoInfoList" objName="SI" var="${param.contentId},${param.pn},15" >

												<tr>
													
													
													<td>
														&nbsp;
														<cms:SystemUser name="${SI.puserName}">
									  
														  	<i>${SysUser.userTrueName}</i>
														  	
														  </cms:SystemUser>
													</td>
													
													<td>
														${SI.actionId}
														
													</td>
													
													
													
													
													<td>
														&nbsp;<cms:FormatDate date="${SI.eventDT}" />
													</td>
													
														<td>
														<cms:if test="${SI.cMode=='微信端'}">
														<img src="../../../core/style/icons/ico-weixin.png" width="16" height="16" />
														</cms:if>
														<cms:else>
														<img src="../../../core/style/icons/television--pencil.png" width="16" height="16" />
														</cms:else>
													 
														
													</td>
													
													<td>
														 <div style="height:5px"></div>
														 <textarea readonly style="width:510px;height:120px" class="form-textarea" readonly>${SI.msgContent}</textarea>&nbsp;
														 <div style="height:5px"></div>
													</td>
													 
													
												
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="SI">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前内容无操作记录!
																</center>
															</td>
														</tr>
											</cms:Empty>
											<cms:PageInfo>
																					<tr id="pageBarTr">
																						<td colspan="8" class="PageBar" align="left">
																							<div class="fr">
																								<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
																								<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
																								<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
																								<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
																								<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
																							</div>
																							<script>
																										function query(flag)
																										{
																											var cp = 0;
																											
																											if('p' == flag)
																											{
													                                                             cp = parseInt('${Page.currentPage-1}');
																											}
												
																											if('n' == flag)
																											{
													                                                             cp = parseInt('${Page.currentPage+1}');
																											}
												
																											if('h' == flag)
																											{
													                                                             cp = 1;
																											}
												
																											if('l' == flag)
																											{
													                                                             cp = parseInt('${Page.pageCount}');
																											}
												
																											if(cp < 1)
																											{
													                                                           cp=1;
																											}
																										
																											
																											replaceUrlParam(window.location,'tab=2&pn='+cp);		
																										}
																							
																							
																										function jump()
																										{
																											replaceUrlParam(window.location,'tab=2&pn='+document.getElementById('pageJumpPos').value);
																										}
																									</script>
																							<div class="fl"></div>
																						</td>
																					</tr>
													</cms:PageInfo>


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

			

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">


function viewVersionContentPageDialog(contentId, mId)
{
	 


	W.$.dialog({ 
		id : 'main_content',
    	title :'查阅镜像内容',
    	width:   '1200px', 
    	 height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	 parent:api,
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/content/ViewVersionContent.jsp?contentId='+contentId+"&classId=${param.classId}&modelId="+mId+"&uid="+Math.random()+'&innerWidth=1200'
	});
 
}



</script>
