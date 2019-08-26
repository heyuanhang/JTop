<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

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
			var hasError = false;
		//验证
		$(window).load(function()
		{
			$("#taskName").bind('focus', function() 
			{
				var target = $("#taskName").val();
				//alert(target);
			    if(target == '')
				{
					hasError = true;
  					showTips('taskName','不可为空');
  				}
  				else
  				{
  					hasError = false;
  				}
			});	
			
			$("#taskName").bind('propertychange', function() 
						{
						   $( 'div.taskName_jtop_ui_tips_class' ).remove();
  							
							var target = $("#taskName").val();

    						if(target == '')
    						{
    							hasError = true;
    							showTips('taskName','不可为空');               					
  							}
  							else
  							{
  								hasError = false;
  							}
  							
  							
						});
						
						
			
		
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
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
         }
         
        </script>
	</head>
	<body>
		<cms:CurrentSite> 
		<cms:SystemList querySign="SELECT_PICK_TASK_SINGLE_QUERY" objName="Task" var="${param.taskId}">
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/blue/icon/application-import.png" width="16" height="16" />
							任务信息
						</div>

						<form id="pickTaskForm" name="pickTaskForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="26%" class="input-title">
										<strong>任务名</strong>
									</td>
									<td class="td-input">
										<input id="taskName" name="taskName" type="text" class="form-input" style="width:310px" value="${Task.taskName}"/>
										<span class="red">*</span>
									</td>
								</tr>
								
								<tr>
									<td   class="input-title">
										<strong>采集模式</strong>
									</td>
									<td class="td-input">
										<input name="pickType" type="radio" class="form-input" value="1" onchange="javascript:change();"/>网络&nbsp;&nbsp;&nbsp;
										<input name="pickType" type="radio" class="form-input" value="2" onchange="javascript:change();"/>第三方接口&nbsp;&nbsp;&nbsp;
										<input name="pickType" type="radio" class="form-input" value="3" onchange="javascript:change();"/>数据库
										 
									</td>
								</tr>

								<tr id="hMode" >
									<td class="input-title">
										<strong>采集规则</strong>
									</td>
									<td class="td-input">
										<select class="form-select" id="ruleId" name="ruleId" style="width:314px">
											<option value="-1">
												----------------- 请选则采集规则 -----------------
											</option>

											<cms:SystemList querySign="SELECT_PICK_RULE_LIST_QUERY" var="${CurrSite.siteId}">
												<option value="${SysObj.pickCfgId}">
													${SysObj.configName}
												</option>
											</cms:SystemList>

										</select>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								
								<tr id="rssMode">
									<td  class="input-title">
										<strong>接口地址</strong>
									</td>
									<td class="td-input">
										<input id="rssUrl" name="rssUrl" type="text" class="form-input" style="width:310px" value="${Task.rssUrl}"/>
									 
									</td>
								</tr>
								
								<tr id="dpClass">
									<td class="input-title">
										<strong>数据处理类</strong>
									</td>
									<td class="td-input">
									<input id="dbRuleBeClass" name="dbRuleBeClass" type="text" class="form-input" style="width:310px" value="${Task.dbRuleBeClass}"/>
									 
									 		</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>目标栏目</strong>
									</td>
									<td class="td-input">

										<select class="form-select" id="classId" name="classId" style="width:314px">
											<option value="-1">
												----------------- 请选则目标栏目 -----------------
											</option>
											<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
											</cms:CurrentSite>
											

										</select>
									</td>
								</tr>
								
								<tr id="maxClass">
									<td class="input-title">
										<strong>数量控制</strong>
									</td>
									<td class="td-input">
										采集前
										<input id="pickMaxListPage" name="pickMaxListPage" type="text" class="form-input" size="6" value="${Task.pickMaxListPage}"/>
										&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每页采集
										<input id="pickMaxContent" name="pickMaxContent" type="text" class="form-input" size="6" value="${Task.pickMaxContent}"/>
										&nbsp;篇
									</td>
								</tr>
								
								
								
								<tr>
									<td class="input-title">
										<strong>入库状态</strong>
									</td>
									<td class="td-input">
										<input type="radio" name="censorMode" value="0" checked />
										立即直接发布文章&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" name="censorMode" value="1" />
										需要人工进行筛选
									</td>
								</tr>
								
								<tr>
									<td class="input-title">
										<strong>引导图片</strong>
									</td>
									<td class="td-input">
										将下载的第一张图片作为 
										<select id="guideImgPos" name="guideImgPos" class="form-select">
										<option value="-1" >
											----无需引导图----
										</option>
										<option value="1" >
											 首页引导图 
										</option>
										<option value="2" >
											 频道引导图 
										</option>
										<option value="3" >
											 栏目引导图 
										</option>
										<option value="4" >
											 内容引导图 
										</option>
									</select>
									</td>
								</tr>
<%--
								<tr>
									<td class="input-title">
										<strong>外站图片</strong>
									</td>
									<td class="td-input">
										<input name="downOutImg" type="radio" class="form-radio" value="0"/>
										保留原图片地址&nbsp;
										<input name="downOutImg" type="radio" class="form-radio" value="1"/>
										下载图片到系统服务器&nbsp;									
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>外链规则</strong>
									</td>
									<td class="td-input">
										<input name="deleteHref" type="radio" class="form-radio" value="0"/>
										保留出现的外部链接&nbsp;
										<input name="deleteHref" type="radio" class="form-radio" value="1" />
										清除所有外部链接&nbsp;
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>采集参数</strong>
									</td>
									<td class="td-input">
										线程数
										<input id="pickThreadCount" name="pickThreadCount" type="text" class="form-input" size="6" value="${Task.pickThreadCount}"/>
										&nbsp;个 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 间隔时间
										<input id="pickInterval" name="pickInterval" type="text" class="form-input" size="6" value="${Task.pickInterval}"/>
										&nbsp;秒
									</td>
								</tr>
								--%>
								<tr>
									<td class="input-title">
										<strong>自动采集周期</strong>
									</td>
									<td class="td-input">
										每
									<input type="text" id="period" name="period" size="4" class="form-input" value="${Task.period}"/>
									<select id="periodType" name="periodType" class="form-select">
										<option value="2" >
											分钟 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="3" >
											小时&nbsp;&nbsp;&nbsp;
										</option>
										<option value="4" >
											天&nbsp;&nbsp;&nbsp;
										</option>
									</select>
									采集一次 (0值为手动)
									</td>
								</tr>
								
								

								<tr>
									<td class="input-title">
										<strong>任务描叙</strong>
									</td>
									<td class="td-input">
										<textarea id="taskDesc" name="taskDesc" style="height:60px;width:310px" class="form-textarea">${Task.taskDesc}</textarea>
									</td>
								</tr>

								

							</table>
							<!-- hidden -->
							<input type="hidden" name="pickTaskId" id="pickTaskId" value="${Task.pickTaskId}" />
							
							<cms:Token mode="html"/>
						</form>
						<div style="height:20px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitPickRuleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
									</div>
									 
								</tr>
							</table>
						</div>
					</td>
				</tr>

				<tr>
					<td height="10">
						&nbsp;
					</td>
				</tr>
			</table>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

initRadio('pickType','${Task.pickType}');
initSelect('ruleId','${Task.ruleId}');
initSelect('classId','${Task.classId}');
initSelect('periodType','${Task.periodType}');

initRadio('downOutImg','${Task.downOutImg}');
initSelect('guideImgPos','${Task.guideImgPos}');
initRadio('deleteHref','${Task.deleteHref}');
initRadio('censorMode','${Task.censorMode}');

function change()
{
	var type = getRadioCheckedValue('pickType');
	
	if(type == 1)//web
	{
		$('#hMode').show();
	 	$('#rssMode').hide();
	 	$('#dpClass').hide();
	 	$('#maxClass').show();
	 	
	}
	else if(type == 2)//rss
	{
	 	$('#hMode').hide();
	 	$('#rssMode').show();
	 	$('#dpClass').show();
	 	$('#maxClass').hide();
	}
	else if(type == 3)//db
	{
	 	$('#hMode').hide();
	 	$('#rssMode').hide();
	 	$('#dpClass').show();
	 	$('#maxClass').hide();
	}

	
}

//init
change();

function close()
{
	api.close();
}

function submitPickRuleForm()
{    

	
	
	
	var target = $("#taskName").val();
				if(target == '')
				{
						showTips('taskName','不可为空');
						hasError = true;
			    }
			    else
			    {
			    		hasError = false;
			    }

    
    if(hasError)
    {
		showErrorMsg('包含未正确填写的数据,请参照提示填写正确!');
	}
	else
	{
	
	document.getElementById('taskName').value = replaceAll(document.getElementById('taskName').value, "'", '');
	
	encodeFormInput('pickTaskForm', false);
   
    var form = document.getElementById('pickTaskForm');
	
	
    form.action="<cms:BasePath/>pick/editPickTask.do";
    
    form.submit(); 
    }
}


  
</script>
</cms:SystemList>
</cms:CurrentSite>
