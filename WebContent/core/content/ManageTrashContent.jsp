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
		<cms:SysClass id="${param.classId}">
			<cms:SystemDataModel id='${Class.contentType}'>

				<div class="breadnav">
					<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left">
								&nbsp;
								<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
								当前位置：
								<a href="#">文档管理</a> &raquo;
								<a href="#">回收站管理</a> &raquo;
								<a href="#">${Class.className}</a>
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
											<a href="javascript:recoverContent();" class="btnwithico"> <img src="../../core/style/icons/arrow-continue-000-top.png" alt="" /><b>恢复内容&nbsp;</b> </a>
											<a href="javascript:deleteSelectContent();" class="btnwithico"> <img src="../../core/style/icons/bin--minus.png" alt="" /><b>彻底删除&nbsp;</b> </a>
											<a href="javascript:recoverAllContent();" class="btnwithico"> <img src="../../core/style/icons/arrow-continue-000-top.png" alt="" /><b>恢复全部内容&nbsp;</b> </a>	
											<a href="javascript:deleteAllContent();" class="btnwithico"> <img src="../../core/style/icons/bin-full.png" alt="" /><b>清空回收站&nbsp;</b> </a>
								
										</div>

										<div class="fr">
											标题关键字:
											<input id="searchKey" name="searchKey" size="45" maxlength="60" class="form-input" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
											<input  onclick="javascript:search();" value="查询" class="btn-1" type="button" />

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
													<td width="30%">
														<strong>标题</strong>
													</td>


													<td width="7%">
														<strong>创建者</strong>
													</td>

													<td width="6%">
														<strong>添加时间</strong>
													</td>



													<td width="6%">
														<strong>删除时间</strong>
													</td>

												</tr>

												<cms:SysTrashContent size="11" classId="${param.classId}" pn="${param.pn}" key="${param.key}" >

													<tr id="tr-${status.index}" height="20">
														<td>
															${Trash.contentId}
														</td>
														<td>
															<input type="checkbox" name="checkContent" value="${Trash.contentId}" onclick="javascript:regId('${Trash.topFlag}', this, '${status.index}');" />
														</td>
														<td>
															<div align="left">

																<span class="STYLE1"> ${Trash.title} </span>
															</div>
														</td>

														<td>
															${Trash.creator}
														</td>

														<td>
															${Trash.addTime}
														</td>



														<td>
															${Trash.deleteTime}
														</td>


													</tr>

												</cms:SysTrashContent>

												<cms:Empty flag="Trash">
													<tr height="32">
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




var currentClassId = "${param.classId}";
var currentModelId = "${Class.contentType}"




var checkedIdMap = new HashMapJs();





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
	
	deleteContent('${Class.contentType}',ids);

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
    				
                    content: '您确认彻底删除所选内容吗？删除后将不能恢复。',
                    
                    ok: function () 
                    { 
                    
                    var url = "<cms:BasePath/>content/deleteContent.do?classId=${param.classId}&ids="+ids+"&modelId="+modelId+"&<cms:Token mode='param'/>";
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
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

function deleteAllContent()
{
	$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认彻底清空当前栏目回收站吗？',
                    
                    ok: function () 
                    { 
                    var tip = $.dialog.tips('正在从回收站粉碎内容及附属文件,请耐心等待...',3600000000,'loading.gif'); 
                    var url = "<cms:BasePath/>content/deleteContent.do?flag=allTrash&classId=${param.classId}&modelId=${Class.contentType}"+"&<cms:Token mode='param'/>";
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == msg)
			               {
			               		 
			               		//W.$.dialog.tips('删除内容成功...',1); 
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


function recoverContent()
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
    				
                    content: '请选择要恢复的内容！', 
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
    				
                    content: '您确认恢复所选内容吗？',
                    
                    ok: function () 
                    { 
                    var tip = $.dialog.tips('正在恢复内容到栏目,请耐心等待...',3600000000,'loading.gif'); 
                    var url = "<cms:BasePath/>content/recoverContent.do?classId=${param.classId}&ids="+ids+"&<cms:Token mode='param'/>";
                    
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == mg || mg.indexOf('发布失败') != -1)
			               {

			               		$.dialog.tips('恢复内容成功...',1); 
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

function recoverAllContent()
{
	
	
	
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认恢复全部内容吗？',
                    
                    ok: function () 
                    { 
                    var tip = $.dialog.tips('正在恢复全部内容到栏目,请耐心等待...',99600000000,'loading.gif'); 
                    var url = "<cms:BasePath/>content/recoverContent.do?classId=${param.classId}&all=true"+"&<cms:Token mode='param'/>";
                    
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == mg || mg.indexOf('发布失败') != -1)
			               {

			               		$.dialog.tips('恢复内容成功...',1); 
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

function search()
{
	var key = encodeData(encodeURI(encodeURI(document.getElementById('searchKey').value)));
	
	window.location='ManageTrashContent.jsp?classId=${param.classId}&key='+key;
}


function regId(topFlag, obj, index)
{
	
	
	//背景色

	if(obj.checked == true)
	{
		$('#tr-'+index).addClass("tdbgyewck"); 
	}
	else
	{
		$('#tr-'+index).removeClass("tdbgyewck"); 
	}
	

}




</script>

</cms:SystemDataModel>
</cms:SysClass>
