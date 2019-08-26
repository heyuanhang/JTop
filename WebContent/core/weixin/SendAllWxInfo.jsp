<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>


		<script>  
		basePath='<cms:BasePath/>';
	 	
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
		    				
		                    content: '添加到群发成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});
                 
         }
         
         
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->

					<div class="addtit">
							<img src="../style/icons/ico-weixin.png" width="16" height="16" />群发参数
					</div>

					<form id="saForm" name="saForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="25%" class="input-title">
									<strong> 群发目标</strong>
								</td>
								<td class="td-input">
									<select id="sendTarget" name="sendTarget" class="form-select" style="width:231px">
											<option value="-9999">
											------------ 全部订阅用户 ------------
											</option>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="">
 											<option value="${UG.wuGroupId}">
												${UG.wuGroupName}
											</option>
											</cms:QueryData>
											
											
									</select>
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
										<strong>发送时间</strong>
									</td>
									<td width="80%" class="td-input">
										<input id="sendDT" name="sendDT"  class="form-input-top-date" size="36" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});"   />
										
										<br/><span class="ps">不选发送时间则为立即发送模式</span>
									</td>
							</tr>
							
							 


						</table>
						<!-- hidden -->
						
						<input id="msgType" name="msgType" type="hidden" />		
						
						<cms:Token mode="html"/>
													 	
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								 
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								
								 
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

 

function submitForm()
{
	 var resType = '${param.msgType}';
	
	if('news' == resType)
	{
		resType = '图文';
	}
	else if('image' == resType)
	{
		resType = '图片';
	}
	else if('video' == resType)
	{
		resType = '视频';
	}
	else if('voice' == resType)
	{
		resType = '录音';
	}
	else if('music' == resType)
	{
		resType = '音乐';
	}
	else if('text' == resType)
	{
		resType = '文本';
	}
	 
	$('#msgType').val(resType);
	 
	disableAnchorElementByName("btnwithicosysflag",true);

    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var form = document.getElementById("saForm");
	
	form.action = '<cms:BasePath/>wx/addSendAll.do?msgId=${param.msgId}';
	
	form.submit();
}


function close()
{
	api.close();
}
 
  
</script>
