<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="GBK">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<cms:SystemDataModel id='${param.modelId}'>
<cms:SystemContent modelId="${param.modelId}" id="${param.contentId}">
	
	<title>JTopCMS - 移动工作台 -工作流</title>
	<link rel="stylesheet" href="css/mui.min.css">
		
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js"></script>
	<style>
		.title {
		    margin: 20px 15px 10px;
		    color: #6d6d72;
		    font-size: 15px;
		}
		.mui-table-view-cell>.text-container-right{
		    position: absolute;
		    top: 50%;
		    right: 15px;
		    -webkit-transform: translateY(-50%);
		    transform: translateY(-50%);
		}
		.mui-bar-tab .mui-btn {
			margin: 0 5px;
		}
		</style>
		<script>
			basePath='<cms:Domain/>';
			
			//当前管理员
			var currentManager = '';
			<cms:LoginUser>
				currentManager = '${Auth.apellation}';
			</cms:LoginUser>
		
			//$(window).load(function()
			$(function()
			{
				 
				 
				 //验证规则注册
				  <cms:SystemModelFiledList modelId="${param.modelId}">
						<cms:SystemModelFiled>		
					    //if('${ModelFiled.defaultValidate}' != '')
					    //{		    	
							<cms:FieldValidateConfig id="${ModelFiled.defaultValidate}">	
							
							//validate('${ModelFiled.fieldSign}','${ModelFiled.isMustFill}','${Valid.regulation}','${Valid.errorMessage}');	
							</cms:FieldValidateConfig>
						//}
	                   </cms:SystemModelFiled>   
				</cms:SystemModelFiledList>
	
				//标题不可为空
 				//validate('title',1,null,null);
			})
			
			
			//检查工作流中内容
			var notProcessMan = false;
				
			var currentProcessMan = '';
				
			if('true' == '${Info.censorState == 0}')
			{
				<cms:SPContent id='${Info.contentId}'>
					
				currentProcessMan = '${SPInfo.currentAuditUser}';
					
				if(currentProcessMan != '' && currentManager != currentProcessMan)
				{
					notProcessMan = true;
				}
					
				</cms:SPContent>
			}	
		</script>
	
</head>
<header class="mui-bar mui-bar-nav">
	<a class="mui-icon mui-icon-left-nav mui-pull-left" href="<cms:BasePath/>core/mob/censor.jsp"></a>
	<h1 class="mui-title">内容审核</h1>
</header>
<!-- 底部菜单 -->
<nav class="mui-bar mui-bar-tab mui-text-center">
 
	
	<cms:WorkflowActionUIHelp contentId="${param.contentId}" classId="${param.classId}" edit="true">
										
										
										<button type="button" onclick="javascript:submitUserDefineContentInfo('${Action.actionId}','${Action.fromStepId}','${Action.toStepId}');" class="mui-btn mui-btn-success">
											${Action.passActionName}
											 		</button>
										</cms:WorkflowActionUIHelp>
									
									<cms:Class id="${param.classId}">
									
										<cms:if test="${Class.workflowId > 0}">
											 
											<button type="button" onclick="javascript:openFlowStepSuggestDialog();" class="mui-btn">
												建议
											</button>
										</cms:if>
																			
									</cms:Class>
									
									
									
									 
									
	
	 			
</nav>
<!--  -->
<div class="mui-content">
	<div class="title">基本信息</div>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell">
			所属栏目
			<p class="mui-ellipsis-2"><cms:ChannelPath classId="${Info.classId}">${PathClass.className} <cms:if test="${!status.last}">></cms:if></cms:ChannelPath></p>
		</li>
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:;">
				<div class="mui-media-body">
					主标题
					<p class="mui-ellipsis-2">${Info.title}</p>
				</div>
			</a>
		</li>
		<cms:if test="${!empty Info.simpleTitle}">
		<li class="mui-table-view-cell">
			副标题
			<p class="mui-ellipsis-2">$${Info.simpleTitle}</p>
		</li>
		</cms:if>
		<cms:if test="${!empty Info.shortTitle}">
		<li class="mui-table-view-cell">
			短标题
			<p class="mui-ellipsis-2">${Info.shortTitle}</p>
		</li>
		</cms:if>
		<li class="mui-table-view-cell">
			作者 
			<p class="text-container-right">${Info.creator}</p>
		</li>
		<li class="mui-table-view-cell">
			来源 
			<p class="text-container-right">${Info.author}</p>
		</li>
		
		<li class="mui-table-view-cell">
			摘要 
			<p class="mui-ellipsis-3">${Info.summary}</p>
		</li>
		<li class="mui-table-view-cell">
			<a class="mui-navigate-right" href="<cms:BasePath/>core/mob/container.jsp?modelId=${param.modelId}&contentId=${param.contentId}">
				内容详情(请点击)
			</a>
		</li>

	</ul>
	<div class="title">高级选项</div>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:showImage('${Info.contentImage}')">
				<img class="mui-media-object mui-pull-right" src="${Info.contentImageCmsSysResize}">
				<div class="mui-media-body">
					内容缩略图
					<p class="mui-ellipsis"></p>
				</div>
			</a>
		</li>
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:showImage('${Info.classImage}')">
				<img class="mui-media-object mui-pull-right" src="${Info.classImageCmsSysResize}">
				<div class="mui-media-body">
					列表缩略图
					<p class="mui-ellipsis">
				</div>
			</a>
		</li>
		
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:showImage('${Info.channelImage}')">
				<img class="mui-media-object mui-pull-right" src="${Info.channelImageCmsSysResize}">
				<div class="mui-media-body">
					频道缩略图
					<p class="mui-ellipsis"></p>
				</div>
			</a>
		</li>
		
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:showImage('${Info.homeImage}')">
				<img class="mui-media-object mui-pull-right" src="${Info.homeImageCmsSysResize}">
				<div class="mui-media-body">
					首页缩略图
					<p class="mui-ellipsis"></p>
				</div>
			</a>
		</li>
		
		
		<li class="mui-table-view-cell">
			内容模型 
			<p class="text-container-right">${DataModel.modelName}</p>
		</li>
		<li class="mui-table-view-cell">
			发布时间 
			<p class="text-container-right">${Info.appearStartDateTime}</p>
		</li>
	</ul>
	<!-- hidden -->
		<form id="userDefineContentForm" name="userDefineContentForm" method="post">
			<!-- 相关数据 -->
			<cms:Token mode="html"/> 
			
			<input type="hidden" id="_jtop_sys_manage_param" name="_jtop_sys_manage_param" value="${param.manageParam}" />
			
			<input type="hidden" id="classId" name="classId" value="${param.classId}" />

			<input type="hidden" id='contentId' name="contentId" value="${param.contentId}" />

			<input type="hidden" id="modelId" name="modelId" value="${param.modelId}" />

			<input type="hidden" id="actionId" name="actionId"  />
	
			<input type="hidden" id="toStepId" name="toStepId" />

			<input type="hidden" id="fromStepId" name="fromStepId" />

			<!-- 标题样式 -->
			<input id='titleStyle' name="titleStyle" type="hidden" value="${Info.titleStyle}" />
			<input id='simpleTitleStyle' name="simpleTitleStyle" type="hidden" value="${Info.simpleTitleStyle}" />

			<!-- 推荐标志 -->
			<input id='commendFlag' name="commendFlag" type="hidden" value="${Info.commendFlag}" />

			<!-- 稿件标志 -->
			<input id='contentAddStatus' name="contentAddStatus" type="hidden" />

			<!-- 原始发布时间 -->
			<input id='cmsSysOldPublishDateTime' name="cmsSysOldPublishDateTime" type="hidden" value="${Info.appearStartDateTime}" />

			<!-- 原始发布ID -->
			<input id='cmsSysOldPublishDT' name="cmsSysOldPublishDT" type="hidden" value="${Info.pubDateSysDT}" />

			<!-- 审核标志 -->
			<input id='censorState' name="censorState" type="hidden" value="${Info.censorState}" />
			
			<!-- 工作流动作建议 -->
			<input id='jtopcms_sys_flow_suguest' name="jtopcms_sys_flow_suguest" type="hidden" value="" />

			<!-- 获取keyword临时变量 -->
			<input type="hidden" id="jtopcms_sys_keyword_content" name="jtopcms_sys_keyword_content" value="" />
			
			<!-- 相关内容 -->
			<input type="hidden" id="relateIds" name="relateIds" value="${Info.relateIds}"/>
			
			<!-- 相关调查 -->
			<input type="hidden" id="relateSurvey" name="relateSurvey" value="${Info.relateSurvey}"/>

			<!-- 同步复制栏目 -->
			<input type="hidden" id="copyClassIds" name="copyClassIds" />
			
			<!-- 共享到其他站点 -->
			<input type="hidden" id="shareSiteIds" name="shareSiteIds" />
			
			</form>
	
</div>
<body><br><br><br></body>
</html>
<script>

function openFlowStepSuggestDialog()
{
	var wsh = window.screen.height-300;
	
 
	$.dialog({ 
		id:'gscp',
    	title :'审核员建议和辅助说明',
    	width: window.screen.width+'px', 
    	height: (window.screen.height-300)+'px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,
        
        close:true,
             
        content: 'url:<cms:Domain/>core/mob/AddWorkflowActSuggest.jsp?wsh='+wsh
	});


}


function submitUserDefineContentInfo(actionId,fromStepId,toStepId,draft)
{  
	if('-1' == '${param.modelId}' || '' == '${param.modelId}')
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '170px', 
	    				height: '70px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '当前栏目不存在内容模型!',       
	    				cancel: true
	    
		});
		
		return;
	}
	
	if(notProcessMan)
	{
		$.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '当前步骤审核权已由管理员 <font color="red"> '+currentProcessMan+' </font> 获得!',
                    
                   
       					cancel: true 

		});
		return;
	
	
	}
	
	 
	
    var hasError = false;
    var currError = false;
	<cms:SystemModelFiledList modelId="${param.modelId}">
			<cms:SystemModelFiled>		
		    //if('${ModelFiled.defaultValidate}' != '')
		    //{
				<cms:FieldValidateConfig id="${ModelFiled.defaultValidate}">		
							
				currError = submitValidate('${ModelFiled.fieldSign}','${ModelFiled.isMustFill}','${Valid.regulation}','${Valid.errorMessage}');					
				
				if(currError)
				{
					hasError = true;
				}	
				
				</cms:FieldValidateConfig>
			//}
           </cms:SystemModelFiled>   
	</cms:SystemModelFiledList>	
	

    currError = submitValidate('title',1,null,null);

    if(currError)
	{
		hasError = true;
    }
    
    hasError = false;
    
    		 
    if(hasError)
    {
      
	  
	  $.dialog({ 
	   					title :'提示',
	    				width: '180px', 
	    				height: '70px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '包含未正确填写的数据,请参照提示填写正确!',
	                    
	                   
	    cancel: true
	                    
		
		
		});
	}
	else
	{
		//工作流验证
		
		var url = "<cms:BasePath/>content/checkWfStatus.do?actionId="+actionId+"&fromStepId="+fromStepId+"&toStepId="+toStepId+"&contentId=${Info.contentId}&classId=${Info.classId}&modelId=${Info.modelId}";
        
        var wfError = false;
        
        var wfLost = false;
        
        var wfAvoid = false;
        
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:false,
		       	data:'',
		   
		       	success: function(mg)
		        {        
		        	var msg = eval("("+mg+")");
		        	
		        	if('wf-empty' == msg)
		        	{
		        		wfError = true;		        			        	
		        	}
		        	else if('wf-update' == msg)
		        	{
		        		wfLost = true;		        			        	
		        	}
		        	else if('wf-avoid' == msg)
		        	{
		        		wfAvoid = true;
		        	}
		        }
		 });
		 
		 
		 
		 if(wfLost)
		 {
		 	$.dialog({ 
					  title :'提示',
					  width: '220px', 
					  height: '70px', 
					  lock: true, 
					   icon: '32X32/i.png', 
					    				
					   content: '当前内容工作流步骤或动作已缺失,将重新审核流程!',
					                    
					   
					   ok: function()
					   {
					   		window.location.reload();
					   }
					                    
						
						
					  });
		 	
		 	return;
		 }
		 
		 if(wfError)
		 {
		 	$.dialog({ 
					  title :'提示',
					  width: '180px', 
					  height: '70px', 
					  lock: true, 
					   icon: '32X32/i.png', 
					    				
					   content: '当前内容工作流状态已丢失或其他管理员已审核!',
					                    
					                   
					   ok: function()
					   {
					   		window.location.reload();
					   }
					                    
						
						
					  });
		 	
		 	return;
		 }
		 
		 if(wfAvoid)
		 {
		 	$.dialog({ 
					  title :'提示',
					  width: '180px', 
					  height: '70px', 
					  lock: true, 
					   icon: '32X32/i.png', 
					    				
					   content: '当前步骤为回避模式,您不可参与审核自己添加的内容!',
					                    
					                   
					   cancel: true
					                    
					  });
		 	
		 	return;
		 }
	
		$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	 	if(draft)
		{
			document.getElementById('contentAddStatus').value='draft';
		}
	     
		if(fromStepId==-3 && toStepId==-2)
		{
							$.dialog({ 
					   					title :'提示',
					    				width: '160px', 
					    				height: '70px', 
					                    lock: true, 
					    				icon: '32X32/i.png', 
					    				
					                    content: '您确定进入发布流程吗?',
					                    
					                    ok: function () 
					                    { 
					                    	disableAnchorElementByName("btnwithicosysflag",true);
					                     	
					                    	$.dialog.tips('正在执行...',3600000000,'loading.gif');
						
											document.getElementById('actionId').value=actionId;											
											document.getElementById('fromStepId').value=fromStepId;
									    	document.getElementById('toStepId').value=toStepId;
									    	
									    	//encodeFormInput('userDefineContentForm', false);
									    	
											userDefineContentForm.action="<cms:BasePath/>content/censorContent.do?fromCensor=true";

									  		userDefineContentForm.submit();
					                    },
					                             
					                   cancel: true
		
						
					   		});
		}
		else
		{
							disableAnchorElementByName("btnwithicosysflag",true);
						
							 
							
							$.dialog.tips('正在执行...',3600000000,'loading.gif');
						
							document.getElementById('actionId').value=actionId;
							document.getElementById('fromStepId').value=fromStepId;
							document.getElementById('toStepId').value=toStepId;
							
							//encodeFormInput('userDefineContentForm', false);
									    	
							userDefineContentForm.action="<cms:BasePath/>content/censorContent.do?fromCensor=true";							 
							
							userDefineContentForm.submit();
						
						
		}
		
		
	}
	
}

function showImage(src)
{

	if(!src.endWith('no-image.gif'))
	{
		window.location.href="<cms:BasePath/>core/mob/viewImage.jsp?src="+src;
	}
	else
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '120px', 
	    				height: '70px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '无图片!',
	                    
	                   
	    cancel: true
	                    
		
		
		});
		 
	}



}


</script>

</cms:SystemContent>
</cms:SystemDataModel>
