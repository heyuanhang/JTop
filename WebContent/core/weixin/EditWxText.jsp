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
		var hasError = false;
		//验证
		 
	     var api = frameElement.api, W = api.opener; 
		
		 
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	W.$.dialog.tips('编辑成功',1,'32X32/succ.png'); 
       		       
         }
         
        	
      </script>
	</head>
	<body>
		<!--main start-->
		 <div  >
			<div class="addtit">
				<img src="../style/icons/script-attribute-t.png" width="16" height="16" />回复文本
			</div>
		</div>
		<div style="height:5px;"></div>
		<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxMessageForTag" objName="WM" var="${param.msgId},,,">
						
		<form id="wxForm" name="wxForm" method="post">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
				
				<tr>
					<td width="7%" class="input-title">
						<strong>文本</strong>
					</td>
					<td class="td-input">
						<textarea id="textMsg" name="textMsg" style="height:430px;width:650px" class="form-textarea">${WM.textMsg}</textarea>
					</td>
				</tr>

				

				 

			</table>

			<div style="height:35px;"></div>
			<div class="breadnavTab" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="btnbg100">
						<div style="float:right">
							<a href="javascript:submitForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
							<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
						</div>
					</tr>
				</table>
			</div>

		
			<!-- hidden -->
			<input type="hidden" name="msgId" id="msgId" value="${WM.msgId}" />
			
			<cms:Token mode="html"/>

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">


  
function close()
{
	api.close();
}


function submitForm()
{    

    var form = document.getElementById('wxForm');
	
	
	encodeForInput('textMsg',false);
		  
	
    form.action="<cms:BasePath/>wx/editText.do";
    
    form.submit(); 
    
  		    
}




</script>
</cms:QueryData>
