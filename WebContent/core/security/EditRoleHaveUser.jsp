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

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->


					<form id="userForm" name="userForm" method="post">
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 6px 6px;" class="">
									<div class="fl">
										<a href="javascript:openSelectUserDialog(${param.roleId})" class="btnwithico"><img src="../style/icons/user-share.png" width="16" height="16" /><b>选取管理员&nbsp;</b> </a>
										<a href="javascript:deleteCheckedUser();" class="btnwithico"><img src="../style/icons/user--minus.png" width="16" height="16" /><b>删除&nbsp;</b> </a>
										&nbsp;<span>(可选管理员为当前角色所属机构以及所有直接上级机构人员)</span>
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="1%" height="30">
													
												</td>
												


												<td width="8%">
													<strong>用户名称</strong>
												</td>

												<td width="8%">
													<strong>真实姓名</strong>
												</td>
												
												<td width="8%">
													<strong>所属机构</strong>
												</td>


											
												

											</tr>

											<cms:SystemUserList roleId="${param.roleId}">
												<cms:SystemUser>
													<tr>
														<td>
															<input class="inputCheckbox" name="checkedUserId" value="${SysUser.userId}" type="checkbox" onclick="javascript:" />
														</td>
														
														<td>
															&nbsp;${SysUser.userName}
														</td>
														<td>
															${SysUser.userTrueName}
														</td>
														<td>
														
														<cms:SystemOrg orgCode="${SysUser.relateOrgCode}">
															${Org.orgName}
														</cms:SystemOrg>
															
														</td>
														
													</tr>
												</cms:SystemUser>
											</cms:SystemUserList>
												<cms:Empty flag="SysUser">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
										</table>
										<!-- hidden -->
										<input type="hidden" id="roleId" name="roleId" value="${param.roleId}"/>
										
										<cms:Token mode="html"/>

										</form>
										<div style="height:15px"></div>
										<div class="breadnavTab"  >
										<table width="100%" border="0" cellspacing="0" cellpadding="0" >
											<tr class="btnbg100">
												<div style="float:right">			
													<a href="javascript:close();" class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>关闭&nbsp;</b> </a>
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
var api = frameElement.api, W = api.opener;

function openSelectUserDialog(roleId)
{
	
	W.$.dialog({ 
	    id : 'osud',
    	title :'选取可选管理员',
    	width: '540px', 
    	height: '540px', 
    	parent:api,
        lock:true,
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/security/ShowAllUser.jsp?uid='+Math.random()+"&roleId="+roleId
	
	  
	});
}

function close()
{
	api.close();
	W.window.location.reload();
}

  
function deleteCheckedUser()
{
	//var checkedIds = document.getElementsByName('checkedUserId');
	
	//for(var i = 0 ; i < checkedIds.length; i++)
	
	var userForm = document.getElementById('userForm');
	userForm.action = '<cms:Domain/>security/deleteUserFromRole.do';
	userForm.submit();
}
  
</script>
