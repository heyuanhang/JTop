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
		 
		<script type="text/javascript" src="../../javascript/format/editor_content_format.js"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/swfupload.js"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/handlerEditorImage.js"></script>
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
			file_size_limit : "${CurrSite.imageMaxC} MB", // MB
			file_types : "${CurrSite.imageATVal}",//设置可上传的类型
			file_types_description : "上传文件",
			file_upload_limit : "150",
			
			file_queue_error_handler : fileQueueError,//选择文件后出错
			file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
			file_queued_handler : fileQueued,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,
			
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
			flash_url : "<cms:Domain/>core//javascript/upload/SWFUpload/swfupload.swf",
			use_query_string : true,
			custom_settings : {
			upload_target : "divFileProgressContainer"
			},
			
			// Debug Settings
			debug: false //是否显示调试窗口
			
			
			});
			};
			</cms:CurrentSite>	
				
			editorId = '${param.editorId}';
			
			//当前管理站点信息,用于资源定位
		    <cms:CurrentSite>
		    var siteUrl = '${CurrSite.siteUrl}';
		    var siteRoot = '${CurrSite.siteRoot}';
		    var imageRoot = '${CurrSite.imageRoot}';
		    </cms:CurrentSite>
        	
     
			isDialogMode ='${param.isDialogMode}';
        	
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
									<a href="javascript:;">&nbsp;上传新图片&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab(2)">
									<a href="javascript:;">&nbsp;图片资源库&nbsp;</a>
								</li>
							</ul>

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
													<textarea id="fileInfo" name="fileInfo" class="form-textarea" style="word-wrap:normal;height:100px;width:270px" readonly></textarea>
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
													<input type="text" size="4" id="maxWidth" class="form-input" value="500"></input>
													宽度&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="text" size="4" id="maxHeight" class="form-input" value="600"></input>
													高度&nbsp;&nbsp;&nbsp;&nbsp;
													<select class="form-select" id="disposeMode">
														<option value="0">
															原宽高
														</option>
														<option value="1" selected>
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



											<%--<tr id="markTr">
												<td class="input-title">
													<strong>水印</strong>
												</td>
												<td class="td-input">
													<input type="radio" name="needMark" class="form-radio" value="m" checked />
													添加水印&nbsp;&nbsp;
													<input type="radio" name="needMark" class="form-radio" value="l" />
													无需添加 &nbsp;

												</td>
											</tr>



											--%>
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

										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<div style="height:10px;"></div>
													<ul id="products">
														<cms:SystemSiteResource classId='${param.classId}' resType='1' pageSize="8">

															<li>
																<a href="javascript:selectImage('${Res.resId}')"><img src="${Res.resizeImgUrl}" title="${Res.width} x ${Res.height}" width="110" height="90" /> <span><input type="checkbox" name="checkResId" value="${Res.resId}"> <cms:SubString len="7" tail="..." str="${Res.resName}" /> </span> </a>
															</li>

														</cms:SystemSiteResource>

													</ul>

												</td>
											</tr>
											<tr>
												<td>
													<div class="fr">
														<cms:PageInfo>
															
															<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage} / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"/>
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



										<div style="height:35px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a id="buttonHref" href="javascript:selectResToCopy();;"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"/><b>复制&nbsp;</b> </a>
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

			<!-- 用来区分是否为编辑器图片上传模式 -->
			<input type="hidden" id="imageUploadMode" value="1" />

			<input type="hidden" name="posConfigCount" id="posConfigCount" value="4" />

			<input type="hidden" name="conConfigCount" id="conConfigCount" value="4" />

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

var editorImageMark = '0';

var wd = W;
			
if('true' == isDialogMode)
{
	wd = api.get('main_content');
} 


if('' != '${param.tab}')
{   
	setTab(2);
}

<cms:SysClass id="${param.classId}">
editorImageMark = '${Class.editorImageMark}';

$('#maxWidth').val('${Class.editorImageW}');
$('#maxHeight').val('${Class.editorImageH}');

initSelect('disposeMode','${Class.editorImageDM}');

//修正
if('${Class.editorImageW}' == '0' || '${Class.editorImageW}' == '')
{
	$('#maxWidth').val('500');
	
	initSelect('disposeMode','1');
}

if('${Class.editorImageH}' == '0' || '${Class.editorImageH}' == '')
{
	$('#maxHeight').val('700');
}
 

</cms:SysClass>

if('0' == editorImageMark)
{
	$('#markTr').hide();
}




function addNewImage()
{
	var source = getRadioCheckedValue('source');
	
	if('server' == source)
	{
		
		startUploadFile('');

		
	}
	else if('web' == source)
	{
		var imageUrlArray = document.getElementById('webUrl').value.split(',');
		
		var mw = document.getElementById('maxWidth').value;
	    var mh = document.getElementById('maxHeight').value;
	    
	    
	    
	    
	    
	 	//var oEditor = wd.FCKeditorAPI.GetInstance("${param.editorId}");
	       
		

	    for(var i = 0 ; i < imageUrlArray.length; i++)
		{				
		
			var webImg = new Image();
    		webImg.src = imageUrlArray[i];
 

	        var newWH = checkSize(webImg.width, webImg.height, mw, mh );
	        	
			if(typeof(newWH) == 'undefined')
			{
				newWH = [100, 100];
			}
		
			//if(oEditor.EditMode == wd.FCK_EDITMODE_WYSIWYG)
		   //	{    
		   		//oEditor.InsertHtml("<div style='text-align: center'><img name='jtopcms_content_image' src='" + imageUrlArray[i] + "' alt='' height='"+newWH[1]+"' width='"+newWH[0]+"'/>&nbsp<br><br>"+name+"</div><br><br>");          
		   	//}
		   	
		   	wd.ue.execCommand('insertHtml', "<div style='text-align: center'><img name='jtopcms_content_image' src='" + imageUrlArray[i] + "' alt='' height='"+newWH[1]+"' width='"+newWH[0]+"'/>&nbsp<br><br>"+name+"</div><br><br>")      
		}
		
		close();
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

function selectResToCopy()
{
    var fieldSign = '${param.valId}';
	var cidCheck = document.getElementsByName('checkResId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   W.$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '没有选择目标图片！', 
       cancel: true 
                    
	  });
	  return;
	}
    
    
	var resId = ids;
	
	
	 	
	var idArray = ids.split(',');
	
	var id;
	
	var template = '';
	
	//var oEditor = wd.FCKeditorAPI.GetInstance(editorId);
	
	var mw = document.getElementById('maxWidth').value;
	       
	var mh = document.getElementById('maxHeight').value;
	
	<cms:CurrentSite>
		        		 
	var siteUrl = '${CurrSite.siteUrl}' 
	var siteRoot = '${CurrSite.siteRoot}';
	var imageRoot = '${CurrSite.imageRoot}';
		        		 
	</cms:CurrentSite>
	
	for(var i=0; i<idArray.length; i++ )
	{
		if(idArray[i] != '')
		{
			var url = "<cms:BasePath/>resources/copyResource.do?classId=${param.classId}&resId="+idArray[i]+"&<cms:Token mode='param'/>";
	
		 	$.ajax({
		      	type: 'POST',
		       	url: url,
		       	async : false,
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
		        		 var jsonObj = msg;		        		 
		        		 		             		
						 var height = jsonObj.obj_0.height;
						 
				         var width = jsonObj.obj_0.width;
				         
				         var newWH = checkSize(width, height, mw, mh );
				      
						 var postData = '?emType=2&path='+jsonObj.obj_0.resSource+"&resId="+jsonObj.obj_0.resId+"&w="+newWH[0]+"&h="+newWH[1]+"&name="+jsonObj.obj_0.resName;
				 		 var url = basePath+"/channel/getEditorCode.do"+postData;
				 						 		
				 		 $.ajax
				 		 ({
							type:'POST',
							async:false,
							url:url,
							
							success:
							function(data)
							{				
								//if(oEditor.EditMode == wd.FCK_EDITMODE_WYSIWYG)
						   		//{   
						   			//oEditor.InsertHtml(data); 
						   			  
						   		//}
						   		wd.ue.execCommand('insertHtml', data)       
				  			}
				  		 });	
				     				     	 
		        	}
		        }
		     });
		}
		
	}
	
 
	close();
	
     
    
}

function close()
{
	api.close();
}


function setTab(pos)
{
	setTab2('two',pos,2);
}


//优化link
linkBlur();
</script>

