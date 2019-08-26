<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html>

	<head>
		<meta charset="UTF-8">
		<cms:Content id="${param.id}">
		<meta name="Keywords" content="${Info.keywords}">
		<meta content="${Info.summary}" name="description">
		
	<title>${Info.title}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="${ResBase}css/mui.css" type="text/css">
	<link rel="stylesheet" href="${ResBase}css/style.css" type="text/css">
	<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ResBase}js/commonUtil_src.js"></script>
 
	</head>

	<body>
		<header class="mui-bar mui-text-center">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left text-color-fff"></a>
			<a href="${SiteBase}" class="mui-icon mui-icon-home mui-pull-right text-color-fff"></a>
			<h1 class="mui-title"><span class="logo">JTopCMS移动站点演示</span></h1>
		</header>
		<div class="mui-content">			
			<!-- 标题 -->
			<div class="article-content-title">
				<h1 class="title">${Info.title}</h1>
				<h4 class="article-info text-color-bbb">					
					<span>${Info.author}</span>
					<span class="ml20"><cms:FormatDate date="${Info.pubDate}" format="yyyy年MM月dd日 HH:mm"/></span>
					<span class="mui-pull-right">参与者<a href="${SiteBase}comment.jsp?id=${Info.contentId}" class="text-color-default"><span id="commCount2">0</span></a>人</span>
				</h4>
			</div>
			<!-- 标题结束 -->

			<!-- 内容开始 -->
			<div class="mui-content-padding article-content-text mui-bg-white">
				${Info.mh_wz}

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
			</div>
			<!-- 内容结束 -->
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
		
		<script src="${ResBase}js/mui.min.js"></script>
		
	</body>

</html>
</cms:Content>