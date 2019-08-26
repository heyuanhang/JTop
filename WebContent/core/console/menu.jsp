<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<cms:ManagerSec jump="true"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title>menu</title>
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<link href="../style/blue/images/left.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../common/js/jquery-1.6.2.min.js"></script>

		<script type="text/javascript" src="javascript/xtree2b/js/xtree2.js"></script>

		<script>
	 //去掉点击链接 虚线边框
      window.onload=function()
      {
     for(var i=0; i<document.links.length; i++)
     document.links[i].onclick=function(){this.blur()}
     }
	 


　

      </script>
		<style type="text/css">
			html{
			scrollbar-3dlight-color:#fff; /*- 最外左 -*/  
			scrollbar-highlight-color:#fff; /*- 左二 -*/  
			scrollbar-face-color:#DCE7F5; /*- 面子 -*/  
			scrollbar-arrow-color:#407DCA; /*- 箭头 -*/  
			scrollbar-shadow-color:#efefef; /*- 右二 -*/  
			scrollbar-darkshadow-color:#E9EDF8; /*- 右一 -*/  
			scrollbar-base-color:#D7DCE0; /*- 基色 -*/  
			scrollbar-track-color:#fcfcfc;/*- 滑道 -*/  
			 
			 overflow-x: hidden; 
				}
		</style>
		<script>
	var resArray = new Array();
	
	
	</script>
	</head>

	<body >

		<cms:SecurityResourceList parentId="${param.parResId}" type="2">
			<cms:SecurityResource>
				<!--3-->
				<table width="100%" height="100%" align="left" border='0' cellspacing='0' cellpadding='0' style="">
					<tr onclick="javascript:controlMenu(${Resource.secResId})">
						<td height="28" width="8" class="topleft"></td>
						<td align="left" class="lefttit" onclick="">
							<img id="list-img-${Resource.secResId}" src="../style/blue/images/down.gif" width="16" height="16" />
							${Resource.resourceName}
						</td>
						<td width="8" class="topright"></td>
					</tr>
					<tr style="display:;">
						<td class="leftline"></td>
						<td width='' valign="top">
							<cms:SecurityResourceList parentId="${Resource.secResId}" type="3">
								<ul class="listul" id="list-${Resource.secResId}">
									<cms:SecurityResource>
										<script>
										resArray.push('${Resource.secResId}');
									</script>
										<li id="li-${Resource.secResId}" onclick="javascript:afterSelect(this);">
											<a id="a-${Resource.secResId}" title="${Resource.resourceName}" href="<cms:Domain/>${Resource.target}" target="main"><img src="<cms:Domain/>core/style/icons/${Resource.icon}" width="16" height="16" /><span>${Resource.resourceName}</span> </a>
										</li>
									</cms:SecurityResource>
								</ul>

							</cms:SecurityResourceList>
						</td>
						<td class="rightline"></td>
					</tr>
					<tr>
						<td height="5" class="leftbt"></td>
						<td class="btbg"></td>
						<td class="rightbt"></td>
					</tr>
				</table>

				<div class="Black8"></div>
			</cms:SecurityResource>
		</cms:SecurityResourceList>



	</body>
</html>


<script>
var targetHref = document.getElementById('a-'+resArray[0]);

if(targetHref != null)
{
	//targetHref.click();
}

function controlMenu(parResId)
{
	var menu = document.getElementById("list-"+parResId);
	var menuImg = document.getElementById("list-img-"+parResId);
	if(menu.style.display == 'none')
	{
		menu.style.display='';
		menuImg.src='../style/blue/images/down.gif';
	}
	else
	{
		menu.style.display='none';
		menuImg.src='../style/blue/images/right.gif';
	}
	


}

function afterSelect(current)
{
	



}


</script>

