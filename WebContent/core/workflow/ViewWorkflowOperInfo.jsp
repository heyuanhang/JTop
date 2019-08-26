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

		
		<div style="height:1px"></div>
		<form  method="post">
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							
							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												
												<td width="2%">
													<strong>序号</strong>
												</td>
												
												<td width="10%">
													<strong>操作人</strong>
												</td>
												
												<td width="8%">
													<strong>执行动作</strong>
												</td>
												
												<td width="8%">
													<strong>上一步骤</strong>
												</td>
												
												<td width="8%">
													<strong>当前步骤</strong>
												</td>
												
												
												
												<td width="7%">
													<strong>执行时间</strong>
												</td>

												
												
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.workflow.service.WorkflowService" method="getWorkflowOperInfoInfoList" objName="SI" var="${param.cid},1,120" >

												<tr>
													<td>
														${status.size-status.index}.
													</td>
													
													<td>
														${SI.pUserName}
													</td>
													
													<td>
														<cms:if test="${SI.actionId == -1}">
															进入工作流
														</cms:if>
														<cms:else>
															<cms:SystemWorkflowAction actId="${SI.actionId}">														
															${Action.passActionName}
															</cms:SystemWorkflowAction>
														</cms:else>
														
													</td>
													
													<td>
														<cms:if test="${SI.fromStepId == -3}">
															无步骤信息
														</cms:if>
														<cms:else>
															<cms:SystemWorkflowStep step="${SI.fromStepId}">
																${Step.stepNodeName}
															</cms:SystemWorkflowStep>
														</cms:else>
													</td>
													
													<td>
														<cms:if test="${SI.toStepId == -2}">
															开始
														</cms:if>
														<cms:else>
															<cms:SystemWorkflowStep step="${SI.toStepId}">
																${Step.stepNodeName}
															</cms:SystemWorkflowStep>
														</cms:else>
													</td>
													
												<%--	<td>

														<div style="height:3px"></div>
														<textarea readonly  style="height:50px;width:390px" class="form-textarea">
														
															<cms:if test="${SI.fromStepId==-3 && SI.toStepId==-2 && SI.actionId == -1}">
																
															
															</cms:if>
															<cms:else>
															
															
															</cms:else>
														
														
														</textarea>
														<div style="height:3px"></div>
													</td>--%>
													
													<td>
														&nbsp;<cms:FormatDate date="${SI.eventDT}" />
													</td>
													
												
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="SI">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前内容没有工作流记录!
																</center>
															</td>
														</tr>
											</cms:Empty>


										</table>
									</div>

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






</script>
