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
		    				
		                    content: '添加视频素材成功!',

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
							<img src="../style/icons/film.png" width="16" height="16" />视频信息
					</div>

					<form id="imForm" name="imForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="11%" class="input-title">
									<strong>分类</strong>
								</td>
								<td class="td-input">
									<select id="resTag" name="resTag" class="form-select" style="width:285px;">
											<option value="">
											--------------- 默认主分类标签 --------------- 
											</option>
											<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getResTagForTag" var="video">
											<option value="${Tag.resTagName}">
												${Tag.resTagName}
											</option>
											</cms:QueryData>
											
											
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>标题</strong></span>
										<input type="text" style="width:285px;" id="resTitle" name="resTitle" class="form-input"></input>	
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							
							 

							 

							<tr>
								<td class="input-title">
									<strong>视频</strong>
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
													<iframe id="wx_sp_file_sys_jtopcms_iframe" frameborder="0" src="<cms:BasePath/>core/weixin/UploadVideoModule.jsp" width="648" height="307" scrolling=no></iframe>
												</td>
											</tr>

											<tr>
												<td>
													视频信息:&nbsp;<input id="wx_sp_file_sys_jtopcms_media_name" readonly style="width: 138px;" maxlength="22" type="text" class="form-input" />&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_showtime" readonly style="width: 40px;" maxlength="20" type="text" class="form-input" />&nbsp;秒&nbsp;&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_width" readonly style="width: 40px;" maxlength="20" type="text" class="form-input" />&nbsp;宽&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_height" readonly style="width: 40px;" maxlength="20" type="text" class="form-input" />&nbsp;高&nbsp;&nbsp;&nbsp;<input type="button" value="阅图" onclick="javascript:showCover('wx_sp_file');" class="btn-1" />&nbsp;<input type="button" value="截图" onclick="javascript:cutCover('wx_sp_file_sys_jtopcms_iframe','wx_sp_file');" class="btn-1" />&nbsp;<input type="button" value="删除" onclick="javascript:deleteMedia('wx_sp_file');" class="btn-1" />&nbsp;<input type="button" onclick="javascript:showMediaDialog('wx_sp_file','wx_sp_file');" value="上传" class="btn-1" />
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
									<td   class="input-title">
										<strong>描述</strong>
									</td>

									<td class="td-input">
										<textarea id="resDesc" name="resDesc" style="width:645px; height:60px;" class="form-textarea"></textarea>
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
		    				
		                    content: '请上传视频资源!',

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
	
	form.action = '<cms:BasePath/>wx/addWxVideo.do';
	
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
        content: 'url:<cms:BasePath/>core/weixin/dialog/MediaUploadModule.jsp?classId=-9999&srcId='+srcId+'&valId='+valId+'&api=${param.dialogApiId}'
	});
}


/**
 * 展示视频截图
 */
function showCover(fieldSign)
{
	var src = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_src').value;
	
	var type = document.getElementById(fieldSign+'_sys_jtopcms_media_type').value;
    
    if('rm' == type)
    {
    	alert('当前视频类型不支持截取图片!');
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
    				
                    content: '当前视频无截取图片!',
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
 * 删除视频文件信息,等待删除
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
    				
                    content: '您确认删除当前视频吗？',
                    
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
					
					document.getElementById(fieldSign+'_sys_jtopcms_iframe').src ='<cms:BasePath/>core/weixin/UploadVideoModule.jsp?fileUrl=&autoStart=false&cover=';
			}, 
    		cancel: true 
	});
	
}


//图片查看效果
loadImageShow();
  
</script>
