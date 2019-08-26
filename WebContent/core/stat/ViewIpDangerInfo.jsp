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
		 
        
         
         
        	
      </script>
	</head>
	<body>
	<cms:QueryData objName="IPD" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteClientIpDangerAccessTraceTag" var="${param.trId},,,">
																														
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/wall--exclamation.png" width="16" height="16" />攻击信息
					</div>

					<form id="msgForm" name="msgForm" method="post">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
						
						<tr>
							<td width="14%" class="input-title">
								<strong>目标URL地址</strong>
							</td>
							<td class="td-input">
								<textarea style="height:105px;width:486px" class="form-textarea" readonly>${IPD.targetUrl}</textarea>
							</td>
						</tr>

						<tr>
							<td class="input-title">
								<strong>可疑参数</strong>
							</td>
							<td class="td-input">
								<textarea  style="height:105px;width:486px" class="form-textarea">${IPD.dangerStr}</textarea>
							</td>
						</tr>

						<tr>
							<td class="input-title">
								<strong> </strong>
							</td>
							<td class="td-input">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
									<tr>
										<td width="58%">
											IP: ${IPD.ip} (<cms:IPArea ip="${IPD.ip}" />)
										</td>
										<td width="40%">
											时间:
											<cms:FormatDate date="${IPD.eventDT}" />
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<!-- 以下为独立选项 start -->
						
						</from>
					</table>
				</td>
			</tr>


		</table>

		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:close();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
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
	W.location.reload();
}






</script>
</cms:QueryData>
