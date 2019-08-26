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
		var hasError = false;
		//验证
		$(window).load(function()
		{
			$("#configName").bind('focus', function() 
			{
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
		 
		    W.$.dialog.tips('回复成功!',1,'success.gif');
         }
      
        	
      </script>
	</head>
	<body>
	<cms:QueryData service="cn.com.mjsoft.cms.message.service.MessageService" method="getManagerMessageTag" objName="Msg" var=",,,,${param.msgId},,">
																				
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						信件细节
					</div>

					<form id="msgForm" name="msgForm" method="post">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
						<tr>
							<td width="14%" class="input-title">
								<strong>标题</strong>
							</td>
							<td class="td-input">
								<input type="text" style="width:520px" readonly class="form-input" value="${Msg.msgTitle}"></input>

							</td>
						</tr>

						<tr >
							<td class="input-title">
								<strong>内容</strong>
							</td>
							<td class="td-input">
								<textarea style="height:230px;width:520px" class="form-textarea" readonly>${Msg.msgContent}</textarea>
							</td>
						</tr>

						<tr>
							<td class="input-title">
								<strong>回信</strong>
							</td>
							<td class="td-input">
								<textarea id="replyContent" name="replyContent" style="height:230px;width:520px" class="form-textarea">${Msg.replyContent}</textarea>
							</td>
						</tr>

						<tr>
							<td class="input-title">
								<strong> </strong>
							</td>
							<td class="td-input">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
									<tr>
										<td width="42%">
											发件人: <cms:SystemUser id="${Msg.sender}">${SysUser.userName}</cms:SystemUser>
										</td>
										<td width="40%">
											发信时间:
											<cms:FormatDate date="${Msg.sendDT}" />
										</td>
									
									</tr>

								</table>
							</td>
						</tr>

						

						<!-- 以下为独立选项 start -->
						<input type="hidden" name="msgId" id="msgId" value="${Msg.msgId}" />
						
						<cms:Token mode="html"/>
						
						</from>
					</table>
				</td>
			</tr>


		</table>

		<div style="height:55px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:reply();"  class="btnwithico"><img src="../style/icons/mail--pencil.png" width="16" height="16"/><b>回复信件&nbsp;</b> </a>
						
						<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
					</div>
				</tr>
			</table>
		</div>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">
 
 
//更新已读状态
if('${Msg.isRead}' == '0')
{
var url = "<cms:BasePath/>message/readMsg.do?msgId=${Msg.msgId}";
                    
$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {     
			               
			              
			            }
 });
}
  
function close()
{
	api.close();
	api.get('omsgd').window.location.reload();    
}

function reply()
{
	encodeFormInput('msgForm', false);
	
	var msgFrom = document.getElementById('msgForm');
	
	msgFrom.action = "<cms:BasePath/>message/replyMsg.do?dialog=true";
	
	msgFrom.submit();

}



</script>
</cms:QueryData>
