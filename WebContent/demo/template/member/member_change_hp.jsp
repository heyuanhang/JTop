<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>
		<!--[if IE 7]>
			<link rel="stylesheet" href="css/font-awesome-ie7.css">
			<![endif]-->
		<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>
		
		 
		<script type="text/javascript" src="${ResBase}js/commonUtil_src.js"></script>
		<script language="javascript" type="text/javascript" src="${ResBase}js/SWFUpload/swfupload.js"></script>
		<script language="javascript" type="text/javascript" src="${ResBase}js/SWFUpload/handSingleImage.js"></script>
		 <script>  
		 basePath = '<cms:BasePath/>';
		  <cms:CurrentSite>
         var swfuH;
			window.onload = function () {
			swfuH = new SWFUpload({
			upload_url: "${SiteBase}content/multiUpload.do;jsessionid=<cms:SID/>",
			
			// File Upload Settings
			file_size_limit : "2 MB", // 100MB
			file_types : "*.png;*.jpeg;*.gif;*.bmp;*.jpg",//设置可上传的类型
			file_types_description : "上传文件",
			file_upload_limit : "150",
			
			file_queue_error_handler : fileQueueError,//选择文件后出错
			file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
			file_queued_handler : fileQueued,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadCompleteModuleH,
			
			// Button Settings
			button_image_url : "${ResBase}js/SWFUpload/upload.png",
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 61,
			button_height: 22,
			button_text : '',
			button_text_style : '',
			button_text_top_padding: 0,
			button_text_left_padding: 18,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			
			
			// Flash Settings
			flash_url : "${ResBase}js/SWFUpload/swfupload.swf",
			use_query_string : true,
			custom_settings : {
			upload_target : "divFileProgressContainer"
			},
			
			// Debug Settings
			debug: false //是否显示调试窗口
			
			
			});
			};
			</cms:CurrentSite>	
				
			 

        	
      </script>
		 
	
	</head>


	<body>
		<!--头部开始-->
		<cms:Include page="../include/head.jsp" />
		<!--头部结束-->

		<cms:Member loginMode="true">
		<!--主体开始-->
		<div class="main_box">
			<!--左侧-->
			<div>

				<div>
					<!--详情页开始-->
					<div>
						<h1 class="ep-h1 bigtitle">
							${Info.title}
						</h1>

						<!--登录-->
						<div class="bs-example">
							<div class="tie-titlebar">
								<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">会员主页</span>
								<ins>

								</ins>
							</div>
							<form id="memForm" name="memForm" method="post">
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
								<tr>
									<td>
									 <cms:Include page="../include/member_left.jsp"></cms:Include>
									</td>
									
									<td>
									
									
									<!-- 右边开始 -->
									
									<table width="100%" border="0" cellpadding="0" cellspacing="12">


									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												会员头像:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
												    
												    	<cms:ResInfo res="${Member.headPhoto}">
                                                          		<cms:if test="${empty Res.url}">
                                                                  <div class="Black20"></div>
                                                                    <p>
                                                                            <img id="headPhotoCmsSysImgShow" src="${ResBase}images/def_avatar.png" height="120" width="120" />
                                                                            <input id="headPhoto" name="headPhoto" type="hidden" value="${Res.resId}" />
                                                                            
                                                                            <span id="spanButtonPlaceholder">选择...</span>
                                                                                    <br />
                                                                                    <span id="uploadPercent"></span>
                                                                    </p>
                                                                    <div class="Black20"></div>
                                                                    
                                                          
		                                                          </cms:if>
		                                                          <cms:else>
                                                                  <div class="Black20"></div>
                                                                    <p>
                                                                            <img id="headPhotoCmsSysImgShow" src="${Res.resize}" height="120" width="120" />
                                                                            <input id="headPhoto" name="headPhoto" type="hidden" value="${Res.resId}" />
                                                                            
                                                                            <span id="spanButtonPlaceholder">选择...</span>
                                                                                    <br />
                                                                                    <span id="uploadPercent"></span>
                                                                    </p>
                                                                    <div class="Black20"></div>
                                                                   
                                                      
                                                      	   	</cms:else>
			
													</cms:ResInfo>	
												    
												    </td>

													<td width="7%">


													</td>
													<td>
														
													</td>

												</tr>

											</table>


										</td>


									</tr>

									

									


								

									<tr>
										<td align="right" width="15%">
											验证码:


										</td>

										<td>
											<table width="40%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="form-control" style="width:80px" maxlength="4" />

													</td>
													<td>
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4" />

													</td>
													<td>

														<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
													</td>

												</tr>
											</table>


										</td>


									</tr>


									</table>
									
									
									<!-- 右边结束 -->
									
									</td>
								
								</tr>
							
							
							</table>
							
							
								





								<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="mh_ly" />

							</form>

							<div class="highlight">

								<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:changeHeadPhoto();">
										确认
									</button> </span>
							</div>
						</div>





					</div>
					<!--新闻详情页结束-->

				</div>


			</div>




		</div>

		<!--主体结束-->
		<div class="main_br"></div>
		<!--主体结束-->

		<cms:Include page="../include/foot.jsp" />

<script>
		 	
                      
  		function changeHeadPhoto()
			{
				var url = '${SiteBase}member/changeHeadPhoto.do';
			    var postData = encodeURI($("#memForm").serialize());
			    //alert(postData);
			    $.ajax({
			      	type: "POST",
			       	url: url,
			       	data:postData,
			   		dataType:'json',
			       	success: function(msg)
			        {        
			            //alert(msg);
			            if('1' == msg)
			            {
							alert('修改头像成功!');
							window.location.href = '${SiteBase}member/member_change_hp.jsp';
			            }
			            else if('-1' == msg)
			            {
							alert('请输入正确的验证码!');
			            	
			            }
						else 
			            {
							alert('修改失败!');
			            	
			            }
						
			        }
			  });	

				 
			}
		
		
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4?rand='+Math.random());

}
</script>




	</body>
</html>
</cms:Member>
