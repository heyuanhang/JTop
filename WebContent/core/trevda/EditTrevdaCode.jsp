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
		$(window).load(function()
		{
			$("#configName").bind('focus', function() 
			{
				var target = $("#configName").val();
				//alert(target);
			    if(target == '')
				{
					hasError = true;
  					showTips('configName','不可为空');
  				}
  				else
  				{
  					hasError = false;
  				}
			});	
			
			$("#configName").bind('propertychange', function() 
						{
						   $( 'div.configName_jtop_ui_tips_class' ).remove();
  							
							var target = $("#configName").val();

    						if(target == '')
    						{
    							hasError = true;
    							showTips('configName','不可为空');               					
  							}
  							else
  							{
  								hasError = false;
  							}
  							
  							
						});
						
						
			
		
		})
	
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
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>
		<!--main start-->
		<cms:SystemAdvertConfig configId="${param.configId}">
		<div  >
			<div class="addtit">
				<img src="../style/icons/script-code.png" width="16" height="16" />代码
			</div>
		</div>
		<div style="height:5px;"></div>
		<form id="advertConfigForm" name="advertConfigForm" method="post">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
				
				<tr>
					<td width="7%" class="input-title">
						<strong>代码</strong>
					</td>
					<td class="td-input">
						<textarea id="advertCode" name="advertCode" style="height:430px;width:650px" class="form-textarea">${Config.advertCode}</textarea>
					</td>
				</tr>

				

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


function submitAdvertConfigForm()
{    

    var form = document.getElementById('advertConfigForm');
	
	
	encodeForInput('advertCode',false);
		  
	
    form.action="<cms:BasePath/>trevda/editTrevdaTemp.do";
    
    form.submit(); 
    
  		    
}




</script>
</cms:SystemAdvertConfig>
