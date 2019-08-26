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
						<a href="#">静态化发布</a> &raquo; 发布任务管理
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="jobForm" name="jobForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openAddPublishJobDialog();" class="btnwithico" onclick=""><img src="../style/icons/task--plus.png" width="16" height="16" /><b>增加发布任务&nbsp;</b> </a>
										<a href="javascript:shutdownPSJob();" class="btnwithico" onclick=""><img src="../style/icons/light-bulb-off.png" width="16" height="16" /><b>停用&nbsp;</b> </a>
										<a href="javascript:startupPSJob();" class="btnwithico" onclick=""><img src="../style/icons/light-bulb.png" width="16" height="16" /><b>启用&nbsp;</b> </a>
										<a href="javascript:deletePSJobs();" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="1%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>


												<td width="14%">
													<strong>发布任务描叙</strong>
												</td>


												<td width="3%">
													<strong>上次执行</strong>
												</td>

												<td width="2%">
													<strong>状态</strong>
												</td>

												<td width="6%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.schedule.service.ScheduleService" method="getScheduleJobDetailTag" objName="Sch" var=",PublishSiteHtmlContentJob" >
												<tr>
													<td>
														${Sch.jobId}
													</td>
													<td>
														<input class="inputCheckbox" id="checkedId" name="checkedId" value="${Sch.jobId}" type="checkbox"   />
													</td>

													<td>
														${Sch.jobDesc}
													</td>
													<td>
														<cms:if test="${ empty Sch.lastExcuteTime}">
															暂未执行
														</cms:if>
														<cms:else>
															<cms:FormatDate date="${Sch.lastExcuteTime}" />

														</cms:else>
													</td>
													<td>
														<cms:if test="${Sch.useState == 1}">
															<font color="green">启用</font>
														</cms:if>
														<cms:else>
															<font color="red">停用</font>
														</cms:else>

													</td>

													<td>
														<center>
															<div>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditPublishJobDialog('${Sch.jobId}')">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deletePSJob('${Sch.jobId}')">删除</a>&nbsp; &nbsp; </span>
															</div>
														</center>
													</td>
												</tr>


											</cms:QueryData>

											<cms:Empty flag="Sch">
												<tr>
													<td class="tdbgyew" colspan="7">
														<center>
															当前没有数据!
														</center>
													</td>
												</tr>
											</cms:Empty>
											<tr>
												<td colspan="7">

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
<script>

function openAddPublishJobDialog()
{
	$.dialog({ 
    	title :'新增内容发布任务',
    	width: '690px', 
    	height: '370px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/deploy/CreatePublishJob.jsp'
	});
}

function openEditPublishJobDialog(jobId)
{
	$.dialog({ 
    	title :'编辑内容发布任务',
    	width: '690px', 
    	height: '370px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/deploy/EditPublishJob.jsp?jobId='+jobId
	});
}

function shutdownPSJob()
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
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要立即停止的任务！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认立即停止所选发布任务吗？',
                    
                    ok: function () 
                    { 
                    
                  
                    var url = "<cms:BasePath/>job/shutdownJob.do?ids="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
							    				width: '140px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/i.png', 
							    				
							                    content: '停止任务成功！', 
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


function startupPSJob()
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
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要立即启动的任务！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
   					title :'提示',
    				width: '190px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认立即启动所选发布任务吗？',
                    
                    ok: function () 
                    { 
                    
                  
                    var url = "<cms:BasePath/>job/startupJob.do?ids="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
							    				width: '140px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/i.png', 
							    				
							                    content: '启动任务成功！', 
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


function deletePSJobs()
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
    				
                    content: '请选择需要删除的任务！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	deletePSJob(ids);

}


function deletePSJob(ids)
{
	

	$.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选发布任务吗？',
                    
                    ok: function () 
                    { 
                    
                  
                    var url = "<cms:BasePath/>job/deletePSJob.do?ids="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
							    				width: '140px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/i.png', 
							    				
							                    content: '删除任务成功！', 
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


</script>
