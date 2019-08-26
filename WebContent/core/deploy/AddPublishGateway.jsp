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
			    				
			                    content: '新增发布点成功!',
	
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
		    validate('name',1,null,null);
 			
 				
		 })
            
      	</script>

	</head>
	<body>
<cms:CurrentSite>  
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/folder-network.png" width="16" height="16" />分发点
					</div>

					<form id="gatewayForm" name="gatewayForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="24%" class="input-title">
									<strong>名称</strong>
								</td>
								<td  class="td-input">
									<input id="name" name="name" type="text" class="form-input" style="width:340px"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>


							<tr>
								<td class="input-title">
									<strong>主机服务器</strong>
								</td>
								<td class="td-input">
									<select id="targetServerId" name="targetServerId" style="width:344px" onchange="javascript:change('s')">
										<option value="-1">
											----------------- 可选的分发服务器 -----------------
										</option>
										
										<cms:DispenseServer>
											<option value="${Server.serverId}">
												${Server.serverName}
											</option>
										</cms:DispenseServer>
										
									</select>
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>云存储服务器</strong>
								</td>
								<td class="td-input">
									<select id="targetCloudId" name="targetCloudId" style="width:344px" onchange="javascript:change('c')">
										<option value="-1">
											----------------- 可选的分发云存储 -----------------
										</option>
										
										<cms:QueryData service="cn.com.mjsoft.cms.site.service.SiteGroupService" method="getCloudCfgForTag" var=",${CurrSite.siteId}" objName="Server"  >
											
											<option value="${Server.cloId}">
												 ${Server.typeStr} - ${Server.bucketName} 
											</option>
										</cms:QueryData>
										
									</select>
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>分发类型</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="transfeType" value="1"  class="form-radio" checked />
									html
									<input type="radio" name="transfeType" value="2"  class="form-radio" />
									图片
									<input type="radio" name="transfeType" value="3" class="form-radio" />
									媒体
									<input type="radio" name="transfeType" value="4"  class="form-radio" />
									文件
									<input type="radio" name="transfeType" value="0" class="form-radio"  />
									资源
								</td>
							</tr>


							<%--<tr id="pubSourceFolderTr" style="display:none">
								<td class="input-title">
									<strong>源内容目录</strong>
								</td>
								<td class="td-input">
									<input name="sourcePath" id="sourcePath" type="text" class="form-input" size="52" />
									<input type="button" value="浏览..." onclick="javascript:openSiteFileInfoList();" class="btn-1" />
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>FTP存储根目录</strong>
								</td>
								<td class="td-input">
									<input name="targetServerRoot" id="targetServerRoot" type="text" class="form-input" size="50" />

								</td>
							</tr>
--%>
							


							<tr>
								<td class="input-title">
									<strong>工作状态</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="useState" value="1" checked />
									启用
									<input type="radio" name="useState" value="0" />
									停用
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
									<a name="btnwithicosysflag" href="javascript:submitPublishGateway();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

function submitPublishGateway()
{
	if($('#targetServerId').val() == '-1' && $('#targetCloudId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '290px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个分发服务器或云存储服务器!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('name',1,null,null);	
        
        if(currError)
        {
        	hasError = true;
        }
        
   
    
    
    			
    if(hasError)
    {
         
	    return;

	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('gatewayForm', false);
	
	var form = document.getElementById("gatewayForm");
	
	form.action = '../../site/addPublishGateway.do';
	form.submit();
}


function close()
{
	api.close();
}
  
  
function displaySourceTr(flag)
{
	if('true' == flag)
	{
		document.getElementById('pubSourceFolderTr').style.display='';
	}
	else
	{
		document.getElementById('pubSourceFolderTr').style.display='none';
	}
}

function openSiteFileInfoList()
{
	W.$.dialog({ 
    	title :'选择站点目录',
    	width: '700px', 
    	height: '500px',
    	parent:api, 
    	lock: true, 
        max: false, 
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/deploy/ListSiteFileInfo.jsp'
	});
}

function change(flag)
{
	if('c' == flag)
	{
		initSelect('targetServerId','-1');
	}
	else if('s' == flag)
	{
		initSelect('targetCloudId','-1');
	}

}
</script>
</cms:CurrentSite>