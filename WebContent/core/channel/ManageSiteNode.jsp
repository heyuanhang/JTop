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
						<a href="#">站群节点管理</a>
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="snForm" name="snForm" method="post">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
			<tr>
				<td class="mainbody" align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
									<a href="javascript:openCreateSiteNodeDialog();" class="btnwithico"> <img src="../../core/style/icons/globe--plus.png" alt="" /><b>添加站点&nbsp;</b> </a>
									
									<a href="javascript:sortSite();" class="btnwithico" onclick=""><img src="../style/default/images/sort-number.png" width="16" height="16" /><b>应用排序&nbsp;</b> </a>
									
									&nbsp;<span>(以下站点列表为当前登录管理员所属机构已管理站点)</span>
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
												<strong>站点名称</strong>
											</td>

											<td width="7%">
												<strong>访问标识</strong>
											</td>

											<td width="21%">
												<strong>域名</strong>
											</td>
											
											<td width="7%">
												<strong>序列</strong>
											</td>

											<td width="4%">
												<strong>编码</strong>
											</td>

											<td width="8%">
												<center><strong>操作</strong></center>
											</td>
										</tr>


										<cms:SystemOrgSite orgId="${Auth.orgIdentity}">
											<tr>
												<td>
													${OrgSite.siteId}
												</td>
												 

												<td>
													${OrgSite.siteName}
												</td>

												<td>
													${OrgSite.siteFlag}
												</td>
												<td>
													${OrgSite.siteUrl}
												</td>
												
												<td>
													
													<input type="text" id="orderFlag-${OrgSite.siteId}" name="orderFlag-${OrgSite.siteId}" class="form-input" size="4" value="${OrgSite.orderFlag}"></input>
											
												</td>
												
												<td>
													${OrgSite.templateCharset}
												</td>
												<td>
													<div>
														<center>
															<img src="../../core/style/icons/card-address.png" width="16" height="16" />
															<a href="javascript:editCreateSiteNodeDialog('${OrgSite.siteId}');">编辑</a>&nbsp; &nbsp;
															<img src="../../core/style/default/images/del.gif" width="16" height="16" />
															<a href="javascript:deleteSiteNode('${OrgSite.siteId}');">删除 </a>&nbsp;&nbsp;&nbsp;
														</center>
													</div>
												</td>

											</tr>

										</cms:SystemOrgSite>
										
										<cms:Empty flag="OrgSite">
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
		
		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	

	</body>
</html>

<script type="text/javascript">

function openCreateSiteNodeDialog()
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
	    				
	                    content: '只有机构主管允许添加站点！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog({ 
	    id : 'ocsnd',
    	title : '创建站群节点',
    	width: '620px', 
    	height: '476px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/channel/CreateSiteNode.jsp?uid='+Math.random()
	});
}

function editCreateSiteNodeDialog(id)
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
	    				
	                    content: '只有机构主管允许编辑站点！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog({ 
	    id : 'ecsnd',
    	title : '编辑站群节点',
    	width: '620px', 
    	height: '476px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/channel/EditSiteNode.jsp?siteId='+id+'&uid='+Math.random()
	});
}

function deleteSiteNode(id)
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
	    				
	                    content: '只有机构主管允许删除站点！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	if(id == '')
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
	    				
	                    content: '请选择需要删除的站群节点！', 
	       cancel: true 
	                    
		  });
		  return;
		}
	}
	else
	{
		ids = id;
	}
	
	$.dialog({ 
   					title :'提示',
    				width: '240px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选站群节点吗？<br/>(站点所属所有内容将被删除)',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>site/deleteSiteGroup.do?ids="+ids+"&<cms:Token mode='param'/>";
                    

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
				                    	window.parent.window.document.getElementById('top').src = 'top.jsp?notReload=true';
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


function sortSite()
{
	var url = "<cms:BasePath/>site/sortSite.do"+"?<cms:Token mode='param'/>";
	
	var postData =$("#snForm").serialize();
                    
 		
 				
 		
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


</script>
</cms:LoginUser>
