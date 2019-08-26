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
						<a href="#">站点维护</a> &raquo; FTP服务器设置
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
										<a href="javascript:openAddServerDialog();" class="btnwithico" onclick=""><img src="../style/icons/server--plus.png" width="16" height="16" /><b>增加服务器&nbsp;</b> </a>
										
										<a href="javascript:openCopyCfgFromSiteDialog('server');"  class="btnwithico"><img src="../style/icons/document-convert.png" width="16" height="16"/><b>同步站群配置&nbsp;</b> </a>
															
										<a href="javascript:checkServerConn();" class="btnwithico" onclick=""><img src="../../core/style/icons/network-hub.png" width="16" height="16" /><b>检查连接状态&nbsp;</b> </a>

									</div>
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
												 

												<td width="9%">
													<strong>名称</strong>
												</td>

												<td width="6%">
													<strong>远程IP</strong>
												</td>

												<td width="4%">
													<strong>传输模式</strong>
												</td>

												<td width="13%">
													<strong>访问域名</strong>
												</td>

												<td width="3%">
													<strong>连接状态</strong>
												</td>

												<td width="7%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

											<cms:DispenseServer>
												<tr>
													<td>
														${Server.serverId}
													</td>
													 
													<td>
														${Server.serverName}
													</td>
													<td>
														${Server.serverIP}
													</td>
													<td>
														<cms:if test="${Server.protocol == 1}">
														 FTP
														</cms:if>
														<cms:if test="${Server.protocol == 2}">
														 SFTP
														</cms:if>
														<cms:if test="${Server.protocol == 3}">
														 本地目录
														</cms:if>
														
													</td>
													<td>
														${Server.serverUrl}
													</td>
													<td>
														<cms:if test="${Server.connectStatus == 1}">
															<font color="blue">正常</font>
														</cms:if>
														<cms:elseif test="${Server.connectStatus == 0}">
															<font color="red">断开</font>
														</cms:elseif>
														<cms:else>
																未知
														</cms:else>
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditServerDialog('${Server.serverId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteServer('${Server.serverId}');">删除</a>
																</span>
															</center>
														</div>
													</td>
												</tr>
											</cms:DispenseServer>

											<cms:Empty flag="Server">
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


function openAddServerDialog()
{
	$.dialog({ 
    	title :'添加服务器',
    	width: '710px', 
    	height: '540px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/deploy/AddServerConfig.jsp'
	});
}

function openEditServerDialog(id)
{
	$.dialog({ 
    	title :'编辑服务器',
    	width: '710px', 
    	height: '540px', 
    	lock: true, 
        max: false,
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/deploy/EditServerConfig.jsp?id='+id
	});
}

function checkServerConn()
{
	var url = "<cms:BasePath/>site/checkServer.do";
 		
 	var tip = $.dialog.tips('已经开启检查服务器网络状态,您可以暂时离开,系统将自动检查',5,'loading.gif'); 
 	
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {    
			            
			            	 var msg = eval("("+mg+")");
			            	 
			            	  if('success' != msg)
			            	  {
			            	  		tip.close();
			            	  		
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


function deleteServer(id)
{

	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
                    
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除分发服务器吗？',
                    
                    ok: function () 
                    { 
                                      
                    	var url = "<cms:BasePath/>site/deleteServerConfig.do?id="+id+"&<cms:Token mode='param'/>";
 		
 	
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
									    				width: '220px', 
									    				height: '60px', 
									                    lock: true, 
									    				icon: '32X32/i.png', 
									    				
									                    content: '删除分发服务器成功!', 
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
       
       
    				}, 
    				cancel: true 
   	});
	
	

	
}


function openCopyCfgFromSiteDialog(mode)
{
	 $.dialog({ 
	    id : 'occcd',
    	title : '从其他站点同步配置',
    	width: '510px', 
    	height: '110px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/channel/CopySiteConfig.jsp?mode='+mode

	});

}


</script>
