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
		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/handlerSingleMedia.js"></script>

		<script>  
		basePath = '<cms:BasePath/>';
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
			file_size_limit : "${CurrSite.mediaMaxC} MB", 
			file_types : "${CurrSite.mediaATVal}",//设置可上传的类型
			file_types_description : "上传文件",
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
			</cms:CurrentSite>
					
          mediaSrcId='${param.srcId}';
          
          mediaValId='${param.valId}';
          
            
          dialogApi = '${param.dialog}';
          
          var api = frameElement.api, W = api.opener; 
	
		  var wd = W;
				
		  if('' != dialogApi)
		  { 
			 wd =  api.get('main_content');
		  }
          
          	
      </script>
	</head>
	<body>




		<form id="advertPosForm" name="advertPosForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top:2px">
							<ul>
								<li id="two1" onclick="setTab(1)" class="selectTag">
									<a href="javascript:;">&nbsp;上传新媒体&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab(2)">
									<a href="javascript:;">&nbsp;已上传文件&nbsp;</a>
								</li>
								<li id="two3" onclick="setTab(3)">
									<a href="javascript:;">&nbsp;媒体资源库&nbsp;</a>
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
												<td width="22%"  class="input-title">
													<strong>类型</strong>
												</td>
												<td class="td-input">
													<input type="radio" disabled name="source" class="form-radio" value="server" onclick="javascript:changeSource('s')" checked />
													本地媒体
													<input type="radio" disabled name="source" class="form-radio" value="web" onclick="javascript:changeSource('w')" />
													网络媒体

												</td>
											</tr>

											<tr id="serverTr">
												<td class="input-title">
													<strong>当前文件</strong>
												</td>
												<td class="td-input">
													<textarea readonly id="fileInfo" name="fileInfo" style="height:80px;width:271px" class="form-textarea"></textarea>
													<span id="spanButtonPlaceholder">选择...</span>
													<div id="divFileProgressContainer" style="width:200px;display:none;"></div>
												</td>
											</tr>

											<tr id="webTr" style="display:none">
												<td class="input-title">
													<strong>网络地址</strong>
												</td>
												<td class="td-input">
													<textarea id="webUrl" name="webUrl" style="height:80px;width:270px" class="form-textarea"></textarea>
													(多地址以逗号分割)
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>文件名</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:272px"  id="posName" name="posName" value="" class="form-input"></input>
												</td>
											</tr>





											<!-- 以下为独立选项 start -->


										</table>
										<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:190px;top:290px;display:none">
											<img style="vertical-align: middle;" src="../../javascript/dialog/skins/icons/loading.gif" width=28 height=28 class="ui_icon_bg" />
											上传处理中......
											<span id="uploadPercent">0%</span>
										</div>
										<div style="height:20px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a id="buttonHref" href="javascript:addNewImage();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>
									</li>
								</ul>
							</div>

							<!-- 第二部分:图片资源库 -->
							<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">

								<ul>
									<li>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="left" valign="top">
													<!--main start-->
													<div style="height:5px;"></div>
													<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

														<tr>
															<td id="uid_td25" style="padding: 2px 6px;">
																<div class="DataGrid">
																	<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																		<tr class="datahead">

																			<td width="1%" height="30">
																				
																			</td>


																			<td width="15%">
																				<strong>文件名</strong>
																			</td>

																			<td width="3%">
																				<strong>类型</strong>
																			</td>

																			<td width="4%">
																				<strong>大小</strong>
																			</td>

																		</tr>

																		<cms:SystemSiteResource classId='${param.classId}' resType='2' pageSize="6">
																			<tr>
																				<td>
																					<input class="form-radio" name="checkResId" value="${Res.resId}" type="radio" />
																				</td>
																				<td>
																					<cms:SubString len="30" tail="..." str="${Res.resName}" />
																				</td>
																				<td>
																					${Res.fileType}
																				</td>
																				<td>
																					${Res.sizeStr}
																				</td>
																			</tr>
																		</cms:SystemSiteResource>
																		<cms:PageInfo>
																		<tr>
																			<td colspan="4" class="PageBar" align="left">
																				<div class="fr">
																					<span class="text_m"> 共 ${Page.totalCount} 条记录 ${Page.currentPage} / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"/>
																						<input type="button" name="goto" value="GOTO" onclick="javascript:jump2();"/>
																					</span>
																					<span class="page">[<a href="javascript:head2();">首页</a>]</span>
																					<span class="page">[<a href="javascript:prev2();">上一页</a>]</span>
																					<span class="page">[<a href="javascript:next2();">下一页</a>]</span>
																					<span class="page">[<a href="javascript:last2();">末页</a>]</span>&nbsp;
																				</div>
																				<script>												
																					function next2()
																					{
																						replaceUrlParam(window.location,'pn=${Page.currentPage+1}&tab=2');
																					}
																					
																					function prev2()
																					{
																						replaceUrlParam(window.location,'pn=${Page.currentPage-1}&tab=2');
																					}
																					
																					function head2()
																					{
																						replaceUrlParam(window.location,'pn=1&tab=2');
																					}
																					
																					function last2()
																					{
																						replaceUrlParam(window.location,'pn=${Page.pageCount}&tab=2');
																					}
																					
																					function jump2()
																					{
																						replaceUrlParam(window.location,'pn='+$('#pageJumpPos').val()+'&tab=2');
																					}																			
																				</script>
																				<div class="fl"></div>
																			</td>
																		</tr>
																		</cms:PageInfo>
																	</table>
																</div>
															</td>
														</tr>


													</table>
													<div style="height:10px;"></div>
													<div class="breadnavTab" >
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr class="btnbg100">
																<div style="float:right">
																	<a id="buttonHref" href="javascript:selectResToCopy();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																	<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
																</div>
															</tr>
														</table>
													</div>
													</td>
													</tr>
													</table>
													 
									</li>
								</ul>
							</div>
							
							
							<!-- 第三部分:资源库 -->
							<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">

								<ul>
									<li>

										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="left" valign="top">
													<!--main start-->
													<div style="height:5px;"></div>
													<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

														<tr>
															<td id="uid_td25" style="padding: 2px 6px;">
																<div class="DataGrid">
																	<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																		<tr class="datahead">

																			<td width="2%" height="30">
																				<input class="inputCheckbox" value="*" onclick="" type="checkbox" />
																			</td>


																			<td width="15%">
																				<strong>文件名</strong>
																			</td>

																			<td width="3%">
																				<strong>类型</strong>
																			</td>

																			<td width="4%">
																				<strong>大小</strong>
																			</td>

																		</tr>

																		 
																		<cms:QueryData objName="Res" service="cn.com.mjsoft.cms.resources.service.ResourcesService" method="getMediaResCovForTag" var="${param.classId},${param.pn},6">
											
																			<tr>
																				<td>
																					<input class="inputCheckbox" name="checkResId" value="${Res.resId}" type="checkbox" />
																				</td>
																				<td>
																					${Res.resName}
																				</td>
																				<td>
																					${Res.fileType}
																				</td>
																				<td>
																					${Res.sizeStr}
																				</td>
																			</tr>
																		</cms:QueryData>
																		<cms:Empty flag="Res">
																			<tr>
																				<td class="tdbgyew" colspan="7">
																					<center>
																						当前没有数据!
																					</center>
																				</td>
																			</tr>
																		</cms:Empty>

																	</table>
																</div>

															</td>
														</tr>
														<tr>
															<td>
																<div class="fr">
																	<cms:PageInfo>

																		<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage} / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump();" /> </span>
																		<span class="page">[<a href="javascript:head();">首页</a>]</span>
																		<span class="page">[<a href="javascript:prev();">上一页</a>]</span>
																		<span class="page">[<a href="javascript:next();">下一页</a>]</span>
																		<span class="page">[<a href="javascript:last();">末页</a>]</span>&nbsp;&nbsp;&nbsp;
																		<script>
																				function next()
																				{
																					replaceUrlParam(window.location,'pn=${Page.currentPage+1}&tab=3');
																				}
																				
																				function prev()
																				{
																					replaceUrlParam(window.location,'pn=${Page.currentPage-1}&tab=3');
																				}
																				
																				function head()
																				{
																					replaceUrlParam(window.location,'pn=1&tab=3');
																				}
																				
																				function last()
																				{
																					replaceUrlParam(window.location,'pn=${Page.pageCount}&tab=3');
																				}
																				
																				function jump()
																				{
																					replaceUrlParam(window.location,'pn='+$('#pageJumpPos').val()+'&tab=3');
																				}
																		</script>
																	</cms:PageInfo>

																</div>

															</td>
														</tr>

													</table>


												</td>
											</tr>


										</table>
										<div style="height:20px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a id="buttonHref" href="javascript:selectResToCopy();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>
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

if('' != '${param.tab}')
{
	setTab(${param.tab});
}


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

function selectResToCopy()
{
	 
    var fieldSign = '${param.valId}';
	var resId = $('input[name="checkResId"]:checked').val();
	
	if('undefined' == typeof(resId) || resId == null)
	{
		W.$.dialog
		({ 
				id:'tip',
				title :'提示',
				width: '150px', 
				height: '60px', 
                lock: true, 
				icon: '32X32/i.png', 
				parent:api,
                content: '没有选择目标媒体资源!',

			 	cancel: function()
			 	{						
			 	}
		 });					
		 return;
	}
	
	var tip = W.$.dialog.tips('正在复制,请等待...',5000,'loading.gif'); 
	
	
	var url = "<cms:BasePath/>resources/copyResource.do?classId=${param.classId}&resId="+resId+"&<cms:Token mode='param'/>";
	
 	$.ajax({
      	type: 'POST',
       	url: url,
       	data:'',
   
       	success: function(mg)
        {        
        	var msg = eval("("+mg+")");
           		
        
        	if(mg.indexOf('error:') != -1)
        	{
        		tip.close();
        		 
        		W.$.dialog
				({ 
							id:'filetip',
		   					title :'提示',
		    				width: '160px', 
		    				height: '60px', 
		                    lock: true, 
		    				icon: '32X32/i.png', 
		    				parent:api,
		                    content: msg.replace('error:',''),

		   				 	cancel: function()
		   				 	{						
		   				 	}
		    	});	
        	}
        	else
        	{
        		var window = W;
			 
				if('' != dialogApi)
				{ 
					window =  api.get('main_content');
				}
        	
        		 var jsonObj = eval("("+mg+")");
        		 
        		 <cms:CurrentSite>
        		 var siteRoot = '${CurrSite.siteRoot}';
        		 var mediaRoot = '${CurrSite.siteMediaPrefixUrl}';
        		 
        		 </cms:CurrentSite>
        		 
        		 var fileUrl = mediaRoot+'/'+jsonObj.obj_0.resSource;
        	
				 window.document.getElementById(fieldSign+'_sys_jtopcms_iframe').src = '<cms:BasePath/>core/content/UploadVideoModule.jsp?fileUrl='+fileUrl+'&autoStart=true';
				 
				//用于显示
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_showtime').value = jsonObj.obj_0.duration;
				 	 
				 var resolution = jsonObj.obj_0.resolution;
				 
				 var w, h = 0;
				 
				 if(null != resolution && '' != resolution)
				 {
				 	 var wh = resolution.split('x');
				 	 
				 	 w = wh[0];
				 	 h = wh[1]; 	 
				 }
				 
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_width').value = w;
				 
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_height').value = h;
				 
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_name').value = jsonObj.obj_0.resName;

				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_type').value = jsonObj.obj_0.fileType;
				 
				 //每次上传新的视频需要将以前截取的图片信息置空
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_cover_src').value = '';
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_cover_w').value = '';
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_cover_h').value = '';
				 window.document.getElementById(fieldSign+'_sys_jtopcms_media_cover_n').value = '';
				 
				 //用于cms处理
				 window.document.getElementById(fieldSign).value = jsonObj.obj_0.resId;
				 
				 window.document.getElementById(fieldSign+'_delete_flag').value = 'false';
		     	
		     	 tip.close();
		     	 close();
        	}
        }
     });
     
    
}


function setTab(pos)
{
	setTab2('two',pos,3);
}

function close()
{
	api.close();

}

//优化link
linkBlur();

</script>

