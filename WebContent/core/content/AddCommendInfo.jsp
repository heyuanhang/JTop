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
		 basePath = '<cms:BasePath/>';
		 
		 var api = frameElement.api, W = api.opener; 
		 
		 var dialogApiId = '${param.dialogApiId}';
		
			
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            	W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '添加推荐内容成功!',
	
			    				ok: function()
			    				{
			    					W.window.location.reload();       
			    				}
			   });     
         }
    
    	 $(function()
		 {
		    validate('title',1,null,null);
 	
 			
 			//图片查看效果
 		  	loadImageShow();	
 				
		 })
    
      	</script>
	</head>
	<body>

		<cms:SystemCommendType typeId="${param.typeId}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					
						<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab('two',1,2)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/document-text-image.png" width="16" height="16" />内容信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab('two',2,2)">
										<a href="javascript:;"><img src="../style/icons/table.png" width="16" height="16" />扩展数据&nbsp;</a>
									</li>

								</ul>
							</div>
						
						
							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<form id="commForm" name="commForm" method="post">
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
													<tr>
														<td width="23%" class="input-title">
															<strong>标题: 
														</td>
														<td class="td-input">
															<input id="title" name="title" type="text" maxLength="80" style="width: 405px;" class="form-input-title titlerule" />
															<input type="button" value="选取内容" onclick="javascript:openSelectSiteAndPushContentDialog();" class="btn-1" />
															<span class="red">*</span><span class="ps"></span>
														</td>
													</tr>
					
													<tr>
														<td class="input-title">
															<strong>URL链接: 
														</td>
														<td class="td-input">
															<input id="url" name="url" type="text" class="form-input" maxlength="600" style="width: 405px;"  />
														</td>
													</tr>
					
													<tr>
														<td class="input-title">
															<strong>引导图: 
														</td>
														<td class="td-input">
															<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																<tr>
																	<td>
																		<a class="cmsSysShowSingleImage" id="imgCmsSysShowSingleImage" href="<cms:SystemResizeImgUrl reUrl="" />"><img id="imgCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="" />" width="90" height="67" /> </a>
																	</td>
																	<td>
																		<table border="0" cellpadding="0" cellspacing="0" height="65" class="form-table-big">
																			<tr>
																				<td>
																					&nbsp;
																					<input type="button" onclick="javascript:showModuleImageDialog('imgCmsSysImgShow','img','${CommendType.imageWidth}','${CommendType.imageHeight}','0',false)" value="上传" onclick="" class="btn-1" />
																					<input type="button" value="裁剪" onclick="javascript:disposeImage('img','','',false,'-1');" class="btn-1" />
																					<input type="button" value="删除" onclick="javascript:deleteImage('img');" class="btn-1" />
																				</td>
																			</tr>
																			<tr>
																				<td>
																					&nbsp;&nbsp;宽&nbsp;&nbsp;
																					<input id="imgCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" />
																					&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																					<input id="imgCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" />
																				</td>
																			</tr>
																		</table>
																		<input id="img" name="img" type="hidden" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
					
													<tr>
														<td class="input-title">
															<strong>简介:</strong>
														</td>
														<td class="td-input">
															<textarea id="summary" name="summary" class="form-textarea" style="width:400px; height:70px;"></textarea>
													</tr>
					
													<tr>
														<td class="input-title">
															<strong>行列位置: 
														</td>
														<td class="td-input">
															添加为
															<select id="infoColumn" name="infoColumn" class="form-select" onchange="javascript:switchRowOption(this)">
					
																<cms:SysCommendContent flag="${CommendType.commFlag}" >
																	<option value="${CommInfo.rowFlag}">
																		第 ${status.count} 行
																	</option>
																</cms:SysCommendContent>
																<cms:Empty flag="CommInfo">
																	<option value="1">
																		第 1 行
																	</option>										
																</cms:Empty>
																<cms:EmptyNot flag="CommInfo">
																	<option value="${status.count+1}">
																		第 ${status.count+1} 行
																	</option>										
																</cms:EmptyNot>
					
															</select>
															&nbsp;&nbsp;
															<input type="checkbox" class="form-checkbox" id="inCol" name="inCol" value="true" onclick="javascript:enableRowSelect(this);" />
															之中 &nbsp;&nbsp;
															<select id="infoRow" name="infoRow" class="form-select" disabled>
																<option value="1">
																	第 1 列
																</option>
															</select>
														</td>
					
													</tr>
					
					
												</table>
												<!-- hidden -->
					
												<input type="hidden" id="classId" name="classId" value="${param.classId}" />
												<input type="hidden" id="rootClassId" name="rootClassId" value="${param.classId}" />
												
												<input type="hidden" id="classChildMode" name="classChildMode" value="true" />
												<input type="hidden" id="typeId" name="typeId" value="${param.typeId}" />
												<input type="hidden" id="contentId" name="contentId" value="-1" />
												<input type="hidden" id="modelId" name="modelId" value="-1" />
					
												<input type="hidden" id="commendTypeId" name="commendTypeId" value="${CommendType.commendTypeId}" />
												<input type="hidden" id="commendFlag" name="commendFlag" value="${CommendType.commFlag}" />
												
												<input type="hidden" id="flag" name="flag" value="" />
												
												<cms:Token mode="html"/>
					
											
											<div style="height:16px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellspacing="0" cellpadding="0" >
													<tr class="btnbg100">
														<div style="float:right">
															<a href="javascript:submitCommForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</div>
													 
													</tr>
												</table>
											</div>
										
										</li>
										
									</ul>
						 		</div>
						 		
						 		<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
							
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" style="padding-top: 8px;">
												<cms:SystemModelFiledList modelId="${CommendType.modelId}" showMode="true">
													<cms:SystemModelFiled>
														<tr>
															<td class="input-title" width="14%">
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
															<td class="input-title" width="14%">
																<!-- 图集内容空标题 -->
															</td>
															<td class="td-input " style="padding-top:0px;">
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
											
											<div style="height:16px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellspacing="0" cellpadding="0" >
													<tr class="btnbg100">
														<div style="float:right">
															<a href="javascript:submitCommForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</div>
													 
													</tr>
												</table>
											</div>
											
											</form>
											
										</li>
									</ul>
								</div>
							</div>
					

						


					</td>
				</tr>


				</tr>
			</table>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
 
changeRowPos(1);

function setTab(flag,pos,size)
{
		 	setTab2(flag,pos,size);
	
}
 
 
function close()
{
	api.close();
}


function openSelectSiteAndPushContentDialog()
{
	W.$.dialog({ 
	    id : 'aospcd',
    	title : '[ ${CommendType.commendName} ] - 选取站点内容',
    	width: '1050px', 
    	height: '760px', 
    	parent:api, 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/dialog/SelectSiteAndPushContent.jsp?&single=true&dialogId=ospcd&uid='+Math.random()+'&typeId=${param.typeId}'+'&classId=${param.classId}'
	});
}

function switchRowOption(opt)
{	
     if('newCol' == opt.value)
     {
     	$("#infoRow").empty();
     	$("#infoRow").append('<option value="1">第 1 列 </option>');	  
     	return;
     }
     
     changeRowPos(opt.value);
     
}

function changeRowPos(rowFlag)
{
	 var url = "<cms:BasePath/>content/getCommRowBeanJson.do?rowFlag="+rowFlag+"&commFlag=${CommendType.commFlag}&random="+Math.random();
  	 $.getJSON
	(
		url, 
  		{},
  		function(data)
  		{
      	  $("#infoRow").empty();
      	 
      	  var index = 0;
  		  $.each
  		  (
  				data,
  				function(i,item)
  				{
  					 
  					$("#infoRow").append('<option value="'+i+'">第 '+i+' 列 </option>');	   
  					index = i;   					      					
  				}	      			
  			);
  			
  			index ++;
  			$("#infoRow").append('<option value="-1">第 '+index+' 列 </option>');
  		}
  	 );



}

function enableRowSelect(box)
{
	if(box.checked)
	{
		document.getElementById('infoRow').disabled  = false;
	}
	else
	{
		document.getElementById('infoRow').disabled  = true;
		var infoRowBoxOpts = document.getElementById('infoRow').options;
		infoRowBoxOpts[0].selected = true;
		//alert(infoRowBoxOpts[0].value);
	}
}

function submitCommForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('title',1,null,null);
        
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
	
	encodeFormInput('commForm', false);
	
   var commForm = document.getElementById('commForm');
   commForm.action="../../content/addSingleCommendInfo.do";
   commForm.submit();
}

   
  
</script>
</cms:SystemCommendType>
