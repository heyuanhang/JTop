<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="GBK">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		
			
 
	<script type="text/javascript" src="js/player/images/swfobject.js"></script>

	<script type="text/javascript" src="js/player/cpMobile/js/action.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
	<title>JTopCMS - 移动工作台 -工作流</title>
	<link rel="stylesheet" href="css/mui.min.css">
	<style>
		.mui-card-content-inner img{
			width: 100%;
		}
		.mui-card-content-inner p {
			color: #666;
		}
		
	</style>
</head>
<body>
 
<header class="mui-bar mui-bar-nav">
	<a class="mui-icon mui-icon-left-nav mui-pull-left" href="javascript:history.go(-1);"></a>
	<h1 class="mui-title">图片展示</h1>
</header>
<div class="mui-content">
<div class="mui-card">
	 
	<div class="mui-card-content">
		<div class="mui-card-content-inner">
			 <img src="${param.src}">
		
		</div>
	</div>
</div>
</div>	
</body>
<script type="text/javascript" src="js/mui.js"></script>
</html>
 
