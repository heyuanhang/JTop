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
		<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil.js"></script>
		<script type="text/javascript" src="../javascript/page/pagination.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

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
						<a href="#">内容审核</a> &raquo;
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
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
									<a id="applyAudit" href="javascript:applyAudit();" class="btnwithico"> <img id="applyAuditImg" src="../../core/style/default/images/doc_add.png" alt="" /><b id="applyAudit-b">申请审核权&nbsp;</b> </a>
									<a href="javascript:readContent();" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_pub.png" alt="" /><b>阅稿&nbsp;</b> </a>
									<a href="javascript:;" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_move.png" alt="" /><b>流程日志&nbsp;</b> </a>
									<a href="javascript:;" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_bro.png" alt="" /><b>预览&nbsp;</b> </a>

									<a href="javascript:;" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
									<select id="auditFilter" name="auditFilter" onchange="javascript:filterAction(this.value);">
										<option value="1">
											待我审核的内容
										</option>
										<option value="2">
											所有未审核内容
										</option>
										<option value="3">
											我的草稿
										</option>
										<option value="4">
											我的被退稿件
										</option>
									</select>
									关键字:&nbsp;
									<input id="query" name="query" size="20" maxlength="60" />
									<input onclick="" value="搜索" class="btn-1" type="button" />
								</div>
								<div class="fr">
								</div>




							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<cms:SPContentList personalReject="${param.personalReject}">
										<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="4%" height="30">
													<strong>ID</strong>
												</td>
												<td width="3%" height="30">
													<input type="checkbox" name="checkbox" value="checkbox" />
												</td>
												<td width="32%">
													<strong>标题</strong>
												</td>

												<td width="8%">
													<strong>投稿人</strong>
												</td>
												<td width="6%">
													<strong>当前步骤</strong>
												</td>
												<td width="8%">
													<strong>当前审核者</strong>
												</td>
												<td width="8%">
													<strong>添加时间</strong>
												</td>

												<td width="8%">
													<strong>状态</strong>
												</td>
											</tr>

											<cms:SPContent>

												<tr height="20">
													<td>
														${SPInfo.contentId}
													</td>
													<td>
														<input type="checkbox" name="checkContent" value="${SPInfo.contentId}" id="check${SPInfo.contentId}" onclick="javascript:regId(this,'${SPInfo.modelId}','${SPInfo.classId}','${SPInfo.possessStatus}');" />
													</td>
													<td>
														<div align="left">
															<span class="STYLE1">&nbsp;<a target="_blank" href="<cms:ContentLink actParam='id=${SPInfo.contentId}'/>">[预览]</a> ${SPInfo.title }</span></a>
														</div>
													</td>
													<td>
														${SPInfo.creator}
													</td>
													<td>
														${SPInfo.stepNodeName}
													</td>
													<td>
														${SPInfo.currentAuditUser}
													</td>
													<td>
														${SPInfo.addTime}
													</td>
													<td>
														<div align="center">
															${SPInfo.operStatusStr}
														</div>
													</td>

												</tr>
											</cms:SPContent>


											<tr>
												<td colspan="8" class="PageBar" align="left">
													<div class="fr">
														<page:page />
													</div>
													<div class="fl"></div>
												</td>
											</tr>

										</table>
									</cms:SPContentList>
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
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">

initSelect('auditFilter','${param.filterBy}');

var currentSelectId = -1;
var currentModelId = -1;
var currentClassId = -1;



function regId(check,modelId,classId,possessStatus)
{
   if(check.checked==true)
   {
      
      //alert(currentSelectId);
      var pervCheck = document.getElementById("check"+currentSelectId);
     //  alert(pervCheck);
      if(pervCheck != null)
      {
      	pervCheck.checked=false;
      }
      currentSelectId=check.value;
      currentModelId = modelId;
      currentClassId = classId;
      
      if(possessStatus == 1)
	  {
	  	disableAnchorElement('applyAudit',true);
	  	document.getElementById('applyAudit').style.cursor="not-allowed";
	  	document.getElementById('applyAudit-b').style.cursor="not-allowed";
	  	
	  }
   }
   else
   {
      currentSelectId = -1;
      currentModelId = -1;
      currentClassId = -1;
      
      disableAnchorElement('applyAudit',false);
      document.getElementById('applyAudit').style.cursor="default";
	  document.getElementById('applyAudit-b').style.cursor="default";
   }
   
   
   //alert(currentSelectId);
} 




//以下为排序筛选操作

function filterAction(filterValue)
{
    var appearStartDate = document.getElementById('appearStartDate').value;
    var appearEndDate = document.getElementById('appearEndDate').value;
    //alert(appearStartDate);
    
	if('top' == filterValue)
	{
		window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&filterBy=top';
	}
	else if('commend' == filterValue)
	{
		window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&filterBy=commend';
	}
	else if('hot' == filterValue)
	{
		window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&filterBy=hot';
	}
	else if('homeImage' == filterValue)
	{
		window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&filterBy=homeImage';
	}
	else
	{
		window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate;
	}

}


/*
*以下为工作流审核操作
*/
function applyAudit()
{
	if(-1 == currentSelectId)
	{
		alert("请选择需申请审核内容!");
		return;
	}
	
	window.location='../../workflow/applyAudit.do?contentId='+currentSelectId;
}


function readContent()
{
	if(currentSelectId == -1)
	{
		alert("请选择要审核的内容!");
		return;
	}
	window.location='EditUserDefineModelContent.jsp?modelId='+currentModelId+'&contentId='+currentSelectId+"&classId="+currentClassId;
}


function filterAction(value)
{
	if(value==4)
	{
		window.location='AuditContentList.jsp?personalReject=true';
	}



}



</script>

