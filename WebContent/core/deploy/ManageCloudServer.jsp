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
						<a href="#">系统配置</a> &raquo;
						<a href="#">云存储配置</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<cms:CurrentSite>
		<form id="roleForm" name="roleForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openAddServerDialog();" class="btnwithico" onclick=""><img src="../style/icons/server--plus.png" width="16" height="16" /><b>添加云存储&nbsp;</b> </a>
									 	<a href="javascript:openCopyCfgFromSiteDialog('cloud');"  class="btnwithico"><img src="../style/icons/document-convert.png" width="16" height="16"/><b>同步站群配置&nbsp;</b> </a>
										
									 	(注意:云存储的文件存储模式需配置为 公有读*私有存 )
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
												 

												<td width="5%">
													<strong>云存储类型</strong>
												</td>

												<td width="9%">
													<strong>文件访问URL</strong>
												</td>

												<td width="4%">
													<strong>地区信息</strong>
												</td>
												
												<td width="6%">
													<strong>Bucket信息</strong>
												</td>

											 

												<td width="8%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.site.service.SiteGroupService" method="getCloudCfgForTag" var=",${CurrSite.siteId}" objName="Server"  >
											<tr>
													<td>
														${Server.cloId}
													</td>
													
													<td>
														<cms:if test="${Server.cloudType == 'TXCOS'}">
														 腾讯COS
														</cms:if>
														<cms:if test="${Server.cloudType == 'ALOSS'}">
														 阿里OSS
														</cms:if>
														<cms:if test="${Server.cloudType == 'QN'}">
														 七牛云
														</cms:if>
														
													</td>
													 
													<td>
														${Server.accessUrl}
													</td>
													<td>
														${Server.location}
													</td>
													
													 <td>
														${Server.bucketName}
													</td>
												 

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/network-hub.png" width="16" height="16" /><a href="javascript:checkServerConn('${Server.cloId}');">&nbsp;检查连接</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditServerDialog('${Server.cloId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteServer('${Server.cloId}');">删除</a>
																</span>
															</center>
														</div>
													</td>
												</tr>
											</cms:QueryData>

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
    	title :'添加云存储',
    	width: '750px', 
    	height: '480px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/deploy/AddCloudServer.jsp'
	});
}

function openEditServerDialog(id)
{
	$.dialog({ 
    	title :'编辑云存储',
    	width: '750px', 
    	height: '480px', 
    	lock: true, 
        max: false,
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/deploy/EditCloudServer.jsp?id='+id
	});
}

function checkServerConn(id)
{
	var url = "<cms:BasePath/>site/checkCloudCfg.do?id="+id;
 		
 	var tip = $.dialog.tips('正在检查云存储可用状态...',25,'loading.gif'); 
 	
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {    
			            	  tip.close();
			            	  
			            	  var msgObj=  eval("("+msg+")");
			            	  
			            	  if('true' == msgObj)
			            	  {
			            	  	$.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/succ.png', 
									    				
									                    content: "云存储连接成功！",
							
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
									    				width: '400px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: "云存储连接失败:"+msgObj,
							
									    				ok: function()
														{
														      					         		 
															       				window.location.reload();     
														} 
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
    				
                    content: '您确认删除云存储配置吗？',
                    
                    ok: function () 
                    { 
                                      
                    	var url = "<cms:BasePath/>site/deleteCloudCfg.do?id="+id+"&<cms:Token mode='param'/>";
 		
 	
						$.ajax({
								      		type: "POST",
								       		url: url,
								       		data:'',
								   
								       		success: function(msg)
								            {     
								                 var msgObj=  eval("("+msg+")");
								             
								                 if("success" === msgObj)
								                 {
								                 	 $.dialog({ 
									   					title :'提示',
									    				width: '220px', 
									    				height: '60px', 
									                    lock: true, 
									    				icon: '32X32/succ.png', 
									    				
									                    content: '删除云存储配置成功!', 
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
												
														    				cancel: function()
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
</cms:CurrentSite>