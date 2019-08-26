<%@ page contentType="text/html; charset=utf-8" session="false"%>
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
</head>
<cms:Class test="true" id="${param.channelId}"/> 
<%--<cms:ChannelWhiteView jump="sdsd" classId="${param.channelId}" />
--%><body>
<!--头部开始-->


 <cms:Include page="include/head.jsp"/>
<!--头部结束-->
<div class="main_br"></div>
<!--主体开始-->
<div class="main_box">
	<cms:Include page="include/child.jsp"/>
	
   	<!--左侧-->
   	<div class="layoutcon news-br mt15">
    	 
        <div class="layoutLeft pr15">
        	<!--头条开始-->
        	<div class="news-hot">
           	<div class="news2-big">
           		<cms:CommendContent flag="mh_gj_zd" size="1">
           			
            		<div class="imgs fl"><a target="_blank" href="${CommInfo.url}"><img src="${CommInfo.img}" width="400" height="278"  alt="${CommInfo.title}"/></a></div>
            		
                 	<div class="txtbox fr">
                  	<h3>${CommInfo.title}</h3>
                    	<span class="date"><cms:FormatDate date="${CommInfo.addTime}" /></span>
                     	<p class="contxt">${CommInfo.summary}</p>
            		</div>
            	</cms:CommendContent>
           	</div>
          	</div>
          	<!--头条结束-->
           <!--ad--><!--ad结束-->
          
          	<!--新闻页图片列表开始-->
           <div class="news-list-pic news2-list">
           	<ul>
	  		  		<cms:Class idList="child:${param.channelId}">
						<cms:if test="${status.first}">
						
							 
							 
				  		  	 <cms:Content  pageSize="8" classId="${Class.classId}" filter="classImg">
			
			                  <li>
			                  	<cms:if test="${Info.classImgFlag == 1}">
			                  	<div class="leftimg"><a target="_blank" href="${Info.contentUrl}"><img src="${Info.classImageCmsSysResize}"  alt=""/></a></div>
			                  	</cms:if>
			                  	<cms:if test="${Info.classImgFlag == 1}">
			                   	  	<div class="conright">
			                   	</cms:if>
			                   	<cms:else>
			                   		<div class="conright conright-100">
			                   	</cms:else>
			                   		<h3><a target="_blank" href="${Info.contentUrl}">${Info.title}</a></h3>
			                        	<span class="s-text">${Info.summary}…[<a target="_blank" href="${Info.contentUrl}">全文</a>]</span>			                   	
			                   	</div>
			                  </li>
			                  </cms:Content>	
			                 
						
						 
						</cms:if>	
					  </cms:Class>
            	</ul>
              	<!--加载开始--> 
               
           </div>
           <!--新闻页图片列表开始-->
        </div>
        
    
   	</div>
    
   	<!--左侧结束-->
  <div class="area-sub fr mt15">
     <cms:Include page="block/channel_content_click_order.jsp"/>
      <!--排行结束-->
      <div class="main_br"></div>
      <!--时政聚焦-->
      <cms:Include page="block/channel_content_new.jsp"/>
        <!--时政聚焦结束-->
        <div class="main_br"></div>
        <cms:Include page="block/channel_content_comment_order.jsp"/>
  </div>
    

</div>

<!--主体结束-->
<div class="main_br"></div>
<cms:Include page="include/foot.jsp"/>


</body>
</html>