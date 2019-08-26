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
						<a href="#">系统配置 </a> &raquo; <a href="#">系统全局配置</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="rtForm" name="rtForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:editRTCfg();" class="btnwithico" onclick=""><img src="../style/icons/wrench--pencil.png" width="16" height="16" /><b>修改配置值&nbsp;</b> </a>
										 <a href="javascript:openShowSystemStatDialog();" class="btnwithico" onclick=""><img src="../style/icons/wrench--pencil.png" width="16" height="16" /><b>系统参数&nbsp;</b> </a>
										
										 
										 
										<span style="align:middle">&nbsp;(以下配置为系统全局配置，由CMS后台以及各站群节点共同使用)</span>
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
												
											
												<td width="16%">
													<strong>配置项名称</strong>
												</td>
												
												<td width="40%">
													<strong>项目值</strong>
												</td>

												
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.common.service.CommonSystemService" method="getCmsSystemRuntimeConfigTag" objName="CmsCfg" var="">

												<tr>
													
													
													<td>
														 &nbsp;系统后台访问地址:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="domain" name="domain" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.domain}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;访问端口:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="port" name="port" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.port}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;部署根目录:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="context" name="context" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.context}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;管理员白名单(IP和IP段):
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="managerIp" name="managerIp" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.managerIp}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;管理员会话有效时间(分钟):
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="loginTime" name="loginTime" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.loginTime}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;管理员登录限制:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<select id="aldOpt" name="aldOpt">
														<option value="1">
															 每天可登录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
														</option>
														<option value="2">
															  周一至周五可登录 
														</option>
														<option value="3">
															  周一至周六可登录 
														</option>
														
														</select>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
														
														
														 <select id="alhOpt" name="alhOpt" onchange="javascript:change();">
															<option value="1">
																 24小时可登录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
															</option>
															<option value="2">
																 限制时间内可登录 
															</option>
														 </select>
														 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
														 
														 <select id="alStartHour" name="alStartHour">
															<option value="0">
																 0点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</option>
															<option value="1">
																 1点&nbsp;&nbsp; 
															</option>
															<option value="2">
																 2点&nbsp;&nbsp; 
															</option>
															<option value="3">
																 3点&nbsp;&nbsp; 
															</option>
															<option value="4">
																 4点&nbsp;&nbsp; 
															</option>
															<option value="5">
																 5点&nbsp;&nbsp; 
															</option>
															<option value="6">
																 6点&nbsp;&nbsp; 
															</option>
															<option value="7">
																 7点&nbsp;&nbsp; 
															</option>
															<option value="8">
																 8点&nbsp;&nbsp; 
															</option>
															<option value="9">
																 9点&nbsp;&nbsp; 
															</option>
															<option value="10">
																 10点&nbsp;&nbsp; 
															</option>
															<option value="11">
																 11点&nbsp;&nbsp; 
															</option>
															<option value="12">
																 12点&nbsp;&nbsp; 
															</option>
															<option value="13">
																 13点&nbsp;&nbsp; 
															</option>
															<option value="14">
																 14点&nbsp;&nbsp; 
															</option>
															<option value="15">
																 15点&nbsp;&nbsp; 
															</option>
															<option value="16">
																 16点&nbsp;&nbsp; 
															</option>
															<option value="17">
																 17点&nbsp;&nbsp; 
															</option>
															<option value="18">
																 18点&nbsp;&nbsp; 
															</option>
															<option value="19">
																 19点&nbsp;&nbsp; 
															</option>
															<option value="20">
																 20点&nbsp;&nbsp; 
															</option>
															<option value="21">
																 21点&nbsp;&nbsp; 
															</option>
															<option value="22">
																 22点&nbsp;&nbsp; 
															</option>
															<option value="23">
																 23点&nbsp;&nbsp; 
															</option>
															 
														 </select>
														 &nbsp;至&nbsp; 
														  
														  
														 
														 <select id="alEndHour" name="alEndHour">
															<option value="0">
																 0点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</option>
															<option value="1">
																 1点&nbsp;&nbsp; 
															</option>
															<option value="2">
																 2点&nbsp;&nbsp; 
															</option>
															<option value="3">
																 3点&nbsp;&nbsp; 
															</option>
															<option value="4">
																 4点&nbsp;&nbsp; 
															</option>
															<option value="5">
																 5点&nbsp;&nbsp; 
															</option>
															<option value="6">
																 6点&nbsp;&nbsp; 
															</option>
															<option value="7">
																 7点&nbsp;&nbsp; 
															</option>
															<option value="8">
																 8点&nbsp;&nbsp; 
															</option>
															<option value="9">
																 9点&nbsp;&nbsp; 
															</option>
															<option value="10">
																 10点&nbsp;&nbsp; 
															</option>
															<option value="11">
																 11点&nbsp;&nbsp; 
															</option>
															<option value="12">
																 12点&nbsp;&nbsp; 
															</option>
															<option value="13">
																 13点&nbsp;&nbsp; 
															</option>
															<option value="14">
																 14点&nbsp;&nbsp; 
															</option>
															<option value="15">
																 15点&nbsp;&nbsp; 
															</option>
															<option value="16">
																 16点&nbsp;&nbsp; 
															</option>
															<option value="17">
																 17点&nbsp;&nbsp; 
															</option>
															<option value="18">
																 18点&nbsp;&nbsp; 
															</option>
															<option value="19">
																 19点&nbsp;&nbsp; 
															</option>
															<option value="20">
																 20点&nbsp;&nbsp; 
															</option>
															<option value="21">
																 21点&nbsp;&nbsp; 
															</option>
															<option value="22">
																 22点&nbsp;&nbsp; 
															</option>
															<option value="23">
																 23点&nbsp;&nbsp; 
															</option>
															 
														 </select>
														 
														 
										
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													   &nbsp;攻击生效为IP黑名单次数:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="dangerAccessCount" name="dangerAccessCount" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.dangerAccessCount}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;OPenOffice主目录(需重启):
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="openOfficePath" name="openOfficePath" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.openOfficePath}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												
												
												 
												
												<tr>
													
													
													<td>
													    &nbsp;百度地图默认城市:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="baiduMapDefCity" name="baiduMapDefCity" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.baiduMapDefCity}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													  &nbsp;根机构组织名称:
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="rootOrgName" name="rootOrgName" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.rootOrgName}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;系统备份最多天数(0值停止自动备份):
													</td>
													
													<td>
														<div style="height:7px"></div>
														<textarea id="backupDay" name="backupDay" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.backupDay}</textarea>
														<div style="height:7px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;备份执行时刻(24小时制整点值):
													</td>
													
													<td>
														<div style="height:9px"></div>
														<textarea id="backupHourExe" name="backupHourExe" style="width:550px; height:42px;" class="form-textarea">${CmsCfg.backupHourExe}</textarea>
														<div style="height:9px"></div>
													</td>
													

													
												</tr>
												
												<tr>
													
													
													<td>
													    &nbsp;备份同步FTP服务器:
													</td>
													
													<td>
														<div style="height:15px"></div>
														<select id="backupFtpId" name="backupFtpId">
														<option value="-1">
															----------------- 不使用备份同步到服务器功能 -----------------
														</option>
														
														<cms:DispenseServer>
															<option value="${Server.serverId}">
																${Server.serverName}
															</option>
														</cms:DispenseServer>
										
														</select>
														<div style="height:15px"></div>
													</td>
													

													
												</tr>
												
												
												
												
												
												<script>

												
												initSelect('aldOpt','${CmsCfg.aldOpt}');
												initSelect('alhOpt','${CmsCfg.alhOpt}');
												
												if($('#alhOpt').val() == 2)
												{
														$('#alStartHour').removeAttr("disabled"); ;
														$('#alEndHour').removeAttr("disabled"); ;
												}
													
												initSelect('alStartHour','${CmsCfg.alStartHour}');
												initSelect('alEndHour','${CmsCfg.alEndHour}');
												
												
												
												
												initSelect('backupFtpId','${CmsCfg.backupFtpId}');
											 
												
												</script>

											</cms:QueryData>
											
											<cms:Empty flag="CmsCfg">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前没有数据!
																</center>
															</td>
														</tr>
											</cms:Empty>


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




function editRTCfg()
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
	    				
	                    content: '只有机构主管允许改动系统配置！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	var url = "<cms:BasePath/>common/editRTcfg.do";
	
	var postData = encodeURI($("#rtForm").serialize());
	
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
							    				icon: '32X32/i.png', 
							    		
							                    content: '更新系统全局配置成功!',
					
							    				ok:function()
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
							
									    				cancel: true
									});
			               }   
			              
			            }
    });	
}



function openShowSystemStatDialog()
{
	$.dialog({ 
	    id : 'osssd',
    	title : '系统运行环境',
    	width: '700px', 
    	height: '520px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/stat/CmsServerStat.jsp'
	});

}

function change()
{
	if($('#alhOpt').val() == 1)
	{
		$('#alStartHour').val("0");
		$('#alEndHour').val("0");
		$('#alStartHour').attr("disabled","disabled");
		$('#alEndHour').attr("disabled","disabled");
	}
	else if($('#alhOpt').val() == 2)
	{
		$('#alStartHour').removeAttr("disabled"); ;
		$('#alEndHour').removeAttr("disabled"); ;
	}
	 

}

</script>
</cms:LoginUser>
