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
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
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
		<cms:LoginUser>
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">微信公众号</a> &raquo;
						<a href="#">公众号配置</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="cfgForm" name="cfgForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:editWxCfg();" class="btnwithico" onclick=""><img src="../style/icons/wrench--pencil.png" width="16" height="16" /><b>公众号配置&nbsp;</b> </a>
										
										<span style="align:middle">&nbsp;(若公众账号关键配置不完整或错误，将无法使用微信模块其他功能)</span>
									</div>
									<div class="fr">

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												
											
												<td width="8%">
													<strong>配置项名称</strong>
												</td>
												
												<td width="20%">
													<strong>项目值</strong>
												</td>

												
											</tr>

											<cms:QueryData single="true" objName="WxCfg" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWeixinConfigForTag"  var="">
												 
												<tr>
													
													
													<td>
													   &nbsp;公众号名称:
													</td>
													
													<td>
														<div style="height:10px"></div>
														<textarea id="wxName" name="wxName" style="width:570px; height:45px;" class="form-textarea">${WxCfg.wxName}</textarea>
														<div style="height:10px"></div>
													</td>
													

													
												</tr>
												
												
												<tr>
													
													
													<td>
														 &nbsp;公众号appID:
													</td>
													
													<td>
														<div style="height:10px"></div>
														<textarea id="appId" name="appId" style="width:570px; height:45px;" class="form-textarea">${WxCfg.appId}</textarea>
														<div style="height:10px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;公众号appsSecret:
													</td>
													
													<td>
														<div style="height:10px"></div>
														<textarea id="appsSecret" name="appsSecret" style="width:570px; height:45px;" class="form-textarea">${WxCfg.appsSecret}</textarea>
														<div style="height:10px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;原始ID:
													</td>
													
													<td>
														<div style="height:10px"></div>
														<textarea id="mainId" name="mainId" style="width:570px; height:45px;" class="form-textarea">${WxCfg.mainId}</textarea>
														<div style="height:10px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;接口TOKEN:
													</td>
													
													<td>
														<div style="height:10px"></div>
														<textarea id="apiToken" name="apiToken" style="width:570px; height:45px;" class="form-textarea">${WxCfg.apiToken}</textarea>
														<div style="height:10px"></div>
													</td>
													

													
												</tr>
												 
												
												
												<tr>
													
													
													<td>
													    &nbsp;用户关注欢迎信息:
													</td>
													
													<td>
														<div style="height:10px"></div>
														 
														
													<cms:if test="${WxCfg.subWelInfoId != -1}">
														<cms:QueryData single="true" objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var=",,,,${WxCfg.subWelInfoId}">						
													
													 		
															 <textarea readonly id="msgTitle" name="msgTitle" style="width:570px; height:45px;" class="form-textarea">[图文${News.infoId}] ${News.title}</textarea>
													
														<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
														</cms:QueryData>
													
													</cms:if>

													<cms:elseif  test="${WxCfg.subWelResId != -1}">
													
														<cms:QueryData single="true" objName="WR" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxResForTag"  var="${WxCfg.subWelResId},,,,">
														 
														<cms:ResInfo res="${WR.imageRes}">
																												
															  <textarea readonly id="msgTitle" name="msgTitle" style="width:570px; height:45px;" class="form-textarea">[${WR.resTypeStr}${WR.wrId}] ${WR.resTitle}</textarea>
													
														<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
													
														
														</cms:ResInfo>
														</cms:QueryData>
													
													</cms:elseif>
													 
													<cms:else>
													
														<textarea readonly id="msgTitle" name="msgTitle" style="width:570px; height:45px;" class="form-textarea"></textarea>
													<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
														
													</cms:else>
													
													<input type="hidden" id="mtId" name="mtId"  class="form-input" value="${WxCfg.subWelInfoId}"></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value="${WxCfg.subWelResId}"></input>
														<div style="height:10px"></div>
													</td>
													

													
												</tr>
												
												<!-- hidden -->
												 <input type="hidden" id="acId" name="acId" value="${WxCfg.acId}" />
												 
												 <cms:Token mode="html"/>
	

											</cms:QueryData>
											
											


										</table>
									</div>
									<div class="mainbody-right"></div>
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




function editWxCfg()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许改动微信配置！', 
	       cancel: true 
	                    
		  });
		  return;
	}
 
	var url = "<cms:BasePath/>wx/configWx.do?subWelInfoId="+$('#mtId').val()+"&subWelResId="+$('#resId').val()+"&<cms:Token mode='param'/>";;
	
	var postData = encodeURI($("#cfgForm").serialize());
	
	postData = postData.replace(/\+/g, " ");
	postData = encodeData(postData);
                    
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	 
			               if('success' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '180px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/succ.png', 
							    		
							                    content: '更新微信公众号配置成功!',
					
							    				ok:function()
							    				{ 
					             					window.location.reload();
							    				}
								});   
			               		
			               } 	
			               else if('-1' == msg)
			               {
			               	       $.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: "已存在相同的原始ID,更新失败！",
							
									    				cancel: function()
									    				{ 
							             					window.location.reload();
									    				}
									});
			               }  
			               else
			               {
			               	       $.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: function()
									    				{ 
							             					window.location.reload();
									    				}
									});
			               }   
			              
			            }
    });	
}


function openSelectWxItemInfoDialog()
{
	$.dialog({ 
	    id : 'odcrd',
    	title : '选取素材',
    	width: '860px',  
    	height: '600px', 
    	lock: true, 
        max: false, 
       
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/SelectWxItemInfo.jsp?apiId='
	});
}

</script>
</cms:LoginUser>
