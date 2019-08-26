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
		
		
function insertVideoPlayerForIE(fileUrl,autoStart,cover) 
{
	var str = '';
	
	if(fileUrl.lastIndexOf('.rm') != -1)
	{
		str +='<object id="rep1" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width=648 height=275>';
			str +='<param name="_ExtentX" value="11298">';
			str +='<param name="_ExtentY" value="7938">';
			str +='<param name="AUTOSTART" value="0">';
			str +='<param name="SHUFFLE" value="0">';
			str +='<param name="PREFETCH" value="0">';
			str +='<param name="NOLABELS" value="-1">';
			str +='<param name="SRC" value="'+fileUrl+'";>';
			str +='<param name="CONTROLS" value="Imagewindow">';
			str +='<param name="CONSOLE" value="clip1">';
			str +='<param name="LOOP" value="1">';
			str +='<param name="NUMLOOP" value="0">';
			str +='<param name="CENTER" value="0">';
			str +='<param name="MAINTAINASPECT" value="1">';
			str +='<param name="BACKGROUNDCOLOR" value="#000000">';
			str +='<param name="wmode" value="transparent">';
		str +='</object>';
		str +='<br />';
		str +='<object id="rep2" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width=648 height=30>';
			str +='<param name="_ExtentX" value="11298">';
			str +='<param name="_ExtentY" value="794">';
			str +='<param name="AUTOSTART" value="0">';
			str +='<param name="SHUFFLE" value="0">';
			str +='<param name="PREFETCH" value="0">';
			str +='<param name="NOLABELS" value="-1">';
			str +='<param name="SRC" value="'+fileUrl+'";>';
			str +='<param name="CONTROLS" value="ControlPanel">';
			str +='<param name="CONSOLE" value="clip1">';
			str +='<param name="LOOP" value="1">';
			str +='<param name="NUMLOOP" value="0">';
			str +='<param name="CENTER" value="0">';
			str +='<param name="MAINTAINASPECT" value="1">';
			str +='<param name="BACKGROUNDCOLOR" value="#000000">';
			str +='<param name="wmode" value="transparent">';
		str +='</object>';
	}
	else if(fileUrl.lastIndexOf('.wmv') != -1 || fileUrl.lastIndexOf('.wma') != -1)
	{
		str +=	'<object id="player" height="305" width="648" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">';
			str +=	'<param NAME="AutoStart" VALUE="-1">';
			str +=	'<param NAME="Balance" VALUE="0">';
			str +=	'<param name="enabled" value="-1">';
			str +=	'<param NAME="EnableContextMenu" VALUE="-1">';
			str +=	'<param NAME="url" value="'+fileUrl+'">';
			str +=	'<param NAME="PlayCount" VALUE="1">';
			str +=	'<param name="rate" value="1">';
			str +=	'<param name="currentPosition" value="0">';
			str +=	'<param name="currentMarker" value="0">';
			str +=	'<param name="defaultFrame" value="">';
			str +=	'<param name="invokeURLs" value="0">';
			str +=	'<param name="baseURL" value="">';
			str +=	'<param name="stretchToFit" value="0">';
			str +=	'<param name="volume" value="50">';
			str +=	'<param name="mute" value="0">';
			str +=	'<param name="uiMode" value="full">';
			//以下参数非常重要！解决遮挡DIV问题
			str +=	'<param name="windowlessVideo" value="true">';
			str +=	'<param name="fullScreen" value="0">';
			str +=	'<param name="enableErrorDialogs" value="-1">';
			str +=	'<param name="SAMIStyle" value>';
			str +=	'<param name="SAMILang" value>';
			str +=	'<param name="SAMIFilename" value>';
			str +='<param name="wmode" value="Opaque">';
		str +=	'</object>';
	}
	else
	{
 
		if('${param.type}' == 'voice')
		{
			str +=	'<object id="testCmsSysMediaImgShow" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" color="black" allowfullscreen="true" quality="high" type="application/x-shockwave-flash" width="390" height="55" wmode="transparent" data="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'">';
			str +=		'<param id="testCmsSysMediaImgShowParam" name="movie" value="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'" />';
			str +=		'<param name="wmode" value="transparent" />';
			str +=		'<param name="quality" value="high" />';
			str +=		'<param name="allowfullscreen" value="true" />';
			str +=		'<param name="displayheight" value="0" />';
			str +=      '<param name="wmode" value="Opaque">';
			str +=		'<embed id="embedPlayer" name="embedPlayer" wmode="transparent" width="390" height="55" allowfullscreen="true" quality="high" type="application/x-shockwave-flash" src="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'" />';
			str +=	'</object>';		
		}
		else
		{		
			str +=	'<object id="testCmsSysMediaImgShow" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" color="black" allowfullscreen="true" quality="high" type="application/x-shockwave-flash" width="648" height="305" wmode="transparent" data="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'">';
			str +=		'<param id="testCmsSysMediaImgShowParam" name="movie" value="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'" />';
			str +=		'<param name="wmode" value="transparent" />';
			str +=		'<param name="quality" value="high" />';
			str +=		'<param name="allowfullscreen" value="true" />';
			str +=		'<param name="displayheight" value="0" />';
			str +=      '<param name="wmode" value="Opaque">';
			str +=		'<embed id="embedPlayer" name="embedPlayer" wmode="transparent" width="648" height="305" allowfullscreen="true" quality="high" type="application/x-shockwave-flash" src="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'" />';
			str +=	'</object>';
		}	
			
		
	}

	document.write(str);
}
		
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
 	   	var url = "<cms:BasePath/>content/snapshotImage.do"+postData;
 	
 		$.ajax
 		({
			type : 'POST',
			url  : encodeURI(url),
			success:
			function(da, textStatus)
			{alert(da);
				var data = eval("("+da+")");
				
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
		return;
	}
	
	alert('正在处理视频,请点击 [确定] 后等待!');
   
 	    var postData = '?pos='+pos+'&classId=-9999&resInfo='+relateUrl+"&random="+Math.random();
 	    
 	   	var url = "<cms:BasePath/>/content/snapshotImage.do"+postData;
 	
 		$.ajax
 		({
			type : 'POST',
			url  : encodeURI(url),
			success:
			function(data, textStatus)
			{ 
				var jsonObj = eval("("+data+")");
					 	
				if('no file' != jsonObj)
				{
				    
					<cms:CurrentSite>
					var siteRoot = '${CurrSite.siteRoot}';
					var videoBase = '${CurrSite.mediaRoot}';
					</cms:CurrentSite>
				    
			 	    
				    window.parent.window.document.getElementById(id+'_sys_jtopcms_iframe').src = '<cms:BasePath/>core/weixin/UploadVideoModule.jsp?fileUrl=${param.fileUrl}&autoStart=false&cover='+jsonObj.obj_0.imageUrl;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_src').value = jsonObj.obj_0.imageUrl;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_w').value = jsonObj.obj_0.width;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_h').value = jsonObj.obj_0.height;
					
					window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_n').value = jsonObj.obj_0.imageName;
					
					//alert(window.parent.window.document.getElementById(id+'_sys_jtopcms_media_cover_id').value);
					//alert( window.parent.window.document.getElementById(id).value);
					
					
					//window.parent.window.document.getElementById("snapshotImg").value = jsonObj.obj_0.relatePath;
			
					//replaceUrlParam(window.location,'autoStart=false&snapshotImg='+jsonObj.obj_0.resizeImageUrl+'&oldImageUrl='+jsonObj.obj_0.relatePath);
					//alert(window.location.href);
				}
				else
				{
					alert('截取视频图片失败!');
				}
  			}
  		});
}



</script>

