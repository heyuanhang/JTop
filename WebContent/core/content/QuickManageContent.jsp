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
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">文档管理</a> &raquo;
						<a href="#">全站内容快速管理</a>

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
									<a href="javascript:copySomeContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_copy.png" alt="" /><b>复制&nbsp;</b> </a>
									<a href="javascript:moveSomeContent('');" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_move.png" alt="" /><b>移动&nbsp;</b> </a>
									<a href="javascript:showCommendMutpiContentDialog('${param.classId}','${param.modelId}');" class="btnwithico"> <img src="../../core/style/default/images/flag-blue.png" alt="" /><b>推荐&nbsp;</b> </a>

									<a href="javascript:shareContent();" class="btnwithico"> <img src="../../core/style/blue/icon/sitemap-application.png" alt="" /><b>共享&nbsp;</b> </a>
									<a href="javascript:addTagForSomeContent();" class="btnwithico"> <img src="../../core/style/icons/tags-label.png" alt="" /><b><span id="topActName">标签</span>&nbsp;</b> </a>
								 	<a href="javascript:deleteSelectContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
								</div>
								<div class="fr">
									搜索:&nbsp;
									<select id="searchModelId" name="searchModelId" class="form-select">
										<option value="-1" selected>
											--- 所有内容模型 ---
										</option>
										<cms:SystemDataModelList modelType="2">
											<cms:SystemDataModel>
												<option value="${DataModel.dataModelId}">
													${DataModel.modelName}
													</span>&nbsp;
												</option>
											</cms:SystemDataModel>
										</cms:SystemDataModelList>

									</select>
									<input id="searchKey" name="searchKey" size="50" maxlength="60" class="form-input" style="vertical-align:top;" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
									<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />

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
									<cms:SystemManageContentList createBy="${param.createBy}" type="${param.typeBy}" order="${param.orderBy}" filter="${param.filterBy}" censorBy="${param.censorBy}" page="true" pageSize="11" classId="${param.classId}" startDate="${param.filterStartDate}" endDate="${param.filterEndDate}" key="${param.key}">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="4%" height="30">
													<strong>ID</strong>
												</td>
												<td width="3%" height="30">
													<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkContent',this);"/>
												</td>
												<td width="30%">
													<strong>标题</strong>
												</td>

												<td width="3%">
													<strong>状态</strong>
												</td>

												<td width="10%">
													<strong>内容模型</strong>
												</td>

												<td width="10%">
													<strong>所属栏目</strong>
												</td>

												<td width="10%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:SystemContent >
												<cms:SysClass id="${Info.classId}">
													<tr id="tr-${status.index}">
														<td>
															${Info.contentId}
														</td>
														<td>
															<input type="checkbox" name="checkContent" value="${Info.contentId}" onclick="javascript:regId('${Info.topFlag}', this, '${status.index}');" />
														</td>
														<td>
															<div align="left">
																<cms:if test="${Info.topFlag==1}">
																	<input id="top-${Info.contentId}" type="hidden" value="true" />
																	<img src="../../core/style/default/images/top_flag.png" alt="固顶内容" />
																</cms:if>
																<cms:elseif test="${Info.hotFlag==1}">
																	<img src="../../core/style/default/images/fire-small.png" alt="热点内容" />
																</cms:elseif>
																<cms:elseif test="${Info.commendFlag==1}">
																	<img src="../../core/style/default/images/hot.png" alt="推荐内容" />
																</cms:elseif>
																<cms:elseif test="${'true' == Info.____CMS_DELETE_FOR_LUCENE____}">
																	<img src="../../core/style/default/images/delete-small.png" alt="已删内容" />
																</cms:elseif>
																
															 
																<cms:else>
																	<img src="../../core/style/default/images/some.png" alt="普通内容" />
																</cms:else>
																
																
																<span class="STYLE1"> <a class="title-a" target="_blank" href="<cms:SystemPreview mode="content" />">${Info.title}</a> </span></a>
															</div>
														</td>

														<td>
															<cms:if test="${Info.censorState==0}">送审</cms:if>
															<cms:elseif test="${Info.censorState==1}">已发</cms:elseif>
															<cms:elseif test="${Info.censorState==-2}">重编</cms:elseif>
															<cms:elseif test="${Info.censorState==2}">待发</cms:elseif>
															<cms:elseif test="${Info.censorState==3}">下线</cms:elseif>
														</td>

														<td>
															<cms:SystemDataModel id="${Class.contentType}">
																	${DataModel.modelName}
															</cms:SystemDataModel>
														</td>

														<td>
															<a href="javascript:window.location='ManageGeneralContent.jsp?classId=${Class.classId}&modelId=${Class.contentType}'"> ${Class.className} </a>
														</td>


														<td>
															<div align="center">
																<cms:if test="${'true' == Info.____CMS_DELETE_FOR_LUCENE____}">
																	<a name="not_use_href" disabled href="javascript:;"><img src="../../core/style/icon/document-pencil.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																    <a name="not_use_href" disabled href="javascript:;"><img src="../../core/style/icon/del.gif" width="16" height="16" />&nbsp;删除</a>											
																
																	<script>
																		disableAnchorElementByName("not_use_href", true);
																	</script>
																</cms:if>
																<cms:else>
																<cms:Class id="${Info.classId}">
																
																	<a href="javascript:gotoEditUserDefineContentPageDialog('${Info.contentId}', '${Info.linkCid}','${Info.classId}', '${Info.modelId}','${Class.orgMode}', '<cms:JsEncode str='${Info.creator}'/>','${Info.censorState}','${Info.orgCode}');"><img src="../../core/style/icon/document-pencil.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																	<a href="javascript:deleteContent('${Info.modelId}','${Info.contentId}');"><img src="../../core/style/icon/del.gif" width="16" height="16" />&nbsp;删除</a>															
																
																
																</cms:Class>
																</cms:else>
																	</span>
															</div>
														</td>

													</tr>
												</cms:SysClass>
											</cms:SystemContent>

											<cms:Empty flag="Info">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
												</tr>
											</cms:Empty>

											<cms:if test="${empty param.key}">
												<tr id="pageBarTr">
													<td colspan="8" class="PageBar" align="left">
														<div class="fr">
															<span class="text_m"> 共 ${page.totalCount} 条记录 第${page.currentPage}页 / ${page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos">
																<input type="button" name="goto" value="GOTO" onclick="javascript:jump()">
															</span>
															<span class="page">[<a href="${page.headQuery}">首页</a>]</span>
															<span class="page">[<a href="${page.prevQuery}">上一页</a>]</span>
															<span class="page">[<a href="${page.nextQuery}">下一页</a>]</span>
															<span class="page">[<a href="${page.endQuery}">末页</a>]</span>&nbsp;
														</div>
														<script>
																function jump()
																{
																	window.location="${page.jumpQuery}&currentPage="+document.getElementById('pageJumpPos').value;
																}
															</script>
														<div class="fl"></div>
													</td>
												</tr>
											</cms:if>
											<cms:else>
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
																
																	
																	replaceUrlParam(window.location,'currentPage='+cp);		
																}
													
													
																function jump()
																{
																    var cp = parseInt(document.getElementById('pageJumpPos').value);
																    
																    if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	replaceUrlParam(window.location,'currentPage='+cp);
																}
															</script>
															<div class="fl"></div>
														</td>
													</tr>
												</cms:PageInfo>
											</cms:else>

										</table>

									</cms:SystemManageContentList>
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
//alert('${param.orderWay}');

initSelect('searchModelId','${param.searchModelId}');


var currentClassId = "${param.classId}";
var currentModelId = "${param.modelId}"


var targetSortId = -1;

var checkedIdMap = new HashMapJs();





function gotoEditUserDefineContentPage(contentId, classId, modelId)
{
	window.location.href="EditUserDefineModelContent.jsp?contentId="+contentId+"&classId="+classId+"&modelId="+modelId;
}





function sortContent()
{
   if(targetSortId == -1)
   {
 
     
        var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择待排序内容！',
                    
                   
       cancel: true 
                    
	
	});
      return;
   }
   
   
   $.dialog({ 
		    id : 'ocsd',
	    	title : '内容排序',
	    	width: '850px', 
	    	height: '500px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:<cms:BasePath/>core/content/dialog/SortContent.jsp?uid='+Math.random()+'&classId=${param.classId}&targetId='+targetSortId
	
	});
   
  
   
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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要删除的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	deleteContent('${param.modelId}',ids);

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
    				
                    content: '您确认删除所选内容吗？(不包含审核状态内容)',
                    
                    ok: function () 
                    { 
                    
                    //var url = "<cms:BasePath/>content/deleteContent.do?ids="+ids+"&modelId="+modelId;
                    var url = "<cms:BasePath/>content/deleteSearchContent.do?classId=-9999&ids="+ids+"&modelId="+modelId+"&<cms:Token mode='param'/>";
                    
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
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
			               	   $.dialog({ 
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

function showCommendContentDialog(contentId,classId,modelId,title)
{

	var ti = '';
	if(title != '')
	{
		ti = ''+title.substring(0,22)+'...';
	}
	
	ti = ti.replace('<','&lt;');
	ti = ti.replace('>','&gt;');
	
	$.dialog({ 
		    id : 'occtcd',
	    	title : '' + ti,
	    	width: '450px', 
	    	height: '480px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowCommendTypeDialog.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+contentId+'&modelId='+modelId
	
	});

}

function showCommendMutpiContentDialog(classId,modelId)
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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要推荐的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
		
	$.dialog({ 
		    id : 'ocmctcd',
	    	title : '推荐内容',
	    	width: '700px', 
	    	height: '485px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowCommendTypeDialog.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+ids+'&modelId='+modelId
	
	});

}

function copySomeContent()
{
    var modelId = '${param.searchModelId}';
    
	if(modelId == null || modelId == '-1' ||  modelId == '')
	{
	  $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '需要指定一个搜索模型的搜索结果！', 
       cancel: true 
                    
	
	  });
	  return;
	}

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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要复制的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	<cms:CurrentSite>
	openCopyContentToSiteClassDialog('manage',modelId,'','${CurrSite.siteId}');
	</cms:CurrentSite>
}


function moveSomeContent()
{
	var modelId = '${param.searchModelId}';
    
	if(modelId == null || modelId == '-1' ||  modelId == '')
	{
	  $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '需要指定一个搜索模型的搜索结果！', 
       cancel: true 
                    
	
	  });
	  return;
	}

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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要移动的内容！', 
       cancel: true 
                    
	  });
	  return;
	}

	openMoveContentToSiteClassDialog(modelId,'-1');
}

function openManageCommentDialog(contentId, title)
{
	var ti = '';
	
	ti = title.replaceAll('<','&lt;');
	ti = ti.replaceAll('>','&gt;');

	$.dialog({ 
		    id : 'omcd',
	    	title : '评论管理 - '+ti,
	    	width: '750px', 
	    	height: '580px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/comment/ManageSingleContentComment.jsp?uid='+Math.random()+'&contentId='+contentId
	
	});


}


function regId(topFlag, obj, index)
{
	var cidCheck = document.getElementsByName('checkContent');
	
	var ids='';
	var topCheck;
	var haveTop = false;
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			topCheck = document.getElementById('top-'+cidCheck[i].value);
			if(topCheck != null && 'true' == topCheck.value)
			{
				haveTop = true;
			}
		}
	}
	
	
	
	//背景色

	if(obj.checked == true)
	{
		$('#tr-'+index).addClass("tdbgyewck"); 
	}
	else
	{
		$('#tr-'+index).removeClass("tdbgyewck"); 
	}
	
	//排序
	if(obj.checked==true)
    {
      targetSortId=obj.value;
      checkedIdMap.put(obj.value,obj.value);
  
    }
    else
    {
      targetSortId = -1;
      checkedIdMap.remove(obj.value);
    }

}


function shareContent()
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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要共享的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}


    openSelectSiteGroupDialog(ids,'manage');
}


function search()
{
	var key = encodeData(encodeURI(encodeURI(document.getElementById('searchKey').value)));
	
	window.location='QuickManageContent.jsp?classId=${param.classId}&key='+key+'&searchModelId='+document.getElementById('searchModelId').value;
}

function addTagForSomeContent()
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
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要添加Tag的内容！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
		    id : 'ostd',
	    	title : '选取Tag标签',
	    	width: '510px', 
	        height: '555px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowSiteTagForMoreContent.jsp?cIds='+ids+'&uid='+Math.random()
	
	});
}


function gotoEditUserDefineContentPageDialog(contentId, linkId, classId, modelId, orgMode, creator, censor, orgCode)
{
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
		var uorgCode = '${Auth.orgCode}';
	</cms:LoginUser>
	
	if(censor == '-1' && creator != currentManager)
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '只能处理自己的稿件！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	if(linkId != '' && linkId != '-1')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '引用来的内容无法编辑！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	if('1' == censor && orgCode != uorgCode && '1' == orgMode)
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '210px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您无法编辑其他部门的内容！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	 

	$.dialog({ 
		id : 'main_content',
    	title :'编辑内容',
    	width:   '1200px', 
    	 height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/content/EditUserDefineModelContent.jsp?contentId='+contentId+"&classId="+classId+"&modelId="+modelId+"&uid="+Math.random()+'&manageParam='+encodeURIComponent(window.location.search)+'&innerWidth=1200'
	});
 
}

</script>


