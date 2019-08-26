<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
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
							<a href="#">微信公众号</a> &raquo;
							<a href="#">回复信息管理</a> 
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
									
										分组:
									<select class="form-select" id="gid" name="gid" onchange="javascript:change(this.value)"  >
										
										<option value="">
											---- 所有关注用户 ----
										</option>
										
										<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="">
										
										<option value="${UG.wuGroupId}">
												${UG.wuGroupName}
										</option>
										
										 </cms:QueryData>
										 
									</select>
									&nbsp;
										
							
									</div>
									<div>
									
										<a href="javascript:openChangeWxUserGroupDialog();" class="btnwithico" onclick=""><img src="../style/icons/zone--plus.png" width="16" height="16" /><b>批量改动分组&nbsp;</b> </a>
										 <a href="javascript:deleteNotSubWxUser();" class="btnwithico"> <img src="../style/icon/del.gif" alt="" /><b>清除未关注用户&nbsp;</b> </a>
										
										<a href="javascript:transferFromWxUser();" class="btnwithico"> <img src="../../core/style/icons/document-page-previous.png" alt="" /><b>从微信服务器同步&nbsp;</b> </a>
										
									</div>
								 
									<div class="fr">
										 搜索用户 <select class="form-select" id="target" name="target"   >
										
										<option value="nc">
											昵称
										</option>
										<option value="bz">
											备注信息
										</option>
											
										 
										 
									</select>:&nbsp;
										<input id="searchKey" name="query" size="30" maxlength="60" class="form-input"   value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
										<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												 
												
												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
 
												<td width="3%">
													<strong>头像</strong>
												</td>
												
												<td width="8%">
													<strong>昵称</strong>
												</td>

												<td width="3%">
													<strong>性别</strong>
												</td>
												
												<td width="8%">
													<strong>地区</strong>
												</td>
												
												<td width="6%">
													<strong>分组</strong>
												</td>
												
												<td width="3%">
													<strong>是否关注</strong>
												</td>

												 
												
												<td width="5%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserForTag" objName="WU" var=",${param.groupId},${param.key},${param.target},,">
 
												<tr>

												 	 
													<td>
														<input class="inputCheckbox"  name="checkedId" value="${WU.openId}" type="checkbox" onclick="javascript:" />
													</td>
													
													<td>				
														<div style="height:3px;"></div>	
															 <cms:if test="${empty WU.wuHeadimgurl}">
															 	<img src="../../core/style/blue/images/no-image.png" width="50" height="50"/>
															 </cms:if>	
															 <cms:else>
															 	<img src="${WU.wuHeadimgurl}" width="50" height="50"/>
															 </cms:else>								
															
															<div style="height:3px;"></div>
													</td>
													
													<td>														
															
															<cms:DecodeParam str="${WU.wuNickname}" /> 
													</td>
													
													<td>
															<cms:if test="${WU.wuSex == 1}">
																男
															</cms:if>
															<cms:else>
																女
															</cms:else>
													</td>		
														
																					
													<td>		
													
													
													 	${WU.wuCountry} - ${WU.wuProvince} - ${WU.wuCity}
													 
													</td>
													
													
													<td>														
															<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="${WU.wuGroupid}">
 																${UG.wuGroupName}
 															</cms:QueryData>
															
													</td>
												 
													<td>
															<cms:if test="${WU.subStatus == 1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
													</td>	
													 
													
													<td>
															<div>
																<center>
																	<span class="STYLE4"><a href="javascript:openEditWxUserDialog('${WU.openId}')"><img src="../../core/style/icons/script-attribute-t.png" width="16" height="16" />&nbsp;备注</a> 
																	&nbsp;
																 	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="WU">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>
											<cms:if test="${empty param.key}">
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
											</cms:if>
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
<cms:LoginUser>
<script>

initSelect('target','${param.target}');
		
function search()
{
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	window.location='ManageWxUser.jsp?key='+encodeData(key)+'&target='+$('#target').val();
}

function openChangeWxUserGroupDialog()
{
	 
	$.dialog({ 
		id:'ocwmd',
    	title :'选择新的分组',
    	width: '370px', 
    	height: '100px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/SelectNewWxUserGroup.jsp'
	});
}

function openEditWxUserDialog(id)
{
	 

	$.dialog({ 
		id: 'oewmd',
    	title :'查看并备注微信用户',
    	width: '660px', 
    	height: '540px',  
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/ViewAndRemarkWxUser.jsp?openId='+id
	});
}



initSelect('gid','${param.groupId}');

function change(val)
{
	replaceUrlParam(window.location, 'groupId='+val);
}



function transferFromWxUser()
{
	var tip = $.dialog.tips('正在同步,请等待...',9600000000,'loading.gif');
	

	 var url = "<cms:BasePath/>wx/getWxUserFromWxServer.do"+"?<cms:Token mode='param'/>";
                    
 		
	
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
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '从微信服务器同步用户成功!',
				                    
				                    ok: function () 
				                    { 
				                    	window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               } 	
			               else if(mg.indexOf('errcode') != -1)
				           {
				           		$.dialog({ 
					   					title :'提示',
					    				width: '270px', 
					    				height: '60px', 
					    				 
					                    lock: true, 
					    				icon: '32X32/fail.png',
					                    content: msg, 
					                    ok: function(){ 
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
							
									    				cancel: function () 
									                    { 
									                    	window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
}


function deleteNotSubWxUser()
{
	$.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认清除未关注用户信息吗？',
                    
                    ok: function () 
                    { 
                    var tip = $.dialog.tips('正在执行,请等待...',9600000000,'loading.gif');
	
                   
                    var url = "<cms:BasePath/>wx/deleteNotSubWxUser.do?<cms:Token mode='param'/>";
                    
 		
 				
 		
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
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除未关注用户成功!',
				                    
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
							
									    				cancel: function () 
									                    { 
									                    	window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
       
       
    				}, 
    				cancel: true 
   	});

}



</script>
</cms:LoginUser>
