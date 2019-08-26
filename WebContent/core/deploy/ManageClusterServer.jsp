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
		<cms:LoginUser>
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#"></a> &raquo;
						<a href="#">系统配置</a> &raquo;
						<a href="#">集群服务器配置</a>
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
							 
							<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openCreateCsDialog();" class="btnwithico" onclick=""><img src="../style/icons/weather-cloud.png" width="16" height="16" /><b>添加服务器&nbsp;</b> </a>
										<a href="javascript:checkServerConn();" class="btnwithico" onclick=""><img src="../../core/style/icons/network-hub.png" width="16" height="16" /><b>检测连通性&nbsp;</b> </a>
						 					<a href="javascript:reload();" class="btnwithico" onclick=""> <img src="../../core/style/icons/arrow-circle.png" alt="" /><b>刷新&nbsp;</b> </a>
								
						 		</div>
									<div class="fr">

									</div>
								</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%" height="30">
												<strong>ID</strong>
											</td>
										 	<td width="11%">
												<strong>服务节点名称</strong>
											</td>

										

											<td width="21%">
												<strong>连接地址</strong>
											</td>
											
											 
											<td width="6%">
												<strong>连接状态</strong>
											</td>

											<td width="9%">
												<center><strong>操作</strong></center>
											</td>
										</tr>


										<cms:QueryData service="cn.com.mjsoft.cms.cluster.service.ClusterService" method="getClusterServerInfoForTag" var="" objName="Cs"  >
											
											<tr>
												<td>
													${Cs.serverId}
												</td>
												 

												<td>
													${Cs.serverName}
												</td>
												
												<td>
													${Cs.clusterUrl}
												</td>

											 
												<td>
														<cms:if test="${Cs.isActive == 1}">
															<font color="blue">正常</font>
														</cms:if>
														<cms:elseif test="${Cs.isActive == 0}">
															<font color="red">无效</font>
														</cms:elseif>
														<cms:else>
																未知
														</cms:else>
												</td>
												<td>
													<div>
														<center>
															<img src="../../core/style/icons/card-address.png" width="16" height="16" />
															<a href="javascript:editCsDialog('${Cs.serverId}');">编辑</a>&nbsp; &nbsp;
															<img src="../../core/style/default/images/del.gif" width="16" height="16" />
															<a href="javascript:deleteCsNode('${Cs.serverId}');">删除 </a>&nbsp;&nbsp;&nbsp;
														</center>
													</div>
												</td>

											</tr>

										</cms:QueryData>
										
										<cms:Empty flag="Cs">
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

function openCreateCsDialog()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '180px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许添加集群服务器！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog({ 
	    id : 'occsnd',
    	title : '添加集群服务器',
    	width: '620px', 
    	height: '200px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/deploy/AddClusterServer.jsp?uid='+Math.random()
	});
}

function editCsDialog(id)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '180px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许编辑集群服务器！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog({ 
	    id : 'ecsnd',
    	title : '编辑集群服务器',
    	width: '620px', 
    	height: '200px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/deploy/EditClusterServer.jsp?sid='+id+'&uid='+Math.random()
	});
}

function deleteCsNode(id)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许删除集群服务器！', 
	       cancel: true 
	                    
		  });
		  return;
	}
 
	
	
	$.dialog({ 
   					title :'提示',
    				width: '240px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选集群服务器吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>cluster/deleteCluServer.do?id="+id+"&<cms:Token mode='param'/>";
                    

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
				    				icon: '32X32/i.png', 
				    				
				                    content: '执行删除操作成功!',
				                    
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

function checkServerConn()
{
	var url = "<cms:BasePath/>cluster/checkCluServerStatus.do";
 		
 	var tip = $.dialog.tips('已开启检查集群服务器网络状态,您可以暂时离开,系统将自动检查',5,'loading.gif'); 
 	
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

function reload()
{
	window.location.href = window.location.href;
}


</script>
</cms:LoginUser>
