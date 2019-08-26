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
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<style>
		/**增加title类型a，改变文字颜色**/
		.title-a {
			text-decoration:none;
			color:#454545;
		}
		
		.title-a:hover {
			text-decoration:none; color:#999;
		}
		
		</style>
		<script>
			basePath = '<cms:BasePath/>';
			
			var orderFlag = '${param.orderBy}';
		
			var orderBy = '';
			var orderWay = '';
			if(orderFlag != '')
			{
				var temp = orderFlag.split('-');
				orderBy = temp[0];
				orderWay = temp[1];
			}
			
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
	<cms:CurrentSite>
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">交互信息</a> &raquo;
						<a href="#">自定义表单内容管理</a>

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
								表单: 
									<select id="modelId" name="modelId" class="form-select"  onchange="javascript:change();">
										<option value="-1" selected>
											--- 请选择自定义表单 ---
										</option>
										 
										<cms:SystemDataModelList modelType="9" >
											<cms:SystemDataModel>
												<option value="${DataModel.dataModelId}">
													${DataModel.modelName}
													</span>&nbsp;
												</option>
											</cms:SystemDataModel>
										</cms:SystemDataModelList>
										 
									</select>
									&nbsp;
								</div>
								<div>
									<a href="javascript:javascript:setCensorFlag('true');" class="btnwithico"> <img src="../../core/style/icons/flag-blue.png" alt="" /><b>通过&nbsp;</b> </a>
									
									<a href="javascript:setCensorFlag('false');" class="btnwithico"> <img src="../../core/style/icons/flag-white.png" alt="" /><b>否决&nbsp;</b> </a>
									
									<a href="javascript:pickSingleAitlcleContent();" class="btnwithico"> <img src="../../core/style/icons/mouse--pencil.png" title="" /><b>转载&nbsp;</b> </a>																		
												
									<a href="javascript:report();" class="btnwithico"><img src="../style/icons/report-excel.png" width="16" height="16" /><b>导出EXCEL&nbsp;</b> </a>
								  
									<a href="javascript:deleteAllContent();" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>全删&nbsp;</b> </a>
								
									<a href="javascript:deleteSelectContent();" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
								
								
								</div>
								<div class="fr">
									<input type="checkbox" id="searchMode" name="searchMode" value="1" class="form-checkbox" onclick="javascript:gotoSearchMode();"/>搜索模式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;
									<cms:if test="${param.searchMode != 1}">
									
										筛选:&nbsp;
										<select id="qfSign" name="qfSign" class="form-select">
											<option value="-1" >
												--- 可选查询字段 ---
											</option>
											<cms:QueryData service="cn.com.mjsoft.cms.metadata.service.MetaDataService" method="getDefFormQueryFieldTag" objName="FormQueryField" var="${param.modelId}">
													
												 
													<option value="${FormQueryField.filedSign}">
														${FormQueryField.showName}
														</span>&nbsp;
													</option>
												 
											</cms:QueryData>
	
										</select>
										&nbsp;
										<select id="queryFlag" name="queryFlag"  class="form-select">
											<option value="="  >
												&nbsp;=&nbsp;
											</option>
											<option value="]"  >
												&nbsp;>&nbsp;
											</option>
											<option value="[" >
												&nbsp;<&nbsp;
											</option>
											<option value="]="  >
												&nbsp;>=&nbsp;
											</option>
											<option value="[=" >
												&nbsp;<=&nbsp;
											</option>
											</select>
									</cms:if>
									&nbsp;
									<cms:if test="${param.searchMode == 1}">
										搜索:&nbsp;
										<select id="seSign" name="seSign" class="form-select">
											<option value="-1" >
												--- 可选搜索字段 ---
											</option>
											<cms:QueryData service="cn.com.mjsoft.cms.metadata.service.MetaDataService" method="getDefFormSearchFieldTag" objName="FormSearchField" var="${param.modelId}">
													
												 
													<option value="${FormSearchField.filedSign}">
														${FormSearchField.showName}
														</span>&nbsp;
													</option>
												 
											</cms:QueryData>
	
										</select>
										
											 
	 
									</cms:if>
									 
									<input id="searchKey" name="searchKey" size="23" maxlength="180" class="form-input" style="vertical-align:top;" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>" />
									&nbsp;
									<input onclick="javascript:searchOrQuery();" value="<cms:if test="${param.searchMode != 1}">查询</cms:if><cms:else>搜索</cms:else>" class="btn-1" type="button" style="vertical-align:top;" />

								</div>
							</td>
						</tr>
						<tr>
							<td style="padding: 2px 10px;">

							</td>
						</tr>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									
									 	<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="4%" height="30">
													<strong>ID</strong>
												</td>
												<td width="3%" height="30">
													<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkContent',this);"/>
												</td>
												
												<cms:QueryData common="true" service="cn.com.mjsoft.cms.metadata.service.MetaDataService" method="getDefFormShowFieldTag" objName="FormField" var="${param.modelId}">
												
													<td width="${FormField.listShowSize}%">
														<strong>${FormField.showName}</strong>
													</td>
													
													
												</cms:QueryData>
												
												
												<td width="3%" height="30">
													<strong>审核状态</strong>
												</td>

											 

												<td width="6%">
													<center><strong>操作</strong></center>
												</td>
											</tr>
											
											<cms:if test="${param.searchMode == 1}">
													<cms:SystemManageContentList  form="true" field="${param.seSign}" page="true" pageSize="10" modelId="${param.modelId}" key="${param.key}">
														<cms:SystemContent >
														<tr id="tr-${status.index}">
															<td>
																${Info.contentId}
															</td>
															<td>
																<input type="checkbox" name="checkContent" value="${Info.contentId}"   />
															</td>
															
														<cms:QueryData reObj="FormField" objName="FF">
													
														<td>
															<cms:if test="${FF.htmlElementId == 5 || FF.htmlElementId == 6}">
																<cms:Get objName="Info" fieldName="${FF.filedSign}" choice="${FF.choiceValue}"/> (<cms:Get objName="Info" fieldName="${FF.filedSign}" />)
															</cms:if>
															<cms:else>
																<cms:Get objName="Info" fieldName="${FF.filedSign}" />
															</cms:else>
															 
	
														</td>
														
														
														
														
													   </cms:QueryData>
															 
														<td>
																 
																	<cms:SystemContent formMode="true" id="${Info.contentId}">
																
																	<cms:if test="${Info.censorState == 1 }">
																		<img src="../style/icon/tick.png" />
																	</cms:if>
																	<cms:else>
																		<img src="../style/icon/del.gif" />
																	</cms:else>
																
														</td> 
										
	
															<td>
																<div align="center">
																	  
																		<a href="javascript:openViewOrEditDefFormDataDialog('${Info.contentId}');"><img src="../../core/style/icon/document-pencil.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																		<a href="javascript:deleteContent('${param.modelId}','${Info.contentId}');"><img src="../../core/style/icon/del.gif" width="16" height="16" />&nbsp;删除</a>															
																	 
																</div>
															</td>
															
																</cms:SystemContent>
																 
														</tr>
													</cms:SystemContent >
	
												<cms:Empty flag="Info">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
												
												</cms:SystemManageContentList>
											
											</cms:if>
											<cms:else>
													 <cms:MutiContent page="true"  classId="form"  siteId="${CurrSite.siteId}" size="10" query="${param.searchOrQuery}" modelId="${param.modelId}">
														<tr id="tr-${status.index}">
															<td>
																${MInfo.contentId}
															</td>
															<td>
																<input type="checkbox" name="checkContent" value="${MInfo.contentId}"   />
															</td>
															
														<cms:QueryData reObj="FormField" objName="FF">
													
														<td>
															<cms:if test="${FF.htmlElementId == 5 || FF.htmlElementId == 6}">
																<cms:Get objName="MInfo" fieldName="${FF.filedSign}" choice="${FF.choiceValue}"/> (<cms:Get objName="MInfo" fieldName="${FF.filedSign}" />)
															</cms:if>
															<cms:else>
																<cms:Get objName="MInfo" fieldName="${FF.filedSign}" />
															</cms:else>

														</td>
														
													 
														
													   </cms:QueryData>
															 
														<td>
																<cms:SystemContent formMode="true" id="${MInfo.contentId}">
																
																	<cms:if test="${Info.censorState == 1 }">
																		<img src="../style/icon/tick.png" />
																	</cms:if>
																	<cms:else>
																		<img src="../style/icon/del.gif" />
																	</cms:else>
																
																</cms:SystemContent>
														</td>
										
	
															<td>
																<div align="center">
																	 
																		<a href="javascript:openViewOrEditDefFormDataDialog('${MInfo.contentId}');"><img src="../../core/style/icon/document-pencil.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																		<a href="javascript:deleteContent('${param.modelId}','${MInfo.contentId}');"><img src="../../core/style/icon/del.gif" width="16" height="16" />&nbsp;删除</a>															
																	 
																</div>
															</td>
	
														</tr>
													</cms:MutiContent>
	
												<cms:Empty flag="MInfo">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
											
											</cms:else>
											

											 
											 
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

				</td>
			</tr>


		</table>


		<from id="publishForm" name="publishForm"> <input type="hidden" id="someContentId" name="someContentId"></input> <input type="hidden" id="staticType" name="staticType" value="2"></input> </from>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


initSelect('modelId','${param.modelId}');

initSelect('qfSign','${param.qfSign}');

initSelect('seSign','${param.seSign}');

initSelect('queryFlag','${param.queryFlag}');

initRadio('searchMode','${param.searchMode}');
 

function change()
{
	replaceUrlParam(window.location, 'modelId='+$('#modelId').val());
}

function searchOrQuery()
{ 
	if('1' == '${param.searchMode}')
	{
		gotoSearchMode();
	}
	else
	{
		var key = encodeData(encodeURI($('#searchKey').val()));
	 
		replaceUrlParam(window.location, 'qfSign='+$('#qfSign').val()+'&queryFlag='+$('#queryFlag').val()+'&key='+key+'&searchOrQuery='+'{'+$('#qfSign').val()+'}'+$('#queryFlag').val()+key);
	}
}

function gotoSearchMode()
{ 
	if($('#searchMode').is(':checked')) 
	{
		var key = encodeData(encodeURI($('#searchKey').val()));
		
    	replaceUrlParam(window.location, 'searchMode=1&seSign='+$('#seSign').val()+'&key='+key);
	}
	else
	{
		replaceUrlParam(window.location, 'searchMode=0&seSign='+'&key=');
	}
	
}

function deleteSelectContent()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要删除的表单内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
 
	deleteContent( '${param.modelId}', ids);
}

function deleteAllContent()
{
	$.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除表单所有内容吗？',
                    
                    ok: function () 
                    { 
                    var tip = $.dialog.tips('正在删除表单内容,请耐心等待...',3600000000,'loading.gif'); 
                   
                    var url = "<cms:BasePath/>content/deleteFormData.do?mode=all&modelId=${param.modelId}&<cms:Token mode='param'/>";
                 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {       
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == mg || mg.indexOf('发布失败') != -1)
			               {
			               		 
			               		window.location.reload();
			               } 	
			               else
			               {
			               		tip.close();
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

//dialog操作
function deleteContent(modelId,ids)
{ 
	


	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选表单内容吗？',
                    
                    ok: function () 
                    { 
                   	 $.dialog.tips('正在执行删除...',3600000000,'loading.gif');
                   
                     var url = "<cms:BasePath/>content/deleteFormData.do?ids="+ids+"&modelId="+modelId+"&<cms:Token mode='param'/>";
                   
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == mg || mg.indexOf('发布失败') != -1)
			               {
			               		 
			               		window.location.reload();
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

function setCensorFlag(censor)
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要审核的表单内容！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	 var url = "<cms:BasePath/>content/censorFormData.do?modelId=${param.modelId}&ids="+ids+'&censorState='+censor+"&<cms:Token mode='param'/>";
                    

 		
	$.ajax({
			   type: "POST",
			   url: url,
			   data:'',
			   
			   success: function(mg)
			   {     
			   	var msg = eval("("+mg+")");
           		
			               if('success' == msg)
			               {

			               		window.location.reload();
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

}



function openViewOrEditDefFormDataDialog(cid)
{
	$.dialog({ 
	    id : 'ovefdd',
    	title : '查看或编辑表单数据',
    	width: '1300px', 
    	height: '820px',
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/content/ViewOrEditDefData.jsp?contentId='+cid+'&modelId=${param.modelId}&uid='+Math.random()+'&dialogApiId=ovefdd'
	});
}

function reset()
{
	 window.location.href='<cms:BasePath/>core/content/ManageCVFormContent.jsp?modelId=114';

}


function pickSingleAitlcleContent()
{
	

	$.dialog({ 
		    id : 'psad',
	    	title : '采集转载外部内容',
	    	width: '720px', 
	        height: '650px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/PickSingleArticle.jsp?mode=form&classId=${param.classId}&uid='+Math.random()
	
	});
}

function report()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要导出的简历！', 
       cancel: true 
                    
	  });
	  return;
	}

	 
                    
    window.location.href = "<cms:BasePath/>content/report.do?modelId=${param.modelId}&ids="+ids;
 		
 

}

</script>

</cms:CurrentSite>
