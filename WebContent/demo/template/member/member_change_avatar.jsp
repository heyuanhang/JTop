<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>


		<link href="${ResBase}member/css/style.css" rel="stylesheet" type="text/css"></link>
		
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
			file_size_limit : "5 MB", // 100MB
			file_types : "*.jpg;*.jpeg;*.png",//设置可上传的类型
			file_types_description : "上传文件",
			file_upload_limit : "2",
			
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
		<div class="header">
			<!--头部开始-->
			<cms:Include page="common/member_head.jsp" />
			<!--头部结束-->

			<cms:Member loginMode="true">
		</div>

		<div class="container">

			<!--左边开始-->
			<cms:Include page="common/member_left.jsp" />
			<!--左边开始-->


			<div class="my_myresume_r">


				<div class="switchBox" id="switchBox">
					<h1 class="new_section">
						更换头像
					</h1>
					<div class="eidt-password">
						<form id="memForm" name="memForm" method="post">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="27%" align="right">
										会员头像
									</td>
									<td>
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
								</tr>
								
								
								<tr>
									<td align="right">
										验证码
									</td>
									<td>
									<table width="73%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td  >

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="from-input" style="width:80px" maxlength="4" />
									

													</td>
													<td  >
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8" />

													</td>
													<td  >

														<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
													</td>

												</tr>
											</table>
										 
									</td>
								</tr>
								
								<tr>
									<td align="right">
										&nbsp;
									</td>
									<td>
										<input type="button" name="button" id="button" value="确认修改" class="btn btn4 fl btn-primary" onclick="javascript:changeHeadPhoto();"/>
									</td>
								</tr>
							</table>
						</form>
					</div>


				</div>
			</div>

		</div>
		<div class="footer">
			© 2013 jtopcms.com All Rights Reserved
		</div>
		 
		<script type="text/javascript">
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
			             
			            if('1' == msg)
			            {
							alert('修改头像成功!');
							window.location.href = '${SiteBase}member/member_change_avatar.jsp';
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
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8&rand='+Math.random());

}
</script>
	</body>
</html>

</cms:Member>
