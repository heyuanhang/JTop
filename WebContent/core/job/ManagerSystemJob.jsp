<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil.js"></script>
		<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script>
	 //去掉点击链接 虚线边框
      window.onload=function()
      {
     for(var i=0; i<document.links.length; i++)
     document.links[i].onclick=function(){this.blur()}
     }

	 

　

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
						<a href="#">系统管理</a> &raquo; 管理计划任务
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
									<select name="date" id="in4">
										<option value="m">
											所有计划任务 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="m">
											系统任务 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="h">
											静态化发步&nbsp;&nbsp;&nbsp;
										</option>
										<option value="d">
											网络采集&nbsp;&nbsp;&nbsp;
										</option>
										<option value="moth">
											数据库采集&nbsp;&nbsp;&nbsp;
										</option>
										<option value="moth">
											数据库备份&nbsp;&nbsp;&nbsp;
										</option>
									</select>
										&nbsp;&nbsp;
									
								</div>
								<div >
									<a href="javascript:openCreateJobDialog();" class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16" /><b>新建任务&nbsp;</b> </a>
									<a href="javascript:;" class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16" /><b>编辑&nbsp;</b> </a>
									<a href="javascript:;" class="btnwithico"><img src="../style/blue/icon/applications.png" width="16" height="16" /><b>复制&nbsp;</b> </a>

								</div>
							</td>

						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="3%" height="30">
												<input class="inputCheckbox" value="*" onclick="" type="checkbox" />
											</td>

											<td width="12%">
												<strong>任务名称</strong>
											</td>
											<td width="32%">
												<strong>描叙</strong>
											</td>
											<td width="3%">
												<strong>步骤</strong>
											</td>
											<td width="8%">
												<strong>创建者</strong>
											</td>
											<td width="3%">
												<strong>越级</strong>
											</td>
											<td width="3%">
												<strong>会审</strong>
											</td>
											<td width="7%">
												<strong>修改时间</strong>
											</td>
										</tr>

										<cms:SystemWorkflowList>
											<cms:SystemWorkflow>
												<tr>

													<td>
														<input class="inputCheckbox" value="${Workflow.actId}" type="checkbox" onclick="javascript:regFlowId(this)" />
													</td>

													<td style="">
														<span title="">${Workflow.flowName}</span>
													</td>
													<td>
														${Workflow.actDesc}
													</td>
													<td>
														${Workflow.step}
													</td>

													<td>
														${Workflow.creator}
													</td>
													<td>
														<cms:if test="${Workflow.bypassFlag==1}">
															<center>
																<img src="../style/blue/icon/ok_status_small.png" />
															</center>
														</cms:if>
													</td>
													<td>
														<cms:if test="${Workflow.conjunctFlag==1}">
															<center>
																<img src="../style/blue/icon/ok_status_small.png" />
															</center>
														</cms:if>
													</td>
													<td title="09-05-21 11:51">
														${Workflow.systemHandleTime}
													</td>
												</tr>
											</cms:SystemWorkflow>


										</cms:SystemWorkflowList>
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
var selectedIdMap = new HashMapJs();

function openCreateJobDialog()
{
	$.dialog({ 
    	title :'添加任务',
    	width: '740px', 
    	height: '430px', 
    	lock: true, 
        max: false, 
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/job/CreateScheduleJob.jsp'
	
	  
	});
}




</script>
