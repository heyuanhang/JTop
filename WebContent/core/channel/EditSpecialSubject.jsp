<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

		<script>  
		basePath='<cms:BasePath/>';
		
		var dialogApiId = '${param.dialogApiId}';
		
		var api = frameElement.api, W = api.opener; 
		
		var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w-]{1,50}$/;

         $(function()
		 {
		    validate('className',1,null,null);
 			validate('classFlag',0,ref_flag,'格式为字母,数字,下划线');
 			
 			//图片查看效果
 		  	loadImageShow();	
 				
		 })
		
			
      </script>
	</head>
	<body>

		<cms:SysClass id="${param.classId}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top: 5px;">
							<ul>
								<li id="two1" onclick="setTab('two',1,4)" class="selectTag">
									<a href="javascript:;"><img src="../style/icon/folder_table.png" width="16" height="16" />专题基本信息&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab('two',2,4)">
									<a href="javascript:;"><img src="../../core/style/icons/gear.png" width="16" height="16" />相关参数设置&nbsp;</a>
								</li>
								<li id="two3" onclick="setTab('two',3,4)">
									<a href="javascript:;"><img src="../style/icons/folder-tree.png" width="16" height="16" />信息子分类&nbsp;</a>
								</li>
								
								<li id="two4" onclick="setTab('two',4,4)">
									<a href="javascript:;"><img src="../style/icons/table--pencil.png" width="16" height="16" />扩展模型数据&nbsp;</a>
								</li>
								
								
							</ul>
						</div>

						<form id="classForm" name="classForm" method="post">
							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-manage">
												<tr>
													<td width="24%" class="input-title listdate-show">
														ID:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:385px" class="form-input" value="${param.classId}" disabled></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														专题标题:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:385px" id="className" name="className" class="form-input" value="${Class.className}"></input>
													</td>
												</tr>


												<tr>
													<td class="input-title listdate-show">
														引用标识:
													</td>
													<td class="td-input listdate-show">
														<input id="classFlagShow" disabled type="text" style="width:385px"  class="form-input" value="${Class.classFlag}"></input>
														<input  type="hidden"  id="classFlag" name="classFlag"  value="${Class.classFlag}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														专题Logo:
													</td>
													<td class="td-input listdate-show">
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="logoImageCmsSysShowSingleImage" href="${Class.logoImage}"><img id="logoImageCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="${Class.logoImageReUrl}" />" width="90" height="67" /> </a>
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																		<tr>
																			<td>
																				&nbsp;
																				<input type="button" onclick="javascript:showModuleImageDialog('logoImageCmsSysImgShow','logoImage','','','0','0',false)" value="上传" onclick="" class="btn-1" />
																				<input type="button" value="裁剪" onclick="javascript:disposeImage('logoImage','','',false,'-1');" class="btn-1" />
																				<input type="button" value="删除" onclick="javascript:deleteImage('logoImage');" class="btn-1" />
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;&nbsp;宽&nbsp;&nbsp;
																				<input id="logoImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Class.logoImageImageW}" />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="logoImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Class.logoImageImageH}" />
																			</td>
																		</tr>
																	</table>
																	<input id="logoImage" name="logoImage" type="hidden" value="${Class.logoImageResId}" />
																	<input id="logoImage_sys_jtopcms_old" name="logoImage_sys_jtopcms_old" type="hidden" value="${Site.logoImageResId}"/>
																</td>
															</tr>
														</table>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														专题首页模板:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:385px" id="classHomeTemplateUrl" name="classHomeTemplateUrl" class="form-input" value="${Class.classHomeTemplateUrl}"></input>

														<select class="form-select" onchange="javascript:selectRule(this,'classHomeTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{class-id}">
																栏目ID
															</option>
														</select>
														<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('channel','', 'pc');" />
														<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.classHomeTemplateUrl}');" />
													 
													</td>
												</tr>
												
												<tr id="mob-show" style="display:none">
													<td class="input-title listdate-show">
														移动端模板:
													</td>
													<td class="td-input listdate-show">
														
													<input type="text" style="width:385px" id="mobClassHomeTemplateUrl" name="mobClassHomeTemplateUrl" class="form-input" value="${Class.mobClassHomeTemplateUrl}"></input>
	
															<select class="form-select" onchange="javascript:selectRule(this,'mobClassHomeTemplateUrl')">
																<option value="-1">
																	备选值
																</option>
	
																<option value="{class-id}">
																	栏目ID
																</option>
															</select>
															<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('channel','', 'mob');" />
														<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.mobClassHomeTemplateUrl}');" />
													 
													
													</td>
												</tr>
												
												<tr id="pad-show" style="display:none">
													<td class="input-title listdate-show">
														Pad端模板:
													</td>
													<td class="td-input listdate-show">
														    
													<input type="text" style="width:385px" id="padClassHomeTemplateUrl" name="padClassHomeTemplateUrl" class="form-input" value="${Class.padClassHomeTemplateUrl}"></input>

														<select class="form-select" onchange="javascript:selectRule(this,'padClassHomeTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{class-id}">
																栏目ID
															</option>
														</select>
														<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('channel','', 'pad');" />
													<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.padClassHomeTemplateUrl}');" />
													
										
												 
										
													</td>
												</tr>
												
												<script>
													<cms:CurrentSite>
													 	
													if('${CurrSite.pcVm}' == '1')
													{
														$('#pc-show').show();
													}
													if('${CurrSite.mobVm}' == '1')
													{
														$('#mob-show').show();
													}
													if('${CurrSite.padVm}' == '1')
													{
														$('#pad-show').show();
													}
																							
													</cms:CurrentSite>
												</script>

												<tr>
													<td class="input-title listdate-show">
														专题首页静态化:
													</td>
													<td class="td-input listdate-show">
														<input name="classHomeProduceType" type="radio" value="1" checked />
														关闭&nbsp;
														<input name="classHomeProduceType" type="radio" value="3" />
														开启&nbsp;
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														首页发布规则:
													</td>
													<td class="td-input listdate-show">
														<cms:SystemPublishRule id="${Class.classHomePublishRuleId}">
															<input type="text" value="${Rule.ruleName}" size="60" id="classHomePublishRuleIdShow" class="form-input" readOnly="true"></input>
														</cms:SystemPublishRule>
														<input type="button" value="规则..." class="btn-1" onclick="javascript:openSelectPublishRuleDialog('1','classHomePublishRuleId');" />
														<input type="hidden" id="classHomePublishRuleId" name="classHomePublishRuleId" value="${Class.classHomePublishRuleId}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														同步发布专题:
													</td>
													<td class="td-input listdate-show">
														<input name="syncPubClass" type="radio" value="1" />
														是
														</span> &nbsp;&nbsp;&nbsp;&nbsp;
														<input name="syncPubClass" type="radio" value="0" />
														否
														</span>
														<span class="ps">专题内容增删改,排序操作将同步发布专题</span>
													</td>
												</tr>
												
												<tr>
													<td class="input-title listdate-show">
														是否显示:
													</td>
													<td class="td-input listdate-show">
														<input name="showStatus" type="radio" value="1" />
														是
														</span> &nbsp;&nbsp;&nbsp;&nbsp;
														<input name="showStatus" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												
											</table>

											<div style="height:50px;"></div>
											<div class="breadnavTab" >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="submitFormBut" href="javascript:submitEditForm()"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>

															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
														</div>
													</tr>
												</table>

											</div>
										</li>
									</ul>
								</div>

								<!-- 第二部分:站点 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="27%" class="input-title listdate-show">
														SEO标题:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:385px" id="seoTitle" name="seoTitle" class="form-input" value="${Class.seoTitle}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														SEO关键字:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:385px" id="seoKeyword" name="seoKeyword" class="form-input" value="${Class.seoKeyword}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														SEO描叙:
													</td>
													<td class="td-input listdate-show">
														<textarea id="seoDesc" name="seoDesc" style="width:385px; height:70px;" class="form-textarea">${Class.seoDesc}</textarea>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														专题横幅图:
													</td>
													<td class="td-input listdate-show">

														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="bannerCmsSysShowSingleImage" href="${Class.banner}"><img id="bannerCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="${Class.bannerReUrl}" />" width="90" height="67" /> </a>
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" height="65" class="form-table-big">
																		<tr>
																			<td>
																				&nbsp;
																				<input type="button" onclick="javascript:showModuleImageDialog('bannerCmsSysImgShow','banner','','','0','0',false)" value="上传" onclick="" class="btn-1" />
																				<input type="button" value="裁剪" onclick="javascript:disposeImage('banner','','',false,'-1');" class="btn-1" />
																				<input type="button" value="删除" onclick="javascript:deleteImage('banner');" class="btn-1" />
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;&nbsp;宽&nbsp;&nbsp;
																				<input id="bannerCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Class.bannerImageW}" />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="bannerCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Class.bannerImageH}" />


																			</td>
																		</tr>
																	</table>
																	<input id="banner" name="banner" type="hidden" value="${Class.bannerResId}" />
																	<input id="banner_sys_jtopcms_old" name="banner_sys_jtopcms_old" type="hidden" value="${Class.bannerResId}"/>
																</td>
															</tr>
														</table>

													</td>
												</tr> 

												<tr>
													<td class="input-title listdate-show">
														专题扩展信息:
													</td>
													<td class="td-input listdate-show">
														<select id="extDataModelId" name="extDataModelId" class="form-select">
															<option value="-1">
																----- 请选专题扩展模型 -----&nbsp;
															</option>

															<cms:SystemDataModelList modelType="3">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
														</select>

														<span class="ps">对应"扩展模型信息"选项卡</span>
													</td>
												</tr>


												<tr>
													<td class="input-title listdate-show">
														开启内容评论:
													</td>
													<td class="td-input listdate-show">
														<input name="openComment" type="radio" value="1" />
														是
														<input name="openComment" type="radio" value="0" />
														否 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论是否审核:<input name="mustCommentCensor" type="radio" value="1" /> 是</span>
														<input name="mustCommentCensor" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														允许非会员评论:
													</td>
													<td class="td-input listdate-show">
														<input name="notMemberComment" type="radio" value="1" />
														是
														<input name="notMemberComment" type="radio" value="0" />
														否 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;">允许评论输入HTML:<input name="commentHtml" type="radio" value="1" /> 是</span>
														<input name="commentHtml" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												<tr>
													<td width="13%" class="input-title listdate-show">
														过滤评论敏感词:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="filterCommentSensitive" type="radio" value="1" />
														是
														<input name="filterCommentSensitive" type="radio" value="0" />
														否 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;">&nbsp;&nbsp;&nbsp;&nbsp;强制输入验证码: <input name="commentCaptcha" type="radio" value="1" /> 是</span>
														<input name="commentCaptcha" type="radio" value="0" />
														否
														</span>
													</td>
												</tr></table>

											<div style="height:50px;"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="submitFormBut" href="javascript:submitEditForm()"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>

															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>

														</div>
													</tr>
												</table>

											</div>
										</li>
									</ul>
								</div>



								<!-- 第三部分：专题信息模型 -->
								<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>
											<div style="height:1px;"></div>
											<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td id="uid_td25" style="padding: 2px 6px;">
														<div class="DataGrid">
															<br/>
															<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																<tr class="datahead">
																	<td width="3%" height="30">
																		<strong>ID</strong>
																	</td>
																 
																	<td width="12%">
																		<strong>分类名称</strong>
																	</td>

																	<td width="12%">
																		<strong>访问标志</strong>
																	</td>

																	<td width="11%">
																		<center><strong>操作</strong></center>
																	</td>
																</tr>

																<cms:SystemCommendType classId="${param.classId}" isSpec="true">
																	<tr>
																		<td>
																			${CommendType.commendTypeId}
																		</td>
																		 

																		<td>
																			${CommendType.commendName}
																		</td>
																		<td>
																			${CommendType.commFlag}
																		</td>

																		<td>
																			<div>
																				<center>
																					<img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditSpecContentTypeDialog('${CommendType.commendTypeId}')">&nbsp;编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteSpecContentTypeDialog('${CommendType.commendTypeId}')">删除 </a>&nbsp;&nbsp;&nbsp;
																				</center>
																			</div>
																		</td>

																	</tr>
																</cms:SystemCommendType>
																<cms:Empty flag="CommendType">
																	<tr>
																		<td class="tdbgyew" colspan="6">
																			<center>
																				当前没有数据!
																			</center>
																		</td>
																	</tr>
																</cms:Empty>

															</table>

														</div>

													</td>
												</tr>

											</table>
											<div style="height:50px;"></div>
											<div class="breadnavTab" style="top:95.5%">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a href="javascript:openAddSpecialContentTypeDialog();"  class="btnwithico"><img src="../style/icons/folder-horizontal.png" width="16" height="16"/><b>新建信息分类&nbsp;</b> </a>

															<a id="submitFormBut" href="javascript:submitEditForm()"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>

															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
														</div>
													</tr>
												</table>

											</div>
										</li>
									</ul>
								</div>


								<!-- 第四部分：扩展模型 -->
								
								<!-- 第三部分：栏目-->
								<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
												<cms:SystemModelFiledList modelId="${Class.extDataModelId}" showMode="true">
													<cms:SystemModelFiled>
														<tr>
															<td class="input-title listdate-show" width="16%">
																<cms:SystemModelRowFiled >
																	<!-- 图集字段区域 -->
																	<cms:if test="${RowFiled.htmlElementId==14}">
																	${RowFiled.showName}:										
															
															</td>
															<td class="td-input listdate-show">
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
																						&nbsp;&nbsp;&nbsp;&nbsp;
																						<input type="text" size="5" id="${RowFiled.fieldSign}CmsSysMaxWidth" class="form-input" value="${RowFiled.fieldInfo.imageW}"></input>
																						宽度&nbsp;&nbsp;&nbsp;&nbsp;
																						<input type="text" size="5" id="${RowFiled.fieldSign}CmsSysMaxHeight" class="form-input" value="${RowFiled.fieldInfo.imageH}"></input>
																						高度&nbsp;&nbsp;&nbsp;&nbsp;
																						<select class="form-select" id="${RowFiled.fieldSign}CmsSysDisposeMode">
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
																								按宽高&nbsp;&nbsp;
																							</option>
																						</select>
																						缩放&nbsp;&nbsp;&nbsp;水印: 
																						<input class="form-checkbox" disabled type="checkbox" value="1" id="${RowFiled.fieldSign}CmsSysNeedMark" />
																						&nbsp;&nbsp;图片数 [
																						<font color=red><span id="${RowFiled.fieldSign}CmsSysImageGroupCount">-1</span></font> ]&nbsp;&nbsp;张
				
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
															<td class="input-title listdate-show" width="12%">
																<!-- 图集内容空标题 -->
															</td>
															<td class="td-input listdate-show" style="padding-top:0px;">
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
																				
																				//alert(document.getElementById('${RowFiled.fieldSign}CmsSysImageCurrentCount').value);
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
																				<td class="td-input listdate-show">
																				
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
																									<span class="input-title listdate-show">${RowFiled.showName}:</span>&nbsp;
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
																							</cms:SystemModelRowFiled>
																						</tr>
																					</table>
																					
																				</td>
																	</tr>
																	</cms:SystemModelFiled>
																	</cms:SystemModelFiledList>
																	
																	
																	<cms:Empty flag="ModelFiled">
														

																		<tr>
																			<td>
																				<center>
																				<table class="listdate" width="98%" cellpadding="0" cellspacing="0">
				
																					<tr>
																						<td class="tdbgyew" style="height:20px;">
				
																							
																								<center>没有应用扩展模型!</center>
																							
																							 
				
																						</td>
																					</tr>
				
																				</table>
																				</center>
																			</td>
																		</tr>
				
																	
		
									
																</cms:Empty>
																
																	
																</table>
																<div style="height:50px;"></div>
																<div class="breadnavTab" >
																	<table width="100%" border="0" cellpadding="0" cellspacing="0">
																		<tr class="btnbg100">
																			 <div style="float:right">
																				<a id="submitFormBut" href="javascript:submitEditForm()"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
					
																				<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
																			</div>
																		</tr>
																	</table>
					
																</div>
														
														</table>
											
											

											 
											
										</li>
									</ul>
								</div>
							</div>


							</div>

							<!-- hidden -->
							<input type="hidden" id='selfId' name='selfId' value='${Class.classId}' />
							<input type="hidden" id='classId' name='classId' value='${Class.parent}' />
							<input type="hidden" id='parent' name='parent' value='${Class.parent}' />
							<input type="hidden" id='isLastChild' name='isLastChild' value='${Class.isLastChild}' />
							<input type="hidden" id='classType' name='classType' value='${Class.classType}' />

						</form>
					</td>
				</tr>
			</table>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">
	
//主配置	
initRadio('classType','${Class.classType}');

initRadio('classHomeProduceType','${Class.classHomeProduceType}');

initRadio('syncPubClass','${Class.syncPubClass}');

initRadio('useStatus','${Class.useStatus}');

initRadio('showStatus','${Class.showStatus}');

//其他配置



initRadio('memberAddContent','${Class.memberAddContent}');

initSelect('extDataModelId','${Class.extDataModelId}');

initRadio('openComment','${Class.openComment}');

initRadio('mustCommentCensor','${Class.mustCommentCensor}');

initRadio('notMemberComment','${Class.notMemberComment}');

initRadio('commentHtml','${Class.commentHtml}');

initRadio('filterCommentSensitive','${Class.filterCommentSensitive}');

initRadio('commentCaptcha','${Class.commentCaptcha}');


//此处初始化tab
var tab = '${param.tab}';

if('' != tab)
{
	setTab2('two',tab,4);
}

function setTab(flag,pos,size)
{
		hideTips('className');
		hideTips('classFlagShow');
		setTab2(flag,pos,size);
	
}


function previewChannel(url)
{
	<cms:CurrentSite>
	var siteRoot = '${CurrSite.siteUrl}';
	</cms:CurrentSite>
	 
	if('${Class.classType}' == '3')
	{
		url = $('#contentTemplateUrl').val();
		
		url = siteRoot+replaceAll(url,'{content-id}', '${Class.singleContentId}');
	}
	else
	{
		url = siteRoot+replaceAll(url,'{class-id}', '${Class.classId}');
	}

	window.open(url,'_blank');

}


function showFormatSelect(id,eventValue)
{
   var target = document.getElementById(id);
   
   if(target.style.display=='none' && eventValue=='3')
   {
      target.style.display='';
   }
   else if(target.style.display=='' && eventValue=='1')
   {
      target.style.display='none';
   }
}


function submitEditForm()
{  
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('classFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('className',1,null,null);

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    { 
    	setTab2('two',1,4);
    	submitValidate('classFlag',0,ref_flag,'格式为字母,数字,下划线');
    	submitValidate('className',1,null,null);
    	
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	//后台验证
	if('${Class.classFlag}' != $('#classFlag').val())
	{
	
		var count = validateExistFlag('contentClass', $('#classFlag').val());
		
		if(count > 0)
		{

			setTab('two',1,4);
			showTips('classFlagShow', '系统已存在此值，不可重复录入');
			
			return;
		}
	}

   $("#submitFormBut").attr("disabled","disabled"); 
   $("#submitFormImg").attr("disabled","disabled"); 
   
 	encodeFormEditor('classForm', false);
		
	var url = "<cms:BasePath/>channel/editSpecSubject.do"+"?<cms:Token mode='param'/>";
 	var postData = encodeURI($("#classForm").serialize());
 	
 	postData = postData.replace(/\+/g, " ");
	postData = encodeData(postData);
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(mg)
        {     
        
        	var msg = eval("("+mg+")");
        	
           if('success' == msg)
           {
           		W.$.dialog({ 
	   					title :'提示',
	    				width: '150px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '修改专题成功！', 
	                    ok: function(){ 
      						window.location.reload();
    					} 
		  		});
           	         		
           } 	
           else
           {
           	    W.$.dialog({ 
					   					title :'提示',
					    				width: '200px', 
					    				height: '60px', 
					    			 	parent:api,
					                    lock: true, 
					    				icon: '32X32/fail.png', 
					    				
					                    content: "执行失败，无权限请联系管理员！", 
					       				cancel: true 
				});
				
				$("#submitFormBut").removeAttr("disabled"); 
  				$("#submitFormImg").removeAttr("disabled"); 
           }   
        }
 	});	
}

function submitDeleteAction()
{
    if(confirm('当前栏目以及子栏目的相关信息将被物理删除,您确定要删除吗?'))
    {
    	document.all.classForm.action='../../channel/deleteContentClass.do'+"?<cms:Token mode='param'/>";
    	document.all.classForm.submit();
    }
    else
    {
      return;
    }
    
    return;
 
}

function goSortNodePage()
{
	window.parent.location.href="SortContentClass.jsp?classId=${Class.classId}";
}

function addClass()
{
	window.location.href="AddContentClassForParent.jsp?classId=${Class.classId}&className=${Class.className}";
}

function showMoveDialog()
{
     
      
      var parent = window.showModalDialog("SelectParentNode.jsp?classId=${Class.classId}&className=${Class.className}&parent=${Class.parent}&random="+Math.random(),"","dialogWidth=150px;dialogHeight=430px;status=no");
      
        
       window.parent.document.all[id='treeFrame'].src='ListClass.jsp'
      
    
}

function openSelectPublishRuleDialog(type,idName)
{
	W.$.dialog({ 
		id:'osprd',
    	title :'选择发布规则',
    	width: '500px', 
    	height: '300px', 
    	parent:api,
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
             
        content: 'url:<cms:BasePath/>core/channel/SelectPublishRule.jsp?type='+type+'&idName='+idName+'&apiId=oessd'
	});
}



function openSelectTempletDialog(mode, objId, vm)
{
      var targetName = '';
      
      var vmName = 'PC端';
      
       
      if('mob' == vm)
      {
      	vmName = 'Mob端';
      }
      else if('pad' == vm)
      {
      	vmName = 'Pad端';
      }
      
      if('channel' == mode)
      {
      	targetName = vmName+'栏目首页';
      }
      else if('class' == mode)
      {
      	targetName = vmName+'列表页';
      }
      else if('content' == mode)
      {
      	targetName = vmName+'内容页';
      }
      else if('home' == mode)
      {
      	targetName = vmName+'首页';
      }
       
	  W.$.dialog({ 
		id:'ostd',
    	title :'选择'+targetName+'模版',
    	width: '750px', 
    	height: '536px', 
    	parent:api,
    	lock: true, 
        max: false, 
         min: false, 
       
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/channel/SelectChannelTemplet.jsp?mode='+mode+'&apiId=oessd'+'&objId='+objId+'&vm='+vm
	});
}


function editCurrentTemplet(mode)
{      
	   var currrentTemplet="";
	   if(mode=='class')
       {
       		currrentTemplet=document.getElementById("classTemplateUrl").value;
       	
       }   
       else if(mode=='content')
       {
       		currrentTemplet=document.getElementById("contentTemplateUrl").value;
       }
      
       if("" == currrentTemplet)
       {
       		return;
       }
   
       
       window.showModalDialog("EditChannelTemplet.jsp?entry="+currrentTemplet.replaceAll("/","*"),"","dialogWidth=1250px;dialogHeight=600px;status=no");
}



function selectRule(rule,target)
{
   var fileRuleName = document.getElementById(target);
   
   var selection = document.selection;
                                
   fileRuleName.focus();
                                
   if (typeof fileRuleName.selectionStart != "undefined")
   {
    	var s = fileRuleName.selectionStart;
         fileRuleName.value = fileRuleName.value.substr(0, fileRuleName.selectionStart) + rule.value + fileRuleName.value.substr(fileRuleName.selectionEnd);
         fileRuleName.selectionEnd = s + rule.value.length;
    } 
    else if (selection && selection.createRange) 
    {
         var sel = selection.createRange();
         sel.text = rule.value;
    } else 
    {
         fileRuleName.value += rule.value;
    }
   
   
   var opts = rule.options;
   
   for(var i=0; i<opts.length; i++)
   {
   		if(opts[i].value == '-1')
   		{
   			opts[i].selected=true;
   			break;
   		}	
   }
}

function openAddSpecialContentTypeDialog()
{
    var classId = '${Class.classId}';
    var className = '${Class.className}';
	W.$.dialog({ 
	    id : 'asctd',
    	title : '增加专题信息分类',
    	width: '600px', 
    	height: '200px', 
    	parent : api,
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/channel/AddSpecialContentType.jsp?classId=${param.classId}'
	
	  
	});
}

function openEditSpecContentTypeDialog(typeId)
{
	W.$.dialog({ 
	    id : 'oesctd',
    	title : '编辑专题信息分类',
    	width: '690px', 
    	height: '500px', 
    	parent: api,
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/channel/EditSpecialContentType.jsp?uid='+Math.random()+'&typeId='+typeId+'&classId=${param.classId}'
	});
}


function deleteSpecContentTypeDialog(ids)
{
	
	 W.$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				parent: api,
                    content: '您确认删除所选分类吗？将删除所有附属内容',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>channel/deleteCommendType.do?typeIds="+ids+"&<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            
			               if('success' == msg)
			               {
			               		//showMsg('回复留言成功!');
			               		W.$.dialog.tips('删除内容成功...',1); 
			               		api.get('oessd').window.location.href = api.get('oessd').window.location.href+ '&tab=3';   
			               } 	
			               else
			               {
			               	    W.$.dialog.tips('删除操作失败!...',1);
			               }   
			            }
			     	});	
    }, 
    cancel: true 
                    
	
    });


}

function close()
{
	api.close();
	
	W.window.location.reload();
}

</script>
</cms:SysClass>
