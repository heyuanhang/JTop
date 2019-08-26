<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />


		<script>  
			var api = frameElement.api, W = api.opener; 
			
			basePath='<cms:BasePath/>';
			
			var dialogApiId = '${param.dialogApiId}';
		
		  	$(function()
			{

				 //验证规则注册
				  <cms:SystemModelFiledList modelId="${param.modelId}">
						<cms:SystemModelFiled>		
					    	    	
							<cms:FieldValidateConfig id="${ModelFiled.defaultValidate}">	
							
							validate('${ModelFiled.fieldSign}','${ModelFiled.isMustFill}','${Valid.regulation}','${Valid.errorMessage}');	
								</cms:FieldValidateConfig>
						 
	                   </cms:SystemModelFiled>   
				</cms:SystemModelFiledList>
	
				//图片查看效果
 		  		loadImageShow();	
			})
         
         
        
      </script>
	</head>
	<body>
					<form id="formDataForm" name="formDataForm" method="post">
								<cms:SystemContent formMode="true" id="${param.contentId}">

													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" >
													<cms:SystemModelFiledList modelId="${param.modelId}" showMode="true">
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
															<div style="height:26px;"></div>
															</table>
															
															<!-- hidden -->
															<input id="contentId" name="contentId" type="hidden" value="${param.contentId}" />
															
															<input id="modelId" name="modelId" type="hidden" value="${param.modelId}" />

											 	
										</form>

											<div style="height:26px;"></div>
											<div class="breadnavTab" >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<cms:Model id="${param.modelId}">
															
															<cms:if test="${Model.isManageEdit != 1}">
																<a id="buttonHref"  name="not_use_href"   href="javascript:;" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>编辑数据&nbsp;</b> </a>
																<script>
																		disableAnchorElementByName("not_use_href", true);
																</script>
															</cms:if>
															<cms:else>
																<a id="buttonHref"  href="javascript:submitEditFormData();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>编辑数据&nbsp;</b> </a>
															
															</cms:else>
															
															</cms:Model>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16" /><b>关闭&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>		  
	</body>
</html>
<script>  


function submitEditFormData()
{
	 var hasError = false;
    var currError = false;
	<cms:SystemModelFiledList modelId="${param.modelId}">
			<cms:SystemModelFiled>		
		    
				<cms:FieldValidateConfig id="${ModelFiled.defaultValidate}">		
							
				currError = submitValidate('${ModelFiled.fieldSign}','${ModelFiled.isMustFill}','${Valid.regulation}','${Valid.errorMessage}');					
				
				if(currError)
				{
					hasError = true;
				}	
				
				</cms:FieldValidateConfig>
		 
           </cms:SystemModelFiled>   
	</cms:SystemModelFiledList>	
	

  
    if(currError)
	{
		hasError = true;
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
		
		return;
	}
	
		url = "<cms:BasePath/>content/editFormData.do"+"?<cms:Token mode='param'/>";
	    var postData = encodeURI($("#formDataForm").serialize());
	    
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
           		
		        	if('success' == msg)
		        	{
		        		W.$.dialog({ 
					   					title :'提示',
					    				width: '160px', 
					    				height: '70px', 
					                    lock: true, 
					    				icon: '32X32/succ.png', 
					    				parent:api,
					    				
					                    content: '改动表单内容成功!',
					                    
					                   
									    ok: function()
									    {
									    	window.location.reload();
									    }
					                    
			
						});
		        		
		        	}
		        }
		        
		        });

 

}

function close()
{
 	var api = frameElement.api, W = api.opener; 
     api.close();
     W.window.location.reload();
}



  
</script>
</cms:SystemContent>	
