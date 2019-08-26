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
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		<script>  
		
		//去掉点击链接 虚线边框
      
		
	
	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             W.$.dialog.tips('改动角色权限成功',1.5,'32X32/succ.png'); 
         	}
       		       
         }
         
         //表格变色
			$(function()
			{ 
		   		$("#showlistclass tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlistspec tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlistcommend tr[id!='pageBarTr']").hover(function() 
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
		<cms:CurrentSite>
			<cms:SystemRole id="${param.roleId}">
				

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab('two',1,5)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/gear.png" width="16" height="16" />系统菜单权限&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab('two',2,5)">
										<a href="javascript:;"><img src="../style/icons/inbox-document-text.png" width="16" height="16" />栏目与内容管理权限&nbsp;</a>
									</li>
									<li id="two3" onclick="setTab('two',3,5)">
										<a href="javascript:;"><img src="../style/icon/folder_table.png" width="16" height="16" />专题管理权限&nbsp;</a>
									</li>
									<li id="two4" onclick="setTab('two',4,5)">
										<a href="javascript:;"><img src="../style/icons/document-share.png" width="16" height="16" />推荐位权限&nbsp;</a>
									</li>
									
									<li id="two5" onclick="setTab('two',5,5)">
										<a href="javascript:;"><img src="../style/icons/socket--pencil.png" width="16" height="16" />留言本权限&nbsp;</a>
									</li>
									
									<%--<li id="two6" onclick="setTab('two',6,6)">
										<a href="javascript:;"><img src="../style/icons/document-share.png" width="16" height="16" />表单数据权限&nbsp;</a>
									</li>


								--%></ul>
							</div>

							<form id="roleAuthForm" name="roleAuthForm" method="post">
								<div class="auntion_tagRoom_Content">
									<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
										<ul>
											<li>
												<cms:ResourceList orgId="${Role.orgId}">
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
														<tr>
															<td class="input-title">

															</td>
															<td width="100%" class="td-input">
																<a href="javascript:regRes('checkAll');" class="btnwithico"> <img src="../style/icons/tick.png" width="16" height="16" /> <b>全选&nbsp;</b> </a>
															</td>
														</tr>

														<tr>
															<td class="input-title">

															</td>
															<td class="td-input">
																<cms:Resource>
																	
																	<cms:if test="${Res.resourceType == 1}">
																	<!-- 系统入口 -->
																		<div class="addtit">
																			<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																			<img src="<cms:Domain/>core/style/icons/${Res.icon}" width="16" height="16" />
																			<strong>${Res.resourceName}</strong>
																		</div>
																		<br />
																	</cms:if>
																	<cms:elseif test="${Res.resourceType == 3}">
																	   	<!-- 菜单 -->										
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																		<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																		<img src="<cms:Domain/>core/style/icons/${Res.icon}" width="16" height="16" />
																		${Res.resourceName}
																		<br />
																		<cms:if test="${Res.isLeaf == 0}">
																		</cms:if>
																	</cms:elseif>
																	<cms:elseif test="${Res.resourceType == 4}">
																		<!-- 组合 -->
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;										
																		<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																		${Res.resourceName}
																		<br/>
																		<cms:if test="${Res.isLastChild == 1}">
																			<br />															 
																		</cms:if>
																	</cms:elseif>
																</cms:Resource>
																
															</td>
														</tr>
													</table>
												</cms:ResourceList>
												<cms:Empty flag="Res">
												
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
														
														<td class="tdbgyew">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
			
													</tr>
														
														</table>
													
													
			
												</cms:Empty>

												<div style="height:40px;"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a href="javascript:submitRoleAuthForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>
											</li>
										</ul>
									</div>

									<!-- 第二部分:站点 -->
									<%--<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			<a href="javascript:;" class="btnwithico" onclick=""><img src="../style/blue/icon/application--pencil.png" width="16" height="16" /><b>编辑&nbsp;</b> </a> (整站维护选项决定是否拥有整站初级管理允许)
																		</div>
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">
																					<td width="2%">
																						<strong>ID</strong>
																					</td>


																					<td width="25%">
																						<strong>站点名称</strong>
																					</td>
																					<td width="7%">
																						<input type="checkbox" id="site-manager-checkAll" onclick="javascript:accreditAllSite('site-manager',this);">
																						<strong>整站维护</strong>
																					</td>
																					
																					<td width="7%">
																						<input type="checkbox" id="site-node-add-checkAll" onclick="javascript:accreditAllSite('add',this);">
																						<strong>节点添加</strong>
																					</td>
																					<td width="7%">
																						<input type="checkbox" id="site-node-edit-checkAll" onclick="javascript:accreditAllSite('edit',this);">
																						<strong>节点修改</strong>
																					</td>

																					<td width="7%">
																						<input type="checkbox" id="site-node-delete-checkAll" onclick="javascript:accreditAllSite('delete',this);">
																						<strong>删除节点</strong>
																					</td>


																				</tr>

																				<cms:SystemOrgSite orgId="${Role.orgId}">

																					<tr>
																						<td>
																							<input type="checkbox" id="${OrgSite.siteId}-checkSiteAll" name="checkSiteAll" onclick="javascript:">
																						</td>

																						<td>
																							${OrgSite.siteName}
																						</td>
																						<td>
																							<input type="checkbox" id="site-manager-${OrgSite.siteId}" name="site-manager" value="${OrgSite.siteId}" onclick="javascript:checkStateSite();">
																						</td>
																						
																						<td>
																							<input type="checkbox">
																						</td>
																						<td>
																							<input type="checkbox">
																						</td>
																						<td>
																							<input type="checkbox">
																						</td>
																					</tr>

																				</cms:SystemOrgSite>
																			</table>
																		</div>

																	</td>
																</tr>

															</table>


														</td>
													</tr>

													<tr>
														<td height="10">
															&nbsp;
														</td>
													</tr>
												</table>
												<div style="height:6px;"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<td class="input-title" width="39%"></td>
															<td class="td-input">
																<a href="javascript:submitRoleAuthForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																<a href="javascript:close();" class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
															</td>
														</tr>
													</table>
												</div>
											</li>
										</ul>
									</div>



									--%>
									<!-- 第三部分：栏目-->
									<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			站点:
																			<select id="currentSiteClass" onchange="javascript:changeSite(this.value,2);" class="form-select">
																				<option value="-1">
																					----- 请选择目标站点 -----
																				</option>
																				<cms:SystemOrgSite orgId="${Role.orgId}">
																					<option value="${OrgSite.siteId}">
																						${OrgSite.siteName}
																					</option>
																				</cms:SystemOrgSite>
																			</select>
																			<script>
																	
																		 initSelect('currentSiteClass','${param.siteId}');
																				
																	</script>
																			&nbsp;&nbsp;
																		</div>

																		<div>
																		</div>
																		(可在站群管理中选择需要整站维护的站点)
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table id="showlistclass" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">
																					<td width="1%">
																						<strong>ID</strong>
																					</td>

																					<td width="25%">
																						<strong>栏目名称</strong>
																					</td>
																					<td width="5%">
																						<input type="checkbox" id="manage-accredit-class-checkAll" onclick="javascript:selectAll('manage-accredit-class',this);" />
																						<strong>栏目管理</strong>
																					</td>

																					<td width="5%">
																						<input type="checkbox" id="maintain-content-accredit-checkAll" onclick="javascript:selectAll('maintain-content-accredit',this);" />
																						<strong>内容维护</strong>
																					</td>



																				</tr>


																				<cms:SystemOrgClass siteId="${param.siteId}" orgId="${Role.orgId}" secType="1,2">

																					<tr>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-checkManageClassAll" name="checkManageClassAll" onclick="javascript:accreditManageClassAll('${Class.linearOrderFlag}',this);" />
																						</td>

																						<td>
																							${Class.layerUIClassName}
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-manage-accredit-class" name="manage-accredit-class" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:checkSelectAll('manage-accredit-class','manage-accredit-class-checkAll');" />
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-maintain-content-accredit" name="maintain-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:checkSelectAll('maintain-content-accredit','maintain-content-accredit-checkAll');" />
																						</td>

																					</tr>

																				</cms:SystemOrgClass>
																				<cms:Empty flag="Class">
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


														</td>
													</tr>

													<tr>
														<td height="10">
															&nbsp;
														</td>
													</tr>
												</table>
												<div style="height:40px;"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a href="javascript:submitRoleAuthForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>
											</li>
										</ul>

									</div>


									<!-- 第四部分： 专题权限 -->
									<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;">
																		<div class="fl">
																			站点:
																			<select id="currentSiteSpec" onchange="javascript:changeSite(this.value,3);" class="form-select">
																				<option value="-1">
																					----- 请选择目标站点 -----
																				</option>
																				<cms:SystemOrgSite orgId="${Role.orgId}">
																					<option value="${OrgSite.siteId}">
																						${OrgSite.siteName}
																					</option>
																				</cms:SystemOrgSite>
																			</select>
																			<script>
																		
																		 	initSelect('currentSiteSpec','${param.siteId}');
																					
																			</script>
																			&nbsp;&nbsp;
																		</div>

																		<div>
																		</div>
																		(可在站群管理中选择需要整站维护的站点)
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table id="showlistspec" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">
																					<td width="1%">
																						<strong>ID</strong>
																					</td>

																					<td width="25%">
																						<strong>专题分类名</strong>
																					</td>
																					<%--<td width="6%">
																						<input type="checkbox" id="manage-spec-accredit-checkAll" onclick="javascript:selectAll('manage-spec-accredit',this);" />
																						<strong>专题配置</strong>
																					</td>

																					--%><td width="9%">
																						<input type="checkbox" id="maintain-spec-accredit-checkAll" onclick="javascript:selectAll('maintain-spec-accredit',this);" />
																						<strong>专题管理与维护</strong>
																					</td>

																				</tr>

																				<cms:SystemOrgClass siteId="${param.siteId}" orgId="${Role.orgId}" secType="3" specMode="true">

																					<tr>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-checkManageSpecAll" name="checkManageSpecAll" onclick="javascript:accreditManageSpecAll('${Class.linearOrderFlag}',this);" />
																						</td>

																						<td>
																							${Class.layerUIClassName}
																						</td>
																						<%--<td>
																							<input type="checkbox" id="${Class.classId}-manage-spec-accredit" name="manage-spec-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:checkSelectAll('manage-spec-accredit','manage-spec-accredit-checkAll');" />
																						</td>
																						--%><td>
																							<input type="checkbox" id="${Class.classId}-maintain-spec-accredit" name="maintain-spec-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:checkSelectAll('maintain-spec-accredit','maintain-spec-accredit-checkAll');" />
																						</td>



																					</tr>


																				</cms:SystemOrgClass>
																				<cms:Empty flag="Class">
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


														</td>
													</tr>


												</table>
												<div style="height:40px;"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a href="javascript:submitRoleAuthForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>
											</li>
										</ul>

									</div>

									<!-- 第五部分： 推荐位权限 -->
									<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">

										<ul>
											<li>
												<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table  class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			站点:
																			<select id="currentSiteCommend" onchange="javascript:changeSite(this.value,2);" class="form-select">
																				<option value="-1">
																					----- 请选择目标站点 -----
																				</option>
																				<cms:SystemOrgSite orgId="${Role.orgId}">
																					<option value="${OrgSite.siteId}">
																						${OrgSite.siteName}
																					</option>
																				</cms:SystemOrgSite>
																			</select>
																			<script>
																		
																		 initSelect('currentSiteCommend','${param.siteId}');
																				
																	</script>
																			&nbsp;&nbsp;
																		</div>

																		<div>
																		</div>
																		(可在站群管理中选择需要整站维护的站点)
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table id="showlistcommend" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">


																					<td width="20%">
																						<strong>推荐位名称</strong>
																					</td>

																					<td width="12%">
																						<strong>所属栏目</strong>
																					</td>

																					<td width="8%">
																						<input type="checkbox" id="manage-commend-accredit-checkAll" onclick="javascript:selectAll('manage-commend-accredit',this);" />
																						<strong>管理与维护</strong>
																					</td>

																				</tr>


																				<cms:SystemOrgCommend siteId="${param.siteId}" orgId="${Role.orgId}" secType="4">

																					<tr>


																						<td>
																							${CommendType.commendName}
																						</td>

																						<td>
																							<cms:if test="${CommendType.classId != -9999}">
																								<cms:SysClass id="${CommendType.classId}">
																									${Class.className}
																								</cms:SysClass>
																							</cms:if>
																							<cms:else>
																						全站通用
																					</cms:else>
																						</td>

																						<td>
																							<input type="checkbox" id="${CommendType.commendTypeId}-manage-commend-accredit" name="manage-commend-accredit" value="${CommendType.commendTypeId}-${CommendType.commendTypeId}" onclick="javascript:checkSelectAll('manage-commend-accredit','manage-commend-accredit-checkAll');" />
																						</td>

																					</tr>

																				</cms:SystemOrgCommend>
																				<cms:Empty flag="CommendType">
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


														</td>
													</tr>

													<tr>
														<td height="10">
															&nbsp;
														</td>
													</tr>
												</table>
												<div style="height:20px"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a href="javascript:submitRoleAuthForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>

											</li>
										</ul>

									</div>
									<!--推荐位结束 -->
									
									<!-- 第五部分： 留言本权限 -->
									<div id="g3_two_5" class="auntion_Room_C_imglist" style="display:none;">

										<ul>
											<li>
												<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table  class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			站点:
																			<select id="currentSiteGuestbook" onchange="javascript:changeSite(this.value,2);" class="form-select">
																				<option value="-1">
																					----- 请选择目标站点 -----
																				</option>
																				<cms:SystemOrgSite orgId="${Role.orgId}">
																					<option value="${OrgSite.siteId}">
																						${OrgSite.siteName}
																					</option>
																				</cms:SystemOrgSite>
																			</select>
																			<script>
																		
																		 initSelect('currentSiteGuestbook','${param.siteId}');
																				
																	</script>
																			&nbsp;&nbsp;
																		</div>

																		<div>
																		</div>
																		(可在站群管理中选择需要整站维护的站点)
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table id="showlistguestbook" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">


																					<td width="20%">
																						<strong>留言本名称</strong>
																					</td>

																					 

																					<td width="8%">
																						<input type="checkbox" id="manage-guestbook-accredit-checkAll" onclick="javascript:selectAll('manage-guestbook-accredit',this);" />
																						<strong>管理与维护</strong>
																					</td>

																				</tr>


																				<cms:SystemOrgGbCfg siteId="${param.siteId}" orgId="${Role.orgId}" secType="5">

																					<tr>


																						<td>
																							${GbCfg.cfgName}
																						</td>

																						 

																						<td>
																							<input type="checkbox" id="${GbCfg.configId}-manage-guestbook-accredit" name="manage-guestbook-accredit" value="${GbCfg.configId}-${GbCfg.configId}" onclick="javascript:checkSelectAll('manage-guestbook-accredit','manage-guestbook-accredit-checkAll');" />
																						</td>

																					</tr>

																				</cms:SystemOrgGbCfg>
																				<cms:Empty flag="GbCfg">
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


														</td>
													</tr>

													<tr>
														<td height="10">
															&nbsp;
														</td>
													</tr>
												</table>
												<div style="height:20px"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a href="javascript:submitRoleAuthForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>

											</li>
										</ul>

									</div>
									<!--留言本结束 -->
									
									
									<!-- 第6部分： 表单权限 -->
									 
									<!--表单结束 -->

								</div>

								<!-- hidden -->
								<input type="hidden" id="roleId" name="roleId" value="${Role.roleId}"/>
								<input type="hidden" id="targetSiteId" name="targetSiteId" value="${param.siteId}"/>
								<input type="hidden" id="tab" name="tab" value="${param.tab}"/>
								<cms:Token mode="html"/>
							</form>
						</td>
					</tr>
				</table>

				<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

//初始化系统菜单功能 开始
var checkedIdMap = new HashMapJs();
var haveAllResIdArray = new Array();
var allResIdArray = new Array();




  
//当前角色所拥有的资源
<cms:ResourceList roleId="${Role.roleId}">
   <cms:Resource>
   	haveAllResIdArray.push("${Res.linearOrderFlag}-checkRes-${Res.secResId}");
   </cms:Resource>
</cms:ResourceList>

//当前角色所有可以选择的所属机构资源
<cms:ResourceList orgId="${Role.orgId}">
   <cms:Resource>
   	allResIdArray.push("${Res.linearOrderFlag}-checkRes-${Res.secResId}");
   </cms:Resource>
</cms:ResourceList>

var targetRes;

//加载已拥有粗粒度资源:在所有已经被选择的资源里寻找
for(var i =0; i < haveAllResIdArray.length; i++)
{
   targetRes = document.getElementById(haveAllResIdArray[i]);
   //若id存在
   if(targetRes != null)
   {
   	targetRes.checked=true;
   	//regRes(targetRes);
   }
}

var selectAllFlag = false;

//注册系统资源checkBox方法,结合树型结构
function regRes(resBox)
{

  if(resBox==="checkAll" && selectAllFlag==false)
  {
      selectAllBox('checkResource');
      selectAllFlag = true;
  }
  
  else if(resBox==="checkAll" &&  selectAllFlag==true)
  {
      unSelectAllBox('checkResource');
      selectAllFlag = false;
  }
  
  else
  {

     //单一资源注册
     //alert(resBox.checked);
     

      var currentIdFlag = resBox.id.split('-')[0];
      var testResId = "";
  	  for(var i =0; i < allResIdArray.length; i++)
		{
		   targetRes = document.getElementById(allResIdArray[i]);
		   //若id存在,将此ID注册在页面
		   if(targetRes != null)
		   {
		      testResId = allResIdArray[i]+"";
		     
		      //alert(testResId + " : " + currentIdFlag);
		   	  if(testResId.startWith(currentIdFlag))
		   	  {
		   	  	 if(resBox.checked == true)
		   	  	 {
		   	  		targetRes.checked=true;
		   	  	 }
		   	     else
		   	     {
		   	        targetRes.checked=false;
		   	     }
		   	  }

		    }
    
		}
	
		
		if(resBox.checked == true)
		{
			//所有父节点check
			var linearCount = currentIdFlag.length / 3;
			var linear;
			var pos=0;
		    for(var j=0; j<linearCount;j++)
			{
			    linear = currentIdFlag.substring(pos,pos + ((j+1) * 3));

			    for(var k =0; k < allResIdArray.length; k++)
				{
				    testResId = allResIdArray[k]+"";
				    targetRes = document.getElementById(allResIdArray[k]);
				    
				    if(targetRes != null)
			        {
					    if(testResId.startWith(linear+"-"))
						{
						   	if(resBox.checked == true)
						   	{
						   	    targetRes.checked=true;
						   	}
						   	else
						   	{
						   	    targetRes.checked=false;
						   	}
						 }
					}
			    }
			}
	   } 
   }
  
  
}
//系统粗粒度资源初试化  --end--


//内容管理细粒度权限初试化  --start--


var maintainCheckBox = document.getElementsByName('maintain-content-accredit');


//允许出现的选择
var orgRoleContentAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="${param.siteId}" secType="1">
<cms:AccInfo>
orgRoleContentAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

////当前的选择
var allRoleContentAccClassInfoMap = new HashMapJs();
<cms:AccInfoList siteId="${param.siteId}" roleId="${Role.roleId}" secType="1">
<cms:AccInfo>

allRoleContentAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');

</cms:AccInfo>
</cms:AccInfoList>

//alert( allRoleContentAccClassInfoMap.allValueToString('*'));


	var box;
	
	for(i=0; i<maintainCheckBox.length;i++)
	{
		box = maintainCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
	

function changeCheckBoxStateForAcc(target)
{
	var checkFlag = allRoleContentAccClassInfoMap.get(target.id+'');
	var parFlag = orgRoleContentAccClassInfoMap.get(target.id+'');
	var id = target.id+'';
	if(target != null)
	{
		if('true' == checkFlag)
		{			
			target.checked = true;	
		}
		
		//alert(parFlag);
		if('true' != parFlag)
		{
		    target.style.display='none';
		}
		//else{
		
		//}
		
	}
}
	

var checkAddClassIds = document.getElementsByName('checkAddClass');
var length = checkAddClassIds.length;
for(var i=0; i<length; i++ )
{
	if('true' == addClassIdTempMap.get(checkAddClassIds[i].value))
	{
		checkAddClassIds[i].checked=true;
	}
}


//内容管理细粒度权限初试化  --end--


//细粒度资源逻辑



var checkedIdMap = new HashMapJs();
var checkedManagerClassIdMap = new HashMapJs();
var checkedAddClassIdMap = new HashMapJs();
//fun:注册细粒度内容录入资源
function regAddContentClass(classBox)
{
  if(classBox.id==='checkAllAddClass')
  {
      if(classBox.checked==true) 
      {  
         selectAll('checkAddClass');
      }
      else if(classBox.checked==false)
      {
         unSelectAll('checkAddClass');
      }
         
      return false;
  }
 
  if(classBox.checked)
  {
      //checkedAddClassIdMap.put(classBox.value,classBox.value);
  }
  else
  {
     // checkedAddClassIdMap.remove(classBox.value,classBox.value);
      document.getElementById('checkAllAddClass').checked=false;
  }
  
  
  //alert(checkedAddClassIdMap.allValueToString('*'));
   return false;  
}

//fun:注册细粒度内容审核资源
function regAuditContentClass(classBox)
{
  if(classBox.id==='checkAllAuditClass')
  {
      if(classBox.checked==true) 
      {  
         selectAll('checkAuditClass',checkedAuditClassIdMap,'checkAllAuditClass');
      }
      else if(classBox.checked==false)
      {
         unSelectAll('checkAuditClass',checkedAuditClassIdMap);
      }
      
       return false;
  }
  
   	  if(classBox.checked)
	  {
	      checkedAuditClassIdMap.put(classBox.value,classBox.value);
	  }
	  else
	  {
	      checkedAuditClassIdMap.remove(classBox.value,classBox.value);
	      document.getElementById('checkAllAuditClass').checked=false;
	  }
  
   return false;
}


function accreditAll(flag,allAction)
{
	var targetCheckBox = document.getElementsByName(flag+'-content-accredit');
	
	var box;
	for(var i=0; i<targetCheckBox.length;i++)
	{
		box = targetCheckBox[i];
		if(box != null)
		{
		    if(allAction.checked)
		    {
		    	box.checked = true;	
		    }
		    else
		    {
		    	box.checked = false;	
		    }
			
		}
	
	}

}

function accreditClassAll(linearFalg,classCheckEvent)
{
	 
	var classCheckAllBox = document.getElementsByName('checkClassAll');
	
	var box;
	for(var i=0; i<addCheckBox.length;i++)
	{
		box = addCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<editCheckBox.length;i++)
	{
		box = editCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<maintainCheckBox.length;i++)
	{
		box = maintainCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<deleteCheckBox.length;i++)
	{
		box = deleteCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<commendCheckBox.length;i++)
	{
		box = commendCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<publishCheckBox.length;i++)
	{
		box = publishCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<classCheckAllBox.length;i++)
	{
		box = classCheckAllBox[i];
		if(box.id.startWith(linearFalg+''))
		if(classCheckEvent.checked)
		{
		    box.checked = true;	
		}
		else
		{
		    box.checked = false;	
		}		
	}
}




////////////细粒度内容管理结束//////////



////////////细粒度栏目管理开始/////////



var manageClassCheckBox = document.getElementsByName('manage-accredit-class');




var orgRoleClassAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="${param.siteId}" secType="2">
<cms:AccInfo>
orgRoleClassAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

var allCheckedOrgRoleClassAccClassInfoMap = new HashMapJs();
<cms:AccInfoList siteId="${param.siteId}" roleId="${Role.roleId}" secType="2">
<cms:AccInfo>
allCheckedOrgRoleClassAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

//alert( allCheckedOrgRoleClassAccClassInfoMap.allKeyToString('*'));
//栏目init
	var box;
	for(var i=0; i<manageClassCheckBox.length;i++)
	{
		box = manageClassCheckBox[i];
		changeClassCheckBoxStateForAcc(box);
	}
	
	
	
	
	
//栏目内容init
var manageContentCheckBox = document.getElementsByName('maintain-content-accredit');

orgRoleClassAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="${param.siteId}" secType="1">
<cms:AccInfo>
orgRoleClassAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

allCheckedOrgRoleClassAccClassInfoMap = new HashMapJs();
<cms:AccInfoList siteId="${param.siteId}" roleId="${Role.roleId}" secType="1">
<cms:AccInfo>
allCheckedOrgRoleClassAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>
	
	for(var i=0; i<manageContentCheckBox.length;i++)
	{
		box = manageContentCheckBox[i];
		changeClassCheckBoxStateForAcc(box);
	}
	
	
	

function accreditManageClassAll(linearFalg,classCheckEvent)
{
	var classCheckAllBox = document.getElementsByName('checkManageClassAll');
	
	var box;
	
	for(var i=0; i<manageClassCheckBox.length;i++)
	{
		box = manageClassCheckBox[i];
	
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(var i=0; i<manageContentCheckBox.length;i++)
	{
		box = manageContentCheckBox[i];
	
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	
	
	
	for(i=0; i<classCheckAllBox.length;i++)
	{
		box = classCheckAllBox[i];
		if(box.id.startWith(linearFalg+''))
		{
			if(classCheckEvent.checked)
			{
			    box.checked = true;	
			}
			else
			{
			    box.checked = false;	
			}		
		}
	}
}
	
	
////////////细粒度专题管理开始/////////



var manageSpecCheckBox = document.getElementsByName('manage-spec-accredit');
var maintainSpecCheckBox = document.getElementsByName('maintain-spec-accredit');


var orgRoleSpecAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="${param.siteId}" secType="3">
<cms:AccInfo>
orgRoleSpecAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

var allCheckedOrgRoleSpecAccClassInfoMap = new HashMapJs();
<cms:AccInfoList siteId="${param.siteId}" roleId="${Role.roleId}"  secType="3">
<cms:AccInfo>

allCheckedOrgRoleSpecAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

//alert( parentOrgRoleClassAccClassInfoMap.allKeyToString('*'));
//专题init
	var box;
	for(var i=0; i<manageSpecCheckBox.length;i++)
	{
		box = manageSpecCheckBox[i];
		changeSpecCheckBoxStateForAcc(box);
	}
	
	for(var i=0; i<maintainSpecCheckBox.length;i++)
	{
		box = maintainSpecCheckBox[i];
		changeSpecCheckBoxStateForAcc(box);
	}


function accreditManageSpecAll(linearFalg,classCheckEvent)
{
	var classCheckAllBox = document.getElementsByName('checkManageSpecAll');
	
	var box;
	
	for(var i=0; i<manageSpecCheckBox.length;i++)
	{
		box = manageSpecCheckBox[i];
	
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(var i=0; i<maintainSpecCheckBox.length;i++)
	{
		box = maintainSpecCheckBox[i];
	
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	
	
	
	for(i=0; i<classCheckAllBox.length;i++)
	{
		box = classCheckAllBox[i];
		if(box.id.startWith(linearFalg+''))
		if(classCheckEvent.checked)
		{
		    box.checked = true;	
		}
		else
		{
		    box.checked = false;	
		}		
	}
}


////////////细粒度推荐位管理开始/////////

var manageCommendCheckBox = document.getElementsByName('manage-commend-accredit');


var orgRoleCommAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="${param.siteId}" secType="4" classMode="commend">
<cms:AccInfo>
orgRoleCommAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

var allCheckedOrgRoleCommAccClassInfoMap = new HashMapJs();
<cms:AccInfoList siteId="${param.siteId}" roleId="${Role.roleId}" secType="4" classMode="commend">
<cms:AccInfo>
//alert('${Acc.accId}-${Acc.sysFlag}');
allCheckedOrgRoleCommAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

//alert( parentOrgRoleClassAccClassInfoMap.allKeyToString('*'));

//init
var box;
for(var i=0; i<manageCommendCheckBox.length;i++)
{
		box = manageCommendCheckBox[i];
		changeCommCheckBoxStateForAcc(box);
}
	



////////////细粒度留言本管理开始/////////

var manageGbCheckBox = document.getElementsByName('manage-guestbook-accredit');


var orgRoleGbAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="${param.siteId}" secType="5" classMode="guestbook">
<cms:AccInfo>
orgRoleGbAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

var allCheckedOrgRoleGbAccClassInfoMap = new HashMapJs();
<cms:AccInfoList siteId="${param.siteId}" roleId="${Role.roleId}" secType="5" classMode="guestbook">
<cms:AccInfo>
//alert('${Acc.accId}-${Acc.sysFlag}');
allCheckedOrgRoleGbAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

//alert( parentOrgRoleClassAccClassInfoMap.allKeyToString('*'));

//init
var box;
for(var i=0; i<manageGbCheckBox.length;i++)
{
		box = manageGbCheckBox[i];
		changeGbCheckBoxStateForAcc(box);
}
	
	
	
	
	
	
	
	
	

//将没有授权的节点变为不可使用状态
function changeClassCheckBoxStateForAcc(target)
{
	var checkFlag = allCheckedOrgRoleClassAccClassInfoMap.get(target.id+'');
	var parFlag = orgRoleClassAccClassInfoMap.get(target.id+'');
	var id = target.id+'';
	if(target != null)
	{
		if('true' == checkFlag)
		{			
			target.checked = true;	
		}
		
		//alert(parFlag);
		if('true' != parFlag)
		{
		  target.style.display='none';
	    }
		//else{
		
		//}
		
	}
}

//spec
function changeSpecCheckBoxStateForAcc(target)
{
	var checkFlag = allCheckedOrgRoleSpecAccClassInfoMap.get(target.id+'');
	var parFlag = orgRoleSpecAccClassInfoMap.get(target.id+'');
	var id = target.id+'';
	if(target != null)
	{
		if('true' == checkFlag)
		{			
			target.checked = true;	
		}
		
		//alert(parFlag);
		if('true' != parFlag)
		{
		  target.style.display='none';
	    }
		//else{
		
		//}
		
	}
}

//comm
function changeCommCheckBoxStateForAcc(target)
{
	var checkFlag = allCheckedOrgRoleCommAccClassInfoMap.get(target.id+'');
	var parFlag = orgRoleCommAccClassInfoMap.get(target.id+'');
	var id = target.id+'';
	if(target != null)
	{
		if('true' == checkFlag)
		{			
			target.checked = true;	
		}
		
		//alert(parFlag);
		if('true' != parFlag)
		{
		  target.style.display='none';
	    }
		//else{
		
		//}
		
	}
}

//guestbook
function changeGbCheckBoxStateForAcc(target)
{
	var checkFlag = allCheckedOrgRoleGbAccClassInfoMap.get(target.id+'');
	var parFlag = orgRoleGbAccClassInfoMap.get(target.id+'');
	var id = target.id+'';
	if(target != null)
	{
		if('true' == checkFlag)
		{			
			target.checked = true;	
		}
		
		//alert(parFlag);
		if('true' != parFlag)
		{
		  target.style.display='none';
	    }
		//else{
		
		//}
		
	}
}


//检查初始花后的checkbox状态
checkStateClass();


function accreditAllClass(flag,actBox)
{
	var boxs = document.getElementsByName(flag);
	
	var checked = actBox.checked;
	var box;
	for(var i = 0; i < boxs.length; i++)
	{
		box = boxs[i];
		
		if(checked)
		{
			box.checked=true;
		}
		else
		{
			box.checked=false;
		}
	}
}







function checkStateClass(flag)
{
	var boxs = document.getElementsByName(flag);
	
	var box;
	var checkStateIn = false;
	var notCheckStateIn = false;
	for(var i = 0; i < boxs.length; i++)
	{
		box = boxs[i];
		
		if(box.checked)
		{
			checkStateIn = true;
		}
		else
		{
			notCheckStateIn = true;
		}
	}
	
	if(checkStateIn && notCheckStateIn)
	{
		document.getElementById(flag+'-checkAll').checked = false;
	}
	else if(checkStateIn)
	{
		document.getElementById(flag+'-checkAll').checked = true;
	}
	else if(notCheckStateIn)
	{
		document.getElementById(flag+'-checkAll').checked = false;
	}
	
	
}











////////////细粒度栏目管理结束/////////



////////////站点管理权限开始////////////

var orgSiteInfoMap = new HashMapJs();
<cms:SystemOrgSite roleId="${Role.roleId}" roleMode="true">
//角色现不需要站点授权
//orgSiteInfoMap.put('site-manager-${OrgSite.siteId}','true');
</cms:SystemOrgSite>

//初始化已选站点管理权
//var siteManagerBox = document.getElementsByName('site-manager');
//var inFlag;
//for(var i=0; i<siteManagerBox.length; i++)
{
	//inFlag = orgSiteInfoMap.get(siteManagerBox[i].id+'');
	//alert(inFlag);
	//if('true'==inFlag)
	{
		//siteManagerBox[i].checked=true;
	}
}

//检查初始化后的checkbox状态
//checkStateSite();	

function accreditAllSite(flag,actBox)
{
	var boxs = document.getElementsByName(flag);
	
	var checked = actBox.checked;
	var box;
	for(var i = 0; i < boxs.length; i++)
	{
		box = boxs[i];
		
		if(checked)
		{
			box.checked=true;
		}
		else
		{
			box.checked=false;
		}
	}
}

function checkStateSite()
{
	var boxs = document.getElementsByName('site-manager');
	
	var box;
	var checkStateIn = false;
	var notCheckStateIn = false;
	for(var i = 0; i < boxs.length; i++)
	{
		box = boxs[i];
		
		if(box.checked)
		{
			checkStateIn = true;
		}
		else
		{
			notCheckStateIn = true;
		}
	}
	
	if(checkStateIn && notCheckStateIn)
	{
		document.getElementById('site-manager-checkAll').checked = false;
	}
	else if(checkStateIn)
	{
		document.getElementById('site-manager-checkAll').checked = true;
	}
	else if(notCheckStateIn)
	{
		document.getElementById('site-manager-checkAll').checked = false;
	}
	
	
}

//公用方法
function changeCheckBoxState(target,flag,event)
{
	if(target != null)
	{
		if('none' == target.style.display)
	 	{
	 		return;
	 	}
	
		if(target.value.startWith(flag+''))
		{
			if(event.checked)
			{
			    target.checked = true;	
			}
			else
			{
			    target.checked = false;	
			}
		}	
	}
}

function accreditSingle(flag,linearFlag,eventBox)
{
		//var allBox = document.getElementsByName(flag+'-accredit-check');
		//var allClassBox = document.getElementsByName('checkClassAll');
		
		//var box;
		//for(var i=0; i<allBox.length;i++)
		//{
		//	box = allBox[i];
		//	changeCheckBoxState(box,linearFlag,eventBox);
		//}
		
		//for(i=0; i<allClassBox.length;i++)
		//{
		//	box = allClassBox[i];
			//changeCheckBoxState(box,linearFlag,eventBox);
		//}
		
}

//此处初始化tab
var tab = '${param.tab}';

if('' != tab)
{
	setTab2('two',tab,4);
}

function setTab(mod,pos,count)
{
	document.getElementById('tab').value = pos;
	setTab2('two',pos,count);
}



function submitRoleAuthForm()
{
	W.$.dialog.tips('正在应用角色授权',4000,'loading.gif'); 
	
	var roleAuthForm = document.getElementById('roleAuthForm');
	roleAuthForm.action = '../../security/matainSystemRoleAuth.do';
	roleAuthForm.submit();
}

function changeSite(siteId,tab)
{
	window.location.href = 'MaintainSystemRole.jsp?roleId=${Role.roleId}&siteId='+siteId+'&tab='+tab	
}

function close()
{
	api.close(); 
	W.window.location.reload();

}

</script>
</cms:SystemRole>
</cms:CurrentSite>
