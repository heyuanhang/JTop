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
         
         	W.$.dialog({ 
				   					title :'提示',
				    				width: '140px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				parent:api,
				                    content: '新建部门机构成功!',
				                    
				                    ok: function () 
				                    { 
				                    	W.window.location.reload();
				                    }
				                    
    								
            });  	 	
             
         }
         
          var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;

         $(function()
		 {
		    validate('orgName',0,ref_name,'格式为文字,数字,下划线,至少2字');
 			validate('orgFlag',0,ref_flag,'格式为字母,数字,下划线');	
 			validate('parentId',1,null,null);
 				
		 })
    
      	</script>
	</head>
	<body>

		<cms:LoginUser>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/balance.png" width="16" height="16" />部门
					</div>

					<form id="orgForm" name="orgForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="24%" class="input-title">
									<strong>上级部门: 
								</td>
								<td width="80%" class="td-input">
									<select id="parentId" name="parentId" class="form-select" style="width:318px">
										<option value="">
											--------------- 请选择上级部门 ---------------
										</option>
										
										<cms:SystemOrg orgId="${Auth.orgIdentity}" childMode="true">
											<option value="${Org.orgId}">
												${Org.uiLayerName}
											</option>
										</cms:SystemOrg>
										
									</select>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							<tr>
								<td width="20%" class="input-title">
									<strong>名称: 
								</td>
								<td width="80%" class="td-input">
									<input id="orgName" name="orgName" type="text" class="form-input" style="width:314px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>标识代码: 
								</td>
								<td class="td-input">
									<input id="orgFlag" name="orgFlag" type="text" class="form-input" style="width:314px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>



							<tr>
								<td class="input-title">
									<strong>职能描叙:</strong>
								</td>
								<td class="td-input">
									<textarea id="orgDesc" name="orgDesc" class="form-textarea" style="width:313px; height:60px;"></textarea>
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
								<a name="btnwithicosysflag" href="javascript:submitOrgForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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
   //showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   
function submitOrgForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('orgName',0,ref_name,'格式为文字,数字,下划线,至少2字');
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('orgFlag',0,ref_flag,'格式为字母,数字,下划线');	

   		if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('parentId',1,null,null);

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
         return;

	}
	
	//后台验证
	
	
		var count = validateExistFlag('sysOrg', $('#orgFlag').val());
		
		if(count > 0)
		{

			showTips('orgFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
		
   disableAnchorElementByName("btnwithicosysflag",true);
		
   var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
   
   encodeFormInput('orgForm', false);

   var orgForm = document.getElementById('orgForm');
   orgForm.action="<cms:BasePath/>organization/createOrg.do";
   orgForm.submit();
}


  
</script>
</cms:LoginUser>
