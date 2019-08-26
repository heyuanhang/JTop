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

		<script>
		basePath='<cms:BasePath/>';
		
		 function showErrorMsg(msg)
		 {
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
		}
      
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     //showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
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
			    				
			                    content: '新增模型字段成功!',
	
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
		    validate('showName',0,ref_name,'格式为文字,数字,下划线');
 			validate('filedSign',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
         
        

      </script>
	</head>
	<body>


		<form id="filedForm" name="filedForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top:2px">
							<ul>
								<li id="two1" onclick="setTab('two',1,4)" class="selectTag">
									<a href="javascript:;"><img src="../style/icons/paint-can.png" width="16" height="16" />字段信息&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab('two',2,4)">
									<a href="javascript:;"><img src="../style/icons/exclamation-white.png" width="16" height="16" />验证规则&nbsp;</a>
								</li>
								<li id="two3" onclick="setTab('two',3,4)">
									<a href="javascript:;"><img src="../style/icons/monitor-window-3d.png" width="16" height="16" />界面与脚本&nbsp;</a>
								</li>

								<li id="two4" onclick="setTab('two',4,4)">
									<a href="javascript:;"><img src="../style/icons/script-code.png" width="16" height="16" />自定义HTML文本&nbsp;</a>
								</li>

							</ul>
						</div>

						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td width="24%" class="input-title">
													<strong>展示形式</strong>
												</td>
												<td width="80%" class="td-input">
													<select  style="width:127px" id="htmlElementId" name="htmlElementId" onchange="javascript:switchHtmlElementType(this.value)">

														<option value="-1" selected>
															- 页面类型 -
														</option>
														<option value="1">
															文本输入框&nbsp;&nbsp;
														</option>
														<option value="2">
															多行文本框
														</option>

														<option value="4">
															下拉选择框
														</option>
														<option value="5">
															单选框
														</option>
														<option value="6">
															复选框
														</option>

														<option value="7">
															日期选择
														</option>
														<option value="8">
															时间选择
														</option>
														<option value="9">
															日期时间选择
														</option>
														<option value="10">
															文件上传
														</option>
														<option value="11">
															图片上传
														</option>
														
														<cms:if test="${param.modelType != 5 && param.modelType != 6 && param.modelType != 7 && param.modelType != 8 && param.modelType != 9   && param.modelType != 3}">
														
														<option value="14">
															图集上传
														</option>
														<option value="12">
															媒体上传
														</option>
														
														</cms:if>
														
														<cms:if test="${param.modelType != 5 && param.modelType != 6 && param.modelType != 8 && param.modelType != 9 && param.modelType != 4  }">
														
														<option value="3">
															文本编辑器
														</option>
														
														</cms:if>
														
														
														
														<option value="13">
															自定义HTML
														</option>
														
														<cms:if test="${param.modelType != 3 && param.modelType != 7 && param.modelType != 5 && param.modelType != 6 && param.modelType != 8 && param.modelType != 9 && param.modelType != 4}">
														<option value="15">
															内部数据
														</option>
														</cms:if>
													</select>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<span id="dataTypeDIV"><span class="input-title"><strong>数据类型</strong> <select style="width:127px" id="dataType" name="dataType" onchange="javascript:switchDataType(this.value)">
																<option value="-1" selected>
																	- 数据类型 -
																</option>
																<option value="1">
																	字符串
																</option>
																<option value="2">
																	大型文章
																</option>
																<option value="3">
																	整数
																</option>
																<option value="4">
																	大型整数
																</option>
																<option value="5">
																	小数
																</option>
																<option value="6">
																	日期时间
																</option>
																<option value="7">
																	日期
																</option>
																<option value="8">
																	时间
																</option>
															</select> </span> </span>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>字段名称</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:340px" id="showName" name="showName" class="form-input"></input>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>唯一标识</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:340px" id="filedSign" name="filedSign" class="form-input"></input>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>字数(字符型)</strong>
												</td>
												<td class="td-input">

													<!-- 文本 -->
													<input type="text"  style="width:345px" id="maxLength" name="maxLength" class="form-input" value="60"></input>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>默认值</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:340px" id="defaultValue" name="defaultValue" class="form-input"></input>
												</td>
											</tr>
											
					


											<tr>
												<td class="input-title">
													<strong>提示描叙</strong>
												</td>
												<td class="td-input">
													<textarea id="htmlDesc" name="htmlDesc" style="height:80px;width:340px" class="form-textarea"></textarea>
												</td>
											</tr>
											<tr id="mustFillDIV">
												<td class="input-title">
													<strong>必填字段</strong>
												</td>
												<td class="td-input">
													<input name="isMustFill" type="radio" class="form-radio" value="1" />
													<span class="STYLE12">是</span>&nbsp;&nbsp;
													<input name="isMustFill" type="radio" class="form-radio" value="0" checked />
													<span class="STYLE12">否</span>
													&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>查询</strong><input type="checkbox" class="form-checkbox" id="queryFlag" name="queryFlag" value="1" disabled />
													&nbsp;&nbsp;&nbsp;<strong>排序</strong><input type="checkbox" class="form-checkbox" id="orderFlag" name="orderFlag" value="1" disabled />
													&nbsp;&nbsp;&nbsp;<strong>搜索</strong><input type="checkbox" class="form-checkbox" id="searchFlag" name="searchFlag" value="1" disabled />
													&nbsp;&nbsp;&nbsp;<strong>采集</strong><input type="checkbox" class="form-checkbox" id="pickFlag" name="pickFlag" value="1"  /></span>
										
												</td>
											</tr>
											<%--

									<tr id="defaultValueMultDIV" style="display:none">
										<td class="input-title">
											<strong>默认值</strong>
										</td>
										<td class="td-input">
											<textarea rows="3" cols="63" id="defaultValueMult" name="defaultValueMult" class="form-textarea"></textarea>
										</td>
									</tr>

									--%>
											<tr id="editorDIV" style="display:none">
												<td class="input-title">
													<strong>全功能 
												</td>
												<td class="td-input">
													<input name="fullEditor" type="radio" class="form-radio" value="1" onclick="javascript:changeMainEditor()" />
													是&nbsp;&nbsp;
													<input name="fullEditor" type="radio" class="form-radio" value="0" checked onclick="javascript:changeMainEditor()" />
													否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<span class="input-title"><strong>规格</strong>&nbsp;<input type="text" size="3" id="editorW" name="editorW" class="form-input" value="752"></input> 宽&nbsp;&nbsp;&nbsp;<input type="text" size="3" id="editorH" name="editorH" class="form-input" value="255"></input> 高&nbsp;&nbsp;&nbsp;主编辑器&nbsp;<input type="checkbox" class="form-checkbox" disabled id="mainEditor" name="mainEditor" value="1" />
													</span>
												</td>
											</tr>


											<tr id="imageDIV" style="display:none">
												<td class="input-title">
													<strong>是否需要水印 
												</td>
												<td class="td-input">
													<input name="needMark" type="radio" class="form-radio" value="1" checked />
													是&nbsp;&nbsp;
													<input name="needMark" type="radio" class="form-radio" value="0" />
													否&nbsp;&nbsp;&nbsp;
													<span class="input-title"><strong>规格</strong> <input type="text" size="3" id="imageW" name="imageW" class="form-input"></input> 宽&nbsp;&nbsp;&nbsp;<input type="text" size="3" id="imageH" name="imageH" class="form-input"></input> 高&nbsp;&nbsp;&nbsp;&nbsp;<select class="form-select" id="imageDisposeMode" name="imageDisposeMode">
															<option value="0" selected>
																不缩放
															</option>
															<option value="1">
																按宽缩放
															</option>
															<option value="2">
																按高缩放
															</option>
															<option value="3">
																宽高缩放
															</option>
														</select> </span>
												</td>
											</tr>

											<tr id="directUrlDIV" style="display:none">
												<td class="input-title">
													<strong>隐藏路径 
												</td>
												<td class="td-input">
													<input name="useSysUrl" type="radio" class="form-radio" value="1" checked />
													是&nbsp;&nbsp;
													<input name="useSysUrl" type="radio" class="form-radio" value="0" />
													否
												</td>
											</tr>

											<tr id="selectDIV" style="display:none">
												<td class="input-title">
													<strong>关联模型 
												</td>
												<td class="td-input">
													<select id="linkModelId" name="linkModelId" class="form-select" onchange="javascript:changeLinkType();">
															<option value="-1">
																----- 无关联模型 -----
																</span>&nbsp;
															</option>

															<cms:SystemDataModelList modelType="${param.modelType}">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}
																		</span>&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
														</select>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="input-title"><strong>字段</strong>&nbsp;<select class="form-select" id="linkFieldId" name="linkFieldId" >
															<option value="-1" selected>
																---- 可选关联字段 ----
															</option>
															<cms:SystemList querySign="SELECT_META_INFO_QUERY" var="${ModelFiled.linkModelId}">
																<option  value="${SysObj.metaDataId}">
																	${SysObj.showName}&nbsp;&nbsp;&nbsp;&nbsp;
																</option>
															</cms:SystemList>
														</select> </span>
												</td>


											</tr>
											



											<!-- 以下为独立选项 start -->


										</table>

										<div style="height:15px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													 <div style="float:right">
														<a name="btnwithicosysflag" href="javascript:submitModelFiledForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td width="22%" class="input-title">
													<strong>业务规则</strong>
												</td>
												<td class="td-input">
													<input type="radio" name="defaultValidate" value='1' />
													电话传真&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='2' />
													手机号码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='3' />
													电子邮件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

												</td>

											</tr>


											<tr>
												<td class="input-title">

												</td>
												<td class="td-input">
													<input type="radio" name="defaultValidate" value='4' />
													邮政编码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='5' />
													网页链接&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='6' />
													IP地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
											</tr>
											<tr>
												<td class="input-title">

												</td>
												<td class="td-input">
													<input type="radio" name="defaultValidate" value='7' />
													用户名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='8' />
													密码数据&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

													<input type="radio" name="defaultValidate" value='9999' checked/>
													无需验证&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
											</tr>




											<tr>
												<td class="input-title">
													<strong>字符规则 
												</td>
												<td class="td-input">

													<input type="radio" name="defaultValidate" value='9' />
													中文字符&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='10' />
													英文字母&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='11' />
													任意数字&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


												</td>
											</tr>

											<tr>
												<td class="input-title">

												</td>
												<td class="td-input">
													<input type="radio" name="defaultValidate" value='12' />
													整数字符&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='13' />
													小数字符&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


												</td>
											</tr>


											<tr>
												<td class="input-title">
													<strong>时间规则 
												</td>
												<td class="td-input">

													<input type="radio" name="defaultValidate" value='16' />
													日期时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='14' />
													仅有日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" name="defaultValidate" value='15' />
													仅有时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


												</td>
											</tr>


											<tr>
												<td class="input-title">
													<strong>自定义规则(正则)</strong>
												</td>
												<td class="td-input">

													<!-- 文本 -->
													<input type="text"  style="width:345px" id="checkRegex" name="checkRegex" class="form-input"></input>
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>提示信息</strong>
												</td>
												<td class="td-input">
													<input type="text"  style="width:345px" id="errorMessage" name="errorMessage" class="form-input"></input>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>允许上传格式</strong>
												</td>
												<td class="td-input">

													<!-- 文本 -->
													<input type="text"  style="width:345px" id="allowableFile" name="allowableFile" class="form-input"></input>
												</td>
											</tr>

											

										</table>

										<div style="height:15px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a name="btnwithicosysflag" href="javascript:submitModelFiledForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>
									</li>
								</ul>
							</div>



							<!-- 第三部分： 审核角色-->
							<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">

								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

											<!-- 以下为独立选项 start -->
											
											<tr>
												<td class="input-title">
													<strong>Class参数 <!-- 文本 -->
												</td>
												<td class="td-input">
													<input type="text" style="width:430px" id="cssClass" name="cssClass" class="form-input"></input>
												</td>
											</tr>

											<tr>
												<td width="15%" class="input-title">
													<strong>CSS文本 <!-- 文本 -->
												</td>
												<td width="75%" class="td-input">
													<textarea id="style" name="style" style="width:430px; height:80px;" class="form-textarea"></textarea>
												</td>
											</tr>


											<tr>
												<td class="input-title">
													<strong>JS文本 <!-- 文本 -->
												</td>
												<td class="td-input">
													<textarea id="javascript" name="javascript" style="width:430px; height:80px;" class="form-textarea"></textarea>
												</td>
											</tr>

											<tr id="choiceValueDIVs" style="display:">
												<td class="input-title">
													<strong>可选值 <!-- 下拉框 -->
												</td>
												<td class="td-input">
													<textarea id="choiceValue" name="choiceValue" style="width:430px; height:150px;" class="form-textarea"></textarea>
												</td>
											</tr>


										</table>
										<div style="height:15px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a name="btnwithicosysflag" href="javascript:submitModelFiledForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>

									</li>
								</ul>

							</div>



							<!-- 第四部分：审核机构 -->
							<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">

								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

											<!-- 以下为独立选项 start -->


											<tr id="htmlContentDIV">
												<td class="input-title">
													<strong>HTML代码: <!-- 自定义html -->
												</td>
												<td class="td-input">
													<textarea id="htmlContent" name="htmlContent" style="height:380px;width:480px" class="form-textarea"></textarea>
												</td>

											</tr>


										</table>

										<div style="height:15px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a name="btnwithicosysflag" href="javascript:submitModelFiledForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

			<input type="hidden" id="dataModelId" name="dataModelId" value="${param.modelId}" />
			
			<input type="hidden" id="linkType" name="linkType" value="0" />
			
			<cms:Token mode="html"/>
		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">

function setTab(flag,pos,size)
{
		hideTips('showName');
		hideTips('filedSign');
		setTab2(flag,pos,size);
	
}

function switchHtmlElementType(htmlType)
{	
    $("#dataType").val("-1");
    
	//文本框
	if((6 < htmlType || 3 == htmlType) && 15 != htmlType)
	{
		$('#dataType').attr("disabled",true); 
	}
	else
	{
		$('#dataType').attr("disabled",false); 
	}
	
	//日期和时间
	if(htmlType == 7 || htmlType == 8 || htmlType == 9)
	{
		$('#queryFlag').attr("disabled",false); 
		$('#orderFlag').attr("disabled",false);  
		 
	}
	else
	{
		$('#queryFlag').attr("disabled",true); 
		$('#orderFlag').attr("disabled",true);  
		
		$("#queryFlag").attr('checked',false);
		$("#orderFlag").attr('checked',false);
	}
	
	//默认样式
	if(htmlType == 1 )
	{
		$('#cssClass').val('form-input');
		$('#style').val('width:302px;');
		
	}
	else if (htmlType == 7 || htmlType == 8 || htmlType == 9)
	{
		$('#style').val('width:302px;');
	}
	else if(htmlType == 2)
	{
		$('#cssClass').val('form-textarea');
		$('#style').val('width:747px;height:200px');
	}
	else if(htmlType == 4)
	{
		$('#cssClass').val('form-select');
		$('#style').val('width:306px;');
	}
	else if(htmlType == 5)
	{
		$('#cssClass').val('form-radio');
	}
	else if(htmlType == 6)
	{
		$('#cssClass').val('form-checkbox');
	}
	else
	{
		$('#cssClass').val('');
		$('#style').val('');
	}
	
	
	//图片类型
	if(11 == htmlType || 14 == htmlType)
	{
		$('#imageDIV').show(); 
	}
	else
	{
		$('#imageDIV').hide();
	}
	
	//是否功能编辑器
	if(3 == htmlType)
	{
		$('#editorDIV').show();
	}
	else
	{
		$('#editorDIV').hide();
	}
	
	//文件是否使用真实地址
	if(10 == htmlType)
	{
		$('#directUrlDIV').show();
	}
	else
	{
		$('#directUrlDIV').hide();
	}
	
	//选择关联字段
	if(4 == htmlType || 5 == htmlType || 6 == htmlType)
	{
		$('#selectDIV').show(); 
		
		if($('input[name="linkType"]:checked').val() == '1')
		{
			$("#dataType").val("1");
			$('#dataType').attr("disabled",true); 
		}
	}
	else
	{
		$('#selectDIV').hide();
	}
	
	//恢复不可设置搜索状态
	//是否功能编辑器
	
	if(3 == htmlType)
	{
		$('#searchFlag').attr("disabled",false);
		$('#maxLength').val('6000');
	}
	else
	{
		$('#searchFlag').attr("disabled",true); 
	}
	
	$("#searchFlag").attr('checked',false);
	
	//清除主编辑器状态
	$("#mainEditor").attr("checked",false);
}

function switchDataType(dataType)
{
	//只要不为文本,都可选择是否为排序和查询字段
	if(dataType > 2)
	{
		$('#queryFlag').attr("disabled",false); 
		$('#orderFlag').attr("disabled",false); 
	}
	else
	{
		$('#queryFlag').attr("disabled",false); 
		$('#orderFlag').attr("disabled",true);  
		
		//$("#queryFlag").attr('checked',false);
		$("#orderFlag").attr('checked',false);
	}
	
	//只有文本类型可以被添加为支持search的字段
	if(dataType == 1)
	{
		$('#searchFlag').attr("disabled",false);  
	}
	else
	{
		$('#searchFlag').attr("disabled",true); 
	
		$("#searchFlag").attr('checked',false);
	}
}

  
function close()
{
	api.close();
}

function changeLinkType()
{
	 var lmId = document.getElementById('linkModelId').value;
	 
	 linkType(lmId);
	 
	 if(lmId != -1)
	 {
		$("#dataType").val("4");
		$('#dataType').attr("disabled",true); 
	 }
	 else
	 {
	 	$("#dataType").val("-1");
	 	$('#dataType').attr("disabled",false); 
	 }
}

function linkType(modelId)
{
	var url = "<cms:BasePath/>metadata/getLinkFieldJson.do?modelId="+modelId+"&random="+Math.random();
  	 $.getJSON
	 (
		url, 
  		{},
  		function(data)
  		{
      	  $("#linkFieldId").empty();
      	  
      	   $("#linkFieldId").append('<option value="-1">---- 可选关联字段 ----</option>');	
      	  

      	  var index = 0;
      	  
  		  $.each
  		  (
  				data,
  				function(i,item)
  				{  					
  					
  					//alert('<option value="'+item.metaDataId+'">'+item.showName+'</option>');	
  					
  				     $("#linkFieldId").append('<option value="'+item.metaDataId+'">'+item.showName+'</option>');	  				      					
  				}	
  				     			
  			);
  			
  			
  		}
  	 );

}


function changeMainEditor()
{
	if($('#htmlElementId').val() == 3)
	{
		if($('input[name="fullEditor"]:checked').val() == '1')
		{
			$('#mainEditor').attr("disabled",false); 
			$('#editorH').val('565'); 
			$('#maxLength').val('100000');
		}
		else
		{
			$('#mainEditor').attr("disabled",true); 
			$("#mainEditor").attr('checked',false);
			$('#editorH').val('255'); 
			$('#maxLength').val('6000');
		}
	}
}


function submitModelFiledForm()
{
	if($('#htmlElementId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个页面元素类型!',

		    				cancel: true
		});
		
		return;
    }
    
	if((($("#htmlElementId").val() < 7 &&  $("#htmlElementId").val() != 3) || $("#htmlElementId").val() == 15 ) &&  $("#dataType").val() == -1)
	{
	    W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	                    parent:api,
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择一个数据类型!',
	                    
	    				cancel: true
		});
		return;
	}
	
	
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('filedSign',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('showName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	setTab2('two',1,4);
        	
        submitValidate('filedSign',0,ref_flag,'格式为字母,数字,下划线');	
        submitValidate('showName',0,ref_name,'格式为文字,数字,下划线');
        	
       
	    return;

	}
	
	//2015：必须以字母开头
	var str = /^[A-Za-z]+$/;
	
	var first = $('#filedSign').val().substring(0,1);
		 
    if(!eval('regexTest("'+first+'",'+str+')'))
	{
			setTab2('two',1,4);
		    showTips('filedSign','必须以字母开头！');
		    return;
	}
	
	//后台验证
	
	
		var count = validateExistFlag('modelField', 'jt_'+$('#filedSign').val());
	 
		if(count > 0)
		{
			setTab2('two',1,4);
        	
        	
			showTips('filedSign', '系统已存在此值，不可重复录入');
			
			return;
		}
		
	 
		if($('#linkModelId').val() != -1 && $('#linkFieldId').val() == -1)
	    {
	    	setTab2('two',1,4);
	        	
	         W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	                    parent:api,
	    				icon: '32X32/i.png', 
	    				
	                    content: '关联模型信息不完全!',
	                    
	    				cancel: true
		    });	        	
			 
	    	return;
	    }

    W.$.dialog.tips('正在更新系统元数据 可能需要较长时间...',60000); 
    

    
    disableAnchorElementByName("btnwithicosysflag",true);
    
    $('#filedSign').val('jt_'+$('#filedSign').val());
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    encodeFormInput('filedForm', false);
    
    var form = document.getElementById('filedForm');
	
    form.action="../../metadata/addModelFiled.do";
     
    form.submit(); 
}


</script>

