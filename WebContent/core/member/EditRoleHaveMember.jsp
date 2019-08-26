<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

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
										<a href="javascript:openSelectUserDialog(${param.roleId})" class="btnwithico"><img src="../style/icons/user-share.png" width="16" height="16" /><b>选取会员&nbsp;</b> </a>
										<a href="javascript:deleteCheckedUser();" class="btnwithico"><img src="../style/icons/user--minus.png" width="16" height="16" /><b>删除&nbsp;</b> </a>
										&nbsp; 
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
												


												<td width="10%">
													<strong>会员名</strong>
												</td>

												<td width="10%">
													<strong>邮箱地址</strong>
												</td>

											</tr>

											<cms:Member page="true" roleId="${param.roleId}" pageSize="10">
												 
													<tr>
														<td>
															<input class="inputCheckbox" name="checkedUserId" value="${Member.memberId}" type="checkbox" onclick="javascript:" />
														</td>
														
														<td>
															&nbsp;${Member.memberName}
														</td>
														<td>
															${Member.email}
														</td>
														 
														
													</tr>
												 
											</cms:Member>
												<cms:Empty flag="Member">
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
																				<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
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
																									
																									if(cp > parseInt('${Page.pageCount}'))
																									{
											                                                           cp=parseInt('${Page.pageCount}');
																									}
																								
																									
																									replaceUrlParam(window.location,'pn='+cp);		
																								}
																					
																					
																								function jump()
																								{
																								    var cp = parseInt(document.getElementById('pageJumpPos').value);
																								    
																								    if(cp > parseInt('${Page.pageCount}'))
																									{
											                                                           cp=parseInt('${Page.pageCount}');
																									}
																								
																									replaceUrlParam(window.location,'pn='+cp);
																								}
																							</script>
																			<div class="fl"></div>
																		</td>
																	</tr>
																</cms:PageInfo>
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
    	title :'选取会员',
    	width: '560px', 
    	height: '540px', 
    	parent:api,
        lock:true,
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/member/ShowAllMember.jsp?uid='+Math.random()+"&roleId="+roleId
	
	  
	});
}

function close()
{
	api.close();
	W.window.location.reload();
}

  
function deleteCheckedUser()
{
	var cidCheck = document.getElementsByName('checkedUserId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	} 
	
	if(ids == '')
	{
		W.$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '200px', 
			    	height: '60px', 
			        lock: true, 
			        parent: api,
			    	icon: '32X32/i.png', 
			        content: '请选择要从会员组删除的会员!', 
			       	cancel: true
		});
		return;
	}
	
	W.$.dialog.tips('会员组成员删除成功...',1,'loading.gif'); 
	
	var userForm = document.getElementById('userForm');
	userForm.action = '<cms:Domain/>security/deleteMemberFromRole.do';
	userForm.submit();
}
  
</script>
