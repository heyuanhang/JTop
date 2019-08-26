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
						<a href="#">系统配置</a> &raquo;
						<a href="#">系统权限</a> &raquo;
						<a href="#">权限资源管理</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="roleForm" name="roleForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
									筛选：<select id="mainRes" name="mainRes" onchange="javascript:filter(this)">
												<option value="000">
													--------------- 所有资源 ---------------
												</option>
												<cms:ResourceList mainMode="true" roleId="all">
												<cms:Resource>
													<option value="${Res.linearOrderFlag}">
														${Res.resourceNameBlank}
													</option>
												</cms:Resource>
												</cms:ResourceList>
										   </select>
										   &nbsp;
									</div>
									<div>
										<a href="javascript:openAddResourceDialog();" class="btnwithico" onclick=""><img src="../style/icons/puzzle--plus.png" width="16" height="16" /><b>增加资源&nbsp;</b> </a>
										<a href="javascript:showSortSecResPage();" class="btnwithico"><img src="../style/icons/sort-quantity.png" width="16" height="16" /><b>排序&nbsp;</b> </a>
												<div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%" height="30">
													<strong>ID</strong>
												</td>

												 
												<td width="14%">
													<strong>资源名称</strong>
												</td>

												<td width="2%">
													<strong>类型</strong>
												</td>

												

												<td width="3%">
													<strong>权限粒度</strong>
												</td>

												

												<td width="18%">
													<strong>受控URL资源</strong>
												</td>

												<td width="6%">
													<center><strong>维护</strong></center>
												</td>
											</tr>

											<cms:ResourceList protectType="100" roleId="all" linear="${param.linear}">
												<cms:Resource>
													<tr>

														<td>
															${Res.secResId} 
															<cms:if test="${status.first}"><input type="hidden" id="firstRes" value="${Res.secResId} "></cms:if>
														</td>
 
														<td>
															${Res.resourceNameInfo}
														</td>
														<td>
															${Res.resourceTypeInfo}
														</td>

													

														<td>
															${Res.protectTypeInfo}
														</td>
														
																												
														<td>
															${Res.target}
														</td>

														<td>
															<div>
															<center>
																<a href="javascript:openEditResourceDialog('${Res.secResId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<a href="javascript:deleteResource('${Res.secResId}')"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a>
															</center>
															</div>
														</td>
													</tr>
												</cms:Resource>
											</cms:ResourceList>
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

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('mainRes','${param.linear}');

function openAddResourceDialog()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '240px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有顶级机构主管允许增加权限资源！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	var firstRes = $('#firstRes').val();
	
	$.dialog({ 
	    id : 'oard',
    	title : '添加权限资源',
    	width: '660px', 
    	height: '540px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/security/AddSecurityResource.jsp?linear=${param.linear}&parent='+firstRes
	});
}


function openEditResourceDialog(resId)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '240px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有顶级机构主管允许编辑权限资源！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog({ 
	    id : 'oerd',
    	title : '编辑权限资源',
    	width: '660px', 
    	height: '540px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/security/EditSecurityResource.jsp?id='+resId
	});
}


function deleteResource(resId)
{    
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '240px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有顶级机构主管允许删除权限资源！', 
	       cancel: true 
	                    
		  });
		  return;
	}


    var dialog = $.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '当前资源以及子资源的相关信息将被物理删除,您确定要删除吗?',
                    
                    ok: function () { 
       
    window.location.href = '<cms:Domain/>security/deleteSecurityResource.do?id='+resId+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
}

function filter(obj)
{
	 window.location = 'ManageResource.jsp?linear='+obj.value;
}

function showSortSecResPage()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '240px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有顶级机构主管允许排序权限资源！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	var resLinear = document.getElementById('mainRes').value;
	
	

	$.dialog({ 
		id:'sssrp',
    	title :'权限资源排序',
    	width: '500px', 
    	height: '650px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
        close: function () 
        { 
        	window.location.reload();
        } ,
        
        content: 'url:<cms:BasePath/>core/security/ShowSortSecRes.jsp?parentResLinear='+resLinear
	});
}



</script>
</cms:LoginUser>
