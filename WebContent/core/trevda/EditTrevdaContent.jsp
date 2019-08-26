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
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

		<script>
		
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
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '编辑广告成功!',
	
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
		    validate('adName',0,ref_name,'格式为文字,数字,下划线');
 			validate('adFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
         
        	
      </script>
	</head>
	<body>

		<cms:SystemAdvertContent advertId="${param.advertId}">
			<form id="advertForm" name="advertForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab('two',1,2)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/megaphone.png" width="16" height="16" />广告信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab('two',2,2)">
										<a href="javascript:;"><img src="../style/icons/table.png" width="16" height="16" />配置参数&nbsp;</a>
									</li>

								</ul>
							</div>

							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td class="input-title">
														<strong>所属版位</strong>
													</td>
													<td class="td-input">
														<select disabled id="posId" name="posId" class="form-select" style="width:365px">
															<option value="-1">
															---------------------- 请选择版位 -----------------------
														</option>
															<cms:SystemAdvertPos posId="all">
																<option value="${Pos.posId}">
																	${Pos.posName}&nbsp;&nbsp;&nbsp;
																</option>
															</cms:SystemAdvertPos>
														</select>
														&nbsp;
														<cms:SystemAdvertConfig posId="${Advert.posId}">
															<cms:if test="${!empty Config.configName}">
														   	[ ${Config.configName} ]
														   
														   </cms:if>
															<cms:else>
	
															</cms:else>
	
														</cms:SystemAdvertConfig>
													</td>
												</tr>
												<tr>
													<td width="24%" class="input-title">
														<strong>广告名称</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:365px" id="adName" name="adName" class="form-input" value="${Advert.adName}"></input>
													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>引用代码</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:365px" id="adFlag" name="adFlag" class="form-input" value="${Advert.adFlag}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>连接关键词</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:365px" id="keyword" name="keyword" class="form-input" value="${Advert.keyword}"></input>
													</td>
												</tr>


												<tr>
													<td class="input-title">
														<strong>投放周期</strong>
													</td>
													<td class="td-input">

														<input id="showStartDate" name="showStartDate" value="<cms:FormatDate date="${Advert.showStartDate}" format="yyyy-MM-dd" />" style="width:163px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" />
														&nbsp;至 &nbsp;
														<input id="showEndDate" name="showEndDate" value="<cms:FormatDate date="${Advert.showEndDate}" checkDate="9999" replace="" format="yyyy-MM-dd" />" style="width:163px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" />

													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>权重值</strong>
													</td>
													<td class="td-input">
														<input type="text" size="25" id="importance" name="importance" class="form-input" value="${Advert.importance}"></input>
														<span class="ps">权重值大的广告，出现机率就会高</span>
													</td>
												</tr>


												<tr id="mustFillDIV">
													<td class="input-title">
														<strong>页面打开方式 
													</td>
													<td class="td-input">
														<input name="target" type="radio" value="1" checked />
														<span class="STYLE12">新页面</span>&nbsp;
														<input name="target" type="radio" value="2" />
														<span class="STYLE12">当前页</span>
													</td>
												</tr>



												<tr id="mustFillDIV">
													<td class="input-title">
														<strong>启用状态 
													</td>
													<td class="td-input">
														<input name="useState" type="radio" value="1" />
														<span class="STYLE12">启用</span> &nbsp;
														<input name="useState" type="radio" value="0" />
														<span class="STYLE12">停用</span>
													</td>
												</tr>

												<!-- 以下为独立选项 start -->


											</table>

											<div style="height:26px;"></div>
											<div class="breadnavTab" >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="buttonHref"  href="javascript:submitAdvertForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16" /><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>
										</li>
									</ul>
								</div>

								<!-- 第二部分:步骤动作 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
							
									<ul>
										<li>
											
												<cms:SystemAdvertConfig posId="${Advert.posId}">

													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" >
												<cms:SystemModelFiledList modelId="${Config.contentModelId}" showMode="true">
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
																			<strong>${RowFiled.showName}</strong>									
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
											<div class="breadnavTab" >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="buttonHref"  href="javascript:submitAdvertForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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
				<input type="hidden" name="advertId" id="advertId" value="${param.advertId}" />

				<input type="hidden" name="posId" id="posId" value="${Advert.posId}" />
				
				<cms:Token mode="html"/>

			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('posId','${Advert.posId}');

initRadio('target','${Advert.target}');

initRadio('useState','${Advert.useState}');

//图片查看效果
loadImageShow();

function setTab(flag,pos,size)
{
		hideTips('adName');
		hideTips('adFlag');
		setTab2(flag,pos,size);
	
}

  
function close()
{
	api.close();
}


function submitAdvertForm()
{    
	if($('#posId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个广告版位!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('adFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('adName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	setTab('two',1,2);
    	submitValidate('adFlag',0,ref_flag,'格式为字母,数字,下划线');
    	submitValidate('adName',0,ref_name,'格式为文字,数字,下划线');
    
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;
	}
	
	
	//后台验证
	
	if('${Advert.adFlag}' != $('#adFlag').val())
	{
		var count = validateExistFlag('advertContent', $('#adFlag').val());
		
		if(count > 0)
		{
			setTab('two',1,2);
			showTips('adFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
	}
	
	encodeFormInput('advertForm', false);
   
    var form = document.getElementById('advertForm');
	
    form.action="<cms:BasePath/>trevda/editTrevda.do";
    
    form.submit(); 
    
}

function switchAdvertPos(posId)
{
	window.location.href='<cms:BasePath/>core/trevda/EditTrevdaContent.jsp?dialogApiId=ocad&posId='+posId+'&uid='+Math.random();
}


</script>
</cms:SystemAdvertContent>
