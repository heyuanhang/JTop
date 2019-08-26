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
						<a href="#">站点维护</a> &raquo;
						<a href="#">广告代码配置</a>
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
							<td style="padding: 6px 11px;" class="">
								<div class="fl">
									<a href="javascript:openCreateAdvertConfigDialog();" class="btnwithico"> <img src="../../core/style/icons/color-adjustment-green.png" alt="" /><b>新建广告配置&nbsp;</b> </a>
									&nbsp;(广告代码配置为CMS站群各站点通用,请关闭浏览器广告屏蔽插件以管理广告)
									</div>
								<div class="fr">
								</div>




							</td>
						<tr>
							<td id="uid_td25" style="padding: 1px 9px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%" height="30">
												<strong>ID</strong>
											</td>
											
											<td width="10%">
												<strong>广告配置名称</strong>
											</td>


											<td width="18%">
												<strong>简介</strong>
											</td>


										

											<td width="10%">
												<center><strong>维护</strong></center>
											</td>
										</tr>


										<cms:SystemAdvertConfig configId="all">
											<tr>
												<td>
													${Config.configId}
												</td>
												

												<td>
													${Config.configName}
												</td>

												<td>
													<cms:SubString len="30" tail="..." str="${Config.configDesc}" />
												</td>

												


												<td>
													<div>
														<center>
															<a href="javascript:openEditAdvertConfigDialog('${Config.configId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<a href="javascript:openEditAdvertCodeDialog('${Config.configId}')"><img src="../../core/style/icons/script-code.png" width="16" height="16" />&nbsp;代码</a>&nbsp;&nbsp;&nbsp;<a href="javascript:deleteAdvertCfg('${Config.configId}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a>
														</center>
													</div>
												</td>

											</tr>
										</cms:SystemAdvertConfig>


										<cms:Empty flag="Config">
														<tr>
															<td class="tdbgyew" colspan="10">
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


function openCreateAdvertConfigDialog()
{
	$.dialog({ 
	    id : 'ocacd',
    	title : '新建广告配置',
    	width: '560px', 
    	height: '330px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/trevda/CreateTrevdaConfig.jsp?uid='+Math.random()

	});
}

function openEditAdvertConfigDialog(configId)
{
	$.dialog({ 
	    id : 'oeacd',
    	title : '编辑广告配置',
    	width: '560px', 
    	height: '330px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/trevda/EditTrevdaConfig.jsp?configId='+configId+"&uid="+Math.random()
	});
}

function openEditAdvertCodeDialog(configId)
{
	$.dialog({ 
	    id : 'oeaccd',
    	title : '编辑广告代码',
    	width: '780px', 
    	height: '540px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/trevda/EditTrevdaCode.jsp?configId='+configId+"&uid="+Math.random()
	});
}


function deleteAdvertCfg(ids)
{
	
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选广告配置吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>trevda/deleteTrevdaConfig.do?configId="+ids+"&<cms:Token mode='param'/>";
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			dataType:"json",
			       		success: function(msg)
			            {     
			            
			            	 	
			               if('success' == msg)
			               {
			               		$.dialog({ 
				   					title :'提示',
				    				width: '160px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除广告配置成功！',
				                    
				                    ok: function () { 
				                    	window.location.reload();
				                    }
				                })
			               		
			               } 	
			               else if('cfguse' == msg)
			               {
			               	    $.dialog({ 
				   					title :'提示',
				    				width: '240px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/fail.png', 
				    				
				                    content: '删除广告配置失败,当前配置正在被使用!',
				                    
				                    cancel: function () { 
				                    	window.location.reload();
				                    }
				                })
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
						
								    				cancel: function () { 
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

