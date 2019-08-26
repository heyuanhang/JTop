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
						<a href="#">站点会员</a> &raquo; <a href="#">会员权限</a>&raquo; <a href="#">会员组管理</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<cms:LoginUser>
			<form id="roleForm" name="roleForm" method="post">

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
					<tr>
						<td class="mainbody" align="left" valign="top">
							<!--main start-->
							<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

								<tr>
									<td style="padding: 7px 10px;" class="">
										<div class="fl">
											 
										</div>
										<div>
											<a href="javascript:addRole();" class="btnwithico" onclick=""><img src="../style/icons/users.png" width="16" height="16" /><b>增加会员组&nbsp;</b> </a>
											<a href="javascript:deleteRole();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
										</div>
									</td>
								</tr>

								<tr>
									<td id="uid_td25" style="padding: 2px 6px;">
										<div class="DataGrid">
											<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

												<tr class="datahead">
													<td width="2%">
														<strong>组ID</strong>
													</td>

													<td width="1%">
														<input class="form-checkbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
													</td>

													<td width="9%">
														<strong>会员组名称</strong>
													</td>
													
													<td width="18%">
														<strong>描叙</strong>
													</td>
													

													 


													<td width="9%">
														<center>
															<strong>操作</strong>
														</center>
													</td>
												</tr>

												<cms:MemberRole >
													 
														<tr>

															<td>
																${Role.roleId}
															</td>

															<td>
																<input class="form-checkbox" name="checkedId" value="${Role.roleId}" type="checkbox" onclick="javascript:" />
															</td>

															<td>
																&nbsp;${Role.roleName}
															</td>

														 <td>
																&nbsp;${Role.roleDesc}
															</td>
															

															<td>
																<div>
																	<center>
																		<a href="javascript:editRole('${Role.roleId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																		<a href="javascript:openMaintainSystemRoleDialog('${Role.roleId}','${Role.roleName}');"><img src="../../core/style/icons/balance.png" width="16" height="16" />&nbsp;授权</a>&nbsp;&nbsp;&nbsp;&nbsp;
																		<a href="javascript:openRoleUserDialog('${Role.roleId}','${Role.roleName}');"><img src="../../core/style/icons/user-green.png" width="16" height="16" />&nbsp;会员</a>&nbsp;&nbsp;&nbsp;&nbsp;
																	</center>
																</div>
															</td>
														</tr>
													 
												</cms:MemberRole>
												<cms:Empty flag="Role">
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

 

function openRoleUserDialog(roleId,roleName)
{
	$.dialog({ 
	    id : 'orud',
    	title : roleName + ' 当前拥有的成员',
    	width: '570px', 
    	height: '530px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/member/EditRoleHaveMember.jsp?roleId='+roleId+'&uid='+Math.random()
	
	  
	});
}

function openMaintainSystemRoleDialog(roleId,roleName)
{
	<cms:CurrentSite>
		var siteId='${CurrSite.siteId}';
	</cms:CurrentSite>
			
	$.dialog({ 
	    id : 'ooard',
    	title : '会员组授权 - '+roleName,
    	width: '650px', 
    	height: '555px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/member/MaintainMemberRole.jsp?roleId='+roleId+'&siteId='+siteId

	});
}


function deleteRole()
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
    				
                    content: '请选择需要删除会员组！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选会员组角色吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>security/deleteMemberRole.do?ids="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
				    				width: '140px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除会员组成功!',
				                    
				                    ok: function () 
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
       
       
    				}, 
    				cancel: true 
   	});
 
}


function addRole()
{
   $.dialog({ 
	    id : 'ocsr',
    	title : '添加会员组角色',
    	width: '540px', 
    	height: '250px',  
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/member/AddMemberRole.jsp?uid='+Math.random()

	});
}

function editRole(roleId)
{
   $.dialog({ 
	    id : 'oesr',
    	title : '编辑会员组角色',
    	width: '540px', 
    	height: '250px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/member/EditMemberRole.jsp?roleId='+roleId

	});
}

 

</script>
</cms:LoginUser>
