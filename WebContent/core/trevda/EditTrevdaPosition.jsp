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
		var hasError = false;
		
		basePath = '<cms:BasePath/>';
		
		var dialogApiId = '${param.dialogApiId}';

	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
			
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
         	
         		W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '190px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '编辑广告版位成功!',
	
			    				ok: function()
			    				{
			    					W.location.reload();
			    				}
			   });
	             
	             
         	}
       		       
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('posName',0,ref_name,'格式为文字,数字,下划线');
 			validate('posFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
        	
      </script>
	</head>
	<body>
		<cms:SystemAdvertPos posId="${param.posId}">
		
			<form id="advertPosForm" name="advertPosForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab('two',1,2)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/ui-scroll-pane-image.png" width="16" height="16" />广告版位信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab('two',2,2)">
										<a href="javascript:;"><img src="../style/icons/table.png" width="16" height="16" />版位配置参数&nbsp;</a>
									</li>
								</ul>
							</div>

							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="28%" class="input-title">
														<strong>广告类型</strong>
													</td>
													<td class="td-input">
														<select disabled id="configId" name="configId" class="form-select" style="width:258px">
															<option value="-1">
																----------- 请选择广告类型 -----------
															</option>
															<cms:SystemAdvertConfig configId="all">
																<option value="${Config.configId}">
																	${Config.configName}
																</option>
															</cms:SystemAdvertConfig>
														</select>
													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>版位名称</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:258px" id="posName" name="posName" class="form-input" value="${Pos.posName}"></input>

													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>引用代码</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:258px" id="posFlag" name="posFlag" class="form-input" value="${Pos.posFlag}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>版位大小</strong>
													</td>
													<td class="td-input">
														宽:
														<input type="text" style="width:102px" id="width" name="width" class="form-input" value="${Pos.width}"></input>
														&nbsp;高:
														<input type="text" style="width:102px" id="height" name="height" class="form-input" value="${Pos.height}"></input>
													</td>
												</tr>


												<tr id="mustFillDIV">
													<td class="input-title">
														<strong>投放策略 
													</td>
													<td class="td-input">
														<input name="showMode" type="radio" value="1" checked />
														<span class="STYLE12">随机排序</span> &nbsp;
														<input name="showMode" type="radio" value="2" />
														<span class="STYLE12">权重优先</span> &nbsp;
														<input name="showMode" type="radio" value="3" />
														<span class="STYLE12">按关键字</span>

													</td>
												</tr>

												<tr id="dataTypeDIV">
													<td class="input-title">
														<strong>版位描叙</strong>
													</td>
													<td class="td-input">
														<textarea id="posDesc" name="posDesc" style="height:50px;width:258px" class="form-textarea">${Pos.posDesc}</textarea>
													</td>
												</tr>

												<tr id="mustFillDIV">
													<td class="input-title">
														<strong>页面打开方式 
													</td>
													<td class="td-input">
														<input name="target" type="radio" value="1" checked />
														<span class="STYLE12">新的页面</span> &nbsp;
														<input name="target" type="radio" value="2" />
														<span class="STYLE12">当前页面</span>
													</td>
												</tr>



												<%--<tr id="mustFillDIV">
													<td class="input-title">
														<strong>启用状态 
													</td>
													<td class="td-input">
														<input name="useState" type="radio" value="1" checked />
														启用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input name="useState" type="radio" value="0" />
														停用
													</td>
												</tr>

												--%><!-- 以下为独立选项 start -->

											</table>

											 
										</li>
									</ul>
								</div>

								<!-- 第二部分:参数 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
									
									<ul>
										<li>

											<cms:SystemAdvertConfig configId="${Pos.configId}">
											
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" >
												<cms:SystemModelFiledList modelId="${Config.posModelId}" showMode="true">
													<cms:SystemModelFiled>
														<tr>
															<td class="input-title" width="20%">
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
															<td class="input-title" width="20%">
																<!-- 图集内容空标题 -->
															</td>
															<td class="td-input" style="padding-top:0px;">
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
																				<td class="td-input">
																				
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
																									<span class="input-title">${RowFiled.showName}:</span>&nbsp;
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
				
																							
																								<center>当前没有数据!</center>
																							
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
												
											</cms:SystemAdvertConfig>

											
											
											<div style="height:26px;"></div>
											<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a id="buttonHref"  href="javascript:submitAdvertPosForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16" /><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
											</div>
										</li>
									</ul>
								</div>

							</div>

						</td>
					</tr>
				</table>

				<!-- hidden -->
				<input type="hidden" name="posId" id="posId" value="${param.posId}" />

				<input type="hidden" name="configId" id="configId" value="${Pos.configId}" />
				
				<input type="hidden" name="useState" id="useState" value="1" />
				
				<cms:Token mode="html"/>

			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">
initSelect('configId','${Pos.configId}');
//switchAdvertConfig('${Pos.configId}','${Pos.posId}');
initRadio('showMode','${Pos.showMode}');
initRadio('target','${Pos.target}');

//图片查看效果
loadImageShow();

function setTab(flag,pos,size)
{
		hideTips('posFlag');
		setTab2(flag,pos,size);
	
}

  
function close()
{
	api.close();
}



 


function submitAdvertPosForm()
{    
	if($('#configId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个广告类型!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('posFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('posName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	
	//后台验证
	
	if('${Pos.posFlag}' != $('#posFlag').val())
	{
		var count = validateExistFlag('advertPos', $('#posFlag').val());
		
		if(count > 0)
		{
			setTab('two',1,2);
			showTips('posFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
	}
	
    encodeFormInput('advertPosForm', false);
    
    var form = document.getElementById('advertPosForm');
	
    form.action="<cms:BasePath/>trevda/editTrevdaPos.do";
    
   	form.submit(); 
    
}

function switchAdvertConfig(configId, posId)
{
	window.location.href = '<cms:BasePath/>core/trevda/EditTrevdaPosition.jsp?posId='+posId+'&configId='+configId+'&uid='+Math.random();
}


</script>
</cms:SystemAdvertPos>
