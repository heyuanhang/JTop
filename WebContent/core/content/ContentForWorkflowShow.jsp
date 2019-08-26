<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
			<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />			
			<cms:SystemDataModel id='${param.modelId}'>
			<title>编辑${DataModel.modelName}内容</title>
			<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
			<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
			<link href="../javascript/colorpicker/css/picker.css" type="text/css" rel="stylesheet" />
			<link rel="stylesheet" href="../style/layui/css/layui.css"  media="all">
			<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		 
			<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>

			 
			<script type="text/javascript" src="../javascript/format/editor_content_format.js"></script>
			<script type="text/javascript" src="../javascript/colorpicker/picker.js"></script>
			
			<!-- 配置文件 -->
		    <script type="text/javascript" src="../javascript/ueditor/ueditor.config.js"></script>
		    <!-- 编辑器源码文件 -->
		    <script type="text/javascript" src="../javascript/ueditor/ueditor.all.gzjs"></script>
		    
		    <script type="text/javascript" charset="utf-8" src="../javascript/ueditor/lang/zh-cn/zh-cn.js"></script>

			<script type="text/javascript" src="../javascript/uuid.js"></script>

			<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
			<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
			<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

			<script src="../javascript/laydate/laydate.js"></script>
			
			
			<script type="text/javascript" src="../javascript/area.js"></script>
			
			<cms:SystemContent modelId="${param.modelId}" id="${param.contentId}">
			<script>
			basePath='<cms:BasePath/>';
			
			 var api = frameElement.api, W = api.opener; 
			 
			 if('undefined' != typeof(W.tip))
			 {
			 	W.tip.close();
			 }
		 
			//当前管理员
			var currentManager = '';
			<cms:LoginUser>
				currentManager = '${Auth.apellation}';
			</cms:LoginUser>
			 
			//三级省市联动
			var s=[];
			
			var sv=[];
			 
			<cms:SystemModelFiledList modelId="${param.modelId}">
			<cms:SystemModelFiled>		
				
						if('${ModelFiled.fieldSign}'.endWith('_sheng'))
						{
							s[0] = '${ModelFiled.fieldSign}';
						}
						if('${ModelFiled.fieldSign}'.endWith('_shi'))
						{
							s[1] = '${ModelFiled.fieldSign}';
						}
						if('${ModelFiled.fieldSign}'.endWith('_xian'))
						{
							s[2] = '${ModelFiled.fieldSign}';
						}
			 </cms:SystemModelFiled>   
			</cms:SystemModelFiledList>
			
		 	 
		
			$(function()
			{
				
				 //颜色选择
				 $("#titleBg").bigColorpicker(
				 	"titleBgVal"
				 ); 
				 
				 $("#simpleTitleBg").bigColorpicker(
				 	"simpleTitleBgVal"
				 ); 
				 
				 //验证规则注册
				  <cms:SystemModelFiledList modelId="${param.modelId}">
						<cms:SystemModelFiled>		
					
					    
					    //{		    	
							<cms:FieldValidateConfig id="${ModelFiled.defaultValidate}">	
							
							validate('${ModelFiled.fieldSign}','${ModelFiled.isMustFill}','${Valid.regulation}','${Valid.errorMessage}');	
							//alert('${ModelFiled.fieldSign} + ${ModelFiled.isMustFill} + ${Valid.regulation} + ${Valid.errorMessage}');				
							</cms:FieldValidateConfig>
						//}
						
	                   </cms:SystemModelFiled>   
				</cms:SystemModelFiledList>
	
				//标题不可为空
 				validate('title',1,null,null);	
 				
 					
 				
 			 
 				
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
			
		 	
			 if("true"==="${param.fromFlow}" )
	         {  
	         	var tipCon = '下线内容成功，请返回管理页!';
	         	
	         	if("delete"==="${param.workflow}")
	         	{
	         	   tipCon = '删除内容成功，请返回管理页!';
	         	}
	         
				var tipdia = W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '210px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: tipCon,
	
			    				  button: [
							        {
							            name: '返回管理',
							            callback: function () {
							            
							                 backManagePage();
							            }
							        } 
							     ]
			   });     
	         	 
	       		       
	         }
			
			
			
		</script>
	</head>
	<body>
		
		<form id="userDefineContentForm" name="userDefineContentForm" method="post">
			 
		 
			<!--新添加修稿20130804-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						 
						
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
								
								<cms:if test="${DataModel.titleMode==1}">
								<tr>
									<td  class="input-title listdate-show-data">
										 标题:
										  
									</td>
									<td class="td-input listdate-show-data">
										<input id="title" name="title" type="text" size="85" maxlength="120" style="width: 545px;${Info.titleStyle}" class="form-input-title titlerule" onkeyup="textCounter(this, 'currCharLen');" value="${Info.title}" />
										<span class="red">*</span>
										<font><span id="currCharLen">&nbsp;0</span>字</font>&nbsp;&nbsp;&nbsp;
										<img src="../../core/style/blue/icons/edit_r1_c7.jpg" height="16" width="16" title="颜色" id="titleBg" class="cursor img-icon" />
										<input type="hidden" id="titleBgVal" name="titleBgVal" value="" />
										<img src="../../core/style/blue/icons/edit_r1_c1.jpg" height="16" width="16" title="粗体" id="strongTitle" onclick="javascript:setTitleChar(this);" class="cursor img-icon" />
										<img src="../../core/style/blue/icons/edit_r1_c3.jpg" height="16" width="16" title="斜体" id="emTitle" onclick="javascript:setTitleChar(this);" class=" cursor img-icon" />
										<img src="../../core/style/blue/icons/edit_r1_c5.jpg" height="16" width="16" title="下划线" id="underTitle" onclick="javascript:setTitleChar(this);" class=" cursor img-icon" />&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="checkbox" size="55" id="simpleT"  class="form-checkbox img-icon" onclick="javascript:showTitleInput('simpleTitle','shortTitle',this);" />&nbsp;&nbsp;
										辅标
									</td>
								</tr>
								<tr id="simpleTitleTr" style="display:none">
									<td class="input-title listdate-show-data">
										副标:
									</td>
									<td class="td-input listdate-show-data">
										<input id="simpleTitle" name="simpleTitle" type="text" style="width: 545px;" class="form-input-title titlerule" onkeyup="textCounter(this, 'currSimpleCharLen');" size="55" maxLength="60" value="${Info.simpleTitle}" />
										<font face="微软雅黑"><span id="currSimpleCharLen">0</span>字</font>
										<img src="../../core/style/blue/icons/edit_r1_c7.jpg" height="16" width="16" title="颜色" id="simpleTitleBg" class="cursor img-icon" />
										<input type="hidden" id="simpleTitleBgVal" name="simpleTitleBgVal" />
										<img src="../../core/style/blue/icons/edit_r1_c1.jpg" height="16" width="16" title="粗体" id="strongSimpleTitle" onclick="javascript:setSimpleTitleChar(this);" class="cursor img-icon" />
										<img src="../../core/style/blue/icons/edit_r1_c3.jpg" height="16" width="16" title="斜体" id="emSimpleTitle" onclick="javascript:setSimpleTitleChar(this);" class="cursor img-icon" />
										<img src="../../core/style/blue/icons/edit_r1_c5.jpg" height="16" width="16" title="下划线" id="underSimpleTitle" onclick="javascript:setSimpleTitleChar(this);" class="cursor img-icon" />
										<span class="ps">简化的标题语句</span>
									</td>

								</tr>
								<tr id="shortTitleTr" style="display:none">
									<td class="input-title listdate-show-data">
										短标:
									</td>
									<td class="td-input listdate-show-data">
										<input id="shortTitle" name="shortTitle" type="text" style="width: 545px;" class="form-input-title titlerule" onkeyup="textCounter(this, 'currShortCharLen');" size="55" maxLength="60" value="${Info.shortTitle}" />
										<font face="微软雅黑"><span id="currShortCharLen">0</span>字</font>&nbsp;
										<span class="ps">起主题描叙,一般为简单短语</span>
									</td>
								</tr>
								</cms:if>
								

								<tr>
									<td id="main-td" class="input-title listdate-show-data">
										 
										<script>
											 var innerW = document.body.scrollWidth;
											 
											 if('' != '${param.innerWidth}')
											 {
											 	innerW = parseInt('${param.innerWidth}');
											 }
											 
											 
											 var hx = (innerW - 795) / 2;
											 
											 $('#main-td').attr('width', hx+'');
									     </script>
									</td>
									<td class="td-input listdate-show-data">
										<cms:SystemAssiInfo contentId="${Info.contentId}">
											<span><img src="../../core/style/blue/icon/documents-text.png" id="sysCopyContentImg" title="复制到:  ${AssiInfo.copyClassStr}" height="16" width="16" class="img-icon" /><a class="a_style" href="javascript:openCopyContentToSiteClassDialog('single','${param.modelId}','${AssiInfo.copyClassStr}','${Info.siteId}');">&nbsp;复制内容</a>[<font color=red><span id='sysCopyClassCount'>${AssiInfo.copyClassCount}</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
										</cms:SystemAssiInfo>
										<span>
										<cms:SystemAssiInfo contentId="${Info.contentId}" mode="share">
										<img src="../../core/style/blue/icon/sitemap-application.png" height="16" width="16" class="img-icon" id="sysShareContentImg" title="共享到:  ${AssiInfo.shareSiteStr}" /><a class="a_style" href="javascript:openSelectSiteGroupDialog('${Info.contentId}','');">&nbsp;站群共享</a>[<font color=red><span id='shareSiteIdCount'>${AssiInfo.shareSiteCount}</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</cms:SystemAssiInfo>
										<span><img src="../../core/style/blue/icon/document-tree.png" height="16" width="16" class="img-icon" /><a class="a_style" href="javascript:openSelectRelatedContentDialog('${Info.contentId}','${param.classId}');">&nbsp;相关内容</a>[<font color=red><span id='sysRelateCount'>0</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
										<span><img src="../../core/style/icons/report.png" height="16" width="16" class="img-icon" /><a class="a_style" href="javascript:openSelectSurveyDialog('${Info.contentId}', '${param.classId}');">&nbsp;所属调查</a>[<font color=red><span id='sysRelateSurveyCount'>0</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
										<input type="checkbox" class="form-checkbox" name="allowCommend" value="0"></input>
										禁止评论&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
										<input type="checkbox" class="form-checkbox" name="topFlag" value="1"></input>
										内容置顶&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="checkbox" class="form-checkbox" id="outlinkc" onclick="javascript:showOutLink(this);"></input>
										外链跳转
									</td>
								</tr>
								
								<tr id="outlinkTr" style="display:none">
									<td class="input-title listdate-show-data">
										外链地址:
									</td>
									<td class="td-input listdate-show-data">
										<input id="outLink" name="outLink" type="text"  style="width: 752px;" class="form-input"  value="<cms:if test="${empty Info.outLink}">http://</cms:if><cms:else>${Info.outLink}</cms:else>" />
									</td>
								</tr>
								

								 
								
								
								<cms:if test="${DataModel.kwMode==1}">
								<tr>
									<td  class="input-title listdate-show-data">
										TAG:
										
									</td>
									<td class="td-input listdate-show-data">
											<cms:QueryData objName="TagStr" service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getInfoTagKeyStrQueryTag" var="${Info.tagKey}">
											<input readonly id="tagKeyVal" name="tagKeyVal" type="text" class="form-input" style="width: 252px;" maxlength="300"  value="${TagStr}"/>
											</cms:QueryData>
											<input id="tagKey" name="tagKey" type="hidden" class="form-input"  value="${Info.tagKey}" /><button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:openSelectTagDialog();"  > 选择</button>
											
											
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span class="input-title">关键字:</span>
										<input id="keywords" name="keywords" type="text" class="form-input" style="width: 252px;" maxlength="200" value="${Info.keywords}" />
											<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:getKeywordFromContent();" >获取</button>
										
										
									</td>
								</tr>
								</cms:if>
								
								<cms:if test="${DataModel.titleMode==1}">
								<tr>
									<td  class="input-title listdate-show-data">
										作者:
										 
									</td>
									<td class="td-input listdate-show-data">
									    <cms:LoginUser>
										<input id="creator" name="creator" readonly value="${Info.creator}" type="text" class="form-input"  style="width: 252px;" maxlength="58" />
										</cms:LoginUser>
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 	
										<span class="input-title">内容来源:</span>
										 	<input name="author" id="author" type="text" class="form-input" style="width: 252px;" maxlength="50" value="${Info.author}"/>
										<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:openSelectContentSourceDialog();"  >选取</button>
									
									</td>
								</tr>
								
								
								 
								<tr>
									<td class="input-title listdate-show-data">
										摘要:
									</td>
									<td class="td-input listdate-show-data">
										<textarea id="summary" name="summary" style="height:65px;width:748px" class="form-textarea">${Info.summary}</textarea>
									</td>
								</tr>
								</cms:if>
							</table>

							<!-- 自定义模型字段区域 -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
								<cms:SystemModelFiledList modelId="${param.modelId}" showMode="true">
									<cms:SystemModelFiled>
										<tr>
											<td class="input-title listdate-show-data" id="data-td"  >
											 <script>
											 
											 
											 $('#data-td').attr('width', hx+'');
											 </script>
												<cms:SystemModelRowFiled >
													<!-- 图集字段区域 -->
													<cms:if test="${RowFiled.htmlElementId==14}">
													${RowFiled.showName}:										
										 
											</td>
											<td class="td-input listdate-show-data">
												<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload" style="padding-top:0px;">
													<tr>
														<td>
															<!-- 图集操作 -->
															<table width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-top:0px;">
																<tr style="padding-top:0px;">
																	<td style="padding-top:0px;">																	
															                <a onclick="javascript:deleteAllGroupPhoto('${RowFiled.fieldSign}');" class="btnwithico"> <img src="../../core/style/icons/image--minus.png" alt="" /><b>全部删除&nbsp;</b> </a>
																			<a onclick="javascript:showModuleImageGroupDialog('${RowFiled.fieldSign}');" class="btnwithico"> <img src="../../core/style/icons/images.png" alt="" /><b>多图上传&nbsp;</b> </a>
																	</td>
																	<td style="padding-top:0px;">
																		&nbsp;&nbsp;<input type="text" style="width:63px;" id="${RowFiled.fieldSign}CmsSysMaxWidth" class="form-input" value="${RowFiled.fieldInfo.imageW}"></input>
																		宽度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width:63px;" id="${RowFiled.fieldSign}CmsSysMaxHeight" class="form-input" value="${RowFiled.fieldInfo.imageH}"></input>
																		高度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select class="form-select" id="${RowFiled.fieldSign}CmsSysDisposeMode">
																			<option value="0">
																				原宽高
																			</option>
																			<option value="1">
																				按宽度
																			</option>
																			<option value="2">
																				按高度
																			</option>
																			<option value="3">
																				按宽高&nbsp;&nbsp;&nbsp;&nbsp;
																			</option>
																		</select>
																		缩放&nbsp;&nbsp;&nbsp;&nbsp;水印:<input class="form-checkbox" disabled type="checkbox" value="1" id="${RowFiled.fieldSign}CmsSysNeedMark" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图片数量 [ <font color=red><span id="${RowFiled.fieldSign}CmsSysImageGroupCount">-1</span></font> ]&nbsp;张

																		<script>
			
																		initSelect('${RowFiled.fieldSign}CmsSysDisposeMode','${RowFiled.fieldInfo.imageDisposeMode}');	
																		initRadio('${RowFiled.fieldSign}CmsSysNeedMark','${RowFiled.fieldInfo.needMark}');	
												
																		</script>
																	</td>
																	
																</tr>
																
																

															</table>
															<!-- 图集操作结束 -->
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<!-- 图集内容开始 -->
										<tr>
											<td class="input-title listdate-show-data" id="data-td2" >
											<script>
											 
											 
											 $('#data-td2').attr('width', hx+'');
											 </script>
												<!-- 图集内容空标题 -->
											</td>
											<td class="td-input listdate-show-data" style="padding-top:0px;">
												<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload" style="padding-top:0px;">
													<tr style="padding-top:0px;">
														<td style="padding-top:0px;">
															<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageCurrentCount" name="${RowFiled.fieldSign}CmsSysImageCurrentCount" value="0" />
															<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageCover" name="${RowFiled.fieldSign}CmsSysImageCover" value="" />
															<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageArrayLength" name="${RowFiled.fieldSign}CmsSysImageArrayLength" value="0" />

															<div id="${RowFiled.fieldSign}CmsSysImageUploadTab">
																<!-- 图片信息区 -->
																<script>
														
																//派序用

																allImageGroupSortInfo['${RowFiled.fieldSign}'] = new Array();
																
																<cms:PhotoGroup group="${RowFiled.fieldSign}" contentId="${RowFiled.info.contentId}" serverMode="true">
																
																	addGroupPhotoToPage('${RowFiled.fieldSign}','${RowFiled.fieldInfo.imageW}','${RowFiled.fieldInfo.imageH}','${Photo.orderFlag}','${Photo.url}','${Photo.resizeUrl}','${Photo.resId}','${Photo.reUrl}','${Photo.photoName}','${Photo.height}','${Photo.width}','${Photo.photoDesc}');
																	//封面
																	if('${Photo.isCover}' == 1)
																	{
																		document.getElementById('${RowFiled.fieldSign}-cover-${Photo.orderFlag-1}').checked = true;
																		$("#${RowFiled.fieldSign}CmsSysImageCover").val('${Photo.reUrl}');
																	}	
																
																</cms:PhotoGroup>
																
																//图片数
																document.getElementById('${RowFiled.fieldSign}CmsSysImageGroupCount').innerHTML = document.getElementById('${RowFiled.fieldSign}CmsSysImageCurrentCount').value;
																
																var sortArray = allImageGroupSortInfo['${RowFiled.fieldSign}'];
																
																document.getElementById('${RowFiled.fieldSign}CmsSysImageArrayLength').value = allImageGroupSortInfo['${RowFiled.fieldSign}'].length;
																
																 
																</script>
															</div>
															
														
														
														
														

														<!-- 图集内容结束 -->
														</cms:if>

														<!-- 普通字段区域开始 -->
														<cms:else>
															<cms:if test="${status.index == 0}">
															${RowFiled.showName}:										
														</td>
																<td class="td-input listdate-show-data">
																
																	<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																		<tr>
																			<td>
																				<table  border="0"  cellpadding="0" cellspacing="0">
																									  <tr>
																									 	<td>
																									 		<div id="sys-obj-${RowFiled.fieldSign}">${RowFiled.editModeLayoutHtml}
																											<cms:if test="${RowFiled.isMustFill==1}">
																												<span class="red">*</span>
																												<span class="ps"></span>
																											</cms:if>
																											</div>
																									 	</td>
																									 	<td>
																									 		<div style="width:${RowFiled.blankCount}px;"></div>
																									 	</td>
																									 </tr>
																								 
																				</table>
																				
																			</td>
																			
																			</cms:if>
																			<cms:else>
																				<td>
																					<span class="input-title  ">${RowFiled.showName}:</span>&nbsp;
																				</td>
																				<td>
																					<table  border="0"  cellpadding="0" cellspacing="0">
																									  <tr>
																									 	<td>
																									 		<div id="sys-obj-${RowFiled.fieldSign}">${RowFiled.editModeLayoutHtml}
																											<cms:if test="${RowFiled.isMustFill==1}">
																												<span class="red">*</span>
																												<span class="ps"></span>
																											</cms:if>
																											</div>
																									 	</td>
																									 	<td>
																									 		<div style="width:${RowFiled.blankCount}px;"></div>
																									 	</td>
																									 </tr>
																								 
																					</table>
																					
																				</td>
																				
																			</cms:else>
																			</cms:else>
																			
																			<script>
																				 
																				if('${RowFiled.fieldSign}'.endWith('_sheng'))
																				{
																					sv[0] = '<cms:Get objName="Info" fieldName="${RowFiled.fieldSign}"/>';
																				}
																				else if('${RowFiled.fieldSign}'.endWith('_shi'))
																				{
																					sv[1] = '<cms:Get objName="Info" fieldName="${RowFiled.fieldSign}"/>';
																				}
																				else if('${RowFiled.fieldSign}'.endWith('_xian'))
																				{
																					sv[2] = '<cms:Get objName="Info" fieldName="${RowFiled.fieldSign}"/>';
																				}
																				 
																				</script>
																			</cms:SystemModelRowFiled>
																		</tr>
																	</table>
																	
																</td>
													</tr>
													</cms:SystemModelFiled>
													</cms:SystemModelFiledList>
												</table>
										<tr>
											<td height="5"></td>
										</tr>
							</table>
							<div style="height:5px"></div>

							<div class="addtit">
								&nbsp;<img src="../../core/style/icons/gear.png" width="16" height="16" />高级选项
							</div>
							<cms:SysClass id="${param.classId}">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="form-table-big-content">
									<cms:if test="${DataModel.titleMode==1}">
									<tr>
										<td   class="input-title listdate-show-data" >
										
											
											内容缩略图:
										</td>
										<td class="td-input listdate-show-data">
											<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
												<tr>
													<td width="42%">
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="contentImageCmsSysShowSingleImage" href="${Info.contentImage}"><img id="contentImageCmsSysImgShow" src="${Info.contentImageCmsSysResize}" width="90" height="67" /> </a>
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																		<tr>
																			<td>
																				&nbsp;
																				<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs" onclick="javascript:showModuleImageDialog('contentImageCmsSysImgShow','contentImage','${Class.contentImageW}','${Class.contentImageH}','1','0')"     > 上传</button>
																				<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:disposeImage('contentImage','${Class.contentImageW}','${Class.contentImageH}',false,'-1');" >裁剪</button>
																				<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"  onclick="javascript:deleteImage('contentImage');"  >删除</button>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;&nbsp;宽&nbsp;&nbsp;
																				<input id="contentImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Info.contentImageImageW}" />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="contentImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Info.contentImageImageH}" />


																			</td>
																		</tr>
																	</table>
																	<input id="contentImage" name="contentImage" type="hidden" value="${Info.contentImageResId}" />
																	<input id="contentImage_sys_jtopcms_old" name="contentImage_sys_jtopcms_old" type="hidden" value="${Info.contentImageResId}"/>
																</td>
															</tr>
														</table>
													</td>
													<td width="11%">

													</td>
													<td>
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td class="input-title">
																	栏目缩略图:&nbsp;&nbsp;&nbsp;
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																		<tr>
																			<td>
																				<a class="cmsSysShowSingleImage" id="classImageCmsSysShowSingleImage" href="${Info.classImage}"><img id="classImageCmsSysImgShow" src="${Info.classImageCmsSysResize}" width="90" height="67" /> </a>
																			</td>
																			<td>
																				<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																					<tr>
																						<td>
																							&nbsp;
																							<button  type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs" onclick="javascript:showModuleImageDialog('classImageCmsSysImgShow','classImage','${Class.listImageW}','${Class.listImageH}','1','0')"   > 上传 </button>
																							<button  type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"  onclick="javascript:disposeImage('classImage','${Class.listImageW}','${Class.listImageH}',false,'-1');"  > 裁剪 </button>
																							<button  type="button"  style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:deleteImage('classImage');"  > 删除</button>
																						</td>
																					</tr>
																					<tr>
																						<td>
																							&nbsp;&nbsp;宽&nbsp;&nbsp;
																							<input id="classImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Info.classImageImageW}" />
																							&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																							<input id="classImageCmsSysImgHeight" class="form-input" readonly type="text"style="width:44px" value="${Info.classImageImageH}" />
																						</td>
																					</tr>
																				</table>
																				<input id="classImage" name="classImage" type="hidden" value="${Info.classImageResId}" />
																				<input id="classImage_sys_jtopcms_old" name="classImage_sys_jtopcms_old" type="hidden" value="${Info.classImageResId}"/>
																			</td>
																		</tr>
																	</table>

																</td>
															</tr>
														</table>

													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td class="input-title listdate-show-data">
											频道缩略图:
										</td>
										<td class="td-input listdate-show-data" >
											<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
												<tr>
													<td width="42%">
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="channelImageCmsSysShowSingleImage" href="${Info.channelImage}"><img id="channelImageCmsSysImgShow" src="${Info.channelImageCmsSysResize}" width="90" height="67" /> </a>
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																		<tr>
																			<td>
																				&nbsp;
																				<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs" onclick="javascript:showModuleImageDialog('channelImageCmsSysImgShow','channelImage','${Class.classImageW}','${Class.classImageH}','1','0')"      >上传</button>
																				<button type="button" type="button"style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:disposeImage('channelImage','${Class.classImageW}','${Class.classImageH}',false,'-1');"   >裁剪</button>
																				<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"  onclick="javascript:deleteImage('channelImage');"   >删除</button>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;&nbsp;宽&nbsp;&nbsp;
																				<input id="channelImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Info.channelImageImageW}" />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="channelImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Info.channelImageImageH}" />


																			</td>
																		</tr>

																	</table>
																	<input id="channelImage" name="channelImage" type="hidden" value="${Info.channelImageResId}" />
																	<input id="channelImage_sys_jtopcms_old" name="channelImage_sys_jtopcms_old" type="hidden" value="${Info.channelImageResId}"/>
																</td>
															</tr>
														</table>
													</td>
													<td width="11%">

													</td>
													<td>
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td class="input-title ">
																	首页缩略图:&nbsp;&nbsp;&nbsp;
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																		<tr>
																			<td>
																				<a class="cmsSysShowSingleImage" id="homeImageCmsSysShowSingleImage" href="${Info.homeImage}"><img id="homeImageCmsSysImgShow" src="${Info.homeImageCmsSysResize}" width="90" height="67" /> </a>
																			</td>
																			<td>
																				<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																					<tr>
																						<td>
																							&nbsp;
																							<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs" onclick="javascript:showModuleImageDialog('homeImageCmsSysImgShow','homeImage','${Class.homeImageW}','${Class.homeImageH}','1','0')"   >上传</button>
																							<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs" value="裁剪" onclick="javascript:disposeImage('homeImage','${Class.homeImageW}','${Class.homeImageH}',false,'-1');"   >裁剪</button>
																							<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs" value="删除" onclick="javascript:deleteImage('homeImage');"   >删除</button>
																						</td>
																					</tr>
																					<tr>
																						<td>
																							&nbsp;&nbsp;宽&nbsp;&nbsp;
																							<input id="homeImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Info.homeImageImageW}" />
																							&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																							<input id="homeImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Info.homeImageImageH}" />


																						</td>
																					</tr>
																				</table>
																				<input id="homeImage" name="homeImage" type="hidden" value="${Info.homeImageResId}" />
																				<input id="homeImage_sys_jtopcms_old" name="homeImage_sys_jtopcms_old" type="hidden" value="${Info.homeImageResId}"/>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>

													</td>
												</tr>
											</table>
										</td>
									</tr>
									</cms:if>
									
									<tr>
										<td class="input-title listdate-show-data">
											发布时间:
										</td>
										<td class="td-input listdate-show-data ">
											<input id="appearStartDateTime" name="appearStartDateTime" style="width: 165px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${Info.appearStartDateTime}" />
											&nbsp;至&nbsp;
											<input id="appearEndDateTime" name="appearEndDateTime" style="width: 165px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${Info.appearEndDateTime}" />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span class="input-title">内容属性:&nbsp;&nbsp;</span>
											<select name="typeFlag" id="typeFlag" class="form-select" style="width: 262px;">
												<option value="-1">
													----------- 所属信息分类 -----------
												</option>
												<cms:SystemContentTypeList>
													<option value="${ContentType.typeFlag}">
														${ContentType.typeName}
													</option>
												</cms:SystemContentTypeList>
											</select>
										</td>
									</tr>
									
									<tr>
										<td class="input-title listdate-show-data">
											添加时间:
										</td>
										<td class="td-input listdate-show-data ">
											<input id="addTime" name="addTime" style="width: 165px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${Info.addTime}"  />
											 <span class="ps">添加时可人工编辑，默认为当前时间 </span>
											 
											 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span class="input-title">搜索权重:&nbsp;&nbsp;</span><input name="boost" id="boost" type="text" class="form-input" style="width: 245px;" maxlength="22" value="${Info.boost}" />
											 
											 
										</td>
									</tr>
									
									<tr>
										<td class="input-title listdate-show-data" id="data-td3">
										<script>
											 
											 
											 $('#data-td3').attr('width', (hx+8)+'');
											 </script>
											独有模板:
										</td>
										<td class="td-input listdate-show-data">
											<input id="especialTemplateUrl" name="especialTemplateUrl" type="text" class="form-input" style="width: 330px;" maxlength="320" value="${Info.especialTemplateUrl}" />
											<select class="form-select" onchange="javascript:selectRule(this,'especialTemplateUrl')">
												<option value="-1">
													备选参数
												</option>
												<option value="{content-id}">
													内容ID
												</option>

												<option value="{page}">
													分页数
												</option>
											</select>
											<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:openSelectTempletDialog('content', 'especialTemplateUrl');"   >模板...</button>
											&nbsp;
											<span class="ps">若选择独有模板,发布内容时将使用此模板 </span>
										</td>
									</tr>
								</table>
							</cms:SysClass>
					</td>
				</tr>
				
				


			</table>

			<!-- hidden -->

			<!-- 相关数据 -->
			<cms:Token mode="html"/> 
			
			<input type="hidden" id="_jtop_sys_manage_param" name="_jtop_sys_manage_param" value="${param.manageParam}" />
			
			<input type="hidden" id="classId" name="classId" value="${param.classId}" />

			<input type="hidden" id='contentId' name="contentId" value="${param.contentId}" />

			<input type="hidden" id="modelId" name="modelId" value="${param.modelId}" />
			
			<input type="hidden" id="flowTarget" name="flowTarget"  />
 
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
			<cms:QueryData objName="Rcid" service="cn.com.mjsoft.cms.content.service.ContentService" method="getRelateContentQueryTag" var="${Info.contentId},all,">						
			<input type="hidden" id="relateIds" name="relateIds" value="${Rcid}"/>
			</cms:QueryData>
			
			<!-- 相关调查 -->
			<input type="hidden" id="relateSurvey" name="relateSurvey" value="${Info.relateSurvey}"/>

			<!-- 同步复制栏目 -->
			<input type="hidden" id="copyClassIds" name="copyClassIds" />
			
			<!-- 共享到其他站点 -->
			<input type="hidden" id="shareSiteIds" name="shareSiteIds" />
			
			<cms:if test="${DataModel.titleMode==0}">			
				<input type="hidden" id="title" name="title" value="${Info.title}"/>
			</cms:if>
		</form>

		<!--提交-->
		<div style="height:60px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
				<tr class="btnbg100">
					 
					<td style="text-align:center">
					
						<table style="margin:auto;" border="0" cellspacing="0" cellpadding="0"  >
							<tr>
							
								<td>
								 

									<a href="javascript:backManagePage();"  class="btnwithico"   ><img src="../style/icon/arrow-return-180-left.png" width="16" height="16"/><b>返回&nbsp;</b> </a>
								</td>

							</tr>

						</table>
						
						</td>
				</tr>
				 
			</table>
			 <div style="height:5px;"></div>
		</div>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">
 
//图片查看效果
loadImageShow();

init();
					 			
//三级联动省市				 
setup();

if(s[0] != null)
{
	initSelect(s[0]+'', sv[0]+'');
	
	if(s[1] != null)
 	{
      change(1);
    }
}
 
if(s[1] != null)
{
	initSelect(s[1],sv[1]);
	if(s[2] != null)
 	{
      change(2);
    }
}

if(s[2] != null)
{
	initSelect(s[2],sv[2]);
}
					
					
					

 
  
function init()
{	
	initRadio('allowCommend','${Info.allowCommend}');
	
	initRadio('topFlag','${Info.topFlag}');
	
	initSelect('typeFlag','${Info.typeFlag}');
	
	var ridArray = $('#relateIds').val().split('-');
	
	var count = 0;
	
    for ( var i = 0; i < ridArray.length; i++ )
    {
    	 
    	
        idStr = ridArray[i];
				
		var isa = idStr.split(':');
		 
		if(typeof(isa[1]) != 'undefined')
		{
			var infoa = isa[1].split('_');
			 
			for ( var j = 0; j < infoa.length; j++ )
	    	{
	    		var info = infoa[j];
	    		//alert(info);
				 if(info != '')
				{	
		              
		            count++;
		        }
	        }
        
        }
        
    }
  
    document.getElementById('sysRelateCount').innerHTML = count;
  
    //相关内容
    ridArray = '${Info.relateSurvey}'.split('*');
	
	count = 0;
	
    for ( var i = 0; i < ridArray.length; i++ )
    {
    	if(i == 10)
    	{
    		break;//最大关联限制
    	}
    	
        idStr = ridArray[i];
				
		if(idStr != '')
		{	
            
            count++;
        }
    }
    
    document.getElementById('sysRelateSurveyCount').innerHTML = count;
    
    //outlink
   
    if('${Info.outLink}' != '')
    {
    	document.getElementById('outlinkc').checked=true;
    	$('#outlinkTr').show();
    }
    
    //防止引用的内容进入
	if('${empty Info.linkCid}' != 'true')
	{
     // window.location='ManageGeneralContent.jsp?classId=${param.classId}';
	}
    
    //防止不是自己的稿件进入,会员退稿除外，退稿由提示框中退出
	if('${Info.censorState}' == '-1' && '${Info.creator}' != currentManager && '${Info.otherFlag}' != '1')
	{
	   //W.window.location.reload();
     // window.location='ManageGeneralContent.jsp?classId=${param.classId}';
	}
	
	 //simpleTitle
   
    if('${Info.simpleTitle}' != '' || '${Info.shortTitle}' != '')
    {
    	document.getElementById('simpleT').checked=true;
    	$('#simpleTitleTr').show();
    	$('#shortTitleTr').show();
    }
	
	//提示
	if('true' == '${param.prePub}')
	{
		W.$.dialog.tips('进入工作流成功',2,'32X32/succ.png'); 
	}
	else
	{
		<cms:SystemWorkflowAction actId="${param.actId}">
		
		if('1' == '${Action.conjunctManFlag}')
		{
			//会签模式
			var info;
			
			if('1' == '${param.actSuccess}')
			{
				info = '<font color="red">[${Action.passActionName}]</font> 成功!';		
			}
			else
			{
				info = '<font color="red">[${Action.passActionName}]</font> 成功! <br/>由于当前动作为会签,需等待全体通过生效';	
			}
			
			
			W.$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				 parent:api,
                    content: info,
   
                    ok: true 
			});

			
		}
		else
		{  
	 		tipdia.close();
	 		  
			W.$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				 parent:api,
                    content: '<font color="red">[${Action.passActionName}]</font> 成功!',
   					
   					button: 
   					[
							        {
							            name: '返回管理',
							            callback: function () {
							          
							                backManagePage();
							            }
							        },
							        {
							            name: '继续编辑',
							            callback: function () {
							                 if(currentManager && '${Info.otherFlag}' == '1' && '${Info.censorState}' == '-1')
					                    	{
					                    
					                    		//window.location='ManageGeneralContent.jsp?classId=${param.classId}';
					                    	}
							            }
							             
							        }
					
					]
                    
			});

			
		}
		
		</cms:SystemWorkflowAction>
	}
}

function submitUserDefineContentInfo(actionName, actionId,fromStepId,toStepId,draft)
{  
	if('-1' == '${param.modelId}' || '' == '${param.modelId}')
	{
		W.$.dialog({ 
	   					title :'提示',
	    				width: '170px', 
	    				height: '70px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				 parent:api,
	                    content: '当前栏目不存在内容模型!',       
	    				cancel: true
	    
		});
		
		return;
	}

	if(notProcessMan)
	{
		W.$.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				 parent:api,
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
	
	if('${DataModel.titleMode}' == '1')
	{
	    currError = submitValidate('title',1,null,null);
	
	    if(currError)
		{
			hasError = true;
	    }
    }
    
   
    if(hasError)
    {
   
	  
	  W.$.dialog.tips('包含未正确填写的数据,请参照提示填写正确!',2,'');
		
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
		 	W.$.dialog({ 
					  title :'提示',
					  width: '220px', 
					  height: '70px', 
					  lock: true, 
					   icon: '32X32/i.png', 
					     parent:api,				
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
		 	W.$.dialog({ 
					  title :'提示',
					  width: '180px', 
					  height: '70px', 
					  lock: true, 
					   icon: '32X32/i.png', 
					  parent:api,			
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
		 	W.$.dialog({ 
					  title :'提示',
					  width: '180px', 
					  height: '70px', 
					  lock: true, 
					   icon: '32X32/i.png', 
					    parent:api, 				
					   content: '当前步骤为回避模式,您不可参与审核自己添加的内容!',
					                    
					                   
					   cancel: true
					                    
					  });
		 	
		 	return;
		 }
		 
		W.tip =  W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
		 
		//后台验证
		url = "<cms:BasePath/>content/contentValidate.do";
	    var postData = encodeURI($("#userDefineContentForm").serialize());
	    
	     postData = postData.replace(/\+/g, " ");
         postData = encodeData(postData);
	
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:true,
		       	data:postData,
		       	dataType:'json',
		   
		       	success: function(msg)
		        {     
		        	hasError = false;
		             		           	
		            if(msg != null && msg != '')
		            {			             
			            for(var key in msg)
			            { 
		　　					hasError = true;	
							showTips(key+'',msg[key]+'');
						}
					}
	
		            if(hasError)
		            {
		       
	  
					   W.$.dialog({ 
					   					title :'提示',
					    				width: '180px', 
					    				height: '70px', 
					                    lock: true, 
					    				icon: '32X32/i.png', 
					    				 parent:api,
					                    content: '包含未正确填写的数据,请参照提示填写正确!',
					                    
					                   
					                   cancel: function()
					                   {
					                   		W.tip.close();
					                   }
					                    
						
						
					    });
		            
		            }
		            else
		            {
		            	
		             
		            	if(draft)
						{
							document.getElementById('contentAddStatus').value='draft';
						}
						
					 
						
						var olv = document.getElementById('outLink');
						
						if('' == olv.value || 'http://' == olv.value)
						{
							olv.value = '';
						}
						 
						if(fromStepId==-3 && toStepId==-2)
						{    
		        
							W.$.dialog({ 
					   					title :'提示',
					    				width: '160px', 
					    				height: '70px', 
					                    lock: true, 
					    				icon: '32X32/i.png', 
					    				 parent:api,
					                    content: '您确定进入审核流程吗?',
					                    
					                    ok: function () 
					                    { 
					                    	disableAnchorElementByName("btnwithicosysflag",true);
					                    	
					                    	if('${DataModel.titleMode}' == '1')
											{
												disposeTitleStyle();
											}
					                    	
					                    	
						
											document.getElementById('actionId').value=actionId;
											document.getElementById('fromStepId').value=fromStepId;
									    	document.getElementById('toStepId').value=toStepId;
									    	
									    	if( '进入发布流程' == actionName)
									    	{
									    		document.getElementById('flowTarget').value = 'publish';
									    	}
									    	else if( '进入删除流程' == actionName)
									    	{
									    		document.getElementById('flowTarget').value = 'delete';
									    	}
									    	else if( '进入下线流程' == actionName)
									    	{
									    		document.getElementById('flowTarget').value = 'offline';
									    	}
									    	
									    	encodeFormInput('userDefineContentForm', false);
									    	 
											userDefineContentForm.action="<cms:BasePath/>content/editContent.do?prePub=true";
										
									  		userDefineContentForm.submit();
					                    },
					                             
					                   cancel: function()
					                   {
					                   		W.tip.close();
					                   }
		
						
					   		});
						}
						else
						{ 
							disableAnchorElementByName("btnwithicosysflag",true);
						
							if('${DataModel.titleMode}' == '1')
							{
								disposeTitleStyle();
							}
							
							document.getElementById('actionId').value=actionId;
							document.getElementById('fromStepId').value=fromStepId;
							document.getElementById('toStepId').value=toStepId;	
							
							W.tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
								
							 
							 
							encodeFormInput('userDefineContentForm', false);
								 
							userDefineContentForm.action="<cms:BasePath/>content/editContent.do";
							  
							userDefineContentForm.submit();
						
						
						}

		            }
		           

		        }
		 });	
		
		
	}
	
}

 


function getResultFromFrame(frameName,targetName)
{
    return document.getElementById(frameName).contentWindow.document.getElementById(targetName).value;
}


function backManagePage()
{ 
	if('true' == '${param.fromDraft}' || 'draft' == '${param.fromDraft}')
	{
		//window.location = '<cms:BasePath/>core/console/Workbench.jsp?isReply=0&tab=4&pn=${param.pn}&dClassId=${param.dClassId}';
		replaceUrlParam(W.window.location, 'isReply=0&tab=5');
		//W.window.location  = '<cms:BasePath/>core/console/Workbench.jsp?isReply=0&tab=5&pn=${param.pn}&dClassId=${param.dClassId}';
	}
	else
	{
		W.window.location.reload();
		 
		//<cms:Class id="${param.classId}">
		//	var classType = '${Class.classType}';				 										
		//</cms:Class>
		
		//if(classType == '3')
		//{
			//replaceUrlParam(window.location, 'fromFlow=');
			
		//}
		//else
		//{
			//var manageParam = '${param.manageParam}';
			//window.location = '<cms:BasePath/>core/content/ManageGeneralContent.jsp'+manageParam ;
		//}
	}
}


function openFlowStepSuggestDialog()
{

	W.$.dialog({ 
		id:'gscp',
    	title :'工作流执行建议和辅助说明',
    	width: '600px', 
    	height: '270px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,
         parent:api,
        close:true,
             
        content: 'url:<cms:Domain/>core/content/dialog/AddWorkflowActSuggest.jsp'
	});


}
 

</script>
</cms:SystemContent>
</cms:SystemDataModel>
