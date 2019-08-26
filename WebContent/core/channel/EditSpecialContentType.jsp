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
		basePath='<cms:BasePath/>';
		
		 var api = frameElement.api, W = api.opener;
		 
		 var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('commendName',0,ref_name,'格式为文字,数字,下划线');
 			validate('commFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 }) 
		 
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
							<img src="../style/icons/folder-tree.png" width="16" height="16" />配置
					</div>
					
					<cms:SystemCommendType typeId="${param.typeId}">
					<form id="typeForm" name="typeForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

							<tr>
								<td width="21%" class="input-title">
									<strong>分类名称: 
								</td>
								<td  class="td-input">
									<input id="commendName" name="commendName" type="text" class="form-input" style="width: 385px;" value="${CommendType.commendName}"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>访问标识: 
								</td>
								<td class="td-input">
									<input id="commFlagShow" disabled type="text" class="form-input" style="width: 385px;" value="${CommendType.commFlag}" />
										<input id="commFlag" name="commFlag" type="hidden" class="form-input"  value="${CommendType.commFlag}" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr id="pc-show" style="display:none">
								<td class="input-title">
									<strong>默认列表模板: 
								</td>
								<td class="td-input">
									<input type="text" style="width: 304px;" id="listTemplateUrl" name="listTemplateUrl" class="form-input" value="${CommendType.listTemplateUrl}"></input>

									<select class="form-select" onchange="javascript:selectRule(this,'listTemplateUrl')">
										<option value="-1">
											备选参数
										</option>

										<option value="{class-id}">
											专题ID
										</option>
										<option value="{type-id}">
											自身ID
										</option>
									</select>
									<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('spec','', 'pc');" />
								</td>
							</tr>
							
							<tr id="mob-show" style="display:none">
								<td class="input-title">
									<strong>Mob列表模板: 
								</td>
								<td class="td-input">
									<input type="text" style="width: 304px;" id="mobListTemplateUrl" name="mobListTemplateUrl" class="form-input" value="${CommendType.mobListTemplateUrl}"></input>

									<select class="form-select" onchange="javascript:selectRule(this,'mobListTemplateUrl')">
										<option value="-1">
											备选参数
										</option>

										<option value="{class-id}">
											专题ID
										</option>
										<option value="{type-id}">
											自身ID
										</option>
									</select>
									<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('spec','', 'mob');" />
								</td>
							</tr>
							
							<tr id="pad-show" style="display:none">
								<td class="input-title">
									<strong>Pad列表模板: 
								</td>
								<td class="td-input">
									<input type="text" style="width: 304px;" id="padListTemplateUrl" name="padListTemplateUrl" class="form-input" value="${CommendType.padListTemplateUrl}"></input>

									<select class="form-select" onchange="javascript:selectRule(this,'padListTemplateUrl')">
										<option value="-1">
											备选参数
										</option>

										<option value="{class-id}">
											专题ID
										</option>
										<option value="{type-id}">
											自身ID
										</option>
									</select>
									<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('spec','', 'pad');" />
								</td>
							</tr>
							
							<script>
													<cms:CurrentSite>
													 	
													if('${CurrSite.pcVm}' == '1')
													{
														$('#pc-show').show();
													}
													if('${CurrSite.mobVm}' == '1')
													{
														$('#mob-show').show();
													}
													if('${CurrSite.padVm}' == '1')
													{
														$('#pad-show').show();
													}
																							
													</cms:CurrentSite>
												</script>

							<tr>
								<td class="input-title">
									<strong>发布规则: 
								</td>
								<td class="td-input">
									<cms:SystemPublishRule id="${CommendType.listPublishRuleId}">
									<input type="text" style="width: 385px;" id="listPublishRuleIdShow" class="form-input" value="${Rule.ruleName}" readonly></input>
									</cms:SystemPublishRule>
									<input type="hidden" id="listPublishRuleId" name="listPublishRuleId" value="${CommendType.listPublishRuleId}"></input>
									<input type="button" value="规则..." class="btn-1" onclick="javascript:openSelectPublishRuleDialog('3','listPublishRuleId');" />
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>列表页静态化: 
								</td>
								<td class="td-input">
									<input name="listProduceType" type="radio" value="1" checked />
									关闭&nbsp;
									<input name="listProduceType" type="radio" value="3" />
									开启&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>编辑推荐内容:</strong></span>
									<input name="mustCensor" type="radio" value="0" checked />
									立即生效&nbsp;
									<input name="mustCensor" type="radio" value="1" />
									需要审核&nbsp;

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>引导图规格: 
								</td>
								<td class="td-input">
									<input type="text" size="6" id="imageWidth" name="imageWidth" class="form-input" value="${CommendType.imageWidth}"></input>
									宽度(px) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" size="6" id="imageHeight" name="imageHeight" class="form-input" value="${CommendType.imageHeight}"></input>
									高度(px)
								</td>
							</tr>


						</table>
						<!-- hidden -->
						<input type="hidden" id="commendTypeId" name="commendTypeId" value="${param.typeId}" />
						<input type="hidden" id="classId" name="classId" value="${param.classId}" />
						<input type="hidden" id="childClassMode" name="childClassMode" value="1" />
						<input type="hidden" id="mustCensor" name="mustCensor" value="0" />
						<input type="hidden" id="isSpec" name="isSpec" value="1" />

					</form>
					<div style="height:16px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<div style="float:right">
								<a id="submitFormBut" href="javascript:submitTypeForm();"  class="btnwithico"><img id="submitFormImg" src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
							</div>
							 
						</tr>
					</table>
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

initRadio('listProduceType','${CommendType.listProduceType}');

initRadio('mustCensor','${CommendType.mustCensor}');
 
function close()
{
	api.close();
}

function submitTypeForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('commFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('commendName',0,ref_name,'格式为文字,数字,下划线');

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
	if('${CommendType.commFlag}' != $('#commFlag').val())
	{
		var count = validateExistFlag('commType', $('#commFlag').val());
		
		if(count > 0)
		{

			showTips('commFlagShow', '系统已存在此值，不可重复录入');
			
			return;
		}
	}
		
   $("#submitFormBut").attr("disabled","disabled"); 
   $("#submitFormImg").attr("disabled","disabled"); 
	
	var url = "../../channel/editCommendType.do"+"?<cms:Token mode='param'/>";
 	var postData = encodeURI($("#typeForm").serialize());
 	
 	postData = postData.replace(/\+/g, " ");
	postData = encodeData(postData);
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(mg)
        {     
        
        	var msg = eval("("+mg+")");
        	
           if('success' == msg)
           {
           	 W.$.dialog.tips('修改专题分类成功...',1);
           	
           	 api.get('oessd').window.location.href = api.get('oessd').window.location.href+ '&tab=3';             
           	 
           	  close(); 		
           } 	
           else
           {
           	    W.$.dialog.tips('修改专题分类失败...',1);
           }   
        }
 	});	
 	
   
}

function openSelectTempletDialog(mode, objId, vm)
{
      var targetName = '';
      
      var vmName = 'PC端';
      
       
      if('mob' == vm)
      {
      	vmName = 'Mob端';
      }
      else if('pad' == vm)
      {
      	vmName = 'Pad端';
      }
      
      if('channel' == mode)
      {
      	targetName = vmName+'栏目首页';
      }
      else if('class' == mode)
      {
      	targetName = vmName+'列表页';
      }
      else if('content' == mode)
      {
      	targetName = vmName+'内容页';
      }
      else if('home' == mode)
      {
      	targetName = vmName+'首页';
      }
       
	  W.$.dialog({ 
		id:'ostd',
    	title :'选择'+targetName+'模版',
    	width: '700px', 
    	height: '466px', 
    	parent:api,
    	lock: true, 
        max: false, 
         min: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/channel/SelectChannelTemplet.jsp?mode='+mode+'&apiId=oesctd'+'&objId='+objId+'&vm='+vm
	});
}


function openSelectPublishRuleDialog(type,idName)
{
	W.$.dialog({ 
		id:'osprd',
    	title :'选择发布规则',
    	width: '500px', 
    	height: '300px', 
    	parent:api,
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
             
        content: 'url:<cms:Domain/>core/channel/SelectPublishRule.jsp?type='+type+'&idName='+idName+'&apiId=oesctd'
	});
}

function selectRule(rule,target)
{
   var fileRuleName = document.getElementById(target);
   
   var selection = document.selection;
                                
   fileRuleName.focus();
                                
   if (typeof fileRuleName.selectionStart != "undefined")
   {
    	var s = fileRuleName.selectionStart;
         fileRuleName.value = fileRuleName.value.substr(0, fileRuleName.selectionStart) + rule.value + fileRuleName.value.substr(fileRuleName.selectionEnd);
         fileRuleName.selectionEnd = s + rule.value.length;
    } 
    else if (selection && selection.createRange) 
    {
         var sel = selection.createRange();
         sel.text = rule.value;
    } else 
    {
         fileRuleName.value += rule.value;
    }
   
   
   var opts = rule.options;
   
   for(var i=0; i<opts.length; i++)
   {
   		if(opts[i].value == '-1')
   		{
   			opts[i].selected=true;
   			break;
   		}	
   }
}


   
  
</script>
</cms:SystemCommendType>
