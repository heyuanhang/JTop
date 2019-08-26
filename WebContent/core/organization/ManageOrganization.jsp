<%@ page contentType="text/html; charset=utf-8" session="false"%>

<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script>
			<cms:CurrentSite>
			var currSiteId = '${CurrSite.siteId}';
			</cms:CurrentSite>
			
			var orgSiteArray = new Array();
			
			var siteArray;
			
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
							<a href="#"></a> &raquo;
							<a href="#">部门机构管理</a>
						</td>
						<td align="right"></td>
					</tr>
				</table>
			</div>
			<div style="height:25px;"></div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openCreateOrgDialog();" class="btnwithico"> <img src="../../core/style/icons/balance--plus.png" alt="" /><b>添加部门&nbsp;</b> </a>
										<a href="javascript:deleteOrg();" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
										
										<span>(注意：删除部门机构同时将删除掉机构下属角色以及相关授权信息)</span>
									</div>
									<div class="fr">
									</div>
								</td>
							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">

										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="2%">
													<strong>ID</strong>
												</td>
												<td width="1%" height="30">
													
												</td>
												<td width="18%">
													<strong>部门名称</strong>
												</td>


												<td width="7%">
													<strong>标识代码</strong>
												</td>

												<td width="7%">
													<strong>部门主管</strong>
												</td>


												<td width="12%">
													<center><strong>维护</strong></center>
												</td>
											</tr>

											<cms:SystemOrg orgId="${Auth.orgIdentity}" childMode="true">
												<tr>
													<td>
														${Org.orgId}
													</td>

													<td>
														<input type="radio" name="checkedId" value="${Org.orgId}" onclick="javascript:;" />
													</td>

													<td>
														&nbsp;${Org.uiName}
													</td>

													<td>
														${Org.orgFlag}
													</td>
													<td>
														<cms:SystemUser id="${Org.orgBossId}">
															${SysUser.userName}
														</cms:SystemUser>
													</td>

													<td>
														<div>
															<center>
																
																<a href="javascript:openOrgAuthRangeDialog('${Org.orgId}','<cms:JsEncode str='${Org.orgName}'/>')"><img src="../../core/style/icons/balance.png" width="16" height="16" />&nbsp;职能</a>&nbsp;&nbsp;&nbsp;
	
																<a href="javascript:openShowOrgUserDialog('${Org.linearOrderFlag}','${Org.orgId}','${Org.orgBossId}')"><img src="../../core/style/icons/user-business-gray-boss.png" width="16" height="16" />&nbsp;主管</a>&nbsp;&nbsp;&nbsp;
																
																<a href="javascript:openEditOrgDialog('${Org.orgId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;
																
													
															</center>
														</div>
													</td>

												</tr>
											</cms:SystemOrg>

											<cms:Empty flag="Org">
												<tr>
													<td class="tdbgyew" colspan="7">
														<center>
															当前没有数据!
														</center>
													</td>
												</tr>
											</cms:Empty>
											<tr>
												<td colspan="8" class="PageBar" align="left">
													<div class="fr">

													</div>
													<div class="fl"></div>
												</td>
											</tr>

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
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


 

function openOrgAuthRangeDialog(orgId,orgName)
{
	if(orgId == '1')
	{
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: orgName+'拥有所有职能权限,无需设置!',
                    
                   
    	cancel: true 
    	});
    	
    	return;
	
	}
	

					
	
	$.dialog({ 
	    id : 'ooard',
    	title : '部门职能范围设置 - '+orgName,
    	width: '1050px', 
    	height: '705px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>organization/directMaintainOrgAuthRange.do?orgId='+orgId+'&targetSiteId='+currSiteId+'?uid='+Math.random()

	});
}

function openCreateOrgDialog()
{
	$.dialog({ 
	    id : 'ocod',
    	title : '添加部门机构',
    	width: '630px', 
    	height: '340px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/organization/CreateOrganization.jsp?uid='+Math.random()

	});
}

function openEditOrgDialog(id)
{
	$.dialog({ 
	    id : 'oeod',
    	title : '编辑部门机构',
    	width: '630px', 
    	height: '340px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/organization/EditOrganization.jsp?orgId='+id+'&uid='+Math.random()

	});
}



function deleteOrg(orgId)
{
	var cidCheck = document.getElementsByName('checkedId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value;
		}
	}
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要删除机构！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	orgId = ids;

	if(orgId == '1')
	{
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '不可删除根机构部门!',
                    
                   
    	cancel: true 
    	});
    	
    	return;
	
	}
	
	$.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选部门以及其下级部门吗？',
                    
                    ok: function () { 
                    
         $.dialog.tips('删除机构成功!...',2,'32X32/succ.png');
   		 window.location.href = '<cms:BasePath/>organization/deleteOrg.do?orgId='+orgId+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
    
    
}

function openShowOrgUserDialog(orgCode, orgId, currBossId)
{
	$.dialog({ 
	    id : 'osoud',
    	title : '当前机构所属人员',
    	width: '560px', 
    	height: '580px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/organization/ShowOrgUser.jsp?orgCode='+orgCode+'&orgId='+orgId+'&currBossId='+currBossId+'&uid='+Math.random()

	});
}





</script>
</cms:LoginUser>

