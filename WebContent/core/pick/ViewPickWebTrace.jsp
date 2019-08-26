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
						<a href="#">内容采集</a> &raquo; 采集结果记录
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
										筛选:&nbsp;
										<select id="ruleId" class="form-select" onchange="javascript:change();">
											<option value="-1">
												---- 所有采集规则结果 ----
											</option>
											<cms:CurrentSite>
												<cms:QueryData querySign="SELECT_PICK_RULE_LIST_QUERY" var="${CurrSite.siteId}">
													<option value="${SysObj.pickCfgId}">
														${SysObj.configName}
													</option>
												</cms:QueryData>
											</cms:CurrentSite>
										</select>
										&nbsp;
									</div>

									<div>
										<a href="javascript:deleteAllPickTrace();" class="btnwithico" onclick=""><img src="../style/icons/bin-full.png" width="16" height="16" /><b>清空全部记录&nbsp;</b> </a>
										<a href="javascript:deletePickTrace();" class="btnwithico" onclick=""><img src="../style/icons/bin--minus.png" width="16" height="16" /><b>删除所选&nbsp;</b> </a>

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">



												<td width="2%">
													<input class="form-checkbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												<td width="22%">
													<strong>标题</strong>
												</td>

												<td width="7%">
													<strong>采集规则</strong>
												</td>

												<td width="7%">
													<strong>目标栏目</strong>
												</td>

												<td width="5%">
													<strong>采集时间</strong>
												</td>


												<td width="4%">
													<strong>是否成功</strong>
												</td>




											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.pick.service.PickService" method="getPickWebContentTrace" objName="Tr" var="${param.ruleId},${param.pn},10">

												<tr>

													<td>
														<input class="form-checkbox" name="checkedId" value="${Tr.tid}" type="checkbox" />
													</td>
													<td>
														<img src="../../core/style/icons/script-attribute-w.png" />
														&nbsp;
														<a target="_blank" href="${Tr.targetUrl}"> <cms:if test="${empty Tr.title}">
															${Tr.targetUrl}
														</cms:if> <cms:else>
															${Tr.title}
														</cms:else> </a>
													</td>
													<td>
														<cms:QueryData objName="Rule" querySign="SELECT_PICK_RULE_SINGLE_QUERY" var="${Tr.selfRuleId}">
															${Rule.configName}
														</cms:QueryData>
													</td>

													<td>
														<cms:Class id="${Tr.classId}">
															${Class.className}
														</cms:Class>
													</td>

													<td>
														<cms:FormatDate date="${Tr.eventDT}" />
													</td>
													<td>
														<center>
															<cms:if test="${Tr.pickSucc == 1}">
																<font color="green">成功</font>
															</cms:if>
															<cms:else>
																<font color="red">失败</font>
															</cms:else>
														</center>
													</td>



												</tr>

											</cms:QueryData>
											<cms:Empty flag="Tr">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>
											<cms:PageInfo>
												<tr id="pageBarTr">
													<td colspan="8" class="PageBar" align="left">
														<div class="fr">
															<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
															<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
															<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
															<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
															<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
														</div>
														<script>
																function query(flag)
																{
																	var cp = 0;
																	
																	if('p' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage-1}');
																	}
		
																	if('n' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage+1}');
																	}
		
																	if('h' == flag)
																	{
			                                                             cp = 1;
																	}
		
																	if('l' == flag)
																	{
			                                                             cp = parseInt('${Page.pageCount}');
																	}
		
																	if(cp < 1)
																	{
			                                                           cp=1;
																	}
																	
																	if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																    var cp = parseInt(document.getElementById('pageJumpPos').value);
																    
																    if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	replaceUrlParam(window.location,'pn='+cp);
																}
															</script>
														<div class="fl"></div>
													</td>
												</tr>
											</cms:PageInfo>




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
<script>

initSelect('ruleId','${param.ruleId}');


var statusDialog;
var timeId = -1;

function openCreatePickJobDialog()
{
	$.dialog({ 
    	title :'新增采集任务',
    	width: '590px', 
    	height: '445px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/CreatePickJob.jsp'
	});
}

function openEditPickJobDialog(taskId)
{
	$.dialog({ 
    	title :'编辑采集任务',
    	width: '590px', 
    	height: '445px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/EditPickTask.jsp?taskId='+taskId
	});
}





 

function deleteAllPickTrace()
{
	$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认清空所有采集记录吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>pick/deleteAllPickTrace.do"+"?<cms:Token mode='param'/>";
 		
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
				    				icon: '32X32/succ.png', 
				    				
				                    content: '清空采集记录成功！',
				                    
				                    ok: function () { 
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
							
									    				cancel: true
									});
			               }   
			              
			            }
			     	});	
       
        
    }, 
    cancel: true 
                    
	});

}


function deletePickTrace()
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
    				
                    content: '请选择要删除的内容！', 
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
    				
                    content: '您确认删除所选采集记录吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>pick/deletePickTrace.do?ids="+ids+"&<cms:Token mode='param'/>";
 		
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
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除采集记录成功！',
				                    
				                    ok: function () { 
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
							
									    				cancel: true
									});
			               }   
			              
			            }
			     	});	
       
        
    }, 
    cancel: true 
                    
	
		});
}

function change()
{
	
	replaceUrlParam(window.location, 'ruleId='+$('#ruleId').val());
	
}

</script>
