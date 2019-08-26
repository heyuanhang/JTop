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
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            	W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '190px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '新增发布URL规则成功!',
	
			    				ok: function()
			    				{
			    					W.location.reload();
			    				}
			   });
         }
         
         var ref_flag=/^[{}\w-/]{1,400}$/; 
         
        
         $(function()
		 {
		    validate('savePathRule',0,ref_flag,'格式为字母,数字,上下划线');
 			validate('fileNameRule',0,ref_flag,'格式为字母,数字,上下划线');	
 				
		 })
      
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/property.png" width="16" height="16" />规则信息
					</div>

					<form id="ruleForm" name="ruleForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="22%" class="input-title">
									<strong>发布类型分类</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="type" value="1" checked  onclick="javascript:changeType(this);"/>
									栏目和专题首页
									<input type="radio" name="type" value="2" onclick="javascript:changeType(this);"/>
									栏目列表页
									<input type="radio" name="type" value="3" onclick="javascript:changeType(this);"/>
									专题列表页
									<input type="radio" name="type" value="4" onclick="javascript:changeType(this);"/>
									内容页

								</td>
							</tr>




							<tr>
								<td class="input-title">
									<strong>发布页存储路径</strong>
								</td>
								<td class="td-input">
									<input id="savePathRule" name="savePathRule" type="text" class="form-input" style="width:375px" />
									<select id="pathRuleT1" style="display:" class="form-select" onchange="javascript:selectRule(this,'savePathRule')">
										<option value="-1">
											备选参数规则
										</option>
										
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										
										<option value="{class-id}">
											栏目ID
										</option>
										<option value="{class-flag}">
											栏目代码
										</option>
										<option value="{class-path}">
											栏目深度
										</option>
									</select><select id="pathRuleT2" style="display:none" class="form-select" onchange="javascript:selectRule(this,'savePathRule')">
										<option value="-1">
											备选参数规则
										</option>
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										
										<option value="{class-id}">
											栏目ID
										</option>
										<option value="{class-flag}">
											栏目代码
										</option>
										<option value="{class-path}">
											栏目深度
										</option>
									</select><select id="pathRuleT3" style="display:none" class="form-select" onchange="javascript:selectRule(this,'savePathRule')">
										<option value="-1">
											备选参数规则
										</option>
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										
										<option value="{class-id}">
											专题ID
										</option>
										 
										<option value="{type-id}">
											子分类ID
										</option>
										<option value="{comm-flag}">
											子分类标识
										</option>
									</select><select id="pathRuleT4" style="display:none" class="form-select" onchange="javascript:selectRule(this,'savePathRule')">
										<option value="-1">
											备选参数规则
										</option>
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										
										<option value="{class-id}">
											栏目ID
										</option>
										<option value="{class-flag}">
											栏目代码
										</option>
										<option value="{class-path}">
											栏目深度
										</option>
										<option value="{content-id}">
											内容ID
										</option>
										 
									</select>
									
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>



							<tr>
								<td valign="top" class="input-title">
									<strong>发布页文件名</strong>
								</td>
								<td class="td-input">
									<input id="fileNameRule" name="fileNameRule" type="text" class="form-input" style="width:375px" />
									<select id="fileRuleT1" class="form-select" onchange="javascript:selectRule(this,'fileNameRule')">
										<option value="-1">
											备选参数规则
										</option>
										
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										 
										<option value="{class-id}">
											栏目ID
										</option>
										<option value="{class-flag}">
											栏目代码
										</option>
										 								
									</select><select id="fileRuleT2" style="display:none" class="form-select" onchange="javascript:selectRule(this,'fileNameRule')">
										<option value="-1">
											备选参数规则
										</option>										
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										 
										<option value="{class-id}">
											栏目ID
										</option>
										<option value="{class-flag}">
											栏目代码
										</option>
										 									
									</select><select id="fileRuleT3" style="display:none" class="form-select" onchange="javascript:selectRule(this,'fileNameRule')">
										<option value="-1">
											备选参数规则
										</option>									
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
										 
										<option value="{class-id}">
											专题ID
										</option>
										 									
										<option value="{type-id}">
											子分类ID
										</option>	
										<option value="{comm-flag}">
											子分类标识
										</option>								
									</select><select id="fileRuleT4" style="display:none" class="form-select" onchange="javascript:selectRule(this,'fileNameRule')">
										<option value="-1">
											备选参数规则
										</option>
										<option value="{year}">
											当前年
										</option>
										<option value="{month}">
											当前月
										</option>
										<option value="{day}">
											当前日
										</option>
									 
										<option value="{class-id}">
											栏目ID
										</option>
										<option value="{class-flag}">
											栏目代码
										</option>
										 
										<option value="{seq}">
											随机码
										</option>
										<option value="{content-id}">
											内容ID
										</option>
										 
									</select>
							
									<span class="red">*</span><span class="ps"></span>
								</td>

							</tr>

							<tr>
								<td class="input-title">
									<strong>发布页后缀</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="suffixRule" value="1" checked />
									.html (默认发布名称)&nbsp;
									<input type="radio" name="suffixRule" value="2" />
									.shtml (支持碎片管理)
								</td>
							</tr>


						</table>
						<!-- hidden -->
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitRuleForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>


				</td>
			</tr>


		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitRuleForm()
{	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('savePathRule',0,ref_flag,'格式为字母,数字,上下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('fileNameRule',0,ref_flag,'格式为字母,数字,上下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
         
	    return;

	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var ruleForm = document.getElementById('ruleForm');
	
	ruleForm.action = '<cms:BasePath/>publish/createPubRule.do';
	
	ruleForm.submit();

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

function changeType(obj)
{
	var type = obj.value;
	
	$('#savePathRule').val('');
	$('#fileNameRule').val('');
	
	if(type == '1')
	{
		$('#pathRuleT1').show();
		
		$('#pathRuleT2').hide();
		$('#pathRuleT3').hide();
		$('#pathRuleT4').hide();
		
		$('#fileRuleT1').show();
		
		$('#fileRuleT2').hide();
		$('#fileRuleT3').hide();
		$('#fileRuleT4').hide();
		
	
	}
	else if(type == '2')
	{
		$('#pathRuleT2').show();
		
		$('#pathRuleT1').hide();
		$('#pathRuleT3').hide();
		$('#pathRuleT4').hide();
		
		$('#fileRuleT2').show();
		
		$('#fileRuleT1').hide();
		$('#fileRuleT3').hide();
		$('#fileRuleT4').hide();
	}
	else if(type == '3')
	{
		$('#pathRuleT3').show();
		
		$('#pathRuleT2').hide();
		$('#pathRuleT1').hide();
		$('#pathRuleT4').hide();
		
		$('#fileRuleT3').show();
		
		$('#fileRuleT2').hide();
		$('#fileRuleT1').hide();
		$('#fileRuleT4').hide();
	}
	else if(type == '4')
	{
		$('#pathRuleT4').show();
		
		$('#pathRuleT2').hide();
		$('#pathRuleT3').hide();
		$('#pathRuleT1').hide();
		
		$('#fileRuleT4').show();
		
		$('#fileRuleT2').hide();
		$('#fileRuleT3').hide();
		$('#fileRuleT1').hide();
	}
	
	


}
   

  
</script>
