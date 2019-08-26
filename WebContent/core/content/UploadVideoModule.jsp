<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>上传视频</title>

		<!--加载 js -->
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
	 
		<script language="javascript" type="text/javascript" src="../extools/player/jwplayer/jwplayer.js"></script>
		<script>
		basePath = '<cms:BasePath/>';
		
		</script>

	</head>
	<body style="margin:0px; padding:0px;">
		<script>		
		 
		insertVideoPlayerForIE('${param.fileUrl}','${param.autoStart}','${param.cover}'); 
		</script>
	</body>


</html>
<script>

function getPlayPosAndSnap(relateUrl, id)
{
    var type = window.parent.window.document.getElementById(id+'_sys_jtopcms_media_type').value;
  
    if('rm' == type)
    {
    	alert('当前类型视频暂不支持截图功能!');
    	return;
    }
    
	$.dialog.prompt('请输入需要截取的时间位置', 
    function(val){ 
        
        if(val <=0)
		{
			$.dialog({ 
	    	title : '提示',
	    	width: '180px', 
	    	height: '60px',
	    	icon: '32X32/i.png', 
	    	cancel:true,
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	  
	        content: '视频截取时间超出范围!'
	
			});
			return;
		}
        
        $.dialog.tips('正在处理视频...',500,'loading.gif');
    
	    var postData = '?pos='+val+'&classId='+window.parent.window.document.getElementById("classId").value+'&resInfo='+relateUrl+"&random="+Math.random();
 	   	var url = "<cms:BasePath/>/content/snapshotImage.do"+postData;
 	
 		$.ajax
 		({
			type : 'POST',
			url  : encodeURI(url),
			success:
			function(data, textStatus)
			{
				if('no file' != data)
				{
				   var jsonObj = eval("("+data+")");
				   
				   window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_src').value = jsonObj.obj_0.imageUrl;
				   
				   window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_w').value = jsonObj.obj_0.width;
					
				   window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_h').value = jsonObj.obj_0.height;
					
				   window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_n').value = jsonObj.obj_0.imageName;
				   
				   $.dialog.tips('截取图片成功!',1,'tips.gif'); 
				}
				else
				{
					alert('截取视频图片失败!');
				}
  			}
  		});
    }, 
    '1.00' 
);


}



function snapshotImage(id)
{
	var relateUrl = window.parent.window.document.getElementById(id).value;	
	
	if(relateUrl == '')
	{
		alert('没有上传视频或视频失效!');
		return;
	}
	
	var jw = jwplayer();
	
	var pos = 0;
	if('undefined' == typeof(jw))
	{
		getPlayPosAndSnap(relateUrl,id);
		return;
	
	}
	else
	{
		pos =jwplayer().getPosition();
	}
	

	if(pos <=0)
	{
		alert('时间超出范围，请播放视频到需要截取的位置!');
		return;
	}
 
    var tipd = window.parent.window.W.$.dialog.tips('正在处理视频...',500,'loading.gif');
    
	    var postData = '?pos='+pos+'&classId='+window.parent.window.document.getElementById("classId").value+'&resInfo='+relateUrl+"&random="+Math.random();
 	   	
 	   	var url = "<cms:BasePath/>/content/snapshotImage.do"+postData;
 	
 		$.ajax
 		({
			type : 'POST',
			url  : encodeURI(url),
			success:
			function(da, textStatus)
			{
				var data = eval("("+da+")");
           		
				if('no file' != data)
				{ 
				    var jsonObj = data;
					
					<cms:CurrentSite>
					var siteRoot = '${CurrSite.siteRoot}';
					var videoBase = '${CurrSite.mediaRoot}';
					</cms:CurrentSite>
				    
				    
				    window.parent.window.document.getElementById(id+'_sys_jtopcms_iframe').src = '<cms:BasePath/>core/content/UploadVideoModule.jsp?fileUrl=${param.fileUrl}&autoStart=false&cover='+jsonObj.obj_0.imageUrl;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_src').value = jsonObj.obj_0.imageUrl;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_w').value = jsonObj.obj_0.width;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_h').value = jsonObj.obj_0.height;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_n').value = jsonObj.obj_0.imageName;
					
					tipd.close();
				}
				else
				{
					alert('截取视频图片失败!');
				}
  			}
  		});
}



</script>

