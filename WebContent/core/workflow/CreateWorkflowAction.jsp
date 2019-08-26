<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		<script>  
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog.tips('新增工作流动作成功',2,'32X32/succ.png'); 
            api.close(); 
         	api.reload( api.get('cwa') );    
       		       
         }
         
          var cuac = new Array();
         
         //当前所有action
		<cms:QueryData objName="CUAC" service="cn.com.mjsoft.cms.workflow.service.WorkflowService" method="getWorkflowActionForTag" var="${param.flowId}">
			
			cuac['${CUAC.fromStepId}-${CUAC.toStepId}'] = 'true';
			
		</cms:QueryData>
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('passActionName',0,ref_name,'格式为文字,数字,下划线');
 			
 				
		 })
       
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/flask--plus.png" width="16" height="16" />动作信息
					</div>

					<form id="flowActionForm" name="flowActionForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="21%" class="input-title">
									<strong>名称 
								</td>
								<td class="td-input">
									<input id="passActionName" name="passActionName" type="text" class="form-input" style="width:374px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="actDesc" name="actDesc" class="form-textarea" style="width:375px; height:60px;"></textarea>
							</tr>

							<tr>
								<td class="input-title">
									<strong>步骤流向 
								</td>
								<td class="td-input">
									<select name="fromStepId" id="fromStepId" style="width:173px">
										<option value="-9999999" selected >
											-----请选起始步骤-----
										</option>
										
										<cms:SystemWorkflowStep flowId="${param.flowId}" >
											<option id="from-${Step.stepId}" value="${Step.stepId}">
												${Step.stepNodeName}
											</option>
										</cms:SystemWorkflowStep>

									</select>
									&nbsp;
									<img src="<cms:Domain/>core/style/icons/arrow-skip.png"></img>
									&nbsp;

									<select name="toStepId" id="toStepId" style="width:173px">
										<option value="-9999999" selected >
											-----请选目标步骤-----
										</option>
										
										<cms:SystemWorkflowStep flowId="${param.flowId}" >
											<option id="to-${Step.stepId}" value="${Step.stepId}">
												${Step.stepNodeName}
											</option>
										</cms:SystemWorkflowStep>
										
										<option value="-5">
												否决(请求不通过)
										</option> 
										
										<option value="-1">
											结束(通过审核)
										</option>
										<option value="-4">
											退稿(原始草稿)
										</option>
									</select>

								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>强制指定:</strong>
								</td>
								<td class="td-input">
									<input name="directMode" type="radio" value="0" checked />
									否&nbsp;
									<input name="directMode" type="radio" value="1" />
									是&nbsp;
									<span class="ps">此模式下执行当前动作前必须先指定下一处理人</span>
								</td>
							<tr>


							<tr>
								<td class="input-title">
									<strong>会签模式:</strong>
								</td>
								<td class="td-input">
									<input name="conjunctManFlag" type="radio" value="0" checked />
									否&nbsp;
									<input name="conjunctManFlag" type="radio" value="1" />
									是&nbsp;
									<span class="ps">此模式起始步骤参与人员须全部执行此动作才审核生效</span>
								</td>
							<tr>
							<tr>
								<td class="input-title">
									<strong>机构主管模式:</strong>
								</td>
								<td class="td-input">
									<input name="orgBossMode" type="radio" value="0" checked />
									否&nbsp;
									<input name="orgBossMode" type="radio" value="1" />
									是&nbsp;
									<span class="ps">在此模式下，机构主管具有机构审核直接通过和否决权</span>
								</td>
							</tr>
							
							

							 
							<%--		
							<tr>
									<td class="input-title">
										<strong>可撤回模式:</strong>
									</td>
									<td class="td-input">
										<input name="recallMode" type="radio" value="0" checked />
										否&nbsp;
										<input name="recallMode" type="radio" value="1" />
										是&nbsp;
										<span class="ps">在此模式下，当前步骤没有审核的情况下可被上一操作人撤回</span>
									</td>
									 
								</tr>

							<tr>
								<td class="input-title">
									<strong>需要申请 
								</td>
								<td class="td-input">
									<input type="radio" name="needRequest" value="0" checked />
									否
									<input type="radio" name="needRequest" value="1" />
									是
								</td>
							</tr>

						--%>
						</table>
						<!-- hidden -->
						<input type="hidden" id="flowId" name="flowId" value="${param.flowId}" />
						<input type="hidden" id="fromStepNodeName" name="fromStepNodeName" />
						<input type="hidden" id="toStepNodeName" name="toStepNodeName" />
						
						<cms:Token mode="html"/>
					</form>
					
					<div style="height:30px"></div>
					
					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitFlowActionForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

function submitFlowActionForm()
{
	if($('#fromStepId').val() == '-9999999' || $('#toStepId').val() == '-9999999')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '起始步骤和结束步骤都要选择!',

		    				cancel: true
		});
		
		return;
    }
    
    if($('#fromStepId').val() ==  $('#toStepId').val())
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/fail.png', 
		    				
		                    content: '起始步骤和结束步骤不可相同!',

		    				cancel: true
		});
		
		return;
    }
    
    if('true' == cuac[$('#fromStepId').val()+'-'+$('#toStepId').val()])
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/fail.png', 
		    				
		                    content: '已存在相同的起始和结束步骤!',

		    				cancel: true
		});
		
		return;
    }

	var hasError = false;
	//系统信息字段验证
     
    currError = submitValidate('passActionName',0,ref_name,'格式为文字,数字,下划线');

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
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    disposeStepNodeName();
    
    encodeFormInput('flowActionForm', false);
	
	var form = document.getElementById('flowActionForm');
	
	
    form.action="<cms:BasePath/>workflow/createWorkflowAction.do";
    
    form.submit();
}

function disposeStepNodeName()
{
	document.getElementById('fromStepNodeName').value = getSelectedText('fromStepId');
	document.getElementById('toStepNodeName').value = getSelectedText('toStepId');
}

  
function close()
{
	api.close();
	api.reload( api.get('cwa') );    
}
   
  
</script>
