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
		 
         if("true"==="${param.fromFlow}")
         {     	 	
       		W.window.parent.location="ManageContentClass.jsp?redirect=true&flag=1&classId=${param.classId}";
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('classNameInfo',1,null,null);
 		
 				
		 })
       
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/folder-horizontal-open.png" width="16" height="16" />栏目信息
					</div>

					<form id="classForm" name="classForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="24%" class="input-title">
									<strong>上级栏目</strong>
								</td>
								<td  class="td-input">
									<input type="text" style="width:315px;"  class="form-input" value='${param.classId}: <cms:DecodeParam enc="utf-8" str="${param.className}"/>' disabled></input>
								</td>
							</tr>							
							
							<tr>
								<td  class="input-title">
									<strong>栏目名称</strong>
								</td>
								<td  class="td-input">
									<textarea style="width:313px; height:180px;" id="classNameInfo" name="classNameInfo" class="form-textarea"></textarea>
									<span class="red">*</span>
									<br/><span class="ps">格式为 "栏目名@标识" ,多栏目间用回车换行分隔 </span>
								</td>
							</tr>
							<%--<tr>
								<td class="input-title">
									<strong>访问标识</strong>
								</td>
								<td class="td-input">
									<textarea style="width:265px; height:60px;" id="classFlag" name="classFlag" class="form-textarea"></textarea>
									<span class="red">*</span><span class="ps"></span>
									<br/><span class="ps">多个栏目标识必须和以上栏目名称按照顺序对应</span>
								</td>
							</tr>
							
							--%><tr>
								<td class="input-title">
									<strong>同步使用配置</strong>
								</td>
								<td class="td-input">
									<select style="width:318px;"  class="form-select" id="targetCfgClassId" name="targetCfgClassId" onchange="javascript:change(this);">
										<option value="-1">
											-----------------&nbsp;选择源配置栏目&nbsp;----------------
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
									<%--<br/>
									<span class="ps">若选择了同步配置栏目,新添加栏目将使用其配置值</span>
								--%></td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>类型</strong>
								</td>
								<td class="td-input">
									<input name="classType" type="radio" onclick="javascript:;" checked value="1" />
									<span class="STYLE12">一般栏目</span> &nbsp;																
									<input name="classType" type="radio" onclick="javascript:;" value="3" />
									<span class="STYLE12">单页展示</span> &nbsp;
									<input name="classType" type="radio" onclick="javascript:;" value="2" />
									<span class="STYLE12">外链</span> &nbsp;
								</td>
							</tr>




							<tr>
								<td class="input-title">
									<strong>信息模型</strong>
								</td>
								<td class="td-input">
									<select style="width:318px;" id="contentType" name="contentType" class="form-select" >
										<option value="-1">
											------------------ 请选内容模型 ------------------
										</option>

										<cms:SystemDataModelList modelType="2">
											<cms:SystemDataModel>
												<option value="${DataModel.dataModelId}">
													${DataModel.modelName}&nbsp;
												</option>
											</cms:SystemDataModel>
										</cms:SystemDataModelList>
									</select>
									<span class="red">*</span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>工作流</strong>
								</td>
								<td class="td-input">
									<cms:SystemWorkflowList>
										<select style="width:318px;" id="workflowId" name="workflowId" class="form-select" >
											<option value="-1">
												------------------ 直接发布内容 ------------------
											</option>
											<cms:SystemWorkflow>
												<option value="${Workflow.flowId}">
													${Workflow.flowName}&nbsp;
												</option>
											</cms:SystemWorkflow>
										</select>
									</cms:SystemWorkflowList>
									
								</td>
							</tr>
							
							
							


						</table>
						<!-- hidden -->
						<input type="hidden" id="parent" name="parent" value="${param.classId}"/>
						
						<cms:Token mode="html"/>
						
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
						<tr class="btnbg100">
							<div style="float:right">
								<a name="btnwithicosysflag" href="javascript:submitContentClassForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();" class="btnwithico"  ><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
   
   
function submitContentClassForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('classNameInfo',1,null,null);
        
        if(currError)
        {
        	hasError = true;
        }
        
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;
	}
	
	var msg = validateExistFlag('mutiContentClass', $('#classNameInfo').val());
		
		if('' != msg)
		{
			
			showTips('classNameInfo', "以下标识已存在:<br/>"+msg);
			
			return;
		}
	
	W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '200px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '注意:不符合命名规范的栏目添加将被忽略!',
	
			    				ok: function()
			    				{
			    					disableAnchorElementByName("btnwithicosysflag",true);
			    					
			    					encodeFormInput('classForm', false);
			    				
			    					var classForm = document.getElementById('classForm');
									classForm.action="<cms:BasePath/>channel/createChannel.do?classId=${param.classId}";
									classForm.submit();
			    				},
			    				cancel: true
	});
   
}

   
function showSelectTempletDialog(mode)
{
	   var templetValue;	
	   var targetTemplet = window.showModalDialog("../channel/SelectChannelTemplet.jsp?random="+Math.random(),"","dialogWidth=950px;dialogHeight=500px;status=no");
	   
       if(targetTemplet=="")
       {
       		return;
       }
       
       templetValue = targetTemplet.replaceAll('\\*','/');
       
     
       
       document.getElementById("templateUrl").value=templetValue;
       
}
function switchType()
{
	var typeRadio = document.getElementsByName('classType');
	
	for(var i = 0; i < typeRadio.length; i++)
	{
		if(typeRadio[i].checked == true)
		{
			if(typeRadio[i].value == 1)
			{
				$('#contentType').removeAttr("disabled"); 
				$('#workflowId').removeAttr("disabled"); 
			}
			else
			{
				$('#contentType').attr("disabled", ""); 
				$('#workflowId').attr("disabled", ""); 
			}
		}
	}
}

function change(obj)
{
	if(obj.value != -1)
	{
		$('input[name=classType]').attr('disabled','disabled');
		
		$('#contentType').attr('disabled','disabled');
		
		$('#workflowId').attr('disabled','disabled');	
	}
	else
	{
		$('input[name=classType]').removeAttr('disabled');
		
		$('#contentType').removeAttr('disabled');
		
		$('#workflowId').removeAttr('disabled');
	}
	

}
  
</script>
