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
		<script type="text/javascript" src="../javascript/uuid.js"></script>


		<script>  
		var hasError = false;
		//验证
		$(window).load(function()
		{
			 validate('configName',1,null,null);
						
						
			
		
		})
	
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
         		W.$.dialog.tips('编辑规则成功！...',1);  
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>
		<cms:SystemList querySign="SELECT_PICK_RULE_SINGLE_QUERY" var="${param.cfgId}">


			<form id="pickRuleForm" name="pickRuleForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab('two',1,4)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/validation-valid-document.png" width="16" height="16" />配置信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab('two',2,4)">
										<a href="javascript:;"><img src="../style/icons/document-xaml.png" width="16" height="16" />数据匹配&nbsp;</a>
									</li>
									
									<li id="two3" onclick="setTab('two',3,4)">
										<a href="javascript:;"><img src="../style/icons/document-xaml.png" width="16" height="16" />扩展数据匹配&nbsp;</a>
									</li>

									<li id="two4" onclick="setTab('two',4,4)">
										<a href="javascript:;"><img src="../style/icons/lightning.png" width="16" height="16" />采集规则测试&nbsp;</a>
									</li>

								</ul>
							</div>

							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

												<tr>
													<td width="20%" class="input-title">
														<strong>规则名称</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:500px" id="configName" name="configName" class="form-input" value="${SysObj.configName}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>列表首页URL</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:500px" id="listHeadUrlRule" name="listHeadUrlRule" class="form-input" value="${SysObj.listHeadUrlRule}"></input>
															<br />
													<span class="ps">列表首页URL为列表第一页无数字区分的情况,一般为列表入口页,若不存在此情况,则不填写</span>
													</td>
												</tr>


												<tr>
													<td class="input-title">
														<strong>列表页URL</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:500px" id="listUrlRule" name="listUrlRule" class="form-input" value="${SysObj.listUrlRule}"></input>
														<br />
													<span class="ps">动态值如 index_{1-100}.html 对应为 1~100 页或 01~100 页,也可为倒序如 {100~1}</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>内容页URL</strong>
													</td>
													<td class="td-input">
														<textarea id="contentUrlRule" name="contentUrlRule" style="height:55px;width:500px" class="form-textarea">${SysObj.contentUrlRule}</textarea>
														<br />
														<select class="form-select" onchange="javascript:selectRule(this,'contentUrlRule')" style="width:237px">
															<option value="-1">
																------------- 设置动态值 --------------
															</option>
															<option value="{数字}">
																数字
															</option>
															<option value="{字母}">
																字母
															</option>
															<option value="{非数字}">
																非数字
															</option>
															<option value="{字母或数字}">
																字母或数字
															</option>
															<option value="{任意}">
																任意
															</option>
														</select>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<select class="form-select"  id="timeFormat" name="timeFormat" style="width:236px">
															<option value="">
																----------- 可选时间格式 ------------
															</option>
															
															<option value="yyyy-MM-dd HH:mm:ss">
																yyyy-MM-dd HH:mm:ss
															</option>
															<option value="yyyy-MM-dd HH:mm:ss:SSS">
																yyyy-MM-dd HH:mm:ss:SSS
															</option>
															<option value="yyyy-MM-dd HH:mm">
																yyyy-MM-dd HH:mm
															</option>
															<option value="yyyy-MM-dd">
																yyyy-MM-dd
															</option>
															<option value="yyyy-MM">
																yyyy-MM
															</option>
															
															<option value="yyyy年MM月dd日 HH:mm:ss">
																yyyy年MM月dd日 HH:mm:ss
															</option>
															<option value="yyyy年MM月dd日 HH:mm:ss:SSS">
																yyyy年MM月dd日 HH:mm:ss:SSS
															</option>
															<option value="yyyy年MM月dd日 HH:mm">
																yyyy年MM月dd日 HH:mm
															</option>
															<option value="yyyy年MM月dd日">
																yyyy年MM月dd日
															</option>
															<option value="yyyy年MM月">
																yyyy年MM月
															</option>
														</select>
													</td>
												</tr>

												<%--<tr>
													<td class="input-title">
														<strong>内容分页URL</strong>
													</td>
													<td class="td-input">
														<textarea id="contentPageUrlRule" name="contentPageUrlRule" style="height:40px;width:500px" class="form-textarea">${SysObj.contentPageUrlRule}</textarea>
														<br />
														<select class="form-select" onchange="javascript:selectRule(this,'contentUrlRule')">
															<option value="-1">
																-- 设置动态值 --
															</option>
															<option value="{数字}">
																数字
															</option>
															<option value="{字母}">
																字母
															</option>
															<option value="{非数字}">
																非数字
															</option>
															<option value="{字母或数字}">
																字母或数字
															</option>
															<option value="{任意}">
																任意
															</option>
														</select>
													</td>
												</tr>

												--%><tr>
													<td class="input-title">
														<strong>内容前缀URL</strong>
													</td>
													<td class="td-input">
														 
														<textarea id="prefixSiteUrl" name="prefixSiteUrl" style="height:45px;width:500px" class="form-textarea">${SysObj.prefixSiteUrl}</textarea>
														<br />
														<span class="ps">当链接为相对URL,自动使用前缀补全为完整URL,c=详情页前缀,p=图片前缀</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>html模式</strong>
													</td>
													<td class="td-input">
														<input type="radio" id="htmlMode" name="htmlMode" class="form-radio" value="1" />
														是&nbsp;
														<input type="radio" id="htmlMode" name="htmlMode" class="form-radio" value="0" />
														否
														<span class="ps">当为html模式时，采集到的信息保留所有html代码，否则只存储无格式文本</span>
													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>采集扩展模型</strong>
													</td>
													<td class="td-input">
														<select class="form-select" id="extModelId" name="extModelId">
															<option value="-1">
																---- 可选扩展采集 ----
															</option>
															<cms:QueryData objName="Er" service="cn.com.mjsoft.cms.pick.service.PickService" method="getPickModelExt" var="-9999">
											
																<option value="${Er.eprId}">
																	${Er.eprName}
																</option>
															</cms:QueryData>
															
														 
														</select>
														<span class="ps">选择一个数据模型并确认，可在数据匹配选项中扩展采集规则</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>描叙</strong>
													</td>
													<td class="td-input">
														<textarea id="configDesc" name="configDesc" style="height:55px;width:500px" class="form-textarea">${SysObj.configDesc}</textarea>
													</td>
												</tr>



												<!-- 以下为独立选项 start -->


											</table>

											<div style="height:50px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">

															<a id="buttonHref" href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>
										</li>
									</ul>
								</div>

								<!-- 第二部分:匹配数据 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">

									<ul>
										<li>

											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="14%" class="input-title">
														<strong>列表URL范围</strong>
													</td>
													<td class="td-input">
														<textarea id="listStart" name="listStart" style="height:70px;width:578px" class="form-textarea">${SysObj.listStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="listEnd" name="listEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.listEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>
												
												<tr>
													<td   class="input-title">
														<strong>标题</strong>
													</td>
													<td class="td-input">
														<textarea id="titleStart" name="titleStart" style="height:70px;width:578px" class="form-textarea">${SysObj.titleStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="titleEnd" name="titleEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.titleEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>正文</strong>
													</td>
													<td class="td-input">
														<textarea id="contentStart" name="contentStart" style="height:70px;width:578px" class="form-textarea">${SysObj.contentStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="contentEnd" name="contentEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.contentEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>


												<tr>
													<td class="input-title">
														<strong>摘要</strong>
													</td>
													<td class="td-input">
														<textarea id="summaryStart" name="summaryStart" style="height:70px;width:578px" class="form-textarea">${SysObj.summaryStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="summaryEnd" name="summaryEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.summaryEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>



												<tr>
													<td class="input-title">
														<strong>录入时间</strong>
													</td>
													<td class="td-input">
														<textarea id="addDateStart" name="addDateStart" style="height:70px;width:578px" class="form-textarea">${SysObj.addDateStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="addDateEnd" name="addDateEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.addDateEnd}</textarea>
														<span class="ps">结束</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>文章来源</strong>
													</td>
													<td class="td-input">
														<textarea id="sourceStart" name="sourceStart" style="height:70px;width:578px" class="form-textarea">${SysObj.sourceStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="sourceEnd" name="sourceEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.sourceEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>作者</strong>
													</td>
													<td class="td-input">
														<textarea id="authorStart" name="authorStart" style="height:70px;width:578px" class="form-textarea">${SysObj.authorStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="authorEnd" name="authorEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.authorEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>Tag词</strong>
													</td>
													<td class="td-input">
														<textarea id="keywordStart" name="keywordStart" style="height:70px;width:578px" class="form-textarea">${SysObj.keywordStart}</textarea>
														<span class="ps">开始</span>
														<br />
														<textarea id="keywordEnd" name="keywordEnd" style="height:70px;width:578px" class="form-textarea">${SysObj.keywordEnd}</textarea>
														<span class="ps">结束</span>
														<br/><br/>
													</td>
												</tr>
												
												 


												<!-- 以下为独立选项 start -->


											</table>

											<div style="height:50px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="buttonHref" href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>
										</li>
									</ul>
								</div>
								
								
								<!-- 第二部分:扩展匹配数据 -->
								<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">

									<ul>
										<li>

											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												 
												
												<cms:QueryData objName="Er"  service="cn.com.mjsoft.cms.pick.service.PickService" method="getPickModelExt" var="${SysObj.extModelId}">
	
												<cms:QueryData objName="PF" service="cn.com.mjsoft.cms.metadata.service.MetaDataService" method="getModelPickFieldTag" var="${Er.modelId}">
												
													<tr>
														<td width="14%" class="input-title">
															<strong>${PF.showName}</strong>
														</td>
														<td class="td-input">
															<cms:QueryData objName="SE" service="cn.com.mjsoft.cms.pick.service.PickService" method="getPickModelExtField" var="${Er.eprId},${PF.filedSign}">
																		
															
															<textarea  id="${PF.filedSign}Start" name="${PF.filedSign}Start" style="height:70px;width:578px" class="form-textarea">${SE.colStart}</textarea>
															<span class="ps">开始</span>
															<br />
															<textarea  id="${PF.filedSign}End" name="${PF.filedSign}End" style="height:70px;width:578px" class="form-textarea">${SE.colEnd}</textarea>
															<span class="ps">结束</span>
															<br/><br/>
															
															
															</cms:QueryData>
														</td>
													</tr>
												
												</cms:QueryData>
												</cms:QueryData>
												<cms:Empty flag="Er">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
														<font>
															当前规则没有选择采集扩展
														</font>
														</center>
													</td>
												</cms:Empty>

												<!-- 以下为独立选项 start -->


											</table>

											<div style="height:50px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="buttonHref" href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>
										</li>
									</ul>
								</div>

								<!-- 第三部分:测试数据 -->
								<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">

									<ul>
										<li>

											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


												<tr>
													<td width="15%" class="input-title">
														<strong>标题结果</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="titleStartTest" name="titleStart" style="height:45px;width:578px" class="form-textarea"></textarea>


													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>正文结果</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="contentStartTest" name="contentStart" style="height:135px;width:578px" class="form-textarea"></textarea>

													</td>
												</tr>


												<tr>
													<td class="input-title">
														<strong>摘要结果</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="summaryStartTest" name="summaryStart" style="height:65px;width:578px" class="form-textarea"></textarea>


													</td>
												</tr><%--



												<tr>
													<td class="input-title">
														<strong>录入时间结果</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="addDateStartTest" name="addDateStart" style="height:25px;width:578px" class="form-textarea"></textarea>

													</td>
												</tr>

												--%><tr>
													<td class="input-title">
														<strong>来源媒体</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="sourceStartTest" name="sourceStart" style="height:25px;width:578px" class="form-textarea"></textarea>

													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>作者或创建人</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="authorStartTest" name="authorStart" style="height:25px;width:578px" class="form-textarea"></textarea>

													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>关键字结果</strong>
													</td>
													<td class="td-input">
														<textarea readonly id="keywordsStartTest" style="height:25px;width:578px" class="form-textarea"></textarea>

													</td>
												</tr>



												<!-- 以下为独立选项 start -->


											</table>

											<div style="height:50px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a id="buttonHref" href="javascript:doTestPickContent();"  class="btnwithico"><img src="../style/icons/lightning.png" width="16" height="16"/><b>测试采集规则&nbsp;</b> </a>

															<a id="buttonHref" href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
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
				<input type="hidden" name="pickCfgId" id="pickCfgId" value="${SysObj.pickCfgId}" />
				
				<cms:Token mode="html"/>



			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initRadio('htmlMode','${SysObj.htmlMode}');

initSelect('extModelId','${SysObj.extModelId}');

initSelect('timeFormat','${SysObj.timeFormat}');

function setTab(flag,pos,size)
{
	hideTips('configName');
	setTab2(flag,pos,size);

}

  
function close()
{
	api.close();
}



 


function submitPickRuleForm()
{    

	var target = $("#configName").val();
				if(target.trim() == '')
				{
						showTips('configName','不可为空');
						hasError = true;
			    }
			    else
			    {
			    		hasError = false;
			    }

    
    if(hasError)
    {
		setTab('two',1,3);
    	showTips('configName','不可为空');
		//showErrorMsg('包含未正确填写的数据,请参照提示填写正确!');
	}
	else
	{
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
 	
	encodeFormInput('pickRuleForm', false);
   
    var form = document.getElementById('pickRuleForm');
	
	
    form.action="<cms:BasePath/>pick/editPickRule.do";
    
    form.submit(); 
    }
}

function doTestPickContent(taskId)
{
	 var key = new UUID().id;
	 
	 W.$.dialog.tips('正在尝试采集，请等待...',2); 
	 
	 var url = "<cms:BasePath/>pick/pickWeb.do?testMode=true&ruleId=${SysObj.pickCfgId}&key="+key+"&<cms:Token mode='param'/>";
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(da)
			            {     
			            	var data = eval("("+da+")");
			            
			            	 if(data.indexOf('您没有操作权限!') != -1)
		    			   {
		    			   		W.$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                    parent:api,
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: true
								});
								
								return;
		    			   }
			            
			               var jsonObj = eval("("+data+")");
		     
		     			   if('true' == jsonObj.empty)
		     			   {
		     			   		W.$.dialog(
							    { 
							   					title :'提示',
							    				width: '190px', 
							    				height: '60px', 
							                    lock: true, 
							                    parent:api,
							    				icon: '32X32/i.png', 
							    				
							                    content: '抱歉，没有采集到任何数据',
					
							    				cancel: true
								});
		     			   	 
		     			   }
		     			   else
		     			   {
		     			   
		     			   							$('#titleStartTest').val(jsonObj.title);
							    					
							    					$('#summaryStartTest').val(jsonObj.summary);
		     				
		     										$('#authorStartTest').val(jsonObj.author);
		     										
		     										$('#sourceStartTest').val(jsonObj.source);
		     										
		     										$('#keywordsStartTest').val(jsonObj.keywords);
		     										
		     										$('#contentStartTest').val(jsonObj.content);						
		     										
		     			   		 
		     				
		     			   }
		     				
							
			            }
	});	
	
			     	
	
	
   
}



</script>
</cms:SystemList>
