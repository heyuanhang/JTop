<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>JTopCMS内容管理系统</title>
		<link href="../style/blue/css/frame.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.6.2.min.js"></script>

		<script language="javascript">  
//iframe 左右拉伸


var isResizing = false;
function Resize_mousedown(event, obj) {
    obj.mouseDownX = event.clientX;
    obj.leftTdW = obj.previousSibling.offsetWidth;
    obj.setCapture();
    isResizing = true;
}
function Resize_mousemove(event, obj) {
    //if (!isResizing) return;
    //var newWidth = obj.leftTdW * 1 + event.clientX * 1 - obj.mouseDownX;
    //if (newWidth > 0) $('#menu').width(newWidth + 'px');
    //else $('#menu').width('1px');;
}
function Resize_mouseup(event, obj) {
    if (!isResizing) return;
    obj.releaseCapture();
    isResizing = false;
}
function Resize_setDefault(event, obj) {
    if (document.getElementById("changeimage").value == 1) 
    {
       $('#menu').width('1px');
        document.getElementById("changeimage").value = 2;
        document.getElementById("changeimage").className = "class_image2";
    } 
    else if (document.getElementById("changeimage").value == 2) 
    {
     	$('#menu').width('200px');
        document.getElementById("changeimage").value = 1;
        document.getElementById("changeimage").className = "class_image1";
    }
    event.cancelBubble = true;
}

//获取高度  
window.onload = function () {
              
				}


  </script>
		<style>
html, body {
	margin:0;
	padding:0;
	height:100%;
}
.input_stlyel .class_image1{ width:8px; height:50px; background:url(../style/blue/images/open.gif) no-repeat; cursor:pointer; color:#f4f8fa; border:none; text-align:left; font-size:1px; overflow:hidden;}
.input_stlyel .class_image2{ width:8px; height:50px; background:url(../style/blue/images/close.gif) no-repeat; cursor:pointer; color:#f4f8fa; border:none; text-align:left; font-size:1px; overflow:hidden;}
</style>

	</head>
	<body id="currentbody">
		<iframe id="top" name="top" frameborder="0" src="top.jsp" width="100%" height="75" scrolling="no"></iframe>
		<table style="width:100%;" id="mainall" border=0 cellspacing=0 cellpadding=0>
			<tr>
				<td style="width:209px;" class="menu" id="menu">
				<script>
				  
				 
				  	$('#menu').attr('style','width:'+window.screen.width * 0.145+'px')
				 
				</script>
					<iframe src="" id="leftMenu" name="menu" frameborder="0" scroll-y="yes"></iframe>
				</td>
				<td style="width:6px; background-color:#e2e9ea;" class="input_stlyel" align="center" valign="middle" onmousedown="Resize_mousedown(event,this);" onmouseup="Resize_mouseup(event,this);" onmousemove="Resize_mousemove(event,this);">
					<input id="changeimage" class="class_image1" border="0" type="button" onmousedown="Resize_setDefault(event,this);" value="1" hidefocus="true" />
				</td>
				<td class="main" align="left" valign="top">
					<iframe id="main" name="main" src="Workbench.jsp" class="mainheight1" frameborder="0" scrolling="auto"></iframe>
				</td>
			</tr>
		</table>
	</body>
</html>
<script>
  var newheight = (document.body.clientHeight - 64 - 10) + "px";
                document.getElementById("mainall").style.height = newheight;
				document.getElementById("main").style.height = newheight;

</script>