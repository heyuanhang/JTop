<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/upload/SWFUpload/swfupload.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/upload/SWFUpload/handlerSingleMediaRes.js"></script>

		<script>
			basePath = '<cms:BasePath/>';
			
		 	var api = frameElement.api, W = api.opener; 
		 	
			if("true"==="${param.fromFlow}")
         	{         
      			var api = frameElement.api, W = api.opener; 
		          
             	api.close();     
            	 W.window.location.reload();
        	 }
        	 
			<cms:CurrentSite>
			var swfu;
			window.onload = function () {
			swfu = new SWFUpload({
			upload_url: "<cms:Domain/>content/multiUpload.do;jsessionid=<cms:SID/>",
			
			// File Upload Settings
			file_size_limit : "5000 MB", 
			file_types : "*.swf;*.flv;*.f4v;*.avi;*.mpg;*.mpeg;*.mp4;*.rm;*.rmvb;*.3gp;*.wmv;*.wma;*.mp3;*.wav",//设置可上传的类型
			file_types_description : "上传媒体文件",
			file_upload_limit : "2",
			button_action : -100,
			
			file_queue_error_handler : fileQueueError,//选择文件后出错
			file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
			file_queued_handler : fileQueued,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadCompleteModule,
			
			// Button Settings
			button_image_url : "<cms:BasePath/>core/javascript/upload/SWFUpload/upload.png",
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
			flash_url : "<cms:BasePath/>core/javascript/upload/SWFUpload/swfupload.swf",
			use_query_string : true,
			custom_settings : {
			upload_target : "divFileProgressContainer"
			},
			
			// Debug Settings
			debug: false //是否显示调试窗口
			
			
			});
			};
			
			
			
			entry = '${param.entry}';
			
			</cms:CurrentSite>
			
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
			<tr>
				<td width="10%" class="input-title">
					<strong></strong>
				</td>
				<td class="td-input">
					<textarea readonly id="fileInfo" name="fileInfo" class="form-textarea" readonly style="width:370px; height:80px;"></textarea>
					<span id="spanButtonPlaceholder">选择...</span>
					<div id="divFileProgressContainer" style="width:200px;display:none;"></div>
				</td>
			</tr>
		</table>

		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:startUploadFile();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
						<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>取消&nbsp;</b> </a>
					</div>
				</tr>
			</table>
		</div>

		</table>





		<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:190px;top:110px;display:none">
			<img style="vertical-align: middle;" src="../javascript/dialog/skins/icons/loading.gif" width=28 height=28 class="ui_icon_bg" />
			上传处理中......
			<span id="uploadPercent">0%</span>
		</div>
	</body>
</html>

<script type="text/javascript">




function close()
{
	var api = frameElement.api, W = api.opener; 	          
    api.close();      
}
				
									
</script>

