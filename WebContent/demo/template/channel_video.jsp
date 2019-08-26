<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTopCMS - 演示站点</title>
<!--[if IE 7]>

<![endif]-->
<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>

<script type="text/javascript" src="${ResBase}js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ResBase}js/common.js"></script>
</head>


<body>
<!--头部开始-->
<cms:Class test="true" id="${param.channelId}"/> 

<cms:Include page="include/head.jsp"/>
<!--头部结束-->
<div class="main_br"></div>
<!--主体开始-->
<div class="main_box"><!--左侧-->
   	<div class="layoutcon news-br mt15">
    	 
        <div class="layoutLeft">
        	<div class="video-mod_bd mb15">
            	<!--左边-->
          		<div class="mod_figure_h_312 fl">
          		<cms:CommendContent flag="mh_sp_top" size="1">
              		<img src="${CommInfo.img}" alt=""/>
                  <a target="_blank" href="${CommInfo.url}" class="bigpic">
                  	<div class="figure_caption">
                    		<strong class="figure_title">${CommInfo.title}</strong>
                         	<p class="figure_desc">${CommInfo.summary}</p>
                    </div>
                  </a>
                </cms:CommendContent>
              	</div>
              	<!--右边-->
              	<div class="mod_figure_h_324 fr mod_figures">
              		<ul>
              		
              		<cms:CommendContent flag="mh_sp_lt" size="4">
              			<cms:Content id="${CommInfo.contentId}">
              			<li>
                    		
                         	<span class="imgs"><a target="_blank" href="${CommInfo.url}"><img src="${CommInfo.img}" alt=""/></a></span>
                         	<span class="txt-name"><a target="_blank" href="${CommInfo.url}">${CommInfo.title}</a></span>
                         	<span class="ico-play"><b><a target="_blank" href="${CommInfo.url}"><i class="fa  fa-youtube-play"></i>${Info.clickCount}</a></b><b><a target="_blank" href="${SiteBase}comment_list.jsp?id=${Info.contentId}"><i class="fa fa-comment"></i>${Info.commCount}</a></b></span>
                    	</li>
                    	</cms:Content>
              		
              		</cms:CommendContent>
              		
              		
                  
                 	</ul>
              	</div>
          	</div>
          	
          	<!--分隔线-->
          	<div class="figures_title"></div>
           <div class="video-mod_bd mod_figures mb15">
           	<ul>
                  	<cms:CommendContent flag="mh_sp_mb" size="4">
              			<cms:Content id="${CommInfo.contentId}">
              			<li>
                    		
                         	<span class="imgs"><a target="_blank" href="${CommInfo.url}"><img src="${CommInfo.img}" alt=""/></a></span>
                         	<span class="txt-name"><a target="_blank" href="${CommInfo.url}">${CommInfo.title}</a></span>
                         	<span class="ico-play"><b><a target="_blank" href="${CommInfo.url}"><i class="fa  fa-youtube-play"></i>${Info.clickCount}</a></b><b><a target="_blank" href="${SiteBase}comment_list.jsp?id=${Info.contentId}"><i class="fa fa-comment"></i>${Info.commCount}</a></b></span>
                    	</li>
                    	</cms:Content>
              		
              		</cms:CommendContent>
                 	</ul>
           </div>
           <!--热闹视频结束-->
           
           
          <!--列表-->
           <cms:Class idList="child:${param.channelId}">
           	
          
           <div class="video-mod_bd mod_figures mb15 ie7-height">
           	<div class="title">
            		<span class="b1"><a href="${Class.classUrl}">${Class.className}</a></span>
            		
            		<cms:Class objName="ChildClass" idList="child:${Class.classId}">
            		
            			<a href="${ChildClass.classUrl}">${ChildClass.className}</a>|
            			
            		</cms:Class>
                  
               
                  <a href="${Class.classUrl}">更多</a>
            	</div>
              	<ul class="limb15">
              	
	               	<cms:Content classId="allChild:${Class.classId}" filter="channelImg" pageSize="4">
	              	
	              		 <li>                       
	                        <span class="imgs"><a target="_blank" href="${Info.contentUrl}"><img src="${Info.channelImage}" alt="${Info.title}"/></a></span>
	                        <span class="txt-name"><a target="_blank" href="${Info.contentUrl}">${Info.title}</a></span>
	                        <span class="ico-play"><b><a target="_blank" href="${Info.contentUrl}"><i class="fa  fa-youtube-play"></i>${Info.clickCount}</a></b><b><a target="_blank" href="${SiteBase}comment_list.jsp?id=${Info.contentId}"><i class="fa fa-comment"></i>${Info.commCount}</a></b></span>
                  
	                     </li>
	              	
	              	</cms:Content>
	              	 
                </ul>
          	</div>
          	 </cms:Class>
          	</div>
        
    
   	</div>
    
   	<!--左侧结束-->
  <div class="area-sub fr mt15">
      <div class="news-pai">
          <div class="p-title">视频总排行榜</div>
          <div class="hot-video">
          <cms:CommendContent flag="mh_sp_mb" size="4">
              			<cms:Content id="${CommInfo.contentId}">
              			 
                    	 <span class="leftimg"><a target="_blank" href="${CommInfo.url}"><img src="${CommInfo.img}" width="122" height="68"  alt="${CommInfo.title}"/></a></span>
			              <div class="righttxt">
			              		<p><a target="_blank" href="${CommInfo.url}">${CommInfo.title}</a></p>
			                 	<div class="icoplay">
			                  	<b><a target="_blank" href="${CommInfo.url}"><i class="fa fa-youtube-play"></i>${Info.clickCount}</a></b>
			                   	  <b><a target="_blank" href="${SiteBase}comment_list.jsp?id=${Info.contentId}"><i class="fa fa-comment"></i>${Info.commCount}</a></b>
			              </div>
                    	</cms:Content>
              		
              		</cms:CommendContent>
             
              </div>
          </div>
        <div class="content-list">
          		<ul>
          		 
              		<cms:Content pageSize="10" classId="allChild:${param.channelId}" order="click-down">
              		
              		
              		<cms:if test="${status.index <3}">
              		
              		<li><span class="num blue-c">${status.index+1}</span><a target="_blank" href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
              		
              		</cms:if>
              		<cms:else>
              		
              		<li><span class="num grey-c">${status.index+1}</span><a target="_blank" href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
              		
              		
              		</cms:else>
              		
              		</cms:Content>
              
              	  	 
              	</ul>
          </div>
      </div>
      <!--排行结束-->
      
        <cms:Class idList="child:${param.channelId}">
          
          	  <div class="main_br"></div>
		      <!--时政聚焦-->
		      <div class="news-pai">
		        <div class="p-title">${Class.className}视频排行榜</div>
		        <div class="content-list">
		          <ul>
		          			 
		              		<cms:Content pageSize="10" classId="allChild:${Class.classId}" order="click-down">
		              		
		              		
		              		<cms:if test="${status.index <3}">
		              		
		              		<li><span class="num blue-c">${status.index+1}</span><a target="_blank" href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
		              		
		              		</cms:if>
		              		<cms:else>
		              		
		              		<li><span class="num grey-c">${status.index+1}</span><a target="_blank" href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
		              		
		              		
		              		</cms:else>
		              		
		              		</cms:Content>
		              
		              	 
		          </ul>
		        </div>
		      </div>
          
        </cms:Class>  
      
      
      
      
      
    
    </div>
  </div>
    

</div>

<!--主体结束-->
<div class="main_br"></div>
<cms:Include page="include/foot.jsp"/>


</body>
</html>