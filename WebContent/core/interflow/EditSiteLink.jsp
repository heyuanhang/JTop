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


		<script>  
		basePath='<cms:BasePath/>';
		
		var dialogApiId = '${param.dialogApiId}';
		
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
		    				
		                    content: '编辑友情站点成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});
                 
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('siteName',1,null,null);
		    
		    validate('siteLink',1,null,null);
 			
 				
		 })
         
         
		
        </script>
	</head>
	<body>
		<cms:QueryData objName="Fl" service="cn.com.mjsoft.cms.interflow.service.InterflowService" method="getFriendSiteInfoTag" var="${param.id},">

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/chain.png" width="16" height="16" />友链信息
						</div>

						<form id="flForm" name="flForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td class="input-title">
										<strong>分类</strong>
									</td>
									<td class="td-input">
										<select id="typeId" name="typeId" class="form-select" style="width:258px">
											<option value="-1">
											--------------- 请选择外链分类 ---------------
											</option>
											<cms:QueryData objName="Ft" service="cn.com.mjsoft.cms.interflow.service.InterflowService" method="getFriendSiteTypeTag">
												<option value="${Ft.ltId}">
												&nbsp;${Ft.typeName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</option>											
											</cms:QueryData>
											
											
										</select>
									</td>
								</tr>

								<tr>
									<td width="26%" class="input-title">
										<strong>友站名称</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:258px" id="siteName" name="siteName" class="form-input" value="${Fl.siteName}"></input>

									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>站点URL</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:258px" id="siteLink" name="siteLink" class="form-input" value="${Fl.siteLink}"></input>
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>站点Logo</strong>
									</td>
									<td class="td-input">
									<cms:ResInfo res="${Fl.siteLogo}">
										<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td>
													<cms:if test="${empty Res.url}">
														<a class="cmsSysShowSingleImage" id="siteLogoCmsSysShowSingleImage" href="<cms:BasePath/>core/style/blue/images/no-image.gif"><img id="siteLogoCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>			
													</cms:if>
													<cms:else>
														<a class="cmsSysShowSingleImage" id="siteLogoCmsSysShowSingleImage" href="${Res.url}"><img id="siteLogoCmsSysImgShow" src="${Res.resize}" width="90" height="67" /> </a>
													</cms:else>
															</td>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
														<tr>
															<td>
																&nbsp;
																<input type="button" onclick="javascript:showModuleImageDialog('siteLogoCmsSysImgShow','siteLogo','','','0','0')" value="上传" onclick="" class="btn-1" />
																<input type="button" value="裁剪" onclick="javascript:disposeImage('siteLogo','','',false,'-1');" class="btn-1" />
																<input type="button" value="删除" onclick="javascript:deleteImage('siteLogo');" class="btn-1" />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;&nbsp;宽&nbsp;&nbsp;
																<input id="siteLogoCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Res.imageW}" />
																&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																<input id="siteLogoCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Res.imageH}" />
															</td>
														</tr>
													</table>
													<input id="siteLogo" name="siteLogo" type="hidden" value="${Res.resId}" />
													<input id="siteLogo_sys_jtopcms_old" name="siteLogo_sys_jtopcms_old" type="hidden" value="${Res.resId}" />
												</td>
											</tr>
										</table>
										</cms:ResInfo>
									</td>
								</tr>



							</table>
							<!-- hidden -->
							<input type="hidden" id="flId" name="flId" value="${param.id}"/>
							
							<cms:Token mode="html"/>

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitFl();"  name="btnwithicosysflag" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

initSelect('typeId','${Fl.typeId}');
 
function submitFl()
{
	if($('#typeId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个外链分类!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('siteName',1,null,null);
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('siteLink',1,null,null);

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
	    return;

	}
	 
	disableAnchorElementByName("btnwithicosysflag",true);
	
	encodeFormInput('flForm', false);
	 
	var form = document.getElementById("flForm");
	
	form.action = '<cms:BasePath/>interflow/editFSite.do';
	 
	form.submit();
}


function close()
{
	api.close();
}


//图片查看效果
loadImageShow();
  
</script>
</cms:QueryData>
