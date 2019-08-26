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
		    				
		                    content: '编辑视频素材成功!',

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
		    validate('videoRes',1,null,null);
		    
		    
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
					
					<cms:QueryData objName="WR" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxResForTag"  var="${param.id},,,,">
					<cms:ResInfo res="${WR.videoRes}">
					 
					 
					 <cms:QueryData objName="SysRes" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getSysResForTag"  var="${Res.resId}">
				
					 
					 <cms:Site siteId="${WR.siteId}">
					 


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
										<input type="text" style="width:285px;"  id="resTitle" name="resTitle" class="form-input" value="${WR.resTitle}"></input>	
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							 

							<tr>
								<td class="input-title">
									<strong>视频</strong>
								</td>
								<td class="td-input"> 
								<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																		<tr>
																			<td>
																				<table  border="0"  cellpadding="0" cellspacing="0">
																									  <tr>
																									 	<td>
																									 		<div id="sys-obj-wx_sp_file"><table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td width="193">
													<iframe id="wx_sp_file_sys_jtopcms_iframe" frameborder="0" src="<cms:BasePath/>core/weixin/UploadVideoModule.jsp?fileUrl=${SysRes.url}&autoStart=false&cover=${Site.siteImagePrefixUrl}${SysRes.cover}" width="648" height="307" scrolling=no></iframe>
												</td>
											</tr>

											<tr>
												<td>
									  视频信息:&nbsp;<input id="wx_sp_file_sys_jtopcms_media_name" readonly style="width: 144px;" maxlength="22" type="text" class="form-input" value="${SysRes.resName}.${SysRes.fileType}"/>&nbsp;&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_showtime" readonly style="width: 40px;" maxlength="20" type="text" class="form-input" value="${SysRes.duration}"/>&nbsp;秒&nbsp;&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_width" readonly style="width: 40px;" maxlength="20" type="text" class="form-input"  />&nbsp;宽&nbsp;&nbsp;<input id="wx_sp_file_sys_jtopcms_media_height" readonly style="width: 40px;" maxlength="20" type="text" class="form-input"  />&nbsp;高&nbsp;&nbsp;&nbsp;<input type="button" value="阅图" onclick="javascript:showCover('wx_sp_file');" class="btn-1" />												
													<input type="button" value="截图" onclick="javascript:cutCover('wx_sp_file_sys_jtopcms_iframe','wx_sp_file');" class="btn-1" />
													<input type="button" value="删除" onclick="javascript:deleteMedia('wx_sp_file');" class="btn-1" />
													<input type="button" onclick="javascript:showMediaDialog('wx_sp_file','wx_sp_file');" value="上传" class="btn-1" />
													<input id="resName" name="resName" type="hidden" value="${SysRes.resName}"/>
													<input id="wx_sp_file" name="wx_sp_file" type="hidden" value="${SysRes.resId}"/>
													<input id="wx_sp_file_sys_jtopcms_old" name="wx_sp_file_sys_jtopcms_old" type="hidden" value="${SysRes.resId}"/>
													<input id="wx_sp_file_sys_jtopcms_old_cover" name="wx_sp_file_sys_jtopcms_old_cover" type="hidden" value="${SysRes.cover}"/>
													<input id="wx_sp_file" name="wx_sp_file_delete_flag" type="hidden"/>
                          <input id="wx_sp_file_sys_jtopcms_media_type" name="wx_sp_file_sys_jtopcms_media_type" type="hidden" value="${SysRes.fileType}"/>
                          <input id="wx_sp_file_sys_jtopcms_media_cover_src" name="wx_sp_file_sys_jtopcms_media_cover_src" type="hidden" value="${Site.siteImagePrefixUrl}${SysRes.cover}"/>
													<input id="wx_sp_file_sys_jtopcms_media_cover_w" name="wx_sp_file_sys_jtopcms_media_cover_w" type="hidden" value="400"/>
													<input id="wx_sp_file_sys_jtopcms_media_cover_h" name="wx_sp_file_sys_jtopcms_media_cover_h" type="hidden" value="292"/>
													<input id="wx_sp_file_sys_jtopcms_media_cover_n" name="wx_sp_file_sys_jtopcms_media_cover_n" type="hidden" value=""/>
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
																	
																	
								</td>							
							</tr>

							<tr>
									<td   class="input-title">
										<strong>描述</strong>
									</td>

									<td class="td-input">
										<textarea id="resDesc" name="resDesc" style="width:645px; height:60px;" class="form-textarea">${WR.resDesc}</textarea>
									</td>
							</tr>

						</table>
						<script>
						
						var dur = '${SysRes.resolution}'.split('x');
						
						
						$('#wx_sp_file_sys_jtopcms_media_width').val(dur[0]);
						
						$('#wx_sp_file_sys_jtopcms_media_height').val(dur[1]);
					 	
						
						</script>
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


//图片查看效果
loadImageShow();
  
</script>

</cms:Site>
</cms:QueryData>
</cms:ResInfo>
</cms:QueryData>
