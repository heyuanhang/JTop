<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/My97DatePicker/WdatePicker.js"></script>

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
      	</script>
	</head>
	<body>

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">文档管理</a> &raquo;
						<a href="#">专题主题管理</a> &raquo; 
						<cms:Class id="${param.classId}">

							${Class.className} &nbsp;(${Class.classFlag} : ${Class.classId})

						</cms:Class>
						
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

								</div>
								<div>
									<a href="javascript:openAddSpecialSubject();" class="btnwithico"> <img src="../../../core/style/default/images/doc_add.png" alt="" /><b>添加专题&nbsp;</b> </a>
									<a href="javascript:openMoveSpecDialog();" class="btnwithico" onclick=""> <img src="../../../core/style/default/images/doc_move.png" alt="" /><b>移动&nbsp;</b> </a>
									<a href="javascript:gotoSortChannelPage();" class="btnwithico"> <img src="../../../core/style/default/images/sort-number.png" alt="" /><b>排序&nbsp;</b> </a>
									<a href="javascript:setSpecSubjectRecomend('check','1');" class="btnwithico"> <img src="../../../core/style/default/images/flag-blue.png" alt="" /><b>推荐&nbsp;</b> </a>
									<a href="javascript:setSpecSubjectRecomend('check','0');" class="btnwithico"> <img src="../../../core/style/default/images/flag-white.png" alt="" /><b>解荐&nbsp;</b> </a>
									<a href="javascript:deleteSpecSubject('check');" class="btnwithico"> <img src="../../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
								</div>
								<div class="fr">
									按标题搜索: &nbsp;
									<input id="searchKey" name="searchKey" size="40" maxlength="100" class="form-input" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
									<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />
								</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<cms:SystemClassList parentId="${param.classId}" classType="5" key="${param.key}" showMode="false">
										<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="4%" height="30">
													<strong>ID</strong>
												</td>
												<td width="3%" height="30">
													<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkSpecSubject',this);"/>
												</td>
												<td width="30%">
													<strong>专题名称</strong>
												</td>

												<td width="12%">
													<strong>引用标识</strong>
												</td>
												
												<td width="15%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:SysClass>

												<tr>
													<td>
														${Class.classId}
													</td>
													<td>
														<input type="checkbox" name="checkSpecSubject" value="${Class.classId}" />
													</td>
													<td>
														<div align="left">
															<cms:if test="${Class.isRecommend==1}">
															 
																<img src="../../../core/style/default/images/hot.png" title="推荐专题" />
															</cms:if>
															<cms:else>
																<img src="../../../core/style/default/images/some.png" title="普通专题" />
															</cms:else>
															&nbsp;${Class.className}
														</div>
													</td>

													<td>
														${Class.classFlag}
													</td>
													
													<td>
														<div align="center">
															<a href="javascript:gotoManageInfoPage('${Class.classId}', '${Class.className}');"><img src="../../../core/style/icon/documents-text.png" width="16" height="16" />内容</a>&nbsp;&nbsp;
															<a href="javascript:openEditSpecialSubject('${Class.classId}');"><img src="../../../core/style/icon/gear.png" width="16" height="16" />配置</a>&nbsp;&nbsp;
															<a href="javascript:deleteSpecSubject('${Class.classId}');"><img src="../images/del.gif" width="16" height="16" />删除</a>
															</span>
														</div>
													</td>

												</tr>

											</cms:SysClass>
											<cms:Empty flag="Class">
												<tr>
													<td class="tdbgyew" colspan="7">
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
									</cms:SystemClassList>
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
 


//initSelect('typeBy','${param.typeBy}');
//initSelect('censorBy','${param.censorBy}');
//initSelect('orderBy',orderBy);
//initSelect('orderWay',orderWay);


var currentClassId = "${param.classId}";
var currentModelId = 1;


var targetSortId = -1;

var checkedIdMap = new HashMapJs();

function regId(check)
{
   if(check.checked==true)
   {
      targetSortId=check.value;
      checkedIdMap.put(check.value,check.value);
  
   }
   else
   {
      targetSortId = -1;
      checkedIdMap.remove(check.value);
   }
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
   

   
  
    
   var returnValue = window.showModalDialog("SortContent.jsp"+window.location.search+"&targetId="+targetSortId,targetSortId,"dialogWidth=950px;dialogHeight=500px;status=yes");
   if("ok"==returnValue)
   {
      window.location=window.location.protocol+"//"+window.location.host+window.location.pathname+'?classId='+currentClassId+'&modelId='+currentModelId;
   }
}

//以下为排序筛选操作

function filterAction(filterValue)
{
    var appearStartDate = document.getElementById('appearStartDate').value;
    var appearEndDate = document.getElementById('appearEndDate').value;
    
   
    var censorByVar = document.getElementById('censorBy').value;
    var typeByVar = document.getElementById('typeBy').value;
    var orderByVar = document.getElementById('orderBy').value+'-'+document.getElementById('orderWay').value;
    	
      window.location='ManageArticleContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&censorBy='+censorByVar+'&typeBy='+typeByVar+'&orderBy='+orderByVar;
	

}



//dialog操作
function deleteSpecSubject(ids)
{

	if(ids == 'check')
	{
		var cidCheck = document.getElementsByName('checkSpecSubject');
		
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
	    				
	                    content: '请选择需要删除的专题！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
	}

	
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选专题吗？',
                    
                    ok: function () { 
                    
                    var tip = $.dialog.tips('正在执行...',3600000000,'loading.gif');
                    
                    var url = "<cms:BasePath/>channel/deleteSpecSubject.do?classId=${param.classId}&specIds="+ids+"&<cms:Token mode='param'/>";
 		 
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
										
										tip.close();
			               }   
			              
			            }
			     	});	
       
        
    }, 
    cancel: function()
    {
    
    	 
    }
 	});
 	
 	
 	
 	
}

function showCommendContentDialog(contentId,classId,modelId,title)
{
	var ti = '';
	if(title != '')
	{
		ti = ''+title.substring(0,22)+'...';
	}
	
	$.dialog({ 
		    id : 'occtcd',
	    	title : ''+ti,
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
	    	width: '450px', 
	    	height: '480px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowCommendTypeDialog.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+ids+'&modelId='+modelId
	});
}




function copySomeContent()
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
    				
                    content: '请选择需要复制的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}


	openCopyContentToSiteClassDialog('manage','1','');
}


function moveSomeContent(classId)
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
    				
                    content: '请选择需要移动的内容！', 
       cancel: true 
                    
	   });
	  return;
	}

	openMoveContentToSiteClassDialog('1',classId);
}


function openAddSpecialSubject()
{
	$.dialog({ 
		    id : 'cassd',
	    	title : '新建专题',
	    	width: '550px', 
    		height: '240px', 
    		
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/channel/AddSpecialSubject.jsp?classId=${param.classId}'
	});
}

function openEditSpecialSubject(classId)
{
	$.dialog({ 
		    id : 'oessd',
	    	title : '配置专题',
	    	width: '980px', 
    		height: '870px',
    		
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/channel/EditSpecialSubject.jsp?dialogApiId=oessd&classId='+classId
	});
}

function gotoManageInfoPage(classId, className)
{
	var typeId = '-1';;
	<cms:SystemCommendType classId="-1" isSpec="true">
		if('${CommendType.classId}' == classId)
		{
			typeId = '${CommendType.commendTypeId}';
		}
	</cms:SystemCommendType>
	
	if(typeId == '-1')
	{
		$.dialog({ 
   				title :'提示',
    			width: '160px', 
    			height: '60px', 
                lock: true, 
    			icon: '32X32/i.png', 	
                content: '当前专题没有信息节点', 
       		    cancel: true    
	  });
	  return;
	}
	
	window.location.href = 'ManageSpecialContent.jsp?classId='+classId+'&typeId='+typeId+'&className='+className;
}

<cms:SysClass id="${param.classId}">

function openMoveSpecDialog()
{
	var cidCheck = document.getElementsByName('checkSpecSubject');
	
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
    				
                    content: '请选择需要移动的专题！', 
       cancel: true 
                    
	
	  });
	  return;
	}

	$.dialog({ 
	    id : 'occtcd',
    	title : '移动专题 - ${Class.className}',
    	width: '400px', 
    	height: '500px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        close: function () 
        { 
            //if(moveSucc)
            //{
        		window.location.reload();
        	//}
        } ,
       
        content: 'url:<cms:BasePath/>core/channel/ShowMoveSpecSubject.jsp?uid='+Math.random()+'&classId='+ids+'&parent=${Class.classId}&linear=${Class.linearOrderFlag}'+'&spec=true'
	});
}


function setSpecSubjectRecomend(ids, status)
{
	if(ids == 'check')
	{
		var cidCheck = document.getElementsByName('checkSpecSubject');
		
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
	    				
	                    content: '请选择需要推荐的专题！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
	}


	var url = "<cms:BasePath/>channel/setSpecRecomStatus.do?classId="+ids+"&status="+status+"&<cms:Token mode='param'/>";
 		
 				 
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			       		dataType:'json',
			   
			       		success: function(msg)
			            {     
			               if('success' == msg)
			               {
			               	 
			               		window.location.reload();
			               } 	
			               else
			               {
			               	   $.dialog.tips('设定状态失败!...',1);
			               }   
			              
			            }
			     	});	
}





function gotoSortChannelPage()
{
	$.dialog({ 
		id:'gscp',
    	title :'[专题排序] 分类名称 - ${Class.className}',
    	width: '420px', 
    	height: '480px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
        close: function () 
        { 
        	window.location.reload();
        } ,
        
        content: 'url:<cms:Domain/>core/channel/ShowSortSpecSubject.jsp?classId=${Class.classId}'
	});
}

function search()
{
	
	var key = encodeData(encodeURI(encodeURI(document.getElementById('searchKey').value)));
	
	window.location='ManageSpecialSubject.jsp?classId=${param.classId}&key='+key;
}



</script>
</cms:SysClass>
