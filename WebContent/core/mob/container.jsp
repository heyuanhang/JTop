<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="GBK">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		
			
 
	<script type="text/javascript" src="js/player/images/swfobject.js"></script>

	<script type="text/javascript" src="js/player/cpMobile/js/action.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
	<title>JTopCMS - 移动工作台 -工作流</title>
	<link rel="stylesheet" href="css/mui.min.css">
	<style>
		.mui-card-content-inner img{
			width: 100%;
		}
		.mui-card-content-inner p {
			color: #666;
		}
		
	</style>
</head>
<body>
<cms:SystemContent modelId="${param.modelId}" id="${param.contentId}">

<header class="mui-bar mui-bar-nav">
	<a class="mui-icon mui-icon-left-nav mui-pull-left" href="<cms:BasePath/>core/mob/workb.jsp?contentId=${param.contentId}&classId=${Info.classId}&modelId=${param.modelId}"></a>
	<h1 class="mui-title">内容详情</h1>
</header>
<div class="mui-content">
<div class="mui-card">
	<div class="mui-card-header">${Info.title}</div>
	<div class="mui-card-content">
		<div class="mui-card-content-inner">
			<cms:SystemModelFiledList modelId="${param.modelId}" showMode="true">
									<cms:SystemModelFiled>
									<cms:SystemModelRowFiled >
								
									  <div class="mui-card-header">${RowFiled.showName}:</div>
									<cms:QueryData objName="MV" service="cn.com.mjsoft.cms.metadata.service.MetaDataService" method="getModelFieldInfoValTag" var="${param.modelId},${param.contentId},${RowFiled.fieldSign}">
												
											<cms:if test="${RowFiled.htmlElementId==14}">
												
												
																<!-- 轮播图开始 -->
																	<div id="slider" class="mui-slider">
																		<div class="mui-slider-group mui-slider-loop">
																			<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
																			
																			<cms:PhotoGroup group="${RowFiled.fieldSign}" contentId="${RowFiled.info.contentId}" serverMode="true">
																																		
																
																				  		<cms:if test="${status.last}">
																				  				 
																				  						<div class="mui-slider-item mui-slider-item-duplicate">
																											<a href="">
																												<img src="${Photo.url}">
																												<p class="mui-slider-title text-color-fff mui-h4 mui-text-center"></p>
																											</a>
																										</div>
																				  			 
																				  		</cms:if>
																  						  	
																		    	</cms:PhotoGroup>
																			
																	 
																			
																			<cms:PhotoGroup group="${RowFiled.fieldSign}" contentId="${RowFiled.info.contentId}" serverMode="true">
																												
																				  		
																				  		
																				  		<div class="mui-slider-item">
																							<a href="">
																								<img src="${Photo.url}">
																								<p class="mui-slider-title text-color-fff mui-h4 mui-text-center"></p>
																							</a>
																						</div>
																				  		
																				  		
																			</cms:PhotoGroup>
																			
																			
																			
																			
																			
																			
																			 
																			<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
																			<cms:PhotoGroup group="${RowFiled.fieldSign}" contentId="${RowFiled.info.contentId}" serverMode="true">
																												
																
																				  		<cms:if test="${status.first}">
																				  			 
																										<div class="mui-slider-item mui-slider-item-duplicate">
																											<a href="">
																												<img src="${Photo.url}">
																												<p class="mui-slider-title text-color-fff mui-h4 mui-text-center"></p>
																											</a>
																										</div>
																				  				 
																				  		</cms:if>
																  						  	
																		     </cms:PhotoGroup>
																			
																			
																			
																			
																			
																		</div>
																		<div class="mui-slider-indicator mui-text-center">
																		
																			<cms:PhotoGroup group="${RowFiled.fieldSign}" contentId="${RowFiled.info.contentId}" serverMode="true">
																												
																
																				  		<cms:if test="${status.first}">
																				  		
																				  				<div class="mui-indicator mui-active"></div>
																				  				
																				  		</cms:if>
																				  		<cms:else>
																				  		
																				  			<div class="mui-indicator"></div>
																				  		
																				  		</cms:else>
																  						  	
																		     </cms:PhotoGroup>
																			
																		 
																		</div>
																	</div>
																	<!-- 轮播图结束 -->
																	
																
																
												</cms:if>
												
												<cms:elseif test="${RowFiled.htmlElementId==11}">
													<img src="${MV}"/>
												</cms:elseif>
												<cms:elseif test="${RowFiled.htmlElementId==12}">
												<SCRIPT LANGUAGE=JavaScript>
													<!--
													var vID        = ""; 
													var vWidth     = "100%";
													var vHeight    = '100%';
													var vFile      = "<cms:BasePath/>core/mob/js/player/cpMobile/CuSunV2set.xml";
													var vPlayer    = "<cms:BasePath/>core/mob/js/player/cpMobile/player.swf?v=2.5";
													
												 
															
															var vPic       = "";
														
													 
														
														
													
													var vCssurl    = "<cms:BasePath/>core/mob/js/player/cpMobile/images/mini.css";
													
													//PC,安卓,iOS
													var vMp4url    = " ${MV}";
													
													//-->
													</SCRIPT>
													
													
													<script class="CuPlayerVideo" data-mce-role="CuPlayerVideo" type="text/javascript" src="<cms:BasePath/>core/mob/js/player/cpMobile/js/CuSunX1.min.js"></script>	 
				
												
												
												
												
												</cms:elseif>
												<cms:else>
												
													${MV}
												</cms:else>
												 <br/>
												 
									</cms:QueryData>
									
								
									 
								 
									</cms:SystemModelRowFiled>
									</cms:SystemModelFiled>
			</cms:SystemModelFiledList>
			
		</div>
	</div>
</div>
</div>	
</body>
<script type="text/javascript" src="js/mui.js"></script>
</html>

</cms:SystemContent>
