<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		 <script>
           var ids = '';
            
         </script>
         <style type="text/css"><!--
*{
margin:0;padding:0;
}
body{
}
.d-con{
width:33%;float:left;vertical-align:top;height:auto;min-height:600px;
}
.modbox{
margin:0 0 15px 20px;border:1px solid #C8E4B3;height:200px;z-index:9;-moz-user-select:none;
}
.modtitle{
width:100%;background:#C8E4B3;height:25px;
}
--></style><style type="text/css" mce_bogus="1">*{
margin:0;padding:0;
}
body{
}
.d-con{
width:33%;float:left;vertical-align:top;height:auto;min-height:600px;
}
.modbox{
margin:0 0 15px 20px;border:1px solid #C8E4B3;height:200px;z-index:9;-moz-user-select:none;
}
.modtitle{
width:100%;background:#C8E4B3;height:25px;
}</style>
	</head>
	<body>
		<cms:PhotoGroup groupId="${param.ids}" contentId="${param.contentId}" >
       
			<img id="photo${Photo.groupPhotoId}" src="<cms:Domain/>UPLOAD/imgResize${Photo.fileName}" height="80" width="100" onclick="javascript:changeCoverPhoto('${Photo.url}','${Photo.fileName}');" />
            <input id="c${Photo.groupPhotoId}" type="button" value="裁剪" onclick="javascript:gotoCropPic('${Photo.url}','${Photo.groupPhotoId}')" class="btn-1" />
             <input id="d${Photo.groupPhotoId}" type="button" value="编辑" onclick="javascript:editGroupPhoto('${Photo.groupPhotoId}')" class="btn-1" />
            <input id="d${Photo.groupPhotoId}" type="button" value="删除" onclick="javascript:deleteGroupPhoto('${Photo.groupPhotoId}')" class="btn-1" />
            <input type="text" size="2" id="orderFlag" name="orderFlag" value="${status.index+1}" />
            &nbsp;
            <script>
            <cms:if test="${param.init==true}">
	            <cms:if test="${status.last}"> 
	             	ids += '${Photo.groupPhotoId}';
	            </cms:if>
	            <cms:else>
	                ids += '${Photo.groupPhotoId},';
	            </cms:else>
	           
            </cms:if>
            </script>
		</cms:PhotoGroup>
		
		

	</body>
</html>
<script>
window.parent.document.all['groupFrame'].style.height=document.body.scrollHeight;
//window.parent.document.all['groupFrame'].style.width=document.body.scrollWidth;
if("${param.init}" == 'true')
{

 window.parent.document.getElementById("ids").value=ids;
 //alert(window.parent.document.getElementById("ids").value);
 
}

function changeCoverPhoto(url,fileName)
{
	window.parent.targetCoverPhoto=fileName+''
	
	var resize = "<cms:Domain/>UPLOAD/imgResize"+fileName;
	window.parent.document.getElementById("showCover").src=resize;
	window.parent.document.getElementById("cover").value=url;
	window.parent.document.getElementById("resizeCover").value=resize;
}

function gotoCropPic(file,gid)
{
	   window.showModalDialog("CropPicture.jsp?flowFlag=group&gid="+gid+"&targetPicFile="+file,"","dialogWidth=1600px;dialogHeight=1200px;status=no");
	   window.location.reload();
}

function deleteGroupPhoto(gid)
{
       document.getElementById("photo"+gid).style.display='none';
       document.getElementById("c"+gid).style.display='none';
       document.getElementById("d"+gid).style.display='none';
       window.parent.document.getElementById("needDeleteIds").value += gid+",";
      
       
	   //window.location="../../content/deletePhoto.do?flowAction=deleteGroupSinglePhoto&gid="+gid+"&contentId=${param.contentId}";
}

function editGroupPhoto(gid)
{
	   window.parent.editSinlePhotoDialog(gid);
}

</script>
