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
		<style type="text/css">
		<!--
		* {
		    margin:0;
		    padding:0;
		    font-size:12px;
		    text-decoration:none;
		}
		#products {
		    width:520px;
		    margin:1px auto;
		}
		#products li {
		    width:115px;
		    height:125px;
		    float:left;
		    margin-top:5px;
		    margin-left:12px;
		    display:inline;
		}
		#products li a {
		    display:block;
		}
		#products li a img {
		    border:1px solid #666;
		    padding:1px;
		}
		#products li span a {
		    width:110px;
		    height:30px;
		    line-height:14px;
		    text-align:center;
		 white-space:nowrap;
		    text-overflow:ellipsis; 
		    overflow: hidden;
		}
		-->
		</style>
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
			
			
          imageSrcId='${param.srcId}';
          
          imageValId='${param.valId}';

		  targetClassId='${param.classId}';
		  
		  isDialogMode = '${param.isDialogMode}';
		  	  
		  apiId = '${param.apiId}';
		  
		  //图集组件替换图片参数
		  groupEditMode = '${param.groupEditMode}';
		  
		  imageOrder = '${param.imageOrder}';
		  
		  fieldSign = '${param.fieldSign}';
		
      	  prevFileId = '-1';
      	  
      	  
          	
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
								<li id="two1" onclick="javascript:setTab(1);" class="selectTag">
									<a href="javascript:;">&nbsp;上传新图片&nbsp;</a>
								</li>
								<li id="two2" onclick="javascript:setTab(2);">
									<a href="javascript:;">&nbsp;图片资源库&nbsp;</a>
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
												<td width="22%" class="input-title">
													<strong>类型</strong>
												</td>
												<td class="td-input">
													<input type="radio" disabled name="source" class="form-radio" value="server" onclick="javascript:changeSource('s')" checked />
													本地图片
													<input type="radio" disabled name="source" class="form-radio" value="web" onclick="javascript:changeSource('w')" />
													网络图片
												</td>
											</tr>

											<tr id="serverTr">
												<td class="input-title">
													<strong>当前文件</strong>
												</td>
												<td class="td-input">
													<textarea readonly id="fileInfo" name="fileInfo" style="height:100px;width:270px" class="form-textarea"></textarea>
													<span id="spanButtonPlaceholder">选择...</span>
													<div id="divFileProgressContainer" style="width:200px;display:none;"></div>
												</td>
											</tr>

											<tr id="webTr" style="display:none">
												<td class="input-title">
													<strong>网络地址</strong>
												</td>
												<td class="td-input">
													<textarea id="webUrl" name="webUrl" style="height:100px;width:270px" class="form-textarea"></textarea>
													(多地址以逗号分割)
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>图片规格</strong>
												</td>
												<td class="td-input">
													<input type="text" size="4" id="maxWidth" class="form-input" value="${param.tw}"></input>
													宽度&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="text" size="4" id="maxHeight" class="form-input" value="${param.th}"></input>
													高度&nbsp;&nbsp;&nbsp;&nbsp;
													<select class="form-select" id="disposeMode">
														<option value="0">
															原宽高
														</option>
														<option value="1" >
															按宽度
														</option>
														<option value="2">
															按高度
														</option>
														<option value="3">
															按宽高
														</option>
													</select>
													缩放
												</td>
											</tr>


											<tr>
												<td class="input-title">
													<strong>图片名</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:270px" id="titleName" name="titleName" class="form-input"></input>
												</td>
											</tr>

											<%--<tr id="markTr">
												<td class="input-title">
													<strong>水印</strong>
												</td>
												<td class="td-input">
													<input type="radio" name="needMark" class="form-radio" value="1" checked />
													添加水印&nbsp;&nbsp;
													<input type="radio" name="needMark" class="form-radio" value="0" />
													无需添加 &nbsp;

												</td>
											</tr>


											--%><!-- 以下为独立选项 start -->


										</table>
										<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:190px;top:310px;display:none">
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
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<div style="height:10px;"></div>
													<ul id="products">
													
														<cms:SystemSiteResource classId='${param.classId}' classChildMode="${param.classChildMode}" resType='1' pageSize="8">

															<li>
																<a href="javascript:selectImage('${Res.resId}')"><img src="${Res.resizeImgUrl}" title="${Res.width} x ${Res.height}" width="110" height="90" /> <span><input type="radio" name="checkResId" value="${Res.resId}"/>
																		<cms:SubString len="7" tail="..." str="${Res.resName}" /> </span> </a>
															</li>

														</cms:SystemSiteResource>

													</ul>

												</td>
											</tr>
											<tr>
												<td>
													<div class="fr">
														<cms:PageInfo>
															
															<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage} / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos">
																<input type="button" name="goto" value="GOTO" onclick="javascript:jump();"/>
															</span>
															<span class="page">[<a href="javascript:head();">首页</a>]</span>
															<span class="page">[<a href="javascript:prev();">上一页</a>]</span>
															<span class="page">[<a href="javascript:next();">下一页</a>]</span>
															<span class="page">[<a href="javascript:last();">末页</a>]</span>&nbsp;&nbsp;&nbsp;
														<script>
																function next()
																{
																	replaceUrlParam(window.location,'pn=${Page.currentPage+1}&tab=2');
																}
																
																function prev()
																{
																	replaceUrlParam(window.location,'pn=${Page.currentPage-1}&tab=2');
																}
																
																function head()
																{
																	replaceUrlParam(window.location,'pn=1&tab=2');
																}
																
																function last()
																{
																	replaceUrlParam(window.location,'pn=${Page.pageCount}&tab=2');
																}
																
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+$('#pageJumpPos').val()+'&tab=2');
																}
														</script>
														</cms:PageInfo>

													</div>

												</td>
											</tr>

										</table>

										<div style="height:10px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a id="buttonHref" href="javascript:selectResToCopy();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"/><b>复制&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

initSelect('disposeMode','${param.dm}');

 

if('' != '${param.tab}')
{
	setTab(2);
}

function addNewImage()
{
	var source = getRadioCheckedValue('source');
	
	if('server' == source)
	{
		if('' == $('#fileInfo').val() || null == $('#fileInfo').val())
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
	                content: '没有选择图片!',
	
				 	cancel: function()
				 	{						
				 	}
			 });					

		}
		else
		{
			startUploadFile();
		}
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
                content: '没有选择目标图片!',

			 	cancel: function()
			 	{						
			 	}
		 });					
		 return;
	}
	
	
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
        		 var jsonObj = eval("("+mg+")");
        		 
        		 <cms:CurrentSite>
        		 
        		 var siteUrl = '${CurrSite.siteUrl}';
        		 var siteRoot = '${CurrSite.siteRoot}';
        		 var imageRoot = '${CurrSite.siteImagePrefixUrl}';
        		 
        		 </cms:CurrentSite>
        		 
        		 var fileUrl = imageRoot+'/'+jsonObj.obj_0.resSource;
	        		 
	             var fileResizeUrl = imageRoot+'/'+jsonObj.obj_0.resSource.replace('/','/imgResize');
        
        		 if('true' == '${param.groupEditMode}')
				 { 
				     fieldSign = '${param.fieldSign}';
				 	
					 var prevRePath = W.document.getElementById(fieldSign+'-relatePath-'+imageOrder).value;
					 
					 W.document.getElementById(fieldSign+'-name-show-'+imageOrder).value = jsonObj.obj_0.resName;
				 
				     W.document.getElementById(fieldSign+'-cmsSysImageShowUrl-'+imageOrder).href = fileUrl;
					 W.document.getElementById(fieldSign+'-resizeUrl-'+imageOrder).src = fileResizeUrl;
					 W.document.getElementById(fieldSign+'-resizeUrl-'+imageOrder).title = jsonObj.obj_0.resName;
					
					 W.document.getElementById(fieldSign+'-resId-'+imageOrder).value = jsonObj.obj_0.resId;
					 W.document.getElementById(fieldSign+'-relatePath-'+imageOrder).value = jsonObj.obj_0.resSource;
					 W.document.getElementById(fieldSign+'-photoName-'+imageOrder).value = jsonObj.obj_0.resName;
					
					 W.document.getElementById(fieldSign+'-height-'+imageOrder).value = jsonObj.obj_0.height;
					 W.document.getElementById(fieldSign+'-width-'+imageOrder).value = jsonObj.obj_0.width;
					 
					 W.document.getElementById(fieldSign+'-height-show-'+imageOrder).value = jsonObj.obj_0.height;
					 W.document.getElementById(fieldSign+'-width-show-'+imageOrder).value = jsonObj.obj_0.width;
					 
					 W.document.getElementById(fieldSign+'-name-show-'+imageOrder).value = jsonObj.obj_0.resName;
					 
					 //若为封面，则替换封面
					 var checkedFlag = W.document.getElementById(fieldSign+'-cover-'+imageOrder).checked;

					 if(checkedFlag)
					 {
						W.document.getElementById(fieldSign+'CmsSysImageCover').value = jsonObj.obj_0.resSource;
					 }
					 //
					 
					 //需要和图集上传模块交互，加入已上传array
					 //W.allUploadImageInfoArray.push(jsonObj.obj_0.relatePath);
					 
					 //需要和图集上传模块交互，将上一个res加入必须删除array
			         //W.mustDeleteImageInfoArray.push(prevRePath);
				 }
				 else
				 {
				 
				 
				 	 if('true' == isDialogMode)
					 {
					 	 //用于显示
					 	 api.get(apiId).document.getElementById(imageSrcId).src = fileResizeUrl;
						 
						 api.get(apiId).document.getElementById(imageSrcId).width = 90;
						 api.get(apiId).document.getElementById(imageSrcId).height = 67;
						 
						 
						 if(api.get(apiId).document.getElementById(imageValId+'CmsSysShowSingleImage') != null)
						 {
					 	 	api.get(apiId).document.getElementById(imageValId+'CmsSysShowSingleImage').href = fileUrl;
						 }
						 
						 if(api.get(apiId).document.getElementById(imageValId+'CmsSysImgWidth') != null)
						 {
						 	api.get(apiId).document.getElementById(imageValId+'CmsSysImgWidth').value= jsonObj.obj_0.width;
						 }
						 
						 if(api.get(apiId).document.getElementById(imageValId+'CmsSysImgHeight') != null)
						 {
						 	api.get(apiId).document.getElementById(imageValId+'CmsSysImgHeight').value= jsonObj.obj_0.height;
						 }
						   //用于cms处理
						 api.get(apiId).document.getElementById(imageValId).value= jsonObj.obj_0.resId;
					   }
					   else
			 	       {
		        		 //用于显示
						 W.document.getElementById(fieldSign+'CmsSysShowSingleImage').href = fileUrl;
						 W.document.getElementById(fieldSign+'CmsSysImgShow').src=fileResizeUrl;
						 
						 W.document.getElementById(fieldSign+'CmsSysImgShow').width = 90;
						 W.document.getElementById(fieldSign+'CmsSysImgShow').height = 67;
		
						 W.document.getElementById(fieldSign+'CmsSysImgWidth').value= jsonObj.obj_0.width;
						 W.document.getElementById(fieldSign+'CmsSysImgHeight').value= jsonObj.obj_0.height;
						 
						  //用于cms处理
						 W.document.getElementById(fieldSign).value= jsonObj.obj_0.resId;
						 
						
				        }
				}
		     	
		     	    
		     	     api.close();
        	}
        }
     });
     
    
}


function setTab(pos)
{
	setTab2('two',pos,2);
}

function close()
{
	api.close();
}

function selectImage(resId)
{
	var imageCheck = document.getElementsByName('checkResId');
	
	for(var i=0; i<imageCheck.length;i++)
	{
		if(imageCheck[i].value == resId)
		{
			if(imageCheck[i].checked == false)
			{
				imageCheck[i].checked = true;
			}
			else
			{
				imageCheck[i].checked = false;
			}
		}
	} 
}

function changeMark()
{
	if($('input[name="needMark"]:checked').val() == '1')
	{
		mark = '1';
	}
	else
	{
		mark = '0';
	}

}

//优化link
linkBlur();


</script>

