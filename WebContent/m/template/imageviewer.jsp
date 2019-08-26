<html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

	<head>
		<meta charset="utf-8">
		<cms:Content id="${param.id}">
		<meta name="Keywords" content="${Info.keywords}">
		<meta content="${Info.summary}" name="description">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${ResBase}css/mui.css" type="text/css">
	<link rel="stylesheet" href="${ResBase}css/style.css" type="text/css">
	<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ResBase}js/commonUtil_src.js"></script>
		<!--标准mui.css-->
		<link rel="stylesheet" href="css/mui.css">
		<link rel="stylesheet" href="css/style.css">
		<!--App自定义的css-->
		<style type="text/css">
			.mui-preview-image.mui-fullscreen {
				position: fixed;
				z-index: 20;
				background-color: #000;
			}
			.mui-preview-header,
			.mui-preview-footer {
				position: absolute;
				width: 100%;
				left: 0;
				z-index: 10;
			}
			.mui-preview-header {
				height: 44px;
				top: 0;
			}
			.mui-preview-footer {
				height: 50px;
				bottom: 0px;
			}
			.mui-preview-header .mui-preview-indicator {
				display: block;
				line-height: 25px;
				color: #fff;
				text-align: center;
				margin: 15px auto 4;
				width: 70px;
				background-color: rgba(0, 0, 0, 0.4);
				border-radius: 12px;
				font-size: 16px;
			}
			.mui-preview-image {
				display: none;
				-webkit-animation-duration: 0.5s;
				animation-duration: 0.5s;
				-webkit-animation-fill-mode: both;
				animation-fill-mode: both;
			}
			.mui-preview-image.mui-preview-in {
				-webkit-animation-name: fadeIn;
				animation-name: fadeIn;
			}
			.mui-preview-image.mui-preview-out {
				background: none;
				-webkit-animation-name: fadeOut;
				animation-name: fadeOut;
			}
			.mui-preview-image.mui-preview-out .mui-preview-header,
			.mui-preview-image.mui-preview-out .mui-preview-footer {
				display: none;
			}
			.mui-zoom-scroller {
				position: absolute;
				display: -webkit-box;
				display: -webkit-flex;
				display: flex;
				-webkit-box-align: center;
				-webkit-align-items: center;
				align-items: center;
				-webkit-box-pack: center;
				-webkit-justify-content: center;
				justify-content: center;
				left: 0;
				right: 0;
				bottom: 0;
				top: 0;
				width: 100%;
				height: 100%;
				margin: 0;
				-webkit-backface-visibility: hidden;
			}
			.mui-zoom {
				-webkit-transform-style: preserve-3d;
				transform-style: preserve-3d;
			}
			.mui-slider .mui-slider-group .mui-slider-item img {
				width: auto;
				height: auto;
				max-width: 100%;
				max-height: 100%;
			}
			.mui-android-4-1 .mui-slider .mui-slider-group .mui-slider-item img {
				width: 100%;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-slider-group .mui-slider-item {
				display: inline-table;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-zoom-scroller img {
				display: table-cell;
				vertical-align: middle;
			}
			.mui-preview-loading {
				position: absolute;
				width: 100%;
				height: 100%;
				top: 0;
				left: 0;
				display: none;
			}
			.mui-preview-loading.mui-active {
				display: block;
			}
			.mui-preview-loading .mui-spinner-white {
				position: absolute;
				top: 50%;
				left: 50%;
				margin-left: -25px;
				margin-top: -25px;
				height: 50px;
				width: 50px;
			}
			.mui-preview-image img.mui-transitioning {
				-webkit-transition: -webkit-transform 0.5s ease, opacity 0.5s ease;
				transition: transform 0.5s ease, opacity 0.5s ease;
			}
			@-webkit-keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@-webkit-keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			@keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			p img {
				max-width: 100%;
				height: auto;
			}
		</style>

	</head>

	<body>
		<header class="mui-bar">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left text-color-fff"></a>
			<a href="index.html" class="mui-icon mui-icon-home mui-pull-right text-color-fff"></a>
			<h1 class="mui-title"><span class="logo">JTopCMS移动站点演示</span></h1>
		</header>
		<div class="mui-content imageviewer mui-bg-white">
			<!-- 标题 -->
			<div class="article-content-title">
				<h1 class="title">${Info.title}</h1>
				<h4 class="article-info text-color-bbb">					
					<span>${Info.author}</span>
					<span class="ml20"><cms:FormatDate date="${Info.pubDate}" format="yyyy年MM月dd日 HH:mm"/></span>
					<span class="mui-pull-right">参与者<a href="comment.html" class="text-color-default">12</a>人</span>
				</h4>
			</div>
			<!-- 标题结束 -->
			<div class="mui-content-padded">
			
				<cms:PhotoGroup group="mh_tpj2">
					
						<p>
							<img src="${Photo.url}" height="598" width="898" data-preview-src="${Photo.url}" data-preview-group="1" />
							<h5 class="text">
								${Photo.photoDesc}
							</h5>
						</p>
					 
					
				</cms:PhotoGroup>
			
				
				
				 
			</div>
			<!-- 分享 -->
			<div class="article-share">
				<div class="title">
					<span class="name">分享</span>
					<span class="line"></span>
				</div>
				<div class="box-content mui-text-center bdsharebuttonbox">
					<a class="weibo bds_tsina" href="#" data-cmd="tsina" title="分享到新浪微博"></a>
					<a class="qz" href="#" data-cmd="qzone" title="分享到QQ空间"></a>
					<a class="qqweibo" href="#" data-cmd="tqq" title="分享到腾讯微博"></a>
				</div>
				<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
				</script>
			</div>

			<!-- 分享 end -->
			<!-- 图文列表 -->
			<cms:Include page="include/commentInclude.jsp?id=${Info.contentId}" />
			<!-- footer -->
			<footer class="footer mui-text-center">
			    
			    <ul class="view">
			        <li><a href="${SiteBase}">手机版</a></li>
			        <li><a href="http://demo.jtopcms.com">桌面版</a></li>
			    </ul>
			    <div class="copyright-box">
			       <a target="_blank" href="http://www.miibeian.gov.cn/">皖ICP备13006219号-1</a>
			    </div>
			</footer>
		    <!-- footer end -->

		</div>
	</body>
	<script src="${ResBase}js/mui.min.js"></script>
	<script src="${ResBase}js/mui.zoom.js"></script>
	<script src="${ResBase}js/mui.previewimage.js"></script>
	<script>
		mui.previewImage();
	</script>

</html>
</cms:Content>