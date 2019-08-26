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
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>
		<script>  

         var api = frameElement.api, W = api.opener;
		
         if("true"==="${param.fromFlow}")
         {
           
      		W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '190px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '编辑分发服务器成功!',
	
			    				ok: function()
			    				{
			    					W.location.reload();
			    				}
			 });
         }
         var ref_d=/^(\d){2,25}$/;
         
         var ref_ip=/^[\d.]{7,35}$/;
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('serverName',0,ref_name,'格式为文字,数字,下划线');
		    
		    validate('serverUrl',1,null,null);
		    
		    validate('serverIP',0,ref_ip,'格式为IP地址');
		    		    
		    validate('serverPort',0,ref_d,'格式为数字,下划线');
		 })
        
      </script>

	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/servers-network.png" width="16" height="16" />配置信息
					</div>

					<cms:DispenseServer id="${param.id}">
						<form id="serverConfigForm" name="serverConfigForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

								<tr>
									<td width="25%" class="input-title">
										<strong>服务器名称</strong>
									</td>
									<td class="td-input">
										<input id="serverName" name="serverName" type="text" class="form-input" style="width:355px" value="${Server.serverName}" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>传输协议</strong>
									</td>
									<td class="td-input">
										<input type="radio" name="protocol" value="1" class="form-radio" />
										FTP
										<%--<input type="radio" name="protocol" value="2" class="form-radio" />
										SFTP
										--%><input type="radio" name="protocol" value="3" class="form-radio" />
										当前主机
										<span class="ps">FTP模式无需指定存储根目录</span>
									</td>
								</tr>


								<tr>
									<td class="input-title">
										<strong>访问域名</strong>
									</td>
									<td class="td-input">
										<input id="serverUrl" name="serverUrl" type="text" class="form-input" style="width:355px" value="${Server.serverUrl}" />
										<span class="red">*</span><br/><span class="ps">资源上传后使用的http访问地址,为网络服务软件提供</span>
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>存储根目录</strong>
									</td>
									<td class="td-input">
										<input id="fileRoot" name="fileRoot" type="text" class="form-input" style="width:355px" value="${Server.fileRoot}" placeholder="仅主机模式下须指定根目录"/>

									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>FTP IP地址</strong>
									</td>
									<td class="td-input">
										<input id="serverIP" name="serverIP" type="text" class="form-input" style="width:355px" value="${Server.serverIP}" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>





								<tr>
									<td class="input-title">
										<strong>端口</strong>
									</td>
									<td class="td-input">
										<input id="serverPort" name="serverPort" type="text" class="form-input" style="width:355px"   value="${Server.serverPort}" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>



								<tr>
									<td class="input-title">
										<strong>FTP登陆名</strong>
									</td>
									<td class="td-input">
										<input id="loginName" name="loginName" type="text" class="form-input" style="width:355px" value="${Server.loginName}" />

									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>FTP密码</strong>
									</td>
									<td class="td-input">
										<input id="loginPassword" name="loginPassword" type="password" class="form-input" style="width:355px" value="${Server.loginPassword}" />

									</td>
								</tr>

								<%--
								<tr>
									<td class="input-title">
										<strong>过滤文件类型</strong>
									</td>
									<td class="td-input">
										<input id="filterFlag" name="filterFlag" type="text" class="form-input" style="width:355px" value="${Server.filterFlag}" />
										<span class="ps">后缀名由逗号隔开</span>
									</td>
								</tr>


								<tr>
									<td class="input-title">
										<strong>服务器状态</strong>
									</td>
									<td class="td-input">
										<input type="radio" name="status" value="1" />
										正常
										<input type="radio" name="status" value="0" />
										维护
									</td>
								</tr>

							--%></table>
							<!-- hidden -->
							<input type="hidden" id="serverId" name="serverId" value="${Server.serverId}" />
							<input type="hidden" id="status" name="status" value="1"/>
							
							<cms:Token mode="html"/>

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitServerConfig();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

initRadio('protocol','${Server.protocol}');
initRadio('status','${Server.status}');

var api = frameElement.api, W = api.opener;

function submitServerConfig()
{
	var po = $('[name=protocol]:checked').val();
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('serverName',0,ref_name,'格式为文字,数字,下划线');
        
        if(currError)
        {
        	hasError = true;
        }
        
        if(po == 1)
        {
	   		currError = submitValidate('serverUrl',1,null,null);
	
	   		if(currError)
	        {
	        	hasError = true;
	        }
	        
	        currError = submitValidate('serverIP',0,ref_ip,'格式为IP地址');
	
	   		if(currError)
	        {
	        	hasError = true;
	        }
	        
	        currError = submitValidate('serverPort',0,ref_d,'格式为数字,下划线');
	
	   		if(currError)
	        {
	        	hasError = true;
	        }
        }
    
    
    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	if( !$('#serverUrl').val().endWith('/'))
	{
		$('#serverUrl').val($('#serverUrl').val()+'/');
	}
	
	encodeFormInput('serverConfigForm', false);
	
	var po = $('[name=protocol]:checked').val();
	
	if(po == 1)
	{
		$('#fileRoot').val('');
	
	}
	else if(po == 3)
	{
		$('#serverIP').val('');
		$('#serverPort').val('');
		$('#loginName').val('');
		$('#loginPassword').val('');
	}
	
	var form = document.getElementById("serverConfigForm");
	
	form.action = '<cms:BasePath/>site/editServerConfig.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
</cms:DispenseServer>
