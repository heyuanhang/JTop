<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html>
<cms:Content id="${param.id}">
	<head>
		<meta charset="UTF-8">
	<title>内容评论</title>	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link rel="stylesheet" href="${ResBase}css/mui.css" type="text/css">
	<link rel="stylesheet" href="${ResBase}css/style.css" type="text/css">
	<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ResBase}js/commonUtil_src.js"></script>
	<script type="text/javascript" src="${ResBase}js/common.js"></script>
	</head>

	<body>
		<header class="mui-bar mui-text-center">
			<a href="${Info.contentUrl}" class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left text-color-fff"></a>
			<a href="index.html" class="mui-icon mui-icon-home mui-pull-right text-color-fff"></a>
			<h1 class="mui-title"><span class="logo">JTopCMS移动站点演示 - 内容评论</span></h1>
		</header>
		
		
		<form id="commentForm" name="commentForm" method="post" style="padding:15px;">		
		<div class="article-content-title">
			<h1 class="title">${Info.title}</h1>		
				 
			</div>
		
		<div class="mui-content">	
			
			<!-- 评论输入框 -->
			<div class="comment-box">
				<div class="mui-input-row">
					<textarea name="commentText" id="commentText" rows="3" placeholder="文明上网,请您发言…" class="comment-input"></textarea>
				
					 <input type="hidden" id="contentId" name="contentId" value="${Info.contentId}" />
          			 <input type="hidden" id="classId" name="classId" value="${Info.classId}" />
				</div>

				<div class="comment-text">
					<ul>
						<li class="input"><input name="userName" id="userName" type="text" placeholder="昵称"></li>
						<li class="yzm">
						 
							<input placeholder="验证码" name="sysCheckCode" id="sysCheckCode" type="text"  maxLength="4"/>
						 
							<img id="checkCodeImg"  onclick="javascript:changeCode();"  src="${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=24&jump=2" />
			
						
						</li>
						<li class="fb mui-text-right"><a href="javascript:commentContent();" class="mui-btn mui-btn-primary">发布</a></li>
					</ul>
				</div>
			</div>
			
			</form>
			<!-- end -->

			<!-- 内容开始 -->
			<div class="next-article-box mui-bg-white mt15">
				<div class="news-list-title">
					<span>评论</span>
					<span class="mui-pull-right mui-h5">参与者12人</span>
				</div>
				<ul class="mui-table-view">
				
					<cms:Comment size="30"  contentId="${Info.contentId}">

					<li class="mui-table-view-cell mui-media">
						<a href="javascript:void();">
							<img class="mui-media-object mui-pull-left img-circle" src="${ResBase}img/def_avatar.png">
							<div class="mui-media-body">
								<span class="mui-h5 text-color-default">${Comment.userName}</span>
								<p class="mui-ellipsis-20">${Comment.commentText}</p>
								<h6 class="timeinfo text-color-bbb">
									<span class="mui-pull-left mui-h6">${Comment.commDT}</span>
									<%--<span class="mui-pull-right mui-h6 text-color-bbb"><i class="iconfont">&#xe603;</i> 233</span>
								--%></h6>
							</div>
						</a>
					</li>
					
					</cms:Comment>
					
					<%--<div class="comment-more mui-text-center">
						<a href="#">
							加载更多
						</a>
					</div>
				--%></ul>
			</div>
			<!-- 内容结束 -->
			
			<!-- footer -->
			<footer class="footer mui-text-center">
			    <ul class="view">
			        <li><a href="/">手机版</a></li>
			        <li><a href="http://www.huxiu.com/?mobile_view_web=1">桌面版</a></li>
			    </ul>
			    <div class="copyright-box">
			        粤ICP备 15029234
			    </div>
			</footer>
		    <!-- footer end -->
		</div>
		
		<script src="js/mui.min.js"></script>
		
	</body>

</html>
<script>

function commentContent()
{
	var text =  document.getElementById('commentText');
    if(text.value.length < 10 || '文明上网,请您发言…' == text.value)
	{
		alert("您输入的字数过少！");
		return;
	}
	
	text =  document.getElementById('sysCheckCode');
	
    if(text.value.length < 4)
	{
		alert("请输入验证码！");
		return;
	}
	
	var url = basePath+'comment/clientAddComment.do';
	
	var postData = encodeURI($("#commentForm").serialize());
	
 	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
		
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
			       		success: function(msg)
			            {     
			            	
			            	
			            	if('1' == msg)
			            	{		            		
			            		alert('您的评论成功,可进入评论页查看!');
			            		
			            		window.location.reload();
			            	}
			            	else if('-1' == msg)
			            	{
			            		alert('验证码错误!');
			            	}
			            	else if('-2' == msg)
			            	{
			            		alert('系统异常,评论失败!');
			            	}
			            	else if('-3' == msg)
			            	{
			            		alert('评论文本少于10字!');
			            	}
			            	else if('-4' == msg)
			            	{
			            		alert('当前内容不允许评论!');
			            	}
			            	else if('-5' == msg)
			            	{
			            		alert('只有会员才可评论!');
			            	}
			            	else if('-6' == msg)
			            	{
			            		alert('留言版已被关闭!');
			            	}
			            	
			            }
	 });
	 
	 


}



</script>
</cms:Content>