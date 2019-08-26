<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="gbk">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<title>JTopCMS - 移动工作台 -工作流</title>
	<link rel="stylesheet" href="css/mui.min.css">
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js"></script>
	<style>
		.mui-table h4 {
		    line-height: 21px;
		    font-weight: 400;
		}
		.mr {
			margin-right: 20px;
		}
	</style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	<h1 class="mui-title">待我审核的内容</h1>
	<a class="mui-btn mui-btn-blue mui-btn-link mui-pull-right" href="<cms:BasePath/>login/postLogin.do?action=LoginOut&<cms:Token mode='param'/>">注销</a>
</header>
<div class="mui-content">
	<div class="mui-content-padded mui-text-center">
	
		<button onclick="javascript:viewWLState('','','');"  class="mui-btn mui-btn-success">意见</button>	
		<button onclick="javascript:readContent('','','');" class="mui-btn mui-btn-success">审稿</button>
		
		<button id="applyAudit" onclick="javascript:applyAudit();" class="mui-btn mui-btn-success">申请</button>
		 
		<button onclick="javascript:window.location.reload();" class="mui-btn mui-btn-success">刷新</button>
	</div>
	<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
	
	
		<cms:SPContentList personalReject="${param.personalReject}" pn="${param.pn}" size="8">
						<cms:SPContent>	
						
						<cms:Content id="${SPInfo.contentId}">
						
							 <li class="mui-table-view-cell">
					            <div class="mui-table">
					                <div class="mui-col-xs-1">
					                		<input type="checkbox" name="checkContent" value="${SPInfo.contentId}" id="check${SPInfo.contentId}" onclick="javascript:regId(this,'${SPInfo.modelId}','${SPInfo.classId}','${SPInfo.possessStatus}','${SPInfo.currentAuditUser}');" />
																					
					                </div>
					                <div class="mui-table-cell mui-col-xs-9">
					                    <h4 class="mui-ellipsis-2">
					                    
					                    <a class="title-a" style="color:black" href='javascript:readContent("${SPInfo.contentId}", "${SPInfo.classId}","${SPInfo.modelId}");'>${SPInfo.title}</a></a>
										<input type="hidden" id="SPTitle-${SPInfo.contentId}" value="${SPInfo.title}" />
					                    
					                    </h4>
					                    <h5>当前步骤：<b>${SPInfo.stepNodeName}</b></h5>
					                    <p class="mui-h6 mui-ellipsis">
					                    	<span class="mr">当前审核者:<b><cms:if test="${empty SPInfo.currentAuditUser}">无</cms:if>
																								<cms:else>${SPInfo.currentAuditUser}</cms:else></b></span>
					                    	<span>投稿人:<b>${SPInfo.creator}</b></span>
					                    </p>
					                </div>
					                <div class="mui-table-cell mui-col-xs-2 mui-text-right">
					                    <span class="mui-h5 mui-text-primary">${SPInfo.operStatusStr}</span>
					                </div>
					            </div>
					        </li>
						
						</cms:Content>
						 </cms:SPContent>
						 <cms:Empty flag="SPInfo">
																						 
																								<center>
																								<br/>
																									没有待我审核的内容!
																								</center>
																						 
						</cms:Empty>
						 
						 <cms:PageInfo>
																					 <br/>
																							 
																							<div style="float:right"> 
																							<span style="font-size:15px;display: inline-block;position: relative;padding: 6px 12px;">第${Page.currentPage}页 / 总${Page.pageCount}页</span>
																							 &nbsp;
																							 
																							 <cms:if test="${Page.pageCount > 1}">
																							 	 <button type="button" onclick="javascript:query('p');" class="mui-btn">
																									上一页
																								</button>
																								<button type="button" onclick="javascript:query('n');" class="mui-btn">
																								下一页
																								</button>
																							 
																							 </cms:if>
																							
																							  	 &nbsp; &nbsp; 
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
																										
																											
																											replaceUrlParam(window.location,'isReply=0&tab=4&pn='+cp);		
																										}
																							
																							
																										function jump()
																										{
																											replaceUrlParam(window.location,'isReply=0&tab=4&pn='+document.getElementById('pageJumpPos').value);
																										}
																								</script>
																							</div> 
																				</cms:PageInfo>
						 												  <br/> <br/>  
		</cms:SPContentList>
	
         
        
    </ul>
</div>	
</body>
</html>
<script>


var currentSelectId = -1;
var currentProcessManager = ''

var currentModelId = -1;
var currentClassId = -1;


/*
*以下为工作流审核操作
*/
function applyAudit()
{
	if(-1 == currentSelectId)
	{
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需申请审核内容!',
                    
                   
       			    cancel: true 
		});

		return;
	}
	
	var url = '<cms:BasePath/>workflow/applyAudit.do?contentId='+currentSelectId+"&<cms:Token mode='param'/>";
 		
	$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				            	var msg = eval("("+mg+")");
				               if('success' == msg)
				               {
				               		 
				               		window.location = '<cms:BasePath/>core/mob/censor.jsp';
				               		 
				               } 	
				               else if('fail' == msg)
				               {
				               		$.dialog({ 
						   					title :'提示',
						    				width: '190px', 
						    				height: '60px', 
						                    lock: true, 
						    				icon: '32X32/fail.png', 
						    				
						                    content: '当前内容已经拥有审核人!',
						                    
						                   
						       				cancel: true 
						
									});
				               	   
				               }   
				              
				            }
	});	
}

function openViewWFSteoInfoDialog()
{
	if(currentSelectId == -1)
	{
		$.dialog({ 
   					title :'提示',
    				width: '190px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要查阅流程日志的内容!',
                    
                   
       					cancel: true 

		});
		return;
	}
 
	$.dialog
	({
   					title :'查看审核流程日志',
    				width: window.screen.width+'px', 
    				height: (window.screen.height-60)+'px', 

    				lock:true,
        			max: false, 
        			min: false,
        			resize: false,
    				
                    content: 'url:<cms:BasePath/>core/workflow/ViewWorkflowOperInfo.jsp?cid='+currentSelectId
	});

}

function viewWLState(cid,ccid,mid)
{
	var tid;
	
	var tcid;
	
	var tmid;
	
	if('' == cid)
	{
		if(currentSelectId == -1)
		{
			$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择要审核的内容!',
	                    
	                   
	       					cancel: true 
	
			});
			return;
		}
		
		tid = currentSelectId;
		
		tcid = currentClassId;
		
		tmid = currentModelId;
	}
	else
	{
		tid = cid;
		
		tcid = ccid;
		
		tmid  = mid;
	}
	
	window.location = "<cms:BasePath/>core/mob/censorState.jsp?contentId="+tid+"&modelId="+tmid;
	
}

function readContent(cid,ccid,mid)
{
	var tid;
	
	var tcid;
	
	var tmid;
	
	if('' == cid)
	{
		if(currentSelectId == -1)
		{
			$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择要审核的内容!',
	                    
	                   
	       					cancel: true 
	
			});
			return;
		}
		
		tid = currentSelectId;
		
		tcid = currentClassId;
		
		tmid = currentModelId;
	}
	else
	{
		tid = cid;
		
		tcid = ccid;
		
		tmid  = mid;
	}
	
	
	
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
	</cms:LoginUser>
	
   if(currentProcessManager != '' && currentManager != currentProcessManager)
   {
	     $.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '当前内容审核权已由管理员 <font color="red"> '+currentProcessManager+' </font> 获得!',
                    
                   
       				cancel: true 

		});
		return;
   }
	
	window.location = "<cms:BasePath/>core/mob/workb.jsp?contentId="+tid+"&classId="+tcid+"&modelId="+tmid;
}


function regId(check,modelId,classId,possessStatus, currentMan)
{
   if(check.checked==true)
   {
      
      var pervCheck = document.getElementById("check"+currentSelectId);
      
      if(pervCheck != null)
      {
      	pervCheck.checked=false;
      }
      currentSelectId=check.value;
      currentProcessManager = currentMan;
      currentModelId = modelId;
      currentClassId = classId;
      
      if(possessStatus == 1)
	  {
	  	disableAnchorElement('applyAudit',true);
	  //	document.getElementById('applyAudit').style.cursor="not-allowed";
	  //	document.getElementById('applyAudit-b').style.cursor="not-allowed";
	  	
	  }
   }
   else
   {
      currentSelectId = -1;
      currentProcessManager = '';
      currentModelId = -1;
      currentClassId = -1;
      
      disableAnchorElement('applyAudit',false);
      //document.getElementById('applyAudit').style.cursor="default";
	 // document.getElementById('applyAudit-b').style.cursor="default";
   }
   
   
  
} 




</script>
