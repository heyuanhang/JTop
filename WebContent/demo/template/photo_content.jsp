<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<cms:Content id="${param.id}">
<title>${Info.title}</title>

<!--必要样式-->

<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>

<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ResBase}css/photo_content.css" />
<link rel="stylesheet" type="text/css" href="${ResBase}css/photo_elastislide.css" />

<script type="text/javascript" src="${ResBase}js/jquery.min.js"></script>
<script type="text/javascript" src="${ResBase}js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="${ResBase}js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${ResBase}js/jquery.elastislide.js"></script>
<script type="text/javascript" src="${ResBase}js/gallery.js"></script>
<style type="text/css">
.es-carousel ul{display:block;}
</style>



<script id="img-wrapper-tmpl" type="text/x-jquery-tmpl">	
	<div class="rg-image-wrapper">
		{{if itemsCount > 1}}
			<div class="rg-image-nav">
				<a href="#" class="rg-image-nav-prev">Previous Image</a>
				<a href="#" class="rg-image-nav-next">Next Image</a>
			</div>
		{{/if}}
		<div class="rg-image"></div>
		<div class="rg-loading"></div>
		<div class="rg-caption-wrapper">
			<div class="rg-caption" style="display:none;">
				<p></p>
			</div>
		</div>
	</div>
</script>

</head>
<body>

<div id="rg-gallery" class="rg-gallery">
	<div class="ArticleControlNavigation">
   	  <div class="attlogo"><cms:Class flagMode="true" id="mh_tp"><a href="${Class.channelUrl}"></cms:Class></a></div>
   		<div class="title">${Info.title}</div>
       	<div class="navtion">
       		<cms:Class id="${Info.classId}">
       		<a href="${Class.classUrl}">返回图片首页</a>
       		</cms:Class>
          	<a target="_blank" href="${SiteBase}comment_list.jsp?id=${Info.contentId}"><i class="fa fa-comments"></i> 评论<b class="actions">(<span id="contentCommCount"></span>)</b></a>
          	<script>
                  
                   
			        var url = '${SiteBase}content/status.do?id=${Info.contentId}';
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {     
			               var status = eval("("+msg+")");
			               
			               $('#contentCommCount').text(status.commCount);
			             
			            }
			     	});	
                  
                  
                  </script>
      	</div>
  </div>
   	<div class="footer">Copyright © 2014 JtopCms. All Rights Reserved</div>
	<div class="rg-thumbs">
	<!-- Elastislide Carousel Thumbnail Viewer -->
		<div class="es-carousel-wrapper">
			<div class="es-nav">
				<span class="es-nav-prev">Previous</span>
				<span class="es-nav-next">Next</span>
			</div>
			<div class="es-carousel">
				<ul>
					<cms:PhotoGroup contentId="${Info.contentId}" group="mh_xxx2">
					
					
						<li><a href="${Photo.resizeUrl}"><img  src="${Photo.url}" data-large="${Photo.url}" alt="image01" data-description="${Photo.photoDesc}" /></a></li>
				
					
					</cms:PhotoGroup>
						</ul>
			</div>
		</div>
	<!-- End Elastislide Carousel Thumbnail Viewer -->
	</div><!-- rg-thumbs -->
</div>


 
</body>
</html>

<cms:VisStat contentId="${Info.contentId}" classId="${Info.classId}"/>

</cms:Content>