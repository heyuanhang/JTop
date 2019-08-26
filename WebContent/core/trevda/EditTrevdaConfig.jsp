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
				    				width: '160px', 
				    				height: '60px', 
				                    lock: true, 
				                    parent:api,
				    				icon: '32X32/i.png', 
				    				
				                    content: '编辑广告代码配置成功!',
		
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
		    validate('configName',0,ref_name,'格式为文字,数字,下划线');
 			
 				
		 })
         
        	
      </script>
	</head>
	<body>
		<!--main start-->
		<cms:SystemAdvertConfig configId="${param.configId}">
			<div class="addtit">
				<img src="../style/icons/color-adjustment-green.png" width="16" height="16" />配置
			</div>
			<form id="advertConfigForm" name="advertConfigForm" method="post">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
					<tr>
						<td width="25%" class="input-title">
							<strong>配置名称</strong>
						</td>
						<td class="td-input">
							<input type="text" style="width:264px" id="configName" name="configName" class="form-input" value="${Config.configName}"></input>

						</td>
					</tr>

					<tr>
						<td class="input-title">
							<strong>描叙</strong>
						</td>
						<td class="td-input">
							<textarea id="configDesc" name="configDesc" style="height:70px;width:260px" class="form-textarea">${Config.configDesc}</textarea>
						</td>
					</tr>

					<tr>
						<td class="input-title">
							<strong>版位参数模型</strong>
						</td>
						<td class="td-input">

							<select id="posModelId" name="posModelId" style="width:262px">
								<option value="-1">
									 ------- 请选择广告版位参数模型 --------
								</option>
								<cms:SystemDataModelList modelType="5">
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
							<strong>内容参数模型</strong>
						</td>
						<td class="td-input">

							<select id="contentModelId" name="contentModelId" style="width:262px">
								<option value="-1">
									-------- 请选择广告内容参数模型 -------
								</option>
								<cms:SystemDataModelList modelType="6">
									<cms:SystemDataModel>
										<option value="${DataModel.dataModelId}">
											${DataModel.modelName}&nbsp;
										</option>
									</cms:SystemDataModel>
								</cms:SystemDataModelList>

							</select>
						</td>
					</tr>

					<%--


											<tr>
												<td class="input-title">
													<strong>广告代码模板</strong>
												</td>
												<td class="td-input">
													<textarea id="advertCode" name="advertCode" style="height:260px;width:680px" class="form-textarea"></textarea>
												</td>
											</tr>
											--%>
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
					<%--<tr id="mustFillDIV">
					<td class="input-title">
						<strong>启用状态 
					</td>
					<td class="td-input">
						<input name="userState" type="radio" value="1" checked />
						<span class="STYLE12">启用</span> &nbsp;
						<input name="userState" type="radio" value="0" />
						<span class="STYLE12">停用</span>
					</td>
				</tr>

				--%>
					<!-- 以下为独立选项 start -->


				</table>

				<div style="height:35px;"></div>
				<div class="breadnavTab" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="btnbg100">
							<div style="float:right">
								<a href="javascript:submitAdvertConfigForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
								<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
							</div>
						</tr>
					</table>
				</div>


				<!-- hidden -->
				<input type="hidden" name="configId" id="configId" value="${param.configId}" />

				<input type="hidden" name="userState" id="userState" value="1" />
				
				<cms:Token mode="html"/>

			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('posModelId','${Config.posModelId}');

initSelect('contentModelId','${Config.contentModelId}');

function setTab(flag,pos,size)
{
	if(!hasError)
	{
		setTab2(flag,pos,size);
	}

}

  
function close()
{
	api.close();
}



 


function submitAdvertConfigForm()
{    

	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('configName',0,ref_name,'格式为字母,数字,下划线');	
        
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
	
	
	encodeFormInput('advertConfigForm', false);
   
    var form = document.getElementById('advertConfigForm');
	
    form.action="<cms:BasePath/>trevda/editTrevdaConfig.do";
    
    form.submit(); 
    
  		    
}




</script>
</cms:SystemAdvertConfig>
