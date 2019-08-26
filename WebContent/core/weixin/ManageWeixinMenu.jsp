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
			<cms:CurrentSite>
			var currSiteId = '${CurrSite.siteId}';
			</cms:CurrentSite>
			
			var orgSiteArray = new Array();
			
			var siteArray;
			
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
							<a href="#">微信公众号</a> &raquo;
							<a href="#">菜单管理</a>
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
								<td style="padding: 7px 11px;" class="">
									<div class="fl">
										<a href="javascript:openCreateWxMenuDialog();" class="btnwithico"> <img src="../../core/style/icons/ui-tab-content.png" alt="" /><b>创建菜单&nbsp;</b> </a>
										<a href="javascript:sortWxMenu();" class="btnwithico"> <img src="../../core/style/default/images/sort-number.png" alt="" /><b>排序&nbsp;</b> </a>
										<a href="javascript:transferMenu();" class="btnwithico"> <img src="../../core/style/icons/document-page-next.png" alt="" /><b>同步到微信&nbsp;</b> </a>
										
										<span>(注意：同步到微信公众号将覆盖原菜单。)</span>
									</div>
									<div class="fr">
									</div>
								</td>
							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
									<form id="menuForm" name="menuForm" method="post">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="2%">
													<strong>ID</strong>
												</td>
												 
												<td width="15%">
													<strong>菜单名称</strong>
												</td>


												<td width="11%">
													<strong>类型</strong>
												</td>

												<td width="4%">
													<strong>序列</strong>
												</td>


												<td width="9%">
													<center><strong>维护</strong></center>
												</td>
											</tr>
											<script>
											var menuc = 0;
											</script>

										<cms:QueryData objName="Wxm" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxMenuForTag" var="1,-9999,">
												<tr>
													<td>
														${Wxm.btId}
													</td>

												 

													<td>
														&nbsp;${Wxm.uiName}
													</td>

													<td>
														${Wxm.btType}
													</td>
													<td>
													 
														<input type="text" id="orderFlag-${Wxm.btId}" name="orderFlag-${Wxm.btId}" class="form-input" size="4" value="${Wxm.btOrder}"></input>										
													 
														</td>

													<td>
														<div>
															<center>
																
																 											
																<a href="javascript:openEditWxMenuDialog('${Wxm.btId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;
																
																<a href="javascript:deleteMenu('${Wxm.btId}')"><img src="../../core/style/default/images/del.gif" alt="" />删除</a>&nbsp;&nbsp;&nbsp;
														
															</center>
														</div>
													</td>

												</tr>
												<cms:QueryData objName="CWxm" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxMenuForTag" var="1,${Wxm.btId},">
													<tr>
													<td>
														${CWxm.btId}
														<script>
														if('${CWxm.btLayer}' == '1')
														{
														 	menuc++;
														 }
														</script>
													</td>

												 

													<td>
														&nbsp;${CWxm.uiName}
													</td>

													<td>
														${CWxm.btType}
													</td>
													<td>
													 
														<input type="text" id="orderFlag-${CWxm.btId}" name="orderFlag-${CWxm.btId}" class="form-input" size="4" value="${CWxm.btOrder}"></input>										
													 
														</td>

													<td>
														<div>
															<center>
																
																 											
																<a href="javascript:openEditWxMenuDialog('${CWxm.btId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;
																
																<a href="javascript:deleteMenu('${CWxm.btId}')"><img src="../../core/style/default/images/del.gif" alt="" />删除</a>&nbsp;&nbsp;&nbsp;
																
															</center>
														</div>
													</td>

												</tr>
												</cms:QueryData>
											</cms:QueryData>

											<cms:Empty flag="Wxm">
												<tr>
													<td class="tdbgyew" colspan="7">
														<center>
															当前没有数据!
														</center>
													</td>
												</tr>
											</cms:Empty>
											<tr>
												<td colspan="8" class="PageBar" align="left">
													<div class="fr">

													</div>
													<div class="fl"></div>
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
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">
 

function openCreateWxMenuDialog()
{ 
	 

	$.dialog({ 
	    id : 'ocwmd',
    	title : '添加微信菜单',
    	width: '710px', 
    	height: '440px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/weixin/CreateWeixinMenu.jsp?uid='+Math.random()

	});
}


function openEditWxMenuDialog(btId)
{
	$.dialog({ 
	    id : 'oewmd',
    	title : '编辑微信菜单',
    	width: '710px', 
    	height: '440px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/weixin/EditWeixinMenu.jsp?uid='+Math.random()+'&btId='+btId

	});
}



function sortWxMenu()
{
	var url = "<cms:BasePath/>wx/sortMenu.do"+"?<cms:Token mode='param'/>";
	
	var postData =$("#menuForm").serialize();                   
 		
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
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
				    				
				                    content: '排序操作成功!',
				                    
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


function transferMenu()
{
	 

	 $.dialog({ 
   					title :'提示',
    				width: '300px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认同步菜单到微信服务器吗？将覆盖原菜单！',
                    
                    ok: function () { 
                    
                    $.dialog.tips('正在同步微信服务器...',3600000000,'loading.gif');
                    
 					var url = "<cms:BasePath/>wx/transferMenu.do?<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            
			            	var msg = eval("("+mg+")");
			            	
			               if('error:sys_err' == msg)
				        	{
				        			$.dialog({ 
							   					title :'提示',
							    				width: '160px', 
							    				height: '70px', 
							                    lock: true, 
							                    
							    				icon: '32X32/fail.png', 
							    				
							                    content: '系统发生异常！请联系维护人员!',
							                    
							                    
							                             
							                   cancel: function ()
							                   {
							                   		window.location.reload();
							                   }
				
								
							   		});
							   		
							   		 	
							   		return;
							  }
			               else
			               {
			               
			               
			               			 var jsonObj = eval("("+msg+")");
				              
				              
				             
						              if(jsonObj.errcode == 0)
						              {	
						              	 	 
						              	 $.dialog({ 
									   					title :'提示',
									    				width: '180px', 
									    				height: '70px', 
									                    lock: true, 
									                    
									    				icon: '32X32/succ.png', 
									    				
									                    content: '同步微信服务器成功!',
									                    
									                             
									                   ok: function ()
									                   {
									                   		window.location.reload();
									                   }
						
										
									   	});             	
						              
						              }
						              else
						              {
						              	$.dialog({ 
									   					title :'提示',
									    				width: '260px', 
									    				height: '70px', 
									                    lock: true, 
									                    
									    				icon: '32X32/fail.png', 
									    				
									                    content: '同步微信服务器失败!<br/>返回状态码：'+jsonObj.errcode+'<br/>错误信息：'+jsonObj.errmsg,
									                    
									                    
									                             
									                   cancel: function ()
									                   {
									                   		window.location.reload();
									                   }
						
										
									   	});
									   	 
									     	 
									   	 return;
						              }
			               
			               	    				
			               }   
			            }
			     	});	
    }, 
    cancel: true 
                    
    });


}



function deleteMenu(id)
{
	 ids = id;


	 $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除微信菜单吗？',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>wx/deleteMenu.do?btId="+ids+"&<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			       		dataType:'json',
			   			
			       		success: function(msg)
			            {     
			               if('success' == msg)
			               {			               		
			               		window.location.reload();
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
</cms:LoginUser>

