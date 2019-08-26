<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${SiteObj.seoTitle}</title>
	<meta name="Keywords" content="${SiteObj.seoKeyword}" >
	<meta name="description" content="${SiteObj.seoDesc}" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="${ResBase}mob/css/mui.css" type="text/css">
	<link rel="stylesheet" href="${ResBase}mob/css/style.css" type="text/css">
	<script type="text/javascript" src="${ResBase}mob/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ResBase}mob/js/commonUtil_src.js"></script>
</head>
<body>
<header class="mui-bar mui-text-center">
	<a class="mui-icon mui-icon-home mui-pull-left text-color-fff" href="${SiteBaseMob}"></a>
	<h1 class="mui-title"><span class="logo">JTopCMS - 演示站点</span></h1>
</header>
<div class="mui-content column-banner zs-news-list-index">
	
	<!-- sab-nav -->
	<nav class="sab-nav">
		<ul>
			<li><a class="active" href="javascript:window.location.reload();">最新</a></li>
			<cms:Class idList="child:root" flagMode="true">
				<li><a href="${Class.mobClassUrl}">${Class.className}</a></li>
			</cms:Class>
			
			 
		</ul>
	</nav>
	<!-- sab-nav end -->

	<!-- 轮播图开始 -->
	<div id="slider" class="mui-slider">
		<div class="mui-slider-group mui-slider-loop">
			<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
			
			<cms:CommendContent typeId="${all_site_tptj_id}"   size="6">

				  		<cms:if test="${status.last}">
				  				<cms:Content id="${CommInfo.content.contentId}">
				  						<div class="mui-slider-item mui-slider-item-duplicate">
											<a href="${Info.mobContentUrl}">
												<img src="${CommInfo.img}">
												<p class="mui-slider-title text-color-fff mui-h4 mui-text-center">${CommInfo.title}</p>
											</a>
										</div>
				  				</cms:Content>		
				  		</cms:if>
  						  	
		    </cms:CommendContent>
			
	 
			
			<cms:CommendContent typeId="${all_site_tptj_id}"   size="6">
			<cms:Content id="${CommInfo.content.contentId}">
				  		
				  		
				  		<div class="mui-slider-item">
							<a href="${Info.mobContentUrl}">
								<img src="${CommInfo.img}">
								<p class="mui-slider-title text-color-fff mui-h4 mui-text-center">${CommInfo.title}</p>
							</a>
						</div>
				  		
				  		
			</cms:Content>				  						  	
		    </cms:CommendContent>
			
			
			
			
			
			
			 
			<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
			<cms:CommendContent typeId="${all_site_tptj_id}"   size="6">

				  		<cms:if test="${status.first}">
				  				<cms:Content id="${CommInfo.content.contentId}">
										<div class="mui-slider-item mui-slider-item-duplicate">
											<a href="${Info.mobContentUrl}">
												<img src="${CommInfo.img}">
												<p class="mui-slider-title text-color-fff mui-h4 mui-text-center">${CommInfo.title}</p>
											</a>
										</div>
				  				</cms:Content>		
				  		</cms:if>
  						  	
		    </cms:CommendContent>
			
			
			
			
			
		</div>
		<div class="mui-slider-indicator mui-text-center">
		
			<cms:CommendContent typeId="${all_site_tptj_id}" page="true" size="6">

				  		<cms:if test="${status.first}">
				  		
				  				<div class="mui-indicator mui-active"></div>
				  				
				  		</cms:if>
				  		<cms:else>
				  		
				  			<div class="mui-indicator"></div>
				  		
				  		</cms:else>
  						  	
		    </cms:CommendContent>
			
		 
		</div>
	</div>
	<!-- 轮播图结束 -->


    <!-- 图文列表-->
	<ul class="mui-table-view mt15" id="tEvent-root">
			 
			<cms:Content  classId="allChild:root" pageSize="15">
			
					<li class="mui-table-view-cell mui-media">
						<a href="${Info.mobContentUrl}">
						<cms:if test="${Info.classImgFlag == 1}">
							<img class="mui-media-jtop mui-pull-left" src="${Info.classImage}">
						</cms:if>
							
							<div class="mui-media-body">
								<p class="mui-ellipsis-2 font-s-16 text-color-333">${Info.title}</p>
								<p class="time-like">
									<i class="mui-icon mui-icon-videocam font-s-16 text-color-default"></i>
									<span class="text-color-bbb font-s-12"><cms:FormatDate date="${Info.pubDate}" format="yyyy年MM月dd日 HH:mm"/></span>
									<span class="mui-pull-right"><span class="mui-badge text-color-bbb"><span id="c-${Info.contentId}">0</span>条评论</span></span>
								</p>
							</div>
						</a>
					</li>
					<script>
                  
                   
						        var url = '${SiteBase}content/status.do?id=${Info.contentId}';
			 		
						 		$.ajax({
						      		type: "POST",
						       		url: url,
						       		data:'',
						   			dataType:'json',
						       		success: function(msg)
						            {     
						                 
						               
						               $('#c-${Info.contentId}').text(status.clickCount);
						               
						            }
						     	});	
			                  
			                  
			                  </script>
			
			</cms:Content>
		 
			
			<span id="lilist">
			
			
			
			</span>
			
		   
		
		<div class="comment-more mui-text-center">
			<a href="javascript:getContent('allChild:root')">
				<img id="load-root" style="display:none" src="${ResBase}mob/images/loading.gif"height="18" /><span id="over-root">加载更多新闻</span>
			</a>
		</div>
	</ul>
	
	
    <!-- footer -->
	<footer class="footer mui-text-center">
			    <!--
			    <ul class="view">
			        <li><a href="${SiteBase}">手机版</a></li>
			        <li><a href="http://demo.jtopcms.com">桌面版</a></li>
			    </ul>
			    <div class="copyright-box">
			       <a target="_blank" href="http://www.miibeian.gov.cn/">皖ICP备13006219号-1</a>
			    </div>
				-->
			</footer>
    <!-- footer end -->
</div>
  <script type="text/javascript" src="${ResBase}mob/js/mui.min.js"></script>

</body>
</html>
<script>

 

var ep = 8;

 
var isEnd = false;

function getContent(classId)
{
		 			 
				 	if(isEnd)
					{
					 	$('#over-root').text('已经达到最后');
					}
					else
					{
				
						var nextQueryCd = 'classId='+classId;
						 
 
	 					next(classId);
 					}
 				 
				

}



function next(classId)
{
 					$('#load-root').show();
					
					var url = "${SiteBase}japi/getContentList.cmd?uid="+Math.random();
 					
 				 	var postData = 'classId='+classId+'&pa=2&ep='+ep+'&nz=3&page=true&order=default-down&pull=true';
 						 
			 		$.ajax({
			      		type: "POST",
			      		async:true,
			       		url: url,
			       		data:postData,
			   			
			       		success: function(msg)
			            {     
			              
			              	var jso = eval('(' + msg + ')');
			             
			                 
			             	$('#load-root').hide();
			                         
			           
			             
				            var cep = jso.endPos;
				            
				            ep = cep;
				              
				              $.each(jso.content,function(name,val) {
							 
								
								genConHtml(classId, val.title, val.classImage, val.mobContentUrl, val.summary, val.pubDate, val.author, val.commCount);
							});
						 
						 
						  	if(jso.isEnd)
				            {
				             	isEnd = true;
				            	$('#over-root').text('已经达到最后');
				            	 
				            }
			             
						 
			            }
			     	});	

}


function genConHtml(classId, title, img, url, summary, pdt, source, sg)
{
              	 var   c = '';
              	 
				 if(typeof(img) == 'undefined')
				 {
				 		c= '<li class="mui-table-view-cell mui-media">'+
						'<a href="'+url+'">'+
						 		 
							'<div class="mui-media-body">'+
								'<p class="mui-ellipsis-2 font-s-16 text-color-333">'+title+'</p>'+
								'<p class="time-like">'+
									'<i class="mui-icon mui-icon-videocam font-s-16 text-color-default"></i>'+
									'<span class="text-color-bbb font-s-12">'+pdt+'</span>'+
									'<span class="mui-pull-right"><span class="mui-badge text-color-bbb">'+sg+'条评论</span></span>'+
								'</p>'+
							'</div>'+
						'</a>'+
					'</li>';
				 }
				 else
				 {
				 	
				 	c= '<li class="mui-table-view-cell mui-media">'+
						'<a href="'+url+'">'+
						 		'<img class="mui-media-jtop mui-pull-left" src="'+img+'">'+
	
							'<div class="mui-media-body">'+
								'<p class="mui-ellipsis-2 font-s-16 text-color-333">'+title+'</p>'+
								'<p class="time-like">'+
									'<i class="mui-icon mui-icon-videocam font-s-16 text-color-default"></i>'+
									'<span class="text-color-bbb font-s-12">'+pdt+'</span>'+
									'<span class="mui-pull-right"><span class="mui-badge text-color-bbb">'+sg+'条评论</span></span>'+
								'</p>'+
							'</div>'+
						'</a>'+
					'</li>';
				 
				 
				 
				 }
	        	
						
			 
	  $("#lilist").append(c);         
	            
	            
	            


}
 
</script>