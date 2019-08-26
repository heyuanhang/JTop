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
				    				width: '260px', 
				    				height: '60px', 
				                    lock: true, 
				                    parent:api,
				    				icon: '32X32/succ.png', 
				    				
				                    content: "添加敏感词执行成功(忽略重复词汇)！",
		
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
			    validate('sensitiveStr',1,null,null);
 				//validate('replaceStr',1,null,null);
	 				
			 })
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/traffic-cone.png" width="16" height="16" />词信息
					</div>

					<form id="swForm" name="swForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

							<tr>
								<td width="24%" class="input-title">
									<strong>敏感词</strong>
								</td>
								<td class="td-input">
									<input id="sensitiveStr" name="sensitiveStr" type="text" class="form-input" style="width:326px" />
									<span class="red">*</span><br/><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>替换词</strong>
								</td>
								<td class="td-input">
									<input id="replaceStr" name="replaceStr" type="text" class="form-input" style="width:326px" />
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
									<a name="btnwithicosysflag" href="javascript:submitSensitive();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

var api = frameElement.api, W = api.opener;

function submitSensitive()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('sensitiveStr',1,null,null);
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('replaceStr',1,null,null);

   		if(currError)
        {
        	//hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	     
	    return;

	}
	
	disableAnchorElementByName("btnwithicosysflag",true);

		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
		
	encodeFormInput('swForm', false);
	
	var form = document.getElementById("swForm");
	
	form.action = '<cms:BasePath/>content/addSw.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
