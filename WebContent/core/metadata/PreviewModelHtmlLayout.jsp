<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		 
		<!-- 配置文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.all.gzjs"></script>
		
		<script type="text/javascript" charset="utf-8" src="../javascript/ueditor/lang/zh-cn/zh-cn.js"></script>
		
		
		<script type="text/javascript" src="../javascript/format/editor_content_format.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			basePath='<cms:Domain/>';
		</script>
		
		<cms:Set val="'fullscreen', 'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','removeformat','forecolor', 'backcolor', 'formatmatch', 'autotypeset', 'link', 'unlink', 'anchor','fontfamily', 'fontsize','undo', 'redo'" id="UE_SMP" />
		
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						模型字段效果预览
					</div>

					
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
							<cms:SystemModelFiledList modelId="${param.modelId}" showMode="true">
								<cms:SystemModelFiled>
									<tr>
										<td class="input-title" width="17%">
											<cms:SystemModelRowFiled >
												<!-- 图集字段区域 -->
												<cms:if test="${RowFiled.htmlElementId==14}">
													${RowFiled.showName}:										

										</td>
										<td class="td-input">
											<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload" style="padding-top:0px;">
												<tr>
													<td>
														<!-- 图集操作 -->
														<table width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-top:0px;">
															<tr style="padding-top:0px;">
																<td style="padding-top:0px;">
																	<a onclick="" class="btnwithico"> <img src="../../core/style/blue/icon/zip.png" alt="" /><b>上传ZIP包&nbsp;</b> </a>

																	<a onclick="" class="btnwithico"> <img src="../../core/style/icons/images.png" alt="" /><b>多图上传&nbsp;</b> </a>

																</td>
																<td style="padding-top:0px;">
																	&nbsp;&nbsp;&nbsp;
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
																	<input class="form-checkbox" type="checkbox" value="1" id="${RowFiled.fieldSign}CmsSysNeedMark" checked />
																	&nbsp;&nbsp;图片数 [
																	<font color=red><span id="${RowFiled.fieldSign}CmsSysImageGroupCount">-1</span> </font>] 张

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
										<td class="input-title" width="17%">
											<!-- 图集内容空标题 -->
										</td>
										<td class="td-input" style="padding-top:0px;">
											<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload" style="padding-top:0px;">
												<tr style="padding-top:0px;"> 
													<td style="padding-top:0px;">
														<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageCurrentCount" name="${RowFiled.fieldSign}CmsSysImageCurrentCount" value="0" />
														<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageCover" name="${RowFiled.fieldSign}CmsSysImageCover" value="" />
														<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageArrayLength" name="${RowFiled.fieldSign}CmsSysImageArrayLength" value="0" />
														
														
														<div id="${RowFiled.fieldSign}CmsSysImageUploadTab" >
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
													<td class="td-input">
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
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
																		<span class="input-title">${RowFiled.showName}:</span>&nbsp;
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
						<!-- hidden -->

				



				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>
		<button   onclick="javascript:window.close();">
			<img src="../../core/style/icon/close.png" alt="" />
			关闭
		</button>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  


   
  
</script>
