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
         		 W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '190px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '添加规则成功！可在编辑时测试规则是否有效',
	
			    				ok: function()
			    				{
			    					 
						          
						             W.location.reload();
			    				}
				});
	            
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>

		

		<form id="pickRuleForm" name="pickRuleForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top:2px">
							<ul>
								<li id="two1" onclick="setTab('two',1,2)" class="selectTag">
									<a href="javascript:;"><img src="../style/icons/validation-valid-document.png" width="16" height="16" />配置信息&nbsp;</a>
								</li>
								<%--<li id="two2" onclick="setTab('two',2,2)">
									<a href="javascript:;"><img src="../style/icons/document-xaml.png" width="16" height="16" />数据匹配&nbsp;</a>
								</li>




							--%></ul>
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
													<input type="text" style="width:500px" id="configName" name="configName" class="form-input"></input>
													<span class="red">*</span><span class="ps"></span>
												</td>
											</tr>
											
											<tr>
												<td class="input-title">
													<strong>列表首页URL</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:500px" id="listHeadUrlRule" name="listHeadUrlRule" class="form-input"></input>
													<br />
													<span class="ps">列表首页URL为列表第一页无数字区分的情况,一般作为列表入口页,若不存在此情况,则不需填写</span>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>列表动态页URL</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:500px" id="listUrlRule" name="listUrlRule" class="form-input"></input>
													<br />
													<span class="ps">动态值如 index_{1-100}.html 对应为 1~100 页或 01~100 页,也可为倒序访问如 {100~1}</span>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>内容页URL</strong>
												</td>
												<td class="td-input">
													<textarea id="contentUrlRule" name="contentUrlRule" style="height:55px;width:500px" class="form-textarea"></textarea>
													<br />
													<select class="form-select" onchange="javascript:selectRule(this,'contentUrlRule')">
														<option value="-1">
															-- 动态值 --
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

											<%--<tr>
												<td class="input-title">
													<strong>内容分页URL</strong>
												</td>
												<td class="td-input">
													<textarea id="contentPageUrlRule" name="contentPageUrlRule" style="height:40px;width:500px" class="form-textarea"></textarea>
													<br />
													<select class="form-select" onchange="javascript:selectRule(this,'contentUrlRule')">
														<option value="-1">
															-- 动态值 --
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
													<input type="text" style="width:500px" id="prefixSiteUrl" name="prefixSiteUrl" class="form-input"></input>
													<br />
													<span class="ps">当内容页链接为相对URL时,系统会自动使用前缀补全为完整URL</span>
												</td>
											</tr>
											
											<tr>
												<td class="input-title">
													<strong>html模式</strong>
												</td>
												<td class="td-input">
													<input type="radio" id="htmlMode" name="htmlMode"  class="form-radio" value="1" checked/>是&nbsp;
													<input type="radio" id="htmlMode" name="htmlMode"  class="form-radio" value="0"/>否
													<span class="ps">当为html模式时，采集到的信息将保留所有html代码，否则只存储无格式文本信息</span>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>规则描叙</strong>
												</td>
												<td class="td-input">
													<textarea id="configDesc" name="configDesc" style="height:65px;width:500px" class="form-textarea"></textarea>
												</td>
											</tr>



											<!-- 以下为独立选项 start -->


										</table>

										<div style="height:50px"></div>
										<div class="breadnavTab" >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a name="btnwithicosysflag" href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>
									</li>
								</ul>
							</div>

							<!-- 第二部分:匹配数据 -->
							<%--<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">

								<ul>
									<li>

										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


											<tr>
												<td width="14%" class="input-title">
													<strong>标题</strong>
												</td>
												<td class="td-input">
													<textarea id="titleStart" name="titleStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="titleEnd" name="titleEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
													<br/><br/>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>正文</strong>
												</td>
												<td class="td-input">
													<textarea id="contentStart" name="contentStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="contentEnd" name="contentEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
													<br/><br/>
												</td>
											</tr>


											<tr>
												<td class="input-title">
													<strong>摘要</strong>
												</td>
												<td class="td-input">
													<textarea id="summaryStart" name="summaryStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="summaryEnd" name="summaryEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
													<br/><br/>
												</td>
											</tr>



											 <tr>
												<td class="input-title">
													<strong>录入时间</strong>
												</td>
												<td class="td-input">
													<textarea id="addDateStart" name="addDateStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="addDateEnd" name="addDateEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
												</td>
											</tr>

											 <tr>
												<td class="input-title">
													<strong>文章来源</strong>
												</td>
												<td class="td-input">
													<textarea id="sourceStart" name="sourceStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="sourceEnd" name="sourceEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
													<br/><br/>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>作者</strong>
												</td>
												<td class="td-input">
													<textarea id="authorStart" name="authorStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="authorEnd" name="authorEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
													<br/><br/>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>Tag词</strong>
												</td>
												<td class="td-input">
													<textarea id="keywordStart" name="keywordStart" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">开始</span>
													<br />
													<textarea id="keywordEnd" name="keywordEnd" style="height:65px;width:578px" class="form-textarea"></textarea>
													<span class="ps">结束</span>
													<br/><br/>
												</td>
											</tr>



											--%><!-- 以下为独立选项 start -->


										</table>

										<div style="height:20px"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<td class="input-title" width="79%"></td>
													<td class="td-input">
														<a name="btnwithicosysflag" href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
													</td>
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
			<input type="hidden" name="posConfigCount" id="posConfigCount" value="4" />

			<input type="hidden" name="conConfigCount" id="conConfigCount" value="4" />
			
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
				if(target == '')
				{
						hasError = true;
			    }
			    else
			    {
			    		hasError = false;
			    }

    
    if(hasError)
    {
    	setTab('two',1,2);
    	showTips('configName','不可为空');
		//showErrorMsg('包含未正确填写的数据,请参照提示填写正确!');
		
		return;
	}
	 
	disableAnchorElementByName("btnwithicosysflag",true);
		
	 
	encodeFormInput('pickRuleForm', false);
   
    var form = document.getElementById('pickRuleForm');
	
	
    form.action="<cms:BasePath/>pick/createPickRule.do";
    
    form.submit(); 
    
}



</script>

