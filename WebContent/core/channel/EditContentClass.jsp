<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		
		<%--<script type="text/javascript" src="../javascript/editor/fckeditor.js"></script>
		--%>
		<script type="text/javascript" src="../javascript/format/editor_content_format.js"></script>
		
		<!-- 配置文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.all.gzjs"></script>
		
		<script type="text/javascript" charset="utf-8" src="../javascript/ueditor/lang/zh-cn/zh-cn.js"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />
		
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script>  
		basePath='<cms:BasePath/>';
		
         if("true"==="${param.fromFlow}")
         {  
         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
         		$.dialog.tips('编辑栏目成功',1,'32X32/succ.png'); 
	            
         	}
       		       
         }
         else if("true"==="${param.redirect}")
         {
            var tip = '';
            
         	if("1"==="${param.flag}")
         	{
         		tip = '添加栏目成功,请进入 [系统权限] 配置栏目管理权限!';
         		
         		$.dialog
			    ({ 
						id:'tip',
	   					title :'提示',
	    				width: '240px', 
					    height: '70px', 
	                    lock: true, 
	    				icon: '32X32/succ.png', 
	                    content: tip,
	                    
	   				 	ok: true 
	    	    });
         	}
         	else if("2"==="${param.flag}")
         	{
         		$.dialog.tips('删除栏目成功',1,'32X32/succ.png'); 
         	}
         	
		    
    	    
    	    
       
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
        

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
						<div class="auntion_tagRoom" style="margin-top: 0px;">
							<ul>
								<li id="two1" onclick="setTab('two',1,3)" class="selectTag">
									<a href="javascript:;"><img src="../style/icons/folder-horizontal-open.png" width="16" height="16" />栏目信息&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab('two',2,3)">
									<a href="javascript:;"><img src="../style/icons/gear--pencil.png" width="16" height="16" />相关参数设置&nbsp;</a>
								</li>
								<li id="two3" onclick="setTab('two',3,3)">
									<a href="javascript:;"><img src="../style/icons/table--pencil.png" width="16" height="16" />扩展模型信息&nbsp;</a>
								</li>
								<%--<li id="two4" onclick="setTab('two',4,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-dock-tab.png" width="16" height="16" />nbsp;</a>
									</li>
									<li id="two5" onclick="setTab('two',5,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-dock-tab.png" width="16" height="16" />推荐位权限&nbsp;</a>
									</li>
								--%>
							</ul>
						</div>

						<form id="classForm" name="classForm" method="post">
							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0"  >
												<tr class="listdate-show">
													<td width="22%" class="input-title listdate-show">
														栏目ID:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:472px" class="form-input" value="${Class.classFlag} (ID:${param.classId})" disabled></input>
														<input  type="hidden"  id="classFlag" name="classFlag"  value="${Class.classFlag}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														栏目名称:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:472px" id="className" name="className" class="form-input" value="${Class.className}"></input>
													</td>
												</tr>

												<%--<tr>
													<td class="input-title">
														引用标识:
													</td>
													<td class="td-input">
														<input id="classFlagShow" disabled type="text" size="77"  class="form-input" value="${Class.classFlag}"></input>
														
													</td>
												</tr>

												--%><tr>
													<td class="input-title listdate-show">
														栏目引导图:
													</td>
													<td class="td-input listdate-show" >
													
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
																				<input type="button" onclick="javascript:showModuleImageDialog('logoImageCmsSysImgShow','logoImage','','','0','0')" value="上传" onclick="" class="btn-1" />
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
																	<input id="logoImage_sys_jtopcms_old" name="logoImage_sys_jtopcms_old" type="hidden" value="${Class.logoImageResId}"/>
																</td>
															</tr>
														</table>
														
													</td>
												</tr>
											 

												<tr>
													<td class="input-title listdate-show">
														栏目类型:
													</td>
													<td class="td-input listdate-show">
														<input name="classType" type="radio" onclick="javascript:disposeClassTypeChange();" value="1" />
														一般栏目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;														
														<input name="classType" type="radio" onclick="javascript:disposeClassTypeChange();" value="3" />
														单页介绍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input name="classType" type="radio" onclick="javascript:disposeClassTypeChange();" value="2" />
														外部链接&nbsp;&nbsp;
														<span class="ps">外链型栏目只需要配置外链地址</span>
													</td>
												</tr>

												<tr id="outLinkTr" style="display:none">
													<td class="input-title listdate-show">
														外链地址:
													</td>
													<td class="td-input listdate-show">
														<input type="text" size="77" id="outLink" name="outLink" class="form-input" value="${Class.outLink}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														信息模型:
													</td>
													<td class="td-input listdate-show">
														<select id="contentType" name="contentType" class="form-select" style="width:199px">
															<option value="-1">
																------ 请选内容模型 ------
																</span>&nbsp;
															</option>

															<cms:SystemDataModelList modelType="2">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}
																		</span>&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
														</select>

														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;">工作流: <cms:SystemWorkflowList>
																<select id="workflowId" name="workflowId" class="form-select" style="width:	199px">
																	<option value="-1">
																		------ 直接发布内容 ------
																	</option>
																	<cms:SystemWorkflow>
																		<option value="${Workflow.flowId}">
																			${Workflow.flowName} &nbsp;
																		</option>
																	</cms:SystemWorkflow>
																</select>
															</cms:SystemWorkflowList>
													</td>
												</tr>



												<tr id="pc-show" style="display:none">
													<td class="input-title listdate-show" >
														PC主模板:
													</td>
													<td class="td-input listdate-show">
													
														<input type="text" style="width:472px" id="classHomeTemplateUrl" name="classHomeTemplateUrl" class="form-input" value="${Class.classHomeTemplateUrl}"></input>

														<select class="form-select" onchange="javascript:selectRule(this,'classHomeTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{class-id}">
																栏目ID
															</option>
														</select>
														<input type="button" value="频道页" class="btn-1" onclick="javascript:openSelectTempletDialog('channel','', 'pc');" />
													<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.classHomeTemplateUrl}');" />
												
												<br/><br/>
												  <input type="text" style="width:472px" id="classTemplateUrl" name="classTemplateUrl" class="form-input" value="${Class.classTemplateUrl}"></input>
														<select class="form-select" onchange="javascript:selectRule(this,'classTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{class-id}">
																栏目ID
															</option>

															<option value="{page}">
																分页数
															</option>
														</select>
														<input type="button" value="列表页" class="btn-1" onclick="javascript:openSelectTempletDialog('class','', 'pc');" />
													<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.classTemplateUrl}');" />
												<br/><br/>
												
												<input type="text" style="width:472px" id="contentTemplateUrl" name="contentTemplateUrl" class="form-input" value="${Class.contentTemplateUrl}"></input>
														<select class="form-select" onchange="javascript:selectRule(this,'contentTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{content-id}">
																内容ID
															</option>

															<%--<option value="{class-id}">
																栏目ID
															</option>

															--%><option value="{page}">
																分页数
															</option>
														</select>
														<input type="button" value="内容页" class="btn-1" onclick="javascript:openSelectTempletDialog('content','', 'pc');" />

													
													</td>
													
												</tr>

												<tr id="mob-show" style="display:none">
													<td class="input-title listdate-show">
														移动端模板:
													</td>
													<td class="td-input listdate-show">
														
													<input type="text" style="width:472px" id="mobClassHomeTemplateUrl" name="mobClassHomeTemplateUrl" class="form-input" value="${Class.mobClassHomeTemplateUrl}"></input>
	
															<select class="form-select" onchange="javascript:selectRule(this,'mobClassHomeTemplateUrl')">
																<option value="-1">
																	备选值
																</option>
	
																<option value="{class-id}">
																	栏目ID
																</option>
															</select>
															<input type="button" value="频道页" class="btn-1" onclick="javascript:openSelectTempletDialog('channel','', 'mob');" />
														<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.mobClassHomeTemplateUrl}');" />
													<br/><br/>
													<input type="text" style="width:472px" id="mobClassTemplateUrl" name="mobClassTemplateUrl" class="form-input" value="${Class.mobClassTemplateUrl}"></input>
															<select class="form-select" onchange="javascript:selectRule(this,'mobClassTemplateUrl')">
																<option value="-1">
																	备选值
																</option>
	
																<option value="{class-id}">
																	栏目ID
																</option>
	
																<option value="{page}">
																	分页数
																</option>
															</select>
															<input type="button" value="列表页" class="btn-1" onclick="javascript:openSelectTempletDialog('class','', 'mob');" />
														<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.mobClassTemplateUrl}');" />
														
														<br/><br/>
													<input type="text" style="width:472px" id="mobContentTemplateUrl" name="mobContentTemplateUrl" class="form-input" value="${Class.mobContentTemplateUrl}"></input>
														<select class="form-select" onchange="javascript:selectRule(this,'mobContentTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{content-id}">
																内容ID
															</option>

															<%--<option value="{class-id}">
																栏目ID
															</option>

															--%><option value="{page}">
																分页数
															</option>
														</select>
														<input type="button" value="内容页" class="btn-1" onclick="javascript:openSelectTempletDialog('content','', 'mob');" />
										
				 
													
													</td>
												</tr>

												<tr id="pad-show" style="display:none">
													<td class="input-title listdate-show">
														Pad端模板:
													</td>
													<td class="td-input listdate-show">
														
													
														<input type="text" style="width:472px" id="padClassHomeTemplateUrl" name="padClassHomeTemplateUrl" class="form-input" value="${Class.padClassHomeTemplateUrl}"></input>

														<select class="form-select" onchange="javascript:selectRule(this,'padClassHomeTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{class-id}">
																栏目ID
															</option>
														</select>
														<input type="button" value="频道页" class="btn-1" onclick="javascript:openSelectTempletDialog('channel','', 'pad');" />
													<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.padClassHomeTemplateUrl}');" />
														
														
														<br/><br/>
																										
														<input type="text" style="width:472px" id="padClassTemplateUrl" name="padClassTemplateUrl" class="form-input" value="${Class.padClassTemplateUrl}"></input>
														<select class="form-select" onchange="javascript:selectRule(this,'padClassTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{class-id}">
																栏目ID
															</option>

															<option value="{page}">
																分页数
															</option>
														</select>
														<input type="button" value="列表页" class="btn-1" onclick="javascript:openSelectTempletDialog('class','', 'pad');" />
													<input type="button" value="预览" class="btn-1" onclick="javascript:previewChannel('${Class.padClassTemplateUrl}');" />
											 
														<br/><br/>
														
														
													<input type="text" style="width:472px" id="padContentTemplateUrl" name="padContentTemplateUrl" class="form-input" value="${Class.padContentTemplateUrl}"></input>
														<select class="form-select" onchange="javascript:selectRule(this,'padContentTemplateUrl')">
															<option value="-1">
																备选值
															</option>

															<option value="{content-id}">
																内容ID
															</option>

															<%--<option value="{class-id}">
																栏目ID
															</option>

															--%><option value="{page}">
																分页数
															</option>
														</select>
														<input type="button" value="内容页" class="btn-1" onclick="javascript:openSelectTempletDialog('content','', 'pad');" />
										
										
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
														是否静态化:
													</td>
													<td class="td-input listdate-show">
														<input name="classHomeProduceType" type="checkbox" onclick="javascript:disposeClassTypeChange();" value="3" />
														频道首页&nbsp;
														<input name="classProduceType" type="checkbox" onclick="javascript:disposeClassTypeChange();" value="3" />
														栏目列表页&nbsp;
														<input name="contentProduceType" type="checkbox" onclick="javascript:disposeClassTypeChange();" value="3" />
														内容展示页&nbsp;
														
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														栏目首页发布规则:
													</td>
													<td class="td-input listdate-show">
														<cms:SystemPublishRule id="${Class.classHomePublishRuleId}">
															<input type="text" value="${Rule.ruleName}" style="width:472px" id="classHomePublishRuleIdShow" class="form-input" readOnly="true"></input>
														</cms:SystemPublishRule>
														<input type="button" value="规则..." class="btn-1" onclick="javascript:openSelectPublishRuleDialog('1','classHomePublishRuleId');" />
														<input type="hidden" id="classHomePublishRuleId" name="classHomePublishRuleId" value="${Class.classHomePublishRuleId}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														列表页发布规则:
													</td>
													<td class="td-input listdate-show">
														<cms:SystemPublishRule id="${Class.classPublishRuleId}">
															<input type="text" value="${Rule.ruleName}" style="width:472px" id="classPublishRuleIdShow" name="classPublishRuleName"  class="form-input" value="${Class.outLink}" readonly="true" />
														</cms:SystemPublishRule>
														<input type="button" value="规则..." class="btn-1" onclick="javascript:openSelectPublishRuleDialog('2','classPublishRuleId');" />
														<input type="hidden" id="classPublishRuleId" name="classPublishRuleId" value="${Class.classPublishRuleId}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														内容页发布规则:
													</td>
													<td class="td-input listdate-show">
														<cms:SystemPublishRule id="${Class.contentPublishRuleId}">
															<input type="text" value="${Rule.ruleName}" style="width:472px" id="contentPublishRuleIdShow" class="form-input" value="年月日/内容ID.html" readOnly="true"></input>
														</cms:SystemPublishRule>
														<input type="button" value="规则..." class="btn-1" onclick="javascript:openSelectPublishRuleDialog('4','contentPublishRuleId');" />
														<input type="hidden" id="contentPublishRuleId" name="contentPublishRuleId" value="${Class.contentPublishRuleId}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														列表分页发布数:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:111px" id="listPageLimit" name="listPageLimit" class="form-input" value="${Class.listPageLimit}"></input>
														页
														<span class="ps">设定列表页只发布前多少页，若为空值，则发布全部页数</span>
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														同步发布栏目:
													</td>
													<td class="td-input listdate-show">
														<input name="syncPubClass" type="radio" value="1" />
														是
														</span> &nbsp;&nbsp;&nbsp;&nbsp;
														<input name="syncPubClass" type="radio" value="0" />
														否
														</span>
														<span class="ps">栏目内容增加,删除,编辑操作将同步发布栏目页及频道页</span>
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
														<span class="ps">当在页面调用栏目以列表方式显示时,若不显示则不会出现</span>
														</span>
													</td>
												</tr>
												
												<tr>
													<td   class="input-title listdate-show">
														栏目SEO标题:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:472px" id="seoTitle" name="seoTitle" class="form-input" value="${Class.seoTitle}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title listdate-show">
														栏目SEO关键字:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:472px" id="seoKeyword" name="seoKeyword" class="form-input" value="${Class.seoKeyword}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														栏目SEO描叙:
													</td>
													<td class="td-input listdate-show">
													
														<textarea id="seoDesc" name="seoDesc" style="width:470px; height:65px;" class="form-textarea">${Class.seoDesc}</textarea>
													
													</td>
												</tr>

												<%--<tr>
													<td class="input-title listdate-show">
														状态:
													</td>
													<td class="td-input listdate-show">
														<input id="userStatus" name="useStatus" type="radio" value="1" />
														启用
														</span> &nbsp;
														<input id="userStatus" name="useStatus" type="radio" value="2" />
														停用
														</span>
													</td>
												</tr>
												--%>
											</table>

											<div style="height:20px;"></div>
											
										</li>
									</ul>
								</div>

								<!-- 第二部分:站点 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="listdate-show">

												<tr>
													<td class="input-title listdate-show">
														栏目扩展信息:
													</td>
													<td class="td-input listdate-show">
														<select id="extDataModelId" name="extDataModelId" class="form-select">
															<option value="-1">
																----- 请选栏目扩展模型 -----&nbsp;
															</option>

															<cms:SystemDataModelList modelType="3">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
														</select>

														<span class="ps">在"扩展模型信息"中,可编辑增加字段信息</span>
													</td>
												</tr>
												
												
												<tr>
													<td width="23%" class="input-title listdate-show">
														部门管理内容:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="orgMode" type="radio" class="form-radio" value="0" />
														关闭
														<input name="orgMode" type="radio" class="form-radio" value="1" />
														开启
														
														<span class="ps">开启部门管理,部门管理员添加的内容只能被本部门管理</span>
													</td>
												</tr>
												<tr>
													<td width="15%" class="input-title listdate-show">
														高级搜索支持:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="searchStatus" type="radio" class="form-radio" value="1" />
														开启
														<input name="searchStatus" type="radio" class="form-radio" value="0" />
														关闭
														<span class="ps">开启全文搜索,将支持类似Baidu,Google的高效搜索功能</span>
													</td>
												</tr>
												<tr>
													<td width="15%" class="input-title listdate-show">
														相关内容筛选:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="relateRangeType" type="radio" class="form-radio" value="1" />
														开启
														<input name="relateRangeType" type="radio" class="form-radio" value="0" />
														关闭
														<span class="ps">开启后,相关内容筛选操作会筛选当前栏目中关联的内容</span>
													</td>
												</tr>

												<tr>
													<td width="15%" class="input-title listdate-show">
														外部投稿:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="memberAddContent" type="radio" class="form-radio" value="1" />
														允许
														<input name="memberAddContent" type="radio" class="form-radio" value="0" />
														禁止
														<span class="ps">开启投稿,本站登陆状态合法会员允许给本栏目投递内容</span>
													</td>
												</tr>

												
												
												<tr>
													<td class="input-title listdate-show">
														引用来源栏目:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:452px" id="linkFromClassShow" name="linkFromClassShow" class="form-input" value="${Class.classTreePath}"></input>
														<input type="hidden"  id="linkFromClass" name="linkFromClass"  value="${Class.linkFromClass}"></input>
											
														<input type="button" value="清除" class="btn-1" onclick="javascript:clearLinkFromClass();" />
														<input type="button" value="选择" class="btn-1" onclick="javascript:openLinkFromClassDialog();" />
														<br/><span class="ps">所选栏目的新增内容将自动引用到本栏目</span>
													</td>
												</tr>
												
												<tr>
													<td class="input-title listdate-show">
														引用推送栏目:
													</td>
													<td class="td-input listdate-show">
														<input type="text" style="width:452px" id="linkToClassShow" name="linkToClassShow" class="form-input" value="${Class.toClassTreePath}"></input>
														<input type="hidden"  id="linkToClass" name="linkToClass"  value="${Class.linkToClass}"></input>
											
														<input type="button" value="清除" class="btn-1" onclick="javascript:clearLinkToClass();" />
														<input type="button" value="选择" class="btn-1" onclick="javascript:openLinkToClassDialog();" />
														<br/><span class="ps">当前栏目的新增内容将自动引用到所选栏目</span>
													</td>
												</tr>
												
												<tr>
													<td class="input-title listdate-show">
														内容关联类别:
													</td>
													<td class="td-input listdate-show">
														<textarea  id="relateClassIds" name="relateClassIds" style="width:450px; height:80px;" class="form-textarea">${Class.relateClassIds}</textarea>
													
													    <br/><span class="ps">内容关联自定义类别名称，多个分类使用逗号分隔</span>
													
													</td>
												</tr>
												
												<tr>
													<td class="input-title listdate-show">
														栏目访问白名单:
													</td>
													<td class="td-input listdate-show">
													
														<textarea id="whiteIp" name="whiteIp" style="width:452px; height:80px;" class="form-textarea">${Class.whiteIp}</textarea>
														 <br/><span class="ps">白名单IP或IP段能访问栏目，多个IP用逗号分隔，IP段格式如192.168.0.*</span>
													</td>
												</tr>
												
												<tr>
													<td class="input-title listdate-show">
														内容推送转换接口:
													</td>
													<td class="td-input listdate-show">
													
														<input type="text" style="width:472px" id="submitDCBehavior" name="submitDCBehavior" class="form-input" value="${Class.submitDCBehavior}"></input>
														 <br/><span class="ps">内容推送数据干预接口，可对接收的数据再加工，转换为数据模型可用模式</span>
														 
													</td>
												</tr>
												
												<tr>
													<td class="input-title listdate-show">
														内容推送白名单:
													</td>
													<td class="td-input listdate-show">
													
														<textarea id="submitWhiteIp" name="submitWhiteIp" style="width:452px; height:80px;" class="form-textarea">${Class.submitWhiteIp}</textarea>
														 <br/><span class="ps">白名单IP或IP段能使用栏目推送接口，多个IP用逗号分隔，IP段格式如192.168.0.*</span>
														 <br/><span class="ps">推送API: 当前站点首页/content/clientPushContent.do</span>
													</td>
												</tr>

												<tr>
													<td width="13%" class="input-title listdate-show">
														内容评论状态:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="openComment" type="radio" value="1" />
														开启
														<input name="openComment" type="radio" value="0" />
														关闭 
														<span class="ps">若关闭栏目内容评论, 栏目包含的所有内容将禁止被评论</span>
													</td>
												</tr>
												
												<tr>
													<td width="13%" class="input-title listdate-show">
														评论需要审核:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="mustCommentCensor" type="radio" value="0" />
														不需
														<input name="mustCommentCensor" type="radio" value="1" />
														需要
														<span class="ps">若开启栏目内容审核, 所有评论内容需要审核后才可显示</span>
													</td>
												</tr>

												<tr>
													<td width="13%" class="input-title listdate-show">
														非会员评论:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="notMemberComment" type="radio" value="1" />
														允许
														<input name="notMemberComment" type="radio" value="0" />
														不可 
														<span class="ps">若允许非会员评论, 任何登录会员和游客都可以进行评论</span>
													</td>
												</tr>
												
												<tr>
													<td width="13%" class="input-title listdate-show">
														强制输入验证码:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="commentCaptcha" type="radio" value="1" /> 
														开启
														<input name="commentCaptcha" type="radio" value="0" />
														关闭
														<span class="ps">若开启评论验证码, 将强制任何用户评论需要填写验证码</span>
													</td>
												</tr>
												
												

												

												<%--<tr>
													<td class="input-title listdate-show">
														栏目浏览权限:
													</td>
													<td class="td-input listdate-show">
														<select id="xx" name="xx" class="form-select">
															<option value="-1">
																----- 请选浏览权限配置 -----
																</span>&nbsp;
															</option>

															<cms:SystemDataModelList modelType="3">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}
																		</span>&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
														</select>


													</td>
												</tr>

												--%><tr>
													<td class="input-title listdate-show">
														编辑器图片规格:
													</td>
													<td class="td-input listdate-show">
														<input type="text" size="6" id="editorImageW" name="editorImageW" class="form-input" value="${Class.editorImageW}"></input>
														宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="editorImageH" name="editorImageH" class="form-input" value="${Class.editorImageH}"></input>
														高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<select class="form-select" id="editorImageDM" name="editorImageDM">
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
																宽和高&nbsp;
															</option>
														</select>
														缩放
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														首页引导图规格:
													</td>
													<td class="td-input listdate-show">
														<input type="text" size="6" id="homeImageW" name="homeImageW" class="form-input" value="${Class.homeImageW}"></input>
														宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="homeImageH" name="homeImageH" class="form-input" value="${Class.homeImageH}"></input>
														高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<select class="form-select" id="homeImageDM" name="homeImageDM">
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
																宽和高&nbsp;
															</option>
														</select>
														缩放
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														栏目页引导图规格:
													</td>
													<td class="td-input listdate-show">
														<input type="text" size="6" id="classImageW" name="classImageW" class="form-input" value="${Class.classImageW}"></input>
														宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="classImageH" name="classImageH" class="form-input" value="${Class.classImageH}"></input>
														高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<select class="form-select" id="classImageDM" name="classImageDM">
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
																宽和高&nbsp;
															</option>
														</select>
														缩放
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														列表引导图规格:
													</td>
													<td class="td-input listdate-show">
														<input type="text" size="6" id="listImageW" name="listImageW" class="form-input" value="${Class.listImageW}"></input>
														宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="listImageH" name="listImageH" class="form-input" value="${Class.listImageH}"></input>
														高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<select class="form-select" id="listImageDM" name="listImageDM">
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
																宽和高&nbsp;
															</option>
														</select>
														缩放
													</td>
												</tr>

												<tr>
													<td class="input-title listdate-show">
														内容引导图规格:
													</td>
													<td class="td-input listdate-show">
														<input type="text" size="6" id="contentImageW" name="contentImageW" class="form-input" value="${Class.contentImageW}"></input>
														宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="contentImageH" name="contentImageH" class="form-input" value="${Class.contentImageH}"></input>
														高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<select class="form-select" id="contentImageDM" name="contentImageDM">
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
																宽和高&nbsp;
															</option>
														</select>
														缩放
													</td>
												</tr>

												<%--<tr>
													<td width="15%" class="input-title listdate-show">
														编辑器图片水印:
													</td>
													<td width="87%" class="td-input listdate-show">
														<input name="editorImageMark" type="radio" class="form-radio" value="1" />
														需要&nbsp;
														<input name="editorImageMark" type="radio" class="form-radio" value="0" />
														无需
														<span class="ps">富编辑器上传组图水印此处独立配置</span>
													</td>
												</tr>

											--%></table>

											<div style="height:20px;"></div>
											
										</li>
									</ul>
								</div>



								<!-- 第三部分：栏目-->
								<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="listdate-show" style="padding-top: 8px;">
												<cms:SystemModelFiledList modelId="${Class.extDataModelId}" showMode="true">
													<cms:SystemModelFiled>
														<tr>
															<td class="input-title listdate-show" width="12%">
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
																							
																							</div>
				
																						</td>
																					</tr>
				
																				</table>
																				</center>
																			</td>
																		</tr>
				
																	
		
									
																</cms:Empty>
																	
																</table>
														
											</table>
											
											

											<div style="height:40px;"></div>
											<div class="breadnavTab" >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<td class="input-title" width="12%"></td>
														<td class="td-input" style="height: 29px;">
															<a href="javascript:submitEditForm()"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
															<a href="javascript:openMoveChannelDialog();"  class="btnwithico"><img src="../style/icons/folder--arrow.png" width="16" height="16"/><b>移动&nbsp;</b> </a>
															 
															<a href="javascript:openCopyClassConfigDialog();"  class="btnwithico"><img src="../style/icons/document-convert.png" width="16" height="16"/><b>同步配置&nbsp;</b> </a>
															<a href="javascript:gotoSortChannelPage();"  class="btnwithico"><img src="../style/default/images/sort-number.png" width="16" height="16"/><b>子栏目排序&nbsp;</b> </a>
															<a href="javascript:openAddContentClassDialog();"  class="btnwithico"><img src="../style/icons/folder-horizontal-open.png" width="16" height="16"/><b>添加子栏目&nbsp;</b> </a>									
														
															<a href="javascript:openDeleteChannelDialog();"  class="btnwithico"><img src="../style/icons/folder--minus.png" width="16" height="16"/><b>删子栏目&nbsp;</b> </a>
															<a href="javascript:submitDeleteAction();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>删自身&nbsp;</b> </a>
												
														</td>
													</tr>
												</table>
											 <div style="height:3px;"></div>
											</div>
										</li>
									</ul>
								</div>
							</div>

							<!-- hidden -->
							<input type="hidden" id='classId' name='classId' value='${Class.classId}' />
							<input type="hidden" id='parent' name='parent' value='${Class.parent}' />
							<input type="hidden" id='isLastChild' name='isLastChild' value='${Class.isLastChild}' />
							<input type="hidden" id='sys_old_model_id' name='sys_old_model_id' value='${Class.contentType}' />
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

disposeClassTypeChange();

initSelect('contentType','${Class.contentType}');

initSelect('workflowId','${Class.workflowId}');

initRadio('classHomeProduceType','${Class.classHomeProduceType}');

initRadio('classProduceType','${Class.classProduceType}');

initRadio('contentProduceType','${Class.contentProduceType}');

initRadio('needCensor','${Class.needCensor}');

initRadio('syncPubClass','${Class.syncPubClass}');

initRadio('useStatus','${Class.useStatus}');

initRadio('showStatus','${Class.showStatus}');

//其他配置

initRadio('orgMode','${Class.orgMode}');

initRadio('searchStatus','${Class.searchStatus}');

initRadio('relateRangeType','${Class.relateRangeType}');

initRadio('memberAddContent','${Class.memberAddContent}');

initSelect('extDataModelId','${Class.extDataModelId}');

initRadio('openComment','${Class.openComment}');

initRadio('mustCommentCensor','${Class.mustCommentCensor}');

initRadio('notMemberComment','${Class.notMemberComment}');


//initRadio('commentHtml','${Class.commentHtml}');

//initRadio('filterCommentSensitive','${Class.filterCommentSensitive}');

initRadio('commentCaptcha','${Class.commentCaptcha}');

//initRadio('editorImageMark','${Class.editorImageMark}');

initSelect('editorImageDM','${Class.editorImageDM}');

initSelect('homeImageDM','${Class.homeImageDM}');

initSelect('classImageDM','${Class.classImageDM}');

initSelect('listImageDM','${Class.listImageDM}');

initSelect('contentImageDM','${Class.contentImageDM}');

var moveSucc = false;

var deleteSucc = false;

function setTab(flag,pos,size)
{
		hideTips('className');
		hideTips('classFlag');
		setTab2(flag,pos,size);
	
}


function openAddContentClassDialog()
{
    var classId = '${Class.classId}';
    var className = '${Class.className}';
	$.dialog({ 
	    id : 'oacd',
    	title : '增加栏目(可批量添加)',
    	width: '645px', 
    	height: '570px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/channel/AddContentClass.jsp?classId='+classId+'&className='+encodeURIComponent(encodeURIComponent(className))
	
	  
	});
}

function openMoveChannelDialog()
{

	$.dialog({ 
	    id : 'occtcd',
    	title : '移动栏目节点位置 - ${Class.className}',
    	width: '430px', 
    	height: '500px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        close: function () 
        { 
            if(moveSucc)
            {
        		window.parent.frames["treeFrame"].location.reload();
        	}
        } ,
       
        content: 'url:<cms:BasePath/>core/channel/ShowMoveChannel.jsp?uid='+Math.random()+'&classId=${Class.classId}&parent=${Class.parent}&linear=${Class.linearOrderFlag}'
	});


}

function openLinkFromClassDialog()
{
	<cms:CurrentSite>
 			 
	var siteId = '${CurrSite.siteId}';
		 
	</cms:CurrentSite>

	$.dialog({ 
	    id : 'oclfcd',
    	title : '内容引用源栏目设置 - ${Class.className}',
    	width: '480px', 
	    height: '560px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        close: function () 
        { 
            if(moveSucc)
            {
        		window.parent.frames["treeFrame"].location.reload();
        	}
        } ,
       
        content: 'url:<cms:BasePath/>core/channel/ShowLinkFromClass.jsp?uid='+Math.random()+'&siteId='+siteId+'&classId=${Class.classId}&parent=${Class.parent}&linear=${Class.linearOrderFlag}'
	});


}

function clearLinkFromClass()
{
	document.getElementById('linkFromClassShow').value='';
	document.getElementById('linkFromClass').value='';
}


function openLinkToClassDialog()
{
	<cms:CurrentSite>
 			 
	var siteId = '${CurrSite.siteId}';
		 
	</cms:CurrentSite>

	$.dialog({ 
	    id : 'oclfcd',
    	title : '内容推引用栏目设置 - ${Class.className}',
    	width: '480px', 
	    height: '560px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        close: function () 
        { 
            if(moveSucc)
            {
        		window.parent.frames["treeFrame"].location.reload();
        	}
        } ,
       
        content: 'url:<cms:BasePath/>core/channel/ShowLinkFromClass.jsp?mode=to&uid='+Math.random()+'&siteId='+siteId+'&classId=${Class.classId}&parent=${Class.parent}&linear=${Class.linearOrderFlag}'
	});


}

function clearLinkToClass()
{
	document.getElementById('linkToClassShow').value='';
	document.getElementById('linkToClass').value='';


}



function openDeleteChannelDialog()
{

	$.dialog({ 
	    id : 'occtcd',
    	title : '删除所属子栏目 - ${Class.className}',
    	width: '430px', 
    	height: '500px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        close: function () 
        { 
            if(deleteSucc)
            {
        		window.parent.frames["treeFrame"].location.reload();
        	}
        } ,
       
        content: 'url:<cms:BasePath/>core/channel/ShowDeleteChannel.jsp?uid='+Math.random()+'&classId=${Class.classId}&linear=${Class.linearOrderFlag}'
	});


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
   
   
   
   if($('[name=classHomeProduceType]').attr('checked') == 'checked' && $('#classHomePublishRuleIdShow').val() == '')
   {
   
   			$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择一个频道页发布规则!',
   
                    ok: true 
			});
			
			return;
   
   }
 
   if($('[name=classProduceType]').attr('checked') == 'checked' && $('#classPublishRuleIdShow').val() == '')
   {
   
   			$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择一个列表页发布规则!',
   
                    ok: true 
			});
			
			return;
   
   }
   
   
   
   
   if($('[name=contentProduceType]').attr('checked') == 'checked' && $('#contentPublishRuleIdShow').val() == '')
   {
   
   			$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择一个内容页发布规则!',
   
                    ok: true 
			});
			
			return;
   
   }
   
    
   
   if($('[name=classType]:checked').val() == '3')
   {
   
   		if(($('[name=contentProduceType]').attr('checked') != 'checked' && $('[name=classProduceType]').attr('checked') == 'checked')
   			||
   		   ($('[name=contentProduceType]').attr('checked') == 'checked' && $('[name=classProduceType]').attr('checked') != 'checked'))
   		{
   			$.dialog({ 
   					title :'提示',
    				width: '260px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '单页模式静态化发布必须列表静态和内容静态同时选择!',
   
                    ok: true 
			});
			
			return;
   		
   		}
   
   }
   
   if('${Class.contentType}' != $('#contentType').val())
   {
   	 $.dialog.tips('内容模型已经更换，正在删除原数据到回收站...',3600000000,'loading.gif');
   }
   
         var currError = submitValidate('className',1,null,null);
  
   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	setTab2('two',1,3);
    	submitValidate('classFlagShow',0,ref_flag,'格式为字母,数字,下划线');
    	submitValidate('className',1,null,null);
    
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	     
	    return;

	}
	
	var tip = $.dialog.tips('正在执行...',3600000000,'loading.gif');
	   
		encodeFormEditor('classForm', false);
		
		url = "<cms:BasePath/>channel/editContentClass.do"+"?<cms:Token mode='param'/>";
	    var postData = encodeURI($("#classForm").serialize());
	   
	    postData = postData.replace(/\+/g, " ");
	    postData = encodeData(postData);
	 	
	 	
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:true,
		       	data:postData,
		   
		       	success: function(mg)
		        {  
		        			var msg = eval("("+mg+")");
		        			
		       			   if('success' == msg  || '' == msg )
			               { 
			               	   $.dialog(
							   { 
								   					title :'提示',
								    				width: '140px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/succ.png', 
								    				
								                    content: "编辑栏目成功！",
						
								    				ok: function()
								    				{
								    					window.location.reload();
								    				}
								});
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
			               
			               tip.close();	  
		        }
		        
		});
	

   
   //document.all.classForm.action='<cms:BasePath/>channel/editContentClass.do';
  // document.all.classForm.submit(); 

}



function submitDeleteAction()
{
	$.dialog({ 
		 title :'提示',
		 width: '300px', 
		 height: '60px', 
         lock: true, 
		 icon: '32X32/i.png', 
		 ok : function()
		 {
		 	$.dialog.tips('正在删除栏目以及附属信息,请等待...',360000000,'loading.gif'); 
		 	
		 	encodeFormInput('classForm', false);
		 	 
		 	document.all.classForm.action='<cms:BasePath/>channel/deleteContentClass.do'+"?<cms:Token mode='param'/>";
    	    document.all.classForm.submit();
		 },
		    				
		 content: '当前栏目 <font color="red">${Class.className}</font> 和其所有子栏目的相关信息将被物理删除,无法恢复! 您确定执行吗?', 
		 cancel: true 
	});

     


}



function gotoSortChannelPage()
{
	$.dialog({ 
		id:'gscp',
    	title :'排序 - ${Class.className}',
    	width: '420px', 
    	height: '480px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
        close: function () 
        { 
        	window.parent.frames["treeFrame"].location.reload();
        } ,
        
        content: 'url:<cms:Domain/>core/channel/ShowSortChannel.jsp?classId=${Class.classId}'
	});
}



function addClass()
{
	window.location.href="AddContentClassForParent.jsp?classId=${Class.classId}&className=${Class.className}";
}



function openSelectPublishRuleDialog(type,idName)
{
	$.dialog({ 
		id:'osprd',
    	title :'选择发布规则',
    	width: '640px', 
    	height: '600px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
             
        content: 'url:<cms:Domain/>core/channel/SelectPublishRule.jsp?type='+type+'&idName='+idName
	});
}

function openCopyClassConfigDialog()
{
    var classId = '${Class.classId}';
    var className = '${Class.className}';
	$.dialog({ 
	    id : 'occcd',
    	title : '同步配置到其他栏目',
    	width: '610px', 
    	height: '510px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/channel/CopyChannelConfig.jsp?classId='+classId+'&className='+className

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
       //alert(currrentTemplet);

       
       window.showModalDialog("EditChannelTemplet.jsp?entry="+currrentTemplet.replaceAll("/","*"),"","dialogWidth=1250px;dialogHeight=600px;status=no");
	 
}

function disposeClassTypeChange()
{
	var classTypeArray = document.getElementsByName('classType');

	for(var i = 0; i < classTypeArray.length; i++)
	{
		if(classTypeArray[i].checked == true)
		{
			if(classTypeArray[i].value==1)
			{
				document.getElementById('outLinkTr').style.display="none";
			}
			else if(classTypeArray[i].value==2)
			{
				document.getElementById('outLinkTr').style.display="";
			}
			else if(classTypeArray[i].value==3)
			{
				document.getElementById('outLinkTr').style.display="none";
			}
			else if(classTypeArray[i].value==4)
			{
				document.getElementById('outLinkTr').style.display="none";
			}
		}
	}
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


//导入数据
function importData()
{

	var tip = 
	'注意：请按照原始EXCEL数据模板制作表格，不符合规则的EXCEL数据将被忽略！以下为关键数据要求\n'+
	'供给：技术名称 供求编号          不能为空且不可重复\n'+

			   '需求: 需求名称  需求编号         不能为空且不可重复\n'+

                 '专家:  专家姓名  专家编号        不能为空且不可重复\n'+

                  '企业：公司名称  组织机构代码     不能为空且不可重复\n'+

                   ' 项目公示: 项目名称  序号         不能为空且不可重复';
                   
                   

		if(confirm(tip)){
			var tip = $.dialog.tips('正在导入Excel数据，请等待...',3600000000,'loading.gif');
	   
		encodeFormEditor('classForm', false);
		
		url = "<cms:BasePath/>gz/importExcel.do"+"?<cms:Token mode='param'/>";
	    var postData = 'classId=${param.classId}';
	   
	    
	 	
	 	
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:true,
		       	data:postData,
		   
		       	success: function(mg)
		        {   
		        
		        			var msg = eval("("+mg+")");
		        			
		       			   if('success' == msg  || '' == msg )
			               { 
			               	   $.dialog(
							   { 
								   					title :'提示',
								    				width: '340px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/succ.png', 
								    				
								                    content: "业务请求成功!<br/>导入最终结果请查看excel_report目录所含报表！",
						
								    				ok: function()
								    				{
								    					window.location.reload();
								    				}
								});
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
								    				
								                    content: "执行失败，请联系管理员！错误码:"+msg,
						
								    				cancel: function()
								    				{
								    					window.location.reload();
								    				}
								});
			               }   	
			               
			               tip.close();	  
		        }
		        
		});
  			
  }
  else{
   	  
  }
		


		




}



</script>
</cms:SysClass>
