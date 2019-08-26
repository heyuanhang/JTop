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
		
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />


		<script>
			//表格变色
			$(function()
			{ 
		   		$("[name=showlist] tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});  
			
			 $(function()
			 {
	        	//图片查看效果
	 		  	loadImageShow();
	 		 })
      </script>
	</head>
	<body>

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">投票与问卷</a> &raquo;
						<a href="#">调查问卷管理</a> &raquo;
						<a href="#"><cms:SystemSurverGroup groupId="${param.groupId}">${Group.questName}</cms:SystemSurverGroup> </a>
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>

		<!-- 主内容开始 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
			<tr>
				<td class="mainbody" align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
									<a href="javascript:openCreateQuestionDialog();" class="btnwithico"> <img src="../../core/style/icons/script-stamp.png" alt="" /><b>增加调查选项&nbsp;</b> </a>
									<a href="javascript:deleteCheckedSurvey();" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
									<a href="javascript:javascript:gotoManageQuestionBaseInfoPage();" class="btnwithico"> <img src="../../core/style/icon/arrow-return-180-left.png" alt="" /><b>返回调查管理&nbsp;</b> </a>
								</div>
								<div>

								</div>
								<div class="fr">
								</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">


								<!-- 内容1 s -->
								<table class="listdate-high" id="" width="99%" cellpadding="0" cellspacing="0">


									<cms:Survey groupId="${param.groupId}" >
										<!-- 单选文字 -->
										<cms:if test="${Survey.optionType==1}">

											<tr class="datahead">

												<td width="65%" height="30">
													<strong>[&nbsp;调查${status.index+1}&nbsp;]&nbsp;&nbsp;${Survey.surveyTitle}</strong>
												</td>

												<td width="35%">
													<input type="button" value="查看" onclick="javascript:openViewVoteDialog('${Survey.surveyId}');" class="btn-1" />
													
													<input type="button" value="编辑" onclick="javascript:openEditQuestionDialog('${Survey.surveyId}');" class="btn-1" />
													
													<input type="button" value="删除" onclick="javascript:deleteSurvey(${Survey.surveyId});" class="btn-1" />
													&nbsp;&nbsp;
													
													<img src="../../core/style/default/images/up.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'up');" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/down.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'down');" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class="form-checkbox" value="${Survey.surveyId}"  name="surveyId" type="checkbox" />
												</td>
											</tr>


											<tr>
												<td colspan="2">
													<table name="showlist" width="99%" border="0" cellspacing="0" cellpadding="0" class="table_inside">
														<cms:SurveyOption >
															<cms:if test="${status.index % 3 ==0}">
																<tr>
																	<td width="32%" height="35">
																	<br />
																		<input type="radio" value="" class="from-radio" />
																		${Option.optionText}<br /><br />
																	</td>
															</cms:if>
															<cms:elseif test="${status.index % 3 ==2}">
																<td width="32%">
																<br />
																	<input type="radio" value="" class="from-radio" />
																	${Option.optionText}<br /><br />
																</td>
																</tr>
															</cms:elseif>
															<cms:else>
																<td width="32%">
																<br />
																	<input type="radio" value="" class="from-radio" />
																	${Option.optionText}<br /><br />
																</td>
															</cms:else>

														</cms:SurveyOption>

														<cms:if test="${status.index % 3 ==0}">
															<td width="32%" height="35">

															</td>
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:if>
														<cms:elseif test="${status.index % 3 ==1}">
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:elseif>

													</table>

												</td>

											</tr>

										</cms:if>
										<!-- 多选文字 -->
										<cms:elseif test="${Survey.optionType==2}">

											<tr class="datahead">

												<td width="65%" height="30">

													<strong>[&nbsp;调查${status.index+1}&nbsp;]&nbsp;&nbsp;${Survey.surveyTitle}</strong>
												</td>

												<td width="35%">
													<input type="button" value="查看" onclick="javascript:openViewVoteDialog('${Survey.surveyId}');" class="btn-1" />
													
													<input type="button" value="编辑" onclick="javascript:openEditQuestionDialog(${Survey.surveyId});" class="btn-1" />
													
													<input type="button" value="删除" onclick="javascript:deleteSurvey(${Survey.surveyId});" class="btn-1" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/up.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'up');" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/down.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'down');" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class="form-checkbox" value="${Survey.surveyId}"  name="surveyId" type="checkbox" />
												</td>
											</tr>


											<tr>
												<td colspan="2">
													<table name="showlist" width="99%" border="0" cellspacing="0" cellpadding="0" class="table_inside">
														<cms:SurveyOption >
															<cms:if test="${status.index % 3 ==0}">
																<tr>
																	<td width="32%" height="35">
																		<br />
																		<input type="checkbox" value="" class="from-checkbox" />
																		${Option.optionText}
																		<br /><br />
																	</td>
															</cms:if>
															<cms:elseif test="${status.index % 3 ==2}">
																<td width="32%">
																	<br />
																	<input type="checkbox" value="" class="from-checkbox" />
																	${Option.optionText}
																	<br /><br />
																</td>
																</tr>
															</cms:elseif>
															<cms:else>
																<td width="32%">
																	<br />
																	<input type="checkbox" value="" class="from-checkbox" />
																	${Option.optionText}
																	<br /><br />
																</td>
															</cms:else>

														</cms:SurveyOption>

														<cms:if test="${status.index % 3 ==0}">
															<td width="32%" height="35">

															</td>
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:if>
														<cms:elseif test="${status.index % 3 ==1}">
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:elseif>


													</table>

												</td>

											</tr>


										</cms:elseif>
										<!-- 单选图片 -->
										<cms:elseif test="${Survey.optionType==3}">

											<tr class="datahead">

												<td width="65%" height="30">

													<strong>[&nbsp;调查${status.index+1}&nbsp;]&nbsp;&nbsp;${Survey.surveyTitle}</strong>
												</td>

												<td width="35%">
													<input type="button" value="查看" onclick="javascript:openViewVoteDialog('${Survey.surveyId}');" class="btn-1" />
													
													<input type="button" value="编辑" onclick="javascript:openEditQuestionDialog(${Survey.surveyId});" class="btn-1" />
													
													<input type="button" value="删除" onclick="javascript:deleteSurvey(${Survey.surveyId});" class="btn-1" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/up.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'up');" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/down.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'down');" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class="form-checkbox" value="${Survey.surveyId}"  name="surveyId" type="checkbox" />
												</td>
											</tr>


											<tr>
												<td colspan="2">
													<table name="showlist" width="99%" border="0" cellspacing="0" cellpadding="0" class="table_inside">
														<cms:SurveyOption >
															<cms:if test="${status.index % 3 ==0}">
																<tr>
																	<td width="32%" height="35">
																		<br />
																		<input type="radio" value="" class="from-radio" />
																		<a class="cmsSysShowSingleImage"  href="${Option.optionImage}"><img src="${Option.optionImage}" width="90" height="67" style="vertical-align:middle;"/></a>
																		<br />
																		<br />
																	</td>
															</cms:if>
															<cms:elseif test="${status.index % 3 ==2}">
																<td width="32%">
																	<br />
																	<input type="radio" value="" class="from-radio" />
																	<a class="cmsSysShowSingleImage"  href="${Option.optionImage}"><img src="${Option.optionImage}" width="90" height="67" style="vertical-align:middle;"/></a>
																	<br />
																	<br />
																</td>
																</tr>
															</cms:elseif>
															<cms:else>
																<td width="32%">
																	<br />
																	<input type="radio" value="" class="from-radio" />
																	<a class="cmsSysShowSingleImage"  href="${Option.optionImage}"><img src="${Option.optionImage}" width="90" height="67" style="vertical-align:middle;"/></a>
																	<br />
																	<br />
																</td>
															</cms:else>

														</cms:SurveyOption>

														<cms:if test="${status.index % 3 ==0}">
															<td width="32%" height="35">

															</td>
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:if>
														<cms:elseif test="${status.index % 3 ==1}">
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:elseif>


													</table>

												</td>

											</tr>

										</cms:elseif>


										<!-- 多选图片 -->
										<cms:elseif test="${Survey.optionType==4}">

											<tr class="datahead">

												<td width="70%" height="30">

													<strong>[&nbsp;调查${status.index+1}&nbsp;]&nbsp;&nbsp;${Survey.surveyTitle}</strong>
												</td>

												<td width="30%">
													<input type="button" value="查看" onclick="javascript:openViewVoteDialog('${Survey.surveyId}');" class="btn-1" />
													
													<input type="button" value="编辑" onclick="javascript:openEditQuestionDialog(${Survey.surveyId});" class="btn-1" />
													
													<input type="button" value="删除" onclick="javascript:deleteSurvey(${Survey.surveyId});" class="btn-1" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/up.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'up');" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/down.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'down');" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class="form-checkbox" value="${Survey.surveyId}" name="surveyId" type="checkbox" />
												</td>
											</tr>


											<tr>
												<td colspan="2">
													<table name="showlist" width="99%" border="0" cellspacing="0" cellpadding="0" class="table_inside">
														<cms:SurveyOption >
															<cms:if test="${status.index % 3 ==0}">
																<tr>
																	<td width="32%" height="35">
																		<br />
																		<input type="checkbox" value="" class="from-checkbox" />
																		<img src="${Option.optionImage}" width="90" height="67" style="vertical-align:middle;"/>
																		<br />
																		<br />
																	</td>
															</cms:if>
															<cms:elseif test="${status.index % 3 ==2}">
																<td width="32%">
																	<br />
																	<input type="checkbox" value="" class="from-checkbox" />
																	<img src="${Option.optionImage}" width="90" height="67" style="vertical-align:middle;"/>
																	<br />
																	<br />
																</td>
																</tr>
															</cms:elseif>
															<cms:else>
																<td width="32%">
																	<br />
																	<input type="checkbox" value="" class="from-checkbox" />
																	<img src="${Option.optionImage}" width="90" height="67" style="vertical-align:middle;"/>
																	<br />
																	<br />
																</td>
															</cms:else>

														</cms:SurveyOption>

														<cms:if test="${status.index % 3 ==0}">
															<td width="32%" height="35">

															</td>
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:if>
														<cms:elseif test="${status.index % 3 ==1}">
															<td width="32%" height="35">

															</td>
															</tr>
														</cms:elseif>

													</table>

												</td>

											</tr>

										</cms:elseif>


										<!-- 用户输入-->
										<cms:elseif test="${Survey.optionType==5}">

											<tr class="datahead">

												<td width="70%" height="60">

													<strong>[&nbsp;调查${status.index+1}&nbsp;]&nbsp;&nbsp;${Survey.surveyTitle}</strong>
												</td>

												<td width="30%">
													<input type="button" value="查看" onclick="javascript:openViewVoteTextDialog('${Survey.surveyId}');" class="btn-1" />
													
													<input type="button" value="编辑" onclick="javascript:openEditQuestionDialog(${Survey.surveyId});" class="btn-1" />
													
													<input type="button" value="删除" onclick="javascript:deleteSurvey(${Survey.surveyId});" class="btn-1" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/up.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'up');" />
													&nbsp;&nbsp;
													<img src="../../core/style/default/images/down.png" alt="" class="cursor" onclick="javascript:orderSurvey(${Survey.surveyId},'down');" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class="form-checkbox" value="${Survey.surveyId}"  name="surveyId" type="checkbox" />
												</td>
											</tr>


											<tr>
												<td colspan="2">
													<table name="showlist" width="99%" border="0" cellspacing="0" cellpadding="0" class="table_inside">

														<cms:SurveyOption >
															<br />
															&nbsp;<textarea style="width:586px; height:65px;" readonly class="form-textarea"></textarea>
															<br />
														</cms:SurveyOption>

													</table>
													<br />
												</td>

											</tr>

										</cms:elseif>



									</cms:Survey>

									<cms:Empty flag="Survey">
										<tr class="datahead">

											<td width="75%" height="30">

											</td>


										</tr>
										<tr>
											<td class="tdbgyew">
												<center>
													当前没有数据!
												</center>
											</td>
										</tr>

									</cms:Empty>


								</table>








							</td>
						</tr>

						<tr>
							<td colspan="8" class="PageBar" align="left">
								<div class="fr">

								</div>
								<div class="fl"></div>
							</td>
						</tr>
					</table>







				</td>
			</tr>
		</table>


		<!-- 主内容结束 -->





		<div style="height:5px;"></div>






		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">

function openCreateQuestionDialog()
{  
	$.dialog({ 
	    id : 'ocqd',
    	title : '添加调查项',
    	width: '800px', 
    	height: '600px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
               
        content: 'url:<cms:Domain/>core/question/CreateSurvey.jsp?groupId=${param.groupId}&groupFlag=${param.groupFlag}&uid='+Math.random()

	});
}

function openEditQuestionDialog(sid)
{  
	$.dialog({ 
	    id : 'oeqd',
    	title : '编辑调查项  (ID:'+sid+')',
    	width: '800px', 
    	height: '600px',  
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
               
        content: 'url:<cms:Domain/>core/question/EditSurvey.jsp?groupId=${param.groupId}&groupFlag=${param.groupFlag}&sid='+sid+'&uid='+Math.random()

	});
}


function openViewVoteDialog(sid)
{  
	$.dialog({ 
	    id : 'ovvd',
    	title : '查看调查结果',
    	width: '725px', 
    	height: '490px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
               
        content: 'url:<cms:Domain/>core/question/ShowVoteOptionResult.jsp?sid='+sid+'&uid='+Math.random()

	});
}

function openViewVoteTextDialog(sid)
{  
	$.dialog({ 
	    id : 'ovvtd',
    	title : '查看调查问卷文本',
    	width: '900px', 
    	height: '580px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
               
        content: 'url:<cms:Domain/>core/question/ShowVoteTextResult.jsp?sid='+sid+'&uid='+Math.random()

	});
}
		
		
		

function orderSurvey(sid, action)
{
	$.dialog.tips('正在执行排序...',3600000000,'loading.gif');
	
	window.location.href = '<cms:BasePath/>survey/swapSurveyOrder.do?action='+action+'&surveyId='+sid+'&groupId=${param.groupId}&groupFlag=${param.groupFlag}'+"&<cms:Token mode='param'/>"; 
}

function gotoManageQuestionBaseInfoPage()
{
	window.location.href = 'ManageSurveyGroup.jsp'; 
}

function deleteSurvey(id)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '185px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选调查选项吗？',
                    
                    ok: function () { 
       
    window.location.href = '<cms:BasePath/>survey/deleteSurvey.do?groupId=${param.groupId}&groupFlag=${param.groupFlag}&ids='+id+"&<cms:Token mode='param'/>"; 
    }, 
    cancel: true 
    });


}

function deleteCheckedSurvey()
{
	var checkedIdArray = new Array();
	var allCheckBox = document.getElementsByName("surveyId");
    for(var i = 0; i < allCheckBox.length; i++)
	{
		if(allCheckBox[i].checked == true)
		{
			checkedIdArray.push(allCheckBox[i].value);
		}
	}
	if(checkedIdArray.length == 0)
	{
		$.dialog({ 
   					title :'提示',
    				width: '165px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '没有选择任何调查选项！',
                    
                     cancel: true 
		});
		
		return;
	}
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '185px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选调查选项吗？',
                    
                    ok: function () { 
       
     window.location.href = '<cms:BasePath/>survey/deleteSurvey.do?groupId=${param.groupId}&groupFlag=${param.groupFlag}&ids='+checkedIdArray.join(',')+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
    
}


 
</script>

