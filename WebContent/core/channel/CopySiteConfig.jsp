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
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog.tips('从其他站点同步配置成功...',2); 
           // api.close(); 
         	//api.reload( api.get('cwa') );    
       		//W.window.location.reload();       
       	
         }
      
      	</script>
	</head>
	<cms:LoginUser>
	<cms:CurrentSite>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					 
					<form id="infoForm" name="infoForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="24%" class="input-title">
									<strong>源站点</strong>
								</td>
								<td class="td-input">
									
									<select id="siteId" onchange="javascript:changeCurrentSite();" class="form-select">
									<option value="-1">
																					----------- 请选择目标站点 -----------
																				</option>
									<cms:SystemOrgSite orgId="${Auth.orgIdentity}">
										<cms:if test="${CurrSite.siteId != OrgSite.siteId}">
										<option value="${OrgSite.siteId}">
											${OrgSite.siteName}&nbsp;&nbsp;
										</option>
										</cms:if>
									</cms:SystemOrgSite>
									<cms:Empty flag="OrgSite">
										<option value="-9999">
											--- 无站点信息 ---&nbsp;&nbsp;
										</option>
									</cms:Empty>
								</select>

								</td>
							</tr>
							 

						</table>
						<!-- hidden -->
						<input type="hidden" id="mode" name="mode" value="${param.mode}" />
					</form>
					
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitCopyConfig();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();" class="btnwithico" ><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>


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
  
function close()
{
	api.close();
}
   
   
function submitCopyConfig(mode)
{

	var url = "<cms:BasePath/>site/copySiteCfg.do?mode="+$('#mode').val()+"&siteId="+$('#siteId').val()+"&<cms:Token mode='param'/>";

	var tip = W.$.dialog.tips('正在执行同步,请耐心等待...',3600000000,'loading.gif');
						
   $.ajax
   (
   {type:'POST',async:true,url:url,success:
	   function(data, textStatus)
	   {      
	       
	    	var obj = eval("("+data+")");
	    	
	    	if('success' == obj)
	    	{
	    		W.$.dialog({ 
				   					title :'提示',
				    				width: '160px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				parent:api,
				                    content: '执行同步成功！',
				                    
				                    ok: function () { 
				                    	W.window.location.reload();
				                    }
				})
	    		
	    	}
	    	else
	    	{
	    		W.$.dialog({ 
				   					title :'提示',
				    				width: '160px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/fail.png', 
				    				parent:api,
				                    content: '执行同步失败！若无权限请联系管理员',
				                    
				                    ok: function () { 
				                    	W.window.location.reload();
				                    }
				})
	    	
	    	
	    	}
	   	}
	 }
	);
   
}

</script>
</cms:CurrentSite>
</cms:LoginUser>