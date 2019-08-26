<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<cms:Class id="${param.id}">
	<cms:Class id="${Class.parent}" objName="PClass">
	<title>zswap</title>	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="${ResBase}mob/css/mui.css" type="text/css">
	<link rel="stylesheet" href="${ResBase}mob/css/style.css" type="text/css">
	<script type="text/javascript" src="${ResBase}mob/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ResBase}mob/js/commonUtil_src.js"></script>
</head>
<body>
<header class="mui-bar mui-text-center">
	<a href="javascript:return_prepage();" class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left text-color-999"></a>
	<h1 class="mui-title"><span class="logo">栏目-${Class.className}</span></h1>
</header>
<div class="mui-content">
	<!-- 路径导航 -->
	<ol class="breadcrumb">
	  	<li><a class="text-color-default" href="${SiteBase}">首页</a></li>
	  	<li class="active"><a href="${Class.classUrl}" >${Class.className}</a></li>
			 
	</ol>
	<!-- end -->
	<div class="zs-list" id="div-${Class.classId}">
				
				<div class="zs-list-box">

					<ul class="mui-table-view" id="tEvent-${Class.classId}">
					
					 
					 <cms:Content page="true" order="default-down" pageSize="8" classId="${param.id}">
					 
					 		<li class="mui-table-view-cell mui-media">
								<a href="${Info.mobContentUrl}">
								
									<cms:if test="${Info.classImgFlag == 1}">
									<img class="mui-media-jtop mui-pull-left" src="${Info.classImage}">											
									</cms:if>
									
									<div class="mui-media-body">
										<h4 class="mui-ellipsis text-color-333 line-30">${Info.title}</h4>
										<p class="mui-ellipsis-2">${Info.summary}</p>
									</div>
								</a>
							</li>
							
							 
					 
					 </cms:Content>
				 
						 

					</ul>
					
						<div class="comment-more mui-text-center">
							<a href="javascript:getContent('${Class.classId}')">
								<img id="load-${Class.classId}" style="display:none" src="${ResBase}mob/images/loading.gif"height="18" /><span id="over-${Class.classId}">加载更多</span>
							</a>
						</div>

				</div>
		 
		 
		
	</div>

	<!-- footer -->
	<footer class="footer mui-text-center">
	    <ul class="footer-nav">
	    	<li><a href="${SiteBase}">首页</a></li>
	    	<cms:Class idList="child:root" flagMode="true">
				<li><a href="${Class.classUrl}">${Class.className}</a></li>
			</cms:Class>
	    	 
	    </ul>
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
					 	$('#over-'+classId).text('已经达到最后');
					}
					else
					{
				
						var nextQueryCd = 'classId='+classId;
						 
 
	 					next(classId);
 					}
 				 
				

}



function next(classId)
{
 
 					$('#load-'+classId).show();
					
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
			              
			                 
			             	$('#load-'+classId).hide();
			                         
			           
			             
				            var cep = jso.endPos;
				            
				            ep = cep;
				              
				              $.each(jso.content,function(name,val) {
								 
								
								genConHtml(classId, val.title, val.classImage, val.contentUrl, val.summary, val.pubDate, val.author, val.supportCount);
							});
						 
						 
						  	if(jso.isEnd)
				            {
				             	isEnd = true;
				            	$('#over-'+classId).text('已经达到最后');
				            	 
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
										'<h4 class="mui-ellipsis text-color-333 line-30">'+title+'</h4>'+
										'<p class="mui-ellipsis-2">'+summary+'</p>'+
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
										'<h4 class="mui-ellipsis text-color-333 line-30">'+title+'</h4>'+
										'<p class="mui-ellipsis-2">'+summary+'</p>'+
									'</div>'+
								'</a>'+
							'</li>';
				 
				 
				 
				 }
	        	
						
			 
	  $("#tEvent-"+classId).append(c);         
	            
	            
	            


}
 
</script>
</cms:Class>
</cms:Class>