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

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">交互信息</a> &raquo;
						<a href="#">调查问卷管理</a>
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
									所属：
									<select class="form-select" id="classId" name="classId" onchange="javascript:changeSurveyGroupClass(this.value)">
										<option value="" selected>
											------ 所有调查问卷 ------
										</option>
										<option value="-9999">
											全站调查
										</option>
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										</cms:CurrentSite>
									</select>
									&nbsp;
								</div>
								<div>
									<a href="javascript:openCreateQuestionDialog();" class="btnwithico"> <img src="../../core/style/icons/report--plus.png" alt="" /> <b> 添加调查&nbsp; </b> </a>
									<a href="javascript:deleteQuestionConfig();" class="btnwithico" > <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
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
											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkIds',this);" />
											</td>
											<td width="18%">
												<strong>调查问卷名称</strong>
											</td>

											<td width="7%">
												<strong>访问标识</strong>
											</td>
											<%--

								
											<td width="6%">
												<strong>开始日期</strong>
											</td>

											--%>
											<td width="6%">
												<strong>结束日期</strong>
											</td>

											<td width="3%">
												<strong>状态</strong>
											</td>

											<td width="10%">
												<center>
													<strong>维护</strong>
												</center>
											</td>
										</tr>

										<cms:SystemSurverGroup classId="${param.classId}"  size="10">
											<tr>
												<td>
													${Group.groupId}
												</td>
												<td>
													<input type="checkbox" name="checkIds" value="${Group.groupId}"    />
												</td>

												<td>
													${Group.questName}
												</td>

												<%--<td>
													<cms:if test="${Group.restriction == 1}">
													按用户
													</cms:if>
														<cms:else>
														IP限制
													</cms:else>

												</td>

												--%>

												<td>
													${Group.flagName}
												</td>
												<%--<td>
													${Group.startDate}
												</td>

												--%>
												<td>
													<cms:if test="${Group.endDate=='9999-12-31'}">永久有效</cms:if>
													<cms:else>${Group.endDate}</cms:else>
												</td>

												<td>
													<cms:if test="${Group.useState==1}">
														启用
													</cms:if>
													<cms:else>
														<font color="red">停用</font>
													</cms:else>
												</td>

												<td>
													<div>
														<center>
															<a href="javascript:gotoManageQuestionPage('${Group.groupId}','${Group.flagName}')"><img src="../../core/style/icons/script-stamp.png" width="16" height="16" />&nbsp;投票项</a>&nbsp;&nbsp;&nbsp;&nbsp;
															<a href="javascript:openEditQuestionDialog('${Group.groupId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />
															编辑</a>

														</center>
													</div>
												</td>

											</tr>
										</cms:SystemSurverGroup>


										<cms:Empty flag="Group">
											<tr>
												<td class="tdbgyew" colspan="9">
													<center>
														当前没有数据!
													</center>
												</td>
											</tr>
										</cms:Empty>


										<cms:PageInfo>
											<tr id="pageBarTr">
												<td colspan="8" class="PageBar" align="left">
													<div class="fr">
														<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
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
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
													<div class="fl"></div>
												</td>
											</tr>
										</cms:PageInfo>
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
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">
initSelect('classId','${param.classId}');

function gotoManageQuestionPage(groupId,groupFlag)
{
	window.location.href = 'ManageSurvey.jsp?groupId='+groupId+'&groupFlag='+groupFlag;
}


function openCreateQuestionDialog()
{
	$.dialog({ 
	    id : 'ocqd',
    	title : '新建调查问卷',
    	width: '640px', 
    	height: '555px', 
        lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/question/CreateSurveyGroup.jsp?uid='+Math.random()
	});
}

function openEditQuestionDialog(id)
{
	$.dialog({ 
	    id : 'oeqd',
    	title : '编辑调查问卷',
    	width: '640px', 
    	height: '555px', 
        lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/question/EditSurveyGroup.jsp?id='+id+'&uid='+Math.random()
	});
}

function deleteQuestionConfig()
{
	var cidCheck = document.getElementsByName('checkIds');
	
	var top = false;
	
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
    				
                    content: '请选择需要删除的调查！', 
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
    				
                    content: '您确认删除当前调查吗？',
                    
                    ok: function () { 
       
    window.location.href = '<cms:BasePath/>survey/deleteSurveyGroup.do?ids='+ids+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
}

function perviewQuestion(QuestionId)
{
	window.open('component/SystemQuestionPreview.jsp?QuestionId='+QuestionId, "_blank");
}

function changeSurveyGroupClass(classId)
{
	
	//alert(classId);
	window.location.href = 'ManageSurveyGroup.jsp?classId='+classId;	
}

function changeCommendTypeClass(obj)
{


}

</script>

