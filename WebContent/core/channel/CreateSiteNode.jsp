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
		basePath='<cms:BasePath/>';
		
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
			    				
			                    content: "添加站群节点成功!",
	
			    				ok: function()
			    				{
			    					W.window.location.reload();
			    					W.window.parent.window.document.getElementById('top').src = 'top.jsp?notReload=true';
			    				}
				});       
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
         var ref_url = /^http[s]?:\/\/([\w-]+\.)+[\w-]+([\w-./?%&=:]*)?$/;

         $(function()
		 {
		    validate('siteName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 			validate('siteFlag',0,ref_flag,'格式为字母,数字,下划线');
 			validate('siteUrl',0,ref_url,'格式为合法的URL地址');	
	
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/sitemap-image.png" width="16" height="16" />节点信息
					</div>

					<form id="siteForm" name="siteForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							
							<tr>
								<td width="23%" class="input-title">
									<strong>站点名称: 
								</td>
								<td class="td-input">
									<input id="siteName" name="siteName" type="text" class="form-input" style="width:321px" placeholder="请输入站点名称"  />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>站点标识: 
								</td>
								<td class="td-input">
									<input id="siteFlag" name="siteFlag" type="text" class="form-input" style="width:321px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>域名: 
								</td>
								<td class="td-input">
									<input id="siteUrl" name="siteUrl" type="text" class="form-input" style="width:321px" value="http://"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>301跳转URL: 
								</td>
								<td class="td-input">
									<textarea id="home301Url" name="home301Url" class="form-textarea" style="width:322px; height:70px;"></textarea>
								 	<br/>	 <span class="ps">多个地址之间用逗号分隔，可跳转到主域名</span>
								</td>
							</tr>




							<tr>
								<td class="input-title">
									<strong>站点描叙:</strong>
								</td>
								<td class="td-input">
									<textarea id="siteDesc" name="siteDesc" class="form-textarea" style="width:322px; height:50px;"></textarea>
							</tr>

							<tr>
								<td class="input-title">
									<strong>模板编码: 
								</td>
								<td class="td-input">
									<input type="radio" name="templateCharset" value="UTF-8" checked/>
									UTF-8&nbsp;
									<input type="radio" name="templateCharset" value="GBK"  />
									GBK
									
								</td>
							</tr>

							


						</table>
						<!-- hidden -->
						<cms:Token mode="html"/>

					</form>
					<div style="height:16px"></div>
					 	 
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<div style="float:right">
								<a name="btnwithicosysflag" href="javascript:submitSiteForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
							</div>
						</tr>
					</table>
					</div>


				</td>
			</tr>


			</tr>
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
 
function close()
{
	api.close();
}

function submitSiteForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('siteName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('siteFlag',0,ref_flag,'格式为字母,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('siteUrl',0,ref_url,'格式为合法的URL地址');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
         
	    return;

	}
	
	//后台验证
	

		var count = validateExistFlag('sysSite', $('#siteFlag').val());
		
		if(count > 0)
		{
			hasError = true;
			showTips('siteFlag', '系统已存在此值，不可重复录入');
			
			
		}
		
		var siteUrl = $('#siteUrl').val();
		
		if( !$('#siteUrl').val().endWith( '/' ) )
        {
              siteUrl = $('#siteUrl').val() + '/';
        }
		
		count = validateExistFlag('sysSiteUrl', siteUrl);
		
		if(count > 0)
		{
			hasError = true;
			showTips('siteUrl', '系统已存在此值，不可重复录入');

		}
		
		if(hasError)
	    {		    
		    return;
		}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    encodeFormInput('siteForm', false);
    
   var siteForm = document.getElementById('siteForm');
   siteForm.action="<cms:BasePath/>site/addSite.do";
   siteForm.submit();
}

   
  
</script>
