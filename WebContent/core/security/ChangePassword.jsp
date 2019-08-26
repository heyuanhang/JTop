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
            W.$.dialog({ 
				title :'提示',
				width: '160px', 
				height: '60px', 
				lock: true, 
				parent:api,
				icon: '32X32/succ.png', 
							    				
				content: '改动密码成功！', 
				ok: function()
				{							       	  
		       		W.window.location.reload();       
				}			   					
							                    
			});
            
         }
         
         var ref_pw = /^[\w~!@#$%^&*()_+]{6,20}$/;
        
         
         $(function()
		 {
		    
 			validate('password',0,ref_pw,'只能输入6-20个字母,数字,特殊字符');	
 			
 				
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/key-solid.png" width="16" height="16" />密码
					</div>

					<form id="pwForm" name="pwForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="27%" class="input-title">
									<strong>新密码</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:232px" id="password" name="password" class="form-input"></input>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td width="23%" class="input-title">
									<strong>确认密码</strong>
								</td>
								<td width="80%" class="td-input">
									<input type="text" style="width:232px" id="affirmPassword" name="affirmPassword" class="form-input"></input>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

						</table>
						<!-- hidden -->
						<input type="hidden" id="userId" name="userId" value="${param.userId}" />
						
						<cms:Token mode="html"/>
					</form>
					
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<div style="float:right">
								<a href="javascript:submitPwForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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
   //showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   
function submitPwForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('password',0,ref_pw,'只能输入6-20个字母,数字,特殊字符');	
        
        if(currError)
        {
        	hasError = true;
        }

	if($('#affirmPassword').val() != $('#password').val())
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '两次输入的密码不一致！!',

		    				cancel: true
		});
		
		return;
    }
    
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
   encodeFormInput('pwForm', false);
   var pwForm = document.getElementById('pwForm');
   pwForm.action="../../security/changePassword.do";
   pwForm.submit();
}
  
</script>
