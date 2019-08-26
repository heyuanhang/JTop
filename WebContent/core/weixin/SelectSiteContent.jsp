<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		<script>  
		
		var hasError = false;
		//验证
		
	
	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
			
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
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
			
			$("#showlist2 tr[id!='pageBarTr']").hover(function() 
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

		<form id="advertPosForm" name="advertPosForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						 

						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
											<tr>
												<td class="mainbody" align="left" valign="top">
													<!--main start-->
													<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

														<tr>
															<td style="padding: 7px 10px;" class="">
																<div class="fl">
																	筛选:
																	<select class="form-select" id="orderBy" onchange="javascript:filterAction('${param.classId}');">
																		<option value="default" selected>
																			默认位置
																		</option>
																		<option value="addDate">
																			创建时间
																		</option>
																		<!-- 以上包含top数据排序,排序位按照本身值 -->
																		<option value="click">
																			点击数
																		</option>
																		<option value="comm">
																			评论数
																		</option>
																		<option value="su">
																			顶人数
																		</option>
																		<option value="ag">
																			踩人数
																		</option>
																		<!-- 以上各种评论数据不包含top数据排序,不包含一般数据,排序位按照本身值 -->
																		<option value="contentImg">
																			内容引图
																		</option>
																		<option value="homeImg">
																			首页引图
																		</option>
																		<option value="channelImg">
																			栏目引图
																		</option>
																		<option value="classImg">
																			列表引图
																		</option>
																		<!-- 以上各引导图不包含top数据排序,不包含一般数据,排序位按照orderIdFlag -->

																	</select>
																	&nbsp; 栏目:
																	<select id="commClassId" class="form-select" onchange="javascript:filterAction(this.value);">
																		<option value="-9999">
																				---- 整站查询 ----&nbsp;&nbsp;
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

																</div>
																<div>
																	<%--<a href="javascript:gotoAddUserDefineContentPage(${param.modelId});" class="btnwithico"> <img src="../core/style/default/images/doc_add.png" alt="" /><b>发布推荐位&nbsp;</b> </a>
																	<a href="javascript:;" class="btnwithico" onmousedown='javascript:sortContent();'> <img src="../core/style/default/images/sort-number.png" alt="" /><b>排序&nbsp;</b> </a>
																	<a href="javascript:;" class="btnwithico" onclick=""> <img src="../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
																--%>
																</div>
																<div class="fr">
																	关键词: &nbsp;
																	<input id="searchKey" name="searchKey" class="form-input" size="35" maxlength="60" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
																	<input onclick="javascript:search();" value="搜索" class="btn-1" type="button" />
																</div>
															</td>
														</tr>

														<tr>
															<td id="uid_td25" style="padding: 2px 6px;">
																<div class="DataGrid">
																	<cms:SystemManageContentList classId="${param.classId}" order="${param.orderBy}" filter="${param.filterBy}" censorBy="1" page="true" pageSize="9" startDate="${param.appearStartDate}" endDate="${param.appearEndDate}" key="${param.key}">
																		<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																			<tr class="datahead">
																				<td width="4%" height="30">
																					<strong>ID</strong>
																				</td>
																				<td width="2%" height="30">
																					<cms:if test="${param.single != true}">
																						<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkContent',this);"/>
																					</cms:if>																	
																				</td>
																				<td width="28%">
																					<strong>标题</strong>
																				</td>
																				<td width="3%">
																					<cms:if test='${param.orderBy=="comm-down" || param.orderBy=="comm-up"}'>
																						<strong>评论数</strong>
																					</cms:if>
																					<cms:elseif test='${param.orderBy=="su-down" || param.orderBy=="su-up"}'>
																						<strong>支持数</strong>
																					</cms:elseif>
																					<cms:elseif test='${param.orderBy=="ag-down" || param.orderBy=="ag-up"}'>
																						<strong>反对数</strong>
																					</cms:elseif>
																					<cms:else>
																						<strong>点击</strong>
																					</cms:else>
																				</td>
																				<td width="6%">
																					<strong>内容模型</strong>
																				</td>

																			</tr>

																			<cms:Content>

																				<tr>
																					<td>
																						${Info.contentId}
																					</td>
																					<td>
																						<cms:if test="${param.single == true}">
																							<input type="radio" name="checkContent" value="${Info.contentId}" />
																						</cms:if>
																						<cms:else>
																							<input type="checkbox" name="checkContent" value="${Info.contentId}" />
																						</cms:else>
																					</td>
																					<td>
																						<div align="left">

																							<span class="STYLE1">&nbsp;${Info.title }</span></a>

																						</div>
																					</td>
																					<td>
																						<cms:if test='${param.orderBy=="comm-down" || param.orderBy=="comm-up"}'>
																							${Info.commCount}
																						</cms:if>
																						<cms:elseif test='${param.orderBy=="su-down" || param.orderBy=="su-up"}'>
																							${Info.supportCount}
																						</cms:elseif>
																						<cms:elseif test='${param.orderBy=="ag-down" || param.orderBy=="ag-up"}'>
																							${Info.againstCount}
																						</cms:elseif>
																						<cms:else>
																							${Info.clickCount}
																						</cms:else>
																					</td>
																					<td>
																						<cms:SystemDataModel id="${Info.modelId}">																					
																								${DataModel.modelName}	
																						</cms:SystemDataModel>
																					</td>
																				</tr>
																			</cms:Content>

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
																							<span class="text_m"> 共 ${page.totalCount} 条记录 第${page.currentPage}页 / ${page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()"> </span>
																							<span class="page">[<a href="${page.headQuery}&spec=${param.spec}&typeId=${param.typeId}&commFlag=${param.commFlag}&single=${param.single}&dialogId=${param.dialogId}">首页</a>]</span>
																							<span class="page">[<a href="${page.prevQuery}&spec=${param.spec}&typeId=${param.typeId}&commFlag=${param.commFlag}&single=${param.single}&dialogId=${param.dialogId}">上一页</a>]</span>
																							<span class="page">[<a href="${page.nextQuery}&spec=${param.spec}&typeId=${param.typeId}&commFlag=${param.commFlag}&single=${param.single}&dialogId=${param.dialogId}">下一页</a>]</span>
																							<span class="page">[<a href="${page.endQuery}&spec=${param.spec}&typeId=${param.typeId}&commFlag=${param.commFlag}&single=${param.single}&dialogId=${param.dialogId}">末页</a>]</span>&nbsp;
																						</div>
																						<script>
																							function jump()
																							{
																								window.location="${page.jumpQuery}&currentPage="+document.getElementById('pageJumpPos').value+'&spec=${param.spec}&typeId=${param.typeId}&commFlag=${param.commFlag}&single=${param.single}&dialogId=${param.dialogId}';
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

											<tr>
												<td height="10">
													&nbsp;
												</td>
											</tr>
										</table>


										<div style="height:5px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													 
													<td class="td-input">
													<div style="float:right">
														<a name="btnwithicosysflag" href="javascript:submitSelectInfo('selectMode');"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确定&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
													&nbsp;
													</div>
													</td>
												</tr>
											</table>
										</div>
									</li>
								</ul>
							</div>
 


						</div>

					</td>
				</tr>
			</table>

			<!-- hidden -->


		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

var orderFlag = '${param.orderBy}';
		
var orderBy = '';
var orderWay = '';
if(orderFlag != '')
{
	var temp = orderFlag.split('-');
	orderBy = temp[0];
	orderWay = temp[1];
}
 
if('${param.classId}' == '-99999')
{
	
}


initSelect('commClassId','${param.classId}');

if('' == orderFlag)
{
	initSelect('orderBy','${param.filterBy}');
}
else
{
	initSelect('orderBy',orderBy);
}


 


function filterAction(classId,orderBy)
{
	var flag = document.getElementById('orderBy').value;
	
	var filter = '';
	
	var orderByVar = '';
	
	 if('contentImg' == flag)
	 {
	 	 filter = 'contentImg';
	 }
	 else if('homeImg' == flag)
	 {
	 	 filter = 'homeImg';
	 }
	 else if('channelImg' == flag)
	 {
	 	 filter = 'channelImg';
	 }
	 else if('classImg' == flag)
	 {
	 	 filter = 'classImg';
	 }
	 else
	 {
	 

         orderByVar = document.getElementById('orderBy').value+'-down';
    
     }
    
   
    
	window.location.href = 'SelectSiteContent.jsp?spec=${param.spec}&classId='+classId+"&typeId=${param.typeId}"+'&commFlag=${param.commFlag}&orderBy='+orderByVar+'&filterBy='+filter+'&single=${param.single}&dialogId=${param.dialogId}';
}

function submitSelectInfo(flag,action)
{
	if('true' == '${param.single}')
	{
		var contentId = null;
		
		var checks;
  	 	  	 	
  	 	if('pushMode' == flag)
  	 	{
  	 		checks = document.getElementsByName('checkPushCid');
  	 	
  	 	}
  	 	else
  	 	{
  	 		checks = document.getElementsByName('checkContent');
  	 	}
	
		for(var i = 0; i < checks.length; i++)
		{
			if(checks[i].checked == true)
			{
				contentId = checks[i].value;
			}
		}
		
		if(contentId == null)
		{
			W.$.dialog({ 
			   	title :'提示',
			    width: '120px', 
			    height: '60px', 
			    parent: api,
			    lock: true, 
			    icon: '32X32/i.png', 
			    				
			    content: '没有选择内容！', 
			    cancel: true 
			});
			return;
		}
		 
		var parWindow = api.get('${param.dialogId}');
		 
		parWindow.document.getElementById('contentId').value = contentId;
 
		var url = "<cms:BasePath/>wx/getContent.do?contentIds="+contentId;
		
		$.getJSON
		(
			url, 
	  		{},
	  		function(data)
	  		{ 
	  		  $.each
	  		  (
	  				data,
	  				function(i,item)
	  				{
	  			 
	  					parWindow.document.getElementById('title').value = item.title;
	  					parWindow.document.getElementById('commendMan').value = item.creator;
	  					parWindow.document.getElementById('url').value = 'JTOPCMS-CONTENT-URL:'+item.contentId ; 
	  					parWindow.document.getElementById('summary').value = item.summary;	
	  					parWindow.document.getElementById('classId').value = item.classId;
	  					 
	  					 
	  					 parWindow.editor1_articleText.execCommand('insertHtml', item.__sys_editor_text__)  
 
 
	  								
	  				}	      			
	  		   );
	  		  api.close();
	  			
	  		}
	  	 );
  	 }
  	 else
  	 {
  	 	var ids = '';
  	 	
  	 	var checks;
  	 	  	 	
  	 	if('pushMode' == flag)
  	 	{
  	 		checks = document.getElementsByName('checkPushCid');
  	 	
  	 	}
  	 	else
  	 	{
  	 		checks = document.getElementsByName('checkContent');
  	 	}

		for(var i = 0; i < checks.length; i++)
		{
			if(checks[i].checked == true)
			{
				ids += checks[i].value+',';
			}
		}
		
		if(ids == '')
		{
			W.$.dialog({ 
			   	title :'提示',
			    width: '120px', 
			    height: '60px', 
			    parent: api,
			    lock: true, 
			    icon: '32X32/i.png', 
			    				
			    content: '没有选择内容！', 
			    cancel: true 
			});
			return;
		}
		
		
		 
		
		var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
		
		var url = '';
		
		if('true' == '${param.spec}')
		{
			var url = "<cms:BasePath/>content/addMuptiSpecInfo.do?typeId=${param.typeId}&ids="+ids+"&commFlag=${param.commFlag}&flag="+flag+"&action="+action+"&<cms:Token mode='param'/>";	
		}
		else
		{
			var url = "<cms:BasePath/>content/addMuptiCommendInfo.do?typeId=${param.typeId}&ids="+ids+"&commFlag=${param.commFlag}&flag="+flag+"&action="+action+"&<cms:Token mode='param'/>";
		}
		
      	
		$.ajax({
		  	type: "POST",
		   	url: url,
		   	data:'',
		   
		       	success: function(mg)
		        {        
		        	var msg = eval("("+mg+")");
		        	
			        if('success' == msg)
			    	{
			    		tip.close();
			    		
			    		W.$.dialog.tips('加入图文成功',1,'32X32/succ.png'); 
			    		 
			    	}
			    	else
			    	{
			    		W.$.dialog({ 
		   					title :'提示',
		    				width: '200px', 
		    				height: '60px', 
		    				parent:api,
		                    lock: true, 
		    				icon: '32X32/fail.png', 
		    				
		                    content: "执行失败，无权限请联系管理员！", 
		       				cancel: true 
			  			});
			  			
			  			 
						
						tip.close();
			    	}
		    	}
		 });	
  	 }
	
	//
	
}

function search()
{
	 

	var key = encodeData(encodeURI(encodeURI(document.getElementById('searchKey').value)));
	
	var did = '${param.dialogId}';
	
	if(did != '')
	{
		window.location='SelectSiteContent.jsp?spec=${param.spec}&classId=${param.classId}&single=true&dialogId=${param.dialogId}&typeId=${param.typeId}&key='+key;
	}
	else
	{
		window.location='SelectSiteContent.jsp?spec=${param.spec}&classId=${param.classId}&single=false&commFlag=${param.commFlag}&typeId=${param.typeId}&key='+key;
	}
	
	
	//replaceUrlParam(window.location, 'key='+key);
}  
  

function close()
{
	 
	api.close();
}

</script>

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
