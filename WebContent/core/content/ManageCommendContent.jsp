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
		<cms:SystemCommendType typeId="${param.typeId}">
			<div class="breadnav">
				<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left">
							&nbsp;
							<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
							当前位置：
							<a href="#">文档管理</a> &raquo;
							<a href="#">推荐位内容维护</a> &raquo;
							<a href="#">${CommendType.commendName}  (${CommendType.commFlag} : ${param.typeId})</a>
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
										<a href="javascript:openAddCommendContentDialog('${param.classId}');" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>添加单条数据&nbsp;</b> </a>
										<a href="javascript:openSelectSiteAndPushContentDialog('${param.classId}');" class="btnwithico"> <img src="../../core/style/default/images/doc_pub.png" alt="" /><b>选取内容&nbsp;</b> </a>
										<a href="PreviewCommendInfo.jsp?commFlag=${CommendType.commFlag}" target="_blank" class="btnwithico"> <img src="../style/icons/monitor-window.png" alt="" /><b>预览&nbsp;</b> </a>


										<a href="javascript:sortCommendInfoColumn('');" class="btnwithico"> <img src="../../core/style/default/images/sort-number.png" alt="" /><b>行排序&nbsp;</b> </a>
										
										<a href="javascript:deleteAllCommendRow();" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除所有&nbsp;</b> </a>

										<a href="javascript:deleteCommendRow();" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除行&nbsp;</b> </a>

									</div>
									<div class="fr">
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">

										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="2%" height="30">
													<strong>行号</strong>
												</td>
												<td width="2%" height="30">
													<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkInfo',this);"/>
												</td>
												<td width="32%">
													<strong>标题</strong>
												</td>

												<td width="4%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:SysCommendContent typeId="${CommendType.commendTypeId}"  size="10">

												<tr>
													<td>
														${CommInfo.rowFlag}.
													</td>
													<td>
														<input type="checkbox" name="checkInfo" value="${CommInfo.rowFlag}" />
													</td>
													<td>
														<div align="left">

															<cms:SysCommendRow>
																<span class="STYLE1">&nbsp; <a href="javascript:openEditCommendContentDialog('${param.classId}','${CommendType.commendTypeId}','${RowInfo.infoId}');"><font style="text-decoration:underline">${RowInfo.title}</font> </a> </span>
															</cms:SysCommendRow>

														</div>
													</td>

													<%--<td>
														站点内容
													</td>

													<td>
														<cms:FormatDate date="${CommInfo.addTime}" />
													</td>
													--%>
													<td>
														<div align="center">
															<span class="STYLE4"> <img src="images/del.gif" width="16" height="16" /> <a href="javascript:openDeleteCommendRowDialog('${CommInfo.commendFlag}','${CommInfo.rowFlag}');">删除列</a> </span>
														</div>
													</td>

												</tr>
												

											</cms:SysCommendContent>

											<cms:Empty flag="CommInfo">
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



var targetSortId = -1;

function regId(check)
{
   if(check.checked==true)
   {
      targetSortId=check.value;
   }
   else
   {
      targetSortId = -1;
   }
} 


//以下为排序筛选操作

function filterAction(filterValue)
{
    var filterByVar = document.getElementById('filterBy').value;
    var censorByVar = document.getElementById('censorBy').value;
	
	//alert(filterByVar);
    window.location='ManageCommendContent.jsp?filterBy='+filterByVar+'&censorBy='+censorByVar;
	

}

function gotoCommendPage()
{

$.dialog({ 
   title :'内容推荐位',
    width: '900px', 
    height: '500px', 
     lock: true, 
    
    content: 'url:<cms:Domain/>core/content/CommendContent.jsp'
	
	
});

}





//dialog操作
function deleteContent(modelId,classId,contentId)
{
    $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除此内容吗？',
                    
                    ok: function () { 
       
     window.location='../../content/deleteUserDefineContent.do?contentId='+contentId+'&classId='+classId+'&&modelId='+modelId+"&<cms:Token mode='param'/>";
       
    }, 
    cancel: true 
                    
	
	//../../content/deleteUserDefineContent.do?contentId=${CommInfo.contentId}&classId=${CommInfo.classId}&&modelId=${CommInfo.modelId}；
	});



} 

function openSelectSiteAndPushContentDialog(classId)
{
	$.dialog({ 
	    id : 'ospcd',
    	title : '[ ${CommendType.commendName} ] - 选取站点内容',
    	width: '1050px', 
    	height: '760px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/dialog/SelectSiteAndPushContent.jsp?uid='+Math.random()+'&single=false&commFlag=${CommendType.commFlag}&typeId=${param.typeId}&classId='+classId
	});
}

function openAddCommendContentDialog(classId)
{
	$.dialog({ 
	    id : 'ospcd',
    	title : '[ ${CommendType.commendName} ] - 添加推荐内容',
    	width: '800px', 
    	height: '555px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/AddCommendInfo.jsp?uid='+Math.random()+'&typeId=${param.typeId}'+'&classId='+classId+'&dialogApiId=ospcd'
	});
}

function openEditCommendContentDialog(classId, commTypeId, infoId)
{
	$.dialog({ 
	    id : 'oeccd',
    	title : '[ ${CommendType.commendName} ] - 编辑推荐内容 - ID: '+infoId,
    	width: '800px', 
    	height: '555px',
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/EditCommendInfo.jsp?uid='+Math.random()+'&typeId=${param.typeId}'+'&classId='+classId+'&typeId='+commTypeId+'&infoId='+infoId+'&dialogApiId=oeccd'
	});
}


function openDeleteCommendRowDialog(commFlag, rowFlag)
{
	$.dialog({ 
	    id : 'odcrd',
    	title : '删除行内容',
    	width: '550px', 
    	height: '465px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/dialog/DeleteCommendRowInfo.jsp?typeId=${param.typeId}&uid='+Math.random()+'&commFlag='+commFlag+'&rowFlag='+rowFlag
	});
}

function deleteCommendRow()
{
	var cidCheck = document.getElementsByName('checkInfo');
	
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
	    				
	                    content: '没有选择要删除的行！', 
	       				cancel: true 
		  });
		  return;
		}
		
		
		
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选行吗？',
                    
                    ok: function () { 
                    
                    	var tip = $.dialog.tips('正在删除所选推荐数据...',9000); 
                    
                    	var url = "../../content/deleteCommRow.do?rowFlag="+ids+"&commFlag=${CommendType.commFlag}&typeId=${param.typeId}&mode=1"+"&<cms:Token mode='param'/>";
	 		
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
					        		tip.close();
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
					     
					     return;    
				       
				    	}, 
				    	cancel: true 
				                    
					
		});
}


function deleteAllCommendRow()
{
	
		
		
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所有的行吗？',
                    
                    ok: function () { 
                    	var tip = $.dialog.tips('正在删除所选专题数据...',9999000); 
                    	
                    	var url = "../../content/deleteCommRow.do?all=true&commFlag=${CommendType.commFlag}&typeId=${param.typeId}&mode=1"+"&<cms:Token mode='param'/>";
	 		
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
					     
					     return;    
				       
				    	}, 
				    	cancel: true 
				                    
					
		});
}


function sortCommendInfoColumn()
{
	var cidCheck = document.getElementsByName('checkInfo');
	
	var rowFlag='';
	var size = 0;
	
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			size++;
			rowFlag = cidCheck[i].value;
		}
	}
	
	if(size == 0)
	{
		$.dialog({ 
			title :'提示',
			width: '150px', 
			height: '60px', 
            lock: true, 
			icon: '32X32/i.png', 
			
            content: '没有选择待排序行！', 
			cancel: true 
		});
		
		return;
	}
	
	
	if(size > 1)
	{
		$.dialog({ 
			title :'提示',
			width: '170px', 
			height: '60px', 
            lock: true, 
			icon: '32X32/i.png', 
			
            content: '只能选择一行数据进行排序！', 
			cancel: true 
		});
		
		return;
	}
		
		
	$.dialog({ 
	    id : 'oeccd',
    	title : '推荐位行排序',
    	width: '210px', 
    	height: '80px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/dialog/SortCommendInfoColumn.jsp?uid='+Math.random()+'&commFlag=${CommendType.commFlag}&typeId=${param.typeId}'+'&rowFlag='+rowFlag
	});
}





</script>
</cms:SystemCommendType>
