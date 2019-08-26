<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js"></script>
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
						<a href="#">站点维护</a> &raquo; 发布点管理
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
										<a href="javascript:openAddPublishGateway();" class="btnwithico" onclick=""><img src="../style/icons/folder-network.png" width="16" height="16" /><b>增加发布点&nbsp;</b> </a>
										<a href="javascript:openCopyCfgFromSiteDialog('pub');"  class="btnwithico"><img src="../style/icons/document-convert.png" width="16" height="16"/><b>同步站群配置&nbsp;</b> </a>
										
										<a href="javascript:deletePublishGateway();" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a> &nbsp;&nbsp;(对于每个站点，同一类型的文件分发点只允许一个在使用状态)
										
										
									</div>
									
									<div class="fr">
										<a href="javascript:changeEditorResUrl('urlToUri');" class="btnwithico" onclick=""><img src="../style/icons/chain-unchain.png" width="16" height="16" /><b>编辑器URL到URI&nbsp;</b> </a>
										<a href="javascript:changeEditorResUrl('uriToClo');" class="btnwithico" onclick=""><img src="../style/icons/chain-unchain.png" width="16" height="16" /><b>编辑器URI到CLOUD&nbsp;</b> </a> 
										<a href="javascript:changeEditorResUrl('cloToUri');" class="btnwithico" onclick=""><img src="../style/icons/chain-unchain.png" width="16" height="16" /><b>编辑器CLOUD到URI&nbsp;</b> </a> 
									
									
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												<td width="8%">
													<strong>分发点</strong>
												</td>

												<td width="8%">
													<strong>目标服务器</strong>
												</td>

												<td width="3%">
													<strong>类型</strong>
												</td>

												<td width="2%">
													<strong>使用状态</strong>
												</td>

												<td width="8%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>


											<cms:SystemGateway>
												<tr>
													<td>
														${Gateway.gatewayId}
													</td>
													<td>
														<input class="form-checkbox" name="checkedId" value="${Gateway.gatewayId}"  type="checkbox" />
													</td>
													<td>
														${Gateway.name}
													</td>
													<td>
														<cms:if test="${Gateway.targetServerId != -1}">
															<cms:DispenseServer id="${Gateway.targetServerId}">
											
															${Server.serverName}
												
															</cms:DispenseServer>
														</cms:if>
														<cms:else>
															<cms:QueryData service="cn.com.mjsoft.cms.site.service.SiteGroupService" method="getCloudCfgForTag" var="${Gateway.targetCloudId}," objName="Server"  >
					
																${Server.typeStr} - ${Server.bucketName} 
															</cms:QueryData>
														</cms:else>
														

													</td>
													<td>
														<cms:if test="${Gateway.transfeType == 1}">
																静态页
															</cms:if>
														<cms:elseif test="${Gateway.transfeType == 2}">
																图片文件
															</cms:elseif>
														<cms:elseif test="${Gateway.transfeType == 3}">
																视频媒体
															</cms:elseif>
														<cms:elseif test="${Gateway.transfeType == 4}">
																附件文件
															</cms:elseif>
														<cms:elseif test="${Gateway.transfeType == 0}">
																站点资源
															</cms:elseif>
													</td>

													<td>
														<cms:if test="${Gateway.useState == 1}">
															<img src="../style/icon/tick.png" />
														</cms:if>
														<cms:elseif test="${Gateway.useState == 0}">
															<img src="../style/icon/del.gif" />
														</cms:elseif>

													</td>

													<td>
														<div>
															<center>
																<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditPublishGateway('${Gateway.gatewayId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:transferData('${Gateway.gatewayId}');"><img src="../../core/style/icons/document-page-next.png" width="16" height="16" />&nbsp;同步至服务器</a> </span>
															</center>
														</div>
													</td>

												</tr>
											</cms:SystemGateway>

											<cms:Empty flag="Gateway">
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

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

		
function openAddPublishGateway()
{
	$.dialog({ 
    	title :'新增内容分发点',
    	width: '660px', 
    	height: '370px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/deploy/AddPublishGateway.jsp'
	});
}

function openEditPublishGateway(id)
{
	$.dialog({ 
    	title :'编辑内容分发点',
    	width: '660px', 
    	height: '370px',  
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/deploy/EditPublishGateway.jsp?id='+id
	});
}

function deletePublishGateway()
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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要删除的分发点！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选分发点吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>site/deletePublishGateway.do?ids="+ids+"&<cms:Token mode='param'/>";
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	 var msg = eval("("+mg+")");
			            	 
			               if('success' == msg)
			               {
			               		$.dialog(
							    { 
							   					title :'提示',
							    				width: '190px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/succ.png', 
							    				
							                    content: '除所选分发点成功!',
					
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

function transferData(id)
{
	var tip = $.dialog.tips('正在执行同步...',9600000000,'loading.gif');

	var url = "<cms:BasePath/>site/transferAllData.do?gwId="+id+"&<cms:Token mode='param'/>";
 	var postData = '';
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(mg)
        {     
       		 var msg = eval("("+mg+")");
       		 
       	   tip.close();
       	   
           if('success' == msg)
           {
           		$.dialog({ 
	   					title :'提示',
	    				width: '170px', 
	    				height: '60px', 

	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '同步文件到服务器完成！', 
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
							
									    				cancel: true
									});
           }   
        }
 	});	

}


function changeEditorResUrl(m)
{
	var tip = $.dialog.tips('正在执行,请耐心等待...',9600000000,'loading.gif');

	var url = "<cms:BasePath/>site/changeEditorRes.do?mode="+m+"&<cms:Token mode='param'/>";
 	var postData = '';
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(msg)
        {     
       		var msgObj = eval("("+msg+")");
       	   tip.close();
       
           if('success' == msgObj)
           {
           		$.dialog({ 
	   					title :'提示',
	    				width: '220px', 
	    				height: '60px', 

	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '编辑器文件资源路径转换完成！', 
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
							
									    				cancel: function(){ 
								      						 window.location.reload();
								    					} 
									});
           }   
        }
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
