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
		    				
		                    content: '编辑图片素材成功!',

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
		    validate('imageRes',1,null,null);
		    
		    
		 })
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->

					<div class="addtit">
							<img src="../style/icons/image.png" width="16" height="16" />图片信息
					</div>
					
					<cms:QueryData objName="WR" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxResForTag"  var="${param.id},,,,">
					<cms:ResInfo res="${WR.imageRes}">
					
					
					<form id="imForm" name="imForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="22%" class="input-title">
									<strong>分类</strong>
								</td>
								<td class="td-input">
									<select id="resTag" name="resTag" class="form-select" style="width:285px;">
											<option value="">
											------------------ 默认主分类标签 ------------------
											</option>
											<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getResTagForTag" var="image">
											<option value="${Tag.resTagName}">
												${Tag.resTagName}
											</option>
											</cms:QueryData>
											
											
									</select>
								</td>
							</tr>

							 

							<tr>
								<td class="input-title">
									<strong>素材</strong>
								</td>
								<td class="td-input">
									<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
													<tr>
														<td>
															<cms:if test="${empty Res.url}">
																<a class="cmsSysShowSingleImage" id="imageResCmsSysShowSingleImage" href="<cms:BasePath/>core/style/blue/images/no-image.gif"><img id="imageResCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>			
															</cms:if>
															<cms:else>
																<a class="cmsSysShowSingleImage" id="imageResCmsSysShowSingleImage" href="${Res.url}"><img id="imageResCmsSysImgShow" src="${Res.resize}" width="90" height="67" /> </a>
															</cms:else>
															</td>
														<td>
															<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																<tr>
																	<td>
																		&nbsp;
																		<input type="button" onclick="javascript:showModuleImageDialog('imageResCmsSysImgShow','imageRes','','','0','0')" value="上传" onclick="" class="btn-1" />
																		<input type="button" value="裁剪" onclick="javascript:disposeImage('imageRes','','',false,'-1');" class="btn-1" />
																		<input type="button" value="删除" onclick="javascript:deleteImage('imageRes');" class="btn-1" />
																	</td>
																</tr>
																<tr>
																	<td>
																		&nbsp;&nbsp;宽&nbsp;&nbsp;
																		<input id="imageResCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Res.imageW}" />
																		&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																		<input id="imageResCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Res.imageH}" />
																	</td>
																</tr>
															</table>
															<input id="imageRes" name="imageRes" type="hidden" value="${Res.resId}" />
															<input id="imageRes_sys_jtopcms_old" name="imageRes_sys_jtopcms_old" type="hidden" value="${Res.resId}" />
														</td>
													</tr>
												</table>
								</td>
							</tr>



						</table>
						<!-- hidden -->
						<input id="wrId" name="wrId" type="hidden" value="${WR.wrId}"/>
						
						<cms:Token mode="html"/>
						
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:censor('1');"  class="btnwithico"><img src="../style/icons/light-bulb.png" width="16" height="16" /><b>通过&nbsp;</b> </a>
									<a name="btnwithicosysflag" href="javascript:censor('0');"  class="btnwithico"><img src="../style/icons/light-bulb-off.png" width="16" height="16" /><b>无效&nbsp;</b> </a>
									
									
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
								&nbsp;
								</div>
								</td>
								 
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

initSelect('resTag', '${WR.resTag}');



function censor(flag)
{
	
	var url = "<cms:BasePath/>wx/censor.do?saId=${param.saId}&censor="+flag+"&<cms:Token mode='param'/>";
	
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {      
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg || 'error:not_upload' == msg)
			               {
			               		 	
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '140px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				api:true,
				                    content: '审核操作成功!',
				                    
				                    ok: function () 
				                    { 
				                    	W.window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               }
			               else if(mg.indexOf('success') != -1)
			               {
			               		 	
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '140px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				api:true,
				                    content: '审核操作成功!',
				                    
				                    ok: function () 
				                    { 
				                    	W.window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               }
			               else if('sendover' == msg)
			               {
			               	       W.$.dialog(
								   { 
									   					title :'提示',
									    				width: '260px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				api:true,
									                    content: "已经推送到微信的群发不可再审核！",
							
									    				cancel: function () 
									                    { 
									                    	W.window.location.reload();
									                    }
									});
			               }   
			               
			               else if(mg.indexOf('errcode') != -1)
				           {
				           		W.$.dialog({ 
					   					title :'提示',
					    				width: '270px', 
					    				height: '60px', 
					    				 api:true,
					                    lock: true, 
					    				icon: '32X32/fail.png',
					                    content: msg, 
					                    ok: function(){ 
				      						W.window.location.reload();
				    					} 
						  		});
				           		
				           } 
			               
			               else
			               {
			               	       W.$.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				api:true,
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: function () 
									                    { 
									                    	W.window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
	  
}




function close()
{
	api.close();
}


//图片查看效果
loadImageShow();
  
</script>
</cms:ResInfo>
</cms:QueryData>
