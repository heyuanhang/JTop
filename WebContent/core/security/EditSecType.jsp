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
		    				width: '150px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '编辑授权类型成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});  
         }
         
         var ref_flag=/^[\w-]{1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
         
         $(function()
		 {
		    validate('typeName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 		
 			validate('accSymbol',0,ref_flag,'格式为字母,数字,上下划线');	
 			
 		
 				
		 })
    
      	</script>
	</head>
	<body>

		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/zone.png" width="16" height="16" />授权信息
						</div>
			
						<form id="secForm" name="secForm" method="post">
						<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getAccSecDataTypeTag" objName="At" var="${param.id}">
						
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="25%" class="input-title">
										<strong>授权名称</strong>
									</td>
									<td  class="td-input">
										<input type="text" style="width:318px" id="typeName" name="typeName" class="form-input" value="${At.typeName}"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								<tr>
									<td class="input-title">
										<strong>授权标志</strong>
									</td>

									<td class="td-input">
										<input type="text" style="width:318px" id="accSymbol" name="accSymbol" class="form-input" value="${At.accSymbol}"></input>
									</td>
								</tr>
								<tr>
									<td width="20%" class="input-title">
										<strong>扩展业务类</strong>
									</td>

									<td class="td-input">
										<textarea id="accBehaviorClass" name="accBehaviorClass" style="width:318px; height:60px;" class="form-textarea">${At.accBehaviorClass}</textarea>
									</td>
								</tr>
								<tr>
									<td width="20%" class="input-title">
										<strong>描叙</strong>
									</td>
									<td class="td-input">
										<textarea id="dtDesc" name="dtDesc" style="width:318px; height:70px;" class="form-textarea">${At.dtDesc}</textarea>
									</td>
								</tr>


								
							</table>
							<!-- hidden -->
				 
							<input type="hidden" id="dataTypeId" name="dataTypeId" value="${At.dataTypeId}" />
							
							<cms:Token mode="html"/>
							

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab" >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitSecForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
   
   
function submitSecForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('accSymbol',0,ref_flag,'格式为字母,数字,上下划线');
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('typeName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');

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

	encodeFormInput('secForm', false);

   var secForm = document.getElementById('secForm');
   secForm.action="<cms:BasePath/>security/editSecType.do";
   secForm.submit();
}

   

  
</script>
</cms:QueryData>


