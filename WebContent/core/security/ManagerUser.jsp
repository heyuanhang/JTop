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

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">系统管理</a> &raquo;
						<a href="#">用户管理</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<cms:LoginUser>
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
											机构:
											<select id="org" class="form-select" id="showMode" onchange="javascript:change(this);">
												<option value="">
													---------- 所有可见部门 ----------
												</option>

												<cms:SystemOrg orgId="${Auth.orgIdentity}" childMode="true">
													<option value="${Org.linearOrderFlag}">
														${Org.uiName}
													</option>
												</cms:SystemOrg>


											</select>
											&nbsp;
										</div>
										<div>
											<a href="javascript:addUser();" class="btnwithico" onclick=""><img src="../style/icons/user--plus.png" width="16" height="16" /><b>增加管理员&nbsp;</b> </a>
											<a href="javascript:changeStatus('close');" class="btnwithico" onclick=""><img src="../style/icons/light-bulb-off.png" width="16" height="16" /><b>停用&nbsp;</b> </a>
											<a href="javascript:changeStatus('open');" class="btnwithico" onclick=""><img src="../style/icons/light-bulb.png" width="16" height="16" /><b>启用&nbsp;</b> </a>
											<a href="javascript:deleteUser();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
											
										</div>
										<div class="fr">
										<span>管理员名:</span>
										<input id="searchKey"size="25" maxlength="60" class="form-input"  value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
										<input onclick="javascript:search();" value="查询" class="btn-1" class="form-input" type="button" style="vertical-align:top;" />

									</div>
									</td>
								</tr>

								<tr>
									<td id="uid_td25" style="padding: 2px 6px;">
										<div class="DataGrid">
											<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

												<tr class="datahead">

													<td width="1%">
														<strong>ID</strong>
													</td>

													<td width="1%">
														<input class="form-checkbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
													</td>
													<td width="4%">
														<strong>用户名称</strong>
													</td>

													<td width="4%">
														<strong>真实姓名</strong>
													</td>

													<%--<td width="7%">
													<strong>所属角色</strong>
												</td>

												--%>
													<td width="4%">
														<strong>所属机构</strong>
													</td>

													<td width="2%">
														<strong>启用状态</strong>
													</td>

													<td width="7%">
														<center>
															<strong>维护</strong>
														</center>
													</td>
												</tr>


												<cms:SystemUserList pn="${param.pn}" size="10" orgCode="${param.orgCode}" manage="true" queryName="${param.key}">
													<cms:SystemUser>
														<tr>
															<td>
																${SysUser.userId}
															</td>

															<td>
																<input class="form-checkbox" name="checkedId" value="${SysUser.userId}" type="checkbox" onclick="javascript:" />
															</td>

															<td>
																${SysUser.userName}
															</td>
															<td>
																${SysUser.userTrueName}
															</td>

															<%--<td>
																<div style="height:1px"></div>
																<textarea  style="width:225px; height:36px;" class="form-textarea">${SysUser.someRoleName}</textarea>
																<div style="height:1px"></div>
															</td>

															--%>
															<td>
																<cms:SystemOrg orgCode="${SysUser.relateOrgCode}">															
																${Org.orgName}
															</cms:SystemOrg>
															</td>

															<td>
																<cms:if test="${SysUser.useState == 1}">
																	<img src="../style/icon/tick.png" />
																</cms:if>
																<cms:else>
																	<img src="../style/icon/del.gif" />
																</cms:else>
															</td>

															<td>
																<div>
																	<center>
																		<a href="javascript:editUser('${SysUser.userId}','${SysUser.userName}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																		<a href="javascript:editUserHaveRole('${SysUser.userId}','${SysUser.userName}');"><img src="../../core/style/icons/users.png" width="16" height="16" />&nbsp;角色</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

						
																		<a href="javascript:changePassword('${SysUser.userId}','${SysUser.userName}');"><img src="../../core/style/icons/key-solid.png" width="16" height="16" />&nbsp;改密</a>

																	</center>
																</div>
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
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
															<div class="fl"></div>
														</td>
													</tr>
												</cms:PageInfo>

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

initSelect('org','${param.orgCode}');

 

function openRoleUserDialog(roleId,roleName)
{
	$.dialog({ 
	    id : 'orud',
    	title : '所属用户 - '+roleName,
    	width: '480px', 
    	height: '300px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/security/EditRoleHaveUser.jsp?roleId='+roleId+'&uid='+Math.random()
	});
}

function openMaintainSystemRoleDialog(roleId,roleName)
{
			
	$.dialog({ 
	    id : 'ooard',
    	title : '角色授权 - '+roleName,
    	width: '820px', 
    	height: '550px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/MaintainSystemRole.jsp?roleId='+roleId

	});
}





function addUser()
{
   $.dialog({ 
	    id : 'ocsr',
    	title : '添加系统用户',
    	width: '620px', 
    	height: '590px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/AddSystemUser.jsp'
	});
}

function editUser(userId, userName)
{
   $.dialog({ 
	    id : 'oesr',
    	title : '编辑系统用户 - '+userName,
    	width: '620px', 
    	height: '560px',
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/EditSystemUser.jsp?userId='+userId

	});
}

function editUserHaveRole(userId, userName)
{
   $.dialog({ 
	    id : 'oesr',
    	title : '用户角色授予 - '+userName,
    	width: '570px',  
    	height: '585px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/EditUserHaveRole.jsp?userId='+userId

	});
}

function deleteUser(userId)
{
   	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除当前所选用户吗？',
                    
                    ok: function () { 
       
      					window.location.href = '<cms:Domain/>security/deleteSystemUser.do?userId='+userId+"&<cms:Token mode='param'/>";
      					$.dialog.tips('删除管理员成功!',2,'32X32/succ.png');
    }, 
    cancel: true 
    });
}

function changePassword(userId, userName)
{
	$.dialog({ 
	    id : 'ocpd',
    	title : '修改密码 - '+userName,
    	width: '530px', 
    	height: '185px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/ChangePassword.jsp?userId='+userId
	});

}

function changeStatus(flag)
{
	var cidCheck = document.getElementsByName('checkedId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择目标管理员！', 
       cancel: true 
                    
	
	  });
	  return;
	}

	var url = "<cms:BasePath/>security/changeStatus.do?flag="+flag+"&ids="+ids+"&<cms:Token mode='param'/>";
                    

			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		$.dialog({ 
							   					title :'提示',
							    				width: '160px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/succ.png', 
							    				
							                    content: '改动管理员状态成功！', 
										       ok: function()
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

function deleteUser()
{
	var cidCheck = document.getElementsByName('checkedId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
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
    				
                    content: '请选择需要删除管理员！', 
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
    				
                    content: '您确认删除管理员吗！', 
                    
                    ok:function()
                    {
                    	var url = "<cms:BasePath/>security/deleteSystemUser.do?ids="+ids+"&<cms:Token mode='param'/>";
                    

				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				            	var msg = eval("("+mg+")");
				            	
				               if('success' == msg)
				               {
				               		$.dialog({ 
								   					title :'提示',
								    				width: '160px', 
								    				height: '60px', 
								                    lock: true, 
								    				icon: '32X32/succ.png', 
								    				
								                    content: '删除管理员成功！', 
											       ok: function()
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
                    ,
       				cancel: true 
                    
	
	  });

	


}

function search()
{
		
	var key = document.getElementById('searchKey').value;
	
	window.location='ManagerUser.jsp?key='+encodeData(key);
}

function change(obj)
{
	replaceUrlParam(window.location, 'pn=1&orgCode='+obj.value);
}

</script>
</cms:LoginUser>
