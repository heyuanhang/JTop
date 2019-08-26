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
		 
		var hasError = false;
		//验证
		 
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             api.close(); 
	             
	             W.location.reload();
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>
		<!--main start-->
		 
		<div  >
			<div class="addtit">
				<img src="../style/icons/script-code.png" width="16" height="16" />批量导入敏感词  (多个词汇间换行为分隔符)
			</div>
		</div>
		<div style="height:5px;"></div>
		<form id="advertConfigForm" name="advertConfigForm" method="post">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
				
				<tr>
					<td width="7%" class="input-title">
						<strong>词汇</strong>
					</td>
					<td class="td-input">
						<textarea id="sword" name="sword" style="height:730px;width:650px" class="form-textarea"></textarea>
					</td>
				</tr>

				

				<!-- 以下为独立选项 start -->


			</table>

			<div style="height:35px;"></div>
			<div class="breadnavTab" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="btnbg100">
						<div style="float:right">
							<a href="javascript:submitSW();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>导入&nbsp;</b> </a>
							<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
						</div>
					</tr>
				</table>
			</div>

		
			<!-- hidden -->
			 
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


function submitSW()
{    

    var url = "<cms:BasePath/>content/importSW.do"+"?<cms:Token mode='param'/>";
    
    var tip = W.$.dialog.tips('正在执行导入,请耐心等待...',3600000000,'loading.gif');
    
 	var data = encodeData($('#sword').val());
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		data: 'sword='+data,
			   
			       		success: function(msg)
			            {     
			            	tip.close();
			            	
			            	var obj = eval("("+msg+")");
			            	
			               if('success' == obj)
			               {			             		
			               		W.$.dialog(
							   { 
								   					title :'提示',
								    				width: '260px', 
								    				height: '60px', 
								                    lock: true, 
								                    parent:api,
								    				icon: '32X32/succ.png', 
								    				
								                    content: "导入执行成功(忽略重复词汇)！",
						
								    				ok: function()
								    				{
								    					W.window.location.reload();
								    				}
								});
			               		
			               		
			               } 	
			               else
			               {
			               	    W.$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                    parent:api,
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: function()
								    				{
								    					W.window.location.reload();
								    				}
								});
			               }   
			            }
	 });	
    
  		    
}




</script>
 
