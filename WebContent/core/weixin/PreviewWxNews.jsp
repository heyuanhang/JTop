<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="GBK">
	<title></title>
	<style type="text/css">
		* {
		    -webkit-box-sizing: border-box;
		    box-sizing: border-box;
		    -webkit-user-select: none;
		    outline: 0;
		    -webkit-tap-highlight-color: transparent;
		    -webkit-tap-highlight-color: transparent;
		}
		h1,h2,h3,h4,h5,h6,p{
			margin: 0;
			padding: 0;
		}
		img{
			border: 0;
		}
		 
		.phone {
		    margin: 0 auto;
		    position: relative;
		   
		    border-radius: 55px;
		   
		    width: 320px;
		   
		    padding: 10px 25px 10px 25px;
		    -webkit-box-sizing: content-box;
		    box-sizing: content-box;
		}
		.phone:before {
		    content: '';
		    width: 60px;
		    height: 10px;
		    border-radius: 10px;
		    position: absolute;
		    left: 50%;
		    margin-left: -30px;
		    background: #333;
		    top: 50px;
		}
		 
		.phone .container {
		    width: 320px;
		    height: 85%;
		    display: block;
		    width: 100%;
		    margin-top: 2px;
		}

		.mui-content {
		    background-color: #efeff4;
		    height: 100%;
		    -webkit-overflow-scrolling: touch;
		    padding: 15px;
		    box-sizing: border-box;
		    overflow: auto;
		}
		.mui-bar-nav~.mui-content {
		    padding-top: 64px;
		}
		.mui-bar {
		    position: absolute;
		    z-index: 10;
		    right: 0;
		    left: 0;
		    height: 44px;
		    line-height: 44px;
		    padding-right: 10px;
		    padding-left: 10px;
		    border-bottom: 0;
		    background-color: #111;
		    color: #fff;
		    font-size: 1.4em;
		    text-align: center;
		    -webkit-backface-visibility: hidden;
		    backface-visibility: hidden;
		}
		.mui-slider {
		    position: relative;
		    z-index: 1;
		    overflow: hidden;
		    width: 100%;
		    background: #fff;
		    border: 1px solid #ccc;
		    border-radius: 4px;
		    box-sizing: border-box;
		}
		.mui-slider .mui-slider-group {
		    font-size: 0;
		    position: relative;
		    -webkit-transition: all 0s linear;
		    transition: all 0s linear;
		    white-space: nowrap;
		}
		.mui-slider .mui-slider-group .mui-slider-item {
		    font-size: 14px;
		    position: relative;
		    display: inline-block;
		    width: 100%;
		    height: 100%;
		    padding: 10px;
		    vertical-align: top;
		    white-space: normal;
		    box-sizing: border-box;
		}
		.mui-slider-item a{
			color: #fff;
		}
		.mui-slider-title {
		    line-height: 20px;
		    position: absolute;
		    bottom: 10px;
		    left: 10px;
		    right: 10px;
		    /*width: 100%;*/
		    margin: 0;
		    padding: 5px;
		    box-sizing: border-box;
		    text-align: left;
		    opacity: .8;
		    font-size: 16px;
		    background-color: #000;
		}
		.mui-slider .mui-slider-group .mui-slider-item img {
		    width: 100%;
		}

		.mui-table-view {
		    position: relative;
		    margin-top: 10px;
		    margin-bottom: 0;
		    padding-left: 0;
		    list-style: none;
		    background-color: #fff;
		}
		.mui-table-view:before {
		    position: absolute;
		    right: 0;
		    left: 0;
		    height: 1px;
		    content: '';
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
		    background-color: #c8c7cc;
		    top: -1px;
		}
		/*.mui-table-view:after {
		    position: absolute;
		    right: 0;
		    bottom: 0;
		    left: 0;
		    height: 1px;
		    content: '';
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
		    background-color: #c8c7cc;
		}*/
		.mui-table-view-cell {
		    position: relative;
		    overflow: hidden;
		    padding: 11px 15px;
		    -webkit-touch-callout: none;
		}
		.mui-table-view-cell a{
			text-decoration: none;
		}
		.mui-table-view .mui-media, .mui-table-view .mui-media-body {
		    overflow: hidden;
		}
		.mui-table-view-cell>a:not(.mui-btn) {
		    position: relative;
		    display: block;
		    overflow: hidden;
		    margin: -11px -15px;
		    padding: inherit;
		    text-overflow: ellipsis;
		    color: inherit;
		}
		.mui-pull-right {
		    float: right;
		}
		.mui-table-view .mui-media-object {
		    line-height: 42px;
		    max-width: 42px;
		    height: 42px;
		}
		.mui-table-view .mui-media-object.mui-pull-right {
		    margin-left: 10px;
		}
		.mui-table-view .mui-media, .mui-table-view .mui-media-body {
		    overflow: hidden;
		}
		.mui-table-view-cell:after {
		    position: absolute;
		    right: 0;
		    bottom: 0;
		    left: 0;
		    height: 1px;
		    content: '';
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
		    background-color: #c8c7cc;
		}
		.phone .statusbar {
		  
		}
	</style>
</head>
<body>
<div class="phone">
	<div class="container">
		 
		<div class="mui-content">
			
			<div class="mui-slider">
			
				<cms:CurrentSite>
						<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemGroupForTag"  var="${CurrSite.siteFlag},${param.rowFlag}">
						<cms:if test="${status.first}">
							<!-- big img -->
							<div class="mui-slider-group mui-slider-loop" >
								<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
								<div class="mui-slider-item mui-slider-item-duplicate">
									<a target="_blank" href="${News.url}">
										<img src="${News.img}">
										<p class="mui-slider-title">${News.title}</p>
									</a>
								</div>
								
							</div>
							<!-- end -->
							
							<!-- list img -->
							<ul class="mui-table-view">
						
						
						</cms:if>
						<cms:else>
						
							<li class="mui-table-view-cell mui-media">
								<a target="_blank" href="${News.url}">
									<img class="mui-media-object mui-pull-right" src="${News.img}">
									<div class="mui-media-body">
										<p class="mui-ellipsis">${News.title}</p>
									</div>
								</a>
							</li>
						
						</cms:else>
				
				
						</cms:QueryData>
				</cms:CurrentSite>
			
				
				 
					
				</ul>
				<!-- end -->
			</div>
			

			
		</div>
	</div>
	<!-- statusbar -->
	<div class="statusbar"></div>
</div>	
</body>
</html>
