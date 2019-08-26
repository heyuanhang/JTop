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
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            api.close();
            
            api.get('omsgd').window.location.reload();         
		 
		    W.$.dialog.tips('发送站内信成功!',1,'success.gif');
         }
         
      </script>
	</head>
	<body>
		<form id="msgForm" name="msgForm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						信件细节
					</div>

					
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
						<tr>
							<td width="14%" class="input-title">
								<strong>收信人</strong>
							</td>
							<td class="td-input">
								<input type="text" style="width:520px" readonly class="form-input" id="showSelectMan"></input>&nbsp;<input onclick="javascript:openSelectReceiverDialog();" value="选取..." class="btn-1" type="button" />
							</td>
						</tr>
						<tr>
							<td width="14%" class="input-title">
								<strong>标题</strong>
							</td>
							<td class="td-input">
								<input type="text" style="width:520px" id="msgTitle" name="msgTitle" class="form-input" ></input>

							</td>
						</tr>

						<tr>
							<td class="input-title">
								<strong>内容</strong>
							</td>
							<td class="td-input">
								<textarea style="height:230px;width:520px" id="msgContent" name="msgContent" class="form-textarea" ></textarea>
							</td>
						</tr>
						

					</table>
				</td>
			</tr>


		</table>
		
		
		<!--hidden -->
		
		<input type="hidden" name="orgIds" id="orgIds" />
		<input type="hidden" name="roleIds" id="roleIds" />
		<input type="hidden" name="userIds" id="userIds" />
		
		<cms:Token mode="html"/>
		
		</form>

		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="btnbg100">
					 <div style="float:right">
						<a name="btnwithicosysflag" href="javascript:sendMsg();"  class="btnwithico"><img src="../style/icons/mail--pencil.png" width="16" height="16"/><b>发送信件&nbsp;</b> </a>

						<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
					</div>
				</tr>
			</table>

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

function openSelectReceiverDialog()
{
	W.$.dialog({ 
    	title : '选取收信人',
    	width: '650px', 
    	height: '540px',
    	parent:api,
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/message/SelectUserAndRoleAndOrg.jsp'
	});
}

function sendMsg()
{
	if('' == $('#showSelectMan').val())
	{
		 	W.$.dialog(
			{ 
			   					title :'提示',
			    				width: '130px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    		
			                    content: '没有选择收信人!',
	
			    				cancel:true
			});   
			
			return;
	}
	
	if('' == $('#msgTitle').val())
	{
		 	W.$.dialog(
			{ 
			   					title :'提示',
			    				width: '130px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    		
			                    content: '信息标题不可为空!',
	
			    				cancel:true
			});   
			
			return;
	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    encodeFormInput('msgForm', false);
	
	var irFrom = document.getElementById('msgForm');
	
	irFrom.action = "<cms:BasePath/>message/sendMsg.do?dialog=true";;
	
 
	irFrom.submit();

}



</script>

