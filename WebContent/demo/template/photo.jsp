<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8" session="false"%><%@ taglib uri="/cmsTag" prefix="cms"%>
<html><cms:Class test="true" id="${param.channelId}"/> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTopCMS - 演示站点</title>
<!--[if IE 7]>

<![endif]-->
<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>


</head>

<body>
<!--头部开始-->




 <cms:Include page="include/head.jsp"/>
 

<!--头部结束-->
<!--body-->

<div class="main_box">
	<!--首页幻灯开始-->
	<div class="photo-banner mt15">
    	<div class="slider_box" id="slider_name"> 
            <div class="mask"></div>
                 
            <ul class="silder_con">	
            	<cms:CommendContent flag="mh_tp_top" size="6">
            	
            				 
            		
		            		<cms:Content objName="InnerInfo" id="${CommInfo.contentId}">
		            			  <li class="silder_panel"><a target="_blank" href="${InnerInfo.contentUrl}" class="f_l"><img src="${InnerInfo.mh_tpj1CmsSysCover}" /></a></li>
		          		    	  
		          		    </cms:Content>
		          		    
		            	 
                  			
                  		 
                  			
                </cms:CommendContent>
               
            </ul>
            
            	<cms:CommendContent flag="mh_tp_top" size="6">
            	
            				 
            		
		            		<cms:Content objName="InnerInfo" id="${CommInfo.contentId}">
				            		<div class="silder_intro">
						                <h3>${InnerInfo.title}</h3>				                
						           </div>
		          		    </cms:Content>
		          		    
		            	 
                  			
                  		 
                  			
                </cms:CommendContent>
             
            
            
            <ul class="silder_nav dec"> 
            	
            	
            	
            	<cms:CommendContent flag="mh_tp_top" size="6">
            	
            				 
            		
		            		<cms:Content objName="InnerInfo" id="${CommInfo.contentId}" >
				            		<li><a href="${InnerInfo.contentUrl}" target="_blank"><img src="${InnerInfo.mh_tpj1CmsSysCoverResize}" /></a></li>
		          		    </cms:Content>
		          		    
		            	 
                  			
                  		 
                  			
                </cms:CommendContent>
            	 
            	        		   
            </ul>
        </div>
    
    </div>
    <!--幻灯结束-->
</div>
<!--banner结束-->
<div class="main_br"></div>


<cms:Class idList="child:${param.channelId}">

	<cms:if test="${status.index == 0}">
		<!--第一个栏目包含大图-->
		<div class="photobox grey-bg">
		<div class="main_box">
	   	  <div class="title"><ins><a href="#"><i class="fa  fa-ellipsis-h fr"></i></a></ins><b>一周精选</b></div>
	   		<div class="photo-sub">
	       		<ul class="picList">
	       		
	       	 
	       				<cms:Content classId="${Class.classId}" showAll="true" pageSize="4">
	       				
	       					<li><a target="_blank" href="${Info.contentUrl}"><img src="${Info.channelImage}" alt="${Info.title}"/><span class="name-txt">${Info.title}</span><b class="name-bg"></b></a></li>

	            
	       				</cms:Content>
	       	 
	           		   	
	           	</ul>
	       	</div>
	       	
	       	<!--photo big-->
	       	<div class="photobig">
	        
            	
            		 
            		
		            	<cms:Content objName="InnerInfo" classId="${Class.classId}" showAll="true" filter="contentImg" pageSize="1" >

							<div class="bigPic"><a target="_blank" href="${InnerInfo.contentUrl}"><img src="${InnerInfo.contentImage}" alt="${InnerInfo.title}"/><span class="name-txt">${InnerInfo.title}</span><b class="name-bg"></b></a></div></div>
		          		</cms:Content>
		         	 
                  			
                  		 
                  			
           
	       	
	        </div>
	</div>
	</cms:if>
	<cms:else>
		<!--其他栏目普通列表-->
		<cms:if test="${status.odd}">
		<div class="photobox">
		</cms:if>
		<cms:else>
			<div class="photobox grey-bg">
		</cms:else>
	
		<div class="main_box">
	   	  <div class="title"><ins><a href="${Class.classUrl}"><i class="fa  fa-ellipsis-h fr"></i></a></ins><b>${Class.className}</b></div>
	       		<ul class="picList">
	       		
	       		 
	       				<cms:Content classId="${Class.classId}" showAll="true" pageSize="8">
	       				
	       					<li><a target="_blank" href="${Info.contentUrl}"><img src="${Info.channelImage}" alt=""/><span class="name-txt">${Info.title}</span><b class="name-bg"></b></a></li>
	       				
	       				</cms:Content>
	       		 
	       			
	             </ul>
	   	</div>
   	
   	
   	
   	
	</div>	
	</cms:else>

	
	



</cms:Class>



<!--国内热门结束-->

<!--国外图片开始-->
<%--<div class="photobox grey-bg">
	<div class="main_box">
   	  <div class="title"><ins><a href="#"><i class="fa  fa-ellipsis-h fr"></i></a></ins><b>国外图片</b></div>
       		<ul class="picList">
           	<li><a href="#"><img src="img/051739.jpg" alt=""/><span class="name-txt">记录一个女孩到母亲的“蜕变”</span><b class="name-bg"></b></a></li>
            	<li><a href="#"><img src="img/051740.jpg" alt=""/><span class="name-txt">书写51年相濡以沫的爱情</span><b class="name-bg"></b></a></li>
              	<li><a href="#"><img src="img/051741.jpg" alt=""/><span class="name-txt">最刺激的高空观景台</span><b class="name-bg"></b></a></li>
            	<li><a href="#"><img src="img/051742.jpg" alt=""/><span class="name-txt">《雪原马》摄影作品</span><b class="name-bg"></b></a></li>
              	<li><a href="#"><img src="img/051743.jpg" alt=""/><span class="name-txt">胡同酒香</span><b class="name-bg"></b></a></li>
            	<li><a href="#"><img src="img/051744.jpg" alt=""/><span class="name-txt">叶子下避雨的青蛙</span><b class="name-bg"></b></a></li>
              	<li><a href="#"><img src="img/051745.jpg" alt=""/><span class="name-txt">普京拉开铁笼放生东北虎</span><b class="name-bg"></b></a></li>
            	<li><a href="#"><img src="img/051746.jpg" alt=""/><span class="name-txt">蜂蜜3种吃法暴瘦38斤</span><b class="name-bg"></b></a></li>
          	</ul>
   	</div>
</div>
<!--国外图片结束--> 


<!--视觉焦点开始-->
<div class="photobox">
	<div class="main_box">
   	  <div class="title"><ins><a href="#"><i class="fa  fa-ellipsis-h fr"></i></a></ins><b>视觉焦点</b></div>
       		<ul class="picList">
              	<li><a href="#"><img src="img/051743.jpg" alt=""/><span class="name-txt">胡同酒香</span><b class="name-bg"></b></a></li>
            	<li><a href="#"><img src="img/051744.jpg" alt=""/><span class="name-txt">叶子下避雨的青蛙</span><b class="name-bg"></b></a></li>
              	<li><a href="#"><img src="img/051745.jpg" alt=""/><span class="name-txt">普京拉开铁笼放生东北虎</span><b class="name-bg"></b></a></li>
            	<li><a href="#"><img src="img/051746.jpg" alt=""/><span class="name-txt">蜂蜜3种吃法暴瘦38斤</span><b class="name-bg"></b></a></li>
          	</ul>
   	</div>
</div>
<!--视觉焦点结束-->



--%><!--footer-->
<cms:Include page="include/foot.jsp"/>

</body>
</html>