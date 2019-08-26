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
						<a href="#">会员相关</a> &raquo;
						<a href="#">会员帐户管理</a>
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
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
								<div class="fl">
								<select id="status" class="form-select" onchange="javascript:filterAction(this.value);">
											<option value="" selected>
												-- 会员状态 --
											</option>

											<option value="1">
												正常使用
											</option>
											<option value="0">
												禁止登录
											</option>
											


										</select>
										&nbsp;&nbsp;
								
								</div>
									<div >
									
										<a href="javascript:changeStatus('close');" class="btnwithico" onclick=""><img src="../style/icons/light-bulb-off.png" width="16" height="16" /><b>停用&nbsp;</b> </a>
											<a href="javascript:changeStatus('open');" class="btnwithico" onclick=""><img src="../style/icons/light-bulb.png" width="16" height="16" /><b>启用&nbsp;</b> </a>
										

										<a href="javascript:sendUserMsg();" class="btnwithico" onclick=""><img src="../style/icons/mail.png" width="16" height="16" /><b>发站内信&nbsp;</b> </a>
										<a href="javascript:sendUserMail();" class="btnwithico" onclick=""><img src="../style/icons/mail.png" width="16" height="16" /><b>发电邮&nbsp;</b> </a>

										<a href="javascript:openChangeMemberScoreDialog();" class="btnwithico" onclick=""><img src="../style/icons/cake.png" width="16" height="16" /><b>赠送积分&nbsp;</b> </a>
										<a href="javascript:deleteSelectMember();" class="btnwithico" onclick=""><img src="../style/icons/user--minus.png" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
									<div class="fr">

										


										<select id="searchAction" class="form-select">
											<option value="">
												--- 搜索域 ---
											</option>
											<option value="name" >
												会员名
											</option>
											<option value="trueName">
												真实名
											</option>
											<option value="email">
												邮件
											</option>
											<option value="phone">
												手机
											</option>

										</select>
										<input id="searchKey" name="query" size="25" maxlength="60" class="form-input" style="vertical-align:top;" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
										<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />

									</div>

									<div class="fr">

									</div>
								</td>
							</tr>



							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="99.8%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" value="*" onclick="javascript:selectAll('checkedMemberId',this);" type="checkbox" />
												</td>

												<td width="8%">
													<strong>会员名</strong>
												</td>

												<td width="6%">
													<strong>真实名称</strong>
												</td>



												<td width="11%">
													<strong>邮箱</strong>
												</td>

											 

												<td width="3%">
													<strong>邮箱验证</strong>
												</td>

												<td width="3%">
													<strong>帐号可用</strong>
												</td>
												
												<td width="3%">
													<strong>身份合法</strong>
												</td>

												 

												<td width="10%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

											<cms:CurrentSite>
												<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="retrieveMemberInfo" objName="Member" var="${CurrSite.siteId},${param.status},10,${param.pn},${param.sa},${param.key}">
													<tr id="tr-${status.index}">
														<td>
															${Member.memberId}
														</td>
														<td>
															<input class="inputCheckbox" name="checkedMemberId" value="${Member.memberId}" type="checkbox" onclick="javascript:changeTRBG(this, '${status.index}');" />
														</td>

														<td>
															${Member.memberName}
														</td>

														<td>
															${Member.trueName}
														</td>

														<td>
															${Member.email}
														</td>

														 

														<td>
															<cms:if test="${Member.isTrueEmail==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>

														<td>
															<cms:if test="${Member.useStatus==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>
														
														<td>
															<cms:if test="${Member.isTrueMan==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>

														 

														<td>
															<div>
																<center>
																	<a href="javascript:openViewMemberDialog('${Member.memberId}');"><img src="../../core/style/icon/card-address.png" width="16" height="16" />&nbsp;查看</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	<a href="javascript:deleteMember('${Member.memberId}');"><img src="../../core/style/icon/user--minus.png" width="16" height="16" />&nbsp;删除</a>&nbsp;&nbsp;&nbsp;
																</center>
															</div>
														</td>
													</tr>

												</cms:QueryData>
												<cms:Empty flag="Member">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
											</cms:CurrentSite>
											<tr id="pageBarTr">
												<cms:PageInfo>
													<td colspan="18" class="PageBar" align="left">
														<div class="fr">
															<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()"> </span>
															<span class="page">[<a href="javascript:page('h');">首页</a>]</span>
															<span class="page">[<a href="javascript:page('p');">上一页</a>]</span>
															<span class="page">[<a href="javascript:page('n');">下一页</a>]</span>
															<span class="page">[<a href="javascript:page('l');">末页</a>]</span>&nbsp;
														</div>
														<script>
														function page(f)
														{
																if('h' == f)//首页
																{					
																	replaceUrlParam(window.location, 'pn=1');
																}
																else if('n' == f)
																{
																	replaceUrlParam(window.location, 'pn=${Page.currentPage+1}');
																}
																else if('p' == f)
																{
																	replaceUrlParam(window.location, 'pn=${Page.currentPage-1}');
																}else if('l' == f)
																{
																	replaceUrlParam(window.location, 'pn=${Page.pageCount}');
																}
														
														}
	
													</script>
												</cms:PageInfo>

												</td>
											</tr>

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

initSelect('status','${param.status}');

initSelect('searchAction','${param.sa}');

 

if('${param.sa}' == '')
{
	document.getElementById('searchKey').value = "";
}

function openCreateMTDialog()
{
	$.dialog({ 
    	title :'新增会员站内信模板',
    	width: '670px', 
    	height: '505px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/CreateMessageTemplate.jsp'
	});
}

function openViewMemberDialog(id)
{
	$.dialog({ 
    	title :'查看会员',
    	width: '740px', 
    	height: '690px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/ViewAndCheckMemberInfo.jsp?id='+id
	});
}


function openChangeMemberScoreDialog()
{
	var cidCheck = document.getElementsByName('checkedMemberId');
	
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
		$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '170px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			        content: '请选择需要改动积分的会员!', 
			       	cancel: function()
			       	{
			       		
			       	}
		});
		return;
	}

	$.dialog({ 
    	title :'改变会员积分',
    	width: '460px', 
    	height: '120px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/ChangeMemberScore.jsp?ids='+ids
	});
}







function filterAction(filterValue)
{
	replaceUrlParam(window.location, 'status='+filterValue);
}

function search()
{
	var sa = document.getElementById('searchAction').value;
	
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	window.location='ManageMember.jsp?sa='+sa+'&key='+encodeData(key);
}





function sendUserMsg()
{
	var cidCheck = document.getElementsByName('checkedMemberId');
	
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
		$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '170px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			        content: '请选择需要发消息的会员!', 
			       	cancel: function()
			       	{
			       		
			       	}
		});
		return;
	}

	$.dialog({ 
    	title :'发送会员站内信',
    	width: '670px', 
    	height: '595px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/SystemManagerSendMessage.jsp'
	});
}


function sendUserMail()
{
	var cidCheck = document.getElementsByName('checkedMemberId');
	
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
		$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '170px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			        content: '请选择需要发送邮件的会员!', 
			       	cancel: function()
			       	{
			       		
			       	}
		});
		return;
	}

	$.dialog({ 
    	title :'发送会员邮件',
    	width: '670px', 
    	height: '595px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/SystemManagerSendMail.jsp'
	});
}

function changeStatus(flag)
{

	var cidCheck = document.getElementsByName('checkedMemberId');
	
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
		$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '170px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			        content: '请选择需要改变状态的会员!', 
			       	cancel: function()
			       	{
			       		
			       	}
		});
		return;
	}
	
	var url = "<cms:BasePath/>member/changeUseStatus.do"+"?<cms:Token mode='param'/>";
    
    var postData = "ids="+ids+"&flag="+flag;

 	$.ajax({
      	type: 'POST',
       	url: url,
       	data:postData,
   
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
				    				
				                    content: '改变帐号状态成功!',
				                    
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

}






function deleteMember(id)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选会员吗？',
                    
                    ok: function () 
                    { 
                   			var url = "<cms:BasePath/>member/deleteMember.do"+"?<cms:Token mode='param'/>";
    
						    var postData = "ids="+id;
						
						 	$.ajax({
						      	type: 'POST',
						       	url: url,
						       	data:postData,
						   
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
										    				
										                    content: '删除会员帐户成功!',
										                    
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

function reg(obj)
{

	if(obj.checked == true)
	{
		selectAll('checkedMemberId');
	}
	else
	{
		unSelectAll('checkedMemberId');
	}
}

function deleteSelectMember()
{
	var cidCheck = document.getElementsByName('checkedMemberId');
	
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
		$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '160px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			        content: '请选择需要删除的会员!', 
			       	cancel: function()
			       	{
			       		
			       	}
		});
		return;
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选会员吗？',
                    
                    ok: function () 
                    { 
                   
                    var url = "<cms:BasePath/>member/deleteMember.do"+"?<cms:Token mode='param'/>";
    
   
				    var postData = "ids="+ids;
				 
				 	$.ajax({
				      	type: 'POST',
				       	url: url,
				       	data:postData,
				   
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
								    				
								                    content: '删除会员帐户成功!',
								                    
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



</script>
