<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="GBK">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
 
<cms:SystemContent modelId="${param.modelId}" id="${param.contentId}">
	
	<title>JTopCMS - 移动工作台 -工作流流程记录</title>
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
		
			 
		</script>
	
</head>
<header class="mui-bar mui-bar-nav">
	<a class="mui-icon mui-icon-left-nav mui-pull-left" href="<cms:BasePath/>core/mob/censor.jsp"></a>
	<h1 class="mui-title">工作流执行记录</h1>
</header>
<!-- 底部菜单 -->


<!--  -->
<div class="mui-content">
	<div class="title">流程信息</div>
	<ul class="mui-table-view">
	
	<cms:QueryData service="cn.com.mjsoft.cms.content.service.ContentService" method="getContentOperInfoInfoList" objName="SI" var="${param.contentId},${param.pn},1" >
		
		 
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:;">
				<div class="mui-media-body">
					内容标题
					<p class="mui-ellipsis-2">${Info.title}</p>
				</div>
			</a>
		</li>
		 
		
		<li class="mui-table-view-cell">
			操作人
			<p class="text-container-right">${SI.puserName}</p>
		</li>
		 
		 
		<li class="mui-table-view-cell">
			执行动作 
			<p class="text-container-right">${SI.actionId}</p>
		</li>
		<li class="mui-table-view-cell">
			执行时间 
			<p class="text-container-right"><cms:FormatDate date="${SI.eventDT}" /></p>
		</li>
		
		<li class="mui-table-view-cell">
			备注和建议
			<p class="mui-ellipsis-3">${SI.msgContent}</p>
		</li>
		
		 
		

	 </cms:QueryData>
 	</ul>
 	
 	<cms:PageInfo>
 	 <nav class="mui-bar mui-bar-tab mui-text-center">
 
	 								 
											<cms:if test="${Page.currentPage > 1}">
																											 
											<button type="button" onclick="javascript:query('p');;" class="mui-btn">
												上一 条
											</button>
											</cms:if> 
											
											<cms:if test="${Page.currentPage+1 != Page.totalCount}">
											
											<button type="button" onclick="javascript:query('n');;" class="mui-btn">
												下一条
											</button>
											</cms:if> 
									
									
									 
									
	
	 			
</nav>
																					<tr id="pageBarTr">
																						<td colspan="8" class="PageBar" align="left">
																							<div class="fr">
																								 
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
																										
																											
																											replaceUrlParam(window.location,'tab=2&pn='+cp);		
																										}
																							
																							
																										function jump()
																										{
																											replaceUrlParam(window.location,'tab=2&pn='+document.getElementById('pageJumpPos').value);
																										}
																									</script>
																							<div class="fl"></div>
																						</td>
																					</tr>
													</cms:PageInfo>
	 
</div>
<body><br><br><br></body>
</html>
<script>


</script>

</cms:SystemContent>
 
