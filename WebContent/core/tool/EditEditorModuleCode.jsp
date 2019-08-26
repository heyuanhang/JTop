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
         		W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '130px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    		
			                    content: '编辑组件信息成功!',
	
			    				ok:function()
			    				{ 
	             					W.location.reload();
			    				}
				});   
	             
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>
		<!--main start-->
		
		<div  style="top:0px">
			<div class="addtit">
				<img src="../style/icons/script-code.png" width="16" height="16" />代码模板
			</div>
		</div>
		 
		<cms:QueryData service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getEditorModuleTag" objName="Editor" var="${param.type}">
		<form id="emForm" name="emForm" method="post">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
				
				<tr>
					<td width="10%" class="input-title">
						<strong>代码</strong>
					</td>
					<td class="td-input">
						<textarea id="emCode" name="emCode" style="height:340px;width:610px" class="form-textarea">${Editor.code}</textarea>
					</td>
				</tr>
				
				<tr>
					<td width="10%" class="input-title">
						<strong>描叙</strong>
					</td>
					<td class="td-input">
						<textarea id="emDesc" name="emDesc" style="height:80px;width:610px" class="form-textarea">${Editor.emDesc}</textarea>
					</td>
				</tr>
				

				<!-- 以下为独立选项 start -->
				

			</table>

			<div style="height:15px;"></div>
			<div class="breadnavTab"  >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="btnbg100">
						<div style="float:right">
							<a href="javascript:submitCodeForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>保存&nbsp;</b> </a>
							<a href="javascript:close();"   class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
						</div>
					</tr>
				</table>
			</div>

		
			<!-- hidden -->
			<input type="hidden" name="emId" id="emId" value="${Editor.emId}" />
			
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


function submitCodeForm()
{    

	encodeFormInput('emForm', false);
	
    var form = document.getElementById('emForm');
	
    form.action="<cms:BasePath/>channel/editEditorCode.do";
    
    form.submit();	    
}




</script>
</cms:QueryData>
