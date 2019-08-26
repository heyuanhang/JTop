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
		    				
		                    content: '添加音乐素材成功!',

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
							<img src="../style/icons/music.png" width="16" height="16" />音乐信息
					</div>

					<form id="imForm" name="imForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="16%" class="input-title">
									<strong>分类</strong>
								</td>
								<td class="td-input">
									<select id="resTag" name="resTag" class="form-select">
											<option value="">
											------------------ 默认主分类标签 ------------------
											</option>
											<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getResTagForTag" var="music">
											<option value="${Tag.resTagName}">
												${Tag.resTagName}
											</option>
											</cms:QueryData>
											
											
									</select>
								</td>
							</tr>
							
							<tr>
								<td  class="input-title">
										<strong>标题</strong>
									</td>
									<td width="80%" class="td-input">
										<input type="text" style="width:385px;" id="resTitle" name="resTitle" class="form-input"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>缩略图</strong>
								</td>
								<td class="td-input">
									<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
										<tr>
											<td>
												<a class="cmsSysShowSingleImage" id="musicThumbCmsSysShowSingleImage" href="<cms:BasePath/>core/style/blue/images/no-image.gif"><img id="musicThumbCmsSysImgShow" src="<cms:BasePath/>core/style/blue/images/no-image.gif" width="90" height="67" /> </a>
											</td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
													<tr>
														<td>
															&nbsp;
															<input type="button" onclick="javascript:showModuleImageDialog('musicThumbCmsSysImgShow','musicThumb','','','1','0');" value="上传" onclick="" class="btn-1" />
															<input type="button" value="裁剪" onclick="javascript:disposeImage('musicThumb','','',false,'-1');" class="btn-1" />
															<input type="button" value="删除" onclick="javascript:deleteImage('musicThumb');" class="btn-1" />
														</td>
													</tr>
													<tr>
														<td>
															&nbsp;&nbsp;宽&nbsp;&nbsp;
															<input id="musicThumbCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" />
															&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
															<input id="musicThumbCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" />


														</td>
													</tr>
												</table>
												<input id="musicThumb" name="musicThumb" type="hidden" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
								

							 

							<tr>
								<td class="input-title">
									<strong>音乐</strong>
								</td>
								<td class="td-input">
									  <table  border="0"  cellpadding="0" cellspacing="0" class="form-table-upload">
																	<tr>
																		<td>
																		  <table  border="0"  cellpadding="0" cellspacing="0">
																			 <tr>
																			 	<td>
																			 		<div id="sys-obj-wx_sp_file"><table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td width="193">
													<iframe id="wx_sp_file_sys_jtopcms_iframe" frameborder="0" src="<cms:BasePath/>core/weixin/UploadVideoModule.jsp?type=voice" width="390" height="60" scrolling=no></iframe>
												</td>
											</tr>

											<tr>
												<td>
													名称:&nbsp;<input id="wx_sp_file_sys_jtopcms_media_name" readonly style="width: 165px;" maxlength="22" type="text" class="form-input" />&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_showtime" readonly style="width: 40px;" maxlength="20" type="text" class="form-input" />&nbsp;秒&nbsp;&nbsp;&nbsp;<input type="button" value="删除" onclick="javascript:deleteMedia('wx_sp_file');" class="btn-1" />&nbsp;<input type="button" onclick="javascript:showMediaDialog('wx_sp_file','wx_sp_file');" value="上传" class="btn-1" />
													<input id="resName" name="resName" type="hidden" />
													<input id="wx_sp_file" name="wx_sp_file" type="hidden" />
													<input id="wx_sp_file" name="wx_sp_file_delete_flag" type="hidden"/>
                          <input id="wx_sp_file_sys_jtopcms_media_type" name="wx_sp_file_sys_jtopcms_media_type" type="hidden" />
                          <input id="wx_sp_file_sys_jtopcms_media_cover_src" name="wx_sp_file_sys_jtopcms_media_cover_src" type="hidden" />
													<input id="wx_sp_file_sys_jtopcms_media_cover_w" name="wx_sp_file_sys_jtopcms_media_cover_w" type="hidden" />
													<input id="wx_sp_file_sys_jtopcms_media_cover_h" name="wx_sp_file_sys_jtopcms_media_cover_h" type="hidden" />
													<input id="wx_sp_file_sys_jtopcms_media_cover_n" name="wx_sp_file_sys_jtopcms_media_cover_n" type="hidden" />
												</td>
											</tr>
										</table>
																					
																					</div>
																			 	</td>
																			 	<td>
																			 		<div style="width:0px;"></div>
																			 	</td>
																			 </tr>
																		 
																		  </table>
																			
																		</td>
																		
																		
																		
																		
																		
																	</tr>
																</table>								
							</tr>
							
							<tr>
								<td  class="input-title">
										<strong>播放URL</strong>
									</td>
									<td width="80%" class="td-input">
										<input type="text" style="width:385px;" id="musicUrl" name="musicUrl" class="form-input" value="http://"></input>
										 
									</td>
								</tr>
							
							
							<tr>
								<td  class="input-title">
										<strong>高质URL</strong>
									</td>
									<td width="80%" class="td-input">
										<input type="text" style="width:385px;" id="hqMusicUrl" name="hqMusicUrl" class="form-input" value="http://"></input>
										 
									</td>
								</tr>
							<tr>
									<td   class="input-title">
										<strong>描述</strong>
									</td>

									<td class="td-input">
										<textarea id="resDesc" name="resDesc" style="width:385px; height:60px;" class="form-textarea"></textarea>
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
									<a name="btnwithicosysflag" href="javascript:submitForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

initSelect('typeId','${param.typeId}');



function submitForm()
{
	 if($('#musicThumb').val() == -1 || $('#musicThumb').val() == '')
        {
           hasError = true;
        	
        	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '140px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请上传缩略图!',

		    				cancel: true
			});
			
			return;
        }
	
	 if($('#wx_sp_file').val() == -1 || $('#wx_sp_file').val() == '')
        {
           hasError = true;
        	
        	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '140px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请上传音乐资源!',

		    				cancel: true
			});
			
			return;
        }
        
        if( $('#resTitle').val().trim() == '')
        {
           hasError = true;
        	
        	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '140px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '标题不可为空!',

		    				cancel: true
			});
			
			return;
        }
        
	 
	disableAnchorElementByName("btnwithicosysflag",true);
	
	encodeFormInput('imForm', false);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var form = document.getElementById("imForm");
	
	form.action = '<cms:BasePath/>wx/addWxMusic.do';
	
	form.submit();
}


function close()
{
	api.close();
}

//媒体上传
function showMediaDialog(srcId,valId)
{
	W.$.dialog({ 
		id:'smmd',
    	title :'多媒体',
    	width: '520px', 
    	height: '375px', 
    	parent:api, 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,         
        content: 'url:<cms:BasePath/>core/weixin/dialog/MediaUploadModule.jsp?type=voice&classId=-9999&srcId='+srcId+'&valId='+valId+'&api=${param.dialogApiId}'
	});
}


/**
 * 展示音乐截图
 */
function showCover(fieldSign)
{
	var src = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_src').value;
	
	var type = document.getElementById(fieldSign+'_sys_jtopcms_media_type').value;
    
    if('rm' == type)
    {
    	alert('当前音乐类型不支持截取图片!');
    	return;
    }
	
	if('' == src || null == src)
	{	
		W.$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '当前音乐无截取图片!',
    				cancel: true 
		 });
		 return;
	 }
	 
	 var w = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_w').value;
	
	 var h = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_h').value;
	
	 var n = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_n').value;
	 
	 var newWH = checkSize(w, h, 800, 800);
	
	 W.$.dialog
	 ({ 
    	title: n+' ('+w+' x '+h+')', 
    	lock: true,
    	max: false, 
        min: false,
        parent:api,
    	content: '<img src="'+src+'" width="'+newWH[0]+'" height="'+newWH[1]+'" />', 
    	padding: 0 
	 });
}


/**
 * 删除音乐文件信息,等待删除
 */
function deleteMedia(fieldSign)
{
	W.$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除当前音乐吗？',
                    
                    ok: function () { 
                    
                    $("#"+fieldSign +"_delete_flag").val('true');
                    
                    $("#"+fieldSign).val('-1');
                    
					$("#"+fieldSign +"_sys_jtopcms_media_showtime").val('');
	
					$("#"+fieldSign +"_sys_jtopcms_media_width").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_height").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_name").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_type").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_src").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_w").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_h").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_n").val('');
					
					document.getElementById(fieldSign+'_sys_jtopcms_iframe').src ='<cms:BasePath/>core/weixin/UploadVideoModule.jsp?type=voice&fileUrl=&autoStart=false&cover=';
			}, 
    		cancel: true 
	});
	
}


//图片查看效果
loadImageShow();
  
</script>
