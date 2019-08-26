<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />	
		<cms:SystemDataModel id='${param.modelId}'>
		<cms:SysClass id="${param.classId}"> 
			<title>添加${DataModel.modelName}</title>
			<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
			<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
			<link href="../javascript/colorpicker/css/picker.css" type="text/css" rel="stylesheet" />
			<link rel="stylesheet" href="../style/layui/css/layui.css"  media="all">
			<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
			<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
	 
 			<script type="text/javascript" src="../javascript/format/editor_content_format.js"></script>
			
			
			<!-- 配置文件 -->
		    <script type="text/javascript" src="../javascript/ueditor/ueditor.config.js"></script>
		    <!-- 编辑器源码文件 -->
		    <script type="text/javascript" src="../javascript/ueditor/ueditor.all.gzjs"></script>
		    
		    <script type="text/javascript" charset="utf-8" src="../javascript/ueditor/lang/zh-cn/zh-cn.js"></script>
		    
			
			<script type="text/javascript" src="../javascript/colorpicker/picker.js"></script>
			<script type="text/javascript" src="../javascript/uuid.js"></script>

			<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
			<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
			<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

			<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

			<script type="text/javascript" src="../javascript/area.js"></script>
			
			<script>
			
	 
			
						
					    var scid = '${Class.singleContentId}';
					   
					    var ct = '${Class.classType}';
					  
					    if(ct == 3 && scid != '' && scid != null && scid != '0')
					    {
					    	window.location.href = 'EditUserDefineModelContent.jsp?contentId='+scid+'&classId=${param.classId}&modelId=${param.modelId}';
					    }
		
		
			
			
			basePath='<cms:BasePath/>';
			
			var api = frameElement.api, W = api.opener; 
			
			
			//三级省市联动及主显标题
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
			
			
			//$(window).load(function()
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
					    //if('${ModelFiled.defaultValidate}' != '')
					    //{		    	
							<cms:FieldValidateConfig id="${ModelFiled.defaultValidate}" >	
						
							validate('${ModelFiled.fieldSign}','${ModelFiled.isMustFill}','${Valid.regulation}','${Valid.errorMessage}');	
							//alert('${ModelFiled.fieldSign} + ${ModelFiled.isMustFill} + ${Valid.regulation} + ${Valid.errorMessage}');				
							</cms:FieldValidateConfig>
						//}
	                   </cms:SystemModelFiled>   
				</cms:SystemModelFiledList>
	
				//标题不可为空
 				validate('title',1,null,null);		
 				
 										
			})
			
			 
		 
		</script>
	</head>
	
	<cms:CurrentSite>
	<body>
		 	<form id="userDefineContentForm" name="userDefineContentForm" method="post">
			
			<!--新添加修稿20130804-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="mainbody-x" >
				<tr >
					<td class="mainbody"  align="left" valign="top">
						<!--main start class="mainbody"-->
						 

						<table width="99.8%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
							<tr>
								<td id="main-td"  >
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
								<td  >
									 
								</td>
							</tr>
							
							
							<cms:if test="${DataModel.titleMode==1}">
							<tr>
								<td class="input-title listdate-show-data">
									标题:
									
								</td>
								<td class="td-input listdate-show-data">
									<input id="title" name="title" type="text" size="85" maxlength="120" style="width: 545px;${Info.titleStyle}" class="form-input-title titlerule" onkeyup="textCounter(this, 'currCharLen');" />
									<span class="red">*</span>
									<font><span id="currCharLen">&nbsp;0</span>字</font>&nbsp;&nbsp;&nbsp;

									<img src="../../core/style/blue/icons/edit_r1_c7.jpg" height="16" width="16" title="颜色" id="titleBg" class="cursor img-icon" />&nbsp;<img src="../../core/style/blue/icons/edit_r1_c1.jpg" height="16" width="16" title="粗体" id="strongTitle" onclick="javascript:setTitleChar(this);" class="cursor img-icon" />&nbsp;<img src="../../core/style/blue/icons/edit_r1_c3.jpg" height="16" width="16" title="斜体" id="emTitle" onclick="javascript:setTitleChar(this);" class=" cursor img-icon" />&nbsp;&nbsp;&nbsp;<img src="../../core/style/blue/icons/edit_r1_c5.jpg" height="16" width="16" title="下划线" id="underTitle" onclick="javascript:setTitleChar(this);" class=" cursor img-icon" />&nbsp;&nbsp;&nbsp;</span><input type="checkbox" size="55" class="form-checkbox img-icon" onclick="javascript:showTitleInput('simpleTitle','shortTitle',this);" />&nbsp;&nbsp;&nbsp;辅标
									<input type="hidden" id="titleBgVal" name="titleBgVal" value="" />
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
							
							
							<cms:if test="${DataModel.simpleMode==1}">
							
							<tr>
								<td  class="input-title listdate-show-data">
									 
									
								</td>
								<td class="td-input listdate-show-data">
									<span><img src="../../core/style/blue/icon/documents-text.png" id="sysCopyContentImg" title="复制到:  " height="16" width="16" class="img-icon" /><a class="a_style" href="javascript:openCopyContentToSiteClassDialog('single','${param.modelId}','','${CurrSite.siteId}');">&nbsp;复制内容</a>[<font color=red><span id='sysCopyClassCount'>0</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<img src="../../core/style/blue/icon/sitemap-application.png" height="16" width="16" class="img-icon" id="sysShareContentImg" title="共享到:  " /><a class="a_style" href="javascript:openSelectSiteGroupDialog('','');">&nbsp;站群共享</a>[<font color=red><span id='shareSiteIdCount'>0</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span><img src="../../core/style/blue/icon/document-tree.png" height="16" width="16" class="img-icon" /><a class="a_style" href="javascript:openSelectRelatedContentDialog('-1','${param.classId}');">&nbsp;相关内容</a>[<font color=red><span id='sysRelateCount'>0</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<span><img src="../../core/style/icons/report.png" height="16" width="16" class="img-icon" /><a class="a_style" href="javascript:openSelectSurveyDialog('', '${param.classId}');">&nbsp;所属调查</a>[<font color=red><span id='sysRelateSurveyCount'>0</span></font>]</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="checkbox" class="form-checkbox" name="allowCommend" value="0"></input>
									禁止评论&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
									
									<input type="checkbox" class="form-checkbox" name="topFlag" value="1"></input>
										内容置顶&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										
									<input type="checkbox" class="form-checkbox" id="outlinkc" onclick="javascript:showOutLink(this);"></input>
									外链跳转&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
									
										
								</td>
							</tr>
							</cms:if>
							
							<tr id="outlinkTr" style="display:none">
									<td class="input-title listdate-show-data">
										外链地址:
										
									</td>
									<td class="td-input listdate-show-data">
										<input id="outLink" name="outLink" type="text"  style="width: 752px;" class="form-input"  value="http://" />
									</td>
							</tr>
							
							
							
							<cms:if test="${DataModel.kwMode==1}">
							<tr>
									<td  class="input-title listdate-show-data">
										TAG:
										
									</td>
									<td class="td-input listdate-show-data">
											<input readonly id="tagKeyVal" name="tagKeyVal" type="text" class="form-input" style="width: 252px;" maxlength="300"  />
											 
											<input id="tagKey" name="tagKey" type="hidden" class="form-input"  value="${Info.tagKey}" /><button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:openSelectTagDialog();"  > 选择</button>
											
											
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span class="input-title">关键字:</span>
										<input id="keywords" name="keywords" type="text" class="form-input" style="width: 252px;" maxlength="200"  />
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
										 	<input name="author" id="author" type="text" class="form-input" style="width: 252px;" maxlength="50"  />
										<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:openSelectContentSourceDialog();"  >选取</button>
									
									</td>
							</tr>

							 
							<tr>
								<td class="input-title listdate-show-data">
									摘要:
								</td>
								<td class="td-input listdate-show-data">
									<textarea id="summary" name="summary" style="height:65px;width:748px" class="form-textarea"></textarea>
								</td>
							</tr>
							</cms:if>
							

							


						</table>
						<!-- 自定义模型字段区域 -->
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
							<cms:SystemModelFiledList modelId="${param.modelId}" showMode="true">
								<cms:SystemModelFiled>
									<tr>
										<td class="input-title listdate-show-data" id="data-td" >
											<script>
											 
											 
											 $('#data-td').attr('width', hx+'');
											 </script>
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
																	&nbsp;&nbsp;<input type="text" style="width:62px;" id="${RowFiled.fieldSign}CmsSysMaxWidth" class="form-input" value="${RowFiled.fieldInfo.imageW}"></input>
																	宽度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width:64px;" id="${RowFiled.fieldSign}CmsSysMaxHeight" class="form-input" value="${RowFiled.fieldInfo.imageH}"></input>
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
																	缩放&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;水印:<input class="form-checkbox" disabled type="checkbox" value="1" id="${RowFiled.fieldSign}CmsSysNeedMark" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图片数量 [<font color=red><span id="${RowFiled.fieldSign}CmsSysImageGroupCount">-1</span></font>]&nbsp;张

																	<script>
			
																	initSelect('${RowFiled.fieldSign}CmsSysDisposeMode','${RowFiled.fieldInfo.imageDisposeMode}');	
																	//initRadio('${RowFiled.fieldSign}CmsSysNeedMark','${RowFiled.fieldInfo.needMark}');	
											
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
										<td class="input-title listdate-show-data" id="data-td2">
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
														
														<cms:PhotoGroup  contentId="${RowFiled.info.contentId}" serverMode="true">
														addGroupPhotoToPage('${RowFiled.fieldSign}','${Photo.orderFlag}','${Photo.url}','${Photo.resizeUrl}','${Photo.resId}','${Photo.reUrl}','${Photo.photoName}','${Photo.height}','${Photo.width}','${Photo.photoDesc}');
														</cms:PhotoGroup>
														
														document.getElementById('${RowFiled.fieldSign}CmsSysImageGroupCount').innerHTML = document.getElementById('${RowFiled.fieldSign}CmsSysImageCurrentCount').value;
	
														document.getElementById('${RowFiled.fieldSign}CmsSysImageArrayLength').value = allImageGroupSortInfo['${RowFiled.fieldSign}'].length;
														//alert(document.getElementById('${RowFiled.fieldSign}CmsSysImageArrayLength').value);
														</script>
														</div>
													</td>

													<!-- 图集内容结束 -->
													</cms:if>

													<!-- 普通字段区域开始 -->
													<cms:else>
														<cms:if test="${status.index == 0}">
														${RowFiled.showName}:										
													</td>
															<td class="td-input listdate-show-data">
															    <table  border="0"  cellpadding="0" cellspacing="0" class="form-table-upload">
																	<tr>
																		<td>
																		  <table  border="0"  cellpadding="0" cellspacing="0">
																			 <tr>
																			 	<td>
																			 		<div id="sys-obj-${RowFiled.fieldSign}">${RowFiled.inputModeLayoutHtml}
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
																				<span class="input-title ">${RowFiled.showName}:</span>&nbsp;
																			</td>
																			<td>
																			
																				<table  border="0"  cellpadding="0" cellspacing="0">
																				 <tr>
																				 	<td>
																				 		<div id="sys-obj-${RowFiled.fieldSign}">${RowFiled.inputModeLayoutHtml}
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
					 
						
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="form-table-big-content">
								
								<cms:if test="${DataModel.titleMode==1}">
								<tr>
									<td id="data-td3" class="input-title listdate-show-data">
											<script>
											 
											 
											 $('#data-td3').attr('width', (hx+8)+'');
											 </script>
										内容缩略图:
									</td>
									<td class="td-input listdate-show-data">
										<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td  width="42%">
													<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
														<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="contentImageCmsSysShowSingleImage" href="${Info.contentImage}"><img id="contentImageCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>
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
																				<input id="contentImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px"   />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="contentImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px"  />


																			</td>
																		</tr>
																	</table>
																	<input id="contentImage" name="contentImage" type="hidden"  />
																 
																</td>
														</tr>
													</table>
												</td>
												<td width="11%">

												</td>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
														<tr>
															<td class="input-title  ">
																栏目缩略图:&nbsp;&nbsp;&nbsp;
															</td>
															<td>
																<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																	<tr>
																			<td>
																				<a class="cmsSysShowSingleImage" id="classImageCmsSysShowSingleImage" href="${Info.classImage}"><img id="classImageCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>
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
																							<input id="classImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px"  />
																							&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																							<input id="classImageCmsSysImgHeight" class="form-input" readonly type="text"style="width:44px"  />
																						</td>
																					</tr>
																				</table>
																				<input id="classImage" name="classImage" type="hidden"   />
																				 
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
									<td class="td-input listdate-show-data">
										<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td  width="42%">
													<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
														<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="channelImageCmsSysShowSingleImage" href="${Info.channelImage}"><img id="channelImageCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>
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
																				<input id="channelImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px"   />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="channelImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px"  />


																			</td>
																		</tr>

																	</table>
																	<input id="channelImage" name="channelImage" type="hidden"   />
																 
																</td>
															</tr>
													</table>
												</td>
												<td width="11%">

												</td>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
														<tr>
															<td class="input-title  ">
																首页缩略图:&nbsp;&nbsp;&nbsp;
															</td>
															<td>
																<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																		<tr>
																			<td>
																				<a class="cmsSysShowSingleImage" id="homeImageCmsSysShowSingleImage" href="${Info.homeImage}"><img id="homeImageCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>
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
																							<input id="homeImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px"   />
																							&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																							<input id="homeImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px"   />


																						</td>
																					</tr>
																				</table>
																				<input id="homeImage" name="homeImage" type="hidden"   />
																				 
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
										流转时间:
									</td>
									<td class="td-input listdate-show-data">
										<input id="appearStartDateTime" name="appearStartDateTime" style="width: 165px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${Info.appearStartDateTime}" />
										&nbsp;至&nbsp;
										<input id="appearEndDateTime" name="appearEndDateTime"  style="width: 165px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${Info.appearEndDateTime}" />
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 
										
										<span class="input-title">内容属性:&nbsp;&nbsp;</span>
										<select name="typeFlag" id="typeFlag" class="form-select"  style="width: 262px;">
											<option value="-1">
												------------- 所属信息分类 -------------
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
											编辑时间:
										</td>
										<td class="td-input listdate-show-data ">
											<input id="addTime" name="addTime" style="width: 165px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});"   />
											 <span class="ps">添加时可人工编辑，默认为当前时间 </span>
											 
											 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span class="input-title">搜索权重:&nbsp;&nbsp;</span><input name="boost" id="boost" type="text" class="form-input" style="width: 245px;" maxlength="22"  />
											 
											 
										</td>
									</tr>
									
									<%--<tr>
									<td class="input-title listdate-show-data" id="data-td3">
										<script>
											 
											 
											 $('#data-td3').attr('width', (hx+8)+'');
											 </script>
											独有模板:
									</td>
									<td class="td-input listdate-show-data">
										<input id="especialTemplateUrl" name="especialTemplateUrl" type="text" class="form-input" style="width: 330px;" maxlength="320" />
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
							--%>
							
								<tr>
								<td id="data-td3" >
									 <script>
										  $('#data-td3').attr('width', (hx+8)+'');
								     </script>
									
								</td>
								<td  >
									 
								</td>
							</tr>
							
							</table>
						 
					</td>
				</tr>

			</table>


			<!-- hidden -->
			<cms:Token mode="html"/> 
			
			<input type="hidden" id="classId" name="classId" value="${param.classId}" />

			<!-- 文章模型 -->
			<input type="hidden" id="modelId" name="modelId" value="${param.modelId}" />

			<input type="hidden" id="actionId" name="actionId" />
			
			<input type="hidden" id="flowTarget" name="flowTarget"  value="publish"/>
 
			<input type="hidden" id="toStepId" name="toStepId" />

			<input type="hidden" id="fromStepId" name="fromStepId" />

			<!-- 标题样式 -->
			<input id='titleStyle' name="titleStyle" type="hidden" />

			<input id='simpleTitleStyle' name="simpleTitleStyle" type="hidden" />

			<!-- 稿件标志 -->
			<input id='contentAddStatus' name="contentAddStatus" type="hidden" />
			
			<!-- 工作流动作审批建议 -->
			<input id='jtopcms_sys_flow_suguest' name="jtopcms_sys_flow_suguest" type="hidden" value="【该内容不涉密】" />
			
			<!-- 工作流动作修改建议 -->
			<input id='jtopcms_sys_flow_edit_suguest' name="jtopcms_sys_flow_edit_suguest" type="hidden" value="【该内容无改动】" />
			
			
			<input id='jtopcms_sys_flow_prev_step' name="jtopcms_sys_flow_prev_step" type="hidden" value="" />
			
		 
			<input id='jtopcms_sys_flow_next_step' name="jtopcms_sys_flow_next_step" type="hidden" value="" />
			

			<!-- 获取keyword临时变量 -->
			<input type="hidden" id="jtopcms_sys_keyword_content" name="jtopcms_sys_keyword_content" value="" />
			
			<!-- 相关内容 -->
			<cms:QueryData objName="Rcid" service="cn.com.mjsoft.cms.content.service.ContentService" method="getRelateContentQueryTag" var=",all,${param.classId}">						
			<input type="hidden" id="relateIds" name="relateIds" value="${Rcid}"/>
			</cms:QueryData>
			
			<!-- 相关调查 -->
			<input type="hidden" id="relateSurvey" name="relateSurvey" value=""/>

			<!-- 同步复制栏目 -->
			<input type="hidden" id="copyClassId" name="copyClassIds" />
			
			<!-- 共享到其他站点 -->
			<input type="hidden" id="shareSiteIds" name="shareSiteIds" />
		</form>

		<!--提交-->
		<div style="height:50px;"></div>
		<div class="breadnavTab">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
				 
					<td style="text-align:center">
						<table style="margin:auto;" border="0" cellspacing="0" cellpadding="0"  >
							<tr>
							
								<td>
									<cms:WorkflowActionUIHelp classId="${param.classId}">
										<cms:if test="${Action.passActionName != '下线内容'}">
											 
											<cms:if test="${Action.passActionName.startsWith('进入') || Class.workflowId < 0}">
										 
												<a name="btnwithicosysflag" style="margin-right: 15px;" onclick="javascript:submitUserDefineContentInfo('${Action.passActionName}','${Action.actionId}','${Action.fromStepId}','${Action.toStepId}');"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16" /><b>${Action.passActionName}&nbsp;</b> </a>
										
												</cms:if>
												<cms:else>
												
												 
													<a name="btnwithicosysflag" style="margin-right: 15px;" onclick="javascript:openFlowStepSuggestDialog('${Action.passActionName}','${Action.actionId}','${Action.fromStepId}','${Action.toStepId}');"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16" /><b>${Action.passActionName}&nbsp;</b> </a>								
												
												</cms:else>
											</cms:if>
									</cms:WorkflowActionUIHelp>
									<a name="btnwithicosysflag" style="margin-right: 15px;" href="javascript:submitUserDefineContentInfo('','','-1','-1',true);"   class="btnwithico"><img src="../style/icons/script--pencil.png" width="16" height="16" /><b>存稿件&nbsp;</b> </a>
									<a href="javascript: backManagePageDialog();"  style="margin-right: 15px;" class="btnwithico" ><img src="../style/icon/arrow-return-180-left.png"" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

function addContentInfo(actionName, actionId, fromStepId, toStepId, draft)
{ 
	W.tip =  W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	//后台验证
		var url = "<cms:BasePath/>content/contentValidate.do";
	    var postData = encodeURI($("#userDefineContentForm").serialize());
	    
	    postData = postData.replace(/\+/g, " ");
        postData = encodeData(postData);
		
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:false,
		       	data:postData,
		       	dataType:'json',
		   
		       	success: function(msg)
		        {       
		        	hasError = false;
					
		            if(msg != null && msg != '')
		            {
		                msg = eval("("+msg+")");
		            
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
					                    
					                   
					                   cancel: true
					                    
						
						
					    });
		            
		            }
		            else
		            {
		            	//outlink
						
						var olv = document.getElementById('outLink');
						
						if('' == olv.value || 'http://' == olv.value)
						{
							olv.value = '';
						}
						
		            	//稿件模式
						if(draft)
						{
							document.getElementById('contentAddStatus').value='draft';
						}
								
						disableAnchorElementByName("btnwithicosysflag",true);
						
						W.tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
						
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
				     
						userDefineContentForm.action="<cms:BasePath/>content/addContent.do";
						
						encodeFormInput('userDefineContentForm', false);
						 
						userDefineContentForm.submit();
				  		 
		            }
		        }
		 });	
}

function submitUserDefineContentInfo(actionName,actionId,fromStepId,toStepId,draft)
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
	
	//系统信息字段验证 
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
	
		//敏感词审查，注意:要放在提交最前处理，避免影响复杂工作流
		
		url = "<cms:BasePath/>content/swLight.do";
	    var postData = encodeURI($("#userDefineContentForm").serialize());
	    
	    
	    
	     postData = postData.replace(/\+/g, " ");
         postData = encodeData(postData);
         
         var haveSw = false;
         
	
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:false,
		       	data:postData,
		       	dataType:'json',
		   
		       	success: function(msg)
		        {     
		        	hasError = false;
		        	  		        	
		            if(msg != null && msg != '')
		            {		
		            	 
		            	haveSw = true;
		            	  
		            	openSWHighlightDialog(msg,actionName, actionId, fromStepId, toStepId, draft);
		            }
		         }
		 });
		 
		 if(haveSw)
		 {
		 	return;
		 }
		 else
		 {
		 	
		 	addContentInfo(actionName, actionId, fromStepId, toStepId, draft);
		 }
	
		
			
	}
}

function getResultFromFrame(frameName,targetName)
{
    return document.getElementById(frameName).contentWindow.document.getElementById(targetName).value;
}
 



function init()
{

	var contentImageSrc = '${Info.contentImage}';
	
	if(contentImageSrc != '')
	{
		document.getElementById('contentImageShow').src = '${Info.contentImage}';
	}
	
	//topFlag
	initRadio('topFlag','${Info.topFlag}');
}




function showShareDialog()
{
   var returnArray=window.showModalDialog("ShareArticle.jsp?currentSelectClassIdStr="+document.getElementById("selectShareIds").value+"&random="+Math.random(),"","dialogWidth=350px;dialogHeight=430px;status=no");

   if(typeof(returnArray) != 'undefined')
   {
     var selectClassIdStr=returnArray[0];
     if(typeof(selectClassIdStr) != 'undefined' && '' != selectClassIdStr  )
     {
   	   document.getElementById("selectShareIds").value=selectClassIdStr;
     }
     
     document.getElementById("shareCount").innerHTML=returnArray[1];
   }
}





//选取站群节点
function openSelectSiteGroupDialog()
{
	W.$.dialog({ 
		    id : 'ossgd',
	    	title : '共享到站群',
	    	width: '440px', 
	    	height: '480px', 
	    	lock: true,
	    	parent:api, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowSiteGroup.jsp?uid='+Math.random()
	
	});
}



function backManagePage()
{
	window.location = 'ManageGeneralContent.jsp?classId=${param.classId}&modelId=${param.modelId}';
}


function backManagePageDialog()
{
	W.window.location = 'ManageGeneralContent.jsp?classId=${param.classId}&modelId=${param.modelId}';
}


function openFlowStepSuggestDialog(actionName, actionId,fromStepId,toStepId)
{

	W.$.dialog({ 
		id:'gscp',
    	title :actionName + ' - 步骤建议',
    	width: '690px', 
    	height: '400px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,
         parent:api,
        close:true,
             
        content: 'url:<cms:Domain/>core/content/dialog/AddWorkflowActSuggest.jsp?an='+encodeURI(encodeURIComponent(actionName))+'&ai='+actionId+'&fs='+fromStepId+'&ts='+toStepId+'&contentId=${param.contentId}'
	});


}


function openSWHighlightDialog(code,actionName, actionId, fromStepId, toStepId, draft)
{
	if('true' != draft)
	{
		draft = '';
	}
	 
	W.$.dialog({ 
		    id : 'oshld',
	    	title : '敏感词提示',
	    	width: '1200px', 
	    	height: '800px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	          parent:api,
	        content: 'url:'+basePath+'core/content/dialog/ViewDataSWHighlight.jsp?uid='+Math.random()+'&code='+code+'&actionName='+encodeURIComponent(encodeURIComponent(actionName))+'&actionId='+actionId+'&fromStepId='+fromStepId+'&toStepId='+toStepId+'&draft='+draft
	
	});


}
  

</script>

</cms:CurrentSite>
					
</cms:SysClass>
</cms:SystemDataModel>
