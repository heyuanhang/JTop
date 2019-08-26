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
	 	var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );
         	api.get('orud').window.location.reload();         
       		//W.window.location.reload();       
         }


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

					<div style="height:5px"></div>
					<form id="userRoleForm" name="userRoleForm" method="post">
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">


							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="1%" height="30">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedUserId',this);" type="checkbox" />
												</td>
												

												<td width="8%">
													<strong>用户名称</strong>
												</td>

												<td width="8%">
													<strong>真实名称</strong>
												</td>

												<td width="8%">
													<strong>所属机构</strong>
												</td>

											</tr>

											<cms:SystemUserList excludeUserRoleId="${param.roleId}">
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
										<input type="hidden" id="roleId" name="roleId" value="${param.roleId}" />

										<cms:Token mode="html"/>
										
										</form>
										<div style="height:15px"></div>
					
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellspacing="0" cellpadding="0" >
												<tr class="btnbg100">
													<div style="float:right">
														<a href="javascript:addNewUserForRole();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
														<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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
  
function close()
{

	api.close();
	api.get('orud').window.location.reload();
}
   
function addNewUserForRole()
{
	var userForm = document.getElementById('userRoleForm');
	userForm.action = '<cms:BasePath/>security/addUserFromRole.do';
	userForm.submit();

}
  
</script>
