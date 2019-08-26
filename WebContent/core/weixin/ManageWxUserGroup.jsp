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
										<a href="javascript:openCreateWxUserGroupDialog();" class="btnwithico" onclick=""><img src="../style/icons/zone--plus.png" width="16" height="16" /><b>创建分组&nbsp;</b> </a>
										 
										<a href="javascript:transferFromWxUserGroup();" class="btnwithico"> <img src="../../core/style/icons/document-page-previous.png" alt="" /><b>从微信服务器同步&nbsp;</b> </a>
										
							
										</div>
									
								 
									<div class="fr">
										 

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												 
												
												 
 
												<td width="8%">
													<strong>组ID</strong>
												</td>
												
												<td width="10%">
													<strong>名称</strong>
												</td>

												<td width="8%">
													<strong>用户数量</strong>
												</td>
												
												 

												 
												
												<td width="6%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="">
 
												<tr>

												 	 
													 
													
													<td>				
														[ ${UG.wuGroupId} ]
													</td>
													
													<td>														
															
														${UG.wuGroupName}
													</td>
													
													<td>
														${UG.wuUserCount}人
													</td>		
														
 
													
													<td>
															<div>
																<center>
																	<span class="STYLE4">&nbsp;<a href="javascript:openEditWxUserGroupDialog('${UG.wuGroupId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteWxUG('${UG.wuGroupId}');">删除</a> 
																	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="UG">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
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
<cms:LoginUser>
<script>

 

function openCreateWxUserGroupDialog()
{
	 
	$.dialog({ 
		id:'ocwmd',
    	title :'新增用户分组',
    	width: '480px', 
    	height: '130px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/CreateWxNewUserGroup.jsp'
	});
}

function openEditWxUserGroupDialog(id)
{
	if('0' == id || '1' == id || '2' == id)
	{
		$.dialog({ 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		    				 
		                    lock: true, 
		    				icon: '32X32/fail.png',
		                    content: '微信默认用户组不可操作！', 
		                    cancel: function(){ 
	      						window.location.reload();
	    					} 
		});
		
		return;
	}

	$.dialog({ 
		id: 'oewmd',
    	title :'编辑用户分组',
    	width: '480px', 
    	height: '130px',  
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/EditWxNewUserGroup.jsp?gid='+id
	});
}
 

function deleteWxUG(id)
{
	if('0' == id || '1' == id || '2' == id)
	{
		$.dialog({ 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		    				 
		                    lock: true, 
		    				icon: '32X32/fail.png',
		                    content: '微信默认用户组不可操作！', 
		                    cancel: function(){ 
	      						window.location.reload();
	    					} 
		});
		
		return;
	}
	 
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除微信用户分组吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/deleteWxUserGroupFromWxServer.do?gid="+id+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
				    				
				                    content: '删除微信用户分组成功!',
				                    
				                    ok: function () 
				                    { 
				                    	window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               } 	
			               else if(mg.indexOf('资源ID:') != -1 )
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
       
       
    				}, 
    				cancel: true 
   	});


}
 


function transferFromWxUserGroup()
{
	 var url = "<cms:BasePath/>wx/getWxUserGroupFromWxServer.do"+"?<cms:Token mode='param'/>";
                    
 		
	
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
				    				
				                    content: '从微信同步用户组成功!',
				                    
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
}



</script>
</cms:LoginUser>
