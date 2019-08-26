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
		 
		 basePath = '<cms:BasePath/>';
		 
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
						<a href="#">系统管理</a> &raquo;
						<a href="#">接口管理</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="apiForm" name="apiForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
								<div class="fl">
							 
									 
								
								</div>
									<div >
									
										<a href="javascript:openCreateApiCfgDialog();" class="btnwithico" onclick=""><img src="../style/icons/network-wireless.png" width="16" height="16" /><b>新增接口&nbsp;</b> </a>
										<a href="javascript:initApi();" class="btnwithico"><img src="../style/icons/magnifier-zoom-in.png" width="16" height="16" /><b>自动识别&nbsp;</b> </a>
										<a href="javascript:deleteApiCfg();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
									<div class="fr">

									

										<select id="searchAction" class="form-select">
											 
											<option value="name" >
												接口名称&nbsp;&nbsp;&nbsp;
											</option>
											<option value="flow">
												访问Flow
											</option>
										 

										</select>
										<input id="searchKey" name="query" size="25" maxlength="60" class="form-input" style="vertical-align:top;" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
										<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />

									</div>

									<div class="fr">

									</div>
								</td>
							</tr>



							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="99.8%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

											<td width="1%" >
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkIds',this);" />
											</td>

											 

												<td width="8%">
													<strong>接口名</strong>
												</td>

												<td width="10%">
													<strong>访问路径</strong>
												</td>




												<td width="2%">
													<strong>Token</strong>
												</td>

												<td width="2%">
													<strong>加密</strong>
												</td>
												
												<td width="2%">
													<strong>POST</strong>
												</td>
												
												<td width="2%">
													<strong>扩展</strong>
												</td>

												 

												<td width="4%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

											<cms:CurrentSite>
												<cms:QueryData service="cn.com.mjsoft.cms.appbiz.service.AppbizService" method="getSysApiCfgForTag" objName="ApiCfg" var=",${param.pn},10,${param.sa},${param.key}">
													<tr id="tr-${status.index}">
														<td>
															<input type="checkbox" name="checkIds" value="${ApiCfg.apiId}"    />
														</td>
														 

														<td>
															${ApiCfg.apiName}
														</td>

														<td>
															${ApiCfg.flowPath}
														</td>

														 

														<td>
															<cms:if test="${ApiCfg.mustTok==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>

														<td>
															<cms:if test="${ApiCfg.mustEnc==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>
														
														<td>
															<cms:if test="${ApiCfg.reqMethod=='post'}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>
														
														<td>
															<cms:if test="${ApiCfg.mustSecTok==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														</td>

														 

														<td>
															<div>
																<center>																	
																	<a href="javascript:openEditApiCfgDialog('${ApiCfg.apiId}');"><img src="../../core/style/icon/card-address.png" width="16" height="16" />&nbsp;编辑</a><%--&nbsp;&nbsp;&nbsp;
															 		<a href="javascript:openEditApiParamDialog('${ApiCfg.apiId}');"><img src="../../core/style/icons/script-code.png" width="16" height="16" />&nbsp;参数</a>&nbsp;&nbsp;&nbsp;
																    <a href="javascript:openEditApiParamDialog('${ApiCfg.apiId}');"><img src="../../core/style/icons/beaker.png" width="16" height="16" />&nbsp;测试</a>&nbsp;&nbsp;&nbsp;
																
															 	--%></center>
															</div>
														</td>
													</tr>

												</cms:QueryData>
												<cms:Empty flag="ApiCfg">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
											</cms:CurrentSite>
											<tr id="pageBarTr">
												<cms:PageInfo>
													<td colspan="18" class="PageBar" align="left">
														<div class="fr">
															<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()"> </span>
															<span class="page">[<a href="javascript:page('h');">首页</a>]</span>
															<span class="page">[<a href="javascript:page('p');">上一页</a>]</span>
															<span class="page">[<a href="javascript:page('n');">下一页</a>]</span>
															<span class="page">[<a href="javascript:page('l');">末页</a>]</span>&nbsp;
														</div>
														<script>
														function page(f)
														{
																if('h' == f)//首页
																{					
																	replaceUrlParam(window.location, 'pn=1');
																}
																else if('n' == f)
																{
																	replaceUrlParam(window.location, 'pn=${Page.currentPage+1}');
																}
																else if('p' == f)
																{
																	replaceUrlParam(window.location, 'pn=${Page.currentPage-1}');
																}else if('l' == f)
																{
																	replaceUrlParam(window.location, 'pn=${Page.pageCount}');
																}
														
														}
	
													</script>
												</cms:PageInfo>

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

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('status','${param.status}');

initSelect('searchAction','${param.sa}');


if('${param.sa}' == '')
{
	document.getElementById('searchKey').value = "";
}

function search()
{
	var sa = document.getElementById('searchAction').value;
	
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	if(key.trim() == '')
	{
		window.location='ManageApiCfg.jsp';
	}
	else
	{
		window.location='ManageApiCfg.jsp?sa='+sa+'&key='+encodeData(key);
	}
}



function initApi()
{
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认自动识别系统接口吗？',
                    
                    ok: function () 
                    { 
						var tip = $.dialog.tips('正在初始化系统接口配置...',3600000000,'loading.gif'); 
					                   
					
						var url = "<cms:BasePath/>appbiz/searchAndInitApi.do"+"?<cms:Token mode='param'/>";
					    
					    var postData = "";
					
					 	$.ajax({
					      	type: 'POST',
					       	url: url,
					       	data:postData,
					   
					       	success: function(mg)
					        {        
					        	var msg = eval("("+mg+")");
					        	
					            if('success' == msg)
								               {
								               	 
								               		
								               		$.dialog({ 
									   					title :'提示',
									    				width: '140px', 
									    				height: '60px', 
									                    lock: true, 
									    				icon: '32X32/succ.png', 
									    				
									                    content: '初始化系统接口成功!',
									                    
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
					 cancel: function () 
				    { 
						window.location.reload();
					}
	})
	 
	
	
	
}



function openCreateApiCfgDialog()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许操作！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	$.dialog({ 
		id:'ocstd',
    	title :'增加接口配置',
    	width: '590px', 
    	height: '380px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/api/CreateApiCfg.jsp'
	});
}

function openEditApiCfgDialog(id)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许操作！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	$.dialog({ 
		id:'oestd',
    	title :'编辑接口配置',
    	width: '590px', 
    	height: '380px',
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/api/EditApiCfg.jsp?id='+id
	});
}

function openEditApiParamDialog(id)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许操作！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	$.dialog({ 
		id:'oeptd',
    	title :'编辑接口参数',
    	width: '490px', 
    	height: '480px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/api/EditApiParams.jsp?id='+id
	});
}

function deleteApiCfg()
{
	 

	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许操作！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	var cidCheck  = document.getElementsByName('checkIds');
		
		 
		
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
	    				
	                    content: '请选择要删除的接口！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
	
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除系统接口配置吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>appbiz/deleteApiCfg.do?ids="+ids+"&<cms:Token mode='param'/>";
                    

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
				    				width: '180px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除系统接口配置成功!',
				                    
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
    				cancel: function () 
				                    { 
				                    	window.location.reload();
				                    }
   	});


}

</script>
