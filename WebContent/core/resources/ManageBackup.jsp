<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />
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
			
			
			 
	         if("true"==="${param.deleteFlag}")
	         {   
	         	$.dialog.tips('删除文件成功...',1,'32X32/succ.png');  	 	
	         }
			
			
        </script>
	</head>
	<cms:LoginUser>
	<body>
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">节点与全局配置</a> &raquo;
						<a href="#">系统备份管理</a>
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
			<tr>
				<td class="mainbody" align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding: 7px 10px;">
								<div class="fl">
									 		<a href="javascript:backup();" class="btnwithico" onclick=""><img src="../style/icons/wrench--pencil.png" width="16" height="16" /><b>立即备份&nbsp;</b> </a>
										<a href="javascript:reload();" class="btnwithico" onclick=""> <img src="../../core/style/icons/arrow-circle.png" alt="" /><b>刷新&nbsp;</b> </a>
									
										 &nbsp;
								</div>
								<div class="fr" >
									&nbsp;&nbsp;&nbsp;确认登录密码:&nbsp;<input type="password" size="35" id="pw" name="pw" class="form-input"></input>
							
								</div>
								 
							</td>
						<tr>
						 
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											
										 <td width="14%">
												<strong>备份文件名</strong>
											</td>
											
										 	<td width="8%">
												<strong>备份时间</strong>
											</td>
										 	

											<td width="7%">
												<strong>大小</strong>
											</td>

										
											<td width="6%">
												<center><strong>操作</strong></center>
											</td>
										</tr>

									    <cms:QueryData service="cn.com.mjsoft.cms.resources.service.ResourcesService" method="showBackupFileForTag" objName="File">
											<tr>
											 
												
												<td>
													&nbsp;&nbsp;<img src="../../core/style/icons/bin.png"/>${File.name}
												</td>
												
													<td>
												 	 ${File.time}
												</td>

												<td>
													${File.size}
												</td>

											

												<td>
													<span >
													<center>
														<a href="javascript:downloadBak('${File.name}');"><img src="../../core/style/icons/drive-download.png" alt="" />下载</a>&nbsp;&nbsp;&nbsp;
													
														<a href="javascript:restore('${File.name}');"><img src="../../core/style/icons/arrow-continue-000-top.png" alt="" />还原</a>												
											
													</center>
													</span>
												</td>


												 
											</tr>
										</cms:QueryData>

										<cms:Empty flag="File">
																					<tr>
																						<td class="tdbgyew" colspan="9">
																							<center>
																								当前没有数据!
																							</center>
																						</td>
										</cms:Empty>
										<tr>
											<td colspan="12" class="PageBar" align="left">
												<div class="fr">

												</div>
												<div class="fl"></div>
											</td>
										</tr>

									</table>

									 
									
									<form id="downFileForm" name="downFileForm" method="post">
										<input type="hidden" id="downFileInfo" name="downFileInfo" />
										<cms:Token mode="html"/>
									</form>
								</div>
								<div class="mainbody-right"></div>
							</td>
						</tr>

					</table>

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

function backup()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if("${Auth.orgCode}" != '001' ||  orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有Admin允许备份系统！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	if($('#pw').val() == '')
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请在执行前输入Admin登录密码！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog.tips('正在执行备份，请耐心等待...',3600000000,'loading.gif');
						

	var url = "<cms:BasePath/>resources/backupSystem.do?<cms:Token mode='param'/>&pw="+$('#pw').val();
	
	var postData = "";
	
	                   
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
			       		success: function(mg)
			            {     
			               var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '160px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/succ.png', 
							    		
							                    content: '备份系统成功！',
					
							    				ok:function()
							    				{ 
					             					window.location.reload();
							    				}
								});   
			               		
			               } 
			               else if('pwerror' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '220px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/fail.png', 
							    		
							                    content: 'Admin登录密码错误，拒绝执行！',
					
							    				ok:function()
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
							
									    				cancel:function()
									    				{ 
							             					window.location.reload();
									    				}
									});
			               }   
			              
			            }
    });	


}


function restore(file)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if("${Auth.orgCode}" != '001' ||  orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有Admin允许还原系统！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	if($('#pw').val() == '')
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请在执行前输入Admin登录密码！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog.tips('正在执行还原，请耐心等待...',3600000000,'loading.gif');
						

	var url = "<cms:BasePath/>resources/restore.do?<cms:Token mode='param'/>&target="+file+"&pw="+$('#pw').val();
	
	var postData = "";
	
	                   
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
			       		success: function(mg)
			            {     
			               var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '160px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/succ.png', 
							    		
							                    content: '还原备份成功！<br/><font color="red">请重启动CMS环境</font>',
					
							    				ok:function()
							    				{ 
					             					window.location.reload();
							    				}
								});   
			               		
			               } 
			               
			               else if('pwerror' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '220px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/fail.png', 
							    		
							                    content: 'Admin登录密码错误，拒绝执行！',
					
							    				ok:function()
							    				{ 
					             					window.location.reload();
							    				}
								});   
			               		
			               } 	
			               
			               else if('-2' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '200px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/fail.png', 
							    		
							                    content: '备份中不存在数据文件！无法还原数据库。',
					
							    				ok:function()
							    				{ 
					             					window.location.reload();
							    				}
								});   
			               		
			               } 
			               else if('-3' == msg)
			               {			               		
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '200px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/fail.png', 
							    		
							                    content: '执行数据还原失败！请检查错误信息。',
					
							    				ok:function()
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
							
									    				cancel:function()
									    				{ 
							             					window.location.reload();
									    				}
									});
			               }   
			              
			            }
    });	


}


function downloadBak(file)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if("${Auth.orgCode}" != '001' ||  orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有Admin允许下载备份！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	var dfForm = document.getElementById('downFileForm');
	
	dfForm.action = '<cms:BasePath/>resources/downloadBak.do?target='+file+"&pw="+$('#pw').val();
	
	dfForm.submit();
}

function reload()
{
	window.location.href = window.location.href;
}


</script>
</cms:LoginUser>
