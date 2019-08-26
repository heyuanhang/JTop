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
		<script>  
		var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            	W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '新增Tag词汇成功!',
	
			    				ok: function()
			    				{
			    					W.location.reload();
			    				}
			   });       
       		
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w,]{1,300}$/;

         $(function()
		 {
		    validate('tagNames',1,null,null);
 				
		 })
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/price-tag.png" width="16" height="16" />Tag信息
					</div>

					<form id="tagForm" name="tagForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="24%" class="input-title">
									<strong>所属分类</strong>
								</td>
								<td class="td-input">
									<select id="tagTypeId" name="tagTypeId"  style="width:324px">
											<option value="-9999">
											----------------- 无分类Tag词汇 -------------------
											</option>							
											<cms:QueryData objName="TagType" service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getTagTypeListQueryTag" >
											<option value="${TagType.tagTypeId}">
											${TagType.tagTypeName}
											</option>										
											</cms:QueryData>				
										</select>
								</td>
							</tr>

							<tr>
								<td  class="input-title">
									<strong>Tag名称</strong>
								</td>
								<td class="td-input">
									<input id="tagNames" name="tagNames" type="text" class="form-input" style="width:320px" />
									<span class="red">*</span><br/><span class="ps">若同时添加多个Tag词，用逗号隔离区分</span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>点击数</strong>
								</td>
								<td class="td-input">
									<input id="clickCount" name="clickCount" type="text" class="form-input" style="width:320px"/>

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
									<a name="btnwithicosysflag" href="javascript:submitTagInfo();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico"  ><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

function submitTagInfo()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('tagNames',1,null,null);
        
        if(currError)
        {
        	hasError = true;
        }
        
   
    			
    if(hasError)
    {
        
	    return;

	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
	
	encodeFormInput('tagForm', false);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var form = document.getElementById("tagForm");
	
	form.action = '<cms:BasePath/>channel/addTag.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
