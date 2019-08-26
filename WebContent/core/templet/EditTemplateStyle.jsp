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
		    				
		                    content: '编辑模板风格成功!',

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
		    validate('styleName',0,ref_name,'格式为文字,数字,下划线');
 			validate('styleFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->

					<div class="addtit">
							<img src="../style/icons/projection-screen.png" width="16" height="16" />风格信息
					</div>
					<cms:QueryData objName="Ts" service="cn.com.mjsoft.cms.templet.service.TempletService" method="getTemplateStyleForTag" var="${param.tsId}">
											
											
											
					<form id="tsForm" name="tsForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							 

							<tr>
								<td width="26%" class="input-title">
									<strong>风格名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:258px" id="styleName" name="styleName" value="${Ts.styleName}" class="form-input"></input>

								</td>
							</tr>
 
							<tr>
								<td class="input-title">
									<strong>标识</strong>
								</td>
								<td class="td-input">
									<input type="text" disabled style="width:258px" id="styleFlag" name="styleFlag" value="${Ts.styleFlag}" class="form-input"></input>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>效果图</strong>
								</td>
								<td class="td-input">
									 <cms:ResInfo res="${Ts.stylePic}">
										<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td>
													<cms:if test="${empty Res.url}">
														<a class="cmsSysShowSingleImage" id="stylePicCmsSysShowSingleImage" href="<cms:BasePath/>core/style/blue/images/no-image.gif"><img id="stylePicCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>			
													</cms:if>
													<cms:else>
														<a class="cmsSysShowSingleImage" id="stylePicCmsSysShowSingleImage" href="${Res.url}"><img id="stylePicCmsSysImgShow" src="${Res.resize}" width="90" height="67" /> </a>
													</cms:else>
															</td>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
														<tr>
															<td>
																&nbsp;
																<input type="button" onclick="javascript:showModuleImageDialog('stylePicCmsSysImgShow','stylePic','','','0','0')" value="上传" onclick="" class="btn-1" />
																<input type="button" value="裁剪" onclick="javascript:disposeImage('stylePic','','',false,'-1');" class="btn-1" />
																<input type="button" value="删除" onclick="javascript:deleteImage('stylePic');" class="btn-1" />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;&nbsp;宽&nbsp;&nbsp;
																<input id="stylePicCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Res.imageW}" />
																&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																<input id="stylePicCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Res.imageH}" />
															</td>
														</tr>
													</table>
													<input id="stylePic" name="stylePic" type="hidden" value="${Res.resId}" />
													<input id="stylePic_sys_jtopcms_old" name="stylePic_sys_jtopcms_old" type="hidden" value="${Res.resId}" />
												</td>
											</tr>
										</table>
										</cms:ResInfo>
								</td>
							</tr>



						</table>
						<!-- hidden -->
						<input type="hidden" id="tsId" name="tsId" value="${param.tsId}"/>
						
						<cms:Token mode="html"/>
						
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitTs();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

 

function submitTs()
{
 
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('styleFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('styleName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    			
    if(hasError)
    {

	    return;

	}
	
	 
	
	disableAnchorElementByName("btnwithicosysflag",true);
	
	encodeFormInput('tsForm', false);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var form = document.getElementById("tsForm");
	
	form.action = '<cms:BasePath/>templet/editTs.do';
	
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
