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
			    				
			                    content: '编辑集群服务器成功!',
	
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
		    validate('serverName',1,null,null);
 			
 				
		 })
            
      	</script>

	</head>
	<body>
		<cms:QueryData service="cn.com.mjsoft.cms.cluster.service.ClusterService" method="getClusterServerInfoForTag" var="${param.sid}" objName="Cs"  >
									
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/weather-cloud.png" width="16" height="16" />集群服务器
					</div>
					
					<form id="csForm" name="gatewayForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="26%" class="input-title">
									<strong>服务器名称</strong>
								</td>
								<td  class="td-input">
									<input id="serverName" name="serverName" type="text" class="form-input" size="50" value="${Cs.serverName}"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>


							<tr>
								<td width="26%" class="input-title">
									<strong>访问地址</strong>
								</td>
								<td  class="td-input">
									<input id="clusterUrl" name="clusterUrl" type="text" class="form-input" size="50" value="${Cs.clusterUrl}"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							

							 
						</table>
						<!-- hidden -->
						<cms:Token mode="html"/>
						
						<input type="hidden" id="serverId" name="serverId" value="${Cs.serverId}" />
						

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitEditCserver();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();" class="btnwithico"  ><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

function submitEditCserver()
{
	 
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('serverName',1,null,null);	
        
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
	
	encodeFormInput('csForm', false);
	
	var form = document.getElementById("csForm");
	
	form.action = '../../cluster/editCluServer.do';
	form.submit();
}


function close()
{
	api.close();
}
  
  
 
</script>
</cms:QueryData>
