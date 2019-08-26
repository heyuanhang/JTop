<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>


		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/swfupload.js"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/handlerSingleImage.js"></script>

		<script>  
		
		 var api = frameElement.api, W = api.opener; 
         if("true"==="${param.fromFlow}")
         {  
         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
         }
         
         <cms:CurrentSite>
         var swfu;
			window.onload = function () {
			swfu = new SWFUpload({
			upload_url: "<cms:Domain/>content/multiUpload.do;jsessionid=<cms:SID/>",
			
			// File Upload Settings
			file_size_limit : "${CurrSite.imageMaxC} MB", 
			file_types : "${CurrSite.imageATVal}",//设置可上传的类型
			file_types_description : "上传文件",
			file_upload_limit : "2",
			button_action : -100,

			
			file_queue_error_handler : fileQueueError,//选择文件后出错
			file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
			file_queued_handler : fileQueued,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadCompleteModuleForPhotoGroup,
			
			// Button Settings
			button_image_url : "<cms:Domain/>core/javascript/upload/SWFUpload/upload.png",
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
			flash_url : "<cms:Domain/>core/javascript/upload/SWFUpload/swfupload.swf",
			use_query_string : true,
			custom_settings : {
			upload_target : "divFileProgressContainer"
			},
			
			// Debug Settings
			debug: false //是否显示调试窗口
			
			
			});
			};
			 <cms:CurrentSite>
			
			imageOrder = '${param.imageOrder}';
			
			//让逻辑知道当前已经有图片存在
      		prevFileId = '-1';
      		
      		 

          	
      </script>
	</head>
	<body>
	<cms:SystemSiteResource resId="${param.resId}">
		<form id="advertPosForm" name="advertPosForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="auntion_tagRoom">
							<ul>
								<li id="two1" onclick="setTab2('two',1,2)" class="selectTag">
									<a href="javascript:;"><img src="../../style/blue/icon/application-share.png" width="16" height="16" />编辑图片&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab2('two',2,2)">
									<a href="javascript:;"><img src="../../style/blue/icon/application-search-result.png" width="16" height="16" />图片资源库&nbsp;</a>
								</li>
							</ul>
							<div id="box" style="left:-9999px;top:-9999px"></div>
						</div>

						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td class="input-title">
													<strong>类型</strong>
												</td>
												<td class="td-input">
													<input type="radio" name="source" class="form-radio" value="server" onclick="javascript:changeSource('s')" checked />
													本地图片
													<input type="radio" name="source" class="form-radio" value="web" onclick="javascript:changeSource('w')" />
													网络图片

												</td>
											</tr>

											<tr id="serverTr">
												<td class="input-title">
													<strong>当前文件</strong>
												</td>
												<td class="td-input">
													<textarea readonly id="fileInfo" name="fileInfo" style="height:60px;width:270px" class="form-textarea">${Res.resName}.${Res.fileType} (${Res.sizeStr})</textarea>
													<span id="spanButtonPlaceholder">选择...</span>
													<div id="divFileProgressContainer" style="width:200px;display:none;"></div>
												</td>
											</tr>

											<tr id="webTr" style="display:none">
												<td class="input-title">
													<strong>网络地址</strong>
												</td>
												<td class="td-input">
													<textarea id="webUrl" name="webUrl" style="height:60px;width:270px" class="form-textarea"></textarea>
													(多地址以逗号分割)
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>目标规格</strong>
												</td>
												<td class="td-input">
													宽:
													<input type="text" size="12" id="width" name="width" class="form-input" value="100"></input>
													&nbsp;高:
													<input type="text" size="12" id="height" name="height" class="form-input" value="90"></input>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>缩放方式</strong>
												</td>
												<td class="td-input">
													<input type="radio" name="pos class=" form-input" value="m" checked />不缩放							
													&nbsp;
													<input type="radio" name="pos class=" form-input" value="l" />最大宽度
													&nbsp;
													<input type="radio" name="pos class=" form-input" value="r" />最大高度
													&nbsp;
													<input type="radio" name="pos class=" form-input" value="r" />强制宽高
												</td>
											</tr>

											<tr>
												<td width="21%" class="input-title">
													<strong>图片名称</strong>
												</td>
												<td class="td-input">
													<input type="text" size="50" id="photoName" name="photoName" class="form-input" value="<cms:UrlParam target="${param.photoName}" />"></input>
												</td>
											</tr>





											<!-- 以下为独立选项 start -->


										</table>
										<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:190px;top:290px;display:none">
											<img style="vertical-align: middle;" src="../../javascript/dialog/skins/icons/loading.gif" width=28 height=28 class="ui_icon_bg" />
											上传处理中......
											<span id="uploadPercent">0%</span>
										</div>
										<div style="height:50px;"></div>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr class="btnbg100">
												<td class="input-title" width="70%"></td>
												<td class="td-input">
													<a id="buttonHref" href="javascript:addNewImage();" class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
													<a href="javascript:close();" class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
												</td>
											</tr>
										</table>
									</li>
								</ul>
							</div>

							<!-- 第二部分:图片资源库 -->
							<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">

								<ul>
									<li>
										<cms:SystemSiteResource classId='${param.classId}' resType='1'>
											<a href="${Res.url}">${Res.resName}</a>


											<br>
											<br>
										</cms:SystemSiteResource>
									</li>
								</ul>
							</div>

						</div>

					</td>
				</tr>
			</table>

			<!-- hidden -->
			<input type="hidden" name="posConfigCount" id="posConfigCount" value="4" />

			<input type="hidden" name="conConfigCount" id="conConfigCount" value="4" />

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

function addNewImage()
{
	var source = getRadioCheckedValue('source');
	
	if('server' == source)
	{
		startUploadFile();
	}
	else if('web' == source)
	{
		var imageUrlArray = document.getElementById('webUrl').value.split(',');
		
		var mw = document.getElementById('width').value;
	    var mh = document.getElementById('height').value;
		var oEditor = W.FCKeditorAPI.GetInstance("content");
	        for(var i = 0 ; i < imageUrlArray.length; i++)
		{				
		
			var webImg = new Image();
    		webImg.src = imageUrlArray[i];
   
	        var newWH = checkSize(webImg.width, webImg.height, mw, mh );
	        	
			if(newWH == 'undefined')
			{
				newWH = checkSize(webImg.width, webImg.height, mw, mh );
			}
			
			if(newWH == 'undefined')
			{
				newWH = checkSize(webImg.width, webImg.height, mw, mh );
			}
			
			if(oEditor.EditMode == W.FCK_EDITMODE_WYSIWYG)
		   	{   
		   		oEditor.InsertHtml("<div style='text-align: center'><img name='jtopcms_content_image' src='" + imageUrlArray[i] + "' alt='' height='"+newWH[1]+"' width='"+newWH[0]+"'/>&nbsp<br><br>"+name+"</div><br><br>");          
		   	}
		}
		
	}
}

function changeSource(flag)
{
	if('s' == flag)
	{
		$('#serverTr').show();
		$('#webTr').hide();
	}
	else if('w' == flag)
	{
		$('#webTr').show();
		$('#serverTr').hide();
	}

}

function close()
{
	api.close();

}


</script>
</cms:SystemSiteResource>
