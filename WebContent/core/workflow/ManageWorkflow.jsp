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
						<a href="#">系统管理</a> &raquo; 工作流设置
					</td>
					<td align="right">

					</td>
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
								    <!-- 暂不使用权限控制显示button -->
									<%--<cms:SystemUiRole flag="create_workFlow_jsp">
										<a href="javascript:openCreateWorkflowDialog();" class="btnwithico" onclick=""><img src="../style/icons/node-select.png" width="16" height="16" /><b>新建工作流&nbsp;</b> </a>
									</cms:SystemUiRole>
								 		--%><a href="javascript:openCreateWorkflowDialog();" class="btnwithico" onclick=""><img src="../style/icons/node-select.png" width="16" height="16" /><b>新建工作流&nbsp;</b> </a>
									 
									 <a href="javascript:openCopyCfgFromSiteDialog('workflow');"  class="btnwithico"><img src="../style/icons/document-convert.png" width="16" height="16"/><b>同步站群配置&nbsp;</b> </a>
										
									 
									<a href="javascript:deleteWorkflow();" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a> (注意:若对工作流的步骤动作进行修改,正在审核中的内容将退回到流程第一步)
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
											<td width="2%" height="30">
												<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
											</td>

											<td width="13%">
												<strong>工作流名称</strong>
											</td>
											<td width="23%">
												<strong>描叙</strong>
											</td>
											<td width="3%">
												<strong>步骤数</strong>
											</td>
										

											<td width="15%">
												<center><strong>操作</strong></center>
											</td>

										</tr>

										<cms:SystemWorkflowList>
											<cms:SystemWorkflow>
												<tr>
													<td>
														${Workflow.flowId}
													</td>
													<td>
														<input class="form-checkbox" name="checkedId" value="${Workflow.flowId}" type="checkbox" onclick="" />
													</td>

													<td style="">
														&nbsp;${Workflow.flowName}
													</td>
													<td>
														${Workflow.flowDesc}
													</td>
													<td>
														${Workflow.step}
													</td>

												

													<td>
														<center>
															<a href="javascript:openEditWorkflowDialog('${Workflow.flowId}')"><img src="../../core/style/icons/card-address.png" width="16" height="15" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;

															<a href="javascript:openWorkflowStepDialog('${Workflow.flowId}','${Workflow.flowName}')"><img src="../style/icons/node-insert-next.png" width="16" height="15" />&nbsp;步骤</a>&nbsp;&nbsp;&nbsp;&nbsp;
															<a href="javascript:openWorkflowActionDialog('${Workflow.flowId}','${Workflow.flowName}')"><img src="../style/icons/flask.png" width="16" height="15" />&nbsp;动作</a>
														</center>
													</td>
												</tr>
											</cms:SystemWorkflow>


										</cms:SystemWorkflowList>
										<cms:Empty flag="Workflow">
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

		<form id="deleteFlowForm" name="deleteFlowForm" method="post">

			<input type="hidden" id="allSelectedIds" name="allSelectedIds" />

		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



function openCreateWorkflowDialog()
{
   
	$.dialog({ 
	    id : 'ocwd',
    	title : '创建工作流基本信息',
    	width: '560px', 
    	height: '210px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/workflow/CreateWorkflow.jsp?uid='+Math.random()

	});
}

function openEditWorkflowDialog(id)
{
   
	$.dialog({ 
	    id : 'oewd',
    	title : '编辑工作流基本信息',
    	width: '560px', 
    	height: '210px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/workflow/EditWorkflow.jsp?flowId='+id+'&uid='+Math.random()

	});
}





function deleteWorkflow()
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
    				
                    content: '请选择需要删除的工作流！', 
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
    				
                    content: '您确认删除所选工作流吗？<br/>(若存在审核内容正在应用所选工作流，将忽略删除)',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>workflow/deleteWorkflow.do?allSelectedIds="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
							
									    				cancel: function () 
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



function gotoCreateWorkflowPage()
{
    window.location='CreateWorkFlow.jsp';
}


function openWorkflowStepDialog(flowId,flowName)
{
	$.dialog({ 
	    id : 'cwf',
    	title : '步骤管理 - '+flowName,
    	width: '880px', 
    	height: '450px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/workflow/ManageWorkflowStep.jsp?flowId='+flowId+'&flowName='+encodeURIComponent(encodeURIComponent(flowName))+'&uid='+Math.random()

	});
}

function openWorkflowActionDialog(flowId,flowName)
{
	$.dialog({ 
	    id : 'cwa',
    	title : '动作管理 - '+flowName,
    	width: '980px', 
    	height: '550px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/workflow/ManageWorkflowAction.jsp?flowId='+flowId+'&flowName='+encodeURIComponent(encodeURIComponent(flowName))+'&uid='+Math.random()

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
