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

		<script> 
		 basePath = '<cms:BasePath/>';	
			 
		 var api = frameElement.api, W = api.opener; 
		
         var dialogApiId = 'oeqd';
         
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );    
       		W.window.location.reload();       
         }
         
         var textCount = 0;
		 var imageCount = 0;
		 
		 if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '编辑调查项成功!',
	
			    				ok: function()
			    				{
			    					W.location.reload();
			    				}
			  });  
         }
         
        var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('surveyTitle',1,null,null);
 				
		 })
    
      	</script>
	</head>
	<body>

		<cms:Survey surveyId="${param.sid}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/script-stamp.png" width="16" height="16" />配置
						</div>

					 
						<form id="surveyForm" name="surveyForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td class="input-title">
										<strong> 选项类型: </strong>
									</td>
									<td class="td-input">
										<input name="optionType" type="radio" class="form-radio" value="1" checked onclick="javascript:changeEleOpt(this.value)" />
										单选文字&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="optionType" type="radio" class="form-radio" value="2" onclick="javascript:changeEleOpt(this.value)" />
										多选文字&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="optionType" type="radio" class="form-radio" value="3" onclick="javascript:changeEleOpt(this.value)" />
										单选图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="optionType" type="radio" class="form-radio" value="4" onclick="javascript:changeEleOpt(this.value)" />
										多选图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="optionType" type="radio" class="form-radio" value="5" onclick="javascript:changeEleOpt(this.value)" />
										输入文本&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%--<input id="addInfo" name="addInfo" type="checkbox" class="form-checkbox" value="1" onclick="javascript:changeAddInfo()" />
										附加信息
									--%></td>
								</tr>


								<tr>
									<td width="17%" class="input-title">
										<strong> 调查标题: </strong>
									</td>
									<td class="td-input">
										&nbsp;&nbsp;&nbsp;&nbsp;<input id="surveyTitle" name="surveyTitle" type="text" class="form-input" style="width:475px;" value="${Survey.surveyTitle}" />
										<span class="red"> * </span>
										<span class="ps"></span>
									</td>
								</tr>

								<tr id="add-info-tr" style="display:none">
									<td class="input-title">
										<strong> 附加标题: </strong>
									</td>
									<td class="td-input">
										<input id="addiTitle" name="addiTitle" type="text" class="form-input" size="35" value="${Survey.addiTitle}" />
										<span class="ps"> 在选项最后给出输入框,用户可输入任意的附加信息 </span>
									</td>
								</tr>

								<%--<tr id="limit-tr" style="display:none">
								<td class="input-title">
									<strong>限制:</strong>
								</td>
								<td class="td-input">
									必选&nbsp;<input id="surveyTitle" name="surveyTitle" type="text" class="form-input" size="6" value="0"/>&nbsp;项
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最多&nbsp;<input id="surveyTitle" name="surveyTitle" type="text" class="form-input" size="6" value="0"/>&nbsp;项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ps">若值为0则允许用户任意选择</span>
								</td>
							</tr>

							--%>
								<tr id="opt-tr">
									<td class="input-title">
										<strong> 选项: </strong>
									</td>
									<td class="td-input">

										<table id="text-opt" border="0" cellspacing="0" cellpadding="0" width="90%" class="form-table">


											<cms:if test="${Survey.optionType==1 || Survey.optionType==2}">

												<cms:SurveyOption >
													<script>
												textCount = '${status.count}';
												</script>

													<tr id="text-opt-tr-${status.count}">
														<td>
															${status.count}.&nbsp;
															<input id="text-opt-ele-input-${status.count}" name="text-opt-ele-input-${status.count}" type="text" class="form-input" style="width:400px" value="${Option.optionText}" />
															&nbsp;票数:
															<input id="text-opt-ele-vote-${status.count}" name="text-opt-ele-vote-${status.count}" type="text" class="form-input" size="4" value="${Option.vote}" />
															&nbsp;
															<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(${status.count});" />
															&nbsp;
															<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(${status.count});" />
														</td>
													</tr>
												</cms:SurveyOption>

											</cms:if>
											<cms:else>
												<script>
												textCount = 9;
											</script>
												<tr id="text-opt-tr-1">
													<td>
														1.&nbsp;
														<input id="text-opt-ele-input-1" name="text-opt-ele-input-1" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-1" name="text-opt-ele-vote-1" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(1);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(1);" />
													</td>
												</tr>

												<tr id="text-opt-tr-2">
													<td>
														2.&nbsp;
														<input id="text-opt-ele-input-2" name="text-opt-ele-input-2" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-2" name="text-opt-ele-vote-2" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(2);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(2);" />
													</td>
												</tr>



												<tr id="text-opt-tr-3">
													<td>
														3.&nbsp;
														<input id="text-opt-ele-input-3" name="text-opt-ele-input-3" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-3" name="text-opt-ele-vote-3" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(3);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(3);" />
													</td>
												</tr>

												<tr id="text-opt-tr-4">
													<td>
														4.&nbsp;
														<input id="text-opt-ele-input-4" name="text-opt-ele-input-4" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-4" name="text-opt-ele-vote-4" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(4);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(4);" />
													</td>
												</tr>

												<tr id="text-opt-tr-5">
													<td>
														5.&nbsp;
														<input id="text-opt-ele-input-5" name="text-opt-ele-input-5" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-5" name="text-opt-ele-vote-5" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(5);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(5);" />
													</td>
												</tr>

												<tr id="text-opt-tr-6">
													<td>
														6.&nbsp;
														<input id="text-opt-ele-input-6" name="text-opt-ele-input-6" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-6" name="text-opt-ele-vote-6" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(6);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(6);" />
													</td>
												</tr>

												<tr id="text-opt-tr-7">
													<td>
														7.&nbsp;
														<input id="text-opt-ele-input-7" name="text-opt-ele-input-7" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-7" name="text-opt-ele-vote-7" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(7);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(7);" />
													</td>
												</tr>

												<tr id="text-opt-tr-8">
													<td>
														8.&nbsp;
														<input id="text-opt-ele-input-8" name="text-opt-ele-input-8" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-8" name="text-opt-ele-vote-8" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(8);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(8);" />
													</td>
												</tr>

												<tr id="text-opt-tr-9">
													<td>
														9.&nbsp;
														<input id="text-opt-ele-input-9" name="text-opt-ele-input-9" type="text" class="form-input" style="width:400px" />
														&nbsp;票数:
														<input id="text-opt-ele-vote-9" name="text-opt-ele-vote-9" type="text" class="form-input" size="4" />
														&nbsp;
														<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement(9);" />
														&nbsp;
														<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement(9);" />
													</td>
												</tr>


											</cms:else>



										</table>

										<!-- image 选项 -->
										<table id="image-opt" border="0" cellspacing="0" cellpadding="0" width="90%" style="display:none">

											<cms:if test="${Survey.optionType==3 || Survey.optionType==4}">

												<cms:SurveyOption >
													<script>
												imageCount = '${status.count}';
												</script>

													<tr id="image-opt-tr-${status.count}">
														<td valign="top">
															${status.count}.&nbsp;
															<a href="javascript:disposeImage('image-opt-${status.count}-','','',false,'-1');"> <img id="image-opt-${status.count}-CmsSysImgShow" src="${Option.optionImage}" width="90" height="67" /> </a>
															<input type="button" value="上传..." onclick="javascript:showModuleImageDialogForDialog('image-opt-${status.count}-CmsSysImgShow','image-opt-${status.count}-','','','1','0','oeqd');" class="btn-1" />
															&nbsp;票数:
															<input id="image-opt-ele-vote-${status.count}" name="image-opt-ele-vote-${status.count}" type="text" class="form-input" size="4" value="${Option.vote}" />
															&nbsp;信息:
															<input id="image-opt-ele-target-${status.count}" name="image-opt-ele-target-${status.count}" type="text" class="form-input" style="width:200px" value="${Option.target}" />
															<img class="cursor" src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addImageNewElement(${status.count});" />
															&nbsp;
															<img class="cursor" src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeImageElement(${status.count});" />
															<input type="hidden" id="image-opt-${status.count}-" name="image-opt-${status.count}-" value="${Option.optionImageResId}" />
															<input type="hidden" id="image-opt-${status.count}-_sys_jtopcms_old" name="image-opt-${status.count}-_sys_jtopcms_old" value="${Option.optionImageResId}" />
															
															<input type="hidden" id="image-opt-${status.count}-CmsSysImgWidth" name="image-opt-${status.count}-CmsSysImgWidth" />
															<input type="hidden" id="image-opt-${status.count}-CmsSysImgHeight" name="image-opt-${status.count}-CmsSysImgHeight" />
														</td>
													</tr>
												</cms:SurveyOption>

											</cms:if>
											<cms:else>
												<script>
												imageCount = 4;
												</script>
												<tr id="image-opt-tr-1">
													<td valign="top">
														1.&nbsp;
														<a href="javascript:disposeImage('image-opt-1-','','',false,'-1');"> <img id="image-opt-1-CmsSysImgShow" src="../style/blue/images/no-image.png" width="90" height="67" /> </a>
														<input type="button" value="上传..." onclick="javascript:showModuleImageDialogForDialog('image-opt-1-CmsSysImgShow','image-opt-1-','','','1','0','oeqd');" class="btn-1" />
														&nbsp;票数:
														<input id="image-opt-ele-vote-1" name="image-opt-ele-vote-1" type="text" class="form-input" size="4" />
														&nbsp;信息:
														<input id="image-opt-ele-target-1" name="image-opt-ele-target-1" type="text" class="form-input" style="width:200px" />
														<img class="cursor" src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addImageNewElement(1);" />
														&nbsp;
														<img class="cursor" src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeImageElement(1);" />
														<input type="hidden" id="image-opt-1-" name="image-opt-1-" />
														<input type="hidden" id="image-opt-${status.count}-_sys_jtopcms_old" name="image-opt-${status.count}-_sys_jtopcms_old" value="-1" />
														<input type="hidden" id="image-opt-1-CmsSysImgWidth" name="image-opt-1-CmsSysImgWidth" />
														<input type="hidden" id="image-opt-1-CmsSysImgHeight" name="image-opt-1-CmsSysImgHeight" />
													</td>
												</tr>

												<tr id="image-opt-tr-2">
													<td valign="top">
														2.&nbsp;
														<a href="javascript:disposeImage('image-opt-2-','','',false,'-1');"> <img id="image-opt-2-CmsSysImgShow" src="../style/blue/images/no-image.png" width="90" height="67" /> </a>
														<input type="button" value="上传..." onclick="javascript:showModuleImageDialogForDialog('image-opt-2-CmsSysImgShow','image-opt-2-','','','1','0','oeqd');" class="btn-1" />
														&nbsp;票数:
														<input id="image-opt-ele-vote-2" name="image-opt-ele-vote-2" type="text" class="form-input" size="4" />
														&nbsp;信息:
														<input id="image-opt-ele-target-2" name="image-opt-ele-target-2" type="text" class="form-input" style="width:200px" />
														<img class="cursor" src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addImageNewElement(2);" />
														&nbsp;
														<img class="cursor" src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeImageElement(2);" />
														<input type="hidden" id="image-opt-2-" name="image-opt-2-" />
														<input type="hidden" id="image-opt-2-CmsSysImgWidth" name="image-opt-2-CmsSysImgWidth" />
														<input type="hidden" id="image-opt-2-CmsSysImgHeight" name="image-opt-2-CmsSysImgHeight" />
													</td>
												</tr>

												<tr id="image-opt-tr-3">
													<td valign="top">
														3.&nbsp;
														<a href="javascript:disposeImage('image-opt-3-','','',false,'-1');"> <img id="image-opt-3-CmsSysImgShow" src="../style/blue/images/no-image.png" width="90" height="67" /> </a>
														<input type="button" value="上传..." onclick="javascript:showModuleImageDialogForDialog('image-opt-3-CmsSysImgShow','image-opt-3-','','','1','0','oeqd');" class="btn-1" />
														&nbsp;票数:
														<input id="image-opt-ele-vote-3" name="image-opt-ele-vote-3" type="text" class="form-input" size="4" />
														&nbsp;信息:
														<input id="image-opt-ele-target-3" name="image-opt-ele-target-3" type="text" class="form-input" style="width:200px" />
														<img class="cursor" src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addImageNewElement(3);" />
														&nbsp;
														<img class="cursor" src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeImageElement(3);" />
														<input type="hidden" id="image-opt-3-" name="image-opt-3-" />
														<input type="hidden" id="image-opt-3-CmsSysImgWidth" name="image-opt-3-CmsSysImgWidth" />
														<input type="hidden" id="image-opt-3-CmsSysImgHeight" name="image-opt-3-CmsSysImgHeight" />
													</td>
												</tr>

												<tr id="image-opt-tr-4">
													<td valign="top">
														4.&nbsp;
														<a href="javascript:disposeImage('image-opt-4-','','',false,'-1');"> <img id="image-opt-4-CmsSysImgShow" src="../style/blue/images/no-image.png" width="90" height="67" /> </a>
														<input type="button" value="上传..." onclick="javascript:showModuleImageDialogForDialog('image-opt-4-CmsSysImgShow','image-opt-4-','','','1','0','oeqd');" class="btn-1" />
														&nbsp;票数:
														<input id="image-opt-ele-vote-4" name="image-opt-ele-vote-4" type="text" class="form-input" size="4" />
														&nbsp;信息:
														<input id="image-opt-ele-target-4" name="image-opt-ele-target-4" type="text" class="form-input" style="width:200px" />
														<img class="cursor" src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addImageNewElement(4);" />
														&nbsp;
														<img class="cursor" src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeImageElement(4);" />
														<input type="hidden" id="image-opt-4-" name="image-opt-4-" />
														<input type="hidden" id="image-opt-4-CmsSysImgWidth" name="image-opt-4-CmsSysImgWidth" />
														<input type="hidden" id="image-opt-4-CmsSysImgHeight" name="image-opt-4-CmsSysImgHeight" />
													</td>
												</tr>


											</cms:else>


										</table>
									</td>
								</tr>

								<tr id="text-size-tr" style="display:none">
									<td class="input-title">
										<strong> 每行字数: </strong>
									</td>
									<td class="td-input">
								
										<cms:if test="${Survey.optionType==5}">
										
											<cms:SurveyOption >
												<input id="inputTextCount" name="inputTextCount" type="text" class="form-input" size="15" value="${Option.inputTextCount}" />
											</cms:SurveyOption>
											
										</cms:if>
										<cms:else>
										
											<input id="inputTextCount" name="inputTextCount" type="text" class="form-input" size="15" value="200" />
										
										</cms:else>
										<%--
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<strong> <span class="input-title"> 每行字数: </span> </strong>
									<input id="inputTextCount" name="inputTextCount" type="text" class="form-input" size="15" value="200" />
								--%>
									</td>
								</tr>


							</table>

							<!-- hidden -->
							<input type="hidden" id="groupId" name="groupId" value="${param.groupId}" />
							<input type="hidden" id="groupFlag" name="groupFlag" value="${param.groupFlag}" />
							<input type="hidden" id="surveyId" name="surveyId" value="${param.sid}" />

							<input type="hidden" id="textCount" name="textCount" />
							<input type="hidden" id="imageCount" name="imageCount" />
							
							<cms:Token mode="html"/>

						</form>
						<div style="height:35px;"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitSurveyForm();"  class="btnwithico"> <img src="../style/icons/tick.png" width="16" height="16" /> <b> 保存&nbsp; </b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""> <img src="../style/icon/close.png" width="16" height="16"><b> 取消&nbsp; </b> </a>
									</div>
								 
								</tr>
							</table>
						</div>
						<div style="height:15px;"></div>
					</td>
				</tr>

			 
			</table>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

//调查类型
changeEleOpt('${Survey.optionType}');

initRadio('optionType','${Survey.optionType}');

//追加标题
initRadio('addInfo','${Survey.haveText}');

if('${Survey.haveText}' == '1' )
{
	changeAddInfo();
}



function addTextNewElement(id)
{
    textCount++;
	var element = '<tr id="text-opt-tr-'+textCount+'"><td>'+textCount+'.&nbsp;&nbsp;<input id="text-opt-ele-input-'+textCount+'" name="text-opt-ele-input-'+textCount+'" type="text" class="form-input" style="width:400px" />&nbsp;&nbsp;票数:&nbsp;<input id="text-opt-ele-vote-'+textCount+'" name="text-opt-ele-vote-'+textCount+'" type="text" class="form-input" size="4" />&nbsp;&nbsp;&nbsp;<img src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addTextNewElement('+textCount+');"/>&nbsp;&nbsp;&nbsp;<img src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeTextElement('+textCount+');"/></td></tr>';
	$('#text-opt').append(element);
	
	for(var i = textCount; i > id+1 ;i--)
	{
		$('#text-opt-ele-input-'+i).val($('#text-opt-ele-input-'+(i-1)).val());
	    $('#text-opt-ele-vote-'+i).val($('#text-opt-ele-vote-'+(i-1)).val());
	}
	
	//将下一行元素的值去掉，作为新的行
	$('#text-opt-ele-input-'+(id+1)).val('');
	$('#text-opt-ele-vote-'+(id+1)).val('');

}

function addImageNewElement(id)
{
    imageCount++;
    
    var element =
    			'<tr id="image-opt-tr-'+imageCount+'"><td valign="top">'
				+imageCount+'.&nbsp;&nbsp;'
    			+'<a  href=\'javascript:disposeImage("image-opt-'+imageCount+'-","","",false,"-1");\'>'
					+'<img id="image-opt-'+imageCount+'-CmsSysImgShow" src="../style/blue/images/no-image.png" width="90"height="67" />'
                +'</a>'
				+'&nbsp;<input type="button" value="上传..." onclick=\'javascript:showModuleImageDialogForDialog("image-opt-'+imageCount+'-CmsSysImgShow","image-opt-'+imageCount+'-","","","1","0","oeqd");\' class="btn-1" />'												
				+'&nbsp;&nbsp;票数:&nbsp;'
				+'<input id="image-opt-ele-vote-'+imageCount+'" name="image-opt-ele-vote-'+imageCount+'" type="text" class="form-input" size="4" />'
				+'&nbsp;&nbsp;信息:'
				+'&nbsp;<input id="image-opt-ele-target-'+imageCount+'" name="image-opt-ele-target-'+imageCount+'" type="text" class="form-input" style="width:200px" />'
				+'&nbsp;<img class="cursor" src="../../core/style/icons/plus.png" width="16" height="16" onclick="javascript:addImageNewElement('+imageCount+');" />'
				+'&nbsp;'
				+'&nbsp;&nbsp;<img class="cursor" src="../../core/style/icons/cross.png" width="16" height="16" onclick="javascript:removeImageElement('+imageCount+');" />'
				+'<input type="hidden" id="image-opt-'+imageCount+'-" name="image-opt-'+imageCount+'-" />'
				+'<input type="hidden" id="image-opt-'+imageCount+'-CmsSysImgWidth" name="image-opt-'+imageCount+'-CmsSysImgWidth" />'
				+'<input type="hidden" id="image-opt-'+imageCount+'-CmsSysImgHeight" name="image-opt-'+imageCount+'-CmsSysImgHeight" />'
				+'</td></tr>';
				
	
	$('#image-opt').append(element);
	
	//下一行元素值移位
	for(var i = imageCount; i > id+1 ;i--)
	{
		$('#image-opt-'+i+'-CmsSysImgShow').attr('src',$('#image-opt-'+(i-1)+'-CmsSysImgShow').attr('src'));
			    
	    $('#image-opt-ele-vote-'+i).val($('#image-opt-ele-vote-'+(i-1)).val());
	    $('#image-opt-ele-target-'+i).val($('#image-opt-ele-target-'+(i-1)).val());
	   														
	    $('#image-opt-'+i+'-').val($('#image-opt-'+(i-1)+'-').val());
	    $('#image-opt-'+i+'-_sys_jtopcms_old').val($('#image-opt-'+(i-1)+'-_sys_jtopcms_old').val());
	    $('#image-opt-'+i+'-CmsSysImgWidth').val($('#image-opt-'+(i-1)+'-CmsSysImgWidth').val());
	    $('#image-opt-'+i+'-CmsSysImgHeight').val($('#image-opt-'+(i-1)+'-CmsSysImgHeight').val());
	    
	    
	}
	
	//将下一行元素的值去掉，作为新的行
	$('#image-opt-'+(id+1)+'-CmsSysImgShow').attr('src','../style/blue/images/no-image.png');
	
	$('#image-opt-ele-vote-'+(id+1)).val('');
	$('#image-opt-ele-target-'+(id+1)).val('');
	
	$('#image-opt-'+(id+1)+'-').val('');
	$('#image-opt-'+(id+1)+'-CmsSysImgWidth').val('');
	$('#image-opt-'+(id+1)+'-CmsSysImgHeight').val('');

}

function removeTextElement(id)
{
	for(var i = id; i <= textCount ;i++)
	{
		$('#text-opt-ele-input-'+i).val($('#text-opt-ele-input-'+(i+1)).val());
	    $('#text-opt-ele-vote-'+i).val($('#text-opt-ele-vote-'+(i+1)).val());
	}

	$('#text-opt-tr-'+textCount).remove();
	textCount--;
}

function removeImageElement(id)
{
	for(var i = id; i <= imageCount ;i++)
	{  
	    $('#image-opt-'+i+'-CmsSysImgShow').attr('src',$('#image-opt-'+(i+1)+'-CmsSysImgShow').attr('src'));
			    
	    $('#image-opt-ele-vote-'+i).val($('#image-opt-ele-vote-'+(i+1)).val());
	    $('#image-opt-ele-target-'+i).val($('#image-opt-ele-target-'+(i+1)).val());
	    
	    $('#image-opt-'+i+'-').val($('#image-opt-'+(i+1)+'-').val());
	    $('#image-opt-'+i+'-CmsSysImgWidth').val($('#image-opt-'+(i+1)+'-CmsSysImgWidth').val());
	    $('#image-opt-'+i+'-CmsSysImgHeight').val($('#image-opt-'+(i+1)+'-CmsSysImgHeight').val());
	}

	$('#image-opt-tr-'+imageCount).remove();
	
	imageCount--;
}


function changeEleOpt(opt)
{
	if('3' == opt || '4' == opt)
	{
		$('#opt-tr').show();
		$('#text-opt').hide();
		$('#image-opt').show();
		$('#text-size-tr').hide();
	}
	else if('1' == opt || '2' == opt)
	{
		$('#opt-tr').show();
		$('#text-opt').show();
		$('#image-opt').hide();
		$('#text-size-tr').hide();
	}
	else
	{
		$('#text-opt').hide();
		$('#image-opt').hide();
		$('#opt-tr').hide();
		$('#text-size-tr').show();	
	}
	
	if('5' == opt)
	{	
		$('#add-info-tr').hide();
		$("#addInfo").removeAttr("checked");
		$("#addInfo").attr("disabled","disabled");
	}
	else
	{
		$("#addInfo").removeAttr("disabled"); 
	}
}

function changeAddInfo()
{
	if('checked' == $("#addInfo").attr("checked"))
	{
		$('#add-info-tr').show();
	}
	else
	{
		$('#add-info-tr').hide();
	}
}


//showTips('modelName','不可为空');
function submitSurveyForm()
{
   var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('surveyTitle',1,null,null);	
        
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
	
   document.getElementById('textCount').value = textCount;
   document.getElementById('imageCount').value = imageCount;
   
   encodeFormInput('surveyForm', false);
   
   surveyForm.action="<cms:BasePath/>survey/editSurvey.do";
   surveyForm.submit();
}


function addSinlePhotoDialog(id)
{
	 W.$.dialog({ 
	   id:'asi',
    	title :'添加图片',
    	width: '640px', 
    	height: '340px', 
    	parent:api,
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/question/AddSingleGroupPhoto.jsp?imagePosId='+id
	
	  
	});

}

function close()
{
	api.close();
}
   
  
</script>
</cms:Survey>
