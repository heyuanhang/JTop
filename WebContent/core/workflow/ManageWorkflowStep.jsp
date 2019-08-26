<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

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
						<a href="#">系统管理</a> &raquo; 工作流设置 &raquo; 步骤管理
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td style="padding: 6px 6px;" class="">
								<div class="fl">
									<a href="javascript:javascript:openCreateFlowStepDialog('${param.flowId}','<cms:DecodeParam enc="utf-8" str="${param.flowName}"/>');" class="btnwithico" onclick=""><img src="../style/icons/node-insert-next.png" width="16" height="16" /><b>新增工作流步骤&nbsp;</b> </a>
									<a href="javascript:deleteWorkflowStep();" class="btnwithico"><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>

								</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="5%" height="30">
												<strong>步骤编号</strong>
											</td>
											<td width="2%" height="30">
												<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
											</td>

											<td width="12%">
												<strong>步骤名称</strong>
											</td>
											<td width="27%">
												<strong>描叙</strong>
											</td>

											<td width="3%">
												<strong>回避</strong>
											</td>
											
											<td width="3%">
												<strong>部门</strong>
											</td>

											<td width="11%">
												<center><strong>操作</strong></center>
											</td>

										</tr>

										<cms:SystemWorkflowStep flowId="${param.flowId}">
											<tr>
												<td>
													<center>
														${status.index+1}.
													</center>
												</td>
												<td>
													<cms:if test="${Step.isStart == 1}">
														<input disabled name="checkedId" class="form-checkbox" value="" type="checkbox" />
													</cms:if>
													<cms:else>
														<input name="checkedId" class="form-checkbox" value="${Step.stepId}" type="checkbox" />
													</cms:else>

												</td>

												<td style="">
													${Step.stepNodeName}
												</td>
												<td>
													<cms:SubString len="16" tail="....." str="${Step.stepDesc}" />
												</td>


												<td>
													<cms:if test="${Step.avoidFlag==1}">
														<center>
															<img src="../style/blue/icon/ok_status_small.png" />
														</center>
													</cms:if>
												</td>
												
												<td>
													<cms:if test="${Step.orgMode==1}">
														<center>
															<img src="../style/blue/icon/ok_status_small.png" />
														</center>
													</cms:if>
												</td>

												<td>
													<center>
														<a href="javascript:openEditFlowStepDialog('${Step.flowId}','${Step.stepId}','<cms:DecodeParam enc="utf-8" str="${param.flowName}"/>','${Step.stepNodeName}')"><img src="../../core/style/icons/card-address.png" width="16" height="15" /> 编辑步骤</a>
													</center>
												</td>
											</tr>
										</cms:SystemWorkflowStep>
										<cms:Empty flag="Step">
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

		<div style="height:30px"></div>
		
		<div class="breadnavTab" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"></img><b>取消&nbsp;</b> </a>
					</div>					 
				</tr>
			</table>
		</div>

		<form id="deleteFlowForm" name="deleteFlowForm" method="post">

			<input type="hidden" id="allSelectedIds" name="allSelectedIds" />

		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">
var selectedIdMap = new HashMapJs();

var api = frameElement.api, W = api.opener;


function openEditFlowStepDialog(flowId,step,flowName,stepName)
{
	
	
	W.$.dialog({ 
	    id : 'mefs',
    	title :'编辑步骤 - '+flowName+' - '+stepName,
    	width: '680px', 
    	height: '450px', 
    	parent:api,
        lock:true,
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/workflow/EditWorkflowStep.jsp?flowId='+flowId+'&stepId='+step+'&random='+Math.random()
	
	  
	});
}

function openCreateFlowStepDialog(flowId,flowName)
{
	W.$.dialog({ 
	    id : 'mcfs',
    	title :'创建步骤 - '+flowName,
    	width: '680px', 
    	height: '450px', 
    	parent:api,
        lock:true,
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/workflow/CreateWorkflowStep.jsp?flowId='+flowId+'&random='+Math.random()
	
	  
	});
}




function close()
{
	api.close();
	W.location.reload();
}




function regFlowId(box)
{
	if(box.checked==true)
	{
		selectedIdMap.put(box.value,box.value);
	}
	else
	{
		selectedIdMap.remove(box.value);
	}
}

function deleteWorkflowStep()
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
	   W.$.dialog({ 
   					title :'提示',
    				width: '190px', 
    				height: '60px', 
                    lock: true, 
                    parent: api,
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要删除的工作流步骤！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	W.$.dialog({ 
   					title :'提示',
    				width: '260px', 
    				height: '60px', 
                    lock: true, 
                    parent: api,
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选工作流步骤吗？<br/>(若存在审核内容正在应用所选工作流，将退回原点)',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>workflow/deleteWorkflowStep.do?flowId=${param.flowId}&ids="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            
							var msg = eval("("+mg+")");
							
			               if('success' == msg)
			               {
		
			               		
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '160px', 
				    				height: '60px', 
				                    lock: true, 
				                    parent: api,
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
			               	       W.$.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     parent:api,
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

</script>
