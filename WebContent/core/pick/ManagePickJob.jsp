<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
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
						<a href="#">内容采集</a> &raquo; 采集任务管理
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="roleForm" name="roleForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openCreatePickJobDialog();" class="btnwithico" onclick=""><img src="../style/icons/task--plus.png" width="16" height="16" /><b>增加采集任务&nbsp;</b> </a>
										<a href="javascript:deletePickTasks();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												<td width="9%">
													<strong>任务名称</strong>
												</td>

												<td width="14%">
													<strong>当前采集规则</strong>
												</td>
												
												<td width="6%">
												<strong>采集频率</strong>
												</td>


												<td width="5%">
													<strong>上次执行时间</strong>
												</td>


												<td width="9%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

										
												<cms:QueryData service="cn.com.mjsoft.cms.pick.service.PickService" method="getPickTaskInfoTag" var="" >

													<tr>
														<td>
															${SysObj.pickTaskId}
														</td>
														<td>
															<input class="form-checkbox" name="checkedId" value="${SysObj.pickTaskId}" type="checkbox"  />
														</td>
														<td>
															
															${SysObj.taskName}
														</td>
														<td>
															<cms:QueryData objName="Rule" querySign="SELECT_PICK_RULE_SINGLE_QUERY" var="${SysObj.ruleId}">
															${Rule.configName}
															</cms:QueryData>
														</td>
														
														<td>
														
															&nbsp;&nbsp; <cms:if test="${SysObj.pickType==1}"><img src="../style/icons/globe-network.png" width="16" height="16" /></cms:if>
															<cms:elseif test="${SysObj.pickType==2}"><img src="../style/icons/rss.png" width="16" height="16" /></cms:elseif>
															<cms:elseif test="${SysObj.pickType==3}"><img src="../style/icons/database-export.png" width="16" height="16" />&nbsp;</cms:elseif>${SysObj.pickPeriod}
														
															
														</td>
														<td>
															<cms:if test="${empty SysObj.excuteDT}">
															暂未执行
															</cms:if>
															<cms:else>
																<cms:FormatDate date="${SysObj.excuteDT}" />
															</cms:else>
															
														</td>


														<td>
															<div>
																<center>
																<cms:Class id="${SysObj.classId}">
																	<span class="STYLE4"><a href="javascript:openEditPickJobDialog('${SysObj.pickTaskId}');">编辑</a>&nbsp;| &nbsp; <a href="javascript:deletePickTask('${SysObj.pickTaskId}')">删除 </a>&nbsp;|&nbsp; <a href="javascript:doPickContent('${SysObj.pickTaskId}','<cms:JsEncode str='${SysObj.taskName}'/>','${Class.className}');">立即执行</a> </span>
																</cms:Class>
																</center>
															</div>
														</td>
													</tr>
												</cms:QueryData>
												<cms:Empty flag="SysObj">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
												</cms:Empty>
											
										</table>
									</div>
									<div class="mainbody-right"></div>
								</td>
							</tr>

						</table>

						</form>

					</td>
				</tr>

				<tr>
					<td height="10">
						&nbsp;
					</td>
				</tr>
			</table>

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>

var statusDialog;
var timeId = -1;

function openCreatePickJobDialog()
{
	$.dialog({ 
    	title :'新增采集任务',
    	width: '660px', 
    	height: '620px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/CreatePickJob.jsp'
	});
}

function openEditPickJobDialog(taskId)
{
	$.dialog({ 
    	title :'编辑采集任务',
    	width: '660px', 
    	height: '620px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/EditPickTask.jsp?taskId='+taskId
	});
}

function doPickContent(taskId, taskName, className)
{
	
	 var key = new UUID().id;
	 
	 var url = "<cms:BasePath/>pick/pickWeb.do?testMode=false&taskId="+taskId+"&key="+key+"&<cms:Token mode='param'/>";
 		
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
			               else if(mg.indexOf("权限") != -1)
			               {
			               	   	   removePublishRTStatus(key);
							       window.clearInterval(timeId);
							       timeId = -1;
							    
							       statusDialog.close();
							    
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
	
			     	
	statusDialog = $.dialog
    ({ 
  		title :'当前执行 - '+taskName,
    	width: '270px', 
    	height: '60px', 
        lock: true, 
    	icon: '32X32/i.png', 
    				
        content: '采集规则 : <strong>'+taskName+'<\/strong> <br/><br/> 目标栏目 :  <strong>'+className+'<\/strong><br/><br/>期望采集 <strong> <font color="red">0</font> <\/strong> 篇 内容<br/><br/>成功采集 <strong> <font color="red">0</font> <\/strong> 篇 内容', 
        ok: function()
       	{
       		removePublishRTStatus(key);
		    window.clearInterval(timeId);
		    timeId = -1;
		    //unSelectAll();
		    
		    window.location.reload();
       	} 
	 });
	
    timeId = setInterval("showPublishRTStatus('"+key+"','"+taskName+"','"+className+"')",300);
	
   
}

function showPublishRTStatus(key, taskName, className)
{
   var url = '<cms:BasePath/>common/queryOperationRTStatus.do?eventKey='+key+"&mode=json";

   $.ajax
   (
   {type:'POST',async:false,url:url,success:
	   function(da, textStatus)
	   {      
	      var data = eval("("+da+")");
	      
	     if(data != '')
	     { 
		     var jsonObj = eval("("+data+")");
		     
		     var content = '';
		     content = '采集规则 : <strong>'+taskName+'<\/strong> <br/><br/> 目标栏目 :  <strong>'+className+'<\/strong><br/><br/>期望采集 <strong> <font color="red">'+jsonObj.pubCount+'</font> <\/strong> 篇 内容<br/><br/>成功采集 <strong> <font color="red">'+jsonObj.current+'</font> <\/strong> 篇 内容';
		  
		     if('1' == jsonObj.homeOperStatus)
		     {
		     	content = content+'<br/><br/>本次采集完成!';
		     }
		     
		   
		     statusDialog.content(content);
		      	     
	      }
          else
          {
                statusDialog.content('采集规则 : <strong>'+taskName+'<\/strong> <br/><br/> 目标栏目 :  <strong>'+className+'<\/strong><br/><br/>期望采集 <strong> <font color="red">0</font> <\/strong> 篇 内容<br/><br/>成功采集 <strong> <font color="red">0</font> <\/strong> 篇 内容');  
          }
	      
	      return;
	   	}
	 }
	);

}

function removePublishRTStatus(key)
{

   var url = '<cms:BasePath/>common/queryOperationRTStatus.do?eventKey='+key+"&mode=remove";
   
   $.ajax
   (
   	 {type:'POST',async:false,url:url,success:
		   function(data, textStatus)
		   {      
		    
		   }
	 }
	);
}

function deletePickTasks()
{
	var cidCheck = document.getElementsByName('checkedId');
	
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
    				
                    content: '请选择需要删除的任务！', 
       cancel: true 
                    
	  });
	  return;
	}

	deletePickTask(ids);
}


function deletePickTask(ids)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选配采集任务吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>pick/deletePickTask.do?ids="+ids+"&<cms:Token mode='param'/>";
 		
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
       
       
    }, 
    cancel: true 
                    
		});
}

</script>
