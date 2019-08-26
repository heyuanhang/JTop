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
            W.$.dialog(
			 { 
			   					title :'提示',
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '编辑留言类型成功!',
	
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
		    validate('cfgName',0,ref_name,'格式为文字,数字,下划线');
 			validate('cfgFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
      
      	</script>
	</head>
	<body>

		<cms:SystemGbConfig configId="${param.configId}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/sockets.png" width="16" height="16" />配置
					</div>
					
					<form id="cofigForm" name="cofigForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="26%" class="input-title">
									<strong>留言板名称</strong>
								</td>
								<td class="td-input">
									<input id="cfgName" name="cfgName" type="text" class="form-input" style="width:292px" value="${GbCfg.cfgName}"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>访问标识</strong>
								</td>
								<td class="td-input">
									<input id="cfgFlag" name="cfgFlag" type=" text" class="form-input" style="width:292px" value="${GbCfg.cfgFlag}"/>
									<span class="red">*</span><span class="ps"></span>
								</td>

							</tr>



							<tr>
								<td valign="top" class="input-title">
									<strong>配置备注</strong>
								</td>
								<td class="td-input">
									<textarea id="cfgDesc" name="cfgDesc" style="width:290px;height:50px" class="form-textarea">${GbCfg.cfgDesc}</textarea>
								</td>

							</tr>

							<tr>
								<td class="input-title">
									<strong>必须登陆</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="mustLogin" value="0" checked />
									游客可留言&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="mustLogin" value="1" />
									只有会员允许
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>管理员审核</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="mustCensor" value="1" checked />
									留言必须审核&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="mustCensor" value="0" />
									直接发布显示
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>验证码</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="needVerifyCode" value="1" checked />
									强制需要验证&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="needVerifyCode" value="0" />
									不需要验证
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>留言标题</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="mustHaveTitle" value="1" checked />
									必须输入&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="mustHaveTitle" value="0" />
									可以为空行
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>使用状态</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="useState" value="1" checked />
									可以留言&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="useState" value="0" />
									关闭禁用
								</td>
							</tr>
							

							<tr>
								<td class="input-title">
									<strong>留言板扩展信息</strong>
								</td>
								<td class="td-input">
									<select id="infoModelId" name="infoModelId" class="form-select" style="width:294px">
										<option value="-1">
											--------------- 请选留言扩展模型 ---------------
										</option>

										<cms:SystemDataModelList modelType="4">
											<cms:SystemDataModel>
												<option value="${DataModel.dataModelId}">
													${DataModel.modelName}&nbsp;
												</option>
											</cms:SystemDataModel>
										</cms:SystemDataModelList>
									</select>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>工作流</strong>
								</td>
								<td class="td-input">
									<cms:SystemWorkflowList>
																<select id="workflowId" name="workflowId" class="form-select" style="width:294px">
																	<option value="-1">
																	--------------- 请选留言工作流 ---------------
																	</option>
																	<cms:SystemWorkflow>
																		<option value="${Workflow.flowId}">
																			${Workflow.flowName} &nbsp;
																		</option>
																	</cms:SystemWorkflow>
																</select>
									</cms:SystemWorkflowList>
								</td>
							</tr>

						</table>
						<!-- hidden -->
						<input type="hidden" id="configId" name="configId" value="${GbCfg.configId}"/>
						
						<cms:Token mode="html"/>
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitGbCofigForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

initRadio('mustLogin','${GbCfg.mustLogin}');
initRadio('mustCensor','${GbCfg.mustCensor}');
initRadio('needVerifyCode','${GbCfg.needVerifyCode}');
initRadio('mustHaveTitle','${GbCfg.mustHaveTitle}');
initRadio('useState','${GbCfg.useState}');
initSelect('infoModelId','${GbCfg.infoModelId}');
initSelect('workflowId','${GbCfg.workflowId}');

   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitGbCofigForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('cfgFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('cfgName',0,ref_name,'格式为文字,数字,下划线');

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
	
	if('${GbCfg.cfgFlag}' != $('#cfgFlag').val())
	{
		var count = validateExistFlag('guestbookCfg', $('#cfgFlag').val());
		
		if(count > 0)
		{

			showTips('cfgFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
	}
	
	encodeFormInput('cofigForm', false);
	
	var cofigForm = document.getElementById('cofigForm');
	
	cofigForm.action = '<cms:BasePath/>guestbook/editGbConfig.do';
	
	cofigForm.submit();

}
    
</script>
</cms:SystemGbConfig>
