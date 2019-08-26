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
		
		<cms:Set val="'fullscreen', 'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','removeformat','forecolor', 'backcolor', 'formatmatch', 'autotypeset', 'link', 'unlink', 'anchor','fontfamily', 'fontsize','undo', 'redo'" id="UE_SMP" />

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
	             //api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             //W.location.reload();
         	} 		       
         }
         else if('true'==='${param.redirect}')
         {
         	if('' == '${param.flag}')
         	{
 					$.dialog.tips('改动站点配置成功',1,'32X32/succ.png'); 
         	}
         	else if('1' == '${param.flag}' || '2' == '${param.flag}')
         	{	
         	    if('-9999' == '${param.classId}' && '2' == '${param.flag}')
         	    {
         	    	 
			    	$.dialog.tips('删除栏目成功',1,'32X32/succ.png'); 
         	    }
         	    else
         	    {
         	    	if('-9999' == '${param.classId}')
         	    	{
         	    		$.dialog
						({ 
									id:'tip',
				   					title :'提示',
				    				width: '140px', 
				    				height: '70px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				                    content: '添加根栏目成功!',
				                    
				   				 	ok: function()
				   				 	{
				   				 		
				   				 	}
				    	});
         	    		
         	    	}
         	    	else
         	    	{
         				window.location.href = 'EditContentClass.jsp?classId=${param.classId}&redirect=true&flag=${param.flag}';
         	    	}
         	    }
         	}
         	
         }
         
      
 		 
 		 var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
         var ref_url = /^http[s]?:\/\/([\w-]+\.)+[\w-]+([\w-./?%&=:]*)?$/;

         $(function()
		 {
		    validate('siteName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 	
 			validate('siteUrl',0,ref_url,'格式为合法的URL地址');	
	
		 })
        	
      </script>
	</head>
	<body>
		<cms:CurrentSite>
			<cms:Site siteId="${CurrSite.siteId}">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">
							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top: 0px;">
								<ul>
									<li id="two1" onclick="setTab('two',1,7)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/sitemap-application.png" width="16" height="16" />站点信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab('two',2,7)">
										<a href="javascript:;"><img src="../style/icons/gear--pencil.png" width="16" height="16" />相关参数&nbsp;</a>
									</li>
									<li id="two3" onclick="setTab('two',3,7)">
										<a href="javascript:;"><img src="../style/icons/mail.png" width="16" height="16" />通信接口&nbsp;</a>
									</li>
									<li id="two4" onclick="setTab('two',4,7)">
										<a href="javascript:;"><img src="../style/icons/images.png" width="16" height="16" />图片与文件&nbsp;</a>
									</li>
									<li id="two5" onclick="setTab('two',5,7)">
										<a href="javascript:;"><img src="../style/icons/users.png" width="16" height="16" />会员相关&nbsp;</a>
									</li>
									<li id="two6" onclick="setTab('two',6,7)">
										<a href="javascript:;"><img src="../style/icons/blogs.png" width="16" height="16" />默认内容模板&nbsp;</a>
									</li>
									<li id="two7" onclick="setTab('two',7,7)">
										<a href="javascript:;"><img src="../style/icons/table--pencil.png" width="16" height="16" />扩展信息&nbsp;</a>
									</li>

								</ul>
							</div>

							<form id="editSiteForm" name="editSiteForm" method="post">
								<div class="auntion_tagRoom_Content">
									<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
										<ul>
											<li>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-manage">

													<tr>
														<td width="22%" class="input-title listdate-show">
															站点名称:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="siteName" name="siteName" class="form-input" value="${Site.siteName}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>

													<tr id="pc-url" style="display:">
														<td class="input-title listdate-show">
															首页地址:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="siteUrl" name="siteUrl" class="form-input" value="${Site.siteUrl}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>
													
													<%--<tr id="mob-url" style="display:none">
														<td class="input-title listdate-show">
															Mobi首页地址:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="mobSiteUrl" name="mobSiteUrl" class="form-input" value="${Site.mobSiteUrl}"></input>
															 
														</td>
													</tr>
													
													<tr id="pad-url" style="display:none">
														<td class="input-title listdate-show">
															Pad首页地址:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="padSiteUrl" name="padSiteUrl" class="form-input" value="${Site.padSiteUrl}"></input>
															 
														</td>
													</tr>
													

													--%><tr>
														<td class="input-title listdate-show">
															访问标识:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" class="form-input" value="${Site.siteFlag} (ID:${Site.siteId})" disabled></input>

														</td>
													</tr>

													<tr>

													</tr>


													<tr>
														<td class="input-title listdate-show">
															站点Logo:
														</td>
														<td class="td-input listdate-show">
															<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																<tr>
																	<td>
																		<a class="cmsSysShowSingleImage" id="logoImageCmsSysShowSingleImage" href="${Site.logoImage}"><img id="logoImageCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="${Site.logoImageReUrl}" />" width="90" height="67" /> </a>
																	</td>
																	<td>
																		<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																			<tr>
																				<td>
																					&nbsp;
																					<input type="button" onclick="javascript:showModuleImageDialog('logoImageCmsSysImgShow','logoImage','','','0','0')" value="上传" class="btn-1" />
																					<input type="button" value="裁剪" onclick="javascript:disposeImage('logoImage','','',false,'-1');" class="btn-1" />
																					<input type="button" value="删除" onclick="javascript:deleteImage('logoImage');" class="btn-1" />
																				</td>
																			</tr>
																			<tr>
																				<td>
																					&nbsp;&nbsp;宽&nbsp;&nbsp;
																					<input id="logoImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Site.logoImageImageW}" />
																					&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																					<input id="logoImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Site.logoImageImageH}" />
																				</td>
																			</tr>
																		</table>
																		<input id="logoImage" name="logoImage" type="hidden" value="${Site.logoImageResId}" />
																		<input id="logoImage_sys_jtopcms_old" name="logoImage_sys_jtopcms_old" type="hidden" value="${Site.logoImageResId}" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>

													
													<tr>
														<td class="input-title listdate-show">
															备案号:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="icp" name="icp" class="form-input" value="${Site.icp}"></input>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															公网安备:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="copyright" name="copyright" class="form-input" value="${Site.copyright}"></input>
														</td>
													</tr>

													<%--<tr>
														<td class="input-title listdate-show">
															站点描叙:
														</td>

														<td class="td-input listdate-show">
															<textarea id="siteDesc" name="siteDesc" style="width:470px; height:65px" class="form-textarea">${Site.siteDesc}</textarea>
														</td>

													</tr>

													--%><tr>
													<td class="input-title listdate-show">
														启用移动模板:
													</td>
													<td class="td-input listdate-show">
													 	<input name="pcVm" id="pcVm" type="hidden"  value="1" />
													
														<input name="mobVm" id="mobVm" type="checkbox"   value="1" />
														移动Mobi端&nbsp;&nbsp;&nbsp;
														<input name="padVm" id="padVm" type="checkbox"   value="1" />
														移动Pad端&nbsp;&nbsp;&nbsp;
														
													</td>
												</tr>

													<tr>
														<td class="input-title listdate-show">
															首页模板:
														</td>
														<td class="td-input listdate-show">
															<span id="pc-tpl" style="display:">
															<input type="text" size="64" id="homePageTemplate" name="homePageTemplate" class="form-input" value="${Site.homePageTemplate}"></input>
															<input type="button" value="默认模板" class="btn-1" onclick="javascript:openSelectTempletDialog('home','','pc');" />
															</span>
															
															<span id="mob-tpl" style="display:none">
															
															<br/><br/>
															<input type="text" size="64" id="mobHomePageTemplate" name="mobHomePageTemplate" class="form-input" value="${Site.mobHomePageTemplate}"></input>
															<input type="button" value="Mob模板" class="btn-1" onclick="javascript:openSelectTempletDialog('home','','mob');" />
															</span>
															
															
															<span id="pad-tpl" style="display:none">
														    <br/><br/>
															<input type="text" size="64" id="padHomePageTemplate" name="padHomePageTemplate" class="form-input" value="${Site.padHomePageTemplate}"></input>
															<input type="button" value="Pad模板" class="btn-1" onclick="javascript:openSelectTempletDialog('home','','pad');" />
															</span>
														</td>
													</tr>
													<script>
													
													//if('${Site.pcVm}' == '1')
													{
														//$('#pc-url').show();
														//$('#pc-tpl').show();
													}
													
													if('${Site.mobVm}' == '1')
													{
														$('#mob-url').show();
														$('#mob-tpl').show();
													}
													
													if('${Site.padVm}' == '1')
													{
														$('#pad-url').show();
														$('#pad-tpl').show();
													}
													
													</script>
													<tr>
														<td class="input-title listdate-show">
															静态页类型:
														</td>
														<td class="td-input listdate-show">
															<select id="staticFileType" name="staticFileType" class="form-select">
																<option value="1">
																	html&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																</option>
																<option value="2">
																	shtml
																</option>
															</select>
															<span class="ps">&nbsp;首页静态化文件为shtml,支持模板区块碎片静态化</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															首页静态发布:
														</td>
														<td class="td-input listdate-show">
															<input name="homePageProduceType" type="radio" value="3" />
															开启&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="homePageProduceType" type="radio" value="1" />
															关闭&nbsp;
															<span class="ps">开启后,首页以静态html(或shtml)静态页形式展示内容</span>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															移动访问跳转:
														</td>
														<td class="td-input listdate-show">
															<input name="mobJump" type="radio" value="0" onclick="javascript:changeMobJump()"/>
															关闭&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="mobJump" type="radio" value="1" onclick="javascript:changeMobJump()"/>
															开启
															<span class="ps">&nbsp;开启后,移动端的页面访问请求将跳转到指定移动站点</span>
														</td>
													</tr>
													
													<tr id="jumpMobTr" style="display:none">
														<td class="input-title listdate-show">
															目标跳转站点:
														</td>
														<td class="td-input listdate-show">
															<select id="mobSiteId" name="mobSiteId" class="form-select">
																<option value="-1">
																	--------------------- 请选择一个适应移动端站点 ---------------------
																</option>
																
																
																<cms:QueryData service="cn.com.mjsoft.cms.site.service.SiteGroupService" method="getAllSiteNotSelfForTag" objName="AllSite" >
																
																		<option value="${AllSite.siteId}">
																		  ${AllSite.siteName}
																	    </option>															 
																
																</cms:QueryData>
																
															</select>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															站点状态:
														</td>
														<td class="td-input listdate-show">
															<input name="useState" type="radio" value="1" />
															启用&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="useState" type="radio" value="0" />
															关闭
															<span class="ps">&nbsp;关闭站点后,所有动态页面将处于无法访问状态 (404)</span>
														</td>
													</tr>
													
													




												</table>

												<div style="height:15px;"></div>

											</li>
										</ul>
									</div>

									<!-- 第二部分:站点 -->
									<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
													<tr>
														<td class="input-title listdate-show">
															站点扩展信息:
														</td>
														<td class="td-input listdate-show">
															<select id="extDataModelId" name="extDataModelId" class="form-select">
																<option value="-1">
																	----- 请选站点扩展模型 -----&nbsp;
																</option>

																<cms:SystemDataModelList modelType="7">
																	<cms:SystemDataModel>
																		<option value="${DataModel.dataModelId}">
																			${DataModel.modelName}&nbsp;
																		</option>
																	</cms:SystemDataModel>
																</cms:SystemDataModelList>
															</select>

															<span class="ps">在"扩展模型信息"中，可编辑字段值</span>
														</td>
													</tr>

													<tr>
														<td width="22%"  class="input-title listdate-show">
															站群内容共享:
														</td>
														<td class="td-input listdate-show">
															<input name="shareMode" type="radio" value="1" />
															开启&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="shareMode" type="radio" value="0" />
															关闭
															<span class="ps">&nbsp;开启时,本站内容可共享到其他站点(需模型能识别)</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															下载外站文件:
														</td>
														<td class="td-input listdate-show">
															<input name="downOutImage" type="radio" value="1" />
															开启&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="downOutImage" type="radio" value="0" />
															关闭
															<span class="ps">&nbsp;开启下载文件,采集目标的文件和图片将被下载到本地</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															删除外站链接:
														</td>
														<td class="td-input listdate-show">
															<input name="deleteOutLink" type="radio" value="1" />
															开启&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="deleteOutLink" type="radio" value="0" />
															关闭
															<span class="ps">&nbsp;开启删除链接,内容编辑中将删除非本站的外部链接</span>
														</td>
													</tr>


													<tr>
														<td class="input-title listdate-show">
															栏目重复标题:
														</td>
														<td class="td-input listdate-show">
															<input name="sameTitle" type="radio" value="0" />
															唯一&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="sameTitle" type="radio" value="1" />
															允许
															<span class="ps">&nbsp;开启允许重复标题,栏目中将存在主标题相同的内容</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															默认关键字:
														</td>
														<td class="td-input listdate-show">
															<input name="genKw" type="radio" value="1" />
															生成&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="genKw" type="radio" value="0" />
															空值
															<span class="ps">&nbsp;开启默认关键字,若没有录入内容关键字,则自动生成</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															自动摘要长度:
														</td>
														<td class="td-input listdate-show">
															<input id="summaryLength" name="summaryLength" class="form-input" type="text" value="${Site.summaryLength}" size="16" />
															<span class="ps">若不录入摘要，则系统将截取编辑器中内容填入摘要</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															默认点击数:
														</td>
														<td class="td-input listdate-show">
															<input id="defClickCount" name="defClickCount" class="form-input" type="text" value="${Site.defClickCount}" size="16" />
															<span class="ps">若填入值大于0，内容初始点击数将以此值随机生成</span>
														</td>
													</tr>

													


													<tr>
														<td class="input-title listdate-show">
															全站高级搜索:
														</td>
														<td class="td-input listdate-show">
															<input name="searchFun" type="radio" value="1" />
															开启&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="searchFun" type="radio" value="0" />
															关闭
															<span class="ps">&nbsp;若关闭,站点将不能使用高级类Baidu,Google搜索</span>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															编辑器模式:
														</td>
														<td class="td-input listdate-show">
															<input name="siteCollType" type="radio" value="1" />
															所有&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="siteCollType" type="radio" value="0" />
															安全
															<span class="ps">&nbsp;安全模式下全站编辑器只允许提交安全html内容</span>
														</td>
													</tr>
													
													<tr>
														<td width="22%" class="input-title listdate-show">
															默认SEO标题:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="seoTitle" name="seoTitle" class="form-input" value="${Site.seoTitle}"></input>
														</td>
													</tr>
													<tr>
														<td class="input-title listdate-show">
															默认SEO关键字:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="seoKeyword" name="seoKeyword" class="form-input" value="${Site.seoKeyword}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															默认SEO描叙:
														</td>
														<td class="td-input listdate-show">
															<textarea id="seoDesc" name="seoDesc" style="width:470px; height:55px;" class="form-textarea">${Site.seoDesc}</textarea>
														</td>
													</tr>

													<%--<tr>
														<td class="input-title listdate-show">
															主动防御:
														</td>
														<td class="td-input listdate-show">
															<input name="useFW" type="radio" value="1" />
															开启
															<input name="useFW" type="radio" value="0" />
															关闭
															<span class="ps">警告:如非特殊需要,请不要关闭主动防御拦截功能</span>
														</td>
													</tr>
												--%></table>

												<div style="height:20px;"></div>

											</li>
										</ul>
									</div>

									<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
													<tr>
														<td width="22%" class="input-title listdate-show">
															邮件服务器地址:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="sendMailHost" name="sendMailHost" class="form-input" value="${Site.sendMailHost}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															邮箱显示名称:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="mailUserName" name="mailUserName" class="form-input" value="${Site.mailUserName}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															邮箱登陆名:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="mail" name="mail" class="form-input" value="${Site.mail}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															发送邮箱密码:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="mailUserPW" name="mailUserPW" class="form-input" value="${Site.mailUserPW}"></input>
														</td>
													</tr>
													<tr>
														<td class="input-title listdate-show">
															邮箱连接方式:
														</td>
														<td class="td-input listdate-show">
															<input name="mailSSL" type="radio" value="0" />
															普通&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="mailSSL" type="radio" value="1" />
															SSL
															<span class="ps">&nbsp;某些邮件服务器,需要使用SSL方式连接</span>
														</td>
													</tr>
													
													<%--<tr>
														<td class="input-title listdate-show">
															微信AppId:
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="75" id="wxAppId" name="wxAppId" class="form-input" value="${Site.wxAppId}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															微信AppKey:
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="75" id="wxAppKey" name="wxAppKey" class="form-input" value="${Site.wxAppKey}"></input>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															微信测试用户:
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="75" id="wxPrevUid" name="wxPrevUid" class="form-input" value="${Site.wxPrevUid}"></input>
														</td>
													</tr>
													
													

													--%><tr>
														<td class="input-title listdate-show">
															短信发送接口地址:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="smsApiUrl" name="smsApiUrl" class="form-input" value="${Site.smsApiUrl}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															短信发送帐户:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="smsAccount" name="smsAccount" class="form-input" value="${Site.smsAccount}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															用户协议密码:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="smsPW" name="smsPW" class="form-input" value="${Site.smsPW}"></input>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															同号码发送间隔:
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="17" id="smsSendOnceSec" name="smsSendOnceSec" class="form-input" value="${Site.smsSendOnceSec}"></input>
															&nbsp;分钟
															<span class="ps">同一手机号码两次发送间隔时间,建议设置值</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															号码最大限制次数:
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="17" id="smsMaxCount" name="smsMaxCount" class="form-input" value="${Site.smsMaxCount}"></input>
															&nbsp;次数
															<span class="ps">每个手机号码最多发送几次短信,建议设置值</span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															IP每天发送次数:
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="17" id="smsIpDayCount" name="smsIpDayCount" class="form-input" value="${Site.smsIpDayCount}"></input>
															&nbsp;次数
															<span class="ps">每个IP在一天最大发送短信限制,建议设置值</span>
														</td>
													</tr>


												</table>

												<div style="height:35px;"></div>

											</li>
										</ul>

									</div>

									<!-- 第三部分：栏目-->
									<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
													<tr>
														<td width="22%" class="input-title listdate-show">
															允许图片类型:
														</td>
														<td class="td-input listdate-show">
															<textarea id="imageAllowType" name="imageAllowType" style="width:400px; height:55px;" class="form-textarea">${Site.imageAllowType}</textarea>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															允许多媒体类型:
														</td>
														<td class="td-input listdate-show">
															<textarea id="mediaAllowType" name="mediaAllowType" style="width:400px; height:55px;" class="form-textarea">${Site.mediaAllowType}</textarea>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															允许文件类型:
														</td>
														<td class="td-input listdate-show">
															<textarea id="fileAllowType" name="fileAllowType" style="width:400px; height:55px;" class="form-textarea">${Site.fileAllowType}</textarea>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															上传文件大小限制:
														</td>
														<td width="87%" class="td-input listdate-show">
															图片&nbsp;
															<input id="imageMaxC" name="imageMaxC" type="text" class="form-input" style="width:48px;" value="${Site.imageMaxC}" />
															&nbsp;(MB)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;媒体&nbsp;
															<input id="mediaMaxC" name="mediaMaxC" type="text" class="form-input" style="width:48px;" value="${Site.mediaMaxC}" />
															&nbsp;(MB)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;附件&nbsp;
															<input id="fileMaxC" name="fileMaxC" type="text" class="form-input" style="width:48px;" value="${Site.fileMaxC}" />
															&nbsp;(MB)
														</td>
													</tr>



													<tr>
														<td class="input-title listdate-show">
															编辑器图片默认:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:68px;" id="defEditorImageW" name="defEditorImageW" class="form-input" value="${Site.defEditorImageW}"></input>
															宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="text" style="width:68px;" id="edefEditorImageH" name="defEditorImageH" class="form-input" value="${Site.defEditorImageH}"></input>
															高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<select class="form-select" id="defEditorImageDM" name="defEditorImageDM">
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
																	宽和高
																</option>
															</select>
															缩放
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															首页引导图默认:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:68px;" id="defHomeImageW" name="defHomeImageW" class="form-input" value="${Site.defHomeImageW}"></input>
															宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="text" style="width:68px;" id="defHomeImageH" name="defHomeImageH" class="form-input" value="${Site.defHomeImageH}"></input>
															高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<select class="form-select" id="defHomeImageDM" name="defHomeImageDM">
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
																	宽和高
																</option>
															</select>
															缩放
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															栏目页引导图默认:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:68px;" id="defClassImageW" name="defClassImageW" class="form-input" value="${Site.defClassImageW}"></input>
															宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="text" style="width:68px;" id="defClassImageH" name="defClassImageH" class="form-input" value="${Site.defClassImageH}"></input>
															高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<select class="form-select" id="defClassImageDM" name="defClassImageDM">
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
																	宽和高
																</option>
															</select>
															缩放
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															列表引导图默认:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:68px;" id="defListImageW" name="defListImageW" class="form-input" value="${Site.defListImageW}"></input>
															宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="text" style="width:68px;" id="defListImageH" name="defListImageH" class="form-input" value="${Site.defListImageH}"></input>
															高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<select class="form-select" id="defListImageDM" name="defListImageDM">
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
																	宽和高
																</option>
															</select>
															缩放
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															内容引导图默认:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:68px;" id="defContentImageW" name="defContentImageW" class="form-input" value="${Site.defContentImageW}"></input>
															宽度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="text" style="width:68px;" id="defContentImageH" name="defContentImageH" class="form-input" value="${Site.defContentImageH}"></input>
															高度(px)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<select class="form-select" id="defContentImageDM" name="defContentImageDM">
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
																	宽和高
																</option>
															</select>
															缩放
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															上传图片加水印:
														</td>
														<td width="87%" class="td-input listdate-show">
															<input name="useImageMark" type="radio" value="1" />
															开启水印&nbsp;&nbsp;
															<input name="useImageMark" type="radio" value="0" />
															关闭水印&nbsp;&nbsp;&nbsp;&nbsp;
															<span class="input-title ">水印透明度:&nbsp;</span>
															<input type="text" style="width:58px;" id="imageMarkDis" name="imageMarkDis" class="form-input" value="${Site.imageMarkDis}"></input>
															<span class="ps">值100最高无透明</span>
														</td>
													</tr>
													<tr>
														<td class="input-title listdate-show">
															水印加入位置:
														</td>
														<td class="td-input listdate-show">
															<select id="imageMarkPos" name="imageMarkPos" class="form-select">
																<option value="northwest">
																	左上角
																</option>
																<option value="north">
																	中上
																</option>
																<option value="northeast">
																	右上角
																</option>
																<option value="west">
																	左中
																</option>
																<option value="center">
																	正中
																</option>
																<option value="east">
																	右中
																</option>
																<option value="southwest">
																	左下角
																</option>
																<option value="south">
																	中下
																</option>
																<option value="southeast">
																	右下角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																</option>
															</select>
															&nbsp;&nbsp;&nbsp;&nbsp;
															<span class="input-title listdate-show">位置偏移量:</span>
															<input type="text" style="width:50px;" id="offSetX" name="offSetX" class="form-input" value="${Site.offSetX}"></input>
															&nbsp;px&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="text" style="width:50px;" id="offSetY" name="offSetY" class="form-input" value="${Site.offSetY}"></input>
															&nbsp;px
														</td>
													</tr>




													<tr>
														<td class="input-title listdate-show">
															站点水印图片:
														</td>
														<td class="td-input listdate-show">
															<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																<tr>
																	<td>
																		<a class="cmsSysShowSingleImage" id="imageMarkCmsSysShowSingleImage" href="${Site.imageMark}"><img id="imageMarkCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="${Site.imageMarkReUrl}" />" width="90" height="67" /> </a>
																	</td>
																	<td>
																		<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																			<tr>
																				<td>
																					&nbsp;
																					<input type="button" onclick="javascript:showModuleImageDialog('imageMarkCmsSysImgShow','imageMark','','','0','0')" value="上传" onclick="" class="btn-1" />
																					<input type="button" value="裁剪" onclick="javascript:disposeImage('imageMark','','',false,'-1');" class="btn-1" />
																					<input type="button" value="删除" onclick="javascript:deleteImage('imageMark');" class="btn-1" />
																				</td>
																			</tr>
																			<tr>
																				<td>
																					&nbsp;&nbsp;宽&nbsp;&nbsp;
																					<input id="imageMarkCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Site.imageMarkImageW}" />
																					&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																					<input id="imageMarkCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Site.imageMarkImageH}" />
																				</td>
																			</tr>
																		</table>
																		<input id="imageMark" name="imageMark" type="hidden" value="${Site.imageMarkResId}" />
																		<input id="imageMark_sys_jtopcms_old" name="imageMark_sys_jtopcms_old" type="hidden" value="${Site.imageMarkResId}" />
																	</td>
																</tr>
															</table>

														</td>
													</tr>

												</table>

												<div style="height:25px;"></div>

											</li>
										</ul>

									</div>



									<!-- 第四部分：栏默-->
									<div id="g3_two_5" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-manage">

													<tr>
														<td width="22%" class="input-title listdate-show">
															APP ID(QQ):
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="qqAppId" name="qqAppId" class="form-input" value="${Site.qqAppId}"></input>

														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															APP KEY(QQ):
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="qqAppKey" name="qqAppKey" class="form-input" value="${Site.qqAppKey}"></input>

														</td>
													</tr>

													<%--<tr>
														<td class="input-title listdate-show">
															回调URI(QQ):
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="75" id="qqBackUri" name="qqBackUri" class="form-input" value="${Site.qqBackUri}"></input>
															 
														</td>
													</tr>
													
													--%>
													<tr>
														<td width="25%" class="input-title listdate-show">
															APP ID(weibo):
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="wbAppId" name="wbAppId" class="form-input" value="${Site.wbAppId}"></input>

														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															APP KEY(weibo):
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="wbAppKey" name="wbAppKey" class="form-input" value="${Site.wbAppKey}"></input>

														</td>
													</tr>

													<%--<tr>
														<td class="input-title listdate-show">
															回调URI(weibo):
														</td>
														<td class="td-input listdate-show">
															<input type="text" size="75" id="wbBackUri" name="wbBackUri" class="form-input" value="${Site.wbBackUri}"></input>
															 
														</td>
													</tr>
													
													--%>
													
													
													<tr>
														<td class="input-title listdate-show">
															新会员注册:
														</td>
														<td class="td-input listdate-show">
															<input name="allowMemberReg" type="radio" value="1" />
															开放注册&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="allowMemberReg" type="radio" value="0" />
															停止服务
															<span class="ps">&nbsp;是否允许用户在当前站点注册新的会员</span>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															登录限制:
														</td>
														<td class="td-input listdate-show">
															<input name="memberLoginOnce" type="radio" value="0" />
															任意&nbsp;&nbsp;&nbsp;&nbsp;
															<input name="memberLoginOnce" type="radio" value="1" />
															单一
															
															<span class="ps">&nbsp;默认会员可同时多次登录,单一则只允许登录一次</span>
														</td>
													</tr>
													
													
													<tr>
														<td class="input-title listdate-show">
															会员注册默认组:
														</td>
														<td class="td-input listdate-show">
															<select id="memberDefRoleId" name="memberDefRoleId" class="form-select">
																<option value="-1">
																	------- 请选择一个会员组 ------&nbsp;
																</option>

																<cms:MemberRole>

																	<option value="${Role.roleId}">
																		${Role.roleName}&nbsp;
																	</option>

																</cms:MemberRole>
															</select>

															<span class="ps">会员注册成功，默认所属的会员组</span>
														</td>
													</tr>
													<tr>
														<td class="input-title listdate-show">
															初始积分值:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="memberDefSc" name="memberDefSc" class="form-input" value="${Site.memberDefSc}"></input>
														</td>
														 
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															会话有效时间(分钟):
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="memberExpire" name="memberExpire" class="form-input" value="${Site.memberExpire}"></input>
													
														</td>
													</tr>
													
													
													
													<tr>
														<td class="input-title listdate-show">
															会员扩展信息:
														</td>
														<td class="td-input listdate-show">
															<select id="extMemberModelId" name="extMemberModelId" class="form-select">
																<option value="-1">
																	----- 请选择会员扩展模型 -----&nbsp;
																</option>

																<cms:SystemDataModelList modelType="8">
																	<cms:SystemDataModel>
																		<option value="${DataModel.dataModelId}">
																			${DataModel.modelName}&nbsp;
																		</option>
																	</cms:SystemDataModel>
																</cms:SystemDataModelList>
															</select>

															<span class="ps">在会员注册维护中，可编辑字段值</span>
														</td>
													</tr>
													
													
													<tr>
														<td class="input-title listdate-show">
															邮箱验证邮件模板:
														</td>
														<td class="td-input listdate-show">
														 	<textarea id="regMailText" name="regMailText" style="height:220px;width:470px"  >${Site.regMailText}</textarea>
														 	<input type="hidden" id="regMailText_jtop_sys_hidden_temp_html" name="regMailText_jtop_sys_hidden_temp_html" >
															 
						                                 	
						                                 	<script type="text/javascript">
						                                 	var editor1_regMailText = UE.getEditor('regMailText',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000, toolbars: [[${UE_SMP}]]}); 
															</script>
														
														</td>
													</tr>
 
													<tr>
														<td class="input-title listdate-show">
															邮件验证回调页:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="mailRegBackUri" name="mailRegBackUri" class="form-input" value="${Site.mailRegBackUri}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															密码重置邮件模板:
														</td>
														<td class="td-input listdate-show">
															<textarea id="resetPwText" name="resetPwText" style="height:220px;width:470px"  >${Site.resetPwText}</textarea>
														 	<input type="hidden" id="resetPwText_jtop_sys_hidden_temp_html" name="resetPwText_jtop_sys_hidden_temp_html" >
															 
						                                 	<script type="text/javascript">
						                                 	var editor1_resetPwText = UE.getEditor('resetPwText',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000, toolbars: [[${UE_SMP}]]}); 
															</script>
															
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															会员登录入口页:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="memberLoginUri" name="memberLoginUri" class="form-input" value="${Site.memberLoginUri}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>



													<tr>
														<td class="input-title listdate-show">
															密码重置回调页:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="resetPwBackUri" name="resetPwBackUri" class="form-input" value="${Site.resetPwBackUri}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>

													<tr>
														<td class="input-title listdate-show">
															帐户关联回调页:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="relateMemberUri" name="relateMemberUri" class="form-input" value="${Site.relateMemberUri}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															第三方登陆失败回调页:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="thirdLoginErrorUri" name="thirdLoginErrorUri" class="form-input" value="${Site.thirdLoginErrorUri}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>
													
													<tr>
														<td class="input-title listdate-show">
															第三方登陆成功回调页:
														</td>
														<td class="td-input listdate-show">
															<input type="text" style="width:470px;" id="thirdLoginSuccessUri" name="thirdLoginSuccessUri" class="form-input" value="${Site.thirdLoginSuccessUri}"></input>
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>
													

													

													<%--<tr>
														<td class="input-title listdate-show">
															默认等级:
														</td>
														<td class="td-input listdate-show">
															 
															<select id="memberDefLv" name="memberDefLv" class="form-select">
																<option value="-1">
																	-------- 请选择一个等级 -------&nbsp;
																</option>

																<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberRankInfoForTag" objName="Rank" >

																	<option value="${Rank.rankLevel}">
																		${Rank.rankName}&nbsp;
																	</option>

																</cms:QueryData>
															</select>

															<span class="ps">会员注册成功，默认所拥有的等级</span>
														</td>
													</tr>

													--%>
													


												</table>

												<div style="height:35px;"></div>

											</li>
										</ul>
									</div>

									<!-- 第四部分：栏默-->
									<div id="g3_two_6" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
													<cms:SystemDataModelList mode="manage" modelType="2">
														<cms:SystemDataModel>
															<cms:SystemList querySign="SELECT_SITE_DEF_TEMPLATE_SINGLE_QUERY" var="${Site.siteId},${DataModel.dataModelId}">
																<tr>
																	<td width="22%" class="input-title listdate-show">
																		${DataModel.modelName}列表模板:
																	</td>
																	<td class="td-input listdate-show">
																		<input type="text" size="62" id="${DataModel.dataModelId}-list" name="${DataModel.dataModelId}-list" class="form-input" value="${SysObj.listTemplateUrl}"></input>
																		<select class="form-select" onchange="javascript:selectRule(this,'${DataModel.dataModelId}-list')">
																			<option value="-1">
																				备选参数
																			</option>

																			<option value="{class-id}">
																				栏目ID
																			</option>

																			<option value="{page}">
																				分页数
																			</option>
																		</select>
																		<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('null','${DataModel.dataModelId}-list');" />
																	</td>
																</tr>

																<tr>
																	<td class="input-title listdate-show">
																		${DataModel.modelName}内容模板:
																	</td>
																	<td class="td-input listdate-show">
																		<input type="text" size="62" id="${DataModel.dataModelId}-content" name="${DataModel.dataModelId}-content" class="form-input" value="${SysObj.contentTemplateUrl}"></input>
																		<select class="form-select" onchange="javascript:selectRule(this,'${DataModel.dataModelId}-content')">
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
																		<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('null','${DataModel.dataModelId}-content');" />
																	</td>
																</tr>

																<tr>
																	<td width="21%" class="input-title listdate-show">
																	</td>
																	<td class="td-input listdate-show">
																		<br />
																	</td>
																</tr>
															</cms:SystemList>
														</cms:SystemDataModel>
													</cms:SystemDataModelList>



												</table>

												<div style="height:35px;"></div>

											</li>
										</ul>
									</div>


									<!-- 第五部分-->
									<div id="g3_two_7" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>

												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
													<cms:SystemModelFiledList modelId="${Site.extDataModelId}" showMode="true">
														<cms:SystemModelFiled>
															<tr>
																<td class="input-title listdate-show" width="12%">
																	<cms:SystemModelRowFiled>
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
																							<font color=red><span id="${RowFiled.fieldSign}CmsSysImageGroupCount">-1</span> </font> ]&nbsp;&nbsp;张

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
																									<table border="0" cellpadding="0" cellspacing="0">
																										<tr>
																											<td>
																												<div id="sys-obj-${RowFiled.fieldSign}">
																													${RowFiled.editModeLayoutHtml}
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
																										<table border="0" cellpadding="0" cellspacing="0">
																											<tr>
																												<td>
																													<div id="sys-obj-${RowFiled.fieldSign}">
																														${RowFiled.editModeLayoutHtml}
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


																									当前没有数据!

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

												<div style="height:35px;"></div>
												<div class="breadnavTab">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<td class="input-title" width="30%"></td>
															<td class="td-input" style="height:29px;">
																<a href="javascript:submitEditSiteForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>

																
																<a href="javascript:gotoSortChannelPage();" class="btnwithico"><img src="../style/default/images/sort-number.png" width="16" height="16" /><b>顶级栏目排序&nbsp;</b> </a>

																<a href="javascript:openAddContentClassDialog();" class="btnwithico"><img src="../style/icons/folder-horizontal-open.png" width="16" height="16" /><b>添加顶级栏目&nbsp;</b> </a>
																
																
																<%--
																
																<a href="javascript:openDeleteChannelDialog();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>删除根栏目&nbsp;</b> </a>
															--%>
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
								<input type="hidden" id='siteId' name='siteId' value='${Site.siteId}' />
								<input type="hidden" id='siteFlag' name='siteFlag' value='${Site.siteFlag}' />

								<input type="hidden" id='siteUrlOld' name='siteUrlOld' value='${Site.siteUrl}' />

								<!-- 此classId由上传模块使用 -->
								<input type="hidden" id='classId' name='classId' value='-9999' />
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

initRadio('pcVm','${Site.pcVm}');
initRadio('mobVm','${Site.mobVm}');
initRadio('padVm','${Site.padVm}');


initSelect('staticFileType','${Site.staticFileType}');
initRadio('homePageProduceType','${Site.homePageProduceType}');
initRadio('useState','${Site.useState}');
initRadio('mobJump','${Site.mobJump}');
initSelect('mobSiteId','${Site.mobSiteId}');
initRadio('shareMode','${Site.shareMode}');
initRadio('downOutImage','${Site.downOutImage}');
initRadio('deleteOutLink','${Site.deleteOutLink}');
initRadio('siteCollType','${Site.siteCollType}');
initRadio('genKw','${Site.genKw}');
initRadio('sameTitle','${Site.sameTitle}');
initRadio('mailSSL','${Site.mailSSL}');
initRadio('searchFun','${Site.searchFun}');
initRadio('useFW','${Site.useFW}');
initRadio('useImageMark','${Site.useImageMark}');
initRadio('imageMarkType','${Site.imageMarkType}');
initSelect('imageMarkPos','${Site.imageMarkPos}');
initSelect('defEditorImageDM','${Site.defEditorImageDM}');
initSelect('defHomeImageDM','${Site.defHomeImageDM}');
initSelect('defClassImageDM','${Site.defClassImageDM}');
initSelect('defListImageDM','${Site.defListImageDM}');
initSelect('defContentImageDM','${Site.defContentImageDM}');
initSelect('extDataModelId','${Site.extDataModelId}');
initSelect('extMemberModelId','${Site.extMemberModelId}');
initRadio('allowMemberReg','${Site.allowMemberReg}');
initSelect('memberDefRoleId','${Site.memberDefRoleId}');
//initSelect('memberDefLv','${Site.memberDefLv}');

initRadio('memberLoginOnce','${Site.memberLoginOnce}');


changeMobJump();

//图片查看效果
loadImageShow();

function setTab(flag,pos,size)
{
		hideTips('siteName');
		hideTips('siteUrl');
		setTab2(flag,pos,size);
	
}

var deleteSucc = false;

function submitEditSiteForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('siteName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
        if(currError)
        {
        	hasError = true;
        }
        
  
        
    currError = submitValidate('siteUrl',0,ref_url,'格式为合法的URL地址');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	setTab2('two',1,6);
    	submitValidate('siteName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
    	submitValidate('siteUrl',0,ref_url,'格式为合法的URL地址');
    
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	
	//后台验证
	

		
		var siteUrl = $('#siteUrl').val();
		
		if( !$('#siteUrl').val().endWith( '/' ) )
        {
              siteUrl = $('#siteUrl').val() + '/';
        }

        if(siteUrl != $('#siteUrlOld').val())
        {	
			var count = validateExistFlag('sysSiteUrl', siteUrl);
			
			if(count > 0)
			{ 
				setTab2('two',1,6);
				hasError = true;
				showTips('siteUrl', '系统已存在此值，不可重复录入');
	
			}
			
			if(hasError)
		    {		    	
			    return;
			}
		}
		
		if($('#pcVm').attr('checked') != 'checked'
			&& $('#mobVm').attr('checked') != 'checked'
			&& $('#padVm').attr('checked') != 'checked')
		{
					
			
		}
	 
	   $.dialog.tips('正在执行...',3600000000,'loading.gif');
	  
	   encodeFormEditor('editSiteForm', false);
		
		
		url = "<cms:BasePath/>site/editSite.do"+"?<cms:Token mode='param'/>";
	    var postData = encodeURI($("#editSiteForm").serialize());
	   
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
								    				width: '160px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/succ.png', 
								    				
								                    content: "改动站点配置成功！",
						
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
		        }
		        
		});

	 
}




function gotoSortChannelPage()
{
	$.dialog({ 
		id:'gscp',
    	title :'排序 - 站点顶级栏目',
    	width: '490px', 
    	height: '580px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,
        
        close: function() 
        { 
        	window.parent.frames["treeFrame"].location.reload();
        } ,
             
        content: 'url:<cms:Domain/>core/channel/ShowSortChannelSiteMode.jsp?classId=-9999'
	});
}



function openAddContentClassDialog()
{
    var classId = '-9999';
    var className = '顶级根栏目';
	$.dialog({ 
	    id : 'oacd',
    	title : '增加顶级栏目(可批量添加)',
    	width: '645px', 
    	height: '570px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
              
        content: 'url:<cms:Domain/>core/channel/AddContentClassSiteMode.jsp?classId='+classId+'&className='+encodeURIComponent(encodeURIComponent(className))
	});
}

function openDeleteChannelDialog()
{

	$.dialog({ 
	    id : 'occtcd',
    	title : '删除站点根栏目 ',
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
       
        content: 'url:<cms:BasePath/>core/channel/ShowDeleteChannel.jsp?uid='+Math.random()+'&classId=-9999&linear='
	});


}

function openDeleteChannelDialog()
{

	$.dialog({ 
	    id : 'occtcd',
    	title : '删除顶级栏目',
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
       
        content: 'url:<cms:BasePath/>core/channel/ShowDeleteChannel.jsp?uid='+Math.random()+'&classId=-9999&linea'
	});


}

function changeMobJump()
{
 	var mj = $("input[name='mobJump']:checked").val();


	if('1' == mj)
	{
		$("#jumpMobTr").show();
	}
	else
	{
		$("#jumpMobTr").hide();
	}
	

}




</script>
</cms:Site>
</cms:CurrentSite>
