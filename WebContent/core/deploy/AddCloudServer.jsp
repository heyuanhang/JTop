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
			    				
			                    content: '新增云存储服务器成功!',
	
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
		    validate('cloudType',1,null,null);
 			
 				
		 })
            
      	</script>

	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/weather-cloud.png" width="16" height="16" />云存储配置
					</div>

					<form id="csForm" name="gatewayForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="23%" class="input-title">
									<strong>云存储类型</strong>
								</td>
								<td  class="td-input">
									<select id="cloudType" name="cloudType" class="form-select" style="width:389px">
										<option value="">
											------------------ 请选择云存储类型 ------------------
										</option>
										<option value="ALOSS">
											阿里云OSS
										</option>
										<option value="TXCOS">
											腾讯云COS
										</option>
										<option value="QN">
											七牛云
										</option>

									</select>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
									<strong>accessKey</strong>
								</td>
								<td  class="td-input">
									<input id="accessUrl" name="accessUrl" type="text" class="form-input" style="width:385px" />
									
								</td>
							</tr>
							
							
							<tr>
								<td  class="input-title">
									<strong>accessSecret</strong>
								</td>
								<td  class="td-input">
									<input id="accessUrl" name="accessUrl" type="text" class="form-input" style="width:385px" />
									
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
									<strong>bucketName</strong>
								</td>
								<td  class="td-input">
									<input id="accessUrl" name="accessUrl" type="text" class="form-input" style="width:385px" />
									
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
									<strong>endPoint</strong>
								</td>
								<td  class="td-input">
									<input id="endPoint" name="endPoint" type="text" class="form-input" style="width:385px" />
									
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
									<strong>appId</strong>
								</td>
								<td  class="td-input">
									<input id="accessUrl" name="accessUrl" type="text" class="form-input" style="width:385px" />
									
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
									<strong>location</strong>
								</td>
								<td  class="td-input">
									<input id="accessUrl" name="accessUrl" type="text" class="form-input" style="width:385px" />
									
								</td>
							</tr>


							<tr>
								<td  class="input-title">
									<strong>访问地址</strong>
								</td>
								<td  class="td-input">
									<input id="accessUrl" name="accessUrl" type="text" class="form-input" style="width:385px"/>
									
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
									<a name="btnwithicosysflag" href="javascript:submitAddCserver();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

function submitAddCserver()
{
	 
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('cloudType',1,null,null);	
        
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
	
	 		
	if( !$('#endPoint').val().endWith( '/' ) )
    {
        $('#endPoint').val($('#endPoint').val() + '/') ;
    }
    
    if( !$('#accessUrl').val().endWith( '/' ) )
    {
        $('#accessUrl').val($('#accessUrl').val() + '/') ;
    }
	
	encodeFormInput('csForm', false);
	
	var form = document.getElementById("csForm");
	
	form.action = '../../site/addCloudCfg.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
  
 
</script>
