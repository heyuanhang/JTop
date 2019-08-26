
//字段标志
var fieldSign = '';

var mode = '';

var serverDataArray = new Array();

//当前操作的栏目
var targetClassId;

//所有图片组
var imageGroup = new Array();

//var upa = new Array();

//所有已经上传的图片
var allUploadImageInfoArray = new Array();

//已经存在的图片
var allExistImageInfoArray = new Array();

//已经删除的图片
var mustDeleteImageInfoArray = new Array();

var tip;




function fileQueueError(file, errorCode, message) {
	
	//var api = frameElement.api, W = api.opener; 
	
	try {
		var imageName = "<font color='red'>文件上传错误</font>";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}

		if (errorName !== "") {
			//alert(errorName);
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			 $.dialog.tips('文件大小为0...',1);
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			 $.dialog.tips('文件大小超过限制...',1); 
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		case -100:
			alert('超过上传允许数量限制...');
			break;
		default:
			//alert(message);
			break;
		}
		
	} catch (ex) {
		this.debug(ex);
	}
}


function buildFileUpload(sbase, rbase, fsize, ftype,  flimit, field, ph)
{
	if(ph == '' || ph == null)
	{
		ph = 'spanButtonPlaceholder';
	}
	
	
	try{
		swfu = new SWFUpload({
		upload_url: sbase+"content/multiUpload.cmd",
		
		// File Upload Settings
		file_size_limit : fsize+" MB", // 1000MB
		file_types : ftype+'',//设置可上传的类型
		file_types_description : '文件',
		file_upload_limit : "20",
		post_params: {"fln" : field+''},
		file_queue_error_handler : fileQueueError,//选择文件后出错
		file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
		file_queued_handler : fileQueuedFile,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadCompleteFile,
		
		// Button Settings
		button_image_url :  rbase+"js/SWFUpload/scbu.png",
		button_placeholder_id : ph,
		button_width: 95,
		button_height: 30,
		button_text : '',
		button_text_style : '',
		button_text_top_padding: 10,
		button_text_left_padding: 18,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		
		// Flash Settings
		flash_url : rbase+"js/SWFUpload/swfupload.swf",
		
		use_query_string : true,
		custom_settings : {
		upload_target : "divFileProgressContainer"
		},
		
		// Debug Settings
		debug: false //是否显示调试窗口
		
		
		});
		
		
		return swfu;
		
		}catch(ex)
		{
			alert(ex);
		}



}

function buildImgUpload(sbase, rbase, fsize, ftype,  flimit, field, ph)
{
	if(ph == '' || ph == null)
	{
		ph = 'spanButtonPlaceholder';
	}
	
	
	try{
		swfu = new SWFUpload({
		upload_url: sbase+"content/multiUpload.cmd",
		
		// File Upload Settings
		file_size_limit : fsize+" MB", // 1000MB
		file_types : ftype+'',//设置可上传的类型
		file_types_description : '图片文件',
		file_upload_limit : flimit,
		post_params: {"fln" : field+''},
		file_queue_error_handler : fileQueueError,//选择文件后出错
		file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
		file_queued_handler : fileQueued,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		
		// Button Settings
		button_image_url : rbase+"js/SWFUpload/scbu.png",
		button_placeholder_id : ph,
		button_width: 95,
		button_height: 30,
		button_text : '',
		button_text_style : '',
		button_text_top_padding: 10,
		button_text_left_padding: 18,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		
		// Flash Settings
		flash_url : rbase+"js/SWFUpload/swfupload.swf",
		
		use_query_string : true,
		custom_settings : {
		upload_target : "divFileProgressContainer"
		},
		
		// Debug Settings
		debug: false //是否显示调试窗口
		
		
		});
		
		
		return swfu;
		
		}catch(ex)
		{
			alert(ex);
		}



}


function buildMupImgUpload(sbase, rbase, fsize, ftype, field, ph)
{
	if(ph == '' || ph == null)
	{
		ph = 'spanButtonPlaceholder';
	}
	
	
	
	try{
		swfu = new SWFUpload({
		upload_url: sbase+"content/multiUpload.cmd",
		
		// File Upload Settings
		file_size_limit : fsize+" MB", // 1000MB
		file_types : ftype+'',//设置可上传的类型
		file_types_description : '图片文件',
		file_upload_limit : 20,
		post_params: {"fln" : field+''},
		file_queue_error_handler : fileQueueError,//选择文件后出错
		file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
		file_queued_handler : fileQueuedMup,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadMupComplete,
		
		// Button Settings
		button_image_url : rbase+"js/SWFUpload/scbu.png",
		button_placeholder_id : ph,
		button_width: 95,
		button_height: 30,
		button_text : '',
		button_text_style : '',
		button_text_top_padding: 10,
		button_text_left_padding: 18,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		
		// Flash Settings
		flash_url : rbase+"js/SWFUpload/swfupload.swf",
		
		use_query_string : true,
		custom_settings : {
		upload_target : "divFileProgressContainer"
		},
		
		// Debug Settings
		debug: false //是否显示调试窗口
		
		
		});
		
		//upa[field] = swfu;
		
		
		return swfu;
		
		}catch(ex)
		{
			alert(ex);
		}



}



function buildSingleImgUpload(sbase, rbase, fsize, ftype, field, ph)
{
	if(ph == '' || ph == null)
	{
		ph = 'spanButtonPlaceholder';
	}
	

	try{
		swfu = new SWFUpload({
		upload_url: sbase+"content/multiUpload.cmd",
		
		// File Upload Settings
		file_size_limit : fsize+" MB", // 1000MB
		file_types : ftype+'',//设置可上传的类型
		file_types_description : '图片文件',
		file_upload_limit : 20,
		post_params: {"fln" : field+''},
		file_queue_error_handler : fileQueueError,//选择文件后出错
		file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
		file_queued_handler : fileQueued,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadSingleComplete,
		
		// Button Settings
		button_image_url : rbase+"js/SWFUpload/scbu.png",
		button_placeholder_id : ph,
		button_width: 95,
		button_height: 30,
		button_text : '',
		button_text_style : '',
		button_text_top_padding: 10,
		button_text_left_padding: 18,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		
		// Flash Settings
		flash_url : rbase+"js/SWFUpload/swfupload.swf",
		
		use_query_string : true,
		custom_settings : {
		upload_target : "divFileProgressContainer"
		},
		
		// Debug Settings
		debug: false //是否显示调试窗口
		
		
		});
		
			
		
		return swfu;
		
		}catch(ex)
		{
			alert(ex);
		}



}

/**
 * 当文件选择对话框关闭消失时，如果选择的文件成功加入上传队列，
 * 那么针对每个成功加入的文件都会触发一次该事件（N个文件成功加入队列，就触发N次此事件）。
 * @param {} file
 * id : string,			    // SWFUpload控制的文件的id,通过指定该id可启动此文件的上传、退出上传等
 * index : number,			// 文件在选定文件队列（包括出错、退出、排队的文件）中的索引，getFile可使用此索引
 * name : string,			// 文件名，不包括文件的路径。
 * size : number,			// 文件字节数
 * type : string,			// 客户端操作系统设置的文件类型
 * creationdate : Date,		// 文件的创建时间
 * modificationdate : Date,	// 文件的最后修改时间
 * filestatus : number		// 文件的当前状态，对应的状态代码可查看SWFUpload.FILE_STATUS }
 */
function fileQueued(file)
{
	  
	 
 fieldSign = this.getSetting('post_params')['fln'];
 
	this.addPostParam("type", 1);
	this.addPostParam("classId", targetClassId);
	this.addPostParam("file.size",file.size);
	this.addPostParam("file.type",file.type);
	this.addPostParam("fileCount", this.getStats().files_queued);
	
	
	tip =  $.dialog.tips('正在上传...',3600000000,'loading.gif');
    startUploadFile(this);
	 
	
	
	
}

function fileQueuedFile(file)
{
	  
	 
 fieldSign = this.getSetting('post_params')['fln'];
 
	this.addPostParam("type", 5);
	this.addPostParam("classId", targetClassId);
	this.addPostParam("file.size",file.size);
	this.addPostParam("file.type",file.type);
	this.addPostParam("fileCount", this.getStats().files_queued);
	
	
	tip =  $.dialog.tips('正在上传...',3600000000,'loading.gif');
    startUploadFile(this);
	 
	
	
	
}

function fileQueuedMup(file)
{
	  
	 
 fieldSign = this.getSetting('post_params')['fln'];
 
	this.addPostParam("type", 1);
	this.addPostParam("classId", targetClassId);
	this.addPostParam("file.size",file.size);
	this.addPostParam("file.type",file.type);
	this.addPostParam("fileCount", this.getStats().files_queued);
	
	
	 tip = $.dialog.tips('正在上传...',3600000000,'loading.gif');
    startUploadFile(this);
	 
	
	
	
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
 
	try {
		if (numFilesQueued > 0) {
			//document.getElementById('btnCancel').disabled = "";
			//this.startUpload();
			//document.getElementById("divProcessing").style.display='';
		}
	} catch (ex) {
		this.debug(ex);
	}
}


function startUploadFile(uobj)
{	 
	//var api = frameElement.api, W = api.opener; 
	if(uobj.getStats().files_queued >0)
	{
		//动态设定参数在此设定
		//swfu.addPostParam("maxWidth", document.getElementById('maxWidth').value);
		//swfu.addPostParam("maxHeight", document.getElementById('maxHeight').value);
		//swfu.addPostParam("disposeMode", document.getElementById('disposeMode').value);
		//swfu.addPostParam("titleName", encodeURI(document.getElementById('titleName').value));
		
        //swfu.addPostParam("mark", getRadioCheckedValue('needMark'));
        
		//document.getElementById("divProcessing").style.display='';
		 
 		uobj.startUpload();
	}
	else
	{
		if('undefined' != typeof(imageOrder) && imageOrder != -1)
		{
			//图集独立编辑模式，可以只改动名称
			 W.document.getElementById('photoName-'+imageOrder).value = document.getElementById('photoName').value;
			 W.document.getElementById('resizeUrl-'+imageOrder).alt = document.getElementById('photoName').value;
			 
			 api.close();
		}
		else
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
	                    content: '请选择所需上传文件',
	                    
	   				 	cancel: true 
	    	});	
		}
	
		
	}
}

function uploadProgress(file, bytesLoaded) {

	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		
		if (percent > 100) {
			percent = 100;
		}
		
		$('#uploadPercent').html(percent+"%");
		//document.getElementById("uploadPercent")
	 

		var progress = new FileProgress(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		 
		if (percent >= 100) {
			progress.setStatus("");//正在创建缩略图...
			progress.toggleCancel(false, this);
		} else {
			progress.setStatus("正在上传...");
			addFileInfo(file.id,"正在上传...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	//try {
		//var progress = new FileProgress(file,  this.customSettings.upload_target);
	//	addFileInfo(file.id,"文件上传完成");
	//	addFileId(file.id,serverData);
	 
		
		serverDataArray.push(serverData);
		
		
	//} catch (ex) {
	//	this.debug(ex);
	//}
}

function addFileId(fileId,id){
	var row = document.getElementById(fileId);
	row.cells[4].innerHTML = id;
 
}
function addFileInfo(fileId,message){
	var row = document.getElementById(fileId);
	row.cells[2].innerHTML = "<font color='green'>"+message+"</font>";
}
 

function cancelUpload(){
	var infoTable = document.getElementById("infoTable");
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if(rows==null){
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids[i] = rows[i].id;
	}	
	for(var i=0;i<ids.length;i++){
		deleteFile(ids[i]);
	}	
}

function deleteFile(fileId){
	//用表格显示
	var infoTable = document.getElementById("infoTable");
	var row = document.getElementById(fileId);
	var filePath = row.cells[4].innerHTML;
	//删除上传成功的文件
	$.ajax({
		type : 'post',
		url : "DeleteFileServlet",
		data : 'filePath='+filePath,
		success : function(data) { // 判断是否成功
			//处理被删除的节点
			infoTable.deleteRow(row.rowIndex);
			swfu.cancelUpload(fileId,false);
		},
		error:function(data){
			addFileInfo(fileId,"<font color='red'>删除文件夹出错</a>");
		}
	});
	
}


function uploadSingleComplete(file) {
	 fieldSign = this.getSetting('post_params')['fln'];
	 
	  tip.close();
	//try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else 
		{
			  
			//var currentCount = parseInt(W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value);
			
			var imageCount = 0;
			
			//var sortArray = W.allImageGroupSortInfo[fieldSign];
			
		  // var mw = W.document.getElementById(fieldSign+'CmsSysMaxWidth').value;
		    
		  // var mh = W.document.getElementById(fieldSign+'CmsSysMaxHeight').value;
			
			
			var imc = parseInt($('#'+fieldSign+'CmsSysImageArrayLength').val());		
					
			imageCount = imc  ;
			 		
			for(var i = 0 ; i < serverDataArray.length; i++)
			{
				 	 
				var jsonObj = eval("("+serverDataArray[i]+")");
				
				if('cert' == mode)
				{	  
					 W.$('#ad-'+fieldSign).addClass( 'hide');
					  W.$('#ar-'+fieldSign).removeClass( 'hide');
				
				
				}
				 
				 
				 	 	
				 $('#showImg-'+fieldSign).attr('src',jsonObj.obj_0.imageUrl);
			 
			 	
			 	 $('#showImg-'+fieldSign).attr('src',jsonObj.obj_0.imageUrl);
					 
				 
				  $('#'+fieldSign).val(jsonObj.obj_0.resId);	 
					 
				   $.dialog.tips('上传成功',1.5,'32X32/succ.png');
		 
			      
		       imageCount ++ ;
			}
			
		 	
			
			
			$('#'+fieldSign+'CmsSysImageArrayLength').val(serverDataArray.length + imc);
			
	       
			
		 	
			//为同一页面下一次上传做准备
		 
			serverDataArray = new Array();
			 
			//加载图片显示效果
			//W.loadImageShow();
			
		 	
			//api.close();
			
		}
	//} catch (ex) {
	//	this.debug(ex);
	//}
	

}



function uploadSingleComplete(file) {
	 fieldSign = this.getSetting('post_params')['fln'];
	 
	  tip.close();
	//try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else 
		{
			// var api = frameElement.api, W = api.opener; 
			 
			//var currentCount = parseInt(W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value);
			
			var imageCount = 0;
			
			//var sortArray = W.allImageGroupSortInfo[fieldSign];
			
		  // var mw = W.document.getElementById(fieldSign+'CmsSysMaxWidth').value;
		    
		  // var mh = W.document.getElementById(fieldSign+'CmsSysMaxHeight').value;
			
			
			var imc = parseInt($('#'+fieldSign+'CmsSysImageArrayLength').val());		
					
			imageCount = imc  ;
			 		
			for(var i = 0 ; i < serverDataArray.length; i++)
			{
				 	 
				var jsonObj = eval("("+serverDataArray[i]+")");
				
				if('cert' == mode)
				{	  
					 W.$('#ad-'+fieldSign).addClass( 'hide');
					  W.$('#ar-'+fieldSign).removeClass( 'hide');
				
				
				}
				else if('mup' == mode)
				{ 
				   var im = '<div class="updateBtn">'+
											 
											'<a href="javascript:delIm('+jsonObj.obj_0.resId+')" class="id-result">'+
												'<img src="'+jsonObj.obj_0.imageUrl+'"  >'+
												'<span>删除</span>'+
											'</a>'+
													'</div>';
										
										 
										
             		W.$('#idcon-'+fieldSign).before(im);
             		 
				
				
				}
				 
				 	 	
				 $('#showImg-'+fieldSign).attr('src',jsonObj.obj_0.imageUrl);
			 
			 	
			 	   $('#showImg-'+fieldSign).attr('src',jsonObj.obj_0.imageUrl);
					 
				 
				  $('#'+fieldSign).val(jsonObj.obj_0.resId);	 
					 
				   //W.$.dialog.tips('上传成功',1.5,'32X32/succ.png');
		 
			      
		       imageCount ++ ;
			}
			
		 	
			
			
			$('#'+fieldSign+'CmsSysImageArrayLength').val(serverDataArray.length + imc);
			
	       
			
		 	
			//为同一页面下一次上传做准备
		 
			serverDataArray = new Array();
			 
			//加载图片显示效果
			//W.loadImageShow();
			
		 	
		 
			
		}
	 
	

}

function deleteImg(id, pos)
{
	$('#upimg-'+id).remove();
	$('#'+fieldSign +'-resId-'+pos).val('');
	
	
	//var imc = parseInt($('#'+fieldSign+'CmsSysImageArrayLength').val());
			
			
	//$('#'+fieldSign+'CmsSysImageArrayLength').val(imc-1);
			
	    

}


function uploadMupComplete(file) {
	 fieldSign = this.getSetting('post_params')['fln'];
	 
	 
	//try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else 
		{
			  
			//var currentCount = parseInt(W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value);
			
			var imageCount = 0;
			
			//var sortArray = W.allImageGroupSortInfo[fieldSign];
			
		  // var mw = W.document.getElementById(fieldSign+'CmsSysMaxWidth').value;
		    
		  // var mh = W.document.getElementById(fieldSign+'CmsSysMaxHeight').value;
			
			
			var imc = parseInt($('#'+fieldSign+'CmsSysImageArrayLength').val());		
					
			imageCount = imc  ;
			 		
			for(var i = 0 ; i < serverDataArray.length; i++)
			{
			
					
					 	 
			 		var jsonObj = eval("("+serverDataArray[i]+")");
			 		
			 	 	var pos = i+imc;	
				
				   var im = '<div class="updateBtn" id="upimg-'+jsonObj.obj_0.resId+'">'+
											 
											'<a href="javascript:deleteImg('+jsonObj.obj_0.resId+', '+pos+')" class="id-result">'+
												'<img src="'+jsonObj.obj_0.imageUrl+'"  >'+
												'<span>删除</span>'+
											'</a>'+
											'<input type="hidden" name="'+fieldSign+'-resId-'+pos+'" id="'+fieldSign+'-resId-'+pos+'" value="'+jsonObj.obj_0.resId+'">'+
										 	'<input type="hidden" name="'+fieldSign+'-relatePath-'+pos+'" id="'+fieldSign+'-relatePath-'+pos+'" value="'+jsonObj.obj_0.relatePath+'">'+
										 
													'</div>';
										
									// alert(im);
										
             		 $('#idcon1-'+fieldSign).prepend(im);
             		 
				
				
				 
			      
		       imageCount ++ ;
			}
			
		 	
			 
		 
			$('#'+fieldSign+'CmsSysImageArrayLength').val(serverDataArray.length + imc);
			
	       	
			//为同一页面下一次上传做准备
		 
			serverDataArray = new Array();
			 
			tip.close();
          
          	$.dialog.tips('上传成功!',1.5,'32X32/hits.png'); 
			
		}
	 
	
          
}


function uploadComplete(file) {
	 fieldSign = this.getSetting('post_params')['fln'];
	//try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else 
		{
			//var api = frameElement.api, W = api.opener; 
			 
			//var currentCount = parseInt(W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value);
			
			var imageCount = 0;
			
			//var sortArray = W.allImageGroupSortInfo[fieldSign];
			
		  // var mw = W.document.getElementById(fieldSign+'CmsSysMaxWidth').value;
		    
		  // var mh = W.document.getElementById(fieldSign+'CmsSysMaxHeight').value;
			
			
			var imc = parseInt($('#'+fieldSign+'CmsSysImageArrayLength').val());		
					
			imageCount = imc  ;
			 		
			for(var i = 0 ; i < serverDataArray.length; i++)
			{
				 	 
				var jsonObj = eval("("+serverDataArray[i]+")");
			 
				 
				
				 
				var pName = jsonObj.obj_0.imageName.substring(0,jsonObj.obj_0.imageName.indexOf('.'));	
				   
				var ih = '<li id="upimg-'+jsonObj.obj_0.resId+'"><img src="'+jsonObj.obj_0.resizeImageUrl+'" width="210"   /><s><a href="javascript:deleteImg('+jsonObj.obj_0.resId+', '+imageCount+');">x</a></s></li>'
		       
		       				 +'<input type="hidden" id="'+fieldSign +'-resId-'+imageCount+'" name="'+fieldSign +'-resId-'+imageCount+'" value="'+jsonObj.obj_0.resId+'" />'
										 +'<input type="hidden" id="'+fieldSign +'-relatePath-'+imageCount+'" name="'+fieldSign +'-relatePath-'+imageCount+'" value="'+jsonObj.obj_0.relatePath+'" />'
								 +'<input type="hidden" id="'+fieldSign +'-name-show-'+imageCount+'" name="'+fieldSign +'-name-show-'+imageCount+'" value="'+pName+'" />';
		
	 
		        $('#image-box-'+fieldSign).append(ih);
		       
		       imageCount ++ ;
			}
			
			//var imc = parseInt($('#'+fieldSign+'CmsSysImageArrayLength').val());
			
			
			
			$('#'+fieldSign+'CmsSysImageArrayLength').val(serverDataArray.length + imc);
			
	       
			
			//总图片数
			//计算真实图片数
			//var count = 0;
			//for(var i = 0 ; i< sortArray.length;i++)
			//{
				//if(sortArray[i] != null)
				//{
					 //count++;
				//}
				
			//}
				
			//W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value = count;
			
			//W.document.getElementById(fieldSign+'CmsSysImageGroupCount').innerHTML = W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value;
	
			//为同一页面下一次上传做准备
			 
			serverDataArray = new Array();
		 
			//加载图片显示效果
			//W.loadImageShow();
			
			
			//alert(W.document.getElementById(fieldSign+'CmsSysImageCurrentCount').value);
			
			//api.close();
			tip.close();
		}
	//} catch (ex) {
	//	this.debug(ex);
	//}
	

}


function uploadCompleteFile(file) {
	 fieldSign = this.getSetting('post_params')['fln'];
	//try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else 
		{
			 		
			 	 		
			for(var i = 0 ; i < serverDataArray.length; i++)
			{
				 	  deleteFile(fieldSign);
				var jsonObj = eval("("+serverDataArray[i]+")");
			 
				 var size = 0;
				 
				 var kbSize = jsonObj.obj_0.fileSize;
				 
				 var testKb = Math.floor(kbSize);
				 if(testKb > 1024)
				 {
				 	size = accDiv(jsonObj.obj_0.fileSize,1024 * 1024).toFixed(1)+" MB";	
				 }else
				 {
				 	size = kbSize.toFixed(1)+" KB";	
				 }
	 
				
			 
				var fh = '<li id="file-'+fieldSign+'" class="completed">'+
																'<span class="title">'+
																	'<strong>'+jsonObj.obj_0.Filename+'</strong>'+
																	'<em>('+size+')</em>'+
																'</span>'+
																'<p class="progress"></p>'+
															   
																'<a href="javascript:deleteFile(\''+fieldSign+'\');" class="del-list"  ">删除</a>'+
															'</li>';
				
				 
			 
	 
		        $('#file-list-'+fieldSign).append(fh);
		        
		        $('#'+fieldSign).val(jsonObj.obj_0.resId);
		       
		       
			}
			
		 		 
			serverDataArray = new Array();
		 
			 
			tip.close();
		}
	//} catch (ex) {
	//	this.debug(ex);
	//}
	

}

function deleteFile(fs)
{
	 
		$('#file-'+fs).remove();
		$('#'+fs).val('');
}




//除法函数，用来得到精确的除法结果
	//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
	//调用：accDiv(arg1,arg2)
	//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2)
{
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
}


function disposeImage(imageUrl,w,h,relatePath)
{
	$.dialog({ 
	    id : 'di',
    	title : '编辑图片',
    	width: '830px', 
    	height: '580px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
       // content: 'url:'+base+'/core/content/CropImageArea.jsp?imageUrl='+imageUrl
      
        
        content: 'url:'+basePath+'/core/content/DisposeImage.jsp?imageUrl='+imageUrl+"&width="+w+"&height="+h+"&relatePath="+relatePath

	});
	
}



function uploadError(file, errorCode, message) {
	var imageName =  "<font color='red'>文件上传出错!</font>";
	var progress;
	try {
		 
		switch (errorCode) 
		{
			
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
				try {
					progress = new FileProgress(file,  this.customSettings.upload_target);
					progress.setCancelled();
					progress.setStatus("<font color='red'>取消上传!</font>");
					progress.toggleCancel(false);
				}
				catch (ex1) {
					this.debug(ex1);
				}
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
				try {
					progress = new FileProgress(file,  this.customSettings.upload_target);
					progress.setCancelled();
					progress.setStatus("<font color='red'>停止上传!</font>");
					progress.toggleCancel(true);
				}
				catch (ex2) {
					this.debug(ex2);
				}
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				imageName = "<font color='red'>文件大小超过限制!</font>";
				break;
			default:
				//alert('i:'+message);
				break;
		}
		addFileInfo(file.id,imageName);
	} catch (ex3) {
		this.debug(ex3);
	}

}


function addImage(src) {

	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}

function fadeIn(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn(element, opacity);
		}, rate);
	}
}



/* ******************************************
 *	FileProgress Object
 *	Control object for displaying file info
 * ****************************************** */
/**
 * 此方法目前没用到，显示的层已经隐藏了。如果需要的话，只需要把divFileProgressContainer
 * 这个层的display设置为显示就行
 */
function FileProgress(file, targetID) {
	this.fileProgressID = "divFileProgress";

	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode("上传文件: "+file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).style.height = "75px";
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "上传文件: "+file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;

}
FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};

FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};


